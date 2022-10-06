package pl.serwersms.apiv2;

import java.util.HashMap;

/**
 *
 * @author SerwerSMS
 * @version: 1.2
 * @date 2022-09
 */
public class Stat {

    private SerwerSMS master = null;

    public Stat(SerwerSMS object) throws Exception {

        master = object;

    }

    /**
     * Statistics an sending
     *
     * @param array params
     * @option String "type" eco|full|voice|mms
     * @option String "begin" Start date
     * @option String "end" End date
     * @return array
     * @option array "items"
     * @option int "id"
     * @option String "name"
     * @option int "delivered"
     * @option int "pending"
     * @option int "undelivered"
     * @option int "unsent"
     * @option String "begin"
     * @option String "end"
     * @option String "text"
     * @option String "type" eco|full|voice|mms
     */
    public String index(HashMap<String, String> params) {

        return master.send("stats/index", params);

    }
}