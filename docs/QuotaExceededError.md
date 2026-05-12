

# QuotaExceededError


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**code** | [**CodeEnum**](#CodeEnum) | Machine-readable error code. |  |
|**kind** | [**KindEnum**](#KindEnum) | Whether the daily or monthly quota was exhausted. |  |
|**message** | **String** | Human-readable error message. |  |
|**limit** | **Integer** | Quota limit that was reached. |  |
|**used** | **Integer** | Number of emails already used against the quota. |  |
|**resetAt** | **OffsetDateTime** | Timestamp at which the quota window resets. |  |



## Enum: CodeEnum

| Name | Value |
|---- | -----|
| QUOTA_EXCEEDED | &quot;quota_exceeded&quot; |



## Enum: KindEnum

| Name | Value |
|---- | -----|
| DAILY | &quot;daily&quot; |
| MONTHLY | &quot;monthly&quot; |



