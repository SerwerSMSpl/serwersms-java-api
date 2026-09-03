package pl.serwersms.apiv2;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * @author SerwerSMS
 */
public class Group {

    private SerwerSMS master = null;

    public Group(SerwerSMS object) throws Exception {

        master = object;

    }

    /**
     * Add new group
     *
     * @param name group name
     * @return JSON response with "success" (boolean) and "id" (int) fields
     */
    public String add(String name) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name", name);

        return master.send("groups/add", params);

    }

    /**
     * List of group
     *
     * @param search group name filter (may be empty)
     * @param params request parameters; supported keys: "page", "limit",
     *               "sort" (values: name), "order" (values: asc|desc)
     * @return JSON response with a "paging" object ("page", "count") and an
     *         "items" array, where each item has "id", "name" and "count"
     *         (number of contacts in the group)
     */
    public String index(String search, HashMap<String, String> params) {

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("search", search);

        HashMap<String, String> requestParams = new HashMap<String, String>(params);
        requestParams.putAll(options);

        return master.send("groups/index", requestParams);

    }

    /**
     * View single group
     *
     * @param id group identifier
     * @return JSON response with "id", "name" and "count" (number of contacts
     *         in the group) fields
     */
    public String view(String id) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);

        return master.send("groups/view", params);

    }

    /**
     * Editing a group
     *
     * @param id   group identifier
     * @param name new group name
     * @return JSON response with "success" (boolean) and "id" (int) fields
     */
    public String edit(String id, String name) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("name", name);

        return master.send("groups/edit", params);

    }

    /**
     * Deleting a group
     *
     * @param id group identifier
     * @return JSON response with a "success" (boolean) field
     */
    public String delete(String id) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);

        return master.send("groups/delete", params);

    }

    /**
     * Viewing a groups containing phone
     *
     * @param phone phone number
     * @return JSON response with "id", "group_id" and "group_name" fields
     */
    public String check(String phone) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("phone", phone);

        return master.send("groups/check", params);

    }
}
