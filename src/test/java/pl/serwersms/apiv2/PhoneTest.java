package pl.serwersms.apiv2;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;
import static org.junit.jupiter.api.Assertions.*;

@Order(600)
class PhoneTest extends ApiTestBase {

    @Test
    @Order(1)
    void testCheck() {
        String result = api.phone.check("500600700", "");
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("phone"), "Expected 'phone', got: " + result);
    }

    @Test
    @Order(2)
    void testTest() {
        String result = api.phone.test("500600700");
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("correct"), "Expected 'correct', got: " + result);
        assertTrue(json.optBoolean("correct"), "Expected correct:true, got: " + result);
    }
}
