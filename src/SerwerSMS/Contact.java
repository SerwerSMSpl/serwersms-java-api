package SerwerSMS;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONObject;
import org.json.JSONArray;


/**
 *
 * @author SerwerSMS
 * @version: 1.0
 * @date 2016-01
 */
public class Contact {

    private SerwerSMS master = null;

    public Contact(SerwerSMS object) throws Exception {

        master = object;

    }

    /**
     * Add new contact
     *
     * @param String group_id
     * @param String phone
     * @param array params
     * @option String "email"
     * @option String "first_name"
     * @option String "last_name"
     * @option String "company"
     * @option String "tax_id"
     * @option String "address"
     * @option String "city"
     * @option String "description"
     * @return array
     * @option boolean "success"
     * @option int "id"
     */
    public String add(String group_id, String phone, HashMap<String, String> params) {

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("group_id", group_id);
        options.put("phone", phone);

        params.putAll(options);

        return master.send("contacts/add", params);

    }

    /**
     * List of contacts
     *
     * @param String group_id
     * @param String search
     * @param array params
     * @option int "page" The number of the displayed page
     * @option int "limit" Limit items are displayed on the single page
     * @option String "sort" Values: first_name|last_name|phone|company|tax_id|email|address|city|description
     * @option String "order" Values: asc|desc
     * @return array
     * @option array "paging"
     * @option int "page" The number of current page
     * @option int "count" The number of all pages
     * @options array "items"
     * @option int "id"
     * @option String "phone"
     * @option String "email"
     * @option String "company"
     * @option String "first_name"
     * @option String "last_name"
     * @option String "tax_id"
     * @option String "address"
     * @option String "city"
     * @option String "description"
     * @option boolean "blacklist"
     * @option int "group_id"
     * @option String "group_name"
     */
    public String index(String group_id, String search, HashMap<String, String> params) {

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("group_id", group_id);
        options.put("search", search);

        params.putAll(options);

        return master.send("contacts/index", params);

    }

    /**
     * View single contact
     *
     * @param int id
     * @return array
     * @option int "id"
     * @option String "phone"
     * @option String "email"
     * @option String "company"
     * @option String "first_name"
     * @option String "last_name"
     * @option String "tax_id"
     * @option String "address"
     * @option String "city"
     * @option String "description"
     * @option boolean "blacklist"
     */
    public String view(String id) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);

        return master.send("contacts/view", params);

    }
    
    
    /**
    * Editing a contact
    * 
    * @param int id
    * @param String group_id
    * @param String phone
    * @param array params
    *      @option String "email"
    *      @option String "first_name"
    *      @option String "last_name"
    *      @option String "company"
    *      @option String "tax_id"
    *      @option String "address"
    *      @option String "city"
    *      @option String "description"
    * @return array
    *      @option boolean "success"
    *      @option int "id"
    */
    public String edit(String id, String group_id, String phone, HashMap<String, String> params) {

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("id", id);
        options.put("group_id", group_id);
        options.put("phone", phone);
        
        params.putAll(options);

        return master.send("contacts/edit", params);

    }
    
    
    /**
    * Deleting a phone from contacts
    * 
    * @param int id
    * @return array
    *      @option boolean "success"
    */
    public String delete(String id) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);

        return master.send("contacts/delete", params);

    }
    
    /**
    * Import contact list
    * 
    * @param String group_name
    * @param array contact
    *      @option String "phone"
    *      @option String "email"
    *      @option String "first_name"
    *      @option String "last_name"
    *      @option String "company"
    * @return array
    *      @option boolean "success"
    *      @option int "id"
    *      @option int "correct" Number of contacts imported correctly
    *      @option int "failed" Number of errors
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