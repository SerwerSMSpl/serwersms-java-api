package pl.serwersms.apiv2;

import java.util.HashMap;

/**
 * @author SerwerSMS
 */
public class Payment {

    private SerwerSMS master = null;

    public Payment(SerwerSMS object) throws Exception {

        master = object;

    }

    /**
     * List of payments
     *
     * @return JSON response with an "items" array, where each item has "id",
     *         "number", "state" (paid|not_paid), "paid", "total", "payment_to"
     *         and "url"
     */
    public String index() {

        return master.send("payments/index", new HashMap<String, String>());

    }

    /**
     * View single payment
     *
     * @param id payment identifier
     * @return JSON response with "id", "number", "state" (paid|not_paid),
     *         "paid", "total", "payment_to" and "url" fields
     */
    public String view(String id) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);

        return master.send("payments/view", params);

    }

    /**
     * Download invoice as PDF
     *
     * @param id payment identifier
     * @return the invoice PDF as a byte array
     */
    public byte[] invoice(String id) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);

        return master.sendByte("payments/invoice", params);

    }
}
