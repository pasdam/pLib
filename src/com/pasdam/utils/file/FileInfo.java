package com.pasdam.utils.file;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author Paco
 * @version 1.0
 */
public class FileInfo implements Serializable {
	
	/** */
	private static final long serialVersionUID = 985720588842279456L;
	
	/** List of archive extensions */
	public static final String[] EXT_ARCHIVE = new String[]{"7z", "gz", "rar", "zip", "zipx", "bz", "bz2", "tgz", "jar", "war", "ace", "bzip", "gzip", "cab", "daa", "dmg", "deb"};
	/** List of media archive extensions */
	public static final String[] EXT_MEDIA_ARCHIVE = new String[]{"iso", "img", "mds", "nrg", "mdx", "dmg", "cue", "cif", "c2d", "daa"};
	/** List of document extensions */
	public static final String[] EXT_DOCUMENT = new String[]{"doc", "docx", "html", "pdf", "rtf", "txt", "xml", "xps", "odp", "pps", "ppt", "pptx", "xls", "xlsx", "odm", "odt", "tex", "pages", "csv"};
	/** List of image extensions */
	public static final String[] EXT_IMAGES = new String[]{"jpg", "jpeg", "bmp", "gif", "png", "ico", "svg", "jp2", "psd", "psp", "tiff", "tif", "exif", "xcf"};
	/** List of audio extensions */
	public static final String[] EXT_AUDIO = new String[]{"mp3", "m3u", "au", "wav", "flac", "m4a", "wma", "mp2", "aac", "aiff", "mid"};
	/** List of video extensions */
	public static final String[] EXT_VIDEO = new String[]{"avi", "m4v", "mkv", "mov", "mpeg", "mpg", "mp4", "3gp", "flv", "ogg", "rm", "wmv", "nsv"};
	
	public static final int UNIT_AUTO = 0;
	public static final int UNIT_BYTE = 1;
	public static final int UNIT_KILOBYTE = 1000;
	public static final int UNIT_MEGABYTE = 1000000;
	public static final int UNIT_GIGABYTE = 1000000000;
	
	private int part = 0;
	private String extension;
	private String partName;
	private String fileName;
	
	public FileInfo() {
	}
	
