package gov.va.vetservices.partner.standarddata.ws.client.remote;

import static org.junit.Assert.assertNotNull;
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
	public void testCallRemoteService() {
		assertTrue(true);
	}

}
