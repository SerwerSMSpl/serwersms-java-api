package pl.serwersms.apiv2;

import java.util.HashMap;

/**
 * @author SerwerSMS
 */
public class Phone {

    private SerwerSMS master = null;

    public Phone(SerwerSMS object) throws Exception {

        master = object;

    }

    /**
     * Checking phone in to HLR
     *
     * @param phone phone number to check
     * @param id    Query ID returned if the processing takes longer than 60
     *              seconds
     * @return JSON response with "phone", "status", "imsi", "network", "ported"
     *         and "network_ported" fields
     */
    public String check(String phone, String id) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("phone", phone);
        params.put("id", id);

        return master.send("phones/check", params);

    }

    /**
     * Validating phone number
     *
     * @param phone phone number to validate
     * @return JSON response with a "correct" (boolean) field
     */
    public String test(String phone) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("phone", phone);

        return master.send("phones/test", params);

    }
}
