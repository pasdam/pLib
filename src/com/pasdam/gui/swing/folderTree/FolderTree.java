package com.pasdam.gui.swing.folderTree;

import java.awt.Component;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

/**
 * A JTree with a list of filesystem folders
 * @author Paco
 * @version 1.0
 */
@SuppressWarnings("serial")
public class FolderTree extends JTree {

	/**
	 * Create the panel.
	 */
	public FolderTree() {
		// create JTree element
		setCellRenderer(new MyTreeCellRenderer());
		final FolderTreeModel model = new FolderTreeModel();
		addTreeWillExpandListener(model);
		setModel(model);
		setRootVisible(false);
	}

	/**
	 * This method select the input folder
	 * @param folder - the folder to select
	 */
	public void setCurrentFolder(File folder){
		SwingUtilities.invokeLater(new SelectNodeWorker(folder));
    }
	
	/** Cells renderer */
	class MyTreeCellRenderer extends DefaultTreeCellRenderer {
		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean sel, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {
			JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
					row, hasFocus);
//			label.setIcon(((FolderNode) value).getIcon());
			label.setIcon(FileSystemView.getFileSystemView().getSystemIcon(((File )((FolderNode) value).getUserObject())));
			return label;
		}
	}
	
	/** Thread used to search and select a node*/
	class SelectNodeWorker extends Thread {
		
		private File folder;
		
		public SelectNodeWorker(File folder) {
			this.folder = folder;
		}
		
		@Override
		public void run() {
			File selectedNode;
			// getting selected node (or root whether no node is selected)
			try {
				selectedNode = (File) ((FolderNode) getSelectionPath().getLastPathComponent()).getUserObject();
			} catch (Exception e) {
				selectedNode = (File) ((FolderNode) ((FolderNode) getModel().getRoot()).getFirstChild()).getUserObject();
			}
			TreePath treePath = null;
			if (folder == null) {
				// select the root
				setSelectionPath(treePath);
				scrollPathToVisible(treePath);
				expandPath(treePath);
			} else if (!selectedNode.equals(folder)) {
				// if the folder is not already selected perform a search
				FolderTreeModel model = (FolderTreeModel) getModel();
				FolderNode currentNode;
				if (selectedNode.getAbsolutePath().startsWith(folder.getAbsolutePath())) {
					// if the folder is in the path of selected node, perform a backward search
					currentNode = (FolderNode) ((FolderNode) getSelectionPath().getLastPathComponent()).getParent();
					while (!folder.equals((File) currentNode.getUserObject())) {
						currentNode = (FolderNode) currentNode.getParent();
					}
					treePath = new TreePath(currentNode.getPath());
					setSelectionPath(treePath);
					scrollPathToVisible(treePath);
					expandPath(treePath);
				} else {
					// start search from selected node if it is in the path of the folder to select, otherwise from the root
					TreePath selectionPath = getSelectionPath();
					if (folder.getAbsolutePath().startsWith(selectedNode.getAbsolutePath()) && selectionPath!=null) {
						currentNode = (FolderNode) selectionPath.getLastPathComponent();
					} else {
						currentNode = (FolderNode) model.getRoot();
					}
					FolderNode currentChild;
					String currentChildPath;
					int childCount = currentNode.getChildCount();
					int i = 0;
					String folderPath = folder.getAbsolutePath();
					while ((childCount-i) > 0) {
						currentChild = (FolderNode) currentNode.getChildAt(i);
						currentChildPath = ((File) currentChild.getUserObject()).getAbsolutePath();
						if (folder.equals((File) currentChild.getUserObject())) {
							// select
							treePath = new TreePath(currentChild.getPath());
							System.out.println(treePath);
							setSelectionPath(treePath);
							scrollPathToVisible(treePath);
							expandPath(treePath);
							break;
						} else if (folderPath.startsWith(currentChildPath)) {
							// change folder
							currentNode = currentChild;
							treePath = new TreePath(currentNode.getPath());
							setSelectionPath(treePath);
							scrollPathToVisible(treePath);
							expandPath(treePath);
							childCount = currentNode.getChildCount();
							i = 0;
						} else {
							// ignore folder
							i++;
						}
					}
				}
			}
		}
	}
}
