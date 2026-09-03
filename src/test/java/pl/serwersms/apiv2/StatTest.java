package pl.serwersms.apiv2;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

@Order(300)
class StatTest extends ApiTestBase {

    @Test
    @Order(1)
    void testIndex() {
        String result = api.stat.index(new HashMap<>());
        assertNoAuthError(result);

        JSONObject json = new JSONObject(result);
        assertTrue(json.has("items"), "Expected 'items', got: " + result);
    }
}
