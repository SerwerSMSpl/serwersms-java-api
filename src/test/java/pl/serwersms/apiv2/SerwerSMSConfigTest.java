package pl.serwersms.apiv2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

@Order(105)
class SerwerSMSConfigTest {

    @Test
    @Order(1)
    void testSetTooShortTimeoutIsApplied() throws Exception {
        String token = System.getenv("SERWERSMS_TEST_TOKEN");
        assumeFalse(token == null || token.isEmpty(),
                "SERWERSMS_TEST_TOKEN not set - skipping timeout integration test");

        SerwerSMS api = new SerwerSMS(token);
        api.setTimeout(1, 1);

        assertThrows(SerwerSMSException.class, () -> {
            api.account.limits(new HashMap<>());
        });
    }

    @Test
    @Order(2)
    void testSetApiUrlRejectsNull() throws Exception {
        SerwerSMS api = new SerwerSMS("test-token-123");
        assertThrows(IllegalArgumentException.class, () -> api.setApiUrl(null));
    }

    @Test
    @Order(3)
    void testSetApiUrlRejectsEmpty() throws Exception {
        SerwerSMS api = new SerwerSMS("test-token-123");
        assertThrows(IllegalArgumentException.class, () -> api.setApiUrl(""));
    }

    @Test
    @Order(4)
    void testSetApiUrlRejectsMalformed() throws Exception {
        SerwerSMS api = new SerwerSMS("test-token-123");
        assertThrows(IllegalArgumentException.class, () -> api.setApiUrl("not-a-valid-url"));
    }

    @Test
    @Order(5)
    void testSetApiUrlRejectsNonHttp() throws Exception {
        SerwerSMS api = new SerwerSMS("test-token-123");
        assertThrows(IllegalArgumentException.class, () -> api.setApiUrl("ftp://example.com/"));
    }

    @Test
    @Order(6)
    void testSetApiUrlAcceptsValidUrl() throws Exception {
        SerwerSMS api = new SerwerSMS("test-token-123");
        api.setApiUrl("https://test.api.serwersms.pl/");
    }
}
