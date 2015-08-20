package com.pasdam.gui.swing.folderTree;

import java.io.File;
import java.io.FileFilter;

import javax.swing.Icon;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.MutableTreeNode;

import com.pasdam.utils.file.fileFilters.DirectoryFilter;
import com.pasdam.utils.file.fileFilters.DirectoryShowHiddenFilter;

/**
 * TreeModel that load filesystem elements dynamically 
 * @author Paco
 * @version 1.0
 */
@SuppressWarnings("serial")
public class FolderTreeModel extends DefaultTreeModel implements TreeWillExpandListener {
	
	private boolean showHidden = false;
	
	/**
	 * Constructor that associate the model to the tree and load root nodes
	 * @param tree - tree to which associate this model
	 */
	public FolderTreeModel() {
		super(new FolderNode(new File("Computer"), true, FileSystemView.getFileSystemView().getSystemIcon(File.listRoots()[0])));
		
		setAsksAllowsChildren(true);
		
		// add elements to the tree
		FolderNode rootNode = ((FolderNode) getRoot());
		
		FolderNode node;
		File[] roots = File.listRoots();
		if (roots.length > 1) {
			final Icon systemIcon = FileSystemView.getFileSystemView().getSystemIcon(roots[0]);
			for (int k=0; k<roots.length; k++) {
				node = new FolderNode(roots[k], true, systemIcon);
				rootNode.add(node);
//				node.add( new DefaultMutableTreeNode(new Boolean(true)));
			}
		} else {
			final FolderNode rootFolder = new FolderNode(roots[0], true, FileSystemView.getFileSystemView().getSystemIcon(roots[0]));
			setRoot(rootFolder);
			SwingUtilities.invokeLater(new LoadChildrenWorker(rootFolder));
		}
	}

	public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
		// get expanded node
		FolderNode node = (FolderNode) event.getPath().getLastPathComponent();
		if (!node.isLeaf()) {
			// load children
			Thread thread = new LoadChildrenWorker(node);
			thread.start();
			try {
				thread.join();
			} catch (InterruptedException e) {}
		}
	}
	
	public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
		// get collapsed node
		FolderNode node = (FolderNode) event.getPath().getLastPathComponent();
		// unload children
		SwingUtilities.invokeLater(new LoadChildrenWorker(node));
	}


	/**
	 * @param showHidden the showHidden to set
	 */
	public void setShowHidden(boolean showHidden) {
		this.showHidden = showHidden;
	}
	

	/**
	 * Thread that loads children of a node
	 */
	class LoadChildrenWorker extends Thread {
		
		private FolderNode parentNode;
		
		/**
		 * @param parentNode - node for which load children
		 */
		public LoadChildrenWorker(FolderNode parentNode) {
			this.parentNode = parentNode;
		}
		
		@Override
		public void run() {
			File[] listFiles;
			FileFilter dirFilter;
			if (showHidden) {
				dirFilter = new DirectoryShowHiddenFilter();
			} else {
				dirFilter = new DirectoryFilter();
			}
			// obtain file associated with node and load children directory otherwise
			listFiles = ((File) parentNode.getUserObject()).listFiles(dirFilter);
			if (listFiles != null) {
				// unload previous loaded children
				int childCount = parentNode.getChildCount();
				for (int i = 0; i < childCount; i++) {
					removeNodeFromParent((MutableTreeNode) parentNode.getChildAt(0));
				}
				for (int i = 0; i < listFiles.length; i++) {
					// if current directory has no subdirectory, mark the node as a leaf
					try {
						if (listFiles[i]
								.listFiles(dirFilter)
								.length == 0) {
							// add a leaf
							insertNodeInto(new FolderNode(listFiles[i], false, FileSystemView.getFileSystemView().getSystemIcon(listFiles[i])), parentNode, i);
						} else {
							// add a node
							insertNodeInto(new FolderNode(listFiles[i], true, FileSystemView.getFileSystemView().getSystemIcon(listFiles[i])), parentNode, i);
						}
					} catch (Exception e) {
						insertNodeInto(new FolderNode(listFiles[i], false, FileSystemView.getFileSystemView().getSystemIcon(listFiles[i])), parentNode, i);
					}
				}
			} else {
				// node has no children
				int childCount = parentNode.getChildCount();
				for (int i = 0; i < childCount; i++) {
					removeNodeFromParent((MutableTreeNode) parentNode.getChildAt(0));
				}
				parentNode.setAllowsChildren(false);
			}
		}
	}
	
	/**
	 * Thread that unloads children of a node
	 */
	class UnloadChildrenWorker extends Thread {
		
		private FolderNode node;
		
		/**
		 * @param parentNode - node for which unload children
		 */
		public UnloadChildrenWorker(FolderNode node) {
			this.node = node;
		}
		
		@Override
		public void run() {
			int childCount = node.getChildCount();
			for (int i = 0; i < childCount; i++) {
				removeNodeFromParent((MutableTreeNode) node.getChildAt(0));
			}
		}
	}
}