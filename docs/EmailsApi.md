# EmailsApi

All URIs are relative to *https://api.goodsender.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getEmailConsentStatus**](EmailsApi.md#getEmailConsentStatus) | **GET** /v1/emails/{email} | Get recipient consent status |
| [**getEmailConsentStatusWithHttpInfo**](EmailsApi.md#getEmailConsentStatusWithHttpInfo) | **GET** /v1/emails/{email} | Get recipient consent status |
| [**listEmailConsents**](EmailsApi.md#listEmailConsents) | **GET** /v1/emails | List email consent statuses |
| [**listEmailConsentsWithHttpInfo**](EmailsApi.md#listEmailConsentsWithHttpInfo) | **GET** /v1/emails | List email consent statuses |
| [**requestEmailConsent**](EmailsApi.md#requestEmailConsent) | **POST** /v1/emails/consent | Request recipients&#39; consent to receive emails from your domain |
| [**requestEmailConsentWithHttpInfo**](EmailsApi.md#requestEmailConsentWithHttpInfo) | **POST** /v1/emails/consent | Request recipients&#39; consent to receive emails from your domain |
| [**sendEmail**](EmailsApi.md#sendEmail) | **POST** /v1/emails/send | Send an email or a batch of emails |
| [**sendEmailWithHttpInfo**](EmailsApi.md#sendEmailWithHttpInfo) | **POST** /v1/emails/send | Send an email or a batch of emails |
| [**sendTemplateEmail**](EmailsApi.md#sendTemplateEmail) | **POST** /v1/emails/template | Send a transactional email using a template |
| [**sendTemplateEmailWithHttpInfo**](EmailsApi.md#sendTemplateEmailWithHttpInfo) | **POST** /v1/emails/template | Send a transactional email using a template |



## getEmailConsentStatus

> List<EmailAccount> getEmailConsentStatus(email, domain)

Get recipient consent status

Retrieve the current consent status for an email address. Optionally filter by sender domain.

### Example

```java
// Import classes:
import com.goodsender.client.ApiClient;
import com.goodsender.client.ApiException;
import com.goodsender.client.Configuration;
import com.goodsender.client.auth.*;
import com.goodsender.client.models.*;
import com.goodsender.client.api.EmailsApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.goodsender.com");
        
        // Configure HTTP bearer authorization: bearerAuth
        HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
        bearerAuth.setBearerToken("BEARER TOKEN");

        EmailsApi apiInstance = new EmailsApi(defaultClient);
        String email = "user@example.com"; // String | Email address to look up.
        String domain = "example.com"; // String | Optional sender domain to filter consent records by. When omitted, returns consent across all domains.
        try {
            List<EmailAccount> result = apiInstance.getEmailConsentStatus(email, domain);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling EmailsApi#getEmailConsentStatus");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **email** | **String**| Email address to look up. | |
| **domain** | **String**| Optional sender domain to filter consent records by. When omitted, returns consent across all domains. | [optional] |

### Return type

[**List&lt;EmailAccount&gt;**](EmailAccount.md)


### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Recipient consent status across all domains |  -  |
| **400** | Invalid email address |  -  |
| **404** | Email address was not found |  -  |

## getEmailConsentStatusWithHttpInfo

> ApiResponse<List<EmailAccount>> getEmailConsentStatusWithHttpInfo(email, domain)

Get recipient consent status

Retrieve the current consent status for an email address. Optionally filter by sender domain.

### Example

```java
// Import classes:
import com.goodsender.client.ApiClient;
import com.goodsender.client.ApiException;
import com.goodsender.client.ApiResponse;
import com.goodsender.client.Configuration;
import com.goodsender.client.auth.*;
import com.goodsender.client.models.*;
import com.goodsender.client.api.EmailsApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.goodsender.com");
        
        // Configure HTTP bearer authorization: bearerAuth
        HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
        bearerAuth.setBearerToken("BEARER TOKEN");

        EmailsApi apiInstance = new EmailsApi(defaultClient);
        String email = "user@example.com"; // String | Email address to look up.
        String domain = "example.com"; // String | Optional sender domain to filter consent records by. When omitted, returns consent across all domains.
        try {
            ApiResponse<List<EmailAccount>> response = apiInstance.getEmailConsentStatusWithHttpInfo(email, domain);
            System.out.println("Status code: " + response.getStatusCode());
            System.out.println("Response headers: " + response.getHeaders());
            System.out.println("Response body: " + response.getData());
        } catch (ApiException e) {
            System.err.println("Exception when calling EmailsApi#getEmailConsentStatus");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Response headers: " + e.getResponseHeaders());
            System.err.println("Reason: " + e.getResponseBody());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **email** | **String**| Email address to look up. | |
| **domain** | **String**| Optional sender domain to filter consent records by. When omitted, returns consent across all domains. | [optional] |

### Return type

ApiResponse<[**List&lt;EmailAccount&gt;**](EmailAccount.md)>


### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Recipient consent status across all domains |  -  |
| **400** | Invalid email address |  -  |
| **404** | Email address was not found |  -  |


## listEmailConsents

> EmailListResponse listEmailConsents(domain, limit, cursor, consentStatus, engagementStatus)

List email consent statuses

Retrieve a paginated list of email consent statuses for a domain.

### Example

```java
// Import classes:
import com.goodsender.client.ApiClient;
import com.goodsender.client.ApiException;
import com.goodsender.client.Configuration;
import com.goodsender.client.auth.*;
import com.goodsender.client.models.*;
import com.goodsender.client.api.EmailsApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.goodsender.com");
        
        // Configure HTTP bearer authorization: bearerAuth
        HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
        bearerAuth.setBearerToken("BEARER TOKEN");

        EmailsApi apiInstance = new EmailsApi(defaultClient);
        String domain = "example.com"; // String | Sender domain to filter consent records by.
        Integer limit = 50; // Integer | Maximum number of records to return.
        String cursor = "cursor_example"; // String | Cursor for pagination.
        String consentStatus = "pending"; // String | Status of the recipient's consent for receiving emails. 'pending' = awaiting consent email send, 'requested' = consent email dispatched, 'failed' = consent email delivery failed, 'granted' = recipient consented to receive emails, 'denied' = recipient declined to receive emails.
        String engagementStatus = "new"; // String | Status of the recipient's engagement with the emails.
        try {
            EmailListResponse result = apiInstance.listEmailConsents(domain, limit, cursor, consentStatus, engagementStatus);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling EmailsApi#listEmailConsents");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **domain** | **String**| Sender domain to filter consent records by. | |
| **limit** | **Integer**| Maximum number of records to return. | [optional] [default to 50] |
| **cursor** | **String**| Cursor for pagination. | [optional] |
| **consentStatus** | **String**| Status of the recipient&#39;s consent for receiving emails. &#39;pending&#39; &#x3D; awaiting consent email send, &#39;requested&#39; &#x3D; consent email dispatched, &#39;failed&#39; &#x3D; consent email delivery failed, &#39;granted&#39; &#x3D; recipient consented to receive emails, &#39;denied&#39; &#x3D; recipient declined to receive emails. | [optional] [enum: pending, requested, failed, granted, denied] |
| **engagementStatus** | **String**| Status of the recipient&#39;s engagement with the emails. | [optional] [enum: new, hot, warm, cooling, dormant, inactive] |

### Return type

[**EmailListResponse**](EmailListResponse.md)


### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | A paginated list of email consent statuses |  -  |
| **400** | Invalid request parameters |  -  |

## listEmailConsentsWithHttpInfo

> ApiResponse<EmailListResponse> listEmailConsentsWithHttpInfo(domain, limit, cursor, consentStatus, engagementStatus)

List email consent statuses

Retrieve a paginated list of email consent statuses for a domain.

### Example

```java
// Import classes:
import com.goodsender.client.ApiClient;
import com.goodsender.client.ApiException;
import com.goodsender.client.ApiResponse;
import com.goodsender.client.Configuration;
import com.goodsender.client.auth.*;
import com.goodsender.client.models.*;
import com.goodsender.client.api.EmailsApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.goodsender.com");
        
        // Configure HTTP bearer authorization: bearerAuth
        HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
        bearerAuth.setBearerToken("BEARER TOKEN");

        EmailsApi apiInstance = new EmailsApi(defaultClient);
        String domain = "example.com"; // String | Sender domain to filter consent records by.
        Integer limit = 50; // Integer | Maximum number of records to return.
        String cursor = "cursor_example"; // String | Cursor for pagination.
        String consentStatus = "pending"; // String | Status of the recipient's consent for receiving emails. 'pending' = awaiting consent email send, 'requested' = consent email dispatched, 'failed' = consent email delivery failed, 'granted' = recipient consented to receive emails, 'denied' = recipient declined to receive emails.
        String engagementStatus = "new"; // String | Status of the recipient's engagement with the emails.
        try {
            ApiResponse<EmailListResponse> response = apiInstance.listEmailConsentsWithHttpInfo(domain, limit, cursor, consentStatus, engagementStatus);
            System.out.println("Status code: " + response.getStatusCode());
            System.out.println("Response headers: " + response.getHeaders());
            System.out.println("Response body: " + response.getData());
        } catch (ApiException e) {
            System.err.println("Exception when calling EmailsApi#listEmailConsents");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Response headers: " + e.getResponseHeaders());
            System.err.println("Reason: " + e.getResponseBody());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **domain** | **String**| Sender domain to filter consent records by. | |
| **limit** | **Integer**| Maximum number of records to return. | [optional] [default to 50] |
| **cursor** | **String**| Cursor for pagination. | [optional] |
| **consentStatus** | **String**| Status of the recipient&#39;s consent for receiving emails. &#39;pending&#39; &#x3D; awaiting consent email send, &#39;requested&#39; &#x3D; consent email dispatched, &#39;failed&#39; &#x3D; consent email delivery failed, &#39;granted&#39; &#x3D; recipient consented to receive emails, &#39;denied&#39; &#x3D; recipient declined to receive emails. | [optional] [enum: pending, requested, failed, granted, denied] |
| **engagementStatus** | **String**| Status of the recipient&#39;s engagement with the emails. | [optional] [enum: new, hot, warm, cooling, dormant, inactive] |

### Return type

ApiResponse<[**EmailListResponse**](EmailListResponse.md)>


### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | A paginated list of email consent statuses |  -  |
| **400** | Invalid request parameters |  -  |


## requestEmailConsent

> ConsentEmailResult requestEmailConsent(consentEmailRequest)

Request recipients&#39; consent to receive emails from your domain

Send a consent message to each address so recipients can approve or reject future emails from your domain. Include the email addresses in the request body to start the consent flow. 

### Example

```java
// Import classes:
import com.goodsender.client.ApiClient;
import com.goodsender.client.ApiException;
import com.goodsender.client.Configuration;
import com.goodsender.client.auth.*;
import com.goodsender.client.models.*;
import com.goodsender.client.api.EmailsApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.goodsender.com");
        
        // Configure HTTP bearer authorization: bearerAuth
        HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
        bearerAuth.setBearerToken("BEARER TOKEN");

        EmailsApi apiInstance = new EmailsApi(defaultClient);
        ConsentEmailRequest consentEmailRequest = new ConsentEmailRequest(); // ConsentEmailRequest | 
        try {
            ConsentEmailResult result = apiInstance.requestEmailConsent(consentEmailRequest);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling EmailsApi#requestEmailConsent");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **consentEmailRequest** | [**ConsentEmailRequest**](ConsentEmailRequest.md)|  | |

### Return type

[**ConsentEmailResult**](ConsentEmailResult.md)


### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Recipient consent status for each address (found or created) |  -  |
| **400** | Invalid request |  -  |
| **429** | Too many consents are awaiting processing for this workspace. The internal release queue will drain pending entries automatically; retry later.  |  -  |
| **500** | Internal server error. The upfront quota reservation (if any) is refunded and the request is safe to retry — &#x60;getOrCreateEmailsInDb&#x60; is idempotent.  |  -  |
| **502** | Bad gateway - upstream email service unavailable |  -  |

## requestEmailConsentWithHttpInfo

> ApiResponse<ConsentEmailResult> requestEmailConsentWithHttpInfo(consentEmailRequest)

Request recipients&#39; consent to receive emails from your domain

Send a consent message to each address so recipients can approve or reject future emails from your domain. Include the email addresses in the request body to start the consent flow. 

### Example

```java
// Import classes:
import com.goodsender.client.ApiClient;
import com.goodsender.client.ApiException;
import com.goodsender.client.ApiResponse;
import com.goodsender.client.Configuration;
import com.goodsender.client.auth.*;
import com.goodsender.client.models.*;
import com.goodsender.client.api.EmailsApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.goodsender.com");
        
        // Configure HTTP bearer authorization: bearerAuth
        HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
        bearerAuth.setBearerToken("BEARER TOKEN");

        EmailsApi apiInstance = new EmailsApi(defaultClient);
        ConsentEmailRequest consentEmailRequest = new ConsentEmailRequest(); // ConsentEmailRequest | 
        try {
            ApiResponse<ConsentEmailResult> response = apiInstance.requestEmailConsentWithHttpInfo(consentEmailRequest);
            System.out.println("Status code: " + response.getStatusCode());
            System.out.println("Response headers: " + response.getHeaders());
            System.out.println("Response body: " + response.getData());
        } catch (ApiException e) {
            System.err.println("Exception when calling EmailsApi#requestEmailConsent");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Response headers: " + e.getResponseHeaders());
            System.err.println("Reason: " + e.getResponseBody());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **consentEmailRequest** | [**ConsentEmailRequest**](ConsentEmailRequest.md)|  | |

### Return type

ApiResponse<[**ConsentEmailResult**](ConsentEmailResult.md)>


### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Recipient consent status for each address (found or created) |  -  |
| **400** | Invalid request |  -  |
| **429** | Too many consents are awaiting processing for this workspace. The internal release queue will drain pending entries automatically; retry later.  |  -  |
| **500** | Internal server error. The upfront quota reservation (if any) is refunded and the request is safe to retry — &#x60;getOrCreateEmailsInDb&#x60; is idempotent.  |  -  |
| **502** | Bad gateway - upstream email service unavailable |  -  |


## sendEmail

> SendEmailResponse sendEmail(sendEmailRequest)

Send an email or a batch of emails

Send one or more emails. Emails can be sent only to recipients who have opted in to receive communications from your domain. The response indicates how many emails were sent versus not sent, based on each recipient&#39;s consent state. 

### Example

```java
// Import classes:
import com.goodsender.client.ApiClient;
import com.goodsender.client.ApiException;
import com.goodsender.client.Configuration;
import com.goodsender.client.auth.*;
import com.goodsender.client.models.*;
import com.goodsender.client.api.EmailsApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.goodsender.com");
        
        // Configure HTTP bearer authorization: bearerAuth
        HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
        bearerAuth.setBearerToken("BEARER TOKEN");

        EmailsApi apiInstance = new EmailsApi(defaultClient);
        SendEmailRequest sendEmailRequest = new SendEmailRequest(); // SendEmailRequest | List of emails to send
        try {
            SendEmailResponse result = apiInstance.sendEmail(sendEmailRequest);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling EmailsApi#sendEmail");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **sendEmailRequest** | [**SendEmailRequest**](SendEmailRequest.md)| List of emails to send | |

### Return type

[**SendEmailResponse**](SendEmailResponse.md)


### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Email(s) accepted for sending |  -  |
| **400** | Bad request - validation error |  -  |
| **401** | Unauthorized - invalid or missing API key |  -  |
| **413** | Payload too large |  -  |
| **429** | Quota exceeded. |  * Retry-After - Seconds until the quota resets. <br>  |
| **500** | Internal server error |  -  |
| **502** | Bad gateway - upstream email service unavailable |  -  |

## sendEmailWithHttpInfo

> ApiResponse<SendEmailResponse> sendEmailWithHttpInfo(sendEmailRequest)

Send an email or a batch of emails

Send one or more emails. Emails can be sent only to recipients who have opted in to receive communications from your domain. The response indicates how many emails were sent versus not sent, based on each recipient&#39;s consent state. 

### Example

```java
// Import classes:
import com.goodsender.client.ApiClient;
import com.goodsender.client.ApiException;
import com.goodsender.client.ApiResponse;
import com.goodsender.client.Configuration;
import com.goodsender.client.auth.*;
import com.goodsender.client.models.*;
import com.goodsender.client.api.EmailsApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.goodsender.com");
        
        // Configure HTTP bearer authorization: bearerAuth
        HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
        bearerAuth.setBearerToken("BEARER TOKEN");

        EmailsApi apiInstance = new EmailsApi(defaultClient);
        SendEmailRequest sendEmailRequest = new SendEmailRequest(); // SendEmailRequest | List of emails to send
        try {
            ApiResponse<SendEmailResponse> response = apiInstance.sendEmailWithHttpInfo(sendEmailRequest);
            System.out.println("Status code: " + response.getStatusCode());
            System.out.println("Response headers: " + response.getHeaders());
            System.out.println("Response body: " + response.getData());
        } catch (ApiException e) {
            System.err.println("Exception when calling EmailsApi#sendEmail");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Response headers: " + e.getResponseHeaders());
            System.err.println("Reason: " + e.getResponseBody());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **sendEmailRequest** | [**SendEmailRequest**](SendEmailRequest.md)| List of emails to send | |

### Return type

ApiResponse<[**SendEmailResponse**](SendEmailResponse.md)>


### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Email(s) accepted for sending |  -  |
| **400** | Bad request - validation error |  -  |
| **401** | Unauthorized - invalid or missing API key |  -  |
| **413** | Payload too large |  -  |
| **429** | Quota exceeded. |  * Retry-After - Seconds until the quota resets. <br>  |
| **500** | Internal server error |  -  |
| **502** | Bad gateway - upstream email service unavailable |  -  |


## sendTemplateEmail

> TemplateEmailResponse sendTemplateEmail(templateEmailRequest)

Send a transactional email using a template

Send a transactional email using a predefined template for common use cases like OTP codes, order confirmations, and new device login alerts. If the recipient has \&quot;denied\&quot; consent, the response returns &#x60;{\&quot;status\&quot;: \&quot;declined\&quot;}&#x60; and the email is not sent. Unknown recipients are auto-registered with \&quot;pending\&quot; consent. The template endpoint does not change the recipient&#39;s consent. Each email includes an approve/reject footer allowing the recipient to manage future communications. Provide the template ID and any variables to fill in the placeholders. All variables are optional and will be replaced with an empty string if omitted. URL-type variables must point to the same domain as the sender&#39;s email address. 

### Example

```java
// Import classes:
import com.goodsender.client.ApiClient;
import com.goodsender.client.ApiException;
import com.goodsender.client.Configuration;
import com.goodsender.client.auth.*;
import com.goodsender.client.models.*;
import com.goodsender.client.api.EmailsApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.goodsender.com");
        
        // Configure HTTP bearer authorization: bearerAuth
        HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
        bearerAuth.setBearerToken("BEARER TOKEN");

        EmailsApi apiInstance = new EmailsApi(defaultClient);
        TemplateEmailRequest templateEmailRequest = new TemplateEmailRequest(); // TemplateEmailRequest | Template email to send
        try {
            TemplateEmailResponse result = apiInstance.sendTemplateEmail(templateEmailRequest);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling EmailsApi#sendTemplateEmail");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **templateEmailRequest** | [**TemplateEmailRequest**](TemplateEmailRequest.md)| Template email to send | |

### Return type

[**TemplateEmailResponse**](TemplateEmailResponse.md)


### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Whether the templated email was sent |  -  |
| **400** | Bad request - invalid variables or request body |  -  |
| **401** | Unauthorized - invalid or missing API key |  -  |
| **404** | Template not found |  -  |
| **413** | Payload too large |  -  |
| **429** | Quota exceeded. |  * Retry-After - Seconds until the quota resets. <br>  |
| **500** | Internal server error |  -  |
| **502** | Bad gateway - upstream email service unavailable |  -  |

## sendTemplateEmailWithHttpInfo

> ApiResponse<TemplateEmailResponse> sendTemplateEmailWithHttpInfo(templateEmailRequest)

Send a transactional email using a template

Send a transactional email using a predefined template for common use cases like OTP codes, order confirmations, and new device login alerts. If the recipient has \&quot;denied\&quot; consent, the response returns &#x60;{\&quot;status\&quot;: \&quot;declined\&quot;}&#x60; and the email is not sent. Unknown recipients are auto-registered with \&quot;pending\&quot; consent. The template endpoint does not change the recipient&#39;s consent. Each email includes an approve/reject footer allowing the recipient to manage future communications. Provide the template ID and any variables to fill in the placeholders. All variables are optional and will be replaced with an empty string if omitted. URL-type variables must point to the same domain as the sender&#39;s email address. 

### Example

```java
// Import classes:
import com.goodsender.client.ApiClient;
import com.goodsender.client.ApiException;
import com.goodsender.client.ApiResponse;
import com.goodsender.client.Configuration;
import com.goodsender.client.auth.*;
import com.goodsender.client.models.*;
import com.goodsender.client.api.EmailsApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.goodsender.com");
        
        // Configure HTTP bearer authorization: bearerAuth
        HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
        bearerAuth.setBearerToken("BEARER TOKEN");

        EmailsApi apiInstance = new EmailsApi(defaultClient);
        TemplateEmailRequest templateEmailRequest = new TemplateEmailRequest(); // TemplateEmailRequest | Template email to send
        try {
            ApiResponse<TemplateEmailResponse> response = apiInstance.sendTemplateEmailWithHttpInfo(templateEmailRequest);
            System.out.println("Status code: " + response.getStatusCode());
            System.out.println("Response headers: " + response.getHeaders());
            System.out.println("Response body: " + response.getData());
        } catch (ApiException e) {
            System.err.println("Exception when calling EmailsApi#sendTemplateEmail");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Response headers: " + e.getResponseHeaders());
            System.err.println("Reason: " + e.getResponseBody());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **templateEmailRequest** | [**TemplateEmailRequest**](TemplateEmailRequest.md)| Template email to send | |

### Return type

ApiResponse<[**TemplateEmailResponse**](TemplateEmailResponse.md)>


### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Whether the templated email was sent |  -  |
| **400** | Bad request - invalid variables or request body |  -  |
| **401** | Unauthorized - invalid or missing API key |  -  |
| **404** | Template not found |  -  |
| **413** | Payload too large |  -  |
| **429** | Quota exceeded. |  * Retry-After - Seconds until the quota resets. <br>  |
| **500** | Internal server error |  -  |
| **502** | Bad gateway - upstream email service unavailable |  -  |

