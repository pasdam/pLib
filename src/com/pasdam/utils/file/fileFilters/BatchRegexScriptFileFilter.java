package com.pasdam.utils.file.fileFilters;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * @author Paco
 * @version 1.0
 */
public class BatchRegexScriptFileFilter extends FileFilter {
	
	public static final String description = "Batch RegEx Renamer Script (brr)";
	public static final String extension = ".brr";
	
	@Override
	public boolean accept(File file) {
		if (file.isDirectory()) {
	        return true;
	    }
		if (file.isFile() && file.getName().endsWith(extension)) {
			return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
