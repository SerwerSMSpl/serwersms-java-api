package pl.serwersms.apiv2;

import java.util.HashMap;

/**
 * @author SerwerSMS
 */
public class Broadcaster {

    private SerwerSMS master = null;

    public Broadcaster(SerwerSMS object) throws Exception {

        master = object;

    }

    /**
     * Creating new Sender name
     *
     * @param name sender name to create
     * @return JSON response with a "success" (boolean) field
     */
    public String add(String name) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name", name);

        return master.send("senders/add", params);

    }

    /**
     * Senders list
     *
     * @param params request parameters; supported keys: "predefined" (boolean),
     *               "sort" (values: name), "order" (values: asc|desc)
     * @return JSON response with an "items" array, where each item has "name",
     *         "agreement" (delivered|required|not_required) and "status"
     *         (pending_authorization|authorized|rejected|deactivated)
     */
    public String index(HashMap<String, String> params) {

        return master.send("senders/index", params);

    }
}
