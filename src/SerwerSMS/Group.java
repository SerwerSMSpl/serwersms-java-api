package SerwerSMS;

import java.util.HashMap;
import java.util.ArrayList;

/**
 *
 * @author SerwerSMS
 * @version: 1.1
 * @date 2016-01
 */
public class Group {

    private SerwerSMS master = null;

    public Group(SerwerSMS object) throws Exception {

        master = object;

    }

    /**
     * Add new group
     *
     * @param String name
     * @return array
     * @option booleanean "success"
     * @option int "id"
     */
    public String add(String name) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name", name);

        return master.send("groups/add", params);

    }

    /**
     * List of group
     *
     * @param String search Group name
     * @param array params
     * @option int "page" The number of the displayed page
     * @option int "limit" Limit items are displayed on the single page
     * @option String "sort" Values: name
     * @option String "order" Values: asc|desc
     * @return array
     * @option array "paging"
     * @option int "page" The number of current page
     * @option int "count" The number of all pages
     * @option array "items"
     * @option int "id"
     * @option String "name"
     * @option int "count" Number of contacts in the group
     */
    public String index(String search, HashMap<String, String> params) {

        HashMap<String, String> options = new HashMap<String, String>();
        params.put("search", search);

        params.putAll(options);

        return master.send("groups/index", params);

    }

    /**
     * View single group
     *
     * @param int id
     * @return array
     * @option int "id"
     * @option String "name"
     * @option int "count" Number of contacts in the group
     */
    public String view(String id) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);

        return master.send("groups/view", params);

    }

    /**
     * Editing a group
     *
     * @param String id
     * @param String name
     * @return array
     * @option booleanean "success"
     * @option int "id"
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
     * @param int id
     * @return array
     * @option booleanean "success"
     */
    public String delete(String id) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);

        return master.send("groups/delete", params);

    }

    /**
     * Viewing a groups containing phone
     *
     * @param String phone
     * @return array
     * @option int "id"
     * @option int "group_id"
     * @option String "group_name"
     */
    public String check(String phone) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("phone", phone);

        return master.send("groups/check", params);

    }
}