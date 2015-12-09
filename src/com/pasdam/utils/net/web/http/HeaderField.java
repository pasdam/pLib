package com.pasdam.utils.net.web.http;

/**
 * @author Paco
 * @version 1.0
 */
public class HeaderField {
	
	public final HeaderFieldName name;
	public final String value;

	public HeaderField(HeaderFieldName name, String value) {
		this.name = name;
		this.value = value;
	}
}
