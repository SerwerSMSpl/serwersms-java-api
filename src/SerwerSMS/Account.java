package pl.serwersms.apiv2;

import java.util.HashMap;

/**
 *
 * @author SerwerSMS
 * @version: 1.2
 * @date 2022-09
 */
public class Account {

    private SerwerSMS master = null;

    public Account(SerwerSMS object) throws Exception {

        master = object;

    }

    /**
     * Register new account
     *
     * @param array params
     * @option String "phone"
     * @option String "email"
     * @option String "first_name"
     * @option String "last_name"
     * @option String "company"
     * @return array
     * @option bool "success"
     */
    
    public String add(HashMap<String, String> params) {

        return master.send("account/add", params);

    }

    /**
     * Return limits SMS
     *
     * @param array params
     * @option boolean "show_type"
     * @return array
     * @option array "items"
     * @option string "type" Type of message
     * @option string "chars_limit" The maximum length of message
     * @option string "value" Limit messages
     *
     */
    public String limits(HashMap<String, String> params) {

        return master.send("account/limits", params);

    }

    /**
     * Return contact details
     *
     * @return array
     * @option String "telephone"
     * @option String "email"
     * @option String "form"
     * @option String "faq"
     * @option array "account_maintainer"
     * @option String "name"
     * @option String "email"
     * @option String "telephone"
     * @option String "photo"
     */
    public String help() {

        return master.send("account/help", new HashMap<String, String>());

    }

    /**
     * Return messages from the administrator
     *
     * @return array
     * @option boolean "new" Marking unread message
     * @option String "message"
     */
    public String messages() {

        return master.send("account/messages", new HashMap<String, String>());

    }
}