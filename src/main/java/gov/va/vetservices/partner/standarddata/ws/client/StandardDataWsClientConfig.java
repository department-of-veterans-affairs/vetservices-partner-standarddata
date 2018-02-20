package gov.va.vetservices.partner.standarddata.ws.client;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

import gov.va.ascent.framework.exception.InterceptingExceptionTranslator;
import gov.va.ascent.framework.log.PerformanceLogMethodInterceptor;
import gov.va.ascent.framework.util.Defense;
import gov.va.ascent.framework.ws.client.BaseWsClientConfig;

/**
 * Spring configuration for the StandardData Web Service Client.
 *
 * @author Vanapalliv
 */
@Configuration
@ComponentScan(basePackages = { "gov.va.vetservices.partner.standarddata.ws.client" }, excludeFilters = @Filter(Configuration.class))
public class StandardDataWsClientConfig extends BaseWsClientConfig {

	/** The Constant TRANSFER_PACKAGE. */
	private static final String SSD_TRANSFER_PACKAGE = "gov.va.vetservices.partner.standarddata.ws.client.transfer";

	/** Exception class for exception interceptor */
	private static final String DEFAULT_EXCEPTION_CLASS =
			"gov.va.vetservices.partner.standarddata.ws.client.StandardDataWsClientException";

	/** exclude package for exception interceptor */
	private static final String EXCLUDE_EXCEPTION_PKG = "gov.va.vetservices.partner.standarddata.ws.client";

	// ####### for test, member values are from src/test/resource/application.yml ######
	/**
	 * Boolean flag to indicate if we should log the JAXB error as an error nor
	 * debug. In the test environment we get so many errors we don't want to polute
	 * logs, however in prod data is expected to be cleaner, logs less polluted and
	 * we may want these logged.
	 */
	@Value("${vetservices-partner-standarddata.ws.client.logSchemaValidationFailureAsError:true}")
	public boolean logSchemaValidationFailureAsError;

	/** Username for standarddata WS Authentication. */
	@Value("${vetservices-partner-standarddata.ws.client.username}")
	private String username;

	/** pw for standarddata WS Authentication. */
	@Value("${vetservices-partner-standarddata.ws.client.password}")
	private String password;

	/** VA Application Name Header value. */
	@Value("${vetservices-partner-standarddata.ws.client.vaApplicationName}")
	private String vaApplicationName;

	/** VA STN_ID value */
	@Value("${vetservices-partner-standarddata.ws.client.stationID}")
	private String stationId;

	/**
	 * decides if jaxb validation logs errors.
	 */
	// causes failure because apparently true is not a boolean value: @Value("${wss-common-services.ws.log.jaxb.validation:false}")
	private boolean logValidation;

	/**
	 * Executed after dependency injection is done to validate initialization.
	 */
	@PostConstruct
	public final void postConstruct() {
		Defense.hasText(username, "Partner username cannot be empty.");
		Defense.hasText(password, "Partner password cannot be empty.");
		Defense.hasText(vaApplicationName, "Partner vaApplicationName cannot be empty.");
	}

	/**
	 * WS Client object marshaller
	 *
	 * @return object marshaller
	 */
	// Ignoring DesignForExtension check, we cannot make this spring bean method private or final
	// CHECKSTYLE:OFF
	@Bean
	@Qualifier("standardDataWsClient")
	Jaxb2Marshaller standardDataMarshaller() {
		// CHECKSTYLE:ON
		final Resource[] schemas = new Resource[] { new ClassPathResource("xsd/StandardDataService.xsd") };
		return getMarshaller(SSD_TRANSFER_PACKAGE, schemas, logValidation);
	}

	/**
	 * Axiom based WebServiceTemplate for the Chapter 31 Case Web Service Client.
	 *
	 * @param endpoint the endpoint
	 * @param readTimeout the read timeout
	 * @param connectionTimeout the connection timeout
	 * @return the web service template
	 * @throws KeyManagementException the key management exception
	 * @throws UnrecoverableKeyException the unrecoverable key exception
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws KeyStoreException the key store exception
	 * @throws CertificateException the certificate exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// Ignoring DesignForExtension check, we cannot make this spring bean method private or final
	// CHECKSTYLE:OFF
	@Bean
	@Qualifier("standardDataWsClient.axiom")
	WebServiceTemplate standardDataWsClientAxiomTemplate(
			// CHECKSTYLE:ON
			@Value("${vetservices-partner-standarddata.ws.client.endpoint}") final String endpoint,
			@Value("${vetservices-partner-standarddata.ws.client.readTimeout:60000}") final int readTimeout,
			@Value("${vetservices-partner-standarddata.ws.client.connectionTimeout:60000}") final int connectionTimeout)
			throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException,
			CertificateException, IOException {

		return createDefaultWebServiceTemplate(endpoint, readTimeout, connectionTimeout, standardDataMarshaller(),
				standardDataMarshaller(),
				new ClientInterceptor[] { getVAServiceWss4jSecurityInterceptor(username, password, vaApplicationName, null) });
	}

	/**
	 * PerformanceLogMethodInterceptor for the Chapter31 Case Web Service Client
	 *
	 * Handles performance related logging of the web service client response times.
	 *
	 * @param methodWarningThreshhold the method warning threshold
	 * @return the performance log method interceptor
	 */
	// Ignoring DesignForExtension check, we cannot make this spring bean method private or final
	// CHECKSTYLE:OFF
	@Bean
	PerformanceLogMethodInterceptor standardDataWsClientPerformanceLogMethodInterceptor(
			@Value("${vetservices-partner-standarddata.ws.client.methodWarningThreshhold:2500}") final Integer methodWarningThreshhold) {
		// CHECKSTYLE:ON
		return getPerformanceLogMethodInterceptor(methodWarningThreshhold);
	}

	/**
	 * InterceptingExceptionTranslator for the Chapter 31 Case Web Service Client
	 *
	 * Handles runtime exceptions raised by the web service client through runtime operation and communication with the remote service.
	 *
	 * @return the intercepting exception translator
	 * @throws ClassNotFoundException the class not found exception
	 */
	// Ignoring DesignForExtension check, we cannot make this spring bean method private or final
	// CHECKSTYLE:OFF
	@Bean
	InterceptingExceptionTranslator standardDataWsClientExceptionInterceptor() throws ClassNotFoundException {
		// CHECKSTYLE:ON
		final InterceptingExceptionTranslator interceptingExceptionTranslator =
				getInterceptingExceptionTranslator(DEFAULT_EXCEPTION_CLASS, PACKAGE_WSS_FOUNDATION_EXCEPTION);
		final Set<String> exclusionSet = new HashSet<>();
		exclusionSet.add(PACKAGE_WSS_FOUNDATION_EXCEPTION);
		exclusionSet.add(EXCLUDE_EXCEPTION_PKG);
		interceptingExceptionTranslator.setExclusionSet(exclusionSet);
		return interceptingExceptionTranslator;
	}

}
