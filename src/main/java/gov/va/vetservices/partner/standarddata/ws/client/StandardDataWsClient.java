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
//Future requirements could add more methods to this class from the partner's SOAP API,
//so it is best to disallow lambda expressions against this interface (not put @FunctionalInterface on this class).
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
