package gov.va.vetservices.partner.standarddata.ws.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class StandardDataWsClientException_UnitTest {

	private StandardDataWsClientException testException;

	private static final String TEST_MESSAGE = "This is a test error message";
	private static NullPointerException TEST_CAUSE = new NullPointerException();

	@Test
	public void testTreatmentFacilityWsClientException() {
		testException = new StandardDataWsClientException();
		assertNotNull(testException);
	}

	@Test
	public void testTreatmentFacilityWsClientExceptionStringThrowable() {
		testException = new StandardDataWsClientException(TEST_MESSAGE, TEST_CAUSE);
		assertEquals(TEST_MESSAGE, testException.getMessage());
		assertEquals(TEST_CAUSE, testException.getCause());
	}

	@Test
	public void testTreatmentFacilityWsClientExceptionString() {
		testException = new StandardDataWsClientException(TEST_MESSAGE);
		assertEquals(TEST_MESSAGE, testException.getMessage());
	}

	@Test
	public void testTreatmentFacilityWsClientExceptionThrowable() {
		testException = new StandardDataWsClientException(TEST_CAUSE);
		assertEquals(TEST_CAUSE, testException.getCause());
	}

}
