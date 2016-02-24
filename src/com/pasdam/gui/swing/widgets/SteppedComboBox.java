package com.pasdam.gui.swing.widgets;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

/**
 * Combobox with a popup that wrap the content
 * 
 * @author paco
 * @version 1.0
 */
public class SteppedComboBox extends JComboBox<String> {

	private static final long serialVersionUID = -5416268998217030602L;
	
	/**	Current width of the popup */
	private int popupWidth = 0;
	
	/**
	 * Creates a combobox with the specified model
	 * 
	 * @param model model of the combobox
	 */
	public SteppedComboBox(ComboBoxModel<String> model) {
		super(model);
		
		setUI(new SteppedComboBoxUI());
		updateWidth();
		setRenderer(new DefaultComboBoxRenderer());
	}

	/**
	 * Sets the width of the popup
	 * 
	 * @param width
	 *            width to set
	 */
	public void setPopupWidth(int width) {
		this.popupWidth = width;
	}
	
	/**	Update the width of the popup */
	private void updateWidth(){
		Dimension d = getPreferredSize();
		setPreferredSize(new Dimension(50, d.height));
		setPopupWidth(d.width);
	}

	/**
	 * Returns the size of the popup
	 * 
	 * @return the size of the popup
	 */
	public Dimension getPopupSize() {
		Dimension size = getSize();
		if (this.popupWidth < 1) {
			this.popupWidth = size.width;
		}
		return new Dimension(this.popupWidth, size.height);
	}
	
	/** Default UI component */
	private class SteppedComboBoxUI extends BasicComboBoxUI {
		
		@Override
		protected ComboPopup createPopup() {
			BasicComboPopup popup = new BasicComboPopup(comboBox) {

				private static final long serialVersionUID = 6566553935228556187L;

				public void show() {
					Dimension popupSize = ((SteppedComboBox) comboBox)
							.getPopupSize();
					popupSize
					.setSize(popupSize.width,
							getPopupHeightForRowCount(comboBox
									.getMaximumRowCount()));
					Rectangle popupBounds = computePopupBounds(0, comboBox
							.getBounds().height, popupSize.width, popupSize.height);
					scroller.setMaximumSize(popupBounds.getSize());
					scroller.setPreferredSize(popupBounds.getSize());
					scroller.setMinimumSize(popupBounds.getSize());
					list.invalidate();
					int selectedIndex = comboBox.getSelectedIndex();
					if (selectedIndex == -1) {
						list.clearSelection();
					} else {
						list.setSelectedIndex(selectedIndex);
					}
					list.ensureIndexIsVisible(list.getSelectedIndex());
					setLightWeightPopupEnabled(comboBox.isLightWeightPopupEnabled());

					show(comboBox, popupBounds.x, popupBounds.y);
				}
			};
			popup.getAccessibleContext().setAccessibleParent(comboBox);
			return popup;
		}
	}
	
	/** Renderer used to render list items */
	private class DefaultComboBoxRenderer extends DefaultListCellRenderer {

		private static final long serialVersionUID = 8060945705351777680L;
		
		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {

			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
				if (-1 < index) {
					// set element's tooltip
					list.setToolTipText(list.getModel().getElementAt(index).toString());
				}
				
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
			
			setFont(list.getFont());
			setText((value == null) ? "" : value.toString());
			
			return this;
		}
	}
}
