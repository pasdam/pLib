package com.pasdam.utils.net;

/**
 * Utility class for network proxy
 * 
 * @author paco
 * @version 0.1
 */
public class ProxyUtils {
	
	/**
	 * Sets the system proxy variables
	 * 
	 * @param httpHost host of the http proxy
	 * @param httpPort port of the http proxy
	 * @param httpsHost host of the https proxy
	 * @param httpsPort port of the https proxy
	 */
	public static void setSystemProxy(String httpHost, int httpPort, String httpsHost, int httpsPort) {
		System.setProperty("http.proxyHost", httpHost);
		System.setProperty("http.proxyPort", ""+httpPort);
		System.setProperty("https.proxyHost", httpsHost);
		System.setProperty("https.proxyPort", ""+httpsPort);
	}

	/**
	 * Sets the system proxy variables: it will set both http and https proxy
	 * with the same address
	 * 
	 * @param host host of the http/https proxy
	 * @param port port of the http/https proxy
	 */
	public static void setSystemProxy(String host, int port) {
		setSystemProxy(host, port, host, port);
	}
}
