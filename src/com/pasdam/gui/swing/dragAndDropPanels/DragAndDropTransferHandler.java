package com.pasdam.gui.swing.dragAndDropPanels;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceMotionListener;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 * <p>
 * Used by both the draggable class and the target for negotiating data.
 * </p>
 * <p>
 * Note that this should be set for both the draggable object and the drop
 * target.
 * </p>
 * 
 * @author paco
 * @version 0.2
 */
public class DragAndDropTransferHandler extends TransferHandler implements DragSourceMotionListener {

	private static final long serialVersionUID = 1625546744782678976L;

	@Override
    public Transferable createTransferable(JComponent c) {
        // the JComponent must implement Transferable interface
        if (c instanceof Transferable) {
            Transferable tip = (Transferable) c;
            return tip;
        }
        // Not found
        return null;
    }

    @Override
    public void dragMouseMoved(DragSourceDragEvent dsde) {}

    @Override
    public int getSourceActions(JComponent c) {
		// This is queried to see whether the component can be copied, moved,
		// both or neither. We are only concerned with copying.
        if (c instanceof Transferable) {
            return TransferHandler.COPY;
        }
        return TransferHandler.NONE;
    }
    
    @Override
    public boolean canImport(TransferSupport info) {
    	if (info.isDrop()) {
    		info.setShowDropLocation(true);
        }
    	return super.canImport(info);
    }
}