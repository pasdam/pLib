package com.pasdam.utils.file;

import java.io.File;
import java.util.ArrayList;

/**
 * This class is used to apply more renaming rules to a file, allowing to undo
 * performed operations
 * 
 * @author paco
 * @version 0.1
 */
public class FileRenamer {
	
	/**
	 * List of renamed files, from last to first (current file is always the first)
	 */
	private ArrayList<File> renameList = new ArrayList<File>();
	
	/**
	 * Constructor that sets the original file
	 * 
	 * @param file
	 *            - original file
	 * @throws IllegalArgumentException
	 *             if file is null or it doesn't exists
	 */
	public FileRenamer(File file) throws IllegalArgumentException {
		if (file != null && file.exists()) {
			this.renameList.add(file);
		
		} else {
			throw new IllegalArgumentException("Input file cannot be null and must exist: " + file);
		}
	}
	
	/**
	 * Returns the original file, the one specified in the constructor
	 * @return the original file, the one specified in the constructor
	 */
	public File getOriginalFile() {
		return this.renameList.get(this.renameList.size() - 1);
	}
	
	/**
	 * Returns the current file, the result of all renaming operations
	 * @return the current file, the result of all renaming operations
	 */
	public File getCurrentFile() {
		return this.renameList.get(0);
	}
	
	/**
	 * This method renames current file
	 * 
	 * @return true if and only if the renaming succeeded; false otherwise
	 */
	public boolean renameTo(File file) {
		File currentFile = getCurrentFile();
		if (!file.equals(currentFile)) {
			boolean result = currentFile.renameTo(file);
			if (result) {
				this.renameList.add(0, file);
			}
			return result;
		
		} else {
			return false;
		}
	}

	/**
	 * This method renames original file as
	 * <i>&lt;parent&gt;/&lt;name&gt;.&lt;extension&gt;</i>
	 * 
	 * @param parent
	 *            parent of the new file
	 * @param name
	 *            name of the new file
	 * @param extension
	 *            extension of the new file
	 * @return true if and only if the renaming succeeded; false otherwise
	 */
	public boolean renameTo(File parent, String name, String extension) {
		return renameTo(new File(parent, name + "." + extension));
	}
	
	/**
	 * Returns true if undo is available, i.e. there is at least one element in
	 * the undo list
	 * 
	 * @return true if undo is available, i.e. there is at least one element in
	 *         the undo list
	 */
	public boolean undoAvailable() {
		return this.renameList.size() > 1;
	}
	
	/**
	 * This method restores the previous file
	 * 
	 * @return true if and only if the restoring succeeded; false otherwise
	 */
	public boolean undoRename(){
		if (undoAvailable()) {
			File currentFile = this.renameList.get(0);
			if (currentFile.renameTo(this.renameList.get(1))) {
				this.renameList.remove(0);
				return true;
			}
		}
		return false;
	}
}
