package com.pasdam.gui.swing.dragAndDropPanels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTarget;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JPanel;

/**
 * <p>Example of dragging and dropping panels in Java 5.</p>
 * <p>Everything kept in simple class for pursposes of simplicity.</p>
 * @author Bryan E. Smith - bryanesmith[at]gmail.com
 */
public class DragAndDropPanels extends JPanel {

	private static final long serialVersionUID = 9172065194140060413L;

	/** Keep a list of the user-added panels so can re-add */
    private final List<Component> panels;
    
    /**
     * <p>This represents the data that is transmitted in drag and drop.</p>
     * <p>In our limited case with only 1 type of dropped item, it will be a panel object!</p>
     * <p>Note DataFlavor can represent more than classes -- easily text, images, etc.</p>
     */
    private static DataFlavor dragAndDropPanelDataFlavor = null;

    public DragAndDropPanels() {
    	setLayout(new GridLayout());
        // Again, needs to negotiate with the draggable object
        setTransferHandler(new DragAndDropTransferHandler());
        // Create the listener to do the work when dropping on this object!
        setDropTarget(new DropTarget(this, new DragAndDropTargetListener(this)));

        // Create a list to hold all the panels
        panels = new ArrayList<Component>();
    }
    
    @Override
    public Component add(Component comp) {
    	panels.add(comp);
    	relayout();
    	return comp;
    }
    
    @Override
    public Component add(Component comp, int index) {
    	panels.add(index, comp);
    	relayout();
    	return comp;
    }
    
    @Override
    public void remove(Component comp) {
    	panels.remove(comp);
    	relayout();
    }
    
    @Override
    public void remove(int index) {
    	panels.remove(index);
    	relayout();
    }
    
    @Override
    public void removeAll() {
    	panels.clear();
    	relayout();
    }
    
    /**
     * <p>Removes all components from our root panel and re-adds them.</p>
     * <p>This is important for two things:</p>
     * <ul>
     *   <li>Adding a new panel (user clicks on button)</li>
     *   <li>Re-ordering panels (user drags and drops a panel to acceptable drop target region)</li>
     * </ul>
     */
    protected void relayout() {
        // Clear out all previously added items
        super.removeAll();
        
        Dimension dim = new Dimension(5, 5);

        // Add the panels, if any
        for (Component p : panels) {
        	super.add(new Box.Filler(dim, dim, dim));
        	super.add(p);
        }

        this.validate();
        this.repaint();
    }
    
    @Override
    public synchronized MouseListener[] getMouseListeners() {
    	return super.getMouseListeners();
    }

    /**
     * <p>Returns (creating, if necessary) the DataFlavor representing RandomDragAndDropPanel</p>
     * @return
     */
    public static DataFlavor getDragAndDropPanelDataFlavor() throws Exception {
        // Lazy load/create the flavor
        if (dragAndDropPanelDataFlavor == null) {
            dragAndDropPanelDataFlavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=java.awt.Component");
        }
        return dragAndDropPanelDataFlavor;
    }

    /**
     * <p>Returns the List of user-added panels.</p>
     * <p>Note that for drag and drop, these will be cleared, and the panels will be added back in the correct order!</p>
     * @return
     */
    protected List<Component> getChildrenComponent() {
        return panels;
    }
}
