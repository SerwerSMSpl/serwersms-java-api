package SerwerSMS;

import java.util.HashMap;

/**
 *
 * @author SerwerSMS
 * @version: 1.0
 * @date 2016-01
 */
public class Broadcaster {

    private SerwerSMS master = null;

    public Broadcaster(SerwerSMS object) throws Exception {

        master = object;

    }

    /**
     * Creating new Sender name
     *
     * @param String name
     * @return array
     * @option booleanean "success"
     */
    public String add(String name) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name", name);

        return master.send("senders/add", params);

    }

    /**
     * Senders list
     *
     * @param array params
     * @option bool "predefined"
     * @option String "sort" Values: name
     * @option String "order" Values: asc|desc
     * @return array
     * @option array "items"
     * @option String "name"
     * @option String "agreement" delivered|required|not_required
     * @option String "status"
     * pending_authorization|authorized|rejected|deactivated
     */
    public String index(HashMap<String, String> params) {

        return master.send("senders/index", params);

    }
}