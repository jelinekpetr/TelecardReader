
package petr.telecardreader;

/**
 * @author petr
 *
 */

//Import Statements
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
//import gnu.io.CommPortIdentifier;
import java.util.*;
//import java.lang.Thread;

public class TelecardReader extends JFrame {

	public static String windowTitle = "Telecard reader";
	JTextField jtfStatusBar;
	JTextArea jtAreaOutput;
	JButton jbnButRead, jbnButOpen, jbnButSave, jbnButExit;
	JRadioButton jrbOld, jrbNew;
	JLabel jLabelContent;
	JMenuBar menuBar;
	JMenu menuFile, menuHelp, menuConfig, submenuPort;
	JMenuItem menuFileOpen, menuFileClose, menuFileSave, menuFileExit, submenuConfigurationDB, menuHelpAbout;
	JRadioButtonMenuItem rbReaderPort1, rbReaderPort2, rbReaderPort3, rbReaderPort4;
	Font textFont;
	ButtonGroup groupSerialPorts;
	JFrame dialogFrame;
	
	private DefaultTableModel tableModel;
	private JTable jtTable;


	String newline = "\n";
	String readType = "";
	public String serPortName;
	
	public static String command = "-";
	public static String serialAnswer = null;
	public static Boolean commProcess = true;
	public static String statusRegistr = new String();
	boolean vypsano = false;
	
	public static ArrayList<String> errors = new ArrayList<String>();
	
	boolean portsFound = false;
	SendComm vlakno = null;
	ConfigFile configFile = null;
	String configSerial = null;
	boolean serialSet = false;
	String configFileName = "telecardreader.conf";	
	
	CardDatabase cardDB;
	
	OptionDialog optDialog;

	// Konstruktor
	public TelecardReader() {
		configFile = new ConfigFile(configFileName);
		createGui();
	}

