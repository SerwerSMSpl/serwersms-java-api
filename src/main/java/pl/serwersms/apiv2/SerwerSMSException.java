package pl.serwersms.apiv2;

/**
 * Exception thrown on API communication errors with SerwerSMS.pl
 * (network error, timeout, HTTP error, response parsing error).
 *
 * <p>This is an unchecked exception (RuntimeException) - intentional design decision
 * to avoid forcing "throws" declarations across dozens of methods
 * in Account, Message, Contact etc. classes that only delegate
 * calls to SerwerSMS.send()/sendByte().</p>
 */
public class SerwerSMSException extends RuntimeException {

    private final int httpStatusCode;

    public SerwerSMSException(String message) {
        super(message);
        this.httpStatusCode = -1;
    }

    public SerwerSMSException(String message, Throwable cause) {
        super(message, cause);
        this.httpStatusCode = -1;
    }

    public SerwerSMSException(String message, int httpStatusCode, Throwable cause) {
        super(message, cause);
        this.httpStatusCode = httpStatusCode;
    }

    /**
     * HTTP response code if available at the time of the error.
     * Returns -1 if the code was not known (e.g. error before receiving a response).
     */
    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
