package pl.serwersms.apiv2;

import org.junit.jupiter.api.Test;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import org.junit.jupiter.api.Order;

@Order(650)
class MessageTest extends ApiTestBase {

    @Test
    @Order(1)
    void testSendSms() {
        HashMap<String, String> options = new HashMap<>();
        options.put("test", "true");
        options.put("details", "true");

        HashMap<String, String> optionsBefore = new HashMap<>(options);

        String result = api.message.sendSms("500600700", "Test message", "INFORMACJA", options);
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.optBoolean("success"), "Expected success:true, got: " + result);
        assertTrue(json.has("items"), "Expected 'items', got: " + result);

        assertEquals(optionsBefore, options, "sendSms must not mutate the caller's params map");
    }

    @Test
    @Order(2)
    void testSendPersonalized() {
        ArrayList<HashMap<String, String>> messages = new ArrayList<>();

        HashMap<String, String> m1 = new HashMap<>();
        m1.put("phone", "500600700");
        m1.put("text", "First message");
        messages.add(m1);

        HashMap<String, String> m2 = new HashMap<>();
        m2.put("phone", "600700800");
        m2.put("text", "Second message");
        messages.add(m2);

        HashMap<String, String> options = new HashMap<>();
        options.put("test", "true");
        options.put("details", "true");

        HashMap<String, String> optionsBefore = new HashMap<>(options);

        String result = api.message.sendPersonalized(messages, "INFORMACJA", options);
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.optBoolean("success"), "Expected success:true, got: " + result);
        assertTrue(json.has("items"), "Expected 'items', got: " + result);

        assertEquals(optionsBefore, options, "sendPersonalized must not mutate the caller's params map");
    }

    @Test
    @Order(3)
    void testSendVoice() {
        HashMap<String, String> options = new HashMap<>();
        options.put("text", "Test message");
        options.put("test", "true");
        options.put("details", "true");

        HashMap<String, String> optionsBefore = new HashMap<>(options);

        String result = api.message.sendVoice("500600700", options);
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.optBoolean("success"), "Expected success:true, got: " + result);
        assertTrue(json.has("items"), "Expected 'items', got: " + result);

        assertEquals(optionsBefore, options, "sendVoice must not mutate the caller's params map");
    }

    @Test
    @Order(4)
    void testSendMms() {
        String listResult = api.file.index("mms");
        assertNoAuthError(listResult);

        JSONObject listJson = new JSONObject(listResult);
        JSONArray items = listJson.optJSONArray("items");

        String fileId;
        String addedFileId = null;

        if (items != null && !items.isEmpty()) {
            // an MMS file already exists - use the first one
            fileId = items.getJSONObject(0).optString("id");
        } else {
            // no files available - add a new one and use the returned id
            HashMap<String, String> fileParams = new HashMap<>();
            fileParams.put("url", "https://static.serwersms.pl/files/demo.jpg");
            fileParams.put("name", "Demo jpg");

            String addResult = api.file.add("mms", fileParams);

            assertNoAuthError(addResult);

            JSONObject addJson = new JSONObject(addResult);
            assertTrue(addJson.optBoolean("success"), "Expected success:true when adding file, got: " + addResult);

            fileId = addJson.optString("id");
            addedFileId = fileId;
        }

        try {
            HashMap<String, String> options = new HashMap<>();
            options.put("test", "true");
            options.put("file_id", fileId);
            options.put("details", "true");

            HashMap<String, String> optionsBefore = new HashMap<>(options);

            String result = api.message.sendMms("500600700", "MMS Title", options);
            assertNoAuthError(result);

            JSONObject json = new JSONObject(result);
            assertTrue(json.optBoolean("success"), "Expected success:true, got: " + result);
            assertTrue(json.has("items"), "Expected 'items', got: " + result);

            assertEquals(optionsBefore, options, "sendMms must not mutate the caller's params map");
        } finally {
            if (addedFileId != null) {
                // check whether the file really exists in the list (production account)
                // or the id was only returned without actually adding it (test account)
                String verifyResult = api.file.index("mms");
                JSONObject verifyJson = new JSONObject(verifyResult);
                JSONArray verifyItems = verifyJson.optJSONArray("items");

                boolean fileExists = false;
                if (verifyItems != null) {
                    for (int i = 0; i < verifyItems.length(); i++) {
                        if (addedFileId.equals(verifyItems.getJSONObject(i).optString("id"))) {
                            fileExists = true;
                            break;
                        }
                    }
                }

                if (fileExists) {
                    api.file.delete(addedFileId, "mms");
                }
                // test account - file does not exist in the list, nothing to delete
            }
        }
    }

    @Test
    @Order(5)
    void testReports() {
        String result = api.message.reports(new HashMap<>());
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("items"), "Expected 'items', got: " + result);
    }

    @Test
    @Order(6)
    void testView() {
        String listResult = api.message.reports(new HashMap<>());
        assertNoAuthError(listResult);

        JSONObject listJson = new JSONObject(listResult);
        assertTrue(listJson.has("items"), "Expected 'items', got: " + listResult);

        JSONArray items = listJson.optJSONArray("items");
        assumeFalse(items == null || items.isEmpty(), "testView requires an existing message in reports - none available on this account");

        String id = items.getJSONObject(0).optString("id");
        String result = api.message.view(id, new HashMap<>());
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("id"), "Expected 'id', got: " + result);
    }

    @Test
    @Order(7)
    void testDelete() {
        String listResult = api.message.reports(new HashMap<>());
        assertNoAuthError(listResult);

        JSONObject listJson = new JSONObject(listResult);
        assertTrue(listJson.has("items"), "Expected 'items', got: " + listResult);

        JSONArray items = listJson.optJSONArray("items");
        assumeFalse(items == null || items.isEmpty(), "testDelete requires an existing message in reports - none available on this account");

        String id = items.getJSONObject(0).optString("id");
        String result = api.message.delete(id, "");
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("success"), "Expected 'success', got: " + result);
    }

    @Test
    @Order(8)
    void testReceived() {
        HashMap<String, String> options = new HashMap<>();
        HashMap<String, String> optionsBefore = new HashMap<>(options);

        String result = api.message.received("nd", options);
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("items"), "Expected 'items', got: " + result);

        assertEquals(optionsBefore, options, "received must not mutate the caller's params map");
    }
}
