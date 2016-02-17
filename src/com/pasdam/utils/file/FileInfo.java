package com.pasdam.utils.file;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class used to retrieve informations about a {@link File}
 * 
 * @author paco
 * @version 0.1
 */
public class FileInfo {
	
	/**
	 * Unit multiplier
	 */
	private static final int UNIT_MULTIPLIER = 1000;
	
	/**
	 * Indicates an automatically choose unit
	 */
	public static final int UNIT_AUTO     = 0;
	
	/**
	 * Indicates the byte unit
	 */
	public static final int UNIT_BYTE 	  = 1;
	
	/**
	 * Indicates the kilobyte unit
	 */
	public static final int UNIT_KILOBYTE = UNIT_BYTE 	  * UNIT_MULTIPLIER;
	
	/**
	 * Indicates the megabyte unit
	 */
	public static final int UNIT_MEGABYTE = UNIT_KILOBYTE * UNIT_MULTIPLIER;
	
	/**
	 * Indicates the gigabyte unit
	 */
	public static final int UNIT_GIGABYTE = UNIT_MEGABYTE * UNIT_MULTIPLIER;
	
	/**
	 * Pattern used to parse the filename parts
	 * TODO: add support for absolute path
	 */
	private static final Pattern PATTERN_NAME_PARTS = Pattern.compile("^(.+?)(\\.(\\w*)$|$)");
	
	/**
	 * Index of the name group
	 */
	private static final int GROUP_NAME = 1;
	
	/**
	 * Index of the extension group
	 */
	private static final int GROUP_EXTENSION = 3;
	
	/**
	 * Pattern used to parse formatted file size
	 */
	private static final Pattern PATTERN_FORMATTED_SIZE = Pattern.compile("(\\d+(\\.\\d+)?)\\s*([kKmMgG]?[bB])");
	
	/**
	 * Index of the size group
	 */
	private static final int GROUP_SIZE = 1;
	
	/**
	 * Index of the unit group
	 */
	private static final int GROUP_UNIT = 3;
	
	/**
	 * Original file
	 */
	private File file;
	
	/**
	 * File name
	 */
	private String name;
	
	/**
	 * File extension
	 */
	private String extension;
	
	/**
	 * Private constructor: prevents direct instantiation
	 * 
	 * @param file
	 *            original file from which retrieve informations
	 * 
	 * @throws NullPointerException
	 *             if the parameter is null
	 */
	private FileInfo(File file) throws IllegalArgumentException {
		assert file != null : "Input file cannot be null";
		
		// parse file parts
		Matcher matcher = PATTERN_NAME_PARTS.matcher(file.getName());
		if (matcher.find()) {
			this.name 		= matcher.group(GROUP_NAME);
			this.extension 	= matcher.group(GROUP_EXTENSION);
		}
		
		this.file = file;
	}
	
	/**
	 * This method parse the full filename and returns the name (without
	 * extension).<br />
	 * It currently doesn't support file path, so please specify only the
	 * filename (without parent directories)
	 * 
	 * @param filename
	 *            filename to parse
	 * @return the name of the file, or an empty string if no name is found
	 * 
	 * @throws NullPointerException
	 *             if the parameter is null
	 */
	public static String parseName(String filename) {
		assert filename != null : "Input filename cannot be null";
		
		Matcher matcher = PATTERN_NAME_PARTS.matcher(filename);
		if (matcher.find()) {
			return matcher.group(GROUP_NAME);
		}
		return "";
	}

	/**
	 * This method parse the full filename and returns the extension.<br />
	 * It currently doesn't support file path, so please specify only the
	 * filename (without parent directories)
	 * 
	 * @param filename
	 *            filename to parse
	 * @return the extension of the file, or an empty string if file has no
	 *         extension
	 *         
	 * @throws NullPointerException
	 *             if the parameter is null
	 */
	public static String parseExtension(String filename) {
		assert filename != null : "Input filename cannot be null";

		Matcher matcher = PATTERN_NAME_PARTS.matcher(filename);
		if (matcher.find()) {
			String extension = matcher.group(GROUP_EXTENSION);
			return extension != null ? extension : "";
		}
		return "";
	}
	
	/**
	 * Returns the file to which the info refer
	 * 
	 * @return the file to which the info refer
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Returns the name of the file
	 * 
	 * @return the name of the file
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the extension of the file
	 * 
	 * @return the extension of the file
	 */
	public String getExtension() {
		return extension;
	}
	
