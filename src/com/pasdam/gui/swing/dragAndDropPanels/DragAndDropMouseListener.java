package com.pasdam.gui.swing.dragAndDropPanels;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 * <p>Listener that make source draggable.</p>
 * 
 * @author paco
 * @version 0.2
 */
public class DragAndDropMouseListener implements MouseListener, MouseMotionListener{

    @Override()
    public void mouseDragged(MouseEvent event) {
        JComponent sourceComponent = (JComponent) event.getSource();

        sourceComponent
        	.getTransferHandler()
        	.exportAsDrag(sourceComponent, event, TransferHandler.COPY);
    }

	@Override
	public void mouseMoved(MouseEvent event) {}

	@Override
	public void mouseClicked(MouseEvent event) {
		// dispatch event to the parent, this is done in order
		// to avoid block events
		event.getComponent().getParent().dispatchEvent(event);
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		mouseClicked(event);
	}

	@Override
	public void mouseExited(MouseEvent event) {
		mouseClicked(event);
	}

	@Override
	public void mousePressed(MouseEvent event) {
		mouseClicked(event);
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		mouseClicked(event);
	}
}