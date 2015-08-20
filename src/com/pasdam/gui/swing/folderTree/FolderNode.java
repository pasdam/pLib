package com.pasdam.gui.swing.folderTree;

import java.io.File;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Node for FolderNode
 * @author Paco
 * @version 1.0
 */
public class FolderNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -835578376976251299L;
	
	private final Icon icon;

	/**
	 * @param userObject - user object associated with the node
	 */
	public FolderNode(File userObject, boolean allowsChildren, Icon icon) {
		super(userObject, allowsChildren);
		this.icon = icon;
	}

	@Override
	public boolean isLeaf() {
		return !getAllowsChildren();
	}
	
	@Override
	public String toString() {
		final File file = (File) this.getUserObject();
		String name = file.getName();
		if (name.length() == 0) {
			// for roots that have no name
			return file.getAbsolutePath();
		} else {
			return name;
		}
	}
	
	/**
	 * @return the icon
	 */
	public Icon getIcon() {
		return icon;
	}
}