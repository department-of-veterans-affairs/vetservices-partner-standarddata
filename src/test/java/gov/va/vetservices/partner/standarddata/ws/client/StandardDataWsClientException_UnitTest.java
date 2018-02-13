package gov.va.vetservices.partner.standarddata.ws.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * Test class for the StandardDataWsClientException
 *
 */
public class StandardDataWsClientException_UnitTest {

	private static final String EXCEPTION_MESSAGE = "This is an exception message";
	private static final Throwable THROWABLE = new StandardDataWsClientException();

	@Test
	public void testDefaultConstructor() {

		final StandardDataWsClientException ex = new StandardDataWsClientException();
		final String message = parseMessageFromExceptionMessage(ex.getMessage());
		assertNull(message);
		assertNull(ex.getCause());
	}

	@Test
	public void testMessageConstructor() {

		final StandardDataWsClientException ex = new StandardDataWsClientException(EXCEPTION_MESSAGE);
		assertEquals(EXCEPTION_MESSAGE, ex.getMessage());
		assertNull(ex.getCause());
	}

	@Test
	public void testThrowableConstructor() {

		final StandardDataWsClientException ex = new StandardDataWsClientException(THROWABLE);
		final String message = parseMessageFromExceptionMessage(ex.getMessage());
		assertNull(message);
		assertEquals(THROWABLE, ex.getCause());
	}

	@Test
	public void testMessageThrowableConstructor() {

		final StandardDataWsClientException ex = new StandardDataWsClientException(EXCEPTION_MESSAGE, THROWABLE);
		assertEquals(EXCEPTION_MESSAGE, ex.getMessage());
		assertEquals(THROWABLE, ex.getCause());
	}

	/**
	 * Parse off the exception classname that gets added by some subclass, and return just the message
	 *
	 * @param message
	 * @return
	 */
	private String parseMessageFromExceptionMessage(final String message) {
		String tmp = StringUtils.substringAfter(message, ":");
		if((tmp != null) && (tmp.trim().length() < 1)) {
			tmp = null;
		}
		return tmp;
	}
}
