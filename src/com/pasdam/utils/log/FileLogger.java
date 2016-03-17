package com.pasdam.utils.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A {@link Logger} that print error/fatal event to {@link System#err} and other
 * events to {@link System#out}
 * 
 * @author paco
 * @version 0.1
 */
public class FileLogger implements Logger {
	
	/**
	 * Log file writer
	 */
	private final FileWriter writer;
	
	/**
	 * Construct a {@link FileLogger} that store all messages in <i>file</i>.
	 * 
	 * @param file
	 *            log file
	 * @param append
	 *            if true, then the log message will be written to the end of
	 *            the file rather than the beginning
	 * @throws IOException
	 *             if the file exists but is a directory rather than a regular
	 *             file, does not exist but cannot be created, or cannot be
	 *             opened for any other reason
	 * @throws NullPointerException if <i>file</i> is null
	 */
	public FileLogger(File file, boolean append) throws IOException, NullPointerException {
		if (file != null) {
			this.writer = new FileWriter(file, append);
		
		} else {
			throw new NullPointerException("Input file cannot be null");
		}
	}
	
	/**
	 * Construct a {@link FileLogger} that store all messages in the specified
	 * file.
	 * 
	 * @param filePath
	 *            log file path
	 * @param append
	 *            if true, then the log message will be written to the end of
	 *            the file rather than the beginning
	 * @throws IOException
	 *             if the file exists but is a directory rather than a regular
	 *             file, does not exist but cannot be created, or cannot be
	 *             opened for any other reason
	 */
	public FileLogger(String filePath, boolean append) throws IOException {
		this(new File(filePath), append);
	}

	@Override
	public void fatal(String message) {
		try {
			this.writer.write(System.currentTimeMillis() + " [FATAL]\t" + message + "\n");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void error(String message) {
		try {
			this.writer.write(System.currentTimeMillis() + " [ERROR]\t" + message + "\n");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void warning(String message) {
		try {
			this.writer.write(System.currentTimeMillis() + " [WARNING]\t" + message + "\n");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void info(String message) {
		try {
			this.writer.write(System.currentTimeMillis() + " [INFO]\t" + message + "\n");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void debug(String message) {
		try {
			this.writer.write(System.currentTimeMillis() + " [DEBUG]\t" + message + "\n");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void trace(String message) {
		try {
			this.writer.write(System.currentTimeMillis() + " [TRACE]\t" + message + "\n");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
