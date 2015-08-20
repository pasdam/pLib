package com.pasdam.utils.text;

import java.util.regex.Pattern;

/**
 * @author Paco
 * @version 1.1
 */
public class TextClean {
	
	private static final Pattern XML_TAG = Pattern.compile("\\s*<.*?>\\s*");
	
	public static String removeXMLTag(String string){
		return XML_TAG.matcher(string).replaceAll("");
	}
}
