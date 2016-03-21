package com.pasdam.gui.swing.panel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.BevelBorder;

/**
 * Expandable {@link JPanel}, with a title and a content; this latter is visible
 * only when component is expanded.
 * 
 * @author paco
 * @version 1.0
 */
public class ExpandableItem extends JPanel {
	
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
		this.titleContainer.addMouseListener(new InternalEventHandler());
		add(this.titleContainer);
	}

	/**
	 * Sets the title component
	 * 
	 * @param component
	 *            title component
	 */
	public void setTitleComponent(JComponent component) {
		this.titleContainer.removeAll();
		this.titleContainer.setPreferredSize(component.getPreferredSize());
		this.titleContainer.setMaximumSize(component.getMaximumSize());
		this.titleContainer.setMinimumSize(component.getMinimumSize());
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
	
	/** Class that handle internal events */
	private class InternalEventHandler implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent event) {
			toggleCollapsedState();
		}
		
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
				if (ExpandableItem.this.contextMenu != null) {
					ExpandableItem.this.contextMenu.show(ExpandableItem.this.titleContainer, event.getX(), event.getY());
				}
			}
		}
	}
}
