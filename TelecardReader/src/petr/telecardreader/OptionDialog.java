
package petr.telecardreader;

import java.awt.*;
import java.awt.event.*;
//import java.io.File;

import javax.swing.*;


public class OptionDialog {

	JFrame parentFrame;
	JDialog dialog;
	JLabel jlEmpty, jlRemDBAddress, jlRemDBPort, jlRemDBName, jlRemDBUsername, jlRemDBPassword, jlRemDBIdent;
	JTextField jtRemDBAddress, jtRemDBPort, jtRemDBName, jtRemDBUsername, jtRemDBIdent;
	JPasswordField jtRemDBPassword;
	JButton jbutBrowse, jbutSave, jbutCancel;
	ConfigFile configFile;
	DesEncrypter desEncrypter;
	
	JTextField jtLocDBDirectory = new JTextField(40);

	public OptionDialog(JFrame parParent) {

		this.parentFrame = parParent;
		dialog = new JDialog(parParent, "Database settings", Dialog.ModalityType.DOCUMENT_MODAL);
		//dialog.getContentPane().setBounds(new Rectangle(0, 0, 200, 100));
		//dialog.getContentPane().setBounds(20, 50, 200, 100);
		desEncrypter = new DesEncrypter("telecard");
		String fileSeparator = System.getProperty("file.separator");
		String configFileName = System.getProperty("user.home")+fileSeparator+".telecardreader"+fileSeparator+"telecardreader.conf";
		configFile = new ConfigFile(configFileName);
		assemblyContent();
		dialog.pack();
		
	}
	
