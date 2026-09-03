package pl.serwersms.apiv2;

import org.junit.jupiter.api.Test;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Order;

@Order(150)
class AccountTest extends ApiTestBase {

    @Test
    void testLimits() {
        String result = api.account.limits(new HashMap<>());
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("items"), "Expected 'items', got: " + result);

        JSONArray items = json.optJSONArray("items");
        assertNotNull(items, "Expected 'items' array, got: " + result);
        assertFalse(items.isEmpty(), "Expected non-empty 'items', got: " + result);

        JSONObject first = items.getJSONObject(0);
        assertTrue(first.has("type"), "Expected 'type' in first item, got: " + result);
        assertEquals("eco", first.optString("type"),
                "Expected type 'eco', got: " + first.optString("type"));
    }
}
