package gov.va.vetservices.partner.standarddata.ws.client;

import gov.va.vetservices.partner.standarddata.ws.client.transfer.GetContentionClassificationTypeCodeList;
import gov.va.vetservices.partner.standarddata.ws.client.transfer.GetContentionClassificationTypeCodeListResponse;

/**
 * The interface for the standarddata Web Service Client.
 *
 */
public interface StandardDataWsClient {

	/** Spring profile for standarddata wsclient remote client implementation. */
	String PROFILE_STANDARDDATAWSCLIENT_REMOTE_CLIENT_IMPL = "standarddatawsclient_remote_client_impl";

	/** Spring profile for standarddata wsclient remote client simulator. */
	String PROFILE_STANDARDDATAWSCLIENT_REMOTE_CLIENT_SIM = "standarddatawsclient_remote_client_sim";

	/**
	 * @param getContentionClassificationTypeCodeListRequest The StandardData Web Service
	 * GetContentionClassificationTypeCodeList request entity
	 * @return getContentionClassificationTypeCodeListResponse
	 * The StandardData Web Service GetContentionClassificationTypeCodeList response entity
	 */
	GetContentionClassificationTypeCodeListResponse getContentionClassificationTypeCodeList(
			final GetContentionClassificationTypeCodeList getContentionClassificationTypeCodeListRequest);
}
