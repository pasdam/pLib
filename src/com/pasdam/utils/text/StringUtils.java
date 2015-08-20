package com.pasdam.utils.text;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * @author Paco
 * @version 1.0
 */
public class StringUtils {
	
	public static InputStream stringToInputStream(String string) {
		try {
			return new ByteArrayInputStream(string.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
