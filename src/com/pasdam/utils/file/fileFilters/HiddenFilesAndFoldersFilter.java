package com.pasdam.utils.file.fileFilters;

import java.io.File;

/**
 * {@link Filter} that accepts regular files and directories (hidden and not hidden)
 * 
 * @author paco
 * @version 0.1
 */
class HiddenFilesAndFoldersFilter implements Filter {
	
	@Override
	public boolean accept(File file) {
		return true;
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
		return true;
	}
}