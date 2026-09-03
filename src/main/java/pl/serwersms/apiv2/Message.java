package pl.serwersms.apiv2;

import java.util.HashMap;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author SerwerSMS
 */
public class Message {

    private SerwerSMS master = null;

    public Message(SerwerSMS object) throws Exception {

        master = object;

    }

    /**
     * Sending messages
     *
     * @param phone  recipient phone number(s)
     * @param text   message text
     * @param sender sender name (only for FULL SMS)
     * @param params request parameters; supported keys: "details", "utf",
     *               "flash", "speed", "test", "vcard", "wap_push", "date",
     *               "group_id", "contact_id", "unique_id"
     * @return JSON response with "success", "queued", "unsent" and an "items"
     *         array (each item: "id", "phone", "status" (queued|unsent),
     *         "queued", "parts", "error_code", "error_message", "text")
     */
    public String sendSms(String phone, String text, String sender, HashMap<String, String> params) {

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("phone", phone);
        options.put("text", text);
        options.put("sender", sender);

        HashMap<String, String> requestParams = new HashMap<String, String>(params);
        requestParams.putAll(options);

        return master.send("messages/send_sms", requestParams);

    }

    /**
     * Sending personalized messages
     *
     * @param messages list of messages; each entry must contain "phone" and
     *                 "text" keys
     * @param sender   sender name (only for FULL SMS)
     * @param params   request parameters; supported keys: "details", "utf",
     *                 "flash", "speed", "test", "date", "group_id", "text",
     *                 "unique_id", "voice"
     * @return JSON response with "success", "queued", "unsent" and an "items"
     *         array (each item: "id", "phone", "status" (queued|unsent),
     *         "queued", "parts", "error_code", "error_message", "text")
     */
    public String sendPersonalized(ArrayList<HashMap<String, String>> messages, String sender, HashMap<String, String> params) {

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("sender", sender);

        JSONArray messagesArray = new JSONArray();

        for (HashMap<String, String> temp : messages) {

            String phone = temp.get("phone");
            String text = temp.get("text");

            if (phone == null || text == null) {
                throw new IllegalArgumentException(
                        "Each entry in 'messages' passed to sendPersonalized() must contain 'phone' and 'text' keys"
                );
            }

            JSONObject entry = new JSONObject();
            entry.put("phone", phone);
            entry.put("text", text);
            messagesArray.put(entry);
        }

        options.put("messages", messagesArray.toString());

        HashMap<String, String> requestParams = new HashMap<String, String>(params);
        requestParams.putAll(options);

        return master.send("messages/send_personalized", requestParams);

    }

    /**
     * Sending Voice message
     *
     * @param phone  recipient phone number
     * @param params request parameters; supported keys: "text" (text to voice),
     *               "file_id" (id of a wav file), "date", "test", "group_id",
     *               "contact_id"
     * @return JSON response with "success", "queued", "unsent" and an "items"
     *         array (each item: "id", "phone", "status" (queued|unsent),
     *         "queued", "parts", "error_code", "error_message", "text")
     */
    public String sendVoice(String phone, HashMap<String, String> params) {

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("phone", phone);

        HashMap<String, String> requestParams = new HashMap<String, String>(params);
        requestParams.putAll(options);

        return master.send("messages/send_voice", requestParams);

    }

    /**
     * Sending MMS
     *
     * @param phone  recipient phone number
     * @param title  message title (max 40 chars)
     * @param params request parameters; supported keys: "file_id", "file"
     *               (base64-encoded file), "date", "test", "group_id"
     * @return JSON response with "success", "queued", "unsent" and an "items"
     *         array (each item: "id", "phone", "status" (queued|unsent),
     *         "queued", "parts", "error_code", "error_message", "text")
     */
    public String sendMms(String phone, String title, HashMap<String, String> params) {

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("phone", phone);
        options.put("title", title);

        HashMap<String, String> requestParams = new HashMap<String, String>(params);
        requestParams.putAll(options);

        return master.send("messages/send_mms", requestParams);

    }

    /**
     * View single message
     *
     * @param id     message identifier
     * @param params request parameters; supported keys: "unique_id",
     *               "show_contact" (show recipient details from the contacts)
     * @return JSON response with the message details: "id", "phone", "status"
     *         (delivered|undelivered|sent|unsent|in_progress|saved), "queued",
     *         "sent", "delivered", "sender", "type" (eco|full|mms|voice),
     *         "text", "reason" and a "contact" object
     */
    public String view(String id, HashMap<String, String> params) {

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("id", id);

        HashMap<String, String> requestParams = new HashMap<String, String>(params);
        requestParams.putAll(options);

        return master.send("messages/view", requestParams);

    }

    /**
     * Checking messages reports
     *
     * @param params request parameters; supported keys: "id", "unique_id",
     *               "phone", "date_from", "date_to", "status"
     *               (delivered|undelivered|pending|sent|unsent), "type"
     *               (eco|full|mms|voice), "stat_id", "show_contact", "page",
     *               "limit", "order" (asc|desc)
     * @return JSON response with a "paging" object ("page", "count") and an
     *         "items" array; each item includes "id", "phone", "status",
     *         "queued", "sent", "delivered", "sender", "type", "text", "flash",
     *         "utf", "parts", "cost", "method", "mnc", "country", "network",
     *         "attachments", "reason" and a "contact" object
     */
    public String reports(HashMap<String, String> params) {

        return master.send("messages/reports", params);

    }

    /**
     * Deleting message from the scheduler
     *
     * @param id        message identifier
     * @param unique_id own message identifier
     * @return JSON response with a "success" (boolean) field
     */
    public String delete(String id, String unique_id) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("unique_id", unique_id);

        return master.send("messages/delete", params);

    }

    /**
     * List of received messages
     *
     * @param type   message type: eco (SMS ECO replies), nd (incoming to ND
     *               number), ndi (incoming to NDI number), mms (incoming MMS)
     * @param params request parameters; supported keys: "ndi", "phone",
     *               "date_from", "date_to", "read", "page", "limit", "order"
     *               (asc|desc)
     * @return JSON response with a "paging" object ("page", "count") and an
     *         "items" array; each item includes "id", "type" (eco|nd|ndi|mms),
     *         "phone", "received", "message_id", "blacklist", "text",
     *         "to_number", "title", "attachments" and a "contact" object
     */
    public String received(String type, HashMap<String, String> params) {

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("type", type);

        HashMap<String, String> requestParams = new HashMap<String, String>(params);
        requestParams.putAll(options);

        return master.send("messages/received", requestParams);

    }

    /**
     * Sending a message to an ND/SC
     *
     * @param phone sender phone number
     * @param text  message text
     * @return JSON response with a "success" (boolean) field
     */
    public String sendNd(String phone, String text) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("phone", phone);
        params.put("text", text);

        return master.send("messages/send_nd", params);

    }

    /**
     * Sending a message to an NDI/SCI
     *
     * @param phone      sender phone number
     * @param text       message text
     * @param ndi_number recipient phone number
     * @return JSON response with a "success" (boolean) field
     */
    public String sendNdi(String phone, String text, String ndi_number) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("phone", phone);
        params.put("text", text);
        params.put("ndi_number", ndi_number);

        return master.send("messages/send_ndi", params);

    }
}
