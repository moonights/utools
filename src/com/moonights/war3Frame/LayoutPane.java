package com.moonights.war3Frame;

import java.awt.ComponentOrientation;
import java.awt.Rectangle;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.moonights.war3replays.War3ReplaysDowner;
/**
 * 
 * @author moonights
 *
 */
public class LayoutPane extends JDesktopPane {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel = null;
	private JTextField jTextField_DOWNLOAD_MAIN_URL = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JComboBox jComboBox = null;
	private JProgressBar jProgressBar = null;
	private JTextField jText_FILE_SAVE_PATH = null;
	private JButton jButton_FILE_SAVE_PATH = null;
	private JLabel jLabel_FILE_SAVE_TYPE = null;
	private JTextField jTextField_FILE_SAVE_TYPE = null;
	private JButton jButton_Submit = null;
	/**
	 * This is the default constructor
	 */
	public LayoutPane() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel_FILE_SAVE_TYPE = new JLabel();
		jLabel_FILE_SAVE_TYPE.setBounds(new Rectangle(30, 167, 91, 18));
		jLabel_FILE_SAVE_TYPE.setVerticalAlignment(SwingConstants.BOTTOM);
		jLabel_FILE_SAVE_TYPE.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		jLabel_FILE_SAVE_TYPE.setText("文件格式：");
		jLabel2 = new JLabel();
		jLabel2.setBounds(new Rectangle(30, 30, 91, 21));
		jLabel2.setText("下载主页面：");
		jLabel1 = new JLabel();
		jLabel1.setText("录像文件版本：");
		jLabel1.setBounds(new Rectangle(30, 120, 91, 21));
		jLabel = new JLabel();
		jLabel.setBounds(new Rectangle(30, 75, 91, 21));
		jLabel.setToolTipText("a");
		jLabel.setText("录像保存路径：");
		jLabel.setName("aa");
		this.setSize(318, 285);

		this.add(jLabel, null);
		this.add(getJTextField_DOWNLOAD_MAIN_URL(), null);
		this.add(jLabel1, null);
		this.add(jLabel2, null);
		this.add(getJComboBox(), null);
		this.add(getJProgressBar(), null);
		this.add(getJText_FILE_SAVE_PATH(), null);
		this.add(getJButton_FILE_SAVE_PATH(), null);
		this.add(jLabel_FILE_SAVE_TYPE, null);
		this.add(getJTextField_FILE_SAVE_TYPE(), null);
		this.add(getJButton_Submit(), null);
	}

	/**
	 * This method initializes jTextField_DOWNLOAD_MAIN_URL	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_DOWNLOAD_MAIN_URL() {
		if (jTextField_DOWNLOAD_MAIN_URL == null) {
			jTextField_DOWNLOAD_MAIN_URL = new JTextField();
			jTextField_DOWNLOAD_MAIN_URL.setBounds(new Rectangle(135, 30, 151, 22));
			jTextField_DOWNLOAD_MAIN_URL.setText("http://w3g.replays.net/ReplayList.aspx?GameRace=2&PageNo=22");
		}
		return jTextField_DOWNLOAD_MAIN_URL;
	}

	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(135, 120, 151, 22));
			jComboBox.addItem("1.20");
			jComboBox.addItem("1.21");
			jComboBox.addItem("1.22");
			jComboBox.addItem("1.23");
			jComboBox.addItem("1.24");
			jComboBox.setToolTipText("1");
			jComboBox.setMaximumRowCount(4);
			jComboBox.setName("file_version");
			jComboBox.setEditable(true);
		}
		return jComboBox;
	}

	/**
	 * This method initializes jProgressBar	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	private JProgressBar getJProgressBar() {
		if (jProgressBar == null) {
			jProgressBar = new JProgressBar();
			jProgressBar.setBounds(new Rectangle(0, 270, 316, 14));
		}
		return jProgressBar;
	}

	/**
	 * This method initializes jText_FILE_SAVE_PATH	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJText_FILE_SAVE_PATH() {
		if (jText_FILE_SAVE_PATH == null) {
			jText_FILE_SAVE_PATH = new JTextField();
			jText_FILE_SAVE_PATH.setBounds(new Rectangle(135, 79, 91, 22));
		}
		return jText_FILE_SAVE_PATH;
	}

	/**
	 * This method initializes jButton_FILE_SAVE_PATH	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_FILE_SAVE_PATH() {
		if (jButton_FILE_SAVE_PATH == null) {
			jButton_FILE_SAVE_PATH = new JButton();
			jButton_FILE_SAVE_PATH.setBounds(new Rectangle(240, 75, 46, 22));
			jButton_FILE_SAVE_PATH.setText("浏览");
			jButton_FILE_SAVE_PATH.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("...............actionPerformed() begin..............."); // TODO Auto-generated Event stub actionPerformed()
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new java.io.File("."));
				    chooser.setDialogTitle("choosertitle");
				    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				    chooser.setAcceptAllFileFilterUsed(false);
				    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				        System.out.println("当前文件目录: " + chooser.getCurrentDirectory());
				        System.out.println("选择的文件目录: " + chooser.getSelectedFile());
				        jText_FILE_SAVE_PATH.setText(chooser.getSelectedFile().toString());
				    } else {
				        System.out.println("没有选择目录文件");
				        jText_FILE_SAVE_PATH.setText("没有选择目录文件");
				      }
				}
			});
		}
		return jButton_FILE_SAVE_PATH;
	}

	/**
	 * This method initializes jTextField_FILE_SAVE_TYPE	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_FILE_SAVE_TYPE() {
		if (jTextField_FILE_SAVE_TYPE == null) {
			jTextField_FILE_SAVE_TYPE = new JTextField();
			jTextField_FILE_SAVE_TYPE.setBounds(new Rectangle(135, 165, 151, 22));
			jTextField_FILE_SAVE_TYPE.setText(".w3g");
		}
		return jTextField_FILE_SAVE_TYPE;
	}

	/**
	 * This method initializes jButton_Submit	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_Submit() {
		if (jButton_Submit == null) {
			jButton_Submit = new JButton();
			jButton_Submit.setBounds(new Rectangle(105, 210, 91, 31));
			jButton_Submit.setText("下载");
			jButton_Submit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String indexUrl="http://w3g.replays.net";
					String mainUrl=jTextField_DOWNLOAD_MAIN_URL.getText();
					String saveVersion=jComboBox.getSelectedItem().toString();
					String saveType=jTextField_FILE_SAVE_TYPE.getText();
					String savePath=jText_FILE_SAVE_PATH.getText();		
					War3ReplaysDowner downer=new War3ReplaysDowner(indexUrl,mainUrl,saveVersion,saveType,savePath);
					System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<actionPerformed() begin>>>>>>>>>>>>>>>>>>>>"+saveVersion); // TODO Auto-generated Event stub actionPerformed()
					downer.downWar3Replays_WAY2();
					try {
						Runtime.getRuntime().exec("cmd.exe /c start "+savePath);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<actionPerformed() end>>>>>>>>>>>>>>>>>>>>"); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return jButton_Submit;
	}

}  //  @jve:decl-index=0:visual-constraint="76,82"
