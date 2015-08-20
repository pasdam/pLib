package com.pasdam.utils.web;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Paco
 * @version 1.0
 */
public class Url {
	
	private static final Pattern BASE_PATTERN = Pattern.compile("https?://(\\w*\\.)?\\w+\\.\\w+/?");
	
	public static String getBaseURL(String url) throws MalformedURLException{
		// check url validity
		new URL(url);
		// get base url
		Matcher matcher = BASE_PATTERN.matcher(url);
		if (matcher.find()) {
			String text = matcher.group();
			if (text.endsWith("/")) {
				return text;
			} else {
				return text + "/";
			}
		}
		return null;
	}
}
