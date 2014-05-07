package com.moonights.war3Frame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private LayoutPane layoutPane = null;

	/**
	 * This is the default constructor
	 */
	public MainFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(325, 311);
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
			jContentPane.add(getLayoutPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes layoutPane	
	 * 	
	 * @return com.moonights.war3Frame.LayoutPane	
	 */
	private LayoutPane getLayoutPane() {
		if (layoutPane == null) {
			layoutPane = new LayoutPane();
		}
		return layoutPane;
	}
	
	public static void main(String args[]){
		MainFrame mainFrame=new MainFrame();
		mainFrame.setVisible(true);
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
