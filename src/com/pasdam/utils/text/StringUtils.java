package com.pasdam.utils.text;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * String utility class
 * 
 * @author paco
 * @version 1.0
 */
public class StringUtils {
	
	/**
	 * @param string
	 * @return
	 */
	public static InputStream stringToInputStream(String string) {
		try {
			return new ByteArrayInputStream(string.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Extract common substring from input strings, both of same size
	 * 
	 * @param str1
	 *            first string
	 * @param str2
	 *            second string
	 * @return the substring in common
	 */
	public static String commonSubstrings(String str1, String str2){
		if (str1.length() == str2.length()) {
			StringBuilder builder = new StringBuilder(str1.length());
			for (int i = 0; i < str1.length(); i++) {
				if (str1.charAt(i) == str2.charAt(i)) {
					builder.append(str1.charAt(i));
				}
			}
			return builder.toString();
		}
		return null;
	}
	
	/**
	 * Extract common prefix from two strings
	 * 
	 * @param first
	 *            first string
	 * @param second
	 *            second string
	 * @return the common prefix of null if at least one of the input string is
	 *         null
	 */
	public static String commonPrefix(String first, String second) {
		if (first != null && second != null) {
			if (first.length() > second.length()) {
				// swap values, in order to make sure that the 
				// longest string is the second one
				String swapString = first;
				first = second;
				second = swapString;
			}
			for (int i = 0; i < first.length(); i++) {
				if (first.charAt(i) != second.charAt(i)) {
					return first.substring(0, i);
				}
			}
			// the first string is the common prefix, because the for didn't
			// return before the end of the string
			return first;
		} else {
			return null;
		}
	}
}
