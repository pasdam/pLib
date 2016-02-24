package com.pasdam.gui.swing.dragAndDropPanels;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceMotionListener;
import java.awt.event.InputEvent;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 * <p>Used by both the draggable class and the target for negotiating data.</p>
 * <p>Note that this should be set for both the draggable object and the drop target.</p>
 * @author besmit
 */
@SuppressWarnings("serial")
public class DragAndDropTransferHandler extends TransferHandler implements DragSourceMotionListener {

    public DragAndDropTransferHandler() {
        super();
    }

    /**
     * <p>This creates the Transferable object. In our case, RandomDragAndDropPanel implements Transferable, so this requires only a type cast.</p>
     * @param c
     * @return
     */
    @Override
    public Transferable createTransferable(JComponent c) {
        // TaskInstancePanel implements Transferable
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
    public void exportAsDrag(JComponent arg0, InputEvent arg1, int arg2) {
    	super.exportAsDrag(arg0, arg1, arg2);
    }

    /**
     * <p>This is queried to see whether the component can be copied, moved, both or neither. We are only concerned with copying.</p>
     * @param c
     * @return
     */
    @Override
    public int getSourceActions(JComponent c) {
        if (c instanceof Transferable) {
            return TransferHandler.COPY;
        }
        return TransferHandler.NONE;
    }
}