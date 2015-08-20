package com.pasdam.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Concurrent implementation of an ArrayList (one writer, much readers)
 * @author Paco
 * @version 1.0
 */
public class ConcurrentReadWriteList<T> extends ArrayList<T> {

	private static final long serialVersionUID = 8356798559863873922L;
	private final ReadWriteLock lock = new ReadWriteLock();

	@Override
	public boolean add(T e) {
		lock.getWriteLock();
		final boolean add = super.add(e);
		lock.releaseWriteLock();
		return add;
	}

	@Override
	public void add(int index, T element) {
		lock.getWriteLock();
		super.add(index, element);
		lock.releaseWriteLock();
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		lock.getWriteLock();
		final boolean addAll = super.addAll(c);
		lock.releaseWriteLock();
		return addAll;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		lock.getWriteLock();
		final boolean addAll = super.addAll(index, c);
		lock.releaseWriteLock();
		return addAll;
	}

	@Override
	public void clear() {
		lock.getWriteLock();
		super.clear();
		lock.releaseWriteLock();
	}

	@Override
	public Object clone() {
		lock.getReadLock();
		final Object clone = super.clone();
		lock.releaseReadLock();
		return clone;
	}

	@Override
	public boolean contains(Object o) {
		lock.getReadLock();
		final boolean contains = super.contains(o);
		lock.releaseReadLock();
		return contains;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		lock.getReadLock();
		final boolean containsAll = super.containsAll(c);
		lock.releaseReadLock();
		return containsAll;
	}

	@Override
	public void ensureCapacity(int minCapacity) {
		lock.getWriteLock();
		super.ensureCapacity(minCapacity);
		lock.releaseWriteLock();
	}

	@Override
	public boolean equals(Object o) {
		lock.getReadLock();
		final boolean equals = super.equals(o);
		lock.releaseReadLock();
		return equals;
	}

	@Override
	public T get(int index) {
		lock.getReadLock();
		final T t = super.get(index);
		lock.releaseReadLock();
		return t;
	}

	@Override
	public int hashCode() {
		lock.getReadLock();
		final int hashCode = super.hashCode();
		lock.releaseReadLock();
		return hashCode;
	}

	@Override
	public int indexOf(Object o) {
		lock.getReadLock();
		final int indexOf = super.indexOf(o);
		lock.releaseReadLock();
		return indexOf;
	}

	@Override
	public boolean isEmpty() {
		lock.getReadLock();
		final boolean empty = super.isEmpty();
		lock.releaseReadLock();
		return empty;
	}

	@Override
	public Iterator<T> iterator() {
		lock.getReadLock();
		final Iterator<T> iterator = super.iterator();
		lock.releaseReadLock();
		return iterator;
	}

	@Override
	public int lastIndexOf(Object o) {
		lock.getReadLock();
		final int lastIndexOf = super.lastIndexOf(o);
		lock.releaseReadLock();
		return lastIndexOf;
	}

	@Override
	public T remove(int index) {
		lock.getWriteLock();
		final T remove = super.remove(index);
		lock.releaseWriteLock();
		return remove;
	}

	@Override
	public ListIterator<T> listIterator() {
		lock.getReadLock();
		final ListIterator<T> listIterator = super.listIterator();
		lock.releaseReadLock();
		return listIterator;
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		lock.getReadLock();
		final ListIterator<T> listIterator = super.listIterator(index);
		lock.releaseReadLock();
		return listIterator;
	}

	@Override
	public boolean remove(Object o) {
		lock.getWriteLock();
		final boolean remove = super.remove(o);
		lock.releaseWriteLock();
		return remove;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		lock.getWriteLock();
		final boolean removeAll = super.removeAll(c);
		lock.releaseWriteLock();
		return removeAll;
	}

	@Override
	protected void removeRange(int fromIndex, int toIndex) {
		lock.getWriteLock();
		super.removeRange(fromIndex, toIndex);
		lock.releaseWriteLock();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		lock.getWriteLock();
		final boolean retainAll = super.retainAll(c);
		lock.releaseWriteLock();
		return retainAll;
	}

	@Override
	public T set(int index, T element) {
		lock.getWriteLock();
		final T set = super.set(index, element);
		lock.releaseWriteLock();
		return set;
	}

	@Override
	public int size() {
		lock.getReadLock();
		final int size = super.size();
		lock.releaseReadLock();
		return size;
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		lock.getReadLock();
		final List<T> subList = super.subList(fromIndex, toIndex);
		lock.releaseReadLock();
		return subList;
	}

	@Override
	public Object[] toArray() {
		lock.getReadLock();
		final Object[] array = super.toArray();
		lock.releaseReadLock();
		return array;
	}

	public <U extends Object> U[] toArray(U[] a) {
		lock.getReadLock();
		final U[] array = super.toArray(a);
		lock.releaseReadLock();
		return array;
	};

	@Override
	public String toString() {
		lock.getReadLock();
		final String string = super.toString();
		lock.releaseReadLock();
		return string;
	}

	@Override
	public void trimToSize() {
		lock.getWriteLock();
		super.trimToSize();
		lock.releaseWriteLock();
	}
}