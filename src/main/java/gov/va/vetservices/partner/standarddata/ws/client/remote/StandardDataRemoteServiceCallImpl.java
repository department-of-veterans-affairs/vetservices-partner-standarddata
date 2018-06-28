package gov.va.vetservices.partner.standarddata.ws.client.remote;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import gov.va.ascent.framework.config.AscentCommonSpringProfiles;
import gov.va.ascent.framework.transfer.PartnerTransferObjectMarker;
import gov.va.ascent.framework.ws.client.remote.RemoteServiceCall;

/**
 * Implements the {@link RemoteServiceCall} interface for the remote client impls spring profile
 */
@Profile(AscentCommonSpringProfiles.PROFILE_REMOTE_CLIENT_IMPLS)
@Component(StandardDataRemoteServiceCallImpl.BEAN_NAME)
public class StandardDataRemoteServiceCallImpl implements RemoteServiceCall {

	/** The spring bean name for implementation. MUST BE UNIQUE ACROSS ALL PARTNER JARS */
	public static final String BEAN_NAME = "standardDataRemoteServiceCall";

	@Override
	public PartnerTransferObjectMarker callRemoteService(final WebServiceTemplate webserviceTemplate,
			final PartnerTransferObjectMarker request,
			final Class<? extends PartnerTransferObjectMarker> requestClass) {

		return (PartnerTransferObjectMarker) webserviceTemplate.marshalSendAndReceive(requestClass.cast(request));
	}

}
