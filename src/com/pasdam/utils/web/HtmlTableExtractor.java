package com.pasdam.utils.web;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class allows to extract the content of an HTML table
 * 
 * @author paco
 * @version 0.1
 */
public class HtmlTableExtractor {

	/**
	 * Default column delimiter
	 */
	public static final String DEFAULT_DELIMITER_COLUMN = "###";

	/**
	 * Default attributes delimiter
	 */
	public static final String DEFAULT_DELIMITER_ATTRS = "_-_";

	private static final Pattern PATTERN_NEW_LINE = Pattern.compile("\r?\n");
	private static final Pattern PATTERN_TABLE = Pattern.compile("<table[ >].*?</table>");
	private static final Pattern PATTERN_USEFULL_ATTRS = Pattern.compile(" ((src)|(href))=[\"']");
	private static final Pattern PATTERN_USEFULL_ATTRS_START = Pattern.compile("<[^<]+?" + DEFAULT_DELIMITER_ATTRS);
	private static final Pattern PATTERN_USEFULL_ATTRS_END = Pattern.compile("[\"'][^>]*>");
	private static final Pattern PATTERN_ALL_EMPTY_TAGS = Pattern.compile("</?\\w+>");
	private static final Pattern PATTERN_ALL_ATTRS = Pattern.compile(" [\\w-]+=(\"|').*?\\1");
	private static final Pattern PATTERN_TABLE_ROW_END_TAG = Pattern.compile("\\s*</tr>");
	private static final Pattern PATTERN_TABLE_COLUMN_END_TAG = Pattern.compile("\\s*</td>");

	private final String delimiterColumns;
	private final String delimiterAttributes;

	/**
	 * Create an {@link HtmlTableExtractor} with default values as delimiters
	 * 
	 * @see {@link #DEFAULT_DELIMITER_COLUMN}, {@link #DEFAULT_DELIMITER_ATTRS}
	 */
	public HtmlTableExtractor() {
		this(DEFAULT_DELIMITER_COLUMN, DEFAULT_DELIMITER_ATTRS);
	}

	/**
	 * Creates an {@link HtmlTableExtractor} with the specified delimiters
	 * 
	 * @param columnDelimiter
	 *            delimiter used to separate columns
	 * @param attributesDelimiter
	 *            delimiter used to separate attributes
	 */
	public HtmlTableExtractor(String columnDelimiter, String attributesDelimiter) {
		this.delimiterColumns = columnDelimiter;
		this.delimiterAttributes = attributesDelimiter;
	}

	/**
	 * Extract all tables content, and returns a list containing an array for
	 * each table, in which each row is an element: columns and attributes are
	 * divided by the delimiters specified in the constructor
	 * 
	 * @param inputText
	 *            html of the page
	 * @return a list containing an array for each table, in which each row is
	 *         an element
	 * @see {@link #HtmlTableExtractor()},
	 *      {@link #HtmlTableExtractor(String, String)},
	 *      {@link #getDelimiterColumns()}, {@link #getDelimiterAttributes()}
	 */
	public List<String[]> extract(String inputText) {
		// remove line endings and get a tables matcher
		Matcher tableMatcher = PATTERN_TABLE.matcher(PATTERN_NEW_LINE.matcher(inputText).replaceAll(""));
		List<String[]> tables = new ArrayList<String[]>();

		while (tableMatcher.find()) {
			String tableHtml = tableMatcher.group(); // the current table
														// matched

			// replace usefull attrs prefix
			tableHtml = PATTERN_USEFULL_ATTRS.matcher(tableHtml).replaceAll(this.delimiterAttributes);
			// remove useless attributes
			tableHtml = PATTERN_ALL_ATTRS.matcher(tableHtml).replaceAll("");
			// replace first part of the tag containing usefull attribute
			tableHtml = PATTERN_USEFULL_ATTRS_START.matcher(tableHtml).replaceAll(this.delimiterAttributes);
			// replace last part of the tag containing usefull attribute
			tableHtml = PATTERN_USEFULL_ATTRS_END.matcher(tableHtml).replaceAll(this.delimiterAttributes);
			// split table rows on different lines
			tableHtml = PATTERN_TABLE_ROW_END_TAG.matcher(tableHtml).replaceAll("\n");
			// replace column end tag with field separator
			tableHtml = PATTERN_TABLE_COLUMN_END_TAG.matcher(tableHtml).replaceAll(this.delimiterColumns);
			// remove all other tags
			tableHtml = PATTERN_ALL_EMPTY_TAGS.matcher(tableHtml).replaceAll("");

			// add the current table (each row is a element of the array) to the
			// tables list
			tables.add(PATTERN_NEW_LINE.split(tableHtml));
		}
		return tables;
	}

	/**
	 * Returns the delimiter used to separate columns
	 * 
	 * @return the delimiter used to separate columns
	 */
	public String getDelimiterColumns() {
		return delimiterColumns;
	}

	/**
	 * Returns the delimiter used to separate attributes
	 * 
	 * @return the delimiter used to separate columns
	 */
	public String getDelimiterAttributes() {
		return delimiterAttributes;
	}
}
