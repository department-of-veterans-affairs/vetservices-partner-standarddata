package gov.va.vetservices.partner.standarddata.ws.client.remote;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import gov.va.ascent.framework.config.AscentCommonSpringProfiles;
import gov.va.ascent.framework.transfer.AbstractTransferObject;
import gov.va.ascent.framework.util.Defense;
import gov.va.ascent.framework.ws.client.remote.AbstractRemoteServiceCallMock;
import gov.va.ascent.framework.ws.client.remote.RemoteServiceCall;

/**
 * Implements the {@link RemoteServiceCall} interface, and extends
 * {@link AbstractRemoteServiceCallMock} for mocking the remote client under the
 * simulators spring profile.
 */
@Profile(AscentCommonSpringProfiles.PROFILE_REMOTE_CLIENT_SIMULATORS)
@Component(StandardDataRemoteServiceCallImpl.BEAN_NAME)
public class StandardDataRemoteServiceCallMock extends AbstractRemoteServiceCallMock {

	/** default mock data if stateCode is null or empty */
	private static final String ALL_DISABILITIES = "allDisabilities";

	/** error for null request */
	static final String ERROR_NULL_REQUEST = "getKeyForMockResponse request parameter cannot be null.";

	/*
	 * (non-Javadoc)
	 *
	 * @see gov.va.ascent.framework.ws.client.remote.RemoteServiceCall#callRemoteService(org.springframework.ws.client.core.
	 * WebServiceTemplate, gov.va.ascent.framework.transfer.AbstractTransferObject, java.lang.Class)
	 */
	@Override
	public AbstractTransferObject callRemoteService(final WebServiceTemplate webserviceTemplate, final AbstractTransferObject request,
			final Class<? extends AbstractTransferObject> requestClass) {

		return super.callMockService(webserviceTemplate, request, requestClass);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * gov.va.ascent.framework.ws.client.remote.AbstractRemoteServiceCallMock#getKeyForMockResponse(gov.va.ascent.framework.transfer.
	 * AbstractTransferObject)
	 */
	@Override
	protected String getKeyForMockResponse(final AbstractTransferObject request) {
		Defense.notNull(request, ERROR_NULL_REQUEST);

		return ALL_DISABILITIES;
	}

}
