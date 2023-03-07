package SerwerSMS;

import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;

/**
 *
 * @author SerwerSMS
 * @version: 1.1
 * @date 2016-01
 */
public class Blacklist {

    private SerwerSMS master = null;

    public Blacklist(SerwerSMS object) throws Exception {

        master = object;

    }

    /**
     * Add phone to the blacklist
     *
     * @param String phone
     * @return array
     * @option boolean "success"
     * @option int "id"
     */
    public String add(String phone) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("phone", phone);

        return master.send("blacklist/add", params);

    }

    /**
     * List of blacklist phones
     *
     * @param String phone
     * @param array params
     * @option int "page" The number of the displayed page
     * @option int "limit" Limit items are displayed on the single page
     * @return array
     * @option array "paging"
     * @option int "page" The number of current page
     * @option int "count" The number of all pages
     * @option array "items"
     * @option String "phone"
     * @option String "added" Date of adding phone
     */
    public String index(String phone, HashMap<String, String> params) {

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("phone", phone);

        params.putAll(options);

        return master.send("blacklist/index", params);

    }

    /**
     * Checking if phone is blacklisted
     *
     * @param String phone
     * @return array
     * @option boolean "exists"
     */
    public String check(String phone) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("phone", phone);

        return master.send("blacklist/check", params);

    }

    /**
     * Deleting phone from the blacklist
     *
     * @param String phone
     * @return array
     * @option boolean "success"
     */
    public String delete(String phone) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("phone", phone);

        return master.send("blacklist/delete", params);

    }
}