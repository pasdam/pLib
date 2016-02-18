package com.pasdam.utils;

/**
 * Interface implemented by those classes that need to be notified of property
 * changes
 * 
 * @author paco
 * @version 0.1
 *
 * @param <T>
 *            type of the property
 */
public interface PropertyChangeListener<T> {

	/**
	 * Notify that the property is changed
	 * 
	 * @param value new value of the property
	 */
	public void propertyChanged(T value);
}
