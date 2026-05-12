import com.goodsender.client.ApiClient;
import com.goodsender.client.ApiException;
import com.goodsender.client.api.DomainsApi;
import com.goodsender.client.api.EmailsApi;
import com.goodsender.client.model.Address;
import com.goodsender.client.model.ConsentEmailEntry;
import com.goodsender.client.model.ConsentEmailRecipient;
import com.goodsender.client.model.ConsentEmailRequest;
import com.goodsender.client.model.SendEmail;
import com.goodsender.client.model.SendEmailRequest;
import com.goodsender.client.model.TemplateEmailRequest;
import com.goodsender.client.model.TemplateEmailRequestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Runner {

    record Result(String method, boolean ok, String detail) {}

    static final List<Result> results = new ArrayList<>();

    interface Body { String call() throws Exception; }

    static void run(String method, Body body) {
        try {
            String detail = body.call();
            results.add(new Result(method, true, detail));
        } catch (Exception ex) {
            String msg = ex.getMessage();
            if (msg == null) msg = ex.getClass().getSimpleName();
            results.add(new Result(method, false, ex.getClass().getSimpleName() + ": " + msg));
        }
    }

    public static void main(String[] args) {
        String baseUrl = System.getenv().getOrDefault("BASE_URL", "http://localhost:4010");
        String apiKey = System.getenv().getOrDefault("GOODSENDER_API_KEY", "test-key");

        ApiClient client = new ApiClient();
        // Strip trailing slash so setBasePath plays nicely with the generated path constants.
        client.setHost(baseUrl.replaceFirst("^https?://", "").replaceFirst("/.*$", ""));
        client.setScheme(baseUrl.startsWith("https://") ? "https" : "http");
        client.setRequestInterceptor(b -> b.header("Authorization", "Bearer " + apiKey));

        EmailsApi emails = new EmailsApi(client);
        DomainsApi domains = new DomainsApi(client);

        run("sendEmail", () -> {
            SendEmail e = new SendEmail()
                .from(new Address().email("sender@example.com"))
                .to(List.of(new Address().email("recipient@example.com")))
                .subject("Hello")
                .textContent("Body");
            SendEmailRequest req = new SendEmailRequest().emails(List.of(e));
            var res = emails.sendEmail(req);
            return "sent=" + res.getSent() + " declined=" + res.getDeclined();
        });

        run("sendTemplateEmail", () -> {
            TemplateEmailRequest req = new TemplateEmailRequest()
                .from(new Address().email("sender@example.com"))
                .to(new Address().email("recipient@example.com"))
                .subject("OTP")
                .template(new TemplateEmailRequestTemplate()
                    .templateId("otp_code")
                    .variables(Map.of("code", "123456")));
            var res = emails.sendTemplateEmail(req);
            return "status=" + res.getStatus();
        });

        run("requestEmailConsent", () -> {
            // Use the ConsentEmailRecipient (object) variant of ConsentEmailEntry.
            // The bare-String variant triggers a Jackson-serialization edge case
            // in the native template where the polymorphic wrapper writes the
            // string at the wrong nesting level.
            List<ConsentEmailEntry> entries = new ArrayList<>();
            entries.add(new ConsentEmailEntry(new ConsentEmailRecipient().email("smoke-java@example.com")));
            ConsentEmailRequest req = new ConsentEmailRequest()
                .domain("example.com")
                .emails(entries);
            var res = emails.requestEmailConsent(req);
            int n = res.getEmails() == null ? 0 : res.getEmails().size();
            return "emails=" + n;
        });

        run("getEmailConsentStatus", () -> {
            var res = emails.getEmailConsentStatus("user@example.com", "example.com");
            return "entries=" + res.size();
        });

        run("listEmailConsents", () -> {
            var res = emails.listEmailConsents("example.com", 50, null, null, null);
            int n = res.getEmails() == null ? 0 : res.getEmails().size();
            return "emails=" + n;
        });

        run("listDomains", () -> {
            var res = domains.listDomains(50, null);
            return "domains=" + res.getDomains().size();
        });

        for (Result r : results) {
            String tag = r.ok ? "PASS" : "FAIL";
            System.out.printf("%-4s  java    %-22s  %s%n", tag, r.method, r.detail);
        }
        long failed = results.stream().filter(r -> !r.ok).count();
        long passed = results.size() - failed;
        System.out.println();
        System.out.println(passed + " passed, " + failed + " failed");
        if (failed > 0) System.exit(1);
    }
}
