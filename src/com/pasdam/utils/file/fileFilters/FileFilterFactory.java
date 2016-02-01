package com.pasdam.utils.file.fileFilters;

import java.io.FileFilter;

/**
 * Factory class used to create {@link FileFilter}s
 * 
 * @author paco
 * @version 0.1
 */
public class FileFilterFactory {
	
	/**
	 * Private constructor: avoids class instantiation
	 */
	private FileFilterFactory() {}
	
	/**
	 * Factory method to create a {@link FileFilter} with the specified
	 * characteristics
	 * 
	 * @param showDirectories
	 *            if true the filter returns directories
	 * @param showFiles
	 *            if true the filter returns regular files
	 * @param showHidden
	 *            if true the filter returns hidden files (if <i>showFiles</i>
	 *            is true) and directory (if <i>showDirectories</i> is true)
	 * @return a {@link FileFilter} with the specified characteristics
	 */
	public static FileFilter getFilter(boolean showDirectories, boolean showFiles, boolean showHidden){
		if (showDirectories && showFiles) {

			if (showHidden) {
				return new HiddenFilesAndFoldersFilter();
			
			} else {
				return new NoHiddenFilesAndFoldersFilter();
			}
			
		} else if (!showDirectories && showFiles) {
			
			if (showHidden) {
				return new HiddenFilesFilter();
				
			} else {
				return new NoHiddenFilesFilter();
			}
			
		} else if (showDirectories && !showFiles) {
			
			if (showHidden) {
				return new HiddenDirectoriesFilter();
			
			} else {
				return new NoHiddenDirectoriesFilter();
			}
		}
		
		return null;
	}
}
