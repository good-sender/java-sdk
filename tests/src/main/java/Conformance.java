import com.goodsender.client.ApiClient;
import com.goodsender.client.ApiException;
import com.goodsender.client.api.DomainsApi;
import com.goodsender.client.api.EmailsApi;
import com.goodsender.client.model.Address;
import com.goodsender.client.model.ConsentEmailEntry;
import com.goodsender.client.model.ConsentEmailRecipient;
import com.goodsender.client.model.ConsentEmailRequest;
import com.goodsender.client.model.Domain;
import com.goodsender.client.model.EmailAccount;
import com.goodsender.client.model.SendEmail;
import com.goodsender.client.model.SendEmailRequest;
import com.goodsender.client.model.TemplateEmailRequest;
import com.goodsender.client.model.TemplateEmailRequestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class Conformance {

    record Result(String id, String name, String status, String detail) {}

    static final List<Result> results = new ArrayList<>();

    interface Body { Outcome call() throws Exception; }
    record Outcome(boolean ok, String detail) {}

    static String requireEnv(String key) {
        String v = System.getenv(key);
        if (v == null || v.isEmpty()) {
            System.err.println("FATAL: " + key + " is not set in .env.dev");
            System.exit(2);
        }
        return v;
    }

    static String envOr(String key, String fallback) {
        String v = System.getenv(key);
        return (v == null || v.isEmpty()) ? fallback : v;
    }

    static void scenario(String id, String name, Body body) {
        try {
            Outcome o = body.call();
            results.add(new Result(id, name, o.ok() ? "PASS" : "FAIL", o.detail()));
        } catch (Exception ex) {
            String msg = ex.getMessage();
            if (msg == null) msg = ex.getClass().getSimpleName();
            if (msg.length() > 160) msg = msg.substring(0, 160);
            results.add(new Result(id, name, "FAIL", "unexpected: " + ex.getClass().getSimpleName() + ": " + msg));
        }
    }

    static void skip(String id, String name) {
        results.add(new Result(id, name, "SKIP", "destructive — set ALLOW_DESTRUCTIVE=1"));
    }

    static String bodySnippet(ApiException e) {
        String b = e.getResponseBody();
        if (b == null) return e.getMessage() == null ? "" : e.getMessage().substring(0, Math.min(160, e.getMessage().length()));
        return b.length() > 160 ? b.substring(0, 160) : b;
    }

    public static void main(String[] args) {
        String baseUrl = requireEnv("BASE_URL");
        String apiKey = requireEnv("GOODSENDER_API_KEY");
        boolean allowDestructive = "1".equals(System.getenv("ALLOW_DESTRUCTIVE"));

        final String verifiedDomain = requireEnv("VERIFIED_SENDER_DOMAIN");
        final String verifiedEmail = requireEnv("VERIFIED_SENDER_EMAIL");
        final String verifiedName = envOr("VERIFIED_SENDER_NAME", "GoodSender SDK Tests");
        final String unverifiedDomain = requireEnv("UNVERIFIED_SENDER_DOMAIN");
        final String unverifiedEmail = requireEnv("UNVERIFIED_SENDER_EMAIL");
        final String granted1 = requireEnv("RECIPIENT_GRANTED_1");
        final String granted2 = requireEnv("RECIPIENT_GRANTED_2");
        final String denied1 = requireEnv("RECIPIENT_DENIED_1");
        final String denied2 = requireEnv("RECIPIENT_DENIED_2");
        final String templateId = requireEnv("TEMPLATE_ID");

        final String runTag = "sdk-" + Long.toHexString(System.currentTimeMillis() / 1000) + "-" + Long.toHexString(new Random().nextLong() & 0xfffff);
        final String fresh1 = runTag + "-1@" + verifiedDomain;
        final String fresh2 = runTag + "-2@" + verifiedDomain;

        final ApiClient client = new ApiClient();
        client.setHost(baseUrl.replaceFirst("^https?://", "").replaceFirst("/.*$", ""));
        client.setScheme(baseUrl.startsWith("https://") ? "https" : "http");
        client.setRequestInterceptor(b -> b.header("Authorization", "Bearer " + apiKey));
        final EmailsApi emails = new EmailsApi(client);
        final DomainsApi domains = new DomainsApi(client);

        // ─── Read-only (R1–R6) ─────────────────────────────────────

        scenario("R1", "listDomains returns both fixtures with correct verification flags", () -> {
            var res = domains.listDomains(100, null);
            Map<String, Domain> byName = new HashMap<>();
            for (Domain d : res.getDomains()) byName.put(d.getDomain(), d);
            Domain v = byName.get(verifiedDomain);
            Domain u = byName.get(unverifiedDomain);
            if (v == null) return new Outcome(false, verifiedDomain + " not in listDomains response");
            if (u == null) return new Outcome(false, unverifiedDomain + " not in listDomains response");
            if (!Boolean.TRUE.equals(v.getVerification().getVerified()))
                return new Outcome(false, verifiedDomain + " has verification.verified=false; should be true");
            if (Boolean.TRUE.equals(u.getVerification().getVerified()))
                return new Outcome(false, unverifiedDomain + " has verification.verified=true; should be false");
            return new Outcome(true, "domains=" + res.getDomains().size() + ", verified=true, unverified=false");
        });

        scenario("R2", "getEmailConsentStatus returns granted for approved recipient", () -> {
            List<EmailAccount> res = emails.getEmailConsentStatus(granted1, verifiedDomain);
            EmailAccount entry = null;
            for (EmailAccount e : res) if (verifiedDomain.equals(e.getDomain())) { entry = e; break; }
            if (entry == null) return new Outcome(false, "no entry for domain=" + verifiedDomain);
            String cs = String.valueOf(entry.getConsentStatus());
            if (!"granted".equals(cs)) return new Outcome(false, "consentStatus=" + cs + ", expected granted");
            return new Outcome(true, "consentStatus=" + cs);
        });

        scenario("R3", "getEmailConsentStatus returns denied for rejected recipient", () -> {
            List<EmailAccount> res = emails.getEmailConsentStatus(denied1, verifiedDomain);
            EmailAccount entry = null;
            for (EmailAccount e : res) if (verifiedDomain.equals(e.getDomain())) { entry = e; break; }
            if (entry == null) return new Outcome(false, "no entry for domain=" + verifiedDomain);
            String cs = String.valueOf(entry.getConsentStatus());
            if (!"denied".equals(cs)) return new Outcome(false, "consentStatus=" + cs + ", expected denied");
            return new Outcome(true, "consentStatus=" + cs);
        });

        scenario("R4", "getEmailConsentStatus returns 404 for unknown recipient", () -> {
            String probe = runTag + "-r4-probe@" + verifiedDomain;
            try {
                List<EmailAccount> res = emails.getEmailConsentStatus(probe, verifiedDomain);
                return new Outcome(false, "expected 404, got 200 with " + res.size() + " entries");
            } catch (ApiException ex) {
                if (ex.getCode() == 404) return new Outcome(true, "404 (probe=" + probe + ")");
                return new Outcome(false, "expected 404, got " + ex.getCode() + " " + bodySnippet(ex));
            }
        });

        scenario("R5", "listEmailConsents for verified domain includes all 4 fixtures", () -> {
            List<String> collected = new ArrayList<>();
            String cursor = null;
            for (int p = 0; p < 20; p++) {
                var res = emails.listEmailConsents(verifiedDomain, 100, cursor, null, null);
                for (EmailAccount e : res.getEmails()) collected.add(e.getEmail());
                cursor = res.getNextCursor();
                if (cursor == null || cursor.isEmpty()) break;
            }
            List<String> missing = new ArrayList<>();
            for (String e : List.of(granted1, granted2, denied1, denied2))
                if (!collected.contains(e)) missing.add(e);
            if (!missing.isEmpty()) return new Outcome(false, "missing from listEmailConsents: " + String.join(", ", missing));
            return new Outcome(true, collected.size() + " entries scanned; all 4 fixtures present");
        });

        scenario("R6", "listEmailConsents with consentStatus=granted filter excludes denied", () -> {
            Set<String> collected = new HashSet<>();
            Set<String> statuses = new HashSet<>();
            String cursor = null;
            int pages = 0;
            for (int p = 0; p < 20; p++) {
                var res = emails.listEmailConsents(verifiedDomain, 100, cursor, "granted", null);
                pages++;
                for (EmailAccount e : res.getEmails()) {
                    collected.add(e.getEmail());
                    statuses.add(String.valueOf(e.getConsentStatus()));
                }
                cursor = res.getNextCursor();
                if (cursor == null || cursor.isEmpty()) break;
            }
            Set<String> nonGranted = new TreeSet<>(statuses);
            nonGranted.remove("granted");
            if (!nonGranted.isEmpty()) return new Outcome(false, "filter leaked non-granted statuses: " + String.join(",", nonGranted));
            List<String> missing = new ArrayList<>();
            for (String e : List.of(granted1, granted2)) if (!collected.contains(e)) missing.add(e);
            if (!missing.isEmpty()) {
                List<String> sample = new ArrayList<>();
                for (String s : collected) { sample.add(s); if (sample.size() >= 5) break; }
                String sampleStr = sample.isEmpty() ? "(none)" : String.join(", ", sample);
                return new Outcome(false, "filter returned " + collected.size() + " entries across " + pages + " page(s); missing=" + String.join(",", missing) + "; sample=[" + sampleStr + "]");
            }
            if (collected.contains(denied1) || collected.contains(denied2)) return new Outcome(false, "denied fixtures leaked into granted filter");
            return new Outcome(true, collected.size() + " granted entries; denied fixtures absent");
        });

        // ─── Destructive (D1–D6, E1–E5) ────────────────────────────

        if (allowDestructive) {
            scenario("D1", "sendEmail to 2 granted recipients delivers both", () -> {
                SendEmail e = new SendEmail()
                    .from(new Address().email(verifiedEmail).name(verifiedName))
                    .to(List.of(new Address().email(granted1), new Address().email(granted2)))
                    .subject("SDK conformance D1 " + runTag)
                    .textContent("D1 — granted recipients");
                var res = emails.sendEmail(new SendEmailRequest().emails(List.of(e)));
                if (res.getSent() == 2 && res.getDeclined() == 0) return new Outcome(true, "sent=" + res.getSent() + " declined=" + res.getDeclined());
                return new Outcome(false, "sent=" + res.getSent() + " declined=" + res.getDeclined() + ", expected 2/0");
            });

            scenario("D2", "sendEmail to 2 denied recipients declines both", () -> {
                SendEmail e = new SendEmail()
                    .from(new Address().email(verifiedEmail).name(verifiedName))
                    .to(List.of(new Address().email(denied1), new Address().email(denied2)))
                    .subject("SDK conformance D2 " + runTag)
                    .textContent("D2");
                var res = emails.sendEmail(new SendEmailRequest().emails(List.of(e)));
                if (res.getSent() == 0 && res.getDeclined() == 2) return new Outcome(true, "sent=" + res.getSent() + " declined=" + res.getDeclined());
                return new Outcome(false, "sent=" + res.getSent() + " declined=" + res.getDeclined() + ", expected 0/2");
            });

            scenario("D3", "sendEmail granted+denied mix splits correctly", () -> {
                SendEmail e = new SendEmail()
                    .from(new Address().email(verifiedEmail).name(verifiedName))
                    .to(List.of(new Address().email(granted1), new Address().email(denied1)))
                    .subject("SDK conformance D3 " + runTag)
                    .textContent("D3");
                var res = emails.sendEmail(new SendEmailRequest().emails(List.of(e)));
                if (res.getSent() == 1 && res.getDeclined() == 1) return new Outcome(true, "sent=" + res.getSent() + " declined=" + res.getDeclined());
                return new Outcome(false, "sent=" + res.getSent() + " declined=" + res.getDeclined() + ", expected 1/1");
            });

            scenario("D4", "sendTemplateEmail to granted recipient returns status=sent", () -> {
                var req = new TemplateEmailRequest()
                    .from(new Address().email(verifiedEmail).name(verifiedName))
                    .to(new Address().email(granted1))
                    .subject("SDK conformance D4 " + runTag)
                    .template(new TemplateEmailRequestTemplate().templateId(templateId).variables(Map.of()));
                var res = emails.sendTemplateEmail(req);
                String s = String.valueOf(res.getStatus());
                return "sent".equals(s) ? new Outcome(true, "status=sent") : new Outcome(false, "status=" + s + ", expected sent");
            });

            scenario("D5", "sendTemplateEmail to denied recipient returns status=declined", () -> {
                var req = new TemplateEmailRequest()
                    .from(new Address().email(verifiedEmail).name(verifiedName))
                    .to(new Address().email(denied1))
                    .subject("SDK conformance D5 " + runTag)
                    .template(new TemplateEmailRequestTemplate().templateId(templateId).variables(Map.of()));
                var res = emails.sendTemplateEmail(req);
                String s = String.valueOf(res.getStatus());
                return "declined".equals(s) ? new Outcome(true, "status=declined") : new Outcome(false, "status=" + s + ", expected declined");
            });

            scenario("D6", "requestEmailConsent registers 2 fresh addresses", () -> {
                List<ConsentEmailEntry> entries = new ArrayList<>();
                entries.add(new ConsentEmailEntry(new ConsentEmailRecipient().email(fresh1).name("Fresh 1")));
                entries.add(new ConsentEmailEntry(new ConsentEmailRecipient().email(fresh2).name("Fresh 2")));
                var req = new ConsentEmailRequest().domain(verifiedDomain).emails(entries);
                var res = emails.requestEmailConsent(req);
                int n = res.getEmails() == null ? 0 : res.getEmails().size();
                if (n != 2) return new Outcome(false, "expected 2 entries, got " + n);
                List<String> statuses = new ArrayList<>();
                for (EmailAccount e : res.getEmails()) statuses.add(String.valueOf(e.getConsentStatus()));
                java.util.Collections.sort(statuses);
                return new Outcome(true, "2 fresh addresses; statuses=[" + String.join(",", statuses) + "] " + fresh1 + " " + fresh2);
            });

            scenario("E1", "sendEmail from unverified domain is rejected", () -> {
                SendEmail e = new SendEmail()
                    .from(new Address().email(unverifiedEmail))
                    .to(List.of(new Address().email(granted1)))
                    .subject("SDK conformance E1 " + runTag)
                    .textContent("should be rejected");
                try {
                    var res = emails.sendEmail(new SendEmailRequest().emails(List.of(e)));
                    return new Outcome(false, "expected 4xx, got 200 sent=" + res.getSent());
                } catch (ApiException ex) {
                    int code = ex.getCode();
                    if (code >= 400 && code < 500) return new Outcome(true, code + " " + bodySnippet(ex));
                    return new Outcome(false, "expected 4xx, got " + code + " " + bodySnippet(ex));
                }
            });

            scenario("E2", "sendTemplateEmail from unverified domain is rejected", () -> {
                var req = new TemplateEmailRequest()
                    .from(new Address().email(unverifiedEmail))
                    .to(new Address().email(granted1))
                    .subject("SDK conformance E2 " + runTag)
                    .template(new TemplateEmailRequestTemplate().templateId(templateId).variables(Map.of()));
                try {
                    var res = emails.sendTemplateEmail(req);
                    return new Outcome(false, "expected 4xx, got 200 status=" + res.getStatus());
                } catch (ApiException ex) {
                    int code = ex.getCode();
                    if (code >= 400 && code < 500) return new Outcome(true, code + " " + bodySnippet(ex));
                    return new Outcome(false, "expected 4xx, got " + code + " " + bodySnippet(ex));
                }
            });

            scenario("E3", "sendTemplateEmail with bogus template_id returns 404", () -> {
                String bad = runTag + "-does-not-exist";
                var req = new TemplateEmailRequest()
                    .from(new Address().email(verifiedEmail))
                    .to(new Address().email(granted1))
                    .subject("SDK conformance E3 " + runTag)
                    .template(new TemplateEmailRequestTemplate().templateId(bad).variables(Map.of()));
                try {
                    var res = emails.sendTemplateEmail(req);
                    return new Outcome(false, "expected 404, got 200 status=" + res.getStatus());
                } catch (ApiException ex) {
                    if (ex.getCode() == 404) return new Outcome(true, "404 " + bodySnippet(ex));
                    return new Outcome(false, "expected 404, got " + ex.getCode() + " " + bodySnippet(ex));
                }
            });

            scenario("E4", "requestEmailConsent for unverified domain is rejected", () -> {
                String fresh = runTag + "-e4-target@example.com";
                List<ConsentEmailEntry> entries = new ArrayList<>();
                entries.add(new ConsentEmailEntry(fresh));
                var req = new ConsentEmailRequest().domain(unverifiedDomain).emails(entries);
                try {
                    var res = emails.requestEmailConsent(req);
                    int n = res.getEmails() == null ? 0 : res.getEmails().size();
                    return new Outcome(false, "expected 4xx, got 200 emails=" + n);
                } catch (ApiException ex) {
                    int code = ex.getCode();
                    if (code >= 400 && code < 500) return new Outcome(true, code + " " + bodySnippet(ex));
                    return new Outcome(false, "expected 4xx, got " + code + " " + bodySnippet(ex));
                }
            });

            scenario("E5", "listEmailConsents for non-existent domain", () -> {
                String bogus = "not-a-real-domain-" + runTag + ".invalid";
                try {
                    var res = emails.listEmailConsents(bogus, 1, null, null, null);
                    int n = res.getEmails() == null ? 0 : res.getEmails().size();
                    return new Outcome(true, "200 emails=" + n + " (no error path for unknown domain)");
                } catch (ApiException ex) {
                    int code = ex.getCode();
                    if (code >= 400 && code < 500) return new Outcome(true, code + " " + bodySnippet(ex));
                    return new Outcome(false, "unexpected: " + code + " " + bodySnippet(ex));
                }
            });
        } else {
            String[][] pending = {
                {"D1", "sendEmail to 2 granted recipients"},
                {"D2", "sendEmail to 2 denied recipients"},
                {"D3", "sendEmail granted+denied mix"},
                {"D4", "sendTemplateEmail to granted"},
                {"D5", "sendTemplateEmail to denied"},
                {"D6", "requestEmailConsent for 2 fresh addresses"},
                {"E1", "sendEmail from unverified domain rejected"},
                {"E2", "sendTemplateEmail from unverified domain rejected"},
                {"E3", "sendTemplateEmail with bogus template_id"},
                {"E4", "requestEmailConsent for unverified domain rejected"},
                {"E5", "listEmailConsents for non-existent domain"},
            };
            for (String[] p : pending) skip(p[0], p[1]);
        }

        for (Result r : results) {
            String name = r.name();
            if (name.length() > 58) name = name.substring(0, 58);
            System.out.printf("%-4s  java    %s  %-58s  %s%n", r.status(), r.id(), name, r.detail());
        }
        long passed = results.stream().filter(r -> "PASS".equals(r.status())).count();
        long failed = results.stream().filter(r -> "FAIL".equals(r.status())).count();
        long skipped = results.stream().filter(r -> "SKIP".equals(r.status())).count();
        System.out.println();
        System.out.println(passed + " passed, " + failed + " failed, " + skipped + " skipped");
        if (allowDestructive) {
            System.out.println();
            System.out.println("Destructive run created consent records for cleanup:");
            System.out.println("  " + fresh1);
            System.out.println("  " + fresh2);
        }
        if (failed > 0) System.exit(1);
    }
}
