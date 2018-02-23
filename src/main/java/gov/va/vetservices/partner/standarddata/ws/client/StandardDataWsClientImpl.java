package gov.va.vetservices.partner.standarddata.ws.client;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import gov.va.ascent.framework.util.Defense;
import gov.va.ascent.framework.ws.client.BaseWsClientImpl;
import gov.va.ascent.framework.ws.client.remote.RemoteServiceCall;
import gov.va.vetservices.partner.standarddata.ws.client.remote.StandardDataRemoteServiceCallImpl;
import gov.va.vetservices.partner.standarddata.ws.client.transfer.GetContentionClassificationTypeCodeList;
import gov.va.vetservices.partner.standarddata.ws.client.transfer.GetContentionClassificationTypeCodeListResponse;

/**
 * Spring Web Service based implementation of the TreatmentFacility
 * interface
 *
 * @author Vanapalliv
 *
 */
@Component(StandardDataWsClientImpl.BEAN_NAME)
public class StandardDataWsClientImpl extends BaseWsClientImpl implements StandardDataWsClient {

	/** The Constant BEAN_NAME. */
	public static final String BEAN_NAME = "standardDataWsClient";

	@Autowired
	@Qualifier(StandardDataRemoteServiceCallImpl.BEAN_NAME)
	private RemoteServiceCall remoteServiceCall;

	/** axiom web service template for treatmentFacility service */
	@Autowired
	@Qualifier("standardDataWsClientAxiomTemplate")
	private WebServiceTemplate standardDataWsTemplate;

	/**
	 * Executed after dependency injection is done to validate initialization.
	 */
	@PostConstruct
	public final void postConstruct() {
		Defense.notNull(remoteServiceCall, "remoteServiceCall cannot be null.");
		Defense.notNull(standardDataWsTemplate,
				"standardDataWsTemplate cannot be null in order for StandardDataWsClientImpl to work properly.");
	}

	/**
	 * <p>
	 * Get a list of treatment facilities from the partner.
	 * </p>
	 * <p>
	 * The RemoteServiceCall implementation is selected by the current spring profile. REMOTE_CLIENT_IMPLS
	 * <ul>
	 * <li>PROFILE_REMOTE_CLIENT_IMPLS instantiates RemoteServiceCallImpl</li>
	 * <li>PROFILE_REMOTE_CLIENT_SIMULATORS instantiates RemoteServiceCallMock</li>
	 * </ul>
	 * </p>
	 */
	@Override
	public final GetContentionClassificationTypeCodeListResponse
			getContentionClassificationTypeCodeList(final GetContentionClassificationTypeCodeList request) {
		Defense.notNull(request);

		final GetContentionClassificationTypeCodeListResponse response =
				(GetContentionClassificationTypeCodeListResponse) remoteServiceCall.callRemoteService(standardDataWsTemplate, request,
						request.getClass());

		Defense.notNull(response, RESPONSE_FROM_WEBSERVICE_CALL_NULL);

		return response;
	}

}
