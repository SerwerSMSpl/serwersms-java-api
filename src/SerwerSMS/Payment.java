package SerwerSMS;

import java.util.HashMap;

/**
 *
 * @author SerwerSMS
 * @version: 1.0
 * @date 2016-01
 */
public class Payment {

    private SerwerSMS master = null;

    public Payment(SerwerSMS object) throws Exception {

        master = object;

    }

    /**
     * List of payments
     *
     * @return array
     * @option array "items"
     * @option int "id"
     * @option String "number"
     * @option String "state" paid|not_paid
     * @option float "paid"
     * @option float "total"
     * @option String "payment_to"
     * @option String "url"
     */
    public String index() {

        return master.send("payments/index", new HashMap<String, String>());

    }

    /**
     * View single payment
     *
     * @param String id
     * @return array
     * @option int "id"
     * @option String "number"
     * @option String "state" paid|not_paid
     * @option float "paid"
     * @option float "total"
     * @option String "payment_to"
     * @option String "url"
     */
    public String view(String id) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);

        return master.send("payments/view", params);

    }

    /**
     * Download invoice as PDF
     *
     * @param String id
     * @return byte[]
     */
    public byte[] invoice(String id) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);

        return master.sendByte("payments/invoice", params);

    }
}