package pl.serwersms.apiv2;

import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;

/**
 *
 * @author SerwerSMS
 * @version: 1.2
 * @date 2022-09
 */
public class File {

    private SerwerSMS master = null;

    public File(SerwerSMS object) throws Exception {

        master = object;

    }

    /**
     * Add new file
     *
     * @param String type - mms|voice
     * @param array params
     * @option String "url" URL address to file
     * @return array
     * @option boolean "success"
     * @option String "id"
     */
    public String add(String type, HashMap<String, String> params) {

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("type", type);

        params.putAll(options);

        return master.send("files/add", params);

    }

    /**
     * List of files
     *
     * @param String type - mms|voice
     * @return array
     * @option array "items"
     * @option String "id"
     * @option String "name"
     * @option int "size"
     * @option String "type" - mms|voice
     * @option String "date"
     */
    public String index(String type) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("type", type);

        return master.send("files/index", params);

    }

    /**
     * View file
     *
     * @param String id
     * @param String type - mms|voice
     * @return array
     * @option String "id"
     * @option String "name"
     * @option int "size"
     * @option String "type" - mms|voice
     * @option String "date"
     */
    public String view(String id, String type) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("type", type);

        return master.send("files/view", params);

    }

    /**
     * Deleting a file
     *
     * @param String id
     * @param String type - mms|voice
     * @return array
     * @option boolean "success"
     */
    public String delete(String id, String type) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("type", type);

        return master.send("files/delete", params);

    }
}