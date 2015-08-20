package com.pasdam.utils.file.fileFilters;

import java.io.File;
import java.io.FileFilter;
import java.security.InvalidParameterException;

/**
 * This class permits to construct a custom regex filter
 * @author Paco
 * @version 1.0
 */
public class CustomFilter implements FileFilter {
	
	/** Constraint used to filter both file and folders */
	public static final int TYPE_ALL = 0;
	/** Constraint used to filter only folders */
	public static final int TYPE_FOLDER_ONLY = 1;
	/** Constraint used to filter only file */
	public static final int TYPE_FILE_ONLY = 2;
	
	private final String[] regEx;
	private final String name;
	private final FileFilter filter;

	/**
	 * Constructor that sets the regular expression to match.<br/>
	 * To set the filter type use the class constraints.
	 * If andRegEx is true, then filter return true only if the the filename matches all the regEx;
	 * otherwise, it returns true if the filename matches at least one regEx.
	 * @param name - the name of this filter 
	 * @param regEx - the regular expression to match
	 * @param andRegEx - true for regex conjunction, false for disjunction
	 * @param type - the type of file to filter (all, file only or folder only)
	 * @throws InvalidParameterException - if the input type is invalid
	 */
	public CustomFilter(String name, String[] regEx, boolean andRegEx, int type) throws InvalidParameterException {
		this.regEx = regEx;
		this.name = name;
		switch (type) {
		case TYPE_ALL:
			if (andRegEx) {
				this.filter = new CustomFilterAllAnd();
			} else {
				this.filter = new CustomFilterAllOr();
			}
			break;
		case TYPE_FOLDER_ONLY:
			if (andRegEx) {
				this.filter = new CustomFilterFolderAnd();
			} else {
				this.filter = new CustomFilterFolderOr();
			}
			break;
		case TYPE_FILE_ONLY:
			if (andRegEx) {
				this.filter = new CustomFilterFileAnd();
			} else {
				this.filter = new CustomFilterFileOr();
			}
			break;
		default:
			throw new InvalidParameterException("Invalid value for parameter \"type\": " + type);
		}
	}

	/**
	 * This method returns true only if input filename matches the regular expression
	 * @param file - file to filter
	 * @return true if input filename matches the regular expression
	 */
	@Override
	public boolean accept(File file) {
		return this.filter.accept(file);
	}

	/**
	 * This method returns the name of the filter<br/>
	 * It can return null or an empty string ("").
	 * @return the name of this filter
	 */
	public String getName(){
		return this.name;
	}
	
	/** This filter returns all files and directories that matches all regEx */
	private class CustomFilterAllAnd implements FileFilter {
		@Override
		public boolean accept(File file) {
			for (int i = 0; i < CustomFilter.this.regEx.length; i++) {
				if (!file.getName().matches(CustomFilter.this.regEx[i])) {
					return false;
				}
			}
			return true;
		}
	}
	
	/** This filter returns all files and directories that matches at least one regEx */
	private class CustomFilterAllOr implements FileFilter {
		@Override
		public boolean accept(File file) {
			for (int i = 0; i < CustomFilter.this.regEx.length; i++) {
				if (file.getName().matches(CustomFilter.this.regEx[i])) {
					return true;
				}
			}
			return false;
		}
	}
	
	/** This filter returns all directories that matches all regEx */
	private class CustomFilterFolderAnd implements FileFilter {
		@Override
		public boolean accept(File file) {
			if (file.isDirectory()) {
				for (int i = 0; i < CustomFilter.this.regEx.length; i++) {
					if (!file.getName().matches(CustomFilter.this.regEx[i])) {
						return false;
					}
				}
				return true;
			}
			return false;
		}
	}
	
	/** This filter returns all directories that matches at least one regEx */
	private class CustomFilterFolderOr implements FileFilter {
		@Override
		public boolean accept(File file) {
			if (file.isDirectory()) {
				for (int i = 0; i < CustomFilter.this.regEx.length; i++) {
					if (file.getName().matches(CustomFilter.this.regEx[i])) {
						return true;
					}
				}
			}
			return false;
		}
	}
	
	/** This filter returns all files that matches all regEx */
	private class CustomFilterFileAnd implements FileFilter {
		@Override
		public boolean accept(File file) {
			if (file.isFile()) {
				for (int i = 0; i < CustomFilter.this.regEx.length; i++) {
					if (!file.getName().matches(CustomFilter.this.regEx[i])) {
						return false;
					}
				}
				return true;
			}
			return false;
		}
	}
	
	/** This filter returns all files that matches at least one regEx */
	private class CustomFilterFileOr implements FileFilter {
		@Override
		public boolean accept(File file) {
			if (file.isFile()) {
				for (int i = 0; i < CustomFilter.this.regEx.length; i++) {
					if (file.getName().matches(CustomFilter.this.regEx[i])) {
						return true;
					}
				}
			}
			return false;
		}
	}
}
