package gov.va.vetservices.partner.standarddata.ws.client;

import gov.va.ascent.framework.exception.AscentRuntimeException;

/**
 * Root hierarchy of exceptions which indicates there was an exception/error in
 * the TreatmentFacility web service
 *
 * @author vgadda
 */
public class StandardDataWsClientException extends AscentRuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7282496605582623526L;

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
