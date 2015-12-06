package com.pasdam.utils.web.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * HTTP client utility class. <br>
 * See {@link #HttpClient(int, boolean, HeaderField[], long)} for parameters
 * description.
 * 
 * @author paco
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
	 * Creates the client with the specified parameters
	 * 
	 * @param timeout
	 *            the timeout value (in milliseconds) to be used (0 means
	 *            infinite timeout, negative values are ignored)
	 * @param followRedirect
	 *            if true the connection follows the redirect
	 * @param headerFields
	 *            array with all required http header fields
	 * @param ifModifiedSince
	 *            ifModifiedSince field of the http protocol
	 */
	private HttpClient(int timeout, boolean followRedirect,
			HeaderField[] headerFields, long ifModifiedSince) {
		this.timeout 		 = timeout;
		this.followRedirect  = followRedirect;
		this.headerFields 	 = headerFields;
		this.ifModifiedSince = ifModifiedSince;
	}
	
	/**
	 * Returns the <i>followRedirect</i> property of the client
	 * 
	 * @return the <i>followRedirect</i> property of the client
	 * 
	 * @see #HttpClient(int, boolean, HeaderField[], long)
	 */
	public boolean getFollowRedirect() {
		return followRedirect;
	}
	
	/**
	 * Sets the <i>followRedirect</i> parameter
	 * 
	 * @param followRedirect
	 *            the <i>followRedirect</i> value to set
	 *            
	 * @see #HttpClient(int, boolean, HeaderField[], long)
	 */
	public void setFollowRedirect(boolean followRedirect) {
		this.followRedirect = followRedirect;
	}
	
	/**
	 * Returns the header fields list
	 * 
	 * @return the header fields list
	 * 
	 * @see #HttpClient(int, boolean, HeaderField[], long)
	 */
	public HeaderField[] getHeaderFields() {
		return headerFields;
	}
	
	/**
	 * Sets the header fields to use for requests
	 * 
	 * @param headerFields
	 *            the header fields to set
	 * 
	 * @see #HttpClient(int, boolean, HeaderField[], long)
	 */
	public void setHeaderFields(HeaderField[] headerFields) {
		this.headerFields = headerFields;
	}
	
	/**
	 * Returns the <i>ifModifiedSince</i> parameter
	 * 
	 * @return the <i>ifModifiedSince</i> parameter
	 * 
	 * @see #HttpClient(int, boolean, HeaderField[], long)
	 */
	public long getIfModifiedSince() {
		return ifModifiedSince;
	}
	
	/**
	 * Set the value of the <i>ifModifiedSince</i> parameter
	 * 
	 * @param ifModifiedSince
	 *            the <i>ifModifiedSince</i> value to set
	 * @see #HttpClient(int, boolean, HeaderField[], long)
	 */
	public void setIfModifiedSince(long ifModifiedSince) {
		this.ifModifiedSince = ifModifiedSince;
	}
	
	/**
	 * Returns the timeout value
	 * 
	 * @return the timeout value
	 * 
	 * @see #HttpClient(int, boolean, HeaderField[], long)
	 */
	public int getTimeout() {
		return timeout;
	}
	
	/**
	 * Sets the timeout value
	 * 
	 * @param timeout
	 *            the timeout value to set
	 * 
	 * @see #HttpClient(int, boolean, HeaderField[], long)
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
	/**
	 * Performs an HTTP GET request
	 * 
	 * @param url
	 *            the url of the resource as a string
	 * @return the requested resource as a string
	 * @throws MalformedURLException
	 *             if no protocol is specified, or an unknown protocol is found,
	 *             or <i>url</i> is null.
	 * @throws IOException
	 *             if an I/O exception occurs.
	 */
	public String get(String url) throws MalformedURLException, IOException{
		return get(new URL(url));
	}
	
	/**
	 * Performs an HTTP GET request
	 * 
	 * @param url
	 *            the {@link URL} of the resource
	 * @return the requested resource as a string
	 * @throws IOException
	 *             if an I/O exception occurs.
	 */
	public String get(URL url) throws IOException {
		return get(url, timeout, followRedirect, headerFields, ifModifiedSince);
	}

	/**
	 * Performs an HTTP GET request
	 * 
	 * @param url
	 *            the {@link URL} of the resource
	 * @return the requested resource as a {@link Reader}
	 * @throws IOException
	 *             if an I/O exception occurs.
	 */
	public Reader getAsReader(URL url) throws IOException {
		return getAsReader(url, timeout, followRedirect, headerFields, ifModifiedSince);
	}

	/**
	 * Performs an HTTP GET request
	 * 
	 * @param url
	 *            the {@link URL} of the resource
	 * @param timeout
	 *            the timeout value (in milliseconds) to be used (0 means
	 *            infinite timeout, negative values are ignored)
	 * @param followRedirect
	 *            if true the connection follows the redirect
	 * @param headerFields
	 *            array with all required http header fields
	 * @return the requested resource as a string
	 * @throws IOException
	 *             if an I/O exception occurs, while opening connection
	 */
	public static String get(URL url, int timeout, boolean followRedirect, HeaderField[] headerFields) throws IOException {
		return get(url, timeout, followRedirect, headerFields, -1);
	}
	
	/**
	 * Performs an HTTP GET request
	 * 
	 * @param url
	 *            the {@link URL} of the resource
	 * @param timeout
	 *            the timeout value (in milliseconds) to be used (0 means
	 *            infinite timeout, negative values are ignored)
	 * @param followRedirect
	 *            if true the connection follows the redirect
	 * @param headerFields
	 *            array with all required http header fields
	 * @param ifModifiedSince
	 *            ifModifiedSince field of the http protocol
	 * @return the requested resource as a string
	 * @throws IOException
	 *             if an I/O exception occurs, while opening connection
	 */
	public static String get(URL url, int timeout, boolean followRedirect, HeaderField[] headerFields, long ifModifiedSince) throws IOException {
		BufferedReader in = new BufferedReader(getAsReader(url, timeout, followRedirect, headerFields, ifModifiedSince));
		String inputLine;
		StringBuilder builder = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			// Process each line.
			builder.append(inputLine);
		}
		in.close();
		return builder.toString();
	}

	/**
	 * Performs an HTTP GET request
	 * 
	 * @param url
	 *            the {@link URL} of the resource
	 * @param timeout
	 *            the timeout value (in milliseconds) to be used (0 means
	 *            infinite timeout, negative values are ignored)
	 * @param followRedirect
	 *            if true the connection follows the redirect
	 * @param headerFields
	 *            array with all required http header fields
	 * @param ifModifiedSince
	 *            ifModifiedSince field of the http protocol
	 * @return the requested resource as a {@link Reader}
	 * @throws IOException
	 *             if an I/O exception occurs, while opening connection
	 */
	public static Reader getAsReader(URL url, int timeout, boolean followRedirect, HeaderField[] headerFields, long ifModifiedSince) throws IOException {
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
		return new InputStreamReader(connection.getInputStream());
	}
}
