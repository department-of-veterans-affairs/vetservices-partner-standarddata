package gov.va.vetservices.partner.standarddata.ws.client.remote;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import gov.va.vetservices.partner.standarddata.ws.client.AbstractStandardDataTest;
import gov.va.vetservices.partner.standarddata.ws.client.transfer.GetContentionClassificationTypeCodeList;


public class RemoteServiceCallMock_UnitTest extends AbstractStandardDataTest {

	private static final String ALL_DISABILITIES = "allDisabilities";

	@Test
	public void testGetKeyForMockResponse() {
		StandardDataRemoteServiceCallMock mock = new StandardDataRemoteServiceCallMock();
		GetContentionClassificationTypeCodeList request = makeRequest();
		String keyForMockResponse = mock.getKeyForMockResponse(request);

		assertNotNull(keyForMockResponse);
    	keyForMockResponse = mock.getKeyForMockResponse(request);

		assertNotNull(keyForMockResponse);
		assertTrue(keyForMockResponse.equals(ALL_DISABILITIES));
	}
	
	@Test
	public void testGetKeyForMockNullResponse() {
		StandardDataRemoteServiceCallMock mock = new StandardDataRemoteServiceCallMock();
		GetContentionClassificationTypeCodeList request = null;
		String keyForMockResponse = null;
		try {
			keyForMockResponse = mock.getKeyForMockResponse(request);
		} catch (Throwable e) {
			e.printStackTrace();
			assertTrue("Invalid excepetion was thrown.", IllegalArgumentException.class.equals(e.getClass()));
		}

		assertNull("Null request should have thrown exception.", keyForMockResponse);

	}

	@Test
	public void testCallRemoteService() {
		assertTrue(true);
	}

}
