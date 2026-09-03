package pl.serwersms.apiv2;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

@Order(550)
class ContactTest extends ApiTestBase {

    @Test
    @Order(1)
    void testAdd() {
        HashMap<String, String> params = new HashMap<>();
        params.put("email", "test@mail.com");
        params.put("first_name", "John");
        params.put("last_name", "Doe");
        params.put("company", "Hello Word!");

        HashMap<String, String> paramsBefore = new HashMap<>(params);

        String result = api.contact.add("123", "500600800", params);
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("success"), "Expected 'success', got: " + result);
        assertTrue(json.optBoolean("success"), "Expected success:true, got: " + result);

        assertEquals(paramsBefore, params, "contact.add must not mutate the caller's params map");
    }

    @Test
    @Order(2)
    void testIndex() {
        HashMap<String, String> params = new HashMap<>();
        HashMap<String, String> paramsBefore = new HashMap<>(params);

        String result = api.contact.index("none", "", params);
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("items"), "Expected 'items', got: " + result);

        assertEquals(paramsBefore, params, "contact.index must not mutate the caller's params map");
    }

    @Test
    @Order(3)
    void testView() {
        String listResult = api.contact.index("none", "", new HashMap<>());
        assertNoAuthError(listResult);

        JSONObject listJson = new JSONObject(listResult);
        JSONArray items = listJson.optJSONArray("items");
        assumeFalse(items == null || items.isEmpty(), "testView requires an existing contact - none available on this account");

        String id = items.getJSONObject(0).optString("id");
        String result = api.contact.view(id);
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("id"), "Expected 'id', got: " + result);
    }

    @Test
    @Order(4)
    void testEdit() {
        String listResult = api.contact.index("none", "", new HashMap<>());
        assertNoAuthError(listResult);

        JSONObject listJson = new JSONObject(listResult);
        JSONArray items = listJson.optJSONArray("items");
        assumeFalse(items == null || items.isEmpty(), "testEdit requires an existing contact - none available on this account");

        HashMap<String, String> params = new HashMap<>();
        params.put("email", "test@mail.com");
        params.put("first_name", "John");
        params.put("last_name", "Doe");
        params.put("company", "Hello Word!");

        String id = items.getJSONObject(0).optString("id");
        HashMap<String, String> paramsBefore = new HashMap<>(params);
        String result = api.contact.edit(id, "123", "500600700", params);
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("success"), "Expected 'success', got: " + result);
        assertTrue(json.optBoolean("success"), "Expected success:true, got: " + result);

        assertEquals(paramsBefore, params, "contact.edit must not mutate the caller's params map");
    }

    @Test
    @Order(5)
    void testDelete() {

        String listResult = api.contact.index("none", "", new HashMap<>());
        assumeFalse(isEmptyResponse(listResult), "testDelete requires an existing contact - none available on this account");
        assertNoAuthError(listResult);

        JSONObject listJson = new JSONObject(listResult);
        JSONArray items = listJson.optJSONArray("items");
        assumeFalse(items == null || items.isEmpty(), "testDelete requires an existing contact - none available on this account");

        String id = items.getJSONObject(0).optString("id");
        String result = api.contact.delete(id);
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("success"), "Expected 'success', got: " + result);
        assertTrue(json.optBoolean("success"), "Expected success:true, got: " + result);
    }

    @Test
    @Order(6)
    void testImport() throws Exception {
        ArrayList<HashMap<String, String>> contact = new ArrayList<>();

        HashMap<String, String> c1 = new HashMap<>();
        c1.put("phone", "500600700");
        c1.put("email", "test@mail.com");
        c1.put("first_name", "John");
        c1.put("last_name", "Doe");
        c1.put("company", "Hello Word!");
        contact.add(c1);

        HashMap<String, String> c2 = new HashMap<>();
        c2.put("phone", "500600800");
        c2.put("email", "test@mail.com");
        c2.put("first_name", "John");
        c2.put("last_name", "Doe");
        c2.put("company", "Hello Word!");
        contact.add(c2);

        String importResult = api.contact.imports("New group", contact);
        assertNoAuthError(importResult);

        JSONObject importJson = new JSONObject(importResult);
        assertTrue(importJson.has("success"), "Expected 'success', got: " + importResult);
        assertTrue(importJson.has("id"), "Expected 'id', got: " + importResult);

        String groupId = importJson.optString("id");
        assertFalse(groupId.isEmpty(), "Expected non-empty group id, got: " + importResult);

        try {
            String listResult = api.contact.index(groupId, "", new HashMap<>());
            assertNoAuthError(listResult);

            JSONObject listJson = new JSONObject(listResult);
            assertTrue(listJson.has("items"), "Expected 'items', got: " + listResult);

            JSONArray items = listJson.optJSONArray("items");
            assertNotNull(items, "Expected 'items' array, got: " + listResult);
            assertEquals(2, items.length(), "Expected 2 imported contacts, got: " + listResult);
        } finally {
            api.group.delete(groupId);
        }
    }
}