	public void assemblyContent() {
		
		//Font labelFont = new Font(UIManager.getFont("Label.font").getFontName(), Font.BOLD, 16);
		//jlLocDBDirectory = new JLabel("Directory:");
		jlRemDBAddress = new JLabel("Adresa databáze:");
		jlRemDBAddress.setVerticalAlignment(SwingConstants.TOP);
		jlRemDBAddress.setHorizontalAlignment(SwingConstants.RIGHT);
		jlRemDBPort = new JLabel("Port:");
		jlRemDBPort.setHorizontalAlignment(SwingConstants.RIGHT);
		jlRemDBName = new JLabel("Jméno databáze:");
		jlRemDBName.setHorizontalAlignment(SwingConstants.RIGHT);
		jlRemDBUsername = new JLabel("Uživatelské jméno:");
		jlRemDBUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		jlRemDBIdent = new JLabel("Identifikace uživatele:");
		jlRemDBIdent.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//textfields
		//jtLocDBDirectory.setEnabled(false);
		//jtLocDBDirectory.setText(configFile.getPropertyValue("localDBDirectory"));
		jtRemDBAddress = new JTextField(30);
		jtRemDBAddress.setToolTipText("Adresa vzdálené databáze");
		jtRemDBAddress.setText(configFile.getPropertyValue("remoteDBAddress"));
		jtRemDBPort = new JTextField(30);
		jtRemDBPort.setText(configFile.getPropertyValue("remoteDBPort"));
		jtRemDBName = new JTextField(30);
		jtRemDBName.setText(configFile.getPropertyValue("remoteDBName"));
		jtRemDBUsername = new JTextField(30);
		jtRemDBUsername.setText(configFile.getPropertyValue("remoteDBUsername"));
		jtRemDBIdent = new JTextField(30);
		jtRemDBIdent.setText(configFile.getPropertyValue("remoteDBIdent"));
		
		//buttons
		jbutBrowse = new JButton("Change...");
		jbutSave = new JButton("Uložit");
		jbutSave.setIcon(new ImageIcon(OptionDialog.class.getResource("/javax/swing/plaf/metal/icons/ocean/floppy.gif")));
		jbutCancel = new JButton("Zrušit");
		jbutCancel.setIcon(new ImageIcon(OptionDialog.class.getResource("/javax/swing/plaf/metal/icons/ocean/close.gif")));
		
		// Container
		GridBagLayout gridBag = new GridBagLayout();
		gridBag.columnWeights = new double[]{0.0, 0.0, 0.0, 20.0};
		gridBag.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		gridBag.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBag.columnWidths = new int[]{116, 105, 0, 0};
		Container contentPane = dialog.getContentPane();
		contentPane.setLayout(gridBag);
		
		lblNastavenDatabze = new JLabel("Nastavení databáze");
		lblNastavenDatabze.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNastavenDatabze = new GridBagConstraints();
		gbc_lblNastavenDatabze.insets = new Insets(10, 0, 10, 0);
		gbc_lblNastavenDatabze.gridwidth = 3;
		gbc_lblNastavenDatabze.fill = GridBagConstraints.BOTH;
		gbc_lblNastavenDatabze.gridx = 1;
		gbc_lblNastavenDatabze.gridy = 0;
		dialog.getContentPane().add(lblNastavenDatabze, gbc_lblNastavenDatabze);
		
		//Address
		GridBagConstraints gridCons2 = new GridBagConstraints();
		gridCons2.anchor = GridBagConstraints.NORTH;
		gridCons2.fill = GridBagConstraints.HORIZONTAL;
		gridCons2.gridx = 0;
		gridCons2.gridy = 1;
		gridCons2.insets = new Insets(0, 7, 0, 2);
		contentPane.add(jlRemDBAddress, gridCons2);
		
		GridBagConstraints gridCons3 = new GridBagConstraints();
		gridCons3.anchor = GridBagConstraints.NORTH;
		gridCons3.fill = GridBagConstraints.HORIZONTAL;
		gridCons3.gridx = 1;
		gridCons3.gridy = 1;
		gridCons3.gridwidth = 3;
		gridCons3.insets = new Insets(0, 2, 0, 2);
		contentPane.add(jtRemDBAddress, gridCons3);
		jlRemDBPassword = new JLabel("Heslo:");
		jlRemDBPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//Password
		GridBagConstraints gridCons10 = new GridBagConstraints();
		gridCons10.anchor = GridBagConstraints.NORTH;
		gridCons10.fill = GridBagConstraints.HORIZONTAL;
		gridCons10.gridx = 0;
		gridCons10.gridy = 5;
		gridCons10.insets = new Insets(0, 7, 0, 2);
		contentPane.add(jlRemDBPassword, gridCons10);
		jtRemDBPassword = new JPasswordField(30);
		jtRemDBPassword.setEchoChar('*');
		jtRemDBPassword.setText(desEncrypter.decrypt(configFile.getPropertyValue("remoteDBPassword")));
		
		GridBagConstraints gridCons11 = new GridBagConstraints();
		gridCons11.anchor = GridBagConstraints.NORTH;
		gridCons11.fill = GridBagConstraints.HORIZONTAL;
		gridCons11.gridx = 1;
		gridCons11.gridy = 5;
		gridCons11.gridwidth = 3;
		gridCons11.insets = new Insets(0, 2, 0, 2);
		contentPane.add(jtRemDBPassword, gridCons11);
		
		//Port
		GridBagConstraints gridCons4 = new GridBagConstraints();
		gridCons4.anchor = GridBagConstraints.NORTH;
		gridCons4.fill = GridBagConstraints.HORIZONTAL;
		gridCons4.gridx = 0;
		gridCons4.gridy = 3;
		gridCons4.insets = new Insets(0, 7, 0, 2);
		contentPane.add(jlRemDBPort, gridCons4);
		
		GridBagConstraints gridCons5 = new GridBagConstraints();
		gridCons5.anchor = GridBagConstraints.NORTH;
		gridCons5.fill = GridBagConstraints.HORIZONTAL;
		gridCons5.gridx = 1;
		gridCons5.gridy = 3;
		gridCons5.gridwidth = 3;
		gridCons5.insets = new Insets(0, 2, 0, 2);
		contentPane.add(jtRemDBPort, gridCons5);
		
		//Name
		GridBagConstraints gridCons6 = new GridBagConstraints();
		gridCons6.anchor = GridBagConstraints.NORTH;
		gridCons6.fill = GridBagConstraints.HORIZONTAL;
		gridCons6.gridx = 0;
		gridCons6.gridy = 2;
		gridCons6.insets = new Insets(0, 7, 0, 2);
		contentPane.add(jlRemDBName, gridCons6);
		
		GridBagConstraints gridCons7 = new GridBagConstraints();
		gridCons7.anchor = GridBagConstraints.NORTH;
		gridCons7.fill = GridBagConstraints.HORIZONTAL;
		gridCons7.gridx = 1;
		gridCons7.gridy = 2;
		gridCons7.gridwidth = 3;
		gridCons7.insets = new Insets(0, 2, 0, 2);
		contentPane.add(jtRemDBName, gridCons7);
		
		//Username
		GridBagConstraints gridCons8 = new GridBagConstraints();
		gridCons8.anchor = GridBagConstraints.NORTH;
		gridCons8.fill = GridBagConstraints.HORIZONTAL;
		gridCons8.gridx = 0;
		gridCons8.gridy = 4;
		gridCons8.insets = new Insets(0, 7, 0, 2);
		contentPane.add(jlRemDBUsername, gridCons8);
		
		GridBagConstraints gridCons9 = new GridBagConstraints();
		gridCons9.anchor = GridBagConstraints.NORTH;
		gridCons9.fill = GridBagConstraints.HORIZONTAL;
		gridCons9.gridx = 1;
		gridCons9.gridy = 4;
		gridCons9.gridwidth = 3;
		gridCons9.insets = new Insets(0, 2, 0, 2);
		contentPane.add(jtRemDBUsername, gridCons9);
		
		//db identifikace uzivatele
		GridBagConstraints gridConsiu = new GridBagConstraints();
		gridConsiu.anchor = GridBagConstraints.NORTHEAST;
		gridConsiu.fill = GridBagConstraints.HORIZONTAL;
		gridConsiu.gridx = 0;
		gridConsiu.gridy = 6;
		gridConsiu.insets = new Insets(0, 7, 0, 2);
		contentPane.add(jlRemDBIdent, gridConsiu);
		
		GridBagConstraints gridConsiuv = new GridBagConstraints();
		gridConsiuv.anchor = GridBagConstraints.NORTH;
		gridConsiuv.fill = GridBagConstraints.HORIZONTAL;
		gridConsiuv.gridx = 1;
		gridConsiuv.gridy = 6;
		gridConsiuv.gridwidth = 3;
		gridConsiuv.insets = new Insets(0, 2, 0, 2);
		contentPane.add(jtRemDBIdent, gridConsiuv);
		
		//Buttons
		GridBagConstraints gridCons12 = new GridBagConstraints();
		gridCons12.fill = GridBagConstraints.HORIZONTAL;
		gridCons12.gridx = 1;
		gridCons12.gridy = 7;
		gridCons12.gridwidth = 1;
		gridCons12.insets = new Insets(2, 2, 10, 2);
		contentPane.add(jbutSave, gridCons12);
		
		GridBagConstraints gridCons13 = new GridBagConstraints();
		gridCons13.fill = GridBagConstraints.HORIZONTAL;
		gridCons13.gridx = 2;
		gridCons13.gridy = 7;
		gridCons13.gridwidth = 1;
		gridCons13.insets = new Insets(2, 2, 10, 2);
		contentPane.add(jbutCancel, gridCons13);
		
		/*
        jbutBrowse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	JFileChooser fc = new JFileChooser();
            	fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            	int returnVal = fc.showOpenDialog(dialog);
            	if (returnVal == JFileChooser.APPROVE_OPTION) {
            		File sf = fc.getSelectedFile(); 
            		jtLocDBDirectory.setText(sf.getAbsolutePath());
            	}
            }
        });
        */
        
        jbutCancel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dialog.dispose();
        	}
        });
        
        jbutSave.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		saveProperties();
        	}
        });
        
		dialog.addWindowListener(closeWindow);
		
		
	}
	
	public void show() {

		dialog.setBounds(parentFrame.getX()+50, parentFrame.getY()+50, 400, 200);
		dialog.setVisible(true);

	}

	private static WindowListener closeWindow = new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			e.getWindow().dispose();
		}

	};
	private JLabel lblNastavenDatabze;
	
	public void saveProperties() {
		
		//configFile.setPropertyValue("localDBDirectory", jtLocDBDirectory.getText());
		configFile.setPropertyValue("remoteDBAddress", jtRemDBAddress.getText());
		configFile.setPropertyValue("remoteDBPort", jtRemDBPort.getText());
		configFile.setPropertyValue("remoteDBName", jtRemDBName.getText());
		configFile.setPropertyValue("remoteDBUsername", jtRemDBUsername.getText());
		configFile.setPropertyValue("remoteDBPassword",	desEncrypter.encrypt(new String(jtRemDBPassword.getPassword())));
		configFile.setPropertyValue("remoteDBIdent", jtRemDBIdent.getText());
		configFile.saveProperties();
		dialog.dispose();
		
	}
	
}
