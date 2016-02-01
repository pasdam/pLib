package com.pasdam.utils.file.fileFilters;

import java.io.File;
import java.io.FileFilter;

/**
 * A filter that returns only directories
 * @author Paco
 * @version 1.0
 */
public class DirectoryFilter implements FileFilter {
	
	/**
	 * This method returns true only if input File represent a directory on the filesystem
	 */
	@Override
	public boolean accept(File file) {
		if (file.isDirectory() && !file.isHidden()) {
			return true;
		}
		return false;
	}
}
