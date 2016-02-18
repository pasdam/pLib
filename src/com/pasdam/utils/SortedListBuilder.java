package com.pasdam.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * This helper class builds a list, sorting elements while inserting, ignoring
 * elements already present (each element can be present only once)
 * 
 * @author paco
 * @version 0.1
 *
 * @param <T>
 *            type of the list's elements
 */
public class SortedListBuilder<T> {
	
	/**
	 * Comparator used to compare list elements
	 */
	private final Comparator<T> comparator;

	/**
	 * Creates a {@link SortedListBuilder} with the specified {@link Comparator}
	 * 
	 * @param comparator
	 *            object used to compare list elements
	 * @throws IllegalArgumentException
	 *             if <i>comparator</i> is null
	 */
	public SortedListBuilder(Comparator<T> comparator) throws IllegalArgumentException {
		if (comparator != null) {
			this.comparator = comparator;
		} else {
			throw new IllegalArgumentException("Comparator can't be null");
		}
	}

	/**
	 * Sorted list
	 */
	private List<T> innerList = new ArrayList<T>();
	
	/**
	 * This method adds the specified element to the list, returning the
	 * insertion position or -1 if such element is already in list
	 * 
	 * @param element element to add
	 * @return the insertion index, or -1 if the element is already in list
	 */
	public int add(T element) {
		int insertIndex = getInsertIndex(element, 0, this.innerList.size());
		if (insertIndex >= 0) {
			this.innerList.add(insertIndex, element);
			return insertIndex;
		}
		return -1;
	}
	
	/**
	 * This method is used to evaluate the insertion index of the specified
	 * element
	 * 
	 * @param elementToInsert
	 *            element that will be added to the list
	 * @param startIndex
	 *            starting index of the sub-list in which add the element
	 * @param endIndex
	 *            end index of the sub-list in which add the element
	 * @return the index at which the element will be added, -1 if it is already
	 *         in list
	 */
	private int getInsertIndex(T elementToInsert, int startIndex, int endIndex) {
		if (startIndex >= endIndex) {
			if (startIndex >= innerList.size()) { // startIndex out of range
				return innerList.size();
			
			} else if (this.comparator.compare(this.innerList.get(startIndex), elementToInsert) != 0) { 
				return startIndex;
			
			} else { // ignore elements already in list
				return -1;
			}
			
		} else {
			int currentIndex  = startIndex + (endIndex - startIndex)/2;
			int compareResult = this.comparator.compare(this.innerList.get(currentIndex), elementToInsert);

			if (compareResult < 0) {
				// listElement < elementToInsert
				return getInsertIndex(elementToInsert, currentIndex+1, endIndex); // recursive call
			
			} else if (compareResult > 0) {
				// listElement > elementToInsert
				return getInsertIndex(elementToInsert, startIndex, currentIndex); // recursive call
				
			} else {
				// elementToInsert already present
				return -1;
			}
		}
	}
	
	/**
	 * This method returns the sorted list
	 * 
	 * @return the sorted list
	 */
	public List<T> getSortedList() {
		return innerList;
	}

	/**
	 * Simple {@link Comparator} of {@link Comparable} elements
	 * 
	 * @author paco
	 * @version 0.1
	 *
	 * @param <T>
	 *            type of the elements to compare
	 */
	public static class DefaultComparator <T extends Comparable<T>> implements Comparator<T> {

		@Override
		public int compare(T object1, T object2) {
			return object1.compareTo(object2);
		}
	}
}