package gov.va.vetservices.partner.standarddata.ws.client;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import gov.va.ascent.framework.config.AscentCommonSpringProfiles;
import gov.va.ascent.framework.util.Defense;
import gov.va.ascent.framework.ws.client.BaseWsClientSimulator;
import gov.va.vetservices.partner.standarddata.ws.client.transfer.ContentionClassification;
import gov.va.vetservices.partner.standarddata.ws.client.transfer.GetContentionClassificationTypeCodeList;
import gov.va.vetservices.partner.standarddata.ws.client.transfer.GetContentionClassificationTypeCodeListResponse;

/**
 * This class implements a Simulator for the standarddata WS Client.
 *
 * @author StuartT
 */
@Component(StandardDataWsClientSimulator.BEAN_NAME)
@Profile({ AscentCommonSpringProfiles.PROFILE_REMOTE_CLIENT_SIMULATORS,
	StandardDataWsClient.PROFILE_STANDARDDATAWSCLIENT_REMOTE_CLIENT_SIM })
public class StandardDataWsClientSimulator extends BaseWsClientSimulator implements StandardDataWsClient {

	/**
	 * The Constant BEAN_NAME.
	 */
	public static final String BEAN_NAME = "standardDataWsClientSimulator";

	/**
	 * The Constant LOGGER.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(StandardDataWsClientSimulator.class);

	/**
	 * Post construct.
	 */
	@PostConstruct
	public final void postConstruct() {
		LOGGER.warn("Using SIMULATOR as implementation for StandardDataWsClient Interface.");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see gov.va.ascent.common.services.ws.client.standarddata.StandardDataWsClient#getContentionClassificationTypeCodeList
	 * (javax.xml.bind.JAXBElement)
	 */
	@Override
	public final GetContentionClassificationTypeCodeListResponse getContentionClassificationTypeCodeList
	(final GetContentionClassificationTypeCodeList getContentionClassificationTypeCodeListRequest) {
		final String[] clsfcnsList = {"abnormal heart", "alveolar abscesses",
				"arrhythmia", "Neurological other System", "Cancer - Musculoskeletal - Ankle"};
		Defense.notNull(getContentionClassificationTypeCodeListRequest,
				"Invalid web service simulator invocation. getContentionClassificationTypeCodeListRequest must not be null.");
		final GetContentionClassificationTypeCodeListResponse response = new GetContentionClassificationTypeCodeListResponse();

		XMLGregorianCalendar xmlDate = null;
		try {
			final Date  endDt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).parse("2016-02-04 17:51:56");
			final GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(endDt);
			xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);

		} catch(final ParseException pe) {
			LOGGER.error("Invalid Date format", pe);
		}catch (final DatatypeConfigurationException dce) {
			LOGGER.error("Failed while converting XMLGregorianCalendar", dce);
		}

		for(final String item:clsfcnsList) {
			final ContentionClassification contention = new ContentionClassification();
			contention.setClsfcnTxt(item);
			if (!"Neurological other System".equals(item) && !"Cancer - Musculoskeletal - Ankle".equals(item)) {
				contention.setEndDt(xmlDate);
			}

			response.getReturn().add(contention);
		}

		return response;
	}
}
