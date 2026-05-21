# GoodSender SDK for Java

Official client library for the GoodSender email API. Package: `com.goodsender:goodsender-sdk`


## Quick start

```java
import com.goodsender.client.ApiClient;
import com.goodsender.client.api.EmailsApi;
import com.goodsender.client.api.DomainsApi;
import com.goodsender.client.model.*;
import java.util.List;

ApiClient client = new ApiClient();
client.setHost("api.goodsender.com");
client.setScheme("https");
client.setRequestInterceptor(b -> b.header("Authorization", "Bearer YOUR_API_KEY"));

EmailsApi emails = new EmailsApi(client);
DomainsApi domains = new DomainsApi(client);

SendEmail e = new SendEmail()
    .from(new Address().email("sender@example.com"))
    .to(List.of(new Address().email("recipient@example.com")))
    .subject("Hello")
    .textContent("Body");
var res = emails.sendEmail(new SendEmailRequest().emails(List.of(e)));
System.out.println("sent=" + res.getSent() + " declined=" + res.getDeclined());
```

## Examples

### Send via a template

```java
TemplateEmailRequest req = new TemplateEmailRequest()
    .from(new Address().email("sender@example.com"))
    .to(new Address().email("recipient@example.com"))
    .subject("Your OTP")
    .template(new TemplateEmailRequestTemplate()
        .templateId("otp_code")
        .variables(Map.of("code", "123456")));
var res = emails.sendTemplateEmail(req);
System.out.println("status=" + res.getStatus());
```

### List domains

```java
var res = domains.listDomains(50, null);
System.out.println("domains=" + res.getDomains().size());
```

### Check consent status

```java
var res = emails.getEmailConsentStatus("user@example.com", "example.com");
System.out.println("entries=" + res.size());

// List all consents for a domain
var list = emails.listEmailConsents("example.com", 50, null, null, null);
System.out.println("emails=" + (list.getEmails() == null ? 0 : list.getEmails().size()));
```

## Documentation

- API reference: <https://goodsender.com/docs>
- OpenAPI spec: `openapi/goodsender.yaml` in this repo
- Conformance tests: `tests/`

## Development

- Regenerate from spec: `scripts/regen.sh` (preserves `tests/`, `.github/`, and hand-curated files per `.regen-ignore`)
- Run conformance tests against local mock: `tests/run.sh mock`
- Run conformance against real dev API: `tests/run.sh dev` (requires `tests/.env.dev`)

## License

MIT — see [LICENSE](LICENSE).
