package com.pasdam.gui.swing.widgets;

import java.awt.Dimension;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

/**
 * Combobox with a popup that wrap the content: the popup can be wider than the
 * combobox is the items are large.
 * 
 * @author paco
 * @version 1.0
 * 
 * @see <a href=
 *      "http://www.jroller.com/santhosh/entry/make_jcombobox_popup_wide_enough">
 *      Santhosh Kumar's Weblog</a>
 */
public class WideComboBox extends JComboBox<String> {

	private static final long serialVersionUID = -5416268998217030602L;
	
	/** Indicates whether the layout operation is in progress or not */
	private boolean layingOut = false;

	/**
	 * Creates a combobox with the specified model
	 * 
	 * @param model model of the combobox
	 */
	public WideComboBox(ComboBoxModel<String> model) {
		super(model);
	}
	
	/** Creates an empty combobox */
	public WideComboBox() {}
	

	public void doLayout() {
		try {
			this.layingOut = true;
			super.doLayout();
			
		} finally {
			this.layingOut = false;
		}
	}

	public Dimension getSize() {
		Dimension dim = super.getSize();
		if (!layingOut) {
			dim.width = Math.max(dim.width, getPreferredSize().width);
		}
		return dim;
	}
}
