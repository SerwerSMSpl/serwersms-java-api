package SerwerSMS;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import org.json.*;

/**
 *
 * @author SerwerSMS
 * @version: 1.0
 * @date 2016-01
 */
public class SerwerSMS {

    private String username = "";
    private String password = "";
    private String api = "https://api2.serwersms.pl/";
    private String format = "json";
    private String client = "client_java";
    private static HttpURLConnection http;

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
    public Premium premium = null;
    public Template template = null;
    public Subaccount subaccount = null;
    public Payment payment = null;

    public SerwerSMS(String user, String pass) throws Exception {

        if (user.isEmpty() || pass.isEmpty()) {
            throw new Exception("Brak danych autoryzacyjnych");
        }

        username = user;
        password = pass;

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
        premium = new Premium(this);
        template = new Template(this);
        subaccount = new Subaccount(this);
        payment = new Payment(this);

    }

    public String send(String action, HashMap<String, String> params) {

        StringBuilder response = new StringBuilder();

        try {

            params.put("username", username);
            params.put("password", password);
            params.put("system", client);

            request(action, params);

            InputStream inputStream = null;
            if (http != null) {

                inputStream = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                http.disconnect();
            }

            return response.toString();

        } catch (Exception e) {

            return "";

        }
    }

    public byte[] sendByte(String action, HashMap<String, String> params) {

        try {

            params.put("username", username);
            params.put("password", password);

            request(action, params);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            InputStream inputStream = null;
            if (http != null) {

                inputStream = http.getInputStream();

                byte[] ba1 = new byte[1024];
                int length;

                while ((length = inputStream.read(ba1)) != -1) {
                    bos.write(ba1, 0, length);
                }

                http.disconnect();
            }

            return bos.toByteArray();

        } catch (Exception e) {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            return bos.toByteArray();

        }
    }

    public boolean setFormat(String type) {

        type = type.toLowerCase();

        if (type == "xml" || type == "json") {
            format = type;
            return true;
        }

        return false;
    }

    private void request(String action, HashMap<String, String> params) throws Exception {

        action += "." + format;

        String requestURL = "";
        requestURL = requestURL.concat(api);
        requestURL = requestURL.concat(action);

        URL url = new URL(requestURL);
        http = (HttpURLConnection) url.openConnection();

        http.setUseCaches(false);
        http.setDoInput(true);
        http.setRequestMethod("POST");
        http.setRequestProperty("Content-Type", "application/json");

        JSONObject jsonParam = new JSONObject();

        if (params != null && params.size() > 0) {

            http.setDoOutput(true);

            Iterator<String> paramIterator = params.keySet().iterator();
            while (paramIterator.hasNext()) {
                String key = paramIterator.next();
                String value = params.get(key);
                jsonParam.put(key, value);
            }

            String requestString = jsonParam.toString();
            
            OutputStream os = http.getOutputStream();
            os.write(requestString.getBytes("UTF-8"));             
            os.close();
        }

    }
}
