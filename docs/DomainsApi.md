# DomainsApi

All URIs are relative to *https://api.goodsender.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**listDomains**](DomainsApi.md#listDomains) | **GET** /v1/domains | List domains |
| [**listDomainsWithHttpInfo**](DomainsApi.md#listDomainsWithHttpInfo) | **GET** /v1/domains | List domains |



## listDomains

> DomainListResponse listDomains(limit, cursor)

List domains

Retrieve a paginated list of sender domains for the workspace the API key belongs to. Each entry includes the domain&#39;s verification state so callers can detect when DNS records still need attention. 

### Example

```java
// Import classes:
import com.goodsender.client.ApiClient;
import com.goodsender.client.ApiException;
import com.goodsender.client.Configuration;
import com.goodsender.client.auth.*;
import com.goodsender.client.models.*;
import com.goodsender.client.api.DomainsApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.goodsender.com");
        
        // Configure HTTP bearer authorization: bearerAuth
        HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
        bearerAuth.setBearerToken("BEARER TOKEN");

        DomainsApi apiInstance = new DomainsApi(defaultClient);
        Integer limit = 50; // Integer | Maximum number of records to return.
        String cursor = "cursor_example"; // String | Cursor for pagination, returned as `nextCursor` from a previous response.
        try {
            DomainListResponse result = apiInstance.listDomains(limit, cursor);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DomainsApi#listDomains");
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
| **limit** | **Integer**| Maximum number of records to return. | [optional] [default to 50] |
| **cursor** | **String**| Cursor for pagination, returned as &#x60;nextCursor&#x60; from a previous response. | [optional] |

### Return type

[**DomainListResponse**](DomainListResponse.md)


### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | A paginated list of domains for the workspace. |  -  |
| **400** | Invalid request parameters. |  -  |
| **401** | Unauthorized - invalid or missing API key. |  -  |

## listDomainsWithHttpInfo

> ApiResponse<DomainListResponse> listDomainsWithHttpInfo(limit, cursor)

List domains

Retrieve a paginated list of sender domains for the workspace the API key belongs to. Each entry includes the domain&#39;s verification state so callers can detect when DNS records still need attention. 

### Example

```java
// Import classes:
import com.goodsender.client.ApiClient;
import com.goodsender.client.ApiException;
import com.goodsender.client.ApiResponse;
import com.goodsender.client.Configuration;
import com.goodsender.client.auth.*;
import com.goodsender.client.models.*;
import com.goodsender.client.api.DomainsApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.goodsender.com");
        
        // Configure HTTP bearer authorization: bearerAuth
        HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
        bearerAuth.setBearerToken("BEARER TOKEN");

        DomainsApi apiInstance = new DomainsApi(defaultClient);
        Integer limit = 50; // Integer | Maximum number of records to return.
        String cursor = "cursor_example"; // String | Cursor for pagination, returned as `nextCursor` from a previous response.
        try {
            ApiResponse<DomainListResponse> response = apiInstance.listDomainsWithHttpInfo(limit, cursor);
            System.out.println("Status code: " + response.getStatusCode());
            System.out.println("Response headers: " + response.getHeaders());
            System.out.println("Response body: " + response.getData());
        } catch (ApiException e) {
            System.err.println("Exception when calling DomainsApi#listDomains");
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
| **limit** | **Integer**| Maximum number of records to return. | [optional] [default to 50] |
| **cursor** | **String**| Cursor for pagination, returned as &#x60;nextCursor&#x60; from a previous response. | [optional] |

### Return type

ApiResponse<[**DomainListResponse**](DomainListResponse.md)>


### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | A paginated list of domains for the workspace. |  -  |
| **400** | Invalid request parameters. |  -  |
| **401** | Unauthorized - invalid or missing API key. |  -  |

