package com.pasdam.utils.web.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Paco
 * @version 2.0
 */
public class HttpClient {
	
	private int timeout;
	private boolean followRedirect;
	private HeaderField[] headerFields;
	private long ifModifiedSince;
	
	/**
	 * Creates the client with default parameters
	 */
	public HttpClient() {
		this(-1, false, null, -1);
	}
	
	/**
	 * @param timeout - an int that specifies the timeout value to be used in milliseconds (0 means infinite timeout, negative values are ignored)
	 * @param followRedirect if true the connection follows the redirect
	 * @param headerFields array with all required http header fields
	 * @param ifModifiedSince ifModifiedSince field of the http protocol
	 */
	private HttpClient(int timeout, boolean followRedirect,
			HeaderField[] headerFields, long ifModifiedSince) {
		this.timeout = timeout;
		this.followRedirect = followRedirect;
		this.headerFields = headerFields;
		this.ifModifiedSince = ifModifiedSince;
	}
	
	/**
	 * @return the followRedirect
	 */
	public boolean getFollowRedirect() {
		return followRedirect;
	}
	
	/**
	 * @param followRedirect the followRedirect to set
	 */
	public void setFollowRedirect(boolean followRedirect) {
		this.followRedirect = followRedirect;
	}
	
	/**
	 * @return the headerFields
	 */
	public HeaderField[] getHeaderFields() {
		return headerFields;
	}
	
	/**
	 * @param headerFields the headerFields to set
	 */
	public void setHeaderFields(HeaderField[] headerFields) {
		this.headerFields = headerFields;
	}
	
	/**
	 * @return the ifModifiedSince
	 */
	public long getIfModifiedSince() {
		return ifModifiedSince;
	}
	
	/**
	 * @param ifModifiedSince the ifModifiedSince to set
	 */
	public void setIfModifiedSince(long ifModifiedSince) {
		this.ifModifiedSince = ifModifiedSince;
	}
	
	/**
	 * @return the timeout
	 */
	public int getTimeout() {
		return timeout;
	}
	
	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
	/**
	 * @param url the url of the resource
	 * @return the requested resource as a string
	 * @throws MalformedURLException if no protocol is specified, or an unknown protocol is found, or url is null.
	 * @throws IOException if an I/O exception occurs.
	 */
	public String get(String url) throws MalformedURLException, IOException{
		return get(new URL(url));
	}

	/**
	 * @param url the url of the resource
	 * @return the requested resource as a string
	 * @throws IOException if an I/O exception occurs.
	 */
	public String get(URL url) throws IOException {
		return get(url, timeout, followRedirect, headerFields, ifModifiedSince);
	}

	/**
	 * @param url the url of the resource
	 * @param timeout - an int that specifies the timeout value to be used in milliseconds (0 means infinite timeout, negative values are ignored)
	 * @param followRedirect if true the connection follows the redirect
	 * @param headerFields array with all required http header fields
	 * @return the requested resource as a string
	 * @throws IOException if an I/O exception occurs, while opening connection
	 */
	public static String get(URL url, int timeout, boolean followRedirect, HeaderField[] headerFields) throws IOException {
		return get(url, timeout, followRedirect, headerFields, -1);
	}

	/**
	 * @param url the url of the resource
	 * @param timeout - an int that specifies the timeout value to be used in milliseconds (0 means infinite timeout, negative values are ignored)
	 * @param followRedirect if true the connection follows the redirect
	 * @param headerFields array with all required http header fields
	 * @param ifModifiedSince ifModifiedSince field of the http protocol
	 * @return the requested resource as a string
	 * @throws IOException if an I/O exception occurs, while opening connection
	 */
	public static String get(URL url, int timeout, boolean followRedirect, HeaderField[] headerFields, long ifModifiedSince) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		if (timeout >= 0) {
			connection.setConnectTimeout(timeout);
			connection.setReadTimeout(timeout);
		}
		if (ifModifiedSince > 0) {
			connection.setIfModifiedSince(ifModifiedSince);
		}
		connection.setInstanceFollowRedirects(followRedirect);
		if (headerFields != null) {
			for (int i = 0; i < headerFields.length; i++) {
				connection.setRequestProperty(headerFields[i].name.headerName,
						headerFields[i].value);
			}
		}
		BufferedReader in = new BufferedReader(
				new InputStreamReader(
						connection.getInputStream()));
		String inputLine;
		StringBuilder builder = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			// Process each line.
			builder.append(inputLine);
		}
		in.close();
		return builder.toString();
	}
}
