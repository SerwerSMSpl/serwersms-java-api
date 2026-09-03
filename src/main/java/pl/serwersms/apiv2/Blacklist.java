package pl.serwersms.apiv2;

import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * @author SerwerSMS
 */
public class Blacklist {

    private SerwerSMS master = null;

    public Blacklist(SerwerSMS object) throws Exception {

        master = object;

    }

    /**
     * Add phone to the blacklist
     *
     * @param phone phone number to add
     * @return JSON response with "success" (boolean) and "id" (int) fields
     */
    public String add(String phone) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("phone", phone);

        return master.send("blacklist/add", params);

    }

    /**
     * List of blacklist phones
     *
     * @param phone  phone number filter (may be empty)
     * @param params request parameters; supported keys: "page" (displayed page
     *               number), "limit" (items per page)
     * @return JSON response with a "paging" object ("page" - current page,
     *         "count" - total pages) and an "items" array, where each item has
     *         "phone" and "added" (date the phone was added)
     */
    public String index(String phone, HashMap<String, String> params) {

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("phone", phone);

        HashMap<String, String> requestParams = new HashMap<String, String>(params);
        requestParams.putAll(options);

        return master.send("blacklist/index", requestParams);

    }

    /**
     * Checking if phone is blacklisted
     *
     * @param phone phone number to check
     * @return JSON response with an "exists" (boolean) field
     */
    public String check(String phone) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("phone", phone);

        return master.send("blacklist/check", params);

    }

    /**
     * Deleting phone from the blacklist
     *
     * @param phone phone number to delete
     * @return JSON response with a "success" (boolean) field
     */
    public String delete(String phone) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("phone", phone);

        return master.send("blacklist/delete", params);

    }
}
