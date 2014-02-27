
/*
 * 
 */

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
import java.io.*;
//import gnu.io.CommPortIdentifier;
import java.sql.SQLException;
import java.util.*;
import java.lang.reflect.InvocationTargetException;
import java.net.*;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
//import java.lang.Thread;

public class TelecardReader extends JFrame {

	public static String windowTitle = "Telecard reader";
	public static JTextField jtfStatusBar;
	public static boolean DOWNLOAD_IN_PROGRESS = false;
	JTextArea jtAreaOutput;
	JButton jbnButRead, jbnButSave, jbnButExit;
	JRadioButton jrbOld, jrbNew;
	JLabel jLabelContent;
	JMenuBar menuBar;
	JMenu menuFile, menuHelp, menuConfig, submenuPort;
	JMenuItem menuFileOpen, menuFileClose, menuFileSave, menuFileExit, submenuConfigurationDB, menuHelpAbout, menuConfigurationConnReader, menuConfigurationConnDB;
	JRadioButtonMenuItem rbReaderPort1, rbReaderPort2, rbReaderPort3, rbReaderPort4;
	Font textFont;
	ButtonGroup groupSerialPorts;
	JFrame dialogFrame;
	
	private JButton btnNajt;
	private JLabel lblKatalog;
	private JLabel lblKatalogCislo;
	private JLabel lblSerialCard;
	private JLabel lblZeme;
	private JLabel lblStat;
	private JTextField textSerialCard;
	private JTextField textKatalogCislo;
	private JTextField textCountry;
	private JTextField textState;
	private JLabel lblSerialChip;
	private JTextField textSerialChip;
	private JCheckBox checkBoxNezeme;
	private JLabel lblVyrobce;
	private JTextField textManufacturer;
	private JLabel lblVydavatel;
	private JTextField textIssuer;
	private JSeparator separator;
	private JTable table;
	private JScrollPane scrollPane_1;
	private JTabbedPane tabbedPane;
	
	private JProgressBar downloadProgressBar;
	private Download download;
	private JFrame downloadFrame;
	
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
	
	public static ArrayList<ErrorRecord> errors = new ArrayList<ErrorRecord>();
	public static ArrayList<ComRes> commandTable1 = new ArrayList<ComRes>();
	
	boolean portsFound = false;
	//SendComm vlakno = null;
	ConfigFile configFile = null;
	String configSerial = null;
	boolean serialSet = false;
	MySerialPort serialPortak;
	
	public static Country selectedCountry;
	private JComboBox<Country> comboBox;
	private JLabel lblPoznmka;
	private JTextField textNote;
	private JComboBox<String> comboBoxKatalog;
	private DefaultTableModel cardTableModel;
	
	CardDatabase cardDB;
	OptionDialog optDialog;
	DBCountry dbSelCountry;
	ArrayList<Country> arrayCountry;
	DBKatalogy Katalogy;
	ArrayList<String> katalogy;
	CardDBSearch cardSearch;
	
	CardDBRecord dbRec = null;
	DBRecordEditor recEditor = null;

	// Konstruktor
	public TelecardReader() {
		
		cardDB = new CardDatabase();
		createGui();
		configFile = new ConfigFile();
		optDialog = new OptionDialog(this);
		//checkNewVersion();
		
	}
	
