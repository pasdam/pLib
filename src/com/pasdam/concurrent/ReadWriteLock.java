package com.pasdam.concurrent;

import java.util.HashMap;

/**
 * Lock that allows only one writer or one or more readers
 * @author Paco
 * @version 1.0
 */
public class ReadWriteLock {

	private boolean write = false;
	private int readers = 0;
	private HashMap<Long, Void> writersMap = new HashMap<Long, Void>();
	private HashMap<Long, Void> readersMap = new HashMap<Long, Void>();

	/**
	 * This method requires a read lock, or wait if there is a write operation
	 */
	public synchronized void getReadLock(){
		if (write) {
			try {
				readersMap.wait();
			} catch (InterruptedException e) {
			}
			getReadLock();
		} else {
			readersMap.put(Thread.currentThread().getId(), null);
			readers++;
		}
	}

	/**
	 * This method release a read lock
	 */
	public synchronized void releaseReadLock(){
		long id = Thread.currentThread().getId();
		if (readersMap.containsKey(id)) {
			readersMap.remove(id);
			readers--;
			writersMap.notifyAll();
		}
	}

	/**
	 * This method requires a write lock, or wait if there are other operations on
	 */
	public synchronized void getWriteLock(){
		if (write || readers > 0) {
			try {
				writersMap.wait();
			} catch (InterruptedException e) {
			}
			getWriteLock();
		} else {
			writersMap.put(Thread.currentThread().getId(), null);
			write = true;
		}
	}

	/**
	 * This method release the write lock
	 */
	public synchronized void releaseWriteLock(){
		long id = Thread.currentThread().getId();
		if (writersMap.containsKey(id)) {
			writersMap.remove(id);
			write = false;
			writersMap.notifyAll();
			readersMap.notifyAll();
		}
	}
}