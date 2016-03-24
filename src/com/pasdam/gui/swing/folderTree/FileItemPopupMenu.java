package com.pasdam.gui.swing.folderTree;

import java.awt.Component;
import java.io.File;

import javax.swing.JPopupMenu;

/**
 * <p>
 * Context menu for expandable rules.
 * </p>
 * <p>
 * Extending class can refers to the protected field {@link #folder}, in order
 * to get the folder on which the menu is opened
 * </p>
 * 
 * @author paco
 * @version 0.1
 */
public class FileItemPopupMenu extends JPopupMenu {
	
	private static final long serialVersionUID = -8735384067484992307L;

	/** Folder element the menu refers to */
	protected File folder;
	
	/**
	 * Show the popup menu for the specified folder
	 * 
	 * @param folder
	 *            folder the menu refers to
	 * @param invoker
	 *            the component in whose space the popup menu is to appear
	 * @param x
	 *            the x coordinate in invoker's coordinate space at which the
	 *            popup menu is to be displayed
	 * @param y
	 *            the y coordinate in invoker's coordinate space at which the
	 *            popup menu is to be displayed
	 */
	public void show(File folder, Component invoker, int x, int y) {
		this.folder = folder;
		show(invoker, x, y);
	}
}
