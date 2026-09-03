package pl.serwersms.apiv2;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

@Order(500)
class BlacklistTest extends ApiTestBase {

    @Test
    @Order(1)
    void testAdd() {
        String result = api.blacklist.add("500600720");
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

        String result = api.blacklist.index("", params);
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("items"), "Expected 'items', got: " + result);

        assertEquals(paramsBefore, params, "blacklist.index must not mutate the caller's params map");

    }

    @Test
    @Order(3)
    void testCheck() {
        String result = api.blacklist.check("500600720");
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("exists"), "Expected 'exists', got: " + result);
        assertTrue(json.optBoolean("exists"), "Expected exists:true, got: " + result);
    }

    @Test
    @Order(4)
    void testDelete() {
        String result = api.blacklist.delete("500600720");
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("success"), "Expected 'success', got: " + result);
        assertTrue(json.optBoolean("success"), "Expected success:true, got: " + result);
    }
}
