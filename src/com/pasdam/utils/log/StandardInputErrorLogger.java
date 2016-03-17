package com.pasdam.utils.log;

/**
 * A {@link Logger} that print error/fatal event to {@link System#err} and other
 * events to {@link System#out}
 * 
 * @author paco
 * @version 0.1
 */
public class StandardInputErrorLogger implements Logger {

	@Override
	public void fatal(String message) {
		System.err.println(System.currentTimeMillis() + " [FATAL]\t" + message);
	}

	@Override
	public void error(String message) {
		System.err.println(System.currentTimeMillis() + " [ERROR]\t" + message);
	}

	@Override
	public void warning(String message) {
		System.out.println(System.currentTimeMillis() + " [WARNING]\t" + message);
	}

	@Override
	public void info(String message) {
		System.out.println(System.currentTimeMillis() + " [INFO]\t" + message);
	}

	@Override
	public void debug(String message) {
		System.out.println(System.currentTimeMillis() + " [DEBUG]\t" + message);
	}

	@Override
	public void trace(String message) {
		System.out.println(System.currentTimeMillis() + " [TRACE]\t" + message);
	}
}
