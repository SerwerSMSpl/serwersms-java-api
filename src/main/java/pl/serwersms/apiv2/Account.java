package pl.serwersms.apiv2;

import java.util.HashMap;

/**
 * @author SerwerSMS
 */
public class Account {

    private SerwerSMS master = null;

    public Account(SerwerSMS object) throws Exception {

        master = object;

    }

    /**
     * Register new account
     *
     * @param params request parameters; supported keys:
     *               "phone", "email", "first_name", "last_name", "company"
     * @return JSON response with a "success" (boolean) field
     */
    public String add(HashMap<String, String> params) {

        return master.send("account/add", params);

    }

    /**
     * Return limits SMS
     *
     * @param params request parameters; supported keys: "show_type" (boolean)
     * @return JSON response with an "items" array, where each item has
     *         "type" (message type), "chars_limit" (maximum message length)
     *         and "value" (message limit)
     */
    public String limits(HashMap<String, String> params) {

        return master.send("account/limits", params);

    }

    /**
     * Return contact details
     *
     * @return JSON response with "telephone", "email", "form", "faq" and an
     *         "account_maintainer" object ("name", "email", "telephone", "photo")
     */
    public String help() {

        return master.send("account/help", new HashMap<String, String>());

    }

    /**
     * Return messages from the administrator
     *
     * @return JSON response with a "new" (boolean, marks unread message) and a
     *         "message" field
     */
    public String messages() {

        return master.send("account/messages", new HashMap<String, String>());

    }
}
