package com.pasdam.utils.file.fileFilters;

import java.io.File;

/**
 * {@link Filter} that accepts only not hidden directory
 * 
 * @author paco
 * @version 0.1
 */
class NoHiddenDirectoriesFilter implements Filter {
	
	@Override
	public boolean accept(File file) {
		return file.isDirectory() && !file.isHidden();
	}

	@Override
	public boolean isShowHidden() {
		return false;
	}

	@Override
	public boolean isShowFile() {
		return false;
	}

	@Override
	public boolean isShowFolder() {
		return true;
	}
}