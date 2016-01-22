package SerwerSMS;

import java.util.HashMap;

/**
 *
 * @author SerwerSMS
 * @version: 1.0
 * @date 2016-01
 */
public class Premium {

    private SerwerSMS master = null;

    public Premium(SerwerSMS object) throws Exception {

        master = object;

    }

    /**
     * List of received SMS Premium
     *
     * @return array
     * @option array "items"
     * @option int "id"
     * @option String "to_number" Premium number
     * @option String "from_number" Sender phone number
     * @option String "date"
     * @option int "limit" Limitation the number of responses
     * @option String "text" Message
     */
    public String index() {

        return master.send("premium/index", new HashMap<String, String>());

    }

    /**
     * Sending replies for received SMS Premium
     *
     * @param String phone
     * @param String text Message
     * @param String gate Premium number
     * @param int id ID received SMS Premium
     * @return array
     * @option booleanean "success"
     */
    public String send(String phone, String text, String gate, String id) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("phone", phone);
        params.put("text", text);
        params.put("gate", gate);
        params.put("id", id);

        return master.send("premium/send", params);

    }

    /**
     * View quiz results
     *
     * @param String id
     * @return array
     * @option int "id"
     * @option String "name"
     * @option array "items"
     * @option int "id"
     * @option int "count" Number of response
     */
    public String quiz(String id) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);

        return master.send("quiz/view", params);

    }
}