package pl.serwersms.apiv2;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

@Order(350)
class SubaccountTest extends ApiTestBase {

    @Test
    @Order(1)
    void testAdd() {
        HashMap<String, String> params = new HashMap<>();
        params.put("phone", "500600700");

        HashMap<String, String> paramsBefore = new HashMap<>(params);

        String result = api.subaccount.add("login", "haslo", "123", params);
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("success"), "Expected 'success', got: " + result);
        assertTrue(json.optBoolean("success"), "Expected success:true, got: " + result);

        assertEquals(paramsBefore, params, "subaccount.add must not mutate the caller's params map");
    }

    @Test
    @Order(2)
    void testIndex() {
        String result = api.subaccount.index();
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("items"), "Expected 'items', got: " + result);
    }

    @Test
    @Order(3)
    void testView() {
        String result = api.subaccount.view("123");
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("username"), "Expected 'username', got: " + result);
    }

    @Test
    @Order(4)
    void testLimit() {
        String result = api.subaccount.limit("123", "eco", "200");
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("success"), "Expected 'success', got: " + result);
    }

    @Test
    @Order(5)
    void testDelete() {
        String result = api.subaccount.delete("123");
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("success"), "Expected 'success', got: " + result);
    }
}
