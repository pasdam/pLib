/**
 * 
 */
package com.pasdam.utils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * {@link File} utility function
 * 
 * @author paco
 * @version 0.1
 */
public final class FileUtils {

	/**
	 * Private constructor: the user can only use static methods
	 */
	private FileUtils() {
	}

	/**
	 * This method reads the input file and returns its content as a string
	 * 
	 * @param file
	 *            the file to read
	 * @return the file content as a string
	 * @throws IOException
	 *             if an I/O error occurs while reading, the file does not
	 *             exist, is a directory rather than a regular file, or for some
	 *             other reason cannot be opened for reading.
	 */
	public static String fileToString(File file) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		StringBuilder builder = new StringBuilder();
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			builder.append(line);
		}
		bufferedReader.close();
		return builder.toString();
	}
}
