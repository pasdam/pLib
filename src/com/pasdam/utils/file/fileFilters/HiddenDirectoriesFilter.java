package com.pasdam.utils.file.fileFilters;

import java.io.File;

/**
 * {@link Filter} that accepts only directories (hidden and not hidden)
 * 
 * @author paco
 * @version 0.1
 */
class HiddenDirectoriesFilter implements Filter {
	
	@Override
	public boolean accept(File file) {
		return file.isDirectory();
	}

	@Override
	public boolean isShowHidden() {
		return true;
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