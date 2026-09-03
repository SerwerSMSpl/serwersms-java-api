package pl.serwersms.apiv2;

import java.util.HashMap;

/**
 * @author SerwerSMS
 */
public class Fault {

    private SerwerSMS master = null;

    public Fault(SerwerSMS object) throws Exception {

        master = object;

    }

    /**
     * Preview an error description.
     *
     * @param code error code
     * @return JSON response with "code" (int), "type" (String) and "message"
     *         (String) fields
     */
    public String view(int code) {

        return master.send("error/" + code, new HashMap<String, String>());

    }
}
