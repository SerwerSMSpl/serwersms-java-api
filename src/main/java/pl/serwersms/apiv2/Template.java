package pl.serwersms.apiv2;

import java.util.HashMap;

/**
 * @author SerwerSMS
 */
public class Template {

    private SerwerSMS master = null;

    public Template(SerwerSMS object) throws Exception {

        master = object;

    }

    /**
     * List of templates
     *
     * @param params request parameters; supported keys: "sort" (values: name),
     *               "order" (values: asc|desc)
     * @return JSON response with an "items" array, where each item has "id",
     *         "name" and "text"
     */
    public String index(HashMap<String, String> params) {

        return master.send("templates/index", params);

    }

    /**
     * Adding new template
     *
     * @param name template name
     * @param text template text
     * @return JSON response with "success" (boolean) and "id" (int) fields
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
     * @param id   template identifier
     * @param name new template name
     * @param text new template text
     * @return JSON response with "success" (boolean) and "id" (int) fields
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
     * @param id template identifier
     * @return JSON response with a "success" (boolean) field
     */
    public String delete(String id) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);

        return master.send("templates/delete", params);

    }
}
