package pl.serwersms.apiv2;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONObject;
import org.json.JSONArray;


/**
 * @author SerwerSMS
 */
public class Contact {

    private SerwerSMS master = null;

    public Contact(SerwerSMS object) throws Exception {

        master = object;

    }

    /**
     * Add new contact
     *
     * @param group_id group identifier
     * @param phone    phone number
     * @param params   request parameters; supported keys: "email", "first_name",
     *                 "last_name", "company", "tax_id", "address", "city",
     *                 "description"
     * @return JSON response with "success" (boolean) and "id" (int) fields
     */
    public String add(String group_id, String phone, HashMap<String, String> params) {

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("group_id", group_id);
        options.put("phone", phone);

        HashMap<String, String> requestParams = new HashMap<String, String>(params);
        requestParams.putAll(options);

        return master.send("contacts/add", requestParams);

    }

    /**
     * List of contacts
     *
     * @param group_id group identifier filter (may be empty)
     * @param search   search phrase (may be empty)
     * @param params   request parameters; supported keys: "page", "limit",
     *                 "sort" (first_name|last_name|phone|company|tax_id|email|
     *                 address|city|description), "order" (asc|desc)
     * @return JSON response with a "paging" object ("page", "count") and an
     *         "items" array, where each item has "id", "phone", "email",
     *         "company", "first_name", "last_name", "tax_id", "address", "city",
     *         "description", "blacklist", "group_id" and "group_name"
     */
    public String index(String group_id, String search, HashMap<String, String> params) {

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("group_id", group_id);
        options.put("search", search);

        HashMap<String, String> requestParams = new HashMap<String, String>(params);
        requestParams.putAll(options);

        return master.send("contacts/index", requestParams);

    }

    /**
     * View single contact
     *
     * @param id contact identifier
     * @return JSON response with "id", "phone", "email", "company",
     *         "first_name", "last_name", "tax_id", "address", "city",
     *         "description" and "blacklist" fields
     */
    public String view(String id) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);

        return master.send("contacts/view", params);

    }
    
    
    /**
     * Editing a contact
     *
     * @param id       contact identifier
     * @param group_id group identifier
     * @param phone    phone number
     * @param params   request parameters; supported keys: "email", "first_name",
     *                 "last_name", "company", "tax_id", "address", "city",
     *                 "description"
     * @return JSON response with "success" (boolean) and "id" (int) fields
     */
    public String edit(String id, String group_id, String phone, HashMap<String, String> params) {

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("id", id);
        options.put("group_id", group_id);
        options.put("phone", phone);

        HashMap<String, String> requestParams = new HashMap<String, String>(params);
        requestParams.putAll(options);

        return master.send("contacts/edit", requestParams);

    }

    /**
     * Deleting a phone from contacts
     *
     * @param id contact identifier
     * @return JSON response with a "success" (boolean) field
     */
    public String delete(String id) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);

        return master.send("contacts/delete", params);

    }
    
    /**
     * Import contact list
     *
     * @param group_name name of the group to import into
     * @param contact    list of contacts; each entry supports keys: "phone",
     *                   "email", "first_name", "last_name", "company"
     * @return JSON response with "success" (boolean), "id" (int), "correct"
     *         (number of contacts imported correctly) and "failed" (number of
     *         errors) fields
     * @throws Exception if the request fails
     */
    public String imports(String group_name, ArrayList<HashMap<String, String>> contact) throws Exception {
    
       
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("group_name", group_name);
    
        JSONArray array = new JSONArray();

        for (HashMap<String, String> temp : contact) {
            JSONObject json = new JSONObject();
            json.put("phone", temp.get("phone"));
            json.put("email", temp.get("email"));
            json.put("first_name", temp.get("first_name"));
            json.put("last_name", temp.get("last_name"));
            json.put("company", temp.get("company"));
            array.put(json);
        }

        params.put("contact", array.toString());

        return master.send("contacts/import", params);

    }
}
