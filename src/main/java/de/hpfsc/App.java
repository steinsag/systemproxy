package de.hpfsc;

import sun.net.NetProperties;

import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Test application to see how default Java proxy selector behaves.
 *
 * @author Sebastian Stein (seb.kde@hpfsc.de)
 */
public class App
{
	public static final String HTTP_PROXY_HOST = "http.proxyHost";
	public static final String HTTP_PROXY_PORT = "http.proxyPort";
	public static final String HTTP_NON_PROXY_HOSTS = "http.nonProxyHosts";
	public static final String HTTP_PROXY_HOST_VALUE = "proxy.example.com";
	public static final String HTTP_PROXY_PORT_VALUE = "8080";
	public static final String HTTP_NON_PROXY_HOSTS_VALUE = "*.example.com";
	public static final String JAVA_NET_USE_SYSTEM_PROXIES = "java.net.useSystemProxies";
	public static final ProxySelector SYSTEM_PROXY_SELECTOR = ProxySelector.getDefault();

	private static URI testUriDirect;
	private static URI testUriThroughProxy;


	public static void main( String[] args ) throws URISyntaxException {
		testUriDirect = new URI("http://host.example.com/somepath/path");
		testUriThroughProxy = new URI("http://stackoverflow.com/");

		outputTestResults("Initial state");

		System.setProperty(HTTP_PROXY_HOST, HTTP_PROXY_HOST_VALUE);
		System.setProperty(HTTP_PROXY_PORT, HTTP_PROXY_PORT_VALUE);
		System.setProperty(HTTP_NON_PROXY_HOSTS, HTTP_NON_PROXY_HOSTS_VALUE);

		outputTestResults("State after setting system settings");
    }

	private static void outputTestResults(final String heading) {
		outputSystemSettings(heading);
		outputSelectProxy(testUriDirect);
		outputSelectProxy(testUriThroughProxy);
	}

	private static void outputSelectProxy(final URI testUri) {
		System.out.println("access to " + testUri + ": " + SYSTEM_PROXY_SELECTOR.select(testUri).toString());
	}

	private static void outputSystemSettings(final String heading) {
		System.out.println(">>>>" + heading + "<<<<");

		System.out.println(getPropertyValueAsFormattedString(HTTP_PROXY_HOST));
		System.out.println(getPropertyValueAsFormattedString(HTTP_PROXY_PORT));
		System.out.println(getPropertyValueAsFormattedString(HTTP_NON_PROXY_HOSTS));
		System.out.println(getPropertyValueAsFormattedString(JAVA_NET_USE_SYSTEM_PROXIES));

		System.out.println("");
	}

	private static String getPropertyValueAsFormattedString(final String propertyKey) {
		return propertyKey + ": " + NetProperties.get(propertyKey);
	}
}
