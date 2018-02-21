package gov.va.vetservices.partner.standarddata.ws.client.remote;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import gov.va.ascent.framework.config.AscentCommonSpringProfiles;
import gov.va.ascent.framework.transfer.AbstractTransferObject;
import gov.va.ascent.framework.util.Defense;
import gov.va.ascent.framework.ws.client.remote.AbstractRemoteServiceCallMock;
import gov.va.ascent.framework.ws.client.remote.RemoteServiceCall;
import gov.va.vetservices.partner.standarddata.ws.client.transfer.GetContentionClassificationTypeCodeList;

/**
 * Implements the {@link RemoteServiceCall} interface, and extends
 * {@link AbstractRemoteServiceCallMock} for mocking the remote client under the
 * simulators spring profile.
 *
 * @author Vanapalliv
 */
@Profile(AscentCommonSpringProfiles.PROFILE_REMOTE_CLIENT_SIMULATORS)
@Component(StandardDataRemoteServiceCallImpl.BEAN_NAME)
public class StandardDataRemoteServiceCallMock extends AbstractRemoteServiceCallMock { // implements RemoteServiceCall {

	/** default mock data if stateCode is null or empty */
	private static final String ALL_DISABILITIES = "allDisabilities";

	@Override
	public AbstractTransferObject callRemoteService(final WebServiceTemplate webserviceTemplate, final AbstractTransferObject request,
			final Class<? extends AbstractTransferObject> requestClass) {

		return super.callMockService(webserviceTemplate, request, requestClass);
	}

	@Override
	protected String getKeyForMockResponse(final AbstractTransferObject request) {
		Defense.notNull(request);

		String mockFilename = null;

		if (request.getClass().isAssignableFrom(GetContentionClassificationTypeCodeList.class)
				&& (((GetContentionClassificationTypeCodeList) request) != null)) {
			// specify a mock filename that is the state code
			mockFilename = ALL_DISABILITIES;
		}

		if(mockFilename == null || mockFilename.trim().equals(""))
			mockFilename = ALL_DISABILITIES;
			
		return mockFilename;
	}

}