	public void createGui() {
		//Textarea
		jtAreaOutput = new JTextArea(10, 45);
		Font textFont = new Font("Courier", Font.PLAIN, 12);
		jtAreaOutput.setEditable(false);
		jtAreaOutput.setFont(textFont);
		jtAreaOutput.setMargin(new Insets(3,3,3,3)); 
		JScrollPane scrollPane = new JScrollPane(jtAreaOutput,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//Table
		tableModel = new DefaultTableModel(new Object[]{"Parameter","Value"},0);
		jtTable = new JTable();
		jtTable.setEnabled(true);
		jtTable.setModel(tableModel);
		JScrollPane scrollPaneTable = new JScrollPane(jtTable,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneTable.setPreferredSize(new Dimension(400, 180));
		jtTable.setPreferredScrollableViewportSize(new Dimension(400, 180));
		jtTable.setFillsViewportHeight(true);
		
		//READ button
		jbnButRead = new JButton("Read card"); // Read button
		jbnButRead.setEnabled(false);
		//Open button
		jbnButOpen = new JButton("Open card"); // Read button
		jbnButOpen.setEnabled(false);
		//Save button
		jbnButSave = new JButton("Save card"); // Read button
		jbnButSave.setEnabled(false);
		//Exit button
		jbnButExit = new JButton("Konec"); // Exit button
		//Status bar
		jtfStatusBar = new JTextField(30); // Status bar
		jtfStatusBar.setEditable(false);
		//Label Card content
		jLabelContent = new JLabel();
		jLabelContent.setText("Card content:");
		// MENU - menubar
		menuBar = new JMenuBar();
		menuFile = new JMenu("Soubor");
		menuConfig = new JMenu("Nastavení");
		menuHelp = new JMenu("Nápověda");
		menuFile.setMnemonic(KeyEvent.KEY_FIRST);
		menuFile.getAccessibleContext().setAccessibleDescription("Menu soubor");
		menuBar.add(menuFile);
		menuBar.add(menuConfig);
		menuBar.add(menuHelp);
		menuFileOpen = new JMenuItem("Otevřít", KeyEvent.VK_T);
		menuFileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				ActionEvent.CTRL_MASK));
		menuFile.add(menuFileOpen);
		menuFileSave = new JMenuItem("Uložit", KeyEvent.VK_T);
		menuFileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		menuFile.add(menuFileSave);
		menuFileClose = new JMenuItem("Zavřít", KeyEvent.VK_T);
		menuFileClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
				ActionEvent.CTRL_MASK));
		menuFile.add(menuFileClose);
		menuFile.addSeparator();
		menuFileExit = new JMenuItem("Konec", KeyEvent.VK_T);
		menuFileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.CTRL_MASK));
		menuFile.add(menuFileExit);
		
		fillSerialPortsToMenu();
		submenuConfigurationDB = new JMenuItem("Nastavení DB", KeyEvent.VK_T);
		submenuConfigurationDB.addActionListener(confDBListener);
		menuConfig.add(submenuConfigurationDB);
		
		menuHelpAbout = new JMenuItem("O programu...", KeyEvent.VK_T);
		menuHelpAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
				ActionEvent.CTRL_MASK));
		menuHelp.add(menuHelpAbout);
		this.setJMenuBar(menuBar);
		
		// Container
		GridBagLayout gridBag = new GridBagLayout();
		gridBag.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 0.0};
		gridBag.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0};
		Container contentPane = getContentPane();
		contentPane.setLayout(gridBag);
		
		//Read
		GridBagConstraints gridCons1 = new GridBagConstraints();
		gridCons1.fill = GridBagConstraints.HORIZONTAL;
		gridCons1.gridx = 0;
		gridCons1.gridy = 0;
		gridCons1.insets = new Insets(2, 2, 2, 2);
		contentPane.add(jbnButRead, gridCons1);
		
		//Open
		GridBagConstraints gridCons2 = new GridBagConstraints();
		gridCons2.fill = GridBagConstraints.HORIZONTAL;
		gridCons2.gridx = 0;
		gridCons2.gridy = 1;
		gridCons2.insets = new Insets(2, 2, 2, 2);
		contentPane.add(jbnButOpen, gridCons2);
		
		//Save
		GridBagConstraints gridCons3 = new GridBagConstraints();
		gridCons3.anchor = GridBagConstraints.NORTH;
		gridCons3.fill = GridBagConstraints.HORIZONTAL;
		gridCons3.gridx = 0;
		gridCons3.gridy = 2;
		gridCons3.insets = new Insets(2, 2, 2, 2);
		contentPane.add(jbnButSave, gridCons3);
		
		//Exit
		GridBagConstraints gridCons4 = new GridBagConstraints();
		gridCons4.fill = GridBagConstraints.HORIZONTAL;
		gridCons4.gridx = 0;
		gridCons4.gridy = 4;
		gridCons4.insets = new Insets(2, 2, 2, 2);
		gridCons4.anchor = GridBagConstraints.SOUTH;
		contentPane.add(jbnButExit, gridCons4);
		
		//Separatory
		GridBagConstraints gridCons5 = new GridBagConstraints();
		gridCons5.fill = GridBagConstraints.HORIZONTAL;
		gridCons5.gridx = 0;
		gridCons5.gridy = 3;
		gridCons5.insets = new Insets(2, 2, 2, 2);	
		JSeparator sep1 = new JSeparator(SwingConstants.HORIZONTAL);
		contentPane.add(sep1, gridCons5);
		
		GridBagConstraints gridCons6 = new GridBagConstraints();
		gridCons6.fill = GridBagConstraints.HORIZONTAL;
		gridCons6.gridx = 1;
		gridCons6.gridy = 0;
		gridCons6.insets = new Insets(2, 2, 2, 2);	
		gridCons6.gridheight = 5;
		gridCons6.fill = GridBagConstraints.VERTICAL;
		JSeparator sep2 = new JSeparator(SwingConstants.VERTICAL);
		contentPane.add(sep2, gridCons6);
		
		//Status bar
		GridBagConstraints gridCons7 = new GridBagConstraints();
		gridCons7.fill = GridBagConstraints.HORIZONTAL;
		gridCons7.fill = GridBagConstraints.HORIZONTAL;
		gridCons7.anchor = GridBagConstraints.SOUTH;
		gridCons7.gridx = 0;
		gridCons7.gridy = 5;
		gridCons7.insets = new Insets(1, 1, 1, 1);
		gridCons7.gridwidth = 4;
		contentPane.add(jtfStatusBar, gridCons7);
		
		//Textarea
		GridBagConstraints gridCons8 = new GridBagConstraints();
		gridCons8.fill = GridBagConstraints.BOTH;
		gridCons8.gridx = 2;
		gridCons8.gridy = 0;
		gridCons8.insets = new Insets(2, 2, 2, 2);
		gridCons8.gridwidth = 2;
		gridCons8.gridheight = 4;
		gridCons8.fill = GridBagConstraints.VERTICAL;
		gridCons8.fill = GridBagConstraints.HORIZONTAL;
		contentPane.add(scrollPane ,gridCons8); 
		
		//Table
		GridBagConstraints gridCons9 = new GridBagConstraints();
		gridCons9.fill = GridBagConstraints.BOTH;
		gridCons9.gridx = 2;
		gridCons9.gridy = 4;
		gridCons9.insets = new Insets(2, 2, 2, 2);
		gridCons9.gridwidth = 2;
		gridCons9.gridheight = 1;
		gridCons9.fill = GridBagConstraints.VERTICAL;
		gridCons9.fill = GridBagConstraints.HORIZONTAL;
		contentPane.add(scrollPaneTable ,gridCons9);
		
		jbnButExit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				//System.exit(0);
				exitApp();
			}
		});
		
		jbnButRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (commProcess == false) {
					serialAnswer = null;
					jtfStatusBar.setText(null);
					jtAreaOutput.setText(null);
					int rowC = tableModel.getRowCount();
					for (int u = 0; u < rowC; u++) {
						tableModel.removeRow(0);
					}
					jbnButRead.setEnabled(false);
					statusRegistr = null;
					clearTable();
					command = "READ0";
					commProcess = true;
					vypsano = false;
					synchronized(vlakno) {
						try {
							vlakno.notify();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});

		menuFileOpen.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				jtAreaOutput.append("Open selected..." + newline);
			}
		});
		menuHelpAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAboutDialog();
			}
		});
		menuFileExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitApp();
			}
		});
		
		new javax.swing.Timer(300, updateStatusAction).start();
		optDialog = new OptionDialog(this);
		cardDB = new CardDatabase();
		
	} 
	
	////////////////////////// konec createGUI /////////////////////////////////////////////
	
	//Prepinani portu v menu
	ActionListener rbmenuActionListener = new ActionListener() {
		
		public void actionPerformed(ActionEvent actionEvent) {
			
			serPortName = actionEvent.getActionCommand();
			openTestSerialPort();
			if (serPortName.equals(configSerial) == false) {
				configFile.setPropertyValue("serialport", serPortName);
				configFile.saveProperties();
			}
		}
		
	};
	
	
	ActionListener confDBListener = new ActionListener() {
		
		public void actionPerformed(ActionEvent actionEvent) {
			
			optDialog.show();
			
		}
		
	};
	
	public void openTestSerialPort() {
		
		jbnButRead.setEnabled(false);
		jtfStatusBar.setText("");
		statusRegistr = "";
		vlakno = new SendComm(serPortName);
		vlakno.start();
		command = "TEST";
		commProcess = true;
		
	}
	
	public void exitApp() {
		
		if (cardDB != null) {
			cardDB.close();
		}
		System.exit(0);
	}
	
	public void checkContent() {
		
		int tableSize = SendComm.commandTable1.size() ;
		boolean cut = true;
		
		for (int i = 0; i < tableSize/2; i++) {
			if (!SendComm.commandTable1.get(i).getAnswer().equals(SendComm.commandTable1.get(i+tableSize/2).getAnswer())) {
				cut = false;
				break;
			}
		}
		if (cut) {
			for (int j = tableSize/2; j < tableSize; j++) {
				SendComm.commandTable1.remove(SendComm.commandTable1.size()-1);
			}
		}
	}
	
	public void vypisTabuli() {
		
		int k = 0;
		String[] binVal = new String[4];
		String hexVal;
		String content = "";

		for (int i = 0; i < SendComm.commandTable1.size(); i++) {
			if (k == 0) {
				content += (String.format("%2s", i) + " "+("000"+Integer.toString(i*8)).substring(("000"+Integer.toString(i*8)).length()-4)+":");
			}
			binVal[k] = Integer.toBinaryString(Integer.parseInt(SendComm.commandTable1.get(i).getAnswer()));
			hexVal = Integer.toHexString(Integer.parseInt(SendComm.commandTable1.get(i).getAnswer())).toUpperCase();
			content += (" " + ("0" + hexVal).substring(("0" + hexVal).length()-2));
			k++;
			if (k == 4) {
				content += (" |");
				for (int j = 0; j < binVal.length; j++) {
					content += (" " + ("0000000" + binVal[j]).substring(("0000000" + binVal[j]).length()-8));
				}
				content += (newline);
				k = 0;
			}
		}
		jtAreaOutput.append(content);
	}
	
	public void clearTable() {

		SendComm.commandTable1.clear();
	}
	
	public void decodeCard() {
		
		ArrayList<String> cardContent = new ArrayList<String>();				
		for (int i = 0; i < SendComm.commandTable1.size(); i++) {
			cardContent.add(i, SendComm.commandTable1.get(i).getAnswer());
		}
		TelephoneCard telCard = new TelephoneCard(cardContent);
		tableModel.addRow(new Object[]{"Card name:", telCard.getCardName()});
		tableModel.addRow(new Object[]{"Card country:", telCard.getCardCountry()});
		tableModel.addRow(new Object[]{"Card usage:", telCard.getCardUsage()});
		tableModel.addRow(new Object[]{"Card issuer:", telCard.getCardIssuer()});
		tableModel.addRow(new Object[]{"Card checksum(s):", telCard.getCardChecksumState()});
		for (TelephoneCard.InfoTable infoFields : telCard.infoTable) {
			tableModel.addRow(new Object[]{infoFields.parameter+":", infoFields.value});
		}
		
	}
	
	public static void recError(String errorText) {
		errors.add(errorText);
	}
	
	Action updateStatusAction = new AbstractAction() {
		
		public void actionPerformed(ActionEvent e) {
			
			if (command == "TEST") {	
				if (statusRegistr.equals("TEST: OK")) {
					jtfStatusBar.setText("Reader found. Status: " + statusRegistr);
					jbnButRead.setEnabled(true);
				}
			}
			if ((command == "READ0") || (command == "READ1") || (command == "-")) {
				jtfStatusBar.setText(statusRegistr);
			}
			if (jtfStatusBar.getText().equals("Receiving finished.") && (vypsano == false)) {
				checkContent();
				vypisTabuli();
				vypsano = true;
				decodeCard();
				jbnButRead.setEnabled(true);
			}
			if (errors.size() > 0) {
				showErrorDialog(errors.get(0));
				errors.remove(0);
			}
			/*
			if (portsFound == false) {
				fillSerialPortsToMenu();
			}
			*/
	   }
	};
	
	ActionListener rbTypeCardListener = new ActionListener() {
		public void actionPerformed(ActionEvent actionEvent) {
			readType = actionEvent.getActionCommand();
		}
	};
	
	public void showAboutDialog() {
		String aboutMessage = "Tento program vám přinesl Petr..."
				+ newline + "...a nedává na něho žádnou záruku.";
		JFrame dialogFrame = new JFrame();
		dialogFrame.setVisible(false);
		JOptionPane.showMessageDialog(dialogFrame, aboutMessage,
				"About this program", JOptionPane.INFORMATION_MESSAGE);
	}

	public void showErrorDialog(String errorText) {
		JFrame dialogFrame = new JFrame();
		dialogFrame.setVisible(false);
		JOptionPane.showMessageDialog(dialogFrame, errorText,
				"Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public void fillSerialPortsToMenu() {
		
		//
		/*

		try {
		    ArrayList<CommPortIdentifier> porty = new ListSerialPorts().getSeriovePorty();
		    jtfStatusBar.setText("Getting serial ports list...");
		    if (porty.size() > 0) {
			    for (CommPortIdentifier i : porty) {
			    	rbReaderPort1 = new JRadioButtonMenuItem(i.getName());
					submenuPort.add(rbReaderPort1);
					groupSerialPorts.add(rbReaderPort1);
					rbReaderPort1.addActionListener(rbmenuActionListener);
			    }
			    menuConfig.add(submenuPort);
				jtfStatusBar.setText("Serial port(s) found");
				portsFound = true;
		    }
		    else {
		    	jtfStatusBar.setForeground(Color.RED);
				jtfStatusBar.setText("Serial port not found.");
		    }
		} catch (Exception ex) {
			jtfStatusBar.setForeground(Color.RED);
			jtfStatusBar.setText("Serial port not found.");
		}
		*/
		
		submenuPort = new JMenu("Port čtečky");
		groupSerialPorts = new ButtonGroup();
		
		if (System.getProperty("os.name").toUpperCase().contains("LINUX")) {
			for (int i = 0; i < 4; i++) {
				rbReaderPort1 = new JRadioButtonMenuItem("/dev/ttyS"+i);
				submenuPort.add(rbReaderPort1);
				groupSerialPorts.add(rbReaderPort1);
				rbReaderPort1.addActionListener(rbmenuActionListener);
			}
			for (int j = 0; j < 4; j++) {
				rbReaderPort1 = new JRadioButtonMenuItem("/dev/ttyUSB"+j);
				submenuPort.add(rbReaderPort1);
				groupSerialPorts.add(rbReaderPort1);
				rbReaderPort1.addActionListener(rbmenuActionListener);
			}
		}
		if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
			for (int i = 1; i <= 4; i++) {
				rbReaderPort1 = new JRadioButtonMenuItem("COM"+i);
				submenuPort.add(rbReaderPort1);
				groupSerialPorts.add(rbReaderPort1);
				rbReaderPort1.addActionListener(rbmenuActionListener);
			}
		}
		menuConfig.add(submenuPort);
				
	}

	public void setSerialPortAccordingToConfig() {
		
		configSerial = configFile.getPropertyValue("serialport");
		if (!configSerial.isEmpty()) {
			serialSet = true;
			Enumeration<AbstractButton> rbenu = groupSerialPorts.getElements(); 
			while (rbenu.hasMoreElements()) {
				AbstractButton ser = rbenu.nextElement();
				if (ser.getText().equals(configSerial)) {
					ser.doClick();
				}
			}
		}

	}
		
	
	public static void main(String[] args) {

		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (InstantiationException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		//UIManager.put("Label.font", new Font("Courier", Font.PLAIN, 12));

		TelecardReader telecardReader = new TelecardReader();
		telecardReader.pack();
		telecardReader.setTitle(windowTitle);
		//telecardReader.setPreferredSize(new Dimension(500, 500));
		//telecardReader.setResizable(false);

		telecardReader.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				//telecardReader.exitApp();
				System.exit(0);
				//exitApp();
			}
			
		});
		telecardReader.setVisible(true);
		telecardReader.setSerialPortAccordingToConfig();
	}

}
