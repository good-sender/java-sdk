

# DomainVerification

Per-record verification state for the domain. `verified` is the overall flag; the individual `*_verified` fields indicate which DNS records still need attention when `verified` is `false`. 

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**verified** | **Boolean** | Overall verification status. True only when every required DNS record is in place. |  |
|**trackingVerified** | **Boolean** | Whether the tracking subdomain CNAME is in place. |  |
|**returnPathVerified** | **Boolean** | Whether the return-path subdomain CNAME is in place. |  |
|**dkim1Verified** | **Boolean** | Whether the first DKIM record is in place. |  |
|**dkim2Verified** | **Boolean** | Whether the second DKIM record is in place. |  |



