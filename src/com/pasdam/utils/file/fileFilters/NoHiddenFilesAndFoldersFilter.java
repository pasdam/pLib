package com.pasdam.utils.file.fileFilters;

import java.io.File;

/**
 * {@link Filter} that accepts only not hidden files and folders
 * 
 * @author paco
 * @version 0.1
 */
class NoHiddenFilesAndFoldersFilter implements Filter {

	@Override
	public boolean accept(File file) {
		return !file.isHidden();
	}

	@Override
	public boolean isShowHidden() {
		return false;
	}

	@Override
	public boolean isShowFile() {
		return true;
	}

	@Override
	public boolean isShowFolder() {
		return true;
	}
}