package com.pasdam.gui.swing.folderTree;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
public class FolderTree extends JTree implements TreeWillExpandListener {
	
	private static final long serialVersionUID = 2956083068858428076L;

	/**
	 * Indicates if the tree is performing multiple changes, this is useful in
	 * order to properly handle the change notifications
	 */
	private boolean ignoreChanges = false;
	
	/** Model used to populate the {@link JTree} */
	private final FolderTreeModel model = new FolderTreeModel();
	
	/** Context menu opened on right click on tree elements */
	private FileItemPopupMenu contextMenu;

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
		
		// set event listener
		addMouseListener(new InternalEventHandler());
	}

	/**
	 * Sets whether the hidden folder should be visible or not
	 * 
	 * @param showHidden
	 *            if true the hidden folder are visible in the tree
	 */
	public void setShowHidden(boolean showHidden) {
		if (showHidden != this.model.showHidden()) {
			SwingUtilities.invokeLater(new ReloadModelWorker(showHidden));
		}
	}

	/**
	 * This method select the input folder
	 * 
	 * @param folder
	 *            the folder to select
	 */
	public void setCurrentFolder(File folder) {
		SwingUtilities.invokeLater(new SelectNodeWorker(folder));
    }
	
	/**
	 * Sets the context menu, to open on right click on a folder
	 * 
	 * @param menu
	 *            context menu
	 */
	public void setContextMenu(FileItemPopupMenu menu){
		this.contextMenu = menu;
		add(menu);
	}

	@Override
	public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
		if (!this.ignoreChanges) {
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
			File folder = (File)((DefaultMutableTreeNode) value).getUserObject();
			
			JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
					row, hasFocus);

			// set the filename as label
			String name = folder.getName();
			if (name != null && !name.isEmpty()) {
				label.setText(name);
			} else {
				label.setText(folder.getAbsolutePath());
			}
			// set the system icon
			label.setIcon(FileSystemView.getFileSystemView().getSystemIcon(folder));
			
			return label;
		}
	}
	
	/** Worker used to search and select a node */
	private class SelectNodeWorker implements Runnable {
		
		private File folder;
		
		public SelectNodeWorker(File folder) {
			this.folder = folder;
		}
		
		@Override
		public void run() {
			TreePath path = new TreePath(FolderTree.this.model.loadPath(this.folder).getPath());
			
			// avoid to start loading of children node when expanding (since is
			// done in the line above)
			FolderTree.this.ignoreChanges = true;
			
			// select, expand and scroll to the node
			expandPath(path);
			setSelectionPath(path);
			scrollPathToVisible(path);
			
			// reset "ignoreChanges"
			FolderTree.this.ignoreChanges = false;
		}
	}
	
	/** Worker that loads children of a node */
	private class LoadChildrenWorker implements Runnable {
		
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
	
	/** Worker used to refresh view */
	private class ReloadModelWorker implements Runnable {
		
		/** Indicates whether hidden folders should be listed or not */
		private final boolean showHidden;
		
		/**
		 * Create a worker to update the model
		 * 
		 * @param showHidden()
		 *            if true indicates that the model should contain the hidden
		 *            folders
		 */
		public ReloadModelWorker(boolean showHidden) {
			this.showHidden = showHidden;
		}

		@Override
		public void run() {
			TreePath selectionPath = getSelectionPath();
			if (selectionPath != null) {
				// save currently selected path
				File selectedFolder = (File) ((DefaultMutableTreeNode) selectionPath.getLastPathComponent())
						.getUserObject();
				// change model property
				FolderTree.this.model.setShowHidden(this.showHidden);
				// restore selection
				new SelectNodeWorker(selectedFolder).run();
			}
		}
	}
	
	/** Class that handle internal events */
	private class InternalEventHandler implements MouseListener {

		/** Event ignored */
		@Override
		public void mouseClicked(MouseEvent event) {}
		
		/** Event ignored */
		@Override
		public void mouseEntered(MouseEvent event) {}
		
		/** Event ignored */
		@Override
		public void mouseExited(MouseEvent event) {}
		
		@Override
		public void mousePressed(MouseEvent event) {
			mouseReleased(event);
		}
		
		@Override
		public void mouseReleased(MouseEvent event) {
			if (event.isPopupTrigger()) {
				// show context menu
				if (FolderTree.this.contextMenu != null) {
					Point location = event.getPoint();
					File folder = (File) ((DefaultMutableTreeNode) FolderTree.this.getPathForLocation(location.x, location.y).getLastPathComponent()).getUserObject();
					FolderTree.this.contextMenu.show(folder, FolderTree.this, event.getX(), event.getY());
				}
			}
		}
	}
}
