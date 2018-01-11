package gov.va.vetservices.partner.standarddata.ws.client;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import gov.va.ascent.framework.config.AscentCommonSpringProfiles;
import gov.va.vetservices.partner.standarddata.ws.client.transfer.ContentionClassification;
import gov.va.vetservices.partner.standarddata.ws.client.transfer.GetContentionClassificationTypeCodeList;
import gov.va.vetservices.partner.standarddata.ws.client.transfer.GetContentionClassificationTypeCodeListResponse;
import gov.va.vetservices.partner.standarddata.ws.client.transfer.ObjectFactory;

/**
 * This class is the implementation for the StandardDataWsClientSimulator Junit test.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({ AscentCommonSpringProfiles.PROFILE_ENV_LOCAL_INT, AscentCommonSpringProfiles.PROFILE_REMOTE_CLIENT_SIMULATORS })

/** ISSUE
 * This was removed from @ContextConfiguration:   , PartnerMockFrameworkTestConfig.class })
 * otherwise the properties fail to load
 */
@ContextConfiguration(inheritLocations = false, classes = { StandardDataWsClientConfig.class })
public class StandardDataWsClientSimulator_UnitTest {

	/** The StandardData WS client */
	@Autowired
	StandardDataWsClient standardDataWsClient;

	/**
	 * The object factory for the StandardData
	 */
	protected static final ObjectFactory STANDARDDATA_OBJECT_FACTORY = new ObjectFactory();

	@Before
	public void before() throws Exception {
		assertNotNull(standardDataWsClient);
	}

	@Test
	public void testGetContentionClassificationTypeCodeList() {

		final GetContentionClassificationTypeCodeList request = new GetContentionClassificationTypeCodeList();

		assertNotNull(request);
		final GetContentionClassificationTypeCodeListResponse response =
				standardDataWsClient.getContentionClassificationTypeCodeList(request);
		assertNotNull(response);
		final ContentionClassification contention = response.getReturn().get(0);
		assertNotNull(contention);
		assertNotNull(contention.getClsfcnTxt());
	}
}
