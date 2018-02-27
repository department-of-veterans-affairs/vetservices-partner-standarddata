package gov.va.vetservices.partner.standarddata.ws.client;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
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
import gov.va.ascent.framework.util.Defense;
import gov.va.ascent.framework.ws.client.BaseWsClientConfig;

/**
 * This class represents the Spring configuration for the Web Service Client.
 */
@Configuration
@ComponentScan(basePackages = { "gov.va.vetservices.partner.standarddata.ws.client" }, excludeFilters = @Filter(Configuration.class))
public class StandardDataWsClientConfig extends BaseWsClientConfig {

	/** The package name for data transfer objects. */
	private static final String TRANSFER_PACKAGE = "gov.va.vetservices.partner.standarddata.ws.client.transfer";

	/** The XSD for this web service */
	private static final String XSD = "xsd/StandardDataService.xsd";

	/** Exception class for exception interceptor */
	private static final String DEFAULT_EXCEPTION_CLASS =
			"gov.va.vetservices.partner.standarddata.ws.client.StandardDataWsClientException";

	// ####### for test, member values are from src/test/resource/application.yml ######
	/** Decides if jaxb validation logs errors. */
	@Value("${vetservices-partner-standarddata.ws.client.logValidation:true}")
	private boolean logValidation;

	/** Username for WS Authentication. */
	@Value("${vetservices-partner-standarddata.ws.client.username}")
	private String username;

	/** Password for WS Authentication. */
	@Value("${vetservices-partner-standarddata.ws.client.password}")
	private String password;

	/** VA Application Name Header value. */
	@Value("${vetservices-partner-standarddata.ws.client.vaApplicationName}")
	private String vaApplicationName;

	/** VA station ID value */
	@Value("${vetservices-partner-standarddata.ws.client.stationID}")
	private String stationId;

	/**
	 * Executed after dependency injection is done to validate initialization.
	 */
	@PostConstruct
	public final void postConstruct() {
		Defense.hasText(username, "Partner username cannot be empty.");
		Defense.hasText(password, "Partner password cannot be empty.");
		Defense.hasText(vaApplicationName, "Partner vaApplicationName cannot be empty.");
		Defense.hasText(stationId, "Partner stationId cannot be empty.");
	}

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
		final Resource[] schemas = new Resource[] { new ClassPathResource(XSD) };
		return getMarshaller(TRANSFER_PACKAGE, schemas, logValidation);
	}

	/**
	 * Axiom based WebServiceTemplate for the Web Service Client.
	 *
	 * @param endpoint the endpoint
	 * @param readTimeout the read timeout
	 * @param connectionTimeout the connection timeout
	 * @return the web service template
	 */
	// Ignoring DesignForExtension check, we cannot make this spring bean method private or final
	// CHECKSTYLE:OFF
	@Bean
	WebServiceTemplate standardDataWsClientAxiomTemplate(
			// CHECKSTYLE:ON
			@Value("${vetservices-partner-standarddata.ws.client.endpoint}") final String endpoint,
			@Value("${vetservices-partner-standarddata.ws.client.readTimeout:60000}") final int readTimeout,
			@Value("${vetservices-partner-standarddata.ws.client.connectionTimeout:60000}") final int connectionTimeout) {

		Defense.hasText(endpoint, "standardDataWsClientAxiomTemplate endpoint cannot be empty.");

		return createDefaultWebServiceTemplate(endpoint, readTimeout, connectionTimeout, standardDataMarshaller(),
				standardDataMarshaller(), new ClientInterceptor[] { standardDataSecurityInterceptor() });
	}

	/**
	 * Security interceptor to apply wss4j security to WS calls.
	 *
	 * @return security interceptor
	 */
	// Ignoring DesignForExtension check, we cannot make this spring bean method private or final
	// CHECKSTYLE:OFF
	@Bean
	Wss4jSecurityInterceptor standardDataSecurityInterceptor() {
		// CHECKSTYLE:ON
		return getVAServiceWss4jSecurityInterceptor(username, password, vaApplicationName, stationId);
	}

	/**
	 * PerformanceLogMethodInterceptor for the Web Service Client
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
	 * InterceptingExceptionTranslator for the Web Service Client
	 *
	 * Handles runtime exceptions raised by the web service client through runtime
	 * operation and communication with the remote service.
	 *
	 * @return the intercepting exception translator
	 * @throws ClassNotFoundException the class not found exception
	 */
	// Ignoring DesignForExtension check, we cannot make this spring bean method private or final
	// CHECKSTYLE:OFF
	@Bean
	InterceptingExceptionTranslator standardDataWsClientExceptionInterceptor() throws ClassNotFoundException {
		// CHECKSTYLE:ON
		return getInterceptingExceptionTranslator(DEFAULT_EXCEPTION_CLASS, PACKAGE_WSS_FOUNDATION_EXCEPTION);
	}

	/**
	 * A standard bean proxy to apply interceptors to the web service client.
	 *
	 * @return the bean name auto proxy creator
	 */
	// Ignoring DesignForExtension check, we cannot make this spring bean method private or final
	// CHECKSTYLE:OFF
	@Bean
	BeanNameAutoProxyCreator standardDataWsClientBeanProxy() {
		// CHECKSTYLE:ON
		return getBeanNameAutoProxyCreator(
				new String[] { StandardDataWsClientImpl.BEAN_NAME },
				new String[] { "standardDataWsClientExceptionInterceptor", "standardDataWsClientPerformanceLogMethodInterceptor" });
	}

}
