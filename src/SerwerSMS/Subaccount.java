package pl.serwersms.apiv2;

import java.util.HashMap;

/**
 *
 * @author SerwerSMS
 * @version: 1.2
 * @date 2022-09
 */
public class Subaccount {

    private SerwerSMS master = null;

    public Subaccount(SerwerSMS object) throws Exception {

        master = object;

    }

    /**
     * Creating new subaccount
     *
     * @param String subaccount_username
     * @param String subaccount_password
     * @param String subaccount_id Subaccount ID, which is template of powers
     * @param array params
     * @option String "name"
     * @option String "phone"
     * @option String "email"
     * @return type
     */
    public String add(String subaccount_username, String subaccount_password, String subaccount_id, HashMap<String, String> params) {

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("subaccount_username", subaccount_username);
        options.put("subaccount_password", subaccount_password);
        options.put("subaccount_id", subaccount_id);

        params.putAll(options);

        return master.send("subaccounts/add", params);

    }

    /**
     * List of subaccounts
     *
     * @return array
     * @option array "items"
     * @option int "id"
     * @option String "username"
     */
    public String index() {

        return master.send("subaccounts/index", new HashMap<String, String>());

    }

    /**
     * View details of subaccount
     *
     * @param String id
     * @return array
     * @option int "id"
     * @option String "username"
     * @option String "name"
     * @option String "phone"
     * @option String "email"
     */
    public String view(String id) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);

        return master.send("subaccounts/view", params);

    }

    /**
     * Setting the limit on subaccount
     *
     * @param String id
     * @param String type Message type: eco|full|voice|mms|hlr
     * @param String value
     * @return array
     * @option booleanean "success"
     * @option int "id"
     */
    public String limit(String id, String type, String value) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("type", type);
        params.put("value", value);

        return master.send("subaccounts/limit", params);

    }

    /**
     * Deleting a subaccount
     *
     * @param String id
     * @return array
     * @option booleanean "success"
     */
    public String delete(String id) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);

        return master.send("subaccounts/delete", params);

    }
    
}