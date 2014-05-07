package com.moonights.utils;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.ComponentOrientation;
import javax.swing.SwingConstants;
import java.awt.event.KeyEvent;

public class Test extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JButton jButton = null;

	/**
	 * This is the default constructor
	 */
	public Test() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(311, 240);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJButton(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("обть");
			jButton.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jButton.setHorizontalAlignment(SwingConstants.LEFT);
			jButton.setMnemonic(KeyEvent.VK_UNDEFINED);
			jButton.setName("jButton");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return jButton;
	}

}  //  @jve:decl-index=0:visual-constraint="178,8"
