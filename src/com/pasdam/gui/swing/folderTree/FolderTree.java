package com.pasdam.gui.swing.folderTree;

import java.awt.Component;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;

/**
 * A JTree with a list of filesystem folders
 * 
 * @author paco
 * @version 0.1
 */
@SuppressWarnings("serial")
public class FolderTree extends JTree implements TreeWillExpandListener {
	
	/**
	 * Indicates if the tree is performing multiple changes, this is useful in
	 * order to properly handle the change notifications
	 */
	private boolean ignoreChanges = false;
	
	/** Model used to populate the {@link JTree} */
	private final FolderTreeModel model = new FolderTreeModel();

	/** Creates the panel */
	public FolderTree() {
		// set model
		setModel(this.model);

		// set this as listener to manage lazy loading of nodes, during expanding event 
		addTreeWillExpandListener(this);

		// make root node visible: it is either a fake root (in case of multiple
		// filesystem roots) or the only one filesystem root
		setRootVisible(false);
		
		// set renderer of the cell
		setCellRenderer(new FolderTreeCellRenderer());
	}

	/**
	 * This method select the input folder
	 * 
	 * @param folder
	 *            the folder to select
	 */
	public void setCurrentFolder(File folder){
		SwingUtilities.invokeLater(new SelectNodeWorker(folder));
    }

	@Override
	public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
		if (!this.ignoreChanges) {
			System.out.println("FolderTree.treeWillExpand - Node: "
					+ (DefaultMutableTreeNode) event.getPath().getLastPathComponent());
			// load direct descendant of the expanded node
			SwingUtilities.invokeLater(
					new LoadChildrenWorker((DefaultMutableTreeNode) event.getPath().getLastPathComponent()));
		}
	}
	
	@Override
	public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {}
	
	/** Cells renderer */
	private class FolderTreeCellRenderer extends DefaultTreeCellRenderer {
		
		private static final long serialVersionUID = 3749795009098192364L;

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean sel, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {
			JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
					row, hasFocus);
			
			File folder = (File)((DefaultMutableTreeNode) value).getUserObject();
			
			// set the filename as label
			label.setText(folder.getName());
			// set the system icon
			label.setIcon(FileSystemView.getFileSystemView().getSystemIcon(folder));
			
			return label;
		}
	}
	
	/** Thread used to search and select a node */
	private class SelectNodeWorker extends Thread {
		
		private File folder;
		
		public SelectNodeWorker(File folder) {
			this.folder = folder;
		}
		
		@Override
		public void run() {
			DefaultMutableTreeNode node = FolderTree.this.model.loadPath(this.folder);
			TreePath path = new TreePath(node.getPath());
			
			// avoid to start loading of children node when expanding (since is
			// done in the line above)
			FolderTree.this.ignoreChanges = true;
			
			// select, expand and scroll to the node
			expandPath(path);
			setSelectionPath(path);
			scrollPathToVisible(path);
			
			FolderTree.this.ignoreChanges = false;
		}
	}
	
	/** {@link Thread} worker that loads children of a node */
	private class LoadChildrenWorker extends Thread {
		
		/** Parent node of the one for which load the children */
		private DefaultMutableTreeNode parentNode;
		
		/**
		 * Creates a worker thread that loads children of the specified node
		 * 
		 * @param parentNode
		 *            node for which load children
		 */
		public LoadChildrenWorker(DefaultMutableTreeNode parentNode) {
			this.parentNode = parentNode;
		}
		
		@Override
		public void run() {
			FolderTree.this.model.indexSubFolder(this.parentNode, 1, true);
		}
	}
}
