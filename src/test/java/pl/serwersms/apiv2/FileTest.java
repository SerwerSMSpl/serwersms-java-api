package pl.serwersms.apiv2;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

@Order(200)
class FileTest extends ApiTestBase {

    @Test
    @Order(1)
    void testAddMms() {
        HashMap<String, String> params = new HashMap<>();
        params.put("url", "https://static.serwersms.pl/files/demo.jpg");
        params.put("name", "Demo png");

        HashMap<String, String> paramsBefore = new HashMap<>(params);

        String result = api.file.add("mms", params);
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("success"), "Expected 'success', got: " + result);
        assertTrue(json.optBoolean("success"), "Expected success:true, got: " + result);

        assertEquals(paramsBefore, params, "file.add must not mutate the caller's params map");
    }

    @Test
    @Order(2)
    void testIndexMms() {
        String result = api.file.index("mms");
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("items"), "Expected 'items', got: " + result);

        JSONArray items = json.optJSONArray("items");
        assumeFalse(items == null || items.isEmpty(), "No MMS files available.");
    }

    @Test
    @Order(3)
    void testViewMms() {
        String listResult = api.file.index("mms");
        assertNoAuthError(listResult);

        JSONObject listJson = new JSONObject(listResult);
        assertTrue(listJson.has("items"), "Expected 'items', got: " + listResult);

        JSONArray items = listJson.optJSONArray("items");
        assumeFalse(items == null || items.isEmpty(), "No MMS files available.");

        String id = items.getJSONObject(0).optString("id");
        String result = api.file.view(id, "mms");
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("id"), "Expected 'id', got: " + result);
    }

    @Test
    @Order(4)
    void testDeleteMms() {
        String listResult = api.file.index("mms");
        assertNoAuthError(listResult);

        JSONObject listJson = new JSONObject(listResult);
        assertTrue(listJson.has("items"), "Expected 'items', got: " + listResult);

        JSONArray items = listJson.optJSONArray("items");
        assumeFalse(items == null || items.isEmpty(), "No MMS files available.");

        String id = items.getJSONObject(0).optString("id");
        String result = api.file.delete(id, "mms");
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("success"), "Expected 'success', got: " + result);
        assertTrue(json.optBoolean("success"), "Expected success:true, got: " + result);
    }

    @Test
    @Order(5)
    void testAddVoice() {
        HashMap<String, String> params = new HashMap<>();
        params.put("url", "https://static.serwersms.pl/files/demo.wav");
        params.put("name", "Demo wav");

        String result = api.file.add("voice", params);
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("success"), "Expected 'success', got: " + result);
        assertTrue(json.optBoolean("success"), "Expected success:true, got: " + result);
    }

    @Test
    @Order(6)
    void testIndexVoice() {
        String result = api.file.index("voice");
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("items"), "Expected 'items', got: " + result);

        JSONArray items = json.optJSONArray("items");
        assumeFalse(items == null || items.isEmpty(), "No Voice files available.");
    }

    @Test
    @Order(7)
    void testViewVoice() {
        String listResult = api.file.index("voice");
        assertNoAuthError(listResult);

        JSONObject listJson = new JSONObject(listResult);
        assertTrue(listJson.has("items"), "Expected 'items', got: " + listResult);

        JSONArray items = listJson.optJSONArray("items");
        assumeFalse(items == null || items.isEmpty(), "No Voice files available.");

        String id = items.getJSONObject(0).optString("id");
        String result = api.file.view(id, "voice");
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("id"), "Expected 'id', got: " + result);
    }

    @Test
    @Order(8)
    void testDeleteVoice() {
        String listResult = api.file.index("voice");
        assertNoAuthError(listResult);

        JSONObject listJson = new JSONObject(listResult);
        assertTrue(listJson.has("items"), "Expected 'items', got: " + listResult);

        JSONArray items = listJson.optJSONArray("items");
        assumeFalse(items == null || items.isEmpty(), "No Voice files available.");

        String id = items.getJSONObject(0).optString("id");
        String result = api.file.delete(id, "voice");
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("success"), "Expected 'success', got: " + result);
        assertTrue(json.optBoolean("success"), "Expected success:true, got: " + result);
    }
}
