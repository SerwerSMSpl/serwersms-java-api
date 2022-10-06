package pl.serwersms.apiv2;

import java.util.HashMap;

/**
 *
 * @author SerwerSMS
 * @version: 1.2
 * @date 2022-09
 */
public class Template {

    private SerwerSMS master = null;

    public Template(SerwerSMS object) throws Exception {

        master = object;

    }

    /**
     * List of templates
     *
     * @param array params
     * @option String "sort" Values: name
     * @option String "order" Values: asc|desc
     * @return array
     * @option array "items"
     * @option int "id"
     * @option String "name"
     * @option String "text"
     */
    public String index(HashMap<String, String> params) {

        return master.send("templates/index", params);

    }

    /**
     * Adding new template
     *
     * @param String name
     * @param String text
     * @return array
     * @option array
     * @option booleanean "success"
     * @option int "id"
     */
    public String add(String name, String text) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name", name);
        params.put("text", text);

        return master.send("templates/add", params);

    }

    /**
     * Editing a template
     *
     * @param String id
     * @param String name
     * @param String text
     * @return array
     * @option booleanean "success"
     * @option int "id"
     */
    public String edit(String id, String name, String text) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("name", name);
        params.put("text", text);

        return master.send("templates/edit", params);

    }

    /**
     * Deleting a template
     *
     * @param String id
     * @return array
     * @option booleanean "success"
     */
    public String delete(String id) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);

        return master.send("templates/delete", params);

    }
}