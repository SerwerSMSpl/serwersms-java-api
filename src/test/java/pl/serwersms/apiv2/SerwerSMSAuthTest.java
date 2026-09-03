package pl.serwersms.apiv2;

import org.junit.jupiter.api.Test;
import org.json.JSONObject;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Order;

@Order(100)
class SerwerSMSAuthTest {

    @Test
    @Order(1)
    void testEmptyTokenThrowsException() {
        Exception ex = assertThrows(Exception.class, () -> new SerwerSMS(""));
        assertNotNull(ex.getMessage(), "Exception message must not be null");
        assertTrue(ex.getMessage().contains("Empty token"), "Expected 'Empty token' message, got: " + ex.getMessage());
    }

    @Test
    @Order(2)
    void testInvalidTokenReturnsError() throws Exception {
        SerwerSMS invalidToken = new SerwerSMS("invalid-token-12345");
        HashMap<String, String> options = new HashMap<>();
        options.put("test", "true");

        String result = invalidToken.message.sendSms("500600700", "Test", "", options);

        assertNotNull(result, "API response must not be null");
        assertFalse(result.isEmpty(), "API response must not be empty");

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("error"), "API response must contain 'error' for invalid token, got: " + result);
        assertEquals("InvalidToken", json.getJSONObject("error").optString("type"), "Expected error type 'InvalidToken', got: " + json.getJSONObject("error").optString("type"));
    }
}
