package gov.va.vetservices.partner.standarddata.ws.client;

import gov.va.ascent.framework.exception.AscentRuntimeException;

/**
 * This class represents the unique exception that can be thrown by the standarddataCaseWsClient.
 *
 */
public class StandardDataWsClientException extends AscentRuntimeException {

	/**
	 * generated version Id
	 */
	private static final long serialVersionUID = -8751235554954215227L;

	/**
	 * Instantiates a new exception.
	 */
	public StandardDataWsClientException() {
		super();
	}

	/**
	 * Instantiates a new exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public StandardDataWsClientException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new exception.
	 *
	 * @param message the message
	 */
	public StandardDataWsClientException(final String message) {
		super(message);
	}

	/**
	 * Instantiates a new exception.
	 *
	 * @param cause the cause
	 */
	public StandardDataWsClientException(final Throwable cause) {
		super(cause);
	}
}
