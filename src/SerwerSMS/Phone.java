package SerwerSMS;

import java.util.HashMap;

/**
 *
 * @author SerwerSMS
 * @version: 1.1
 * @date 2016-01
 */
public class Phone {

    private SerwerSMS master = null;

    public Phone(SerwerSMS object) throws Exception {

        master = object;

    }

    /**
     * Checking phone in to HLR
     *
     * @param String phone
     * @param String id Query ID returned if the processing takes longer than 60
     * seconds
     * @return array
     * @option String "phone"
     * @option String "status"
     * @option int "imsi"
     * @option String "network"
     * @option boolean "ported"
     * @option String "network_ported"
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
     * @param String phone
     * @return array
     * @option boolean "correct"
     */
    public String test(String phone) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("phone", phone);

        return master.send("phones/test", params);

    }
}