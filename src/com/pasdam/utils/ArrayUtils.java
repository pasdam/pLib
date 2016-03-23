package com.pasdam.utils;

/**
 * @author paco
 * @version 0.1
 */
public class ArrayUtils {

	/**
	 * @param first
	 * @param second
	 * @return
	 * @throws NullPointerException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] merge(T[] first, T[] second) throws NullPointerException {
		Object[] result = new Object[first.length + second.length];
		int i = 0;
		for (T object: first) {
			result[i++] = object;
		}
		for (T object: second) {
			result[i++] = object;
		}
		return (T[]) result;
	}
	
	/**
	 * @param first
	 * @param second
	 * @return
	 * @throws NullPointerException
	 */
	public static int[] merge(int[] first, int[] second) throws NullPointerException {
		int[] result = new int[first.length + second.length];
		int i = 0;
		for (int value: first) {
			result[i++] = value;
		}
		for (int value: second) {
			result[i++] = value;
		}
		return result;
	}
}
