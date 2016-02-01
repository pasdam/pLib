/**
 * 
 */
package com.pasdam.utils.text;

/**
 * Class with utility methods for char and {@link Character} types
 * 
 * @author paco
 * @version 0.1
 */
public class CharUtils {
	
	/**
	 * Returns true if <i>character</i> is a latin digit, in other words if it
	 * is between '0' and '9', false otherwise
	 * 
	 * @param character
	 *            character to check
	 * @return true if character is a latin digit, false otherwise
	 */
	public static boolean isLatinDigit(char character) {
		return (character >= '0' && character <= '9');
	}
}
