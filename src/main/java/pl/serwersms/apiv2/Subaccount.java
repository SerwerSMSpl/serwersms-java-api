package pl.serwersms.apiv2;

import java.util.HashMap;

/**
 * @author SerwerSMS
 */
public class Subaccount {

    private SerwerSMS master = null;

    public Subaccount(SerwerSMS object) throws Exception {

        master = object;

    }

    /**
     * Creating new subaccount
     *
     * @param subaccount_username subaccount username
     * @param subaccount_password subaccount password
     * @param subaccount_id       subaccount id used as a permissions template
     * @param params              request parameters; supported keys: "name",
     *                            "phone", "email"
     * @return JSON response with a "success" (boolean) field
     */
    public String add(String subaccount_username, String subaccount_password, String subaccount_id, HashMap<String, String> params) {

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("subaccount_username", subaccount_username);
        options.put("subaccount_password", subaccount_password);
        options.put("subaccount_id", subaccount_id);

        HashMap<String, String> requestParams = new HashMap<String, String>(params);
        requestParams.putAll(options);

        return master.send("subaccounts/add", requestParams);

    }

    /**
     * List of subaccounts
     *
     * @return JSON response with an "items" array, where each item has "id" and
     *         "username"
     */
    public String index() {

        return master.send("subaccounts/index", new HashMap<String, String>());

    }

    /**
     * View details of subaccount
     *
     * @param id subaccount identifier
     * @return JSON response with "id", "username", "name", "phone" and "email"
     *         fields
     */
    public String view(String id) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);

        return master.send("subaccounts/view", params);

    }

    /**
     * Setting the limit on subaccount
     *
     * @param id    subaccount identifier
     * @param type  message type (eco|full|voice|mms|hlr)
     * @param value limit value
     * @return JSON response with "success" (boolean) and "id" (int) fields
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
     * @param id subaccount identifier
     * @return JSON response with a "success" (boolean) field
     */
    public String delete(String id) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);

        return master.send("subaccounts/delete", params);

    }
    
}
