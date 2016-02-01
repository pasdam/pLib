package com.pasdam.utils.file.fileFilters;

import java.io.FileFilter;

/**
 * @author paco
 *
 */
interface Filter extends FileFilter {
	
	/**
	 * Returns true if the filter accepts hidden files and folders, false otherwise
	 * @return true if the filter accepts hidden files and folders, false otherwise
	 */
	public boolean isShowHidden();
	
	/**
	 * Returns true if the filter accepts regular files, false otherwise
	 * @return true if the filter accepts regular files, false otherwise
	 */
	public boolean isShowFile();
	
	/**
	 * Returns true if the filter accepts folder, false otherwise
	 * @return true if the filter accepts folder, false otherwise
	 */
	public boolean isShowFolder();
}