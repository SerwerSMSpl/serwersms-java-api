package pl.serwersms.apiv2;

import java.util.HashMap;
import java.util.ArrayList;

/**
 *
 * @author SerwerSMS
 * @version: 1.2
 * @date 2022-09
 */
public class Message {

    private SerwerSMS master = null;

    public Message(SerwerSMS object) throws Exception {

        master = object;

    }

    /**
     * Sending messages
     *
     * @param String phone
     * @param String text Message
     * @param String sender Sender name only for FULL SMS
     * @param array params
     * @option String "details" Show details of messages
     * @option String "utf" Change encoding to UTF-8 (Only for FULL SMS)
     * @option String "flash"
     * @option String "speed" Priority canal only for FULL SMS
     * @option String "test" Test mode
     * @option String "vcard" vCard message
     * @option String "wap_push" WAP Push URL address
     * @option String "date" Set the date of sending
     * @option String "group_id" Sending to the group instead of a phone number
     * @option String "contact_id" Sending to phone from contacts
     * @option String "unique_id" Own identifiers of messages
     * @return array
     * @option boolean "success"
     * @option int "queued" Number of queued messages
     * @option int "unsent" Number of unsent messages
     * @option array "items"
     * @option String "id"
     * @option String "phone"
     * @option String "status" - queued|unsent
     * @option String "queued" Date of enqueued
     * @option int "parts" Number of parts a message
     * @option int "error_code"
     * @option String "error_message"
     * @option String "text"
     */
    public String sendSms(String phone, String text, String sender, HashMap<String, String> params) {

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("phone", phone);
        options.put("text", text);
        options.put("sender", sender);

        params.putAll(options);

        return master.send("messages/send_sms", params);

    }

    /**
     * Sending personalized messages
     *
     * @param array messages
     * @option String "phone"
     * @option String "text"
     * @param String sender Sender name only for FULL SMS
     * @param array params
     * @option String "details" Show details of messages
     * @option String "utf" Change encoding to UTF-8 (only for FULL SMS)
     * @option String "flash"
     * @option String "speed" Priority canal only for FULL SMS
     * @option String "test" Test mode
     * @option String "date" Set the date of sending
     * @option String "group_id" Sending to the group instead of a phone number
     * @option String "text" Message if is set group_id
     * @option String|array "uniqe_id" Own identifiers of messages
     * @option String "voice" Send VMS
     * @return array
     * @option boolean "success"
     * @option int "queued" Number of queued messages
     * @option int "unsent" Number of unsent messages
     * @option array "items"
     * @option String "id"
     * @option String "phone"
     * @option String "status" - queued|unsent
     * @option String "queued" Date of enqueued
     * @option int "parts" Number of parts a message
     * @option int "error_code"
     * @option String "error_message"
     * @option String "text"
     */
    public String sendPersonalized(ArrayList<HashMap<String, String>> messages, String sender, HashMap<String, String> params) {

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("sender", sender);

        StringBuffer messageList = new StringBuffer();

        for (HashMap<String, String> temp : messages) {
            messageList.append(temp.get("phone"));
            messageList.append(":");
            messageList.append(temp.get("text"));
            messageList.append("]|[");
        }
        
        String  message = messageList.toString();
        Integer length = message.length();
        if(length > 3) {
            length -= 3;
            message = message.substring(0,length);
        }
        
        options.put("messages", message);

        params.putAll(options);

        return master.send("messages/send_personalized", params);

    }

    /**
     * Sending Voice message
     *
     * @param String phone
     * @param array params
     * @option String "text" If send of text to voice
     * @option String "file_id" ID from wav files
     * @option String "date" Set the date of sending
     * @option String "test" Test mode
     * @option String "group_id" Sending to the group instead of a phone number
     * @option String "contact_id" Sending to phone from contacts
     * @return array
     * @option boolean "success"
     * @option int "queued" Number of queued messages
     * @option int "unsent" Number of unsent messages
     * @option array "items"
     * @option String "id"
     * @option String "phone"
     * @option String "status" - queued|unsent
     * @option String "queued" Date of enqueued
     * @option int "parts" Number of parts a message
     * @option int "error_code"
     * @option String "error_message"
     * @option String "text"
     */
    public String sendVoice(String phone, HashMap<String, String> params) {

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("phone", phone);

        params.putAll(options);

        return master.send("messages/send_voice", params);

    }

    /**
     * Sending MMS
     *
     * @param String phone
     * @param String title Title of message (max 40 chars)
     * @param array params
     * @option String "file_id"
     * @option String "file" File in base64 encoding
     * @option String "date" Set the date of sending
     * @option boolean "test" Test mode
     * @option String "group_id" Sending to the group instead of a phone number
     * @return array
     * @option boolean "success"
     * @option int "queued" Number of queued messages
     * @option int "unsent" Number of unsent messages
     * @option String "items"
     * @option String "id"
     * @option String "phone"
     * @option String "status" - queued|unsent
     * @option String "queued" Date of enqueued
     * @option int "parts" Number of parts a message
     * @option int "error_code"
     * @option String "error_message"
     * @option String "text"
     */
    public String sendMms(String phone, String title, HashMap<String, String> params) {

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("phone", phone);
        options.put("title", title);

        params.putAll(options);

        return master.send("messages/send_mms", params);

    }

