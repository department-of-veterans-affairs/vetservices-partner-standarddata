package gov.va.vetservices.partner.standarddata.ws.client;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import gov.va.vetservices.partner.standarddata.ws.client.transfer.GetContentionClassificationTypeCodeList;


public class AbstractStandardDataTest {

	protected GetContentionClassificationTypeCodeList makeRequest() {
		final GetContentionClassificationTypeCodeList request = new GetContentionClassificationTypeCodeList();
		return request;
	}

	@Test
	public void testInterface() {
		assertTrue(StandardDataWsClient.class.isInterface());
	}

}
