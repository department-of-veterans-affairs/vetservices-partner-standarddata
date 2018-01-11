package gov.va.vetservices.partner.standarddata.ws.client;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import gov.va.ascent.framework.config.AscentCommonSpringProfiles;
import gov.va.ascent.framework.util.Defense;
import gov.va.ascent.framework.ws.client.BaseWsClientImpl;
import gov.va.vetservices.partner.standarddata.ws.client.transfer.GetContentionClassificationTypeCodeList;
import gov.va.vetservices.partner.standarddata.ws.client.transfer.GetContentionClassificationTypeCodeListResponse;
import gov.va.vetservices.partner.standarddata.ws.client.transfer.ObjectFactory;

/**
 * This class provides an implementation of the StandardDataWSClient interface. It encapsulates the details of interacting with
 * the Person Web Service.
 */
@Component(StandardDataWsClientImpl.BEAN_NAME)
@Profile({ AscentCommonSpringProfiles.PROFILE_REMOTE_CLIENT_IMPLS,
	StandardDataWsClient.PROFILE_STANDARDDATAWSCLIENT_REMOTE_CLIENT_IMPL })
public class StandardDataWsClientImpl extends BaseWsClientImpl implements StandardDataWsClient {

	/**
	 * A constant representing the Spring Bean name.
	 */
	public static final String BEAN_NAME = "standardDataWsClient";

	/**
	 * The Constant StandardData_OBJECT_FACTORY.
	 */
	protected static final ObjectFactory STANDARDDATA_OBJECT_FACTORY = new ObjectFactory();

	/**
	 * Spring axiom web service template.
	 */
	@Autowired
	@Qualifier("standardDataWsClient.axiom")
	private WebServiceTemplate axiomWebServiceTemplate;

	/**
	 * The WebServiceTemplate can't be null.
	 */
	@PostConstruct
	public final void postConstruct() {
		Defense.notNull(axiomWebServiceTemplate,
				"axiomWebServiceTemplate cannot be null in order for " + this.getClass().getSimpleName() + " to work properly.");
	}

	@SuppressWarnings("unchecked")
	@Override
	public final GetContentionClassificationTypeCodeListResponse getContentionClassificationTypeCodeList
	(final GetContentionClassificationTypeCodeList contentionClassificationTypeCodeListRequest) {

		Defense.notNull(contentionClassificationTypeCodeListRequest, REQUEST_FOR_WEBSERVICE_CALL_NULL);
		GetContentionClassificationTypeCodeListResponse contentionClassificationTypeCodeListResponse = null;
		final JAXBElement<GetContentionClassificationTypeCodeList> contentionClassificationTypeCodeListElement =
				STANDARDDATA_OBJECT_FACTORY.createGetContentionClassificationTypeCodeList(contentionClassificationTypeCodeListRequest);

		final Object webServiceResponse = axiomWebServiceTemplate.marshalSendAndReceive(contentionClassificationTypeCodeListElement);
		Defense.notNull(webServiceResponse, RESPONSE_FROM_WEBSERVICE_CALL_NULL);
		final JAXBElement<GetContentionClassificationTypeCodeListResponse> webServiceResponseElement =
				(JAXBElement<GetContentionClassificationTypeCodeListResponse>) webServiceResponse;
		contentionClassificationTypeCodeListResponse = webServiceResponseElement.getValue();

		return contentionClassificationTypeCodeListResponse;
	}
}
