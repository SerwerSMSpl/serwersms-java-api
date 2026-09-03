package pl.serwersms.apiv2;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

@Order(400)
class TemplateTest extends ApiTestBase {

    @Test
    @Order(1)
    void testIndex() {
        String result = api.template.index(new HashMap<>());
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("items"), "Expected 'items', got: " + result);
    }

    @Test
    @Order(2)
    void testAdd() {
        String result = api.template.add("New template", "Message from template");
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("success"), "Expected 'success', got: " + result);
        assertTrue(json.optBoolean("success"), "Expected success:true, got: " + result);
    }

    @Test
    @Order(3)
    void testEdit() {
        String listResult = api.template.index(new HashMap<>());
        assertNoAuthError(listResult);

        JSONObject listJson = new JSONObject(listResult);
        JSONArray items = listJson.optJSONArray("items");
        assumeFalse(items == null || items.isEmpty(), "testEdit requires an existing SMS template - none available on this account");

        String id = items.getJSONObject(0).optString("id");
        String result = api.template.edit(id, "New template", "Editing message from template");
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("success"), "Expected 'success', got: " + result);
        assertTrue(json.optBoolean("success"), "Expected success:true, got: " + result);
    }

    @Test
    @Order(4)
    void testDelete() {
        String listResult = api.template.index(new HashMap<>());
        assertNoAuthError(listResult);

        JSONObject listJson = new JSONObject(listResult);
        JSONArray items = listJson.optJSONArray("items");
        assumeFalse(items == null || items.isEmpty(),
                "testDelete requires an existing SMS template - none available on this account");

        String id = items.getJSONObject(0).optString("id");
        String result = api.template.delete(id);
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("success"), "Expected 'success', got: " + result);
        assertTrue(json.optBoolean("success"), "Expected success:true, got: " + result);
    }
}
