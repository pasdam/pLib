package com.pasdam.utility.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownServiceException;

/**
 * This class is allows to download a web page
 * 
 * @author paco
 * @version 0.1
 */
public class WebPageDownloader {

	/**
	 * Download the page identified by the url and returns it as a string.
	 * 
	 * @param url
	 *            url of the page to download
	 * @return the web page as a string
	 * @throws UnknownServiceException
	 *             - if the protocol does not support input.
	 * @throws IOException
	 *             - if an I/O exception occurs.
	 */
	public static String download(URL url) throws UnknownServiceException, IOException {
		URLConnection connection = url.openConnection();
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder response = new StringBuilder();
		String inputLine;
		while ((inputLine = reader.readLine()) != null) {
			response.append(inputLine);
		}
		reader.close();
		return response.toString();
	}
}
