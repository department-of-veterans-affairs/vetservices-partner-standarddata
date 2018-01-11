package gov.va.vetservices.partner.standarddata.ws.client;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import gov.va.ascent.framework.config.AscentCommonSpringProfiles;
import gov.va.vetservices.partner.mock.framework.PartnerMockFrameworkTestConfig;
import gov.va.vetservices.partner.standarddata.ws.client.transfer.ObjectFactory;

/**
 * Unit test of AddressValidateWsClientImpl.
 */
// ignored for now as its integration test and requires SOAP UI to be running
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({ AscentCommonSpringProfiles.PROFILE_ENV_LOCAL_INT, AscentCommonSpringProfiles.PROFILE_REMOTE_CLIENT_IMPLS })
@ContextConfiguration(inheritLocations = false, classes = { StandardDataWsClientConfig.class,
		PartnerMockFrameworkTestConfig.class })
public class StandardDataWsClientImpl_UnitTest {

	/**
	 * The chapter 31 case ws client.
	 */
	@Autowired
	StandardDataWsClient standardDataWsClient;

	/**
	 * The Constant STANDARDDATA_OBJECT_FACTORY.
	 */
	private static final ObjectFactory STANDARDDATA_OBJECT_FACTORY = new ObjectFactory();

	@Before
	public void before() {
		assertNotNull(standardDataWsClient);
		assertNotNull(STANDARDDATA_OBJECT_FACTORY);
		// ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
		// this.validator = vf.getValidator();
	}

	/**
	 * Test findStationAddress.
	 */
	@Test
	public void testGetContentionClassificationTypeCodeList() {

		/** ISSUE
		 * Commented because approach to mocking has changed
		new MockUp<WebServiceTemplate>() {
			@Mock
			public Object marshalSendAndReceive(final Object requestPayload) {
				final GetContentionClassificationTypeCodeListResponse response = new GetContentionClassificationTypeCodeListResponse();
				final ContentionClassification contention = new ContentionClassification();
				contention.setClsfcnTxt("abnormal mitral valve");
				try {
					final Date dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2016-02-04 11:51:56");
					final GregorianCalendar cal = new GregorianCalendar();
					cal.setTime(dt);
					final XMLGregorianCalendar xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);

					contention.setEndDt(xmlDate);
				} catch(final ParseException pe) {
					Assert.fail(pe.getMessage());
				}catch (final DatatypeConfigurationException dce) {
					Assert.fail(dce.getMessage());
				}

				response.getReturn().add(contention);
				return STANDARDDATA_OBJECT_FACTORY.createGetContentionClassificationTypeCodeListResponse(response);
			}
		};


		final GetContentionClassificationTypeCodeList request = new GetContentionClassificationTypeCodeList();

		final GetContentionClassificationTypeCodeListResponse response =
				standardDataWsClient.getContentionClassificationTypeCodeList(request);

		// Check the response for valid data.
		Assert.assertNotNull("Invalid Web Service response element. Response must not be null.", response);
		final ContentionClassification contention = response.getReturn().get(0);

		Assert.assertTrue("Contention should not be null", contention != null);

		// If no DTO was returned, the participant Id did not match a case.
		Assert.assertNotNull("No data returned from Contention" , contention.getClsfcnTxt());
		Mockit.tearDownMocks(WebServiceTemplate.class);
		 */
	}
}
