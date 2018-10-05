package gov.va.vetservices.partner.standarddata.ws.client.remote;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import gov.va.ascent.framework.config.AscentCommonSpringProfiles;
import gov.va.ascent.framework.log.AscentLogger;
import gov.va.ascent.framework.log.AscentLoggerFactory;
import gov.va.ascent.framework.transfer.PartnerTransferObjectMarker;
import gov.va.ascent.framework.ws.client.remote.RemoteServiceCall;

/**
 * Implements the {@link RemoteServiceCall} interface for the remote client impls spring profile
 */
@Profile(AscentCommonSpringProfiles.PROFILE_REMOTE_CLIENT_IMPLS)
@Component(StandardDataRemoteServiceCallImpl.BEAN_NAME)
public class StandardDataRemoteServiceCallImpl implements RemoteServiceCall {
	private static final AscentLogger LOGGER = AscentLoggerFactory.getLogger(StandardDataRemoteServiceCallImpl.class);

	/** The spring bean name for implementation. MUST BE UNIQUE ACROSS ALL PARTNER JARS */
	public static final String BEAN_NAME = "standardDataRemoteServiceCall";

	@Override
	public PartnerTransferObjectMarker callRemoteService(final WebServiceTemplate webserviceTemplate,
			final PartnerTransferObjectMarker request,
			final Class<? extends PartnerTransferObjectMarker> requestClass) {

		PartnerTransferObjectMarker response = null;

		try {
			LOGGER.info("Calling partner SOAP service with request " + ReflectionToStringBuilder.toString(requestClass.cast(request)));
			response = (PartnerTransferObjectMarker) webserviceTemplate.marshalSendAndReceive(requestClass.cast(request));
			LOGGER.info("Returned from partner SOAP service with response " + ReflectionToStringBuilder.toString(response));
		} catch (Exception e) {
			LOGGER.error("IMPL partner service call failed with requestClass "
					+ requestClass.getName() + " and request object " + ReflectionToStringBuilder.toString(request), e);
			throw e;
		}

		return response;
	}

}
