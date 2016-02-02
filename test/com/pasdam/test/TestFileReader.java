package com.pasdam.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * This class allows to read test files, that must have .tst extension and in
 * which is possible to insert comments (prefixing a line with "#"), ignored
 * during reading phase
 * 
 * @author paco
 * @version 0.1
 */
public class TestFileReader {

	/**
	 * Extension of the test files 
	 */
	public static final String EXTENSION = "tst";
	
	/**
	 * Default values separator in the data file
	 */
	public static final String DEFAULT_SEPARATOR = "||||||||";
	
	/**
	 * Default split pattern
	 */
	private static final Pattern DEFAULT_SPLIT_PATTERN = Pattern.compile(Pattern.quote(DEFAULT_SEPARATOR));
	
	/**
	 * Prefix of the comment line in the test data files
	 */
	private static final String COMMENT_PREFIX = "#";

	/**
	 * {@link BufferedReader} used to read the test data 
	 */
	private final BufferedReader reader;
	
	/**
	 * Pattern used to split data file's lines
	 */
	private final Pattern splitPattern;

	/**
	 * Creates a {@link TestFileReader} for the specified file.
	 * {@link #DEFAULT_SEPARATOR} will be used as separator to split lines if
	 * needed
	 * 
	 * @param file
	 *            file containing test data; it must have .tst extension
	 * @param separatorPattern
	 *            pattern used to split read lines
	 * @throws IllegalArgumentException
	 *             if the file extension isn't equals to #EXTEN
	 * @throws FileNotFoundException
	 *             if the file does not exist, is a directory rather than a
	 *             regular file, or for some other reason cannot be opened for
	 *             reading.
	 */
	public TestFileReader(File file, Pattern separatorPattern) throws IllegalArgumentException, FileNotFoundException {
		assert file != null : "Input file must be not null";
		assert separatorPattern != null : "Input pattern must be not null";
		
		if (file.getName().endsWith("." + EXTENSION)) {
			this.reader = new BufferedReader(new FileReader(file));
			this.splitPattern = separatorPattern;
			
		} else {
			throw new IllegalArgumentException("Invalid test file: it must be a .tst file");
		}
	}
	
	/**
	 * Creates a {@link TestFileReader} for the specified file.
	 * 
	 * @param file
	 *            file containing test data; it must have .tst extension
	 * @throws IllegalArgumentException
	 *             if the file extension isn't equals to #EXTEN
	 * @throws FileNotFoundException
	 *             if the file does not exist, is a directory rather than a
	 *             regular file, or for some other reason cannot be opened for
	 *             reading.
	 */
	public TestFileReader(File file) throws IllegalArgumentException, FileNotFoundException {
		this(file, DEFAULT_SPLIT_PATTERN);
	}

	/**
	 * Reads the next available line of the data file, ignoring eventual
	 * comments and empty lines
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
		}
		return null;
	}
	
	/**
	 * Reads next line from file and split it with the specified pattern.
	 * 
	 * @return The array of strings computed by splitting the line read around
	 *         matches of this reader's {@link Pattern}, or an empty array if
	 *         there are no other lines in the file to read
	 * @see #TestFileReader(File, Pattern)
	 */
	public String[] readAndSplitLine() {
		String line = readLine();
		
		if (line != null) {
			return this.splitPattern.split(line);
			
		} else {
			return new String[]{};
		}
	}
}
