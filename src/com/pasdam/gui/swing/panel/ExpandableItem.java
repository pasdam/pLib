package com.pasdam.gui.swing.panel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.BevelBorder;

import com.pasdam.gui.swing.dragAndDropPanels.DragAndDropPanels;

/**
 * 
 * 
 * @author paco
 * @version 1.0
 */
public class ExpandableItem extends JPanel implements Transferable {
	
	private static final long serialVersionUID = -8618270652322173457L;

	/** Title's panel container */
	private Box titleContainer;
	
	/** Content panel */
	private JPanel contentPanel;
	
	/** Context menu opened on right click on title panel */
	private JPopupMenu contextMenu;

	/** Create the panel */
	public ExpandableItem() {
		// set layout parameters
		setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// create and add title container
		this.titleContainer = Box.createHorizontalBox();
		this.titleContainer.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		this.titleContainer.setMaximumSize(new Dimension(32767, 30));
		add(this.titleContainer);
		
		InternalEventHandler handler = new InternalEventHandler();
		this.titleContainer.addMouseListener(handler);
		this.titleContainer.addMouseMotionListener(handler);

		// TODO: enable drag&drop behavior
		//setTransferHandler(new DragAndDropTransferHandler());
		//addMouseListener(new DragAndDropMouseListener());
	}

	/**
	 * Sets the title component
	 * 
	 * @param component
	 *            title component
	 */
	public void setTitleComponent(Component component) {
		this.titleContainer.removeAll();
		this.titleContainer.add(component);
	}
	
	/**
	 * Sets content panel
	 * 
	 * @param component
	 *            content panel
	 */
	public void setContent(JPanel component) {
		for (int i = getComponentCount() - 1; i > 0; i--) {
			remove(i);
		}
		this.contentPanel = component;
		add(component);
	}
	
	/**
	 * Sets the context menu, to open on right click on title panel
	 * 
	 * @param menu
	 *            context menu
	 */
	public void setContextMenu(JPopupMenu menu){
		this.contextMenu = menu;
		this.titleContainer.add(menu);
	}
	
	/**
	 * Indicates whether the panel is collapsed or not
	 * 
	 * @return true if the panel is collapsed
	 */
	public boolean isCollapsed() {
		return !this.contentPanel.isVisible();
	}
	
	/** Collapses the panel */
	public void collapse() {
		this.contentPanel.setVisible(false);
	}
	
	/** Expand the panel */
	public void expand() {
		this.contentPanel.setVisible(true);
	}
	
	/** Toggle the collapsed state of the panel */
	public void toggleCollapsedState() {
		if (isCollapsed()) {
			expand();
		} else {
			collapse();
		}
	}
	
	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		DataFlavor thisFlavor = null;
		try {
			thisFlavor = DragAndDropPanels.getDragAndDropPanelDataFlavor();
		} catch (Exception ex) {
			return null;
		}
		// For now, assume wants this class... see loadDnD
		if (thisFlavor != null && flavor.equals(thisFlavor)) {
			return ExpandableItem.this;
		}
		return null;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		DataFlavor[] flavors = {null};
		try {
			flavors[0] = DragAndDropPanels.getDragAndDropPanelDataFlavor();
		} catch (Exception ex) {
			return null;
		}
		return flavors;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		DataFlavor[] flavors = {null};
        try {
            flavors[0] = DragAndDropPanels.getDragAndDropPanelDataFlavor();
        } catch (Exception ex) {
            return false;
        }
        for (DataFlavor f : flavors) {
            if (f.equals(flavor)) {
                return true;
            }
        }
		return false;
	}
	
	/**
	 * @author paco
	 * @version 0.1
	 */
	private class InternalEventHandler implements MouseListener,
												  MouseMotionListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.isPopupTrigger()) {
				// show context menu
				if (ExpandableItem.this.contextMenu != null) {
					ExpandableItem.this.contextMenu.show(titleContainer, e.getX(), e.getY());
				}
			} else {
				// toggle content panel visibility
				toggleCollapsedState();
			}
		}
		
		/** Event ignored */
		@Override
		public void mouseEntered(MouseEvent e) {}
		
		/** Event ignored */
		@Override
		public void mouseExited(MouseEvent e) {}
		
		/** Event ignored */
		@Override
		public void mousePressed(MouseEvent e) {}
		
		/** Event ignored */
		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseDragged(MouseEvent e) {
			// get the original source of the event
			Box box = (Box) e.getSource();

			// replace event's source with this ExpandableItem
			e.setSource(ExpandableItem.this);

			// invoke mouse pressed on the first mouse listener of original
			// source's parent, which is the DragAndDropMouseListener set in the
			// constructor
			box.getParent().getMouseListeners()[0].mousePressed(e);
		}
		
		/** Event ignored */
		@Override
		public void mouseMoved(MouseEvent e) {}
	}
}
