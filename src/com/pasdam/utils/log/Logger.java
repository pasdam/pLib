package com.pasdam.utils.log;

/**
 * Interface that a logger instance must implement
 * 
 * @author paco
 * @version 0.1
 */
public interface Logger {
	
	/**
	 * Log a fatal event
	 * @param message message to log
	 */
	public void fatal(String message);
	
	/**
	 * Log an error event
	 * @param message message to log
	 */
	public void error(String message);
	
	/**
	 * Log a warning event
	 * @param message message to log
	 */
	public void warning(String message);
	
	/**
	 * Log an info event
	 * @param message message to log
	 */
	public void info(String message);
	
	/**
	 * Log a debug event
	 * @param message message to log
	 */
	public void debug(String message);
	
	/**
	 * Log a trace event
	 * @param message message to log
	 */
	public void trace(String message);
}
