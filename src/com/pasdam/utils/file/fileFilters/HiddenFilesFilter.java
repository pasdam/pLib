package com.pasdam.utils.file.fileFilters;

import java.io.File;

/**
 * {@link Filter} that accepts only regular files (hidden and not hidden)
 * 
 * @author paco
 * @version 0.1
 */
class HiddenFilesFilter implements Filter {
	
	@Override
	public boolean accept(File file) {
		return file.isFile();
	}

	@Override
	public boolean isShowHidden() {
		return true;
	}

	@Override
	public boolean isShowFile() {
		return true;
	}

	@Override
	public boolean isShowFolder() {
		return false;
	}
}