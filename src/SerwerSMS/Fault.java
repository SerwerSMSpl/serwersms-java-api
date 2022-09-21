package pl.serwersms.apiv2;

import java.util.HashMap;

/**
 *
 * @author SerwerSMS
 * @version: 1.2
 * @date 2022-09
 */
public class Fault {

    private SerwerSMS master = null;

    public Fault(SerwerSMS object) throws Exception {

        master = object;

    }

    /**
     * Preview error
     *
     * @param int code
     * @return array
     * @option int "code"
     * @option String "type"
     * @option String "message"
     */
    public String view(int code) {

        return master.send("error/" + code, new HashMap<String, String>());

    }
}