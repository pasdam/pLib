package com.pasdam.gui.swing.panel;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.CellRendererPane;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 * @author Paco
 * @version 1.0
 */
public class ComponentTitledBorder implements Border, MouseListener, SwingConstants{ 
	int offset = 10; 

	Component comp; 
	JComponent container; 
	Rectangle rect; 
	Border border; 

	/**
	 * @param component to be displayed in the border
	 * @param container for which this border is set
	 * @param border which has to be titled with component
	 */
	public ComponentTitledBorder(Component component, JComponent container, Border border){ 
		this.comp = component; 
		this.container = container; 
		this.border = border; 
		container.setBorder(this);
		container.addMouseListener(this); 
	} 

	@Override
	public boolean isBorderOpaque(){ 
		return true; 
	} 

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height){ 
		Insets borderInsets = border.getBorderInsets(c); 
		Insets insets = getBorderInsets(c); 
		int temp = (insets.top-borderInsets.top)/2; 
		border.paintBorder(comp, g, x, y+temp, width, height-temp); 
//		border.paintBorder(c, g, x, y, width, height); 
//		border.paintBorder(
//				c, 
//				g, 
//				x + borderInsets.left, 
//				y + temp, 
//				width - borderInsets.left - borderInsets.right, 
//				height - temp
//		);
		Dimension size = comp.getPreferredSize(); 
		rect = new Rectangle(offset, 0, size.width, size.height); 
//		SwingUtilities.paintComponent(g, comp, (Container)c, rect); 
		final CellRendererPane cellRendererPane = new CellRendererPane(); 
		cellRendererPane.paintComponent(g, this.comp, (Container) c, this.rect); 
	} 

	@Override
	public Insets getBorderInsets(Component c){ 
		Dimension size = comp.getPreferredSize(); 
		Insets insets = border.getBorderInsets(c); 
		insets.top = Math.max(insets.top, size.height); 
//		insets.left += 4;
//		insets.bottom += 4;
//		insets.right += 4;
		return insets; 
	} 

	private void dispatchEvent(MouseEvent me){ 
		if(rect!=null && rect.contains(me.getX(), me.getY())){ 
			Point pt = me.getPoint(); 
			pt.translate(-offset, 0); 
			comp.setBounds(rect); 
			comp.dispatchEvent(new MouseEvent(comp, me.getID() 
					, me.getWhen(), me.getModifiers() 
					, pt.x, pt.y, me.getClickCount() 
					, me.isPopupTrigger(), me.getButton())); 
			if(!comp.isValid()) 
				container.repaint(); 
		} 
	} 

	@Override
	public void mouseClicked(MouseEvent me){ 
		dispatchEvent(me); 
	} 

	@Override
	public void mouseEntered(MouseEvent me){ 
		dispatchEvent(me); 
	} 

	@Override
	public void mouseExited(MouseEvent me){ 
		dispatchEvent(me); 
	} 

	@Override
	public void mousePressed(MouseEvent me){ 
		dispatchEvent(me); 
	} 

	@Override
	public void mouseReleased(MouseEvent me){ 
		dispatchEvent(me); 
	} 
}