package com.pasdam.utils.file.fileFilters;

import java.io.File;
import java.io.FileFilter;

/**
 * Abstract class that represent a generic files filter (by type)
 * @author Paco
 * @version 1.0
 */
public class FileTypeFilter implements FileFilter {
	
	/** List of archive extensions */
	public static final String[] EXT_ARCHIVE = new String[]{".7z", ".gz", ".rar", ".zip", ".zipx", ".bz", ".bz2", ".tgz", ".jar", ".war", ".ace", ".bzip", ".gzip", ".cab", ".daa", ".dmg", ".deb"};
	/** List of media archive extensions */
	public static final String[] EXT_MEDIA_ARCHIVE = new String[]{".iso", ".img", ".mds", ".nrg", ".mdx", ".dmg", ".cue", ".cif", ".c2d", ".daa"};
	/** List of document extensions */
	public static final String[] EXT_DOCUMENT = new String[]{".doc", ".docx", ".html", ".pdf", ".rtf", ".txt", ".xml", ".xps", ".odp", ".pps", ".ppt", ".pptx", ".xls", ".xlsx", ".odm", ".odt", ".tex", ".pages", ".csv"};
	/** List of image extensions */
	public static final String[] EXT_IMAGES = new String[]{".jpg", ".jpeg", ".bmp", ".gif", ".png", ".ico", ".svg", ".jp2", ".psd", ".psp", ".tiff", ".tif", ".exif", ".xcf"};
	/** List of audio extensions */
	public static final String[] EXT_AUDIO = new String[]{".mp3", ".m3u", ".au", ".wav", ".flac", ".m4a", ".wma", ".mp2", ".aac", ".aiff", ".mid"};
	/** List of video extensions */
	public static final String[] EXT_VIDEO = new String[]{".avi", ".m4v", ".mkv", ".mov", ".mpeg", ".mpg", ".mp4", ".3gp", ".flv", ".ogg", ".rm", ".wmv", ".nsv"};
	
	private final String[] EXT;
	private boolean showHidden;
	
	/**
	 * Constructor that sets the extensions list and the name of the filter
	 * @param showHidden - if true the filter shows hidden files
	 * @param extensions - the array of supported extension
	 */
	public FileTypeFilter(String[] extension, boolean showHidden) {
		this.EXT = extension;
		this.showHidden = showHidden;
	}

	/**
	 * This method returns true only if input File represent an archive on the filesystem
	 * @param file - file to filter
	 * @return true if input file has a valid extension
	 */
	@Override
	public boolean accept(File file) {
		if (file.isFile()) {
			if (!showHidden && file.isHidden()) {
				return false;
			} else {
				String name = file.getName().toLowerCase();
				for (int i = 0; i < EXT.length; i++) {
					if (name.endsWith(EXT[i])) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * This method return true if the input extension is supported by this filter
	 * @param extension string to check
	 * @return true if extension is supported
	 */
	public boolean isSupportedExtension(String extension){
		String extToSearch;
		if (extension.startsWith(".")) {
			extToSearch = extension;
		} else {
			extToSearch = "." + extension;
		}
		for (int i = 0; i < EXT.length; i++) {
			if (EXT[i].equals(extToSearch)) {
				return true;
			}
		}
		return false;
	}
}
