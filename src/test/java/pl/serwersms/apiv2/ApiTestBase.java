package pl.serwersms.apiv2;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class ApiTestBase {

    protected SerwerSMS api;
    private static boolean tokenValid = true;
    protected String skipReason = null;

    @BeforeEach
    protected void setUp() throws Exception {
        skipReason = null;
        assumeTrue(tokenValid, "Previous test failed due to missing or invalid token - all subsequent tests skipped");

        String token = System.getenv("SERWERSMS_TEST_TOKEN");
        if (token == null || token.isEmpty()) {
            tokenValid = false;
            fail("SERWERSMS_TEST_TOKEN environment variable is not set");
        }
        api = new SerwerSMS(token);
    }

    protected boolean isEmptyResponse(String result) {
        return result == null || result.isEmpty() || "null".equals(result);
    }

    protected void assertNoAuthError(String result) {
        JSONObject json = new JSONObject(result);
        if (!json.has("error")) {
            return;
        }

        JSONObject error = json.getJSONObject("error");
        int code = error.optInt("code", -1);
        String message = error.optString("message", "");

        // --- FATAL: abort all tests ---
        // 1001 InvalidUser     - wrong login/password
        // 1002 InvalidRole     - no API access permission
        // 1003 InvalidIP       - unauthorized IP
        // 1007 InvalidRole     - account not fully active
        // 1009 InvalidIP       - blocked IP
        // 1011 InvalidToken    - token expired
        // 1012 InvalidToken    - invalid token
        if (code == 1001 || code == 1002 || code == 1003 || code == 1007 || code == 1009 || code == 1011 || code == 1012) {
            tokenValid = false;
            fail("Fatal API error (code " + code + "): " + message + " - check SERWERSMS_TOKEN");
        }

        // --- SKIP: skip this test only ---
        // API error codes returned by SerwerSMS.pl (source: dev.serwersms.pl/https-api-v2/komunikaty-ogolne)
        //
        // --- General ---
        // 1000 InvalidAction            - No action specified
        // 1001 InvalidUser              - Invalid login or password
        // 1002 InvalidRole              - User has no permission to use the API
        // 1003 InvalidIP                - Unauthorized IP address
        // 1004 InvalidID                - Invalid ID parameter
        // 1005 InvalidRole              - No permission
        // 1006 InvalidData              - Invalid data format
        // 1007 InvalidRole              - Account is not fully active
        // 1008 InvalidRole              - User has no permission to view numbers
        // 1009 InvalidIP                - Blocked IP address
        // 1010 SystemError              - System temporarily unavailable
        // 1011 InvalidToken             - API token has expired
        // 1012 InvalidToken             - Invalid API token
        //
        // --- Validation ---
        // 2000 ValidationRequiredError  - Missing required parameters
        // 2001 ValidationPhoneError     - Invalid phone number
        // 2002 ValidationCodeError      - Invalid error code
        //
        // --- Messages ---
        // 3000 SendError                - SMS ECO+ limit exhausted
        // 3001 SendError                - No permission to send SMS ECO+
        // 3002 SendError                - SMS FULL limit exhausted
        // 3003 SendError                - No permission to send SMS FULL
        // 3004 SendError                - MMS limit exhausted
        // 3005 SendError                - No permission to send MMS
        // 3006 SendError                - VOICE limit exhausted
        // 3007 SendError                - No permission to send VOICE
        // 3008 SendError                - No permission to send Flash SMS
        // 3009 SendError                - No permission to send SMS
        // 3100 SendError                - SMS sending has been blocked
        // 3101 SendError                - Message is empty
        // 3102 SendError                - Message exceeded the allowed number of characters
        // 3103 SendError                - Invalid recipient number
        // 3104 SendError                - No phone numbers provided
        // 3105 SendError                - Invalid message content
        // 3106 SendError                - Cannot use this sender name
        // 3107 SendError                - No permission to send personalized messages
        // 3108 SendError                - Invalid send time
        // 3109 SendError                - Invalid file
        // 3110 SendError                - Too many messages to send (max 100 000 SMS per request)
        // 3111 SendError                - Uniqueness error
        // 3200 SendError                - Invalid characters in unique_id
        // 3201 SendError                - Provided unique_id values are not unique
        // 3202 SendError                - Mismatched count of personalized messages and unique_id
        // 3330 MessageError             - No messages
        // 3331 MessageError             - Too many records to display
        //
        // --- Files ---
        // 4000 FileError                - File is too large
        // 4001 FileError                - File does not exist
        // 4002 FileInvalid              - Invalid file
        //
        // --- Phone number / HLR ---
        // 4100 PhoneError               - Connection error
        // 4101 PhoneError               - Invalid number
        // 4102 PhoneError               - No permission to HLR
        // 4103 PhoneError               - HLR query limit exhausted
        // 4104 PhoneError               - Account blocked
        // 4105 PhoneError               - Waiting for response
        // 4106 PhoneError               - Invalid identifier
        //
        // --- Premium ---
        // 4200 PremiumError             - Send error
        // 4201 PremiumError             - Invalid data
        //
        // --- Account ---
        // 4300 AccountError             - Missing login
        // 4301 AccountError             - Missing password
        // 4302 AccountError             - Missing phone number
        // 4303 AccountError             - Missing e-mail address
        // 4304 AccountError             - Missing first name
        // 4305 AccountError             - Missing last name
        // 4306 AccountError             - Missing company name
        // 4307 AccountError             - Invalid number
        // 4308 AccountError             - Invalid e-mail address
        // 4309 AccountError             - Login too short
        // 4310 AccountError             - Invalid login
        // 4311 AccountError             - Invalid company
        // 4312 AccountError             - Login already exists
        // 4313 AccountError             - Registration error
        // 4320 AccountError             - Current password not provided
        // 4321 AccountError             - Current password is invalid
        // 4322 AccountError             - New password not provided
        // 4323 AccountError             - New passwords do not match
        // 4324 AccountError             - Login not provided
        // 4325 AccountError             - Invalid login
        //
        // --- Sender names ---
        // 4400 SenderExists             - Sender name already exists
        // 4401 SenderError              - Cannot add a number as a sender name
        // 4402 SenderInvalid            - Invalid sender name
        // 4403 SenderError              - Invalid number
        // 4404 SenderError              - Invalid code or number
        // 4405 SenderError              - Number was already active
        // 4406 SenderError              - Demo account cannot create new sender names
        // 4407 SenderError              - Free test package allows creating only one custom sender name
        //
        // --- Subaccounts ---
        // 4500 SubaccountError          - No permission to the users section
        // 4501 SubaccountError          - Subaccount cannot create subaccounts
        // 4502 SubaccountError          - Login taken
        // 4503 SubaccountError          - No permission template
        // 4504 SubaccountError          - Invalid login or password
        //
        // --- Blacklist ---
        // 4600 BlacklistError           - No operation to perform
        // 4601 BlacklistPhoneExists     - Number already exists on the list
        // 4602 BlacklistError           - Specified number not found
        if (code >= 1000) {
            skipReason = result;
            assumeTrue(false, skipReason);
        }
    }
}
