package com.pasdam.utils.text;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that can be used to extract regex pattern from strings.<br>
 * If no {@link PatternHandler} is specified, it is used a default handler that
 * store all detected patterns in a {@link List}.
 * 
 * @author Paco
 * @version 2.0
 */
public class PatternExtractor {
	
	private PatternHandler patternHandler;
	private Pattern pattern;
	
	/**
	 * Default constructor, <i>pattern</i> must be specified with related
	 * method, {@link #setPattern(Pattern)}.
	 */
	public PatternExtractor() {
	}
	
	/**
	 * Constructs the extractor for the specified {@link Pattern}
	 * 
	 * @param pattern
	 *            pattern to be used for the extracting process
	 */
	public PatternExtractor(Pattern pattern) {
		this.pattern = pattern;
	}

	/**
	 * Constructs the extractor with the specified {@link Pattern} and
	 * {@link PatternHandler}
	 * 
	 * @param pattern
	 *            pattern to be used for the extracting process
	 * @param patternHandler
	 *            handler to notify when a new pattern is detected during
	 *            extraction process
	 */
	public PatternExtractor(Pattern pattern, PatternHandler patternHandler) {
		this.pattern = pattern;
		this.patternHandler = patternHandler;
	}
	
	/**
	 * Returns the {@link PatternHandler}
	 * 
	 * @return the {@link PatternHandler}
	 */
	public PatternHandler getPatternHandler() {
		return patternHandler;
	}

	/**
	 * Sets the {@link PatternHandler} to use for the extraction process
	 * 
	 * @param patternHandler
	 *            {@link PatternHandler} to use in the extraction process
	 */
	public void setPatternHandler(PatternHandler patternHandler) {
		this.patternHandler = patternHandler;
	}

	/**
	 * Returns the {@link Pattern} used for the extraction process
	 * 
	 * @return the {@link Pattern} used
	 */
	public Pattern getPattern() {
		return pattern;
	}

	/**
	 * Sets the pattern to match during the extraction process
	 * 
	 * @param pattern
	 *            to match during the extraction process
	 */
	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}
	
	/**
	 * This methods perform the extraction process
	 * 
	 * @param text
	 *            from which extract the patterns
	 * @return the list of all patterns stored by the {@link PatternHandler}
	 * @throws IllegalStateException
	 *             if the pattern is null
	 */
	public List<String> extractPattern(String text) throws IllegalStateException {
		if (this.pattern != null) {
			if (this.patternHandler != null) {
				return extractPattern(text, this.pattern, this.patternHandler);
			} else {
				// use default handler
				return extractPattern(text, this.pattern, new DefaultHandler());
			}
		} else {
			throw new IllegalStateException("Pattern cannot be null, please set it before call extractPattern method");
		}
	}
	
	/**
	 * This method perform the extraction process with the specified
	 * {@link Pattern} and {@link PatternHandler}
	 * 
	 * @param text
	 *            from which extract the patterns
	 * @param pattern
	 *            the pattern to match
	 * @param patternHandler
	 *            handler to notify when a new pattern is detected
	 * @return the list of all patterns stored by the {@link PatternHandler}
	 * @throws NullPointerException
	 *             is one of the parameters is null
	 */
	public static List<String> extractPattern(String text, Pattern pattern, PatternHandler patternHandler) throws NullPointerException {
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			// notify the handler
			patternHandler.patternDetected(matcher);
		}
		return patternHandler.detectedPatterns();
	}
	
	/**
	 * Handler interface, used to get notified when a new pattern is detected
	 * during extraction process
	 * 
	 * @author paco
	 * @version 0.1
	 */
	public static interface PatternHandler {
		
		/**
		 * Called by {@link PatternExtractor} when a new pattern is detected
		 * 
		 * @param pattern detected pattern
		 * @param startIndex index of the first character matched
		 * @param endIndex the offset after the last character matched.
		 */
		public void patternDetected(Matcher matcher);
		
		/**
		 * Returns the list of all detected patterns
		 * @return the list af all detected patterns
		 */
		public List<String> detectedPatterns();
	}
	
	/**
	 * Default implementation of a {@link PatternHandler}, it records all
	 * detected patterns in a list
	 * 
	 * @author paco
	 * @version 0.1
	 */
	private class DefaultHandler implements PatternHandler {

		private List<String> patterns = new ArrayList<String>();
		
		@Override
		public void patternDetected(Matcher matcher) {
			this.patterns.add(matcher.group());
		}

		@Override
		public List<String> detectedPatterns() {
			return this.patterns;
		}
	}
}
