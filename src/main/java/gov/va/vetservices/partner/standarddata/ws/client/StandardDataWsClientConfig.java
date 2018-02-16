package gov.va.vetservices.partner.standarddata.ws.client;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
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
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;

import gov.va.ascent.framework.exception.InterceptingExceptionTranslator;
import gov.va.ascent.framework.log.PerformanceLogMethodInterceptor;
import gov.va.ascent.framework.ws.client.BaseWsClientConfig;
import gov.va.ascent.framework.ws.client.WsClientSimulatorMarshallingInterceptor;

/**
 * This class represents the Spring configuration for the Chapter31 Case Web Service Client.
 *
 */
@Configuration
@ComponentScan(basePackages = { "gov.va.vetservices.partner.standarddata.ws.client" }, excludeFilters = @Filter(Configuration.class))
@SuppressWarnings("PMD.ExcessiveImports")
public class StandardDataWsClientConfig extends BaseWsClientConfig {

	/** The Constant TRANSFER_PACKAGE. */
	private static final String SSD_TRANSFER_PACKAGE = "gov.va.vetservices.partner.standarddata.ws.client.transfer";

	// ####### values are from /resource/config/*.properties ######
	/** The username. */
	@Value("${wss-partner-standarddata.ws.client.username}")
	private String ssdUsername;

	/** The password. */
	@Value("${wss-partner-standarddata.ws.client.password}")
	private String ssdPassword;

	/** The va application name. */
	@Value("${wss-partner-standarddata.ws.client.vaApplicationName}")
	private String vaApplicationName;

	/** VA STN_ID value */
	@Value("${wss-partner-standarddata.ws.client.stationID}")
	private String stationId;

	/**
	 * decides if jaxb validation logs errors.
	 */
	// causes failure because apparently true is not a boolean value: @Value("${wss-common-services.ws.log.jaxb.validation:false}")
	private boolean logValidation;

	/**
	 * WS Client object marshaller
	 *
	 * @return object marshaller
	 */
	// Ignoring DesignForExtension check, we cannot make this spring bean method private or final
	// CHECKSTYLE:OFF
	@Bean
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
			@Value("${wss-partner-standarddata.ws.client.endpoint}") final String endpoint,
			@Value("${wss-partner-standarddata.ws.client.readTimeout:60000}") final int readTimeout,
			@Value("${wss-partner-standarddata.ws.client.connectionTimeout:60000}") final int connectionTimeout)
			throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException,
			CertificateException, IOException {

		return createDefaultWebServiceTemplate(endpoint, readTimeout, connectionTimeout, standardDataMarshaller(),
				standardDataMarshaller(), new ClientInterceptor[] { standardDataSecurityInterceptor() });
	}

	/**
	 * Security interceptor to apply security to Chapter31 Case WS calls.
	 *
	 * @return security interceptor
	 */
	// jluck - ignoring DesignForExtension check, we cannot make this spring
	// bean method private or final
	// CHECKSTYLE:OFF
	@Bean
	Wss4jSecurityInterceptor standardDataSecurityInterceptor() {
		// CHECKSTYLE:ON
		return getVAServiceWss4jSecurityInterceptor(ssdUsername, ssdPassword, vaApplicationName, stationId);
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
			@Value("${wss-partner-standarddata.ws.client.methodWarningThreshhold:2500}") final Integer methodWarningThreshhold) {
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
		return getInterceptingExceptionTranslator("gov.va.vetservices.partner.standarddata.ws.client.StandardDataWsClientException",
				PACKAGE_WSS_FOUNDATION_EXCEPTION);
	}

	/**
	 * A standard bean proxy to apply interceptors to the Address web service client.
	 *
	 * @return the bean name auto proxy creator
	 */
	// Ignoring DesignForExtension check, we cannot make this spring bean method private or final
	// CHECKSTYLE:OFF
	@Bean
	BeanNameAutoProxyCreator standardDataWsClientBeanProxy() {
		// CHECKSTYLE:ON
		return getBeanNameAutoProxyCreator(
				new String[] { StandardDataWsClientImpl.BEAN_NAME, StandardDataWsClientSimulator.BEAN_NAME },
				new String[] { "standardDataWsClientExceptionInterceptor", "standardDataWsClientPerformanceLogMethodInterceptor" });
	}

	/**
	 * Ws client simulator marshalling interceptor, so that requests and responses to the simulator are passed through the marshaller
	 * to ensure we don't have any Java-to-XML conversion surprises if we leverage simulators heavily in development and then start
	 * using real web services later on.
	 *
	 * @return the ws client simulator marshalling interceptor
	 */
	// Ignoring DesignForExtension check, we cannot make this spring bean method private or final
	// CHECKSTYLE:OFF
	@Bean
	WsClientSimulatorMarshallingInterceptor standardDataWsClientSimulatorMarshallingInterceptor() {
		// CHECKSTYLE:ON
		final Map<String, Jaxb2Marshaller> marshallerForPackageMap = new HashMap<>();
		marshallerForPackageMap.put(SSD_TRANSFER_PACKAGE, standardDataMarshaller());
		return new WsClientSimulatorMarshallingInterceptor(marshallerForPackageMap);
	}

	/**
	 * A standard bean proxy to apply interceptors to the web service client simulations that we don't need/want to apply to real web
	 * service client impls.
	 *
	 * @return the bean name auto proxy creator
	 */
	// Ignoring DesignForExtension check, we cannot make this spring bean method private or final
	// CHECKSTYLE:OFF
	@Bean
	BeanNameAutoProxyCreator standardDataWsClientSimulatorProxy() {
		// CHECKSTYLE:ON
		return getBeanNameAutoProxyCreator(new String[] { StandardDataWsClientSimulator.BEAN_NAME },
				new String[] { "standardDataWsClientSimulatorMarshallingInterceptor" });
	}

}