    /**
     * View single message
     *
     * @param String id
     * @param array params
     * @option String "unique_id"
     * @option boolean "show_contact" Show details of the recipient from the contacts
     * @return array
     * @option String "id"
     * @option String "phone"
     * @option String "status" - delivered: The message is sent and delivered -
     * undelivered: The message is sent but not delivered - sent: The message is
     * sent and waiting for report - unsent: The message wasn't sent -
     * in_progress: The message is queued for sending - saved: The message was
     * saved in schedule
     * @option String "queued" Date of enqueued
     * @option String "sent" Date of sending
     * @option String "delivered" Date of deliver
     * @option String "sender"
     * @option String "type" - eco|full|mms|voice
     * @option String "text"
     * @option String "reason" - message_expired - unsupported_number -
     * message_rejected - missed_call - wrong_number - limit_exhausted -
     * lock_send - wrong_message - operator_error - wrong_sender_name -
     * number_is_blacklisted - sending_to_foreign_networks_is_locked -
     * no_permission_to_send_messages - other_error
     * @option array "contact"
     * @option String "first_name"
     * @option String "last_name"
     * @option String "company"
     * @option String "phone"
     * @option String "email"
     * @option String "tax_id"
     * @option String "city"
     * @option String "address"
     * @option String "description"
     */
    public String view(String id, HashMap<String, String> params) {

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("id", id);

        params.putAll(options);

        return master.send("messages/view", params);

    }

    /**
     * Checking messages reports
     *
     * @param array params
     * @option String "id" ID message
     * @option String "unique_id" ID message
     * @option String "phone"
     * @option String "date_from" The scope of the initial
     * @option String "date_to" The scope of the final
     * @option String "status" delivered|undelivered|pending|sent|unsent
     * @option String "type" eco|full|mms|voice
     * @option int "stat_id" Id package messages
     * @option boolean "show_contact" Show details of the recipient from the contacts
     * @option int "page" The number of the displayed page
     * @option int "limit" Limit items are displayed on the single page
     * @option String "order" asc|desc
     * @return array
     * @option array "paging"
     * @option int "page" The number of current page
     * @option int "count" The number of all pages
     * @option array items
     * @option String "id"
     * @option String "phone"
     * @option String "status" - delivered: The message is sent and delivered -
     * undelivered: The message is sent but not delivered - sent: The message is
     * sent and waiting for report - unsent: The message wasn't sent -
     * in_progress: The message is queued for sending - saved: The message was
     * saved in the scheduler
     * @option String "queued" Date of enqueued
     * @option String "sent" Date of sending
     * @option String "delivered" Date of deliver
     * @option String "sender"
     * @option String "type" - eco|full|mms|voice
     * @option String "text"
     * @option boolean "flash"
     * @option boolean "utf"
     * @option int "parts"
     * @option float "cost"
     * @option String "method"
     * @option int "mnc"
     * @option String "country"
     * @option String "network"
     * @option array "attachments"
     * @option int "id"
     * @option String "reason" - message_expired - unsupported_number -
     * message_rejected - missed_call - wrong_number - limit_exhausted -
     * lock_send - wrong_message - operator_error - wrong_sender_name -
     * number_is_blacklisted - sending_to_foreign_networks_is_locked -
     * no_permission_to_send_messages - other_error
     * @option array "contact"
     * @option int "id"
     * @option String "first_name"
     * @option String "last_name"
     * @option String "company"
     * @option String "phone"
     * @option String "email"
     * @option String "tax_id"
     * @option String "city"
     * @option String "address"
     * @option String "description"
     */
    public String reports(HashMap<String, String> params) {

        return master.send("messages/reports", params);

    }

    /**
     * Deleting message from the scheduler
     *
     * @param String id
     * @param String unique_id
     * @return array
     * @option boolean "success"
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
     * @param String type - eco SMS ECO replies - nd Incoming messages to ND number - ndi Incoming messages to ND number - mms Incoming MMS
     * @param array params
     * @option String "ndi" Filtering by NDI
     * @option String "phone" Filtering by phone
     * @option String "date_from" The scope of the initial
     * @option String "date_to" The scope of the final
     * @option boolean "read" Mark as read
     * @option int "page" The number of the displayed page
     * @option int "limit" Limit items are displayed on the single page
     * @option String "order" asc|desc
     * @return array
     * @option array "paging"
     * @option int "page" The number of current page
     * @option int "count" The number of all pages
     * @option array "items"
     * @option int "id"
     * @option String "type" eco|nd|ndi|mms
     * @option String "phone"
     * @option String "recived" Date of received message
     * @option String "message_id" ID of outgoing message (only for ECO SMS)
     * @option boolean "blacklist" Is the phone is blacklisted?
     * @option String "text" Message
     * @option String "to_number" Number of the recipient (for MMS)
     * @option String "title" Title of message (for MMS)
     * @option array "attachments" (for MMS)
     * @option int "id"
     * @option String "name"
     * @option String "content_type"
     * @option String "data" File
     * @option array "contact"
     * @option String "first_name"
     * @option String "last_name"
     * @option String "company"
     * @option String "phone"
     * @option String "email"
     * @option String "tax_id"
     * @option String "city"
     * @option String "address"
     * @option String "description"
     */
    public String recived(String type, HashMap<String, String> params) {

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("type", type);

        params.putAll(options);

        return master.send("messages/recived", params);

    }

    /**
     * Sending a message to an ND/SC
     *
     * @param String phone Sender phone number
     * @param String text Message
     * @return array
     * @option boolean "success"
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
     * @param String phone Sender phone number
     * @param String text Message
     * @param String ndi_number Recipient phone number
     * @return array
     * @option boolean "success"
     */
    public String sendNdi(String phone, String text, String ndi_number) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("phone", phone);
        params.put("text", text);
        params.put("ndi_number", ndi_number);

        return master.send("messages/send_ndi", params);

    }
}
