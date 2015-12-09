package com.pasdam.utils.net.web.http;

/**
 * @author Paco
 * @version 1.0
 */
public enum HeaderFieldName {

	ACCEPT("Accept"),
	ACCEPT_ENCODING("Accept-Encoding"),
	CONNECTION("Connection"),
	COOKIE("Cookie"),
	REFER("Referer"),
	USER_AGENT("User-Agent"),
	X_REQUESTED_WITH("X-Requested-With");
	
	public final String headerName;
	
	private HeaderFieldName(String headerName) {
		this.headerName = headerName;
	}
}