	public void createGui() {
		//Textarea
		jtAreaOutput = new JTextArea(10, 60);
		//Font textFont = new Font("Courier", Font.PLAIN, 14);
		jtAreaOutput.setEditable(false);
		jtAreaOutput.setFont(new Font("Courier New", Font.PLAIN, 14));
		jtAreaOutput.setMargin(new Insets(3,3,3,3)); 
		//JScrollPane scrollPane = new JScrollPane(jtAreaOutput,
			//	JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			//	JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setViewportView(jtAreaOutput);
		//Table
		tableModel = new DefaultTableModel(new Object[]{"Parametr","Hodnota"},0);
		jtTable = new JTable();
		jtTable.setEnabled(true);
		jtTable.setModel(tableModel);
		JScrollPane scrollPaneTable = new JScrollPane();
		scrollPaneTable.setViewportBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPaneTable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneTable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneTable.setViewportView(jtTable);
		
		//READ button
		jbnButRead = new JButton("Přečíst kartu"); // Read button
		jbnButRead.setEnabled(false);
		//Save button
		jbnButSave = new JButton("Uložit kartu"); // Save button
		jbnButSave.setEnabled(false);
		//Exit button
		jbnButExit = new JButton("Konec"); // Exit button
		//Status bar
		jtfStatusBar = new JTextField(40); // Status bar
		jtfStatusBar.setHorizontalAlignment(SwingConstants.LEFT);
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
		
		menuConfigurationConnReader = new JMenuItem("Znovu připojit čtečku", KeyEvent.VK_T);
		menuConfig.add(menuConfigurationConnReader);
		
		menuConfigurationConnDB = new JMenuItem("Znovu připojit k databázi");
		menuConfig.add(menuConfigurationConnDB);
		
		menuHelpAbout = new JMenuItem("O programu...", KeyEvent.VK_T);
		menuHelpAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
				ActionEvent.CTRL_MASK));
		menuHelp.add(menuHelpAbout);
		this.setJMenuBar(menuBar);
		
		//Container
		GridBagLayout gridBag = new GridBagLayout();
		gridBag.columnWeights = new double[]{1.0};
		gridBag.rowWeights = new double[]{1.0, 0.0, 0.0};
		Container contentPane = getContentPane();
		contentPane.setLayout(gridBag);
		
		//Tabbed pane
		GridBagConstraints gridConsTabbed = new GridBagConstraints();
		tabbedPane = new JTabbedPane();
		gridConsTabbed.gridx = 0;
		gridConsTabbed.gridy = 0;
		gridConsTabbed.fill = GridBagConstraints.BOTH;
		contentPane.add(tabbedPane, gridConsTabbed);
		
		//Panel
		JPanel jpPanelTab1 = new JPanel();
		tabbedPane.addTab("Čtečka", jpPanelTab1);
		tabbedPane.setEnabledAt(0, true);
		GridBagLayout gbl_jpPanelTab1 = new GridBagLayout();
		gbl_jpPanelTab1.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_jpPanelTab1.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0};
		gbl_jpPanelTab1.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0};
		jpPanelTab1.setLayout(gbl_jpPanelTab1);
		
		//Read
		GridBagConstraints gridCons1 = new GridBagConstraints();
		gridCons1.anchor = GridBagConstraints.NORTH;
		gridCons1.fill = GridBagConstraints.HORIZONTAL;
		gridCons1.gridx = 0;
		gridCons1.gridy = 0;
		gridCons1.insets = new Insets(2, 2, 2, 2);
		jpPanelTab1.add(jbnButRead, gridCons1);
		
		//Save
		GridBagConstraints gridCons3 = new GridBagConstraints();
		gridCons3.anchor = GridBagConstraints.NORTH;
		gridCons3.fill = GridBagConstraints.HORIZONTAL;
		gridCons3.gridx = 0;
		gridCons3.gridy = 1;
		gridCons3.insets = new Insets(2, 2, 2, 2);
		jpPanelTab1.add(jbnButSave, gridCons3);
		
		//Separatory
		GridBagConstraints gridCons5 = new GridBagConstraints();
		gridCons5.fill = GridBagConstraints.HORIZONTAL;
		gridCons5.gridx = 0;
		gridCons5.gridy = 3;
		gridCons5.insets = new Insets(2, 2, 2, 2);	
		JSeparator sep1 = new JSeparator(SwingConstants.HORIZONTAL);
		jpPanelTab1.add(sep1, gridCons5);
		
		GridBagConstraints gridCons6 = new GridBagConstraints();
		gridCons6.fill = GridBagConstraints.VERTICAL;
		gridCons6.gridx = 1;
		gridCons6.gridy = 0;
		gridCons6.insets = new Insets(2, 2, 2, 2);	
		gridCons6.gridheight = 5;
		JSeparator sep2 = new JSeparator(SwingConstants.VERTICAL);
		jpPanelTab1.add(sep2, gridCons6);
		
		//Exit
		GridBagConstraints gridCons4 = new GridBagConstraints();
		gridCons4.anchor = GridBagConstraints.EAST;
		gridCons4.gridx = 0;
		gridCons4.gridy = 1;
		gridCons4.insets = new Insets(2, 2, 2, 2);
		contentPane.add(jbnButExit, gridCons4);
		
		//Status bar
		GridBagConstraints gridConss = new GridBagConstraints();
		gridConss.fill = GridBagConstraints.BOTH;
		gridConss.gridx = 0;
		gridConss.gridy = 2;
		gridConss.insets = new Insets(1, 1, 1, 1);
		gridConss.gridwidth = 1;
		contentPane.add(jtfStatusBar, gridConss);
		
		//Textarea
		GridBagConstraints gridCons8 = new GridBagConstraints();
		gridCons8.fill = GridBagConstraints.BOTH;
		gridCons8.gridx = 2;
		gridCons8.gridy = 0;
		gridCons8.insets = new Insets(2, 2, 2, 2);
		gridCons8.gridwidth = 2;
		gridCons8.gridheight = 4;
		jpPanelTab1.add(scrollPane ,gridCons8); 
		
		//Table
		GridBagConstraints gridCons9 = new GridBagConstraints();
		gridCons9.fill = GridBagConstraints.BOTH;
		gridCons9.gridx = 2;
		gridCons9.gridy = 4;
		gridCons9.insets = new Insets(2, 2, 2, 2);
		gridCons9.gridwidth = 2;
		gridCons9.gridheight = 1;
		jpPanelTab1.add(scrollPaneTable ,gridCons9);
		
		//////////////////////////////////////////////////
		
		JPanel jpPanelTab2;
		jpPanelTab2 = new JPanel();
		tabbedPane.addTab("Databáze", null, jpPanelTab2, null);
		GridBagLayout gbl_jpPanelTab2 = new GridBagLayout();
		gbl_jpPanelTab2.columnWidths = new int[]{0, 150, 0, 0, 0, 150, 0};
		gbl_jpPanelTab2.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 50, 0};
		gbl_jpPanelTab2.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_jpPanelTab2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		jpPanelTab2.setLayout(gbl_jpPanelTab2);
		tabbedPane.setEnabledAt(1, true);
		
		class ArrayListComboBoxModel extends AbstractListModel<Country> implements ComboBoxModel<Country> {
			private Object selectedItem;

			private ArrayList<Country> anArrayList;

			public ArrayListComboBoxModel(ArrayList<Country> arrayList) {
				anArrayList = arrayList;
			}

			public Object getSelectedItem() {
				return selectedItem;
			}

			public void setSelectedItem(Object newValue) {
				selectedItem = newValue;
			}

			public int getSize() {
				return anArrayList.size();
			}

			public Country getElementAt(int i) {
				return anArrayList.get(i);
			}

		}
		
		arrayCountry = new ArrayList<Country>();
		dbSelCountry = new DBCountry(cardDB.connection(), 1);
		arrayCountry = dbSelCountry.getAll();
		arrayCountry.add(null);
		ArrayListComboBoxModel comboModel = new ArrayListComboBoxModel(arrayCountry);

		comboBox = new JComboBox<Country>(comboModel);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 2;
		gbc_comboBox.insets = new Insets(0, 0, 2, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 3;
		jpPanelTab2.add(comboBox, gbc_comboBox);
		
		ActionListener comboCountryListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				selectedCountry = (Country) comboBox.getSelectedItem();
			}
		};

		comboBox.addActionListener(comboCountryListener);

		
		class StringArrayListComboBoxModel extends AbstractListModel<String> implements ComboBoxModel<String> {
			
			private Object selectedItem;

			private ArrayList<String> anArrayList;

			public StringArrayListComboBoxModel(ArrayList<String> arrayList) {
				anArrayList = arrayList;
			}

			public Object getSelectedItem() {
				return selectedItem;
			}

			public void setSelectedItem(Object newValue) {
				selectedItem = newValue;
			}

			public int getSize() {
				return anArrayList.size();
			}

			public String getElementAt(int i) {
				return anArrayList.get(i);
			}

		}
		
		katalogy = new ArrayList<String>();
		Katalogy = new DBKatalogy(cardDB.connection());
		katalogy = Katalogy.getAll();
		katalogy.add(null);
		StringArrayListComboBoxModel stringComboBoxModel = new StringArrayListComboBoxModel(katalogy);
		
		comboBoxKatalog = new JComboBox<String>(stringComboBoxModel);
		GridBagConstraints gbc_comboBoxKatalog = new GridBagConstraints();
		gbc_comboBoxKatalog.insets = new Insets(5, 0, 2, 5);
		gbc_comboBoxKatalog.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxKatalog.gridx = 1;
		gbc_comboBoxKatalog.gridy = 0;
		jpPanelTab2.add(comboBoxKatalog, gbc_comboBoxKatalog);
		
		lblPoznmka = new JLabel("Poznámka:");
		lblPoznmka.setVerticalAlignment(SwingConstants.TOP);
		lblPoznmka.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblPoznmka = new GridBagConstraints();
		gbc_lblPoznmka.anchor = GridBagConstraints.EAST;
		gbc_lblPoznmka.insets = new Insets(0, 0, 2, 5);
		gbc_lblPoznmka.gridx = 0;
		gbc_lblPoznmka.gridy = 6;
		jpPanelTab2.add(lblPoznmka, gbc_lblPoznmka);
		
		textNote = new JTextField();
		textNote.setFont(UIManager.getFont("EditorPane.font"));
		GridBagConstraints gbc_textNote = new GridBagConstraints();
		gbc_textNote.gridwidth = 2;
		gbc_textNote.insets = new Insets(0, 0, 2, 5);
		gbc_textNote.fill = GridBagConstraints.BOTH;
		gbc_textNote.gridx = 1;
		gbc_textNote.gridy = 6;
		jpPanelTab2.add(textNote, gbc_textNote);
		textNote.setColumns(10);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridwidth = 6;
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 8;
		jpPanelTab2.add(scrollPane_1, gbc_scrollPane_1);
		
		table = new JTable();
		cardTableModel = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Katalog", "Číslo", "Číslo karty", "Číslo čipu", "Země", "Vydavatel"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		table.setModel(cardTableModel);
		//table.getColumnModel().removeColumn(table.getColumn("ID"));
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setWidth(0);

		
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(cardTableModel);
	    table.setRowSorter(sorter);
		
		scrollPane_1.setViewportView(table);
		
		table.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if (e.getClickCount() == 2){
					//CardDBSearch cardDBSearch = new CardDBSearch(cardDB.dbConnection, (Integer) cardTableModel.getValueAt(table.getSelectedRow(), 0));
					CardDBSearch cardDBSearch = new CardDBSearch(cardDB.dbConnection, (Integer) table.getValueAt(table.getSelectedRow(), 0));
					try {
						//lastRec = cardDBSearch.getDBRecord();
						DBRecordEditor recEditorU = new DBRecordEditor(cardDB.dbConnection, cardDBSearch.getDBRecord(), null);
						//po zavreni editoru znovu provedeni dotazu
						try {
							performQuery();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						//update Comboboxu "Katalogy"
						String oldValue = (String) comboBoxKatalog.getSelectedItem();
						katalogy = Katalogy.getAll();
						katalogy.add(null);
						StringArrayListComboBoxModel stringComboBoxModel = new StringArrayListComboBoxModel(katalogy);
						comboBoxKatalog.setModel(stringComboBoxModel);
						comboBoxKatalog.setSelectedItem(oldValue);
						
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		} );
		
		separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridwidth = 6;
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 7;
		jpPanelTab2.add(separator, gbc_separator);
		
		textIssuer = new JTextField();
		GridBagConstraints gbc_textIssuer = new GridBagConstraints();
		gbc_textIssuer.gridwidth = 3;
		gbc_textIssuer.insets = new Insets(0, 0, 2, 5);
		gbc_textIssuer.fill = GridBagConstraints.BOTH;
		gbc_textIssuer.gridx = 3;
		gbc_textIssuer.gridy = 5;
		jpPanelTab2.add(textIssuer, gbc_textIssuer);
		textIssuer.setColumns(20);
		
		lblVydavatel = new JLabel("Vydavatel:");
		lblVydavatel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblVydavatel = new GridBagConstraints();
		gbc_lblVydavatel.anchor = GridBagConstraints.EAST;
		gbc_lblVydavatel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblVydavatel.insets = new Insets(0, 0, 2, 5);
		gbc_lblVydavatel.gridx = 2;
		gbc_lblVydavatel.gridy = 5;
		jpPanelTab2.add(lblVydavatel, gbc_lblVydavatel);
		
		textManufacturer = new JTextField();
		GridBagConstraints gbc_textManufacturer = new GridBagConstraints();
		gbc_textManufacturer.fill = GridBagConstraints.BOTH;
		gbc_textManufacturer.insets = new Insets(0, 0, 2, 5);
		gbc_textManufacturer.gridx = 1;
		gbc_textManufacturer.gridy = 5;
		jpPanelTab2.add(textManufacturer, gbc_textManufacturer);
		textManufacturer.setColumns(20);
		
		lblVyrobce = new JLabel("Výrobce:");
		GridBagConstraints gbc_lblVyrobce = new GridBagConstraints();
		gbc_lblVyrobce.anchor = GridBagConstraints.EAST;
		gbc_lblVyrobce.insets = new Insets(0, 0, 2, 5);
		gbc_lblVyrobce.gridx = 0;
		gbc_lblVyrobce.gridy = 5;
		jpPanelTab2.add(lblVyrobce, gbc_lblVyrobce);
		
		btnNajt = new JButton("Najít");
		btnNajt.setEnabled(true);
		GridBagConstraints gbc_btnNajt = new GridBagConstraints();
		gbc_btnNajt.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNajt.insets = new Insets(2, 0, 5, 5);
		gbc_btnNajt.gridx = 5;
		gbc_btnNajt.gridy = 6;
		jpPanelTab2.add(btnNajt, gbc_btnNajt);
		
		checkBoxNezeme = new JCheckBox("Zahrnout \"nezemě\"");
		GridBagConstraints gbc_checkBoxNezeme = new GridBagConstraints();
		gbc_checkBoxNezeme.anchor = GridBagConstraints.WEST;
		gbc_checkBoxNezeme.gridwidth = 2;
		gbc_checkBoxNezeme.insets = new Insets(0, 0, 2, 5);
		gbc_checkBoxNezeme.gridx = 4;
		gbc_checkBoxNezeme.gridy = 3;
		jpPanelTab2.add(checkBoxNezeme, gbc_checkBoxNezeme);
		
		textSerialChip = new JTextField();
		GridBagConstraints gbc_textSerialChip = new GridBagConstraints();
		gbc_textSerialChip.gridwidth = 2;
		gbc_textSerialChip.insets = new Insets(0, 0, 2, 5);
		gbc_textSerialChip.fill = GridBagConstraints.BOTH;
		gbc_textSerialChip.gridx = 4;
		gbc_textSerialChip.gridy = 2;
		jpPanelTab2.add(textSerialChip, gbc_textSerialChip);
		textSerialChip.setColumns(15);
		
		lblSerialChip = new JLabel("Sériové číslo čipu:");
		lblSerialChip.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblSerialChip = new GridBagConstraints();
		gbc_lblSerialChip.anchor = GridBagConstraints.EAST;
		gbc_lblSerialChip.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblSerialChip.gridwidth = 2;
		gbc_lblSerialChip.insets = new Insets(0, 0, 2, 5);
		gbc_lblSerialChip.gridx = 2;
		gbc_lblSerialChip.gridy = 2;
		jpPanelTab2.add(lblSerialChip, gbc_lblSerialChip);
		
		textState = new JTextField();
		GridBagConstraints gbc_textState = new GridBagConstraints();
		gbc_textState.fill = GridBagConstraints.BOTH;
		gbc_textState.insets = new Insets(0, 0, 2, 5);
		gbc_textState.gridx = 1;
		gbc_textState.gridy = 4;
		jpPanelTab2.add(textState, gbc_textState);
		textState.setColumns(15);
		
		textCountry = new JTextField();
		textCountry.setHorizontalAlignment(SwingConstants.CENTER);
		textCountry.setEnabled(false);
		GridBagConstraints gbc_textCountry = new GridBagConstraints();
		gbc_textCountry.fill = GridBagConstraints.BOTH;
		gbc_textCountry.insets = new Insets(0, 0, 2, 5);
		gbc_textCountry.gridx = 3;
		gbc_textCountry.gridy = 3;
		jpPanelTab2.add(textCountry, gbc_textCountry);
		textCountry.setColumns(15);
		
		textSerialCard = new JTextField();
		GridBagConstraints gbc_textSerialCard = new GridBagConstraints();
		gbc_textSerialCard.fill = GridBagConstraints.BOTH;
		gbc_textSerialCard.insets = new Insets(0, 0, 2, 5);
		gbc_textSerialCard.gridx = 1;
		gbc_textSerialCard.gridy = 2;
		jpPanelTab2.add(textSerialCard, gbc_textSerialCard);
		textSerialCard.setColumns(15);
		
		lblKatalog = new JLabel("Katalog:");
		GridBagConstraints gbc_lblKatalog = new GridBagConstraints();
		gbc_lblKatalog.anchor = GridBagConstraints.EAST;
		gbc_lblKatalog.insets = new Insets(5, 0, 2, 5);
		gbc_lblKatalog.gridx = 0;
		gbc_lblKatalog.gridy = 0;
		jpPanelTab2.add(lblKatalog, gbc_lblKatalog);
		
		lblKatalogCislo = new JLabel("Katalogové číslo:");
		GridBagConstraints gbc_lblKatalogCislo = new GridBagConstraints();
		gbc_lblKatalogCislo.anchor = GridBagConstraints.EAST;
		gbc_lblKatalogCislo.insets = new Insets(0, 0, 2, 5);
		gbc_lblKatalogCislo.gridx = 0;
		gbc_lblKatalogCislo.gridy = 1;
		jpPanelTab2.add(lblKatalogCislo, gbc_lblKatalogCislo);
		
		textKatalogCislo = new JTextField();
		GridBagConstraints gbc_textKatalogCislo = new GridBagConstraints();
		gbc_textKatalogCislo.fill = GridBagConstraints.BOTH;
		gbc_textKatalogCislo.insets = new Insets(0, 0, 2, 5);
		gbc_textKatalogCislo.gridx = 1;
		gbc_textKatalogCislo.gridy = 1;
		jpPanelTab2.add(textKatalogCislo, gbc_textKatalogCislo);
		textKatalogCislo.setColumns(15);
		
		lblSerialCard = new JLabel("Sériové číslo karty:");
		GridBagConstraints gbc_lblSerialCard = new GridBagConstraints();
		gbc_lblSerialCard.anchor = GridBagConstraints.EAST;
		gbc_lblSerialCard.insets = new Insets(0, 0, 2, 5);
		gbc_lblSerialCard.gridx = 0;
		gbc_lblSerialCard.gridy = 2;
		jpPanelTab2.add(lblSerialCard, gbc_lblSerialCard);
		
		lblZeme = new JLabel("Země:");
		GridBagConstraints gbc_lblZeme = new GridBagConstraints();
		gbc_lblZeme.anchor = GridBagConstraints.EAST;
		gbc_lblZeme.insets = new Insets(0, 0, 2, 5);
		gbc_lblZeme.gridx = 0;
		gbc_lblZeme.gridy = 3;
		jpPanelTab2.add(lblZeme, gbc_lblZeme);
		
		lblStat = new JLabel("Stát:");
		GridBagConstraints gbc_lblStat = new GridBagConstraints();
		gbc_lblStat.anchor = GridBagConstraints.EAST;
		gbc_lblStat.insets = new Insets(0, 0, 2, 5);
		gbc_lblStat.gridx = 0;
		gbc_lblStat.gridy = 4;
		jpPanelTab2.add(lblStat, gbc_lblStat);
		tabbedPane.setEnabledAt(1, true);
		
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
					serialPortak.sendCommandToReader(command);
					vypsano = false;
					/*
					synchronized(vlakno) {
						try {
							vlakno.notify();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					*/
				}
			}
		});
		
		jbnButSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openRecEditor();
			}
		});
		
		btnNajt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					performQuery();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
		
		menuConfigurationConnReader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openTestSerialPort();
			}
		});
		
		menuConfigurationConnDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardDB = new CardDatabase();
			}
		});
		
		new javax.swing.Timer(300, updateStatusAction).start();
		
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
			
			JFrame dialogFrame = new JFrame();
			dialogFrame.setVisible(false);
			String password;
			JPasswordField passwordField = new JPasswordField();
			passwordField.setEchoChar('*');
			Object stringArray[] = {"OK","Zrušit"};
			Object[] obj = {"Pro přístup ke konfiguraci je požadováno heslo:\n\n", passwordField};
			int odp = JOptionPane.showOptionDialog(dialogFrame, obj,
					"Přístup ke konfiguraci", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, stringArray, obj);
			if (odp == JOptionPane.YES_OPTION) {
				password = new String(passwordField.getPassword());
				if (password.equalsIgnoreCase("LS321")) {
					optDialog.show();
					tabbedPane.setEnabledAt(1, true);
					cardDB = new CardDatabase();
				}
			}
			
		}
		
	};
	
	public void openTestSerialPort() {
		
		jbnButRead.setEnabled(false);
		jtfStatusBar.setText("");
		statusRegistr = "";
		//vlakno = new SendComm(serPortName);
		//vlakno.start();
		serialPortak = new MySerialPort(serPortName, 57600);
		//System.out.println("Jmeno portu: "+serPortName);
		command = "TEST";
		commProcess = true;
		serialPortak.sendCommandToReader(command);
	}
	
	public void exitApp() {
		
		if (cardDB != null) {
			cardDB.close();
		}
		System.exit(0);
	}
	
	public void checkContent() {
		
		int tableSize = commandTable1.size() ;
		boolean cut = true;
		
		for (int i = 0; i < tableSize/2; i++) {
			if (!commandTable1.get(i).getAnswer().equals(commandTable1.get(i+tableSize/2).getAnswer())) {
				cut = false;
				break;
			}
		}
		if (cut) {
			for (int j = tableSize/2; j < tableSize; j++) {
				commandTable1.remove(commandTable1.size()-1);
			}
		}
	}
	
	public void vypisTabuli() {
		
		int k = 0;
		String[] binVal = new String[4];
		String hexVal;
		String content = "";

		for (int i = 0; i < commandTable1.size(); i++) {
			if (k == 0) {
				content += (String.format("%2s", i) + " "+("000"+Integer.toString(i*8)).substring(("000"+Integer.toString(i*8)).length()-4)+":");
			}
			binVal[k] = Integer.toBinaryString(Integer.parseInt(commandTable1.get(i).getAnswer()));
			hexVal = Integer.toHexString(Integer.parseInt(commandTable1.get(i).getAnswer())).toUpperCase();
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

		commandTable1.clear();
	}
	
	public void performQuery() throws SQLException {
		
		int rowC = cardTableModel.getRowCount();
		for (int u = 0; u < rowC; u++) {
			cardTableModel.removeRow(0);
		}
		
		ArrayList<ParamValue> whereClause = new ArrayList<ParamValue>();
		
		//Katalog combo
		if (comboBoxKatalog.getSelectedItem() != null) {
			whereClause.add(new ParamValue("catalog", (String) comboBoxKatalog.getSelectedItem(), "="));
		}
		// Katalogove cislo
		if (!textKatalogCislo.getText().isEmpty()) {
			whereClause.add(new ParamValue("catalog_number", textKatalogCislo.getText(), "like"));
		}
		//Poznamka
		if (!textNote.getText().isEmpty()) {
			whereClause.add(new ParamValue("note", textNote.getText(), "like"));
		}
		//Vydavatel
		if (!textIssuer.getText().isEmpty()) {
			whereClause.add(new ParamValue("issuer", textIssuer.getText(), "like"));
		}
		//Vyrobce
		if (!textManufacturer.getText().isEmpty()) {
			whereClause.add(new ParamValue("manufacturer", textManufacturer.getText(), "like"));
		}
		//Zeme
		if (!textCountry.getText().isEmpty()) {
			whereClause.add(new ParamValue("country", textCountry.getText(), "="));
			//nezeme
			if (checkBoxNezeme.isSelected()) {
				whereClause.add(new ParamValue("country", "XXX", "=", "OR"));
			}
		}
		//Seriove cislo cipu
		if (!textSerialChip.getText().isEmpty()) {
			whereClause.add(new ParamValue("chip_serial", textSerialChip.getText(), "like"));
		}
		//Seriove cislo karty
		if (!textSerialCard.getText().isEmpty()) {
			whereClause.add(new ParamValue("card_serial", textSerialCard.getText(), "like"));
		}
		//Stat
		if (!textState.getText().isEmpty()) {
			whereClause.add(new ParamValue("state", textState.getText(), "like"));
		}

		if (whereClause.size() == 0) {
			cardSearch = new CardDBSearch(cardDB.dbConnection, 0);
		} else {
			cardSearch = new CardDBSearch(cardDB.dbConnection, whereClause);
		}
		
		ArrayList<CardDBRecord> cardSet = cardSearch.getArrayList();
		for (int i = 0; i < cardSet.size(); i++) {
			cardTableModel.addRow(new Object[]{
					cardSet.get(i).recId,
					cardSet.get(i).catalog,
					cardSet.get(i).catalogNumber,
					cardSet.get(i).cardSerial,
					cardSet.get(i).chipSerial,
					cardSet.get(i).csCountry,
					cardSet.get(i).issuer});
		}
		
	}
	
	public void decodeCard() {
		
		ArrayList<String> cardContent = new ArrayList<String>();				
		for (int i = 0; i < commandTable1.size(); i++) {
			cardContent.add(i, commandTable1.get(i).getAnswer());
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
	
	public void openRecEditor() {
		
		String chipSerial = "";
		String countryExtra = "";
		String manufacturer = "";
		String issuer = "";
		String content = "";
		String contentHash = "";
		
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			if (tableModel.getValueAt(i, 0).toString().matches(".*[Ss]erial.*")) {
				chipSerial = tableModel.getValueAt(i, 1).toString();
			}
			if (tableModel.getValueAt(i, 0).toString().matches(".*[Cc]ountry.*")) {
				countryExtra = tableModel.getValueAt(i, 1).toString();
			}
			if (tableModel.getValueAt(i, 0).toString().matches(".*[Mm]anufacturer.*")) {
				manufacturer = tableModel.getValueAt(i, 1).toString();
			}
			if (tableModel.getValueAt(i, 0).toString().matches(".*[Ii]ssuer.*")) {
				issuer = tableModel.getValueAt(i, 1).toString();
			}
		}
		
		content = jtAreaOutput.getText();
		contentHash = MD5.getMD5(content);

		ArrayList<String> old = null;
		
		if ((dbRec != null) && (recEditor != null) && (recEditor.toRemember == true)) {
			old = recEditor.getOldValues();			
		}
		
		dbRec = new CardDBRecord(
				cardDB.dbConnection,
				0,       //rec_id
				"",      //katalog
				"",      //katalog id
				"",      //serial karty
				chipSerial,
				"",      //country
				countryExtra,
				"",      //state
				manufacturer,
				issuer,
				content,
				contentHash,
				null,     //date_add
				configFile.getPropertyValue("remoteDBIdent"),    //who add
				"",       //poznamka
				""        //csCountry
				);
		
		if (old != null) {
			recEditor = new DBRecordEditor(cardDB.dbConnection, dbRec, old);
		} else {
			recEditor = new DBRecordEditor(cardDB.dbConnection, dbRec, null);
		}
		
	}
	
	public static void recError(ErrorRecord er) {
		errors.add(er);
	}
	
	public String getInstallPath() {
		
		String installPath = "";
		
		if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
			
			try {
				installPath = WinRegistry.readString (
					    WinRegistry.HKEY_LOCAL_MACHINE,                             //HKEY
					    "SOFTWARE\\TelecardReader",           //Key
					    "InstallPath",                                              //ValueName
					    0);                                                         //Standard access
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				installPath = "";
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				installPath = "";
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				installPath = "";
			}

		}
		
		if (System.getProperty("os.name").toUpperCase().contains("LINUX")) {
			installPath = TelecardReader.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			installPath = installPath.substring(0, installPath.length()-1);
		}
		
		return installPath;
		
	}
	
	public String getTempPath() {
		
		String tempPath = "";
		
		tempPath = System.getProperty("java.io.tmpdir");
		
		if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
			tempPath = tempPath.substring(0, tempPath.length()-1);
		}
		
		return tempPath;
		
	}
	
	ActionListener updateStatusAction = new AbstractAction() {
		
		public void actionPerformed(ActionEvent e) {
			
			if (command == "TEST") {	
				if (statusRegistr.equals("TEST: OK")) {
					jtfStatusBar.setText("Port nalezen. Čtečka: " + statusRegistr);
					jbnButRead.setEnabled(true);
				} else {
					jtfStatusBar.setText("Čtečka: test bez odpovědi");
				}
				
			}
			if ((command == "READ0") || (command == "READ1")) {
				//System.out.println(command);
				jtfStatusBar.setText(statusRegistr);
				
			}
			if (jtfStatusBar.getText().equals("Vlozte kartu do ctecky.")) {
				jbnButRead.setEnabled(true);
			}
			if (jtfStatusBar.getText().equals("Příjem dat ukončen.") && (vypsano == false)) {
				checkContent();
				vypisTabuli();
				vypsano = true;
				String fileSeparator = System.getProperty("file.separator");
				String chipyFileName = System.getProperty("user.home")+fileSeparator+".telecardreader"+fileSeparator+"CHIPYDEF.TXT";
				File chipyDefFile = new File(chipyFileName);
				if ( chipyDefFile.exists()) {
					decodeCard();
				} else {
					TelecardReader.recError(new ErrorRecord("Nenalezen soubor s definicemi karet. ("+ chipyDefFile.getAbsolutePath() +")", -1));
				}
				jbnButRead.setEnabled(true);
			}
			if (errors.size() > 0) {
				
				int errCode = errors.get(0).getErrorCode();
				if (errCode < 0) {
					showErrorDialog(errors.get(0).getErrorText());
				} else {
					showInfoDialog(errors.get(0).getErrorText());
				}
				errors.remove(0);
				if (errCode == -9) {
					tabbedPane.setSelectedIndex(0);
					tabbedPane.setEnabledAt(1, false);
				}
			}
			
			if (selectedCountry == null) {
				textCountry.setText(null);
			}
			
			if ((selectedCountry != null) && (selectedCountry.getCode() != textCountry.getText())) {
				textCountry.setText(selectedCountry.getCode());
			}
			if (jtAreaOutput.getText().length() > 0) {
				jbnButSave.setEnabled(true);
			} else {
				jbnButSave.setEnabled(false);
			}
			
			if (DOWNLOAD_IN_PROGRESS) {
				downloadProgressBar.setValue((int) download.getProgress());
				downloadProgressBar.repaint();
				if (download.getProgress() == 100.00) {
					downloadFrame.dispose();
					DOWNLOAD_IN_PROGRESS = false;
					//Process doSystem.getProperty("user.dir")
					String installPath = getInstallPath();
					//System.out.println("Install path: " + installPath);
					String tempPath = getTempPath();
					//System.out.println("Temp path: " + tempPath);
					try {
						String updaterPath = installPath + System.getProperty("file.separator") + "Updater.jar";
						String newVerPath = tempPath +  System.getProperty("file.separator") + "TelecardReader.jar";
						//System.out.println(updaterPath);
						//System.out.println(newVerPath);
						ProcessBuilder pb = new ProcessBuilder("java", "-jar", updaterPath, newVerPath, installPath);
						Process p = pb.start();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.exit(0);
				}
			}
			
	   }
		
	};
	
	ActionListener rbTypeCardListener = new ActionListener() {
		public void actionPerformed(ActionEvent actionEvent) {
			readType = actionEvent.getActionCommand();
		}
	};
	
	public void showAboutDialog() {
		String aboutMessage = "Tento program vám přináší Petr..."
				+ newline + "...a nedává na něho žádnou záruku. :-)";
		JFrame dialogFrame = new JFrame();
		dialogFrame.setVisible(false);
		JOptionPane.showMessageDialog(dialogFrame, aboutMessage,
				"O programu", JOptionPane.INFORMATION_MESSAGE);
	}

	public void showErrorDialog(String errorText) {
		JFrame dialogFrame = new JFrame();
		dialogFrame.setVisible(false);
		JOptionPane.showMessageDialog(dialogFrame, errorText,
				"Chyba", JOptionPane.ERROR_MESSAGE);
	}
	
	public void showInfoDialog(String text) {
		JFrame dialogFrame = new JFrame();
		dialogFrame.setVisible(false);
		JOptionPane.showMessageDialog(dialogFrame, text,
				"Informace", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void fillSerialPortsToMenu() {
		
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
			for (int i = 1; i <= 16; i++) {
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
		
	public static void setStatus(String text) {
		
		TelecardReader.jtfStatusBar.setText(text);
		
	}
	
	
	public void checkNewVersion() {
		
		String version = "";
		URL updater = null;
		BufferedReader in; 
		try {
			updater = new URL("http://telecardreader.petrjelinek.net/updater.php?action=check&projekt=TelecardReader");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (updater != null) {
			try {
				in = new BufferedReader(
						new InputStreamReader(updater.openStream()));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					//System.out.println(inputLine);
					version = inputLine;
				}
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				version = "0";
				e.printStackTrace();
			}
		} else {
			version = "0";
		}
		
		// pokud bylo neco precteno
		if (!version.equals("0")) {
			if (version.compareTo(CurrentVersion.VERSION) > 0) {
				JFrame dialogFrame = new JFrame();
				dialogFrame.setVisible(false);
				Object stringArray[] = {"Nainstalovat", "Odložit instalaci"};
				Object[] obj = {"Je k dispozici nová verze programu ("+version+")\n\n"+"Přejete si naistalovat novou verzi?"};
				int odp = JOptionPane.showOptionDialog(dialogFrame, obj,
						"Nová verze programu", JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE, null, stringArray, obj);
				if (odp == 0) { // Nainstalovat
					//System.out.println("Ted by se spustil updater");
					URL fileURL;
					try {
						fileURL = new URL("http://telecardreader.petrjelinek.net/" + version + "/TelecardReader.jar");
						download = new Download(fileURL);
						DOWNLOAD_IN_PROGRESS = true;
						//download.run();
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					downloadFrame = new JFrame();
					downloadFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					downloadFrame.setVisible(false);
					Object stringDownArray[] = {"Přerušit"};
					downloadProgressBar = new JProgressBar(0, 100);
					downloadProgressBar.setValue(0);
					downloadProgressBar.setStringPainted(true);
					Object[] downObj = {"Stahuje se verze "+version+"...\n\n", downloadProgressBar};
					int downOdp = JOptionPane.showOptionDialog(downloadFrame, downObj,
							"Stahování nové verze programu", JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE, null, stringDownArray, obj);
					
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
		telecardReader.setTitle(windowTitle + " v. " + CurrentVersion.VERSION);
		telecardReader.setPreferredSize(new Dimension(800, 600));
		telecardReader.pack();
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
