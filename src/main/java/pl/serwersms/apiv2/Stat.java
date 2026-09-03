package pl.serwersms.apiv2;

import java.util.HashMap;

/**
 * @author SerwerSMS
 */
public class Stat {

    private SerwerSMS master = null;

    public Stat(SerwerSMS object) throws Exception {

        master = object;

    }

    /**
     * Statistics an sending
     *
     * @param params request parameters; supported keys: "type"
     *               (eco|full|voice|mms), "begin" (start date), "end" (end date)
     * @return JSON response with an "items" array, where each item has "id",
     *         "name", "delivered", "pending", "undelivered", "unsent", "begin",
     *         "end", "text" and "type" (eco|full|voice|mms)
     */
    public String index(HashMap<String, String> params) {

        return master.send("stats/index", params);

    }
}
