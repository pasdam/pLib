package com.pasdam.gui.swing.panel;

import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class ComponentTitledPane extends JPanel {
	/** */
	private static final long serialVersionUID = -8237863714997362780L;

	protected ComponentTitledBorder border;

	protected JComponent component;

	protected JPanel panel;

	public ComponentTitledPane(JComponent component) {
		this.component = component;
		border = new ComponentTitledBorder(component);
		setBorder(border);
		panel = new JPanel();
		setLayout(null);
		add(component);
		add(panel);
	}

	public JComponent getTitleComponent() {
		return component;
	}

	public void setTitleComponent(JComponent newComponent) {
		remove(component);
		add(newComponent);
		border.setTitleComponent(newComponent);
		component = newComponent;
	}

	public JPanel getContentPane() {
		return panel;
	}

	@Override
	public void doLayout() {
		Insets insets = getInsets();
		Rectangle rect = getBounds();
		rect.x = 0;
		rect.y = 0;

		Rectangle compR = border.getComponentRect(rect, insets);
		component.setBounds(compR);
		rect.x += insets.left;
		rect.y += insets.top;
		rect.width -= insets.left + insets.right;
		rect.height -= insets.top + insets.bottom;
		panel.setBounds(rect);
	}

}