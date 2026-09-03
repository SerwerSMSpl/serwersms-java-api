package pl.serwersms.apiv2;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;
import static org.junit.jupiter.api.Assertions.*;

@Order(250)
class FaultTest extends ApiTestBase {

    @Test
    @Order(1)
    void testView() {
        String result = api.fault.view(1000);
        assertNotNull(result, "API response must not be null");
        assertFalse(result.isEmpty(), "API response must not be empty");

        JSONObject json = new JSONObject(result);

        assertTrue(json.has("error"), "Expected 'error', got: " + result);
        assertEquals(1000, json.getJSONObject("error").optInt("code"),
                "Expected error code 1000, got: " + result);
    }
}
