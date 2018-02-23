package gov.va.vetservices.partner.standarddata.ws.client;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import gov.va.ascent.framework.config.AscentCommonSpringProfiles;
import gov.va.vetservices.partner.standarddata.ws.client.transfer.GetContentionClassificationTypeCodeList;
import gov.va.vetservices.partner.standarddata.ws.client.transfer.GetContentionClassificationTypeCodeListResponse;


/**
 * <p>
 * Tests the webservice implementation. Note specifically the @ActiveProfiles
 * and @ContextConfiguration.
 * </p>
 * <p>
 * To engage mocking capabilities, @ActiveProfiles must specify the simulator
 * profile. {@link TreatmentFacilityRemoteServiceCallMock} declares itself as the mocking
 * implementation for the simulator profile.
 * </p>
 * <p>
 * MockitoJUnitRunner class cannot be used to @RunWith because the application
 * context must Autowire the WebServiceTemplate from the client implementation.
 * </p>
 *
 * @author Vanapalliv
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners(inheritListeners = false, listeners = { DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class })
@ActiveProfiles({ AscentCommonSpringProfiles.PROFILE_REMOTE_CLIENT_SIMULATORS })
@ContextConfiguration(inheritLocations = false, classes = { PartnerMockFrameworkTestConfig.class,
		StandardDataWsClientConfig.class })
public class StandardDataWsClientImpl_UnitTest extends AbstractStandardDataTest{

	@Autowired
	@Qualifier(StandardDataWsClientImpl.BEAN_NAME)
	StandardDataWsClient standardDataWsClientImpl;

	@Before
	public void setUp() {
		assertNotNull("FAIL standardDataWsClientImpl cannot be null.", standardDataWsClientImpl);
	}

	@Test
	public void testGetContentionClassificationTypeCodeList() {

		// call the impl declared by the current @ActiveProfiles
		GetContentionClassificationTypeCodeList request = makeRequest();
		GetContentionClassificationTypeCodeListResponse response = 
				standardDataWsClientImpl.getContentionClassificationTypeCodeList(request);
		assertNotNull(response);
		assertNotNull(response.getReturns());
		assertNotNull(response.getReturns().size());
		assertTrue(response.getReturns().size() > 1);
	}



}