	/**
	 * This method parse the input file
	 * 
	 * @param file
	 *            original file from which retrieve informations
	 * @return the {@link FileInfo} related to the input {@link File}
	 * 
	 * @throws NullPointerException
	 *             if the parameter is null
	 */
	public static FileInfo parseFileInfo(File file) throws IllegalArgumentException {
		return new FileInfo(file);
	}
	
	/**
	 * This method format the input size into a human-readable string.<br />
	 * Use this class static field to specify unit parameters.
	 * 
	 * @param size
	 *            size to format
	 * @param inputUnit
	 *            unit of the input size; {@link #UNIT_AUTO} cannot be used
	 * @param resultUnit
	 *            unit of the input string; use {@link #UNIT_AUTO} to
	 *            automatically choose the output size unit
	 * @return the formatted size
	 * @throws IllegalArgumentException
	 *             if inputUnit or resultUnit have an invalid value
	 */
	public static String formatSize(long size, int inputUnit, int resultUnit) throws IllegalArgumentException {
		if (size >= 0) {
			String suffix = null;
			
			switch (resultUnit) {
				case UNIT_BYTE:
					suffix = " B";
					break;
				
				case UNIT_KILOBYTE:
					suffix = " KB";
					break;
				
				case UNIT_MEGABYTE:
					suffix = " MB";
					break;
				
				case UNIT_GIGABYTE:
					suffix = " GB";
					break;
				
				case UNIT_AUTO:
				{
					double dim;
					double prevDim = ((double) size) * inputUnit / inputUnit;
					NumberFormat formatter = new DecimalFormat("#.##");
					
					for (int i = inputUnit; i <= UNIT_GIGABYTE; i = i * UNIT_MULTIPLIER) {
						dim = ((double) size) * inputUnit / (i * UNIT_MULTIPLIER);
						
						if (dim < 1) {
							switch (i) {
							case UNIT_BYTE:
								return formatter.format(prevDim) + " B";

							case UNIT_KILOBYTE:
								return formatter.format(prevDim) + " KB";

							case UNIT_MEGABYTE:
								return formatter.format(prevDim) + " MB";

							case UNIT_GIGABYTE:
								return formatter.format(prevDim) + " GB";

							default:
								// error
								throw new IllegalArgumentException("Invalid inputUnit: " + inputUnit);
							}
						}
						
						prevDim = dim;
					}
					
					// this code is reached only if inputUnit is invalid 
					throw new IllegalArgumentException("Invalid inputUnit: " + inputUnit);
				}
					
				default:
					throw new IllegalArgumentException("Invalid resultUnit: " + resultUnit);
			}
			
			// TODO make pattern customizable
			return new DecimalFormat("#.##").format(((double) size) * inputUnit / resultUnit) + suffix;

		} else {
			return null;
		}
	}
	
	/**
	 * Parse the input string and return the size in byte. The input string must
	 * have two parts, optionally separated by whitespaces:<br />
	 * <ul>
	 * <li><b>decimal number</b>, to indicate the size (both dot and comma can
	 * be used as decimal separator)</li>
	 * <li><b>unit</b>, as <i>B</i>, <i>KB</i>, <i>MB</i>, <i>GB</i> (they can be
	 * also lowercase)</li>
	 * </ul>
	 * 
	 * @param formattedSize
	 *            string to parse
	 * @return the corresponding size in byte
	 * 
	 * @throws NullPointerException
	 *             if the parameter is null
	 * @throws IllegalArgumentException
	 *             if size isn't well formatted
	 */
	public static long parseSize(String formattedSize) throws NullPointerException, IllegalArgumentException {
		formattedSize = formattedSize.trim().replace(",", ".");
		
		Matcher matcher = PATTERN_FORMATTED_SIZE.matcher(formattedSize);
		if (matcher.find()) {
			
			String unit = matcher.group(GROUP_UNIT).toLowerCase();
			double size = Double.parseDouble(matcher.group(GROUP_SIZE));
			
			if (unit.equals("b")) {
				return (long) size;
				
			} else if (unit.equals("kb")) {
				return (long) (size * UNIT_KILOBYTE);
				
			} else if (unit.equals("mb")) {
				return (long) (size * UNIT_MEGABYTE);
				
			} else if (unit.equals("gb")) {
				return (long) (size * UNIT_GIGABYTE);
			}
		}
		
		throw new IllegalArgumentException("Invalid input format: " + formattedSize);
	}
}
