# SerwerSMS.pl JAVA Client API
Klient JAVA do komunikacji zdalnej z API v2 SerwerSMS.pl

Uwaga. Wersja 2.0.0 działa w oparciu o token API.

W celu autoryzacji za pośrednictwem Tokenu API, należy wygenerować go po stronie Panelu Klienta w menu Ustawienia interfejsów → HTTP API → Tokeny API. Format nagłówka autoryzacyjnego jest zgodny z formatem Bearer token.

## Instalacja (Maven)

```xml
<dependency>
  <groupId>pl.serwersms</groupId>
  <artifactId>apiv2</artifactId>
  <version>2.0.0</version>
</dependency>
```

## Wymagania
Java 17 lub nowsza

Biblioteka do obsługi formatu JSON lub XML (org.json)

## Konfiguracja

Token API przekazywany jest w konstruktorze. Pozostałe ustawienia (adres API, limity czasu, format odpowiedzi) są opcjonalne i mają wartości domyślne.

```java
SerwerSMS SerwerSMSApi = new SerwerSMS("token");

// Opcjonalnie: własny adres API (domyślnie https://api2.serwersms.pl/)
SerwerSMSApi.setApiUrl("https://api2.serwersms.pl/");

// Opcjonalnie: limity czasu połączenia i odczytu w milisekundach (domyślnie 30000)
SerwerSMSApi.setTimeout(5000, 5000);

// Opcjonalnie: format odpowiedzi - "json" (domyślny) lub "xml"
SerwerSMSApi.setFormat("json");
```

Uwaga: konfiguracja przekazywana jest jawnie przez konstruktor i metody ustawiające. Jeśli wartości mają pochodzić ze środowiska uruchomieniowego, aplikacja odczytuje zmienne środowiskowe samodzielnie i przekazuje je do biblioteki, np. `SerwerSMSApi.setApiUrl(System.getenv("SERWERSMS_API_URL"))`.

## Obsługa błędów

Błędy komunikacji z API (błąd HTTP, przekroczenie limitu czasu, problem połączenia) zgłaszane są jako `SerwerSMSException`. Błędy aplikacyjne API zwracane są w treści odpowiedzi (pole `error`).

```java
try {
    SerwerSMS SerwerSMSApi = new SerwerSMS("token");

    HashMap<String, String> options = new HashMap<String, String>();
    options.put("test", "true");

    String result = SerwerSMSApi.message.sendSms("500600700", "Test message", "INFORMACJA", options);
    System.out.println(result);
} catch (SerwerSMSException e) {
    // Błędy komunikacji z API (nieopakowany RuntimeException)
    System.out.println("Błąd API: " + e.getMessage());
} catch (Exception e) {
    // Błąd konstruktora (np. pusty token) - konstruktor deklaruje throws Exception
    System.out.println("Błąd inicjalizacji: " + e.getMessage());
}
```

## Przykładowe wywołanie
```java
import java.io.*;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.json.*;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import pl.serwersms.apiv2.*;

public class NewMain {

    public static void main(String[] args) {

        try {

            SerwerSMS SerwerSMSApi = new SerwerSMS("token");

            HashMap<String, String> options = new HashMap<String, String>();
            options.put("test", "true");
            options.put("details", "true");

            String type = "json";
            SerwerSMSApi.setFormat(type);
            String result = SerwerSMSApi.message.sendSms("500600700", "Test message", "", options);

            if (type.equals("xml")) {

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(new InputSource(new StringReader(result)));
                NodeList list = document.getElementsByTagName("item");

                for (int temp = 0; temp < list.getLength(); temp++) {

                    Node item = list.item(temp);
                    if (item.getNodeType() == Node.ELEMENT_NODE) {

                        Element element = (Element) item;
                        String id = element.getElementsByTagName("id").item(0).getTextContent();
                        String phone = element.getElementsByTagName("phone").item(0).getTextContent();
                        String status = element.getElementsByTagName("status").item(0).getTextContent();

                        System.out.println(id + " - " + phone + " - " + status);
                    }
                }

            } else if (type.equals("json")) {

                JSONObject json = new JSONObject(result);
                JSONArray jsonArray = new JSONArray(json.get("items").toString());

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject tmp = new JSONObject(jsonArray.get(i).toString());
                    String id = tmp.get("id").toString();
                    String phone = tmp.get("phone").toString();
                    String status = tmp.get("status").toString();

                    System.out.println(id + " - " + phone + " - " + status);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
```

#### Wysyłka SMS (FULL)
```java
try {
    SerwerSMS SerwerSMSApi = new SerwerSMS("token");

    HashMap<String, String> options = new HashMap<String, String>();
    options.put("test", "true");
    options.put("details", "true");

    String result = SerwerSMSApi.message.sendSms("500600700,600700800", "Test message", "INFORMACJA", options);
    System.out.println(result);
} catch (Exception e) {
    System.out.println(e.getMessage());
}
```

#### Wysyłka SMS (ECO)
```java
try {
    SerwerSMS SerwerSMSApi = new SerwerSMS("token");

    HashMap<String, String> options = new HashMap<String, String>();
    options.put("test", "true");
    options.put("details", "true");

    String result = SerwerSMSApi.message.sendSms("500600700,600700800", "Test message", "", options);
    System.out.println(result);
} catch (Exception e) {
    System.out.println(e.getMessage());
}
```

#### Wysyłka VOICE (z tekstu)
```java
try {
    SerwerSMS SerwerSMSApi = new SerwerSMS("token");

    HashMap<String, String> options = new HashMap<String, String>();
    options.put("test", "true");
    options.put("text", "Test message");
    options.put("details", "true");

    String result = SerwerSMSApi.message.sendVoice("500600700", options);
    System.out.println(result);
} catch (Exception e) {
    System.out.println(e.getMessage());
}
```

#### Wysyłka MMS
```java
try {
    SerwerSMS SerwerSMSApi = new SerwerSMS("token");

    HashMap<String, String> options = new HashMap<String, String>();
    options.put("test", "true");
    options.put("file_id", "82aa9f108c");

    String result = SerwerSMSApi.message.sendMms("500600700", "Title message", options);
    System.out.println(result);
} catch (Exception e) {
    System.out.println(e.getMessage());
}
```

#### Wysyłka spersonalizowanych SMS
```java
try {

    ArrayList<HashMap<String, String>> listMessages = new ArrayList<HashMap<String, String>>();

    // Message nr. 1
    HashMap<String, String> message = new HashMap<String, String>();
    message.put("phone", "500600700");
    message.put("text", "Test message 1");
    listMessages.add(message);

    // Message nr. 2
    message = new HashMap<String, String>();
    message.put("phone", "600700800");
    message.put("text", "Test message 2");
    listMessages.add(message);

    HashMap<String, String> options = new HashMap<String, String>();
    options.put("test", "true");
    options.put("details", "true");

    String result = SerwerSMSApi.message.sendPersonalized(listMessages, "", options);
    System.out.println(result);
    
} catch (Exception e) {
    System.out.println(e.getMessage());
}
```

#### Pobieranie raportów doręczeń
```java
try {

    HashMap<String, String> options = new HashMap<String, String>();
    options.put("id", "b7ac0db51a,0a83b5d5eb");
    String result = SerwerSMSApi.message.reports(options);
    System.out.println(result);

} catch (Exception e) {
    System.out.println(e.getMessage());
}
```

#### Pobieranie wiadomości przychodzących
```java
try {

    HashMap<String, String> options = new HashMap<String, String>();
    options.put("phone", "500600700");
    // typ wiadomości: eco | nd | ndi | mms
    String result = SerwerSMSApi.message.received("ndi", options);

} catch (Exception e) {
    System.out.println(e.getMessage());
}
```

## Migracja z wersji 1.2

Wersja 2.0.0 wprowadza zmiany niekompatybilne wstecz:

- Wymagana jest Java 17 (poprzednio Java 8).
- Błędy komunikacji z API zgłaszane są jako `SerwerSMSException` (wcześniej były zwracane w treści odpowiedzi lub zgłaszane jako ogólny wyjątek). `SerwerSMSException` dziedziczy po `RuntimeException` (jest nieopakowany), więc nie wymaga deklaracji `throws` ani obowiązkowego `catch`.
- Metoda `Message.recived` została poprawiona na `Message.received`.
- Usunięto klasę `Premium`.

## Dokumentacja
http://dev.serwersms.pl

## Konsola API
http://apiconsole.serwersms.pl

## Maven
https://central.sonatype.com/artifact/pl.serwersms/apiv2
