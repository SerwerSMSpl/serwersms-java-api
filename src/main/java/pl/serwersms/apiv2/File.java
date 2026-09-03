package pl.serwersms.apiv2;

import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * @author SerwerSMS
 */
public class File {

    private SerwerSMS master = null;

    public File(SerwerSMS object) throws Exception {

        master = object;

    }

    /**
     * Add new file
     *
     * @param type   file type (mms|voice)
     * @param params request parameters; supported keys: "url" (URL address to
     *               the file)
     * @return JSON response with "success" (boolean) and "id" (String) fields
     */
    public String add(String type, HashMap<String, String> params) {

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("type", type);

        HashMap<String, String> requestParams = new HashMap<String, String>(params);
        requestParams.putAll(options);

        return master.send("files/add", requestParams);

    }

    /**
     * List of files
     *
     * @param type file type (mms|voice)
     * @return JSON response with an "items" array, where each item has "id",
     *         "name", "size", "type" (mms|voice) and "date"
     */
    public String index(String type) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("type", type);

        return master.send("files/index", params);

    }

    /**
     * View file
     *
     * @param id   file identifier
     * @param type file type (mms|voice)
     * @return JSON response with "id", "name", "size", "type" (mms|voice) and
     *         "date" fields
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
     * @param id   file identifier
     * @param type file type (mms|voice)
     * @return JSON response with a "success" (boolean) field
     */
    public String delete(String id, String type) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("type", type);

        return master.send("files/delete", params);

    }
}
