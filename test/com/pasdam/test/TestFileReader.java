package com.pasdam.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class allows to read test files, that must have .tst extension and in
 * which is possible to insert comments (prefixing a line with "#"), ignored
 * during reading phase
 * 
 * @author paco
 * @version 0.1
 */
public class TestFileReader {

	private static final String EXTENSION = ".tst";
	private static final String COMMENT_PREFIX = "#";

	/**
	 * {@link BufferedReader} used to read the test data 
	 */
	private final BufferedReader reader;

	/**
	 * Creates a {@link TestFileReader} for the specified file.
	 * 
	 * @param file
	 *            file containing test data; it must have .tst extension
	 * @throws IllegalArgumentException
	 *             if <i>file</i> is null or the file extension isn't .tst
	 * @throws FileNotFoundException
	 *             if the file does not exist, is a directory rather than a
	 *             regular file, or for some other reason cannot be opened for
	 *             reading.
	 */
	public TestFileReader(File file) throws IllegalArgumentException, FileNotFoundException {
		if (file != null) {
			if (file.getName().endsWith(EXTENSION)) {
				this.reader = new BufferedReader(new FileReader(file));

			} else {
				throw new IllegalArgumentException("Invalid test file: it must be a .tst file");
			}
		} else {
			throw new IllegalArgumentException("Invalid test file: it must be not null");
		}
	}

	/**
	 * Reads the next available line of the data file, ignoring eventual comments and empty lines
	 *  
	 * @return the next available line of the data file
	 */
	public String readLine() {
		try {
			String line = this.reader.readLine();

			// ignore comments and empty lines
			while (line != null && (line.startsWith(COMMENT_PREFIX) || line.trim().length() == 0)) {
				line = this.reader.readLine();
			}

			return line;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
