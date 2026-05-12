

# ConsentEmailEntry

Single entry in the `emails` array of a consent request. Either a bare email string (back-compat) or a `{ email, name? }` object so callers can attach a display name to a specific recipient. 

## oneOf schemas
* [ConsentEmailRecipient](ConsentEmailRecipient.md)
* [String](String.md)

## Example
```java
// Import classes:
import com.goodsender.client.model.ConsentEmailEntry;
import com.goodsender.client.model.ConsentEmailRecipient;
import com.goodsender.client.model.String;

public class Example {
    public static void main(String[] args) {
        ConsentEmailEntry exampleConsentEmailEntry = new ConsentEmailEntry();

        // create a new ConsentEmailRecipient
        ConsentEmailRecipient exampleConsentEmailRecipient = new ConsentEmailRecipient();
        // set ConsentEmailEntry to ConsentEmailRecipient
        exampleConsentEmailEntry.setActualInstance(exampleConsentEmailRecipient);
        // to get back the ConsentEmailRecipient set earlier
        ConsentEmailRecipient testConsentEmailRecipient = (ConsentEmailRecipient) exampleConsentEmailEntry.getActualInstance();

        // create a new String
        String exampleString = new String();
        // set ConsentEmailEntry to String
        exampleConsentEmailEntry.setActualInstance(exampleString);
        // to get back the String set earlier
        String testString = (String) exampleConsentEmailEntry.getActualInstance();
    }
}
```


