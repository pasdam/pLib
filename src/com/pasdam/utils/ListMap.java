package com.pasdam.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Paco
 * @version 1.0
 */
public class ListMap<K, V> {
	
	private HashMap<K, List<V>> map;
	
	/**
	 * 
	 */
	public ListMap() {
		map = new HashMap<K, List<V>>();
	}
	
	public void clear(){
		map.clear();
	}

	public boolean containsKey(K key){
		return map.containsKey(key);
	}
	
	public void put(K key, V value){
		List<V> list = map.get(key);
		if (list == null) {
			list = new ArrayList<V>();
			map.put(key, list);
		}
		list.add(value);
	}
}
