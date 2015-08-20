package com.pasdam.utils.file.fileFilters;

import java.io.File;
import java.io.FileFilter;

/**
 * A filter that returns only files
 * @author Paco
 * @version 1.0
 */
public class FilesFilter implements FileFilter {

	private boolean showHidden;

	/**
	 * @param showHidden - if true the filter shows hidden files
	 */
	public FilesFilter(boolean showHidden) {
		this.showHidden = showHidden;
	}
	

	/**
	 * This method returns true only if input File represent a file on the filesystem
	 */
	@Override
	public boolean accept(File pathname) {
		if (pathname.isFile()) {
			if (!showHidden && pathname.isHidden()) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
}
