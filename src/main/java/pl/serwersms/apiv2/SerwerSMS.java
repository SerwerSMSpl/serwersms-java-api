package pl.serwersms.apiv2;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import org.json.*;

/**
 * @author SerwerSMS
 */
public class SerwerSMS {

    private String token = "";
    private String api = "https://api2.serwersms.pl/";
    private String format = "json";
    private String client = "client_java";
    private int connectTimeoutMillis = 30000;
    private int readTimeoutMillis = 30000;

    public Message message = null;
    public File file = null;
    public Blacklist blacklist = null;
    public Fault fault = null;
    public Stat stat = null;
    public Phone phone = null;
    public Account account = null;
    public Contact contact = null;
    public Group group = null;
    public Broadcaster broadcaster = null;
    public Template template = null;
    public Subaccount subaccount = null;
    public Payment payment = null;

    /**
     * Creates a client with the given API token.
     *
     * @param auth API authorization token
     * @throws Exception if the token is empty
     */
    public SerwerSMS(String auth) throws Exception {

        if (auth.isEmpty()) {
            throw new Exception("Empty token");
        }

        token = auth;

        message = new Message(this);
        file = new File(this);
        blacklist = new Blacklist(this);
        fault = new Fault(this);
        stat = new Stat(this);
        phone = new Phone(this);
        account = new Account(this);
        contact = new Contact(this);
        group = new Group(this);
        broadcaster = new Broadcaster(this);
        template = new Template(this);
        subaccount = new Subaccount(this);
        payment = new Payment(this);

    }

    /**
     * Sends a POST request to the given API action and returns the raw response.
     *
     * @param action API action path (e.g. "messages/send_sms")
     * @param params request parameters
     * @return the raw API response body
     * @throws SerwerSMSException if the request fails or the API returns an HTTP error
     */
    public String send(String action, HashMap<String, String> params) {

        StringBuilder response = new StringBuilder();
        HttpURLConnection http = null;

        try {
            http = request(action, token, params);

            if (http != null) {
                int statusCode = http.getResponseCode();

                if (statusCode >= 400) {
                    throw new SerwerSMSException(
                            "SerwerSMS API returned HTTP error " + statusCode + " for action '" + action + "'",
                            statusCode,
                            null
                    );
                }

                InputStream inputStream = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
            }

            return response.toString();

        } catch (SerwerSMSException e) {

            throw e;

        } catch (Exception e) {

            throw new SerwerSMSException(
                    "Error calling SerwerSMS API for action '" + action + "': " + e.getMessage(),
                    e
            );

        } finally {

            if (http != null) {
                http.disconnect();
            }
        }
    }

    /**
     * Sends a POST request and returns the raw response as bytes (e.g. for PDF).
     *
     * @param action API action path
     * @param params request parameters
     * @return the raw API response as a byte array
     * @throws SerwerSMSException if the request fails or the API returns an HTTP error
     */
    public byte[] sendByte(String action, HashMap<String, String> params) {

        HttpURLConnection http = null;

        try {

            http = request(action, token, params);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            if (http != null) {

                int statusCode = http.getResponseCode();

                if (statusCode >= 400) {
                    throw new SerwerSMSException(
                            "SerwerSMS API returned HTTP error " + statusCode + " for action '" + action + "'",
                            statusCode,
                            null
                    );
                }

                InputStream inputStream = http.getInputStream();

                byte[] ba1 = new byte[1024];
                int length;

                while ((length = inputStream.read(ba1)) != -1) {
                    bos.write(ba1, 0, length);
                }
            }

            return bos.toByteArray();

        } catch (SerwerSMSException e) {

            throw e;

        } catch (Exception e) {

            throw new SerwerSMSException(
                    "Error calling SerwerSMS API for action '" + action + "': " + e.getMessage(),
                    e
            );

        } finally {

            if (http != null) {
                http.disconnect();
            }
        }
    }

    /**
     * Overrides the default API URL. A trailing slash is added if missing.
     *
     * @param apiUrl non-null, non-empty, http(s) URL
     * @throws IllegalArgumentException if the URL is null, empty or malformed
     */
    public void setApiUrl(String apiUrl) {
        if (apiUrl == null || apiUrl.isEmpty()) {
            throw new IllegalArgumentException("API URL must not be null or empty");
        }
        if (!apiUrl.endsWith("/")) {
            apiUrl = apiUrl + "/";
        }
        try {
            URL parsed = new URL(apiUrl);
            String protocol = parsed.getProtocol();
            if (!protocol.equals("http") && !protocol.equals("https")) {
                throw new IllegalArgumentException("API URL must use http or https, got: " + protocol);
            }
            String host = parsed.getHost();
            if (host == null || host.isEmpty()) {
                throw new IllegalArgumentException("API URL must contain a host, got: " + apiUrl);
            }
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("API URL is malformed: " + apiUrl, e);
        }
        this.api = apiUrl;
    }

    /**
     * Sets the HTTP connection timeouts (in milliseconds) for SerwerSMS API calls.
     * Default value is 30000 ms (30 seconds) for both.
     *
     * @param connectTimeoutMillis maximum time to establish a connection
     * @param readTimeoutMillis    maximum time to wait for a response after connection is established
     */
    public void setTimeout(int connectTimeoutMillis, int readTimeoutMillis) {
        if (connectTimeoutMillis < 0) {
            throw new IllegalArgumentException("connectTimeoutMillis must not be negative, got: " + connectTimeoutMillis);
        }
        if (readTimeoutMillis < 0) {
            throw new IllegalArgumentException("readTimeoutMillis must not be negative, got: " + readTimeoutMillis);
        }
        this.connectTimeoutMillis = connectTimeoutMillis;
        this.readTimeoutMillis = readTimeoutMillis;
    }

    /**
     * Sets the response format.
     *
     * @param type response format, "json" or "xml" (case-insensitive)
     * @return true if the format was accepted, false otherwise
     */
    public boolean setFormat(String type) {

        if (type == null) {
            return false;
        }

        type = type.toLowerCase();

        if (type.equals("xml") || type.equals("json")) {
            format = type;
            return true;
        }

        return false;
    }

    private HttpURLConnection request(String action, String token, HashMap<String, String> params) throws Exception {

        action += "." + format;

        String requestURL = "";
        requestURL = requestURL.concat(api);
        requestURL = requestURL.concat(action);

        URL url = new URL(requestURL);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();

        http.setConnectTimeout(connectTimeoutMillis);
        http.setReadTimeout(readTimeoutMillis);
        http.setUseCaches(false);
        http.setDoInput(true);
        http.setRequestMethod("POST");
        http.setRequestProperty("Content-Type", "application/json");
        http.setRequestProperty("Authorization", "Bearer " + token);

        JSONObject jsonParam = new JSONObject();

        if (params != null && params.size() > 0) {

            Iterator<String> paramIterator = params.keySet().iterator();
            while (paramIterator.hasNext()) {
                String key = paramIterator.next();
                String value = params.get(key);

                // "messages" is a special case from sendPersonalized() - it contains
                // a pre-serialized JSON array of {phone, text} objects and must be
                // inserted as JSONArray, not as a plain string value.
                if (key.equals("messages")) {
                    jsonParam.put(key, new JSONArray(value));
                } else {
                    jsonParam.put(key, value);
                }
            }
        }

        jsonParam.put("system", client);
        http.setDoOutput(true);

        String requestString = jsonParam.toString();

        OutputStream os = http.getOutputStream();
        os.write(requestString.getBytes("UTF-8"));
        os.close();

        return http;
    }
}
