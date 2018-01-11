package gov.va.vetservices.partner.standarddata.ws.client;

import gov.va.vetservices.partner.standarddata.ws.client.transfer.GetContentionClassificationTypeCodeList;
import gov.va.vetservices.partner.standarddata.ws.client.transfer.GetContentionClassificationTypeCodeListResponse;

/**
 * The interface for the standarddata Web Service Client.
 *
 */
//Sonar mis-identifies this interface as a single-abstract-method interface ("squid:S1609").
//More methods could be added from the partner's SOAP API,
//so anyone who uses lambda expressions against this interface will be hosed if/when that happens.
@SuppressWarnings("squid:S1609")
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
