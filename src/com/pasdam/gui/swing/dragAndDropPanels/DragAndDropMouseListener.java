package com.pasdam.gui.swing.dragAndDropPanels;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 * <p>Listener that make source draggable.</p>
 * <p>
 * Thanks, source modified from:
 * http://www.zetcode.com/tutorials/javaswingtutorial/draganddrop/
 * </p>
 */
public class DragAndDropMouseListener extends MouseAdapter {

    @Override()
    public void mousePressed(MouseEvent event) {
        JComponent sourceComponent = (JComponent) event.getSource();

        sourceComponent
        	.getTransferHandler()
        	.exportAsDrag(sourceComponent, event, TransferHandler.COPY);
    }
}