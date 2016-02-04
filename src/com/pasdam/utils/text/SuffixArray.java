package com.pasdam.utils.text;

import java.util.Arrays;
import java.util.Comparator;

/**
 * This class contains utility methods to create and manage suffix arrays
 * 
 * @author paco
 * @version 0.1
 */
public class SuffixArray {

	/**
	 * Creates the suffix array related to the input string
	 * 
	 * @param string
	 *            string from which create the suffix array
	 * @return the suffix array created from <i>string</i>
	 * @throws IllegalArgumentException
	 *             if the input parameter is null
	 */
	public static Integer[] stringToSuffixArray(String string) throws IllegalArgumentException {
		if (string != null) {
			Integer[] suffixArray = new Integer[string.length()];
			
			// array initialization
			for (int i = 0; i < suffixArray.length; i++) {
				suffixArray[i] = i;
			}
			
			// array sorting
			Arrays.sort(suffixArray, new SuffixArrayComparator(string));
			
			return suffixArray;
			
		} else {
			throw new IllegalArgumentException("Input string cannot be null");
		}
	}
	
	/**
	 * Class used to compare substrings while sorting suffix array
	 * 
	 * @author paco
	 * @version 0.1
	 */
	private static class SuffixArrayComparator implements Comparator<Integer> {
		
		/**
		 * Input string from which the suffix array is created
		 */
		private final String string;
		
		/**
		 * Creates a {@link SuffixArrayComparator} with the specified
		 * <i>string</i>
		 * 
		 * @param string
		 *            string from which the suffix array is created, used to
		 *            compare substrings
		 */
		public SuffixArrayComparator(String string) {
			this.string = string;
		}

		@Override
		public int compare(Integer index0, Integer index1) {
			// compare substrings (starting from input indexes)
			return this.string.substring(index0).compareTo(this.string.substring(index1));
		}
	}
}
