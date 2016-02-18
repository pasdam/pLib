package com.pasdam.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to notify property changes to listeners
 * 
 * @author paco
 * @version 0.1
 *
 * @param <T> type of the observed property
 */
public class PropertyListenersManager<T> {
	
	/**
	 * Preference listeners
	 */
	private List<PropertyChangeListener<T>> listeners = new ArrayList<PropertyChangeListener<T>>();
	
	/**
	 * Adds a listener to the list
	 * 
	 * @param listener
	 *            listener to add
	 */
	public void addListener(PropertyChangeListener<T> listener) {
		if (listener != null) {
			this.listeners.add(listener);
		}
	}

	/** Notify each listener the new value of the property */
	public void notifyListeners(T newValue) {
		for (int i = 0; i < this.listeners.size(); i++) {
			this.listeners.get(i).propertyChanged(newValue);
		}
	}
}