	public void parseFileName(String fileName) {
		if (fileName == null) {
			return;
		}
		String text = fileName.replaceFirst("^.*\\.", "").toLowerCase();
		if (text.matches("[0-9]+")) {
			this.part = Integer.parseInt(text);
			// remove part # from name
			this.fileName = fileName.substring(0, fileName.length() - text.length() - 1);
			this.extension = this.fileName.replaceFirst("^.*\\.", "");
			this.fileName = this.fileName.substring(0, this.fileName.length() - this.extension.length() - 1);
			this.partName = fileName;
			return;
		} else if (text.matches("[rz][0-9]+")) {
			this.part = Integer.parseInt(text.replaceFirst("[^0-9]+", ""));
			if (text.startsWith("r")) {
				extension = "rar";
			} else if (text.startsWith("z")) {
				extension = "zip";
			}
			// remove extension from name
			this.fileName = fileName.substring(0, fileName.length() - text.length() - 1); // TODO check -1
			this.partName = fileName;
			return;
		} else {
			extension = text;
			this.partName = fileName.substring(0, fileName.length() - text.length() - 1);
			String[] parts = this.partName.split("[\\W_](([pP][aA][rR][tT])|([cC][dD]))[0-9]+", 2);
			if (parts.length == 2) {
				this.fileName = parts[0] + parts[1];
				try {
					part = Integer.parseInt("0" + partName
							.substring(parts[0].length(), partName.length() - parts[1].length())
							.replaceAll("[^0-9]+", ""));
				} catch (NumberFormatException e) {
					part = 0;
					e.printStackTrace();
				}
			} else {
				this.fileName = partName = fileName;
			}
		}
//		if (!partFound) {
//			for (int i = 0; i < parts.length; i++) {
//				fileName += parts[i];
//			}
//			StringBuilder builder = new StringBuilder();
//			builder.append(parts);
//			this.fileName = builder.toString();
//			part = Integer.parseInt("0" + name
//					.replaceFirst("\\Wpart", "\npart")
//					.replaceFirst("\\Wcd", "\ncd")
//					.replaceAll("[^0-9a-z]+", "\n")
//					.replaceAll("(?m)^(?!((part)|(cd))).*$", "")
//					.replaceAll("[^0-9]+", ""));
//		}
//		this.fileName = "filename";
//		this.partName = "partname";
//		this.part = 0;
//		this.extension = "zip";
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	
	/**
	 * @return the part
	 */
	public int getPart() {
		return part;
	}

	/**
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * @return the name
	 */
	public String getPartName() {
		return partName;
	}

	public static String getName(String filename){
		return filename.replaceFirst("\\.\\w+?$", "");
	}

	public static String getExtension(String filename){
		return filename.replaceFirst("^.*\\.", "");
	}
	
	public static String getSize(long size, int unit, int resultUnit){
		if (size >= 0) {
			String suffix = "";
			switch (unit) {
				case UNIT_BYTE:
					suffix = " B";
					break;
				case UNIT_KILOBYTE:
					suffix = " KB";
					break;
				case UNIT_MEGABYTE:
					suffix = " MB";
					break;
				case UNIT_GIGABYTE:
					suffix = " GB";
					break;
				default:
					return null;
			}
			switch (resultUnit) {
				case UNIT_BYTE:
					suffix = " B";
					break;
				case UNIT_KILOBYTE:
					suffix = " KB";
					break;
				case UNIT_MEGABYTE:
					suffix = " MB";
					break;
				case UNIT_GIGABYTE:
					suffix = " GB";
					break;
				case UNIT_AUTO:
					break;
				default:
					return null;
			}
			if (resultUnit != UNIT_AUTO) {
				StringBuilder textSize = new StringBuilder();
				textSize.append(new DecimalFormat("#.##").format(((double)size)*unit/resultUnit));
				textSize.append(suffix);
				return textSize.toString();
			} else {
				double dim;
				String prevDim = size + suffix;
				NumberFormat formatter = new DecimalFormat("#.##");
				for (int i = unit*1000; i <= UNIT_GIGABYTE; i = i*1000) {
					dim = ((double)size)*unit/i;
					if (dim < 1) {
						break;
					} else {
						switch (i) {
							case UNIT_KILOBYTE:
								prevDim = formatter.format(dim) + " KB";
								break;
							case UNIT_MEGABYTE:
								prevDim = formatter.format(dim) + " MB";
								break;
							case UNIT_GIGABYTE:
								prevDim = formatter.format(dim) + " GB";
								break;
						}
					}
				}
				return prevDim;
			}
		}
		return null;
	}
	
	public static long parseSize(String size){
		size = size.trim().replaceFirst(",", ".");
		if (size.matches("[0-9]+([.][0-9]+)*\\s*[kKmMgG]?[bB]")) {
//			Matcher matcher = Pattern.compile(
//					"[0-9]+([.,][0-9]+)*\\s*[kKmMgG]?[bB]").matcher(size);
//			if (matcher.find()) {
//				size = matcher.group();
				String unit = size.replaceFirst("[0-9]+([.,][0-9]+)*\\s*", "")
						.toLowerCase();
				if (unit.equals("b")) {
					return (long) Double.parseDouble(size.replaceFirst(
							"\\s*[kKmMgG]?[bB]", ""));
				} else if (unit.equals("kb")) {
					return (long) (Double.parseDouble(size.replaceFirst(
							"\\s*[kKmMgG]?[bB]", "")) * UNIT_KILOBYTE);
				} else if (unit.equals("mb")) {
					return (long) (Double.parseDouble(size.replaceFirst(
							"\\s*[kKmMgG]?[bB]", "")) * UNIT_MEGABYTE);
				} else if (unit.equals("gb")) {
					return (long) (Double.parseDouble(size.replaceFirst(
							"\\s*[kKmMgG]?[bB]", "")) * UNIT_GIGABYTE);
				}
//			}
		}
		return 0;
	}
}
