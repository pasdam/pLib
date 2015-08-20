package com.pasdam.utils.web;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pasdam.utils.web.http.HttpClient;


/**
 * @author Paco
 * @version 1.0
 */
public class LinkExtractor {
	
	/** URL regex patter */
	private static final Pattern URL_PATTERN = Pattern.compile("https?://[a-zA-Z0-9\\.\\-]+\\.[a-zA-Z]+/\\w+[^\\s\"<>()']+");
//	private String bodyContent;
	
	/**
	 * @param pageUrl the url of the page
	 * @return a list of link found in the page, or null if no links were found
	 * @throws IOException if an I/O exception occurs while downloading page.
	 */
	public static List<String> extractLink(URL pageUrl) throws IOException{
		return extractLink(pageUrl.toString(), URL_PATTERN);
	}
	
	/**
	 * @param pageUrl the url of the page
	 * @return a list of link found in the page, empty if no links were found
	 */
	public static List<String> extractLink(String url, Pattern pattern){
		if (url != null && pattern != null) {
			try {
				List<String> list = new ArrayList<String>();
				new HttpClient();
				Matcher matcher = pattern.matcher(new HttpClient().get(url));
				while (matcher.find()) {
					list.add(matcher.group());
				}
				return list;
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static List<String> extractLink(String url){
		try {
			new HttpClient();
			return extractLink(new HttpClient().get(url), URL_PATTERN);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
