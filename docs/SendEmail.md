

# SendEmail

Must provide valid sender and subject. At least one recipient (from 'to', 'cc', or 'bcc') is required. Either 'text_content', 'html_content', 'markdown_content', or 'template_id' is required. When 'markdown_content' is provided, 'text_content' and 'html_content' are ignored. 

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**from** | [**Address**](Address.md) | Sender address (required) |  |
|**to** | [**List&lt;Address&gt;**](Address.md) | To recipients. At least one recipient (to, cc, or bcc) is required. Maximum 1000 recipients per email. |  |
|**subject** | **String** | The subject of the email (required) |  |
|**textContent** | **String** | Plain text content |  [optional] |
|**htmlContent** | **String** | HTML content |  [optional] |
|**markdownContent** | **String** | Markdown content. When provided, text_content and html_content are ignored. The raw markdown is used as text_content and rendered to HTML for html_content.  |  [optional] |
|**templateId** | **String** | Template ID for templated emails |  [optional] |
|**templateData** | **Map&lt;String, Object&gt;** | Data to populate template variables |  [optional] |
|**attachments** | [**List&lt;Attachment&gt;**](Attachment.md) | Email attachments |  [optional] |
|**headers** | **Map&lt;String, String&gt;** | Custom email headers |  [optional] |
|**replyTo** | [**Address**](Address.md) | Reply-to address |  [optional] |
|**sendTime** | **Long** | Unix timestamp for when to send the email. Must not be more than 72 hours in the future. If 0, sends immediately. |  [optional] |
|**webhookData** | **Map&lt;String, String&gt;** | Custom data to include in webhook events. Maximum 10 keys, key length 50 chars, value length 100 chars. |  [optional] |
|**tag** | **String** | Custom tag for tracking. Maximum 100 characters. |  [optional] |
|**tracking** | [**TrackingSettings**](TrackingSettings.md) | Email tracking settings |  [optional] |



