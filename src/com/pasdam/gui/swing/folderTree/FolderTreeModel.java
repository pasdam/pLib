package com.pasdam.gui.swing.folderTree;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;

import com.pasdam.utils.file.fileFilters.DirectoryFilter;
import com.pasdam.utils.file.fileFilters.DirectoryShowHiddenFilter;

/**
 * {@link TreeModel} that load filesystem elements dynamically. In order to perform<br />
 * By default, it doesn't change hidden folders, to change this behavior, use
 * {@link #setShowHidden(boolean)}.
 * 
 * @author paco
 * @version 0.1
 */
@SuppressWarnings("serial")
class FolderTreeModel extends DefaultTreeModel implements Comparator<File> {
	
	/**
	 * {@link FileFilter} used to index retrieve folder's children
	 */
	private FileFilter directoryFilter = new DirectoryFilter();
	
	/**
	 * Constructor that associate the model to the tree and load root nodes
	 * @param tree - tree to which associate this model
	 */
	public FolderTreeModel() {
		super(new DefaultMutableTreeNode());
		
		setAsksAllowsChildren(true);
		
		// add filesystem roots to the tree
		File[] roots = File.listRoots();
		DefaultMutableTreeNode rootNode;
		if (roots.length > 1) {
			rootNode = (DefaultMutableTreeNode) getRoot();

			DefaultMutableTreeNode currentNode;
			for (int k = 0; k < roots.length; k++) {
				currentNode = new DefaultMutableTreeNode(roots[k]);
				rootNode.add(currentNode);
				indexSubFolder(currentNode, 1, true);
			}
			
		} else {
			// if there is only one filesystem root, make it as the root of the
			// tree
			rootNode = new DefaultMutableTreeNode(roots[0]);
			setRoot(rootNode);
			indexSubFolder(rootNode, 2, true);
		}
	}

	/**
	 * Sets whether the hidden folder should be visible or not
	 * @param showHidden if true the hidden folder are visible in the tree
	 */
	public void setShowHidden(boolean showHidden) {
		this.directoryFilter = showHidden
				? new DirectoryShowHiddenFilter()
				: new DirectoryFilter();
	}
	
	/**
	 * Ensures that the node identified by the input file is loaded
	 * 
	 * @param path
	 *            folder to check, and eventually load
	 * @return the {@link DefaultMutableTreeNode} corresponding to the input
	 *         file
	 */
	@SuppressWarnings("unchecked")
	public DefaultMutableTreeNode loadPath(File path) {
		// normalize input
		if (path.isFile()) {
			path = path.getParentFile();
		}
		String targetPath = path.getAbsoluteFile().getAbsolutePath();
		
		DefaultMutableTreeNode currentNode;
		String currentFilePath;
		Enumeration<DefaultMutableTreeNode> childrenNodes = ((DefaultMutableTreeNode) getRoot()).children();
		
		while (childrenNodes.hasMoreElements()) {
			// get current node info
			currentNode = childrenNodes.nextElement();
			currentFilePath = ((File) currentNode.getUserObject()).getAbsolutePath();
			
			if (targetPath.startsWith(currentFilePath)) {
				// make sure that the children are loaded
				indexSubFolder(currentNode, 1, false);

				if (targetPath.equals(currentFilePath)) {
					// the current node is the requested one
					return currentNode;
					
				} else {
					// scan subfolders
					childrenNodes = currentNode.children();
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Recursive method used to load children nodes
	 * 
	 * @param parentNode
	 *            node for which load children
	 * @param depth
	 *            indicates the depth of the descendants to load
	 * @param refresh
	 *            if node children are already loaded it indicates whether
	 *            the list should be refreshed or not
	 */
	public void indexSubFolder(DefaultMutableTreeNode parentNode, int depth, boolean refresh) {
		int childCount = parentNode.getChildCount();
		if (childCount != 0) {
			if (refresh) {
				// unload previous loaded children
				for (int i = 0; i < childCount; i++) {
					removeNodeFromParent((MutableTreeNode) parentNode.getChildAt(0));
				} 
				
			} else {
				return;
			}
		}

		File[] listFiles = ((File) parentNode.getUserObject()).listFiles(this.directoryFilter);
		if (listFiles != null && listFiles.length > 0) {
			// sort children alphabetically (case insensitive)
			Arrays.sort(listFiles, this);

			// add children
			DefaultMutableTreeNode currentChildNode;
			for (int i = 0; i < listFiles.length; i++) {
				currentChildNode = new DefaultMutableTreeNode(listFiles[i]);
				insertNodeInto(currentChildNode, parentNode, i);
				
				if (depth > 0) {
					// index also subfolders
					indexSubFolder(currentChildNode, depth - 1, refresh);
				}
			} 
			
		} else {
			// node has no children
			childCount = parentNode.getChildCount();
			for (int i = 0; i < childCount; i++) {
				removeNodeFromParent((MutableTreeNode) parentNode.getChildAt(0));
			}
			parentNode.setAllowsChildren(false);
		}
	}

	@Override
	public int compare(File o1, File o2) {
		// case insensitive filenames comparison
		return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
	}
}