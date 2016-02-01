package com.pasdam.utils.file.fileFilters;

import java.io.File;
import java.util.Comparator;

/**
 * Comparator used to sort a files list showing folders first
 * 
 * @author paco
 * @version 1.0
 */
public class FirstFolderComparator implements Comparator<File> {

	@Override
	public int compare(File f0, File f1) {
		if (f0 != null && f1 != null) {
			
			if (f0.isDirectory() && f1.isFile()) {
				return -1;
			
			} else if (f0.isFile() && f1.isDirectory()) {
				return 1;
			
			} else {
				// same type: compare names
				return f0.getName().toLowerCase().compareTo(f1.getName().toLowerCase());
			}
			
		} else {
			// at least one parameter is null
			if (f0 == null) {
				return 1;
			} else {
				return -1;
			}
		}
	}
}
