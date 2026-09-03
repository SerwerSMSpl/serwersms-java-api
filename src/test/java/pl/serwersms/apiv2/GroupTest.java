package pl.serwersms.apiv2;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

@Order(450)
class GroupTest extends ApiTestBase {

    @Test
    @Order(1)
    void testAdd() {
        String result = api.group.add("test");
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("success"), "Expected 'success', got: " + result);
        assertTrue(json.optBoolean("success"), "Expected success:true, got: " + result);
    }

    @Test
    @Order(2)
    void testIndex() {
        HashMap<String, String> params = new HashMap<>();
        HashMap<String, String> paramsBefore = new HashMap<>(params);

        String result = api.group.index("", params);
        assertEquals(paramsBefore, params, "group.index must not mutate the caller's params map");

        assumeFalse(isEmptyResponse(result), "testIndex requires at least one group - none available on this account");
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("items"), "Expected 'items', got: " + result);
    }

    @Test
    @Order(3)
    void testView() {
        String listResult = api.group.index("", new HashMap<>());
        assumeFalse(isEmptyResponse(listResult), "testView requires at least one group - none available on this account");
        assertNoAuthError(listResult);

        JSONObject listJson = new JSONObject(listResult);
        JSONArray items = listJson.optJSONArray("items");
        assumeFalse(items == null || items.isEmpty(), "testView requires an existing group in items - none available on this account");

        String id = items.getJSONObject(0).optString("id");
        String result = api.group.view(id);
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("id"), "Expected 'id', got: " + result);
    }

    @Test
    @Order(4)
    void testEdit() {
        String listResult = api.group.index("", new HashMap<>());
        assumeFalse(isEmptyResponse(listResult), "testEdit requires at least one group - none available on this account");
        assertNoAuthError(listResult);

        JSONObject listJson = new JSONObject(listResult);
        JSONArray items = listJson.optJSONArray("items");
        assumeFalse(items == null || items.isEmpty(), "testEdit requires an existing group in items - none available on this account");

        String id = items.getJSONObject(0).optString("id");
        String result = api.group.edit(id, "New name");
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("success"), "Expected 'success', got: " + result);
        assertTrue(json.optBoolean("success"), "Expected success:true, got: " + result);
    }

    @Test
    @Order(5)
    void testDelete() {
        String listResult = api.group.index("", new HashMap<>());
        assumeFalse(isEmptyResponse(listResult), "testDelete requires an existing group - none available on this account");
        assertNoAuthError(listResult);

        JSONObject listJson = new JSONObject(listResult);
        JSONArray items = listJson.optJSONArray("items");
        assumeFalse(items == null || items.isEmpty(), "testDelete requires an existing group in items - none available on this account");

        String id = items.getJSONObject(0).optString("id");
        String result = api.group.delete(id);
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("success"), "Expected 'success', got: " + result);
        assertTrue(json.optBoolean("success"), "Expected success:true, got: " + result);
    }

    @Test
    @Order(6)
    void testCheck() {
        String result = api.group.check("600700800");
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("items"), "Expected 'items', got: " + result);
    }
}
