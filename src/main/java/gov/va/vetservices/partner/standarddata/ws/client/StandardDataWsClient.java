package gov.va.vetservices.partner.standarddata.ws.client;

import gov.va.vetservices.partner.standarddata.ws.client.transfer.GetContentionClassificationTypeCodeList;
import gov.va.vetservices.partner.standarddata.ws.client.transfer.GetContentionClassificationTypeCodeListResponse;

/**
 * This interface contains the TreatmentFacility operations in the remote MedicalTreatmentFacility
 * web service
 *
 * @author Vanapalliv
 *
 */
//Sonar mis-identifies this interface as a single-abstract-method interface ("squid:S1609").
// More methods could be added from the partner's SOAP API,
// so anyone who uses lambda expressions against this interface will be hosed if/when that happens.
@SuppressWarnings("squid:S1609")
public interface StandardDataWsClient {

	/**
	 * Get the TreatmentFacilityList from the remote MedicalTreatmentFacility Web Service.
	 * @param request The remote request entity to find treatment facilities
	 * @return GetVAMedicalTreatmentFacilityListResponse The remote response entity
	 */

	GetContentionClassificationTypeCodeListResponse getContentionClassificationTypeCodeList(
			final GetContentionClassificationTypeCodeList getContentionClassificationTypeCodeListRequest);
}
