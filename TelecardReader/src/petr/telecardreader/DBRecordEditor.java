package petr.telecardreader;


import javax.swing.JPanel;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.InputVerifier;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.JCheckBox;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.sql.Connection;
import javax.swing.UIManager;

public class DBRecordEditor extends JDialog {

	public CardDBRecord dbRec;
	private JComboBox comboBoxKatalog, comboBoxCountry;
	private JTextField txtKatalogCislo;
	private JTextField txtSerialKarty;
	private JTextField txtSerialCip;
	private JTextField txtZeme;
	private JTextField txtZemeExtra;
	private JTextField txtStat;
	private JTextField txtVyrobce;
	private JTextField txtVydavatel;
	private JTextField txtPridal;
	private JTextField txtDne;
	//private JTextField txtPoznamka;
	private JTextArea txtPoznamka;
	private Country selectedCountry;
	private JTextArea txtAreaObsah;
	private JCheckBox checkObsah;
	
	//CardDatabase cardDB;
	Connection dbConnection;
	DBCountry dbSelCountry;
	TempFile tempFileCountry;
	TempFile tempFileKatalog;
	ArrayList<Country> arrayCountry;
	private JTextField txtStatus;
	private ArrayList<String> oldParameters;
	private String katalog;
	
	public boolean toRemember = true;
	
	public DBRecordEditor(Connection dbConnection, CardDBRecord dbRec, ArrayList<String> oldParameters) {
		
		this.dbRec = dbRec;
		this.oldParameters = oldParameters;
		this.dbConnection = dbConnection;
		//cardDB = new CardDatabase();
		
		createGUI();
		setValues();
		setOldParameters();
		this.pack();
		this.setTitle("Editace záznamu");
		this.setModal(true);
		this.setModalityType(ModalityType.DOCUMENT_MODAL);
		//this.setBounds(this.getParent().getX() + 50, this.getParent().getY() + 50, 750, 350);
		this.setVisible(true);
	}

	private void createGUI() {
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 166, 0, 132, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 36, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblKatalog = new JLabel("Katalog:");
		GridBagConstraints gbc_lblKatalog = new GridBagConstraints();
		gbc_lblKatalog.anchor = GridBagConstraints.EAST;
		gbc_lblKatalog.insets = new Insets(10, 10, 5, 5);
		gbc_lblKatalog.gridx = 0;
		gbc_lblKatalog.gridy = 0;
		panel.add(lblKatalog, gbc_lblKatalog);
		
		/*
		txtKatalog = new JTextField();
		GridBagConstraints gbc_txtkatalog = new GridBagConstraints();
		gbc_txtkatalog.insets = new Insets(10, 0, 5, 5);
		gbc_txtkatalog.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtkatalog.gridx = 1;
		gbc_txtkatalog.gridy = 0;
		panel.add(txtKatalog, gbc_txtkatalog);
		txtKatalog.setColumns(10);
		*/
		
		class LengthVerifier extends InputVerifier {

			private int length;

			public LengthVerifier(int length) {
				this.length = length;
			}

			public boolean verify(JComponent input) {
				JTextField tf = (JTextField) input;
				if (tf.getText().length() > length) {
					JFrame dialogFrameE = new JFrame();
            		dialogFrameE.setVisible(false);
            		JOptionPane.showMessageDialog(dialogFrameE, "Hodnota je příliš dlouhá.\n\nMaximum je "+length+" znaků\n",
            				"Chyba", JOptionPane.ERROR_MESSAGE);
				}
				return tf.getText().length() <= length;
			}
		}
		LengthVerifier lv20 = new LengthVerifier(20);
		LengthVerifier lv50 = new LengthVerifier(50);
		LengthVerifier lv100 = new LengthVerifier(100);
		
		class LengthVerifierTextArea extends InputVerifier {

			private int length;

			public LengthVerifierTextArea(int length) {
				this.length = length;
			}

			public boolean verify(JComponent input) {
				JTextArea tf = (JTextArea) input;
				if (tf.getText().length() > length) {
					JFrame dialogFrameE = new JFrame();
            		dialogFrameE.setVisible(false);
            		JOptionPane.showMessageDialog(dialogFrameE, "Hodnota je příliš dlouhá.\n\nMaximum je "+length+" znaků\n",
            				"Chyba", JOptionPane.ERROR_MESSAGE);
				}
				return tf.getText().length() <= length;
			}
		}
		
		LengthVerifierTextArea lvta200 = new LengthVerifierTextArea(200);
		
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
		
		ArrayList<String> katalogy = new ArrayList<String>();
		if (dbConnection != null) {                                // kdyz dbConnection je null pak plnit ze souboru
			DBKatalogy Katalogy = new DBKatalogy(dbConnection);
			katalogy = Katalogy.getAll();
			katalogy.add(null);
		} else {
			tempFileKatalog = new TempFile("catalogs.txt");
			katalogy = tempFileKatalog.read();
		}
		StringArrayListComboBoxModel stringComboBoxModel = new StringArrayListComboBoxModel(katalogy);
		
		comboBoxKatalog = new JComboBox<String>(stringComboBoxModel);
		comboBoxKatalog.setBackground(new Color(152, 251, 152));
		GridBagConstraints gbc_comboBoxKatalog = new GridBagConstraints();
		gbc_comboBoxKatalog.insets = new Insets(5, 0, 5, 5);
		gbc_comboBoxKatalog.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxKatalog.gridx = 1;
		gbc_comboBoxKatalog.gridy = 0;
		panel.add(comboBoxKatalog, gbc_comboBoxKatalog);
		
		//kontrola delky
		ActionListener comboKatalogListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				katalog = (String) comboBoxKatalog.getSelectedItem();
				if (katalog != null) {
					if (katalog.length() > 20) {
						JFrame dialogFrameE = new JFrame();
						dialogFrameE.setVisible(false);
						JOptionPane.showMessageDialog(dialogFrameE, "Hodnota je příliš dlouhá.\n\nMaximum je 20 znaků\n",
								"Chyba", JOptionPane.ERROR_MESSAGE);
						comboBoxKatalog.transferFocus();
					}
				}
			}
		};
		
		comboBoxKatalog.addActionListener(comboKatalogListener);
		
		JLabel lblObsahKarty = new JLabel("Obsah karty:");
		GridBagConstraints gbc_lblObsahKarty = new GridBagConstraints();
		gbc_lblObsahKarty.insets = new Insets(10, 0, 5, 5);
		gbc_lblObsahKarty.gridx = 2;
		gbc_lblObsahKarty.gridy = 0;
		panel.add(lblObsahKarty, gbc_lblObsahKarty);
		
		JLabel lblKatalogovslo = new JLabel("Katalogové číslo:");
		GridBagConstraints gbc_lblKatalogovslo = new GridBagConstraints();
		gbc_lblKatalogovslo.anchor = GridBagConstraints.EAST;
		gbc_lblKatalogovslo.insets = new Insets(0, 10, 5, 5);
		gbc_lblKatalogovslo.gridx = 0;
		gbc_lblKatalogovslo.gridy = 1;
		panel.add(lblKatalogovslo, gbc_lblKatalogovslo);
		
		txtKatalogCislo = new JTextField();
		txtKatalogCislo.setBackground(new Color(152, 251, 152));
		GridBagConstraints gbc_txtKatalogCislo = new GridBagConstraints();
		gbc_txtKatalogCislo.insets = new Insets(0, 0, 5, 5);
		gbc_txtKatalogCislo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtKatalogCislo.gridx = 1;
		gbc_txtKatalogCislo.gridy = 1;
		panel.add(txtKatalogCislo, gbc_txtKatalogCislo);
		txtKatalogCislo.setColumns(10);
		txtKatalogCislo.setInputVerifier(lv20);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.gridheight = 7;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 7);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 1;
		panel.add(scrollPane, gbc_scrollPane);
		
		txtAreaObsah = new JTextArea();
		txtAreaObsah.setColumns(60);
		txtAreaObsah.setRows(10);
		Font textFont = new Font("Courier", Font.PLAIN, 12);
		txtAreaObsah.setEditable(false);
		txtAreaObsah.setFont(new Font("Courier New", Font.PLAIN, 13));
		scrollPane.setViewportView(txtAreaObsah);
		
		JLabel lblSriovsloKarty = new JLabel("Sériové číslo karty:");
		GridBagConstraints gbc_lblSriovsloKarty = new GridBagConstraints();
		gbc_lblSriovsloKarty.anchor = GridBagConstraints.EAST;
		gbc_lblSriovsloKarty.insets = new Insets(0, 10, 5, 5);
		gbc_lblSriovsloKarty.gridx = 0;
		gbc_lblSriovsloKarty.gridy = 2;
		panel.add(lblSriovsloKarty, gbc_lblSriovsloKarty);
		
		txtSerialKarty = new JTextField();
		txtSerialKarty.setBackground(new Color(152, 251, 152));
		GridBagConstraints gbc_txtSerialKarty = new GridBagConstraints();
		gbc_txtSerialKarty.insets = new Insets(0, 0, 5, 5);
		gbc_txtSerialKarty.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSerialKarty.gridx = 1;
		gbc_txtSerialKarty.gridy = 2;
		panel.add(txtSerialKarty, gbc_txtSerialKarty);
		txtSerialKarty.setColumns(10);
		txtSerialKarty.setInputVerifier(lv50);
		
		JLabel lblSriovsloipu = new JLabel("Sériové číslo čipu:");
		GridBagConstraints gbc_lblSriovsloipu = new GridBagConstraints();
		gbc_lblSriovsloipu.anchor = GridBagConstraints.EAST;
		gbc_lblSriovsloipu.insets = new Insets(0, 10, 5, 5);
		gbc_lblSriovsloipu.gridx = 0;
		gbc_lblSriovsloipu.gridy = 3;
		panel.add(lblSriovsloipu, gbc_lblSriovsloipu);
		
		txtSerialCip = new JTextField();
		GridBagConstraints gbc_txtSerialCip = new GridBagConstraints();
		gbc_txtSerialCip.insets = new Insets(0, 0, 5, 5);
		gbc_txtSerialCip.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSerialCip.gridx = 1;
		gbc_txtSerialCip.gridy = 3;
		panel.add(txtSerialCip, gbc_txtSerialCip);
		txtSerialCip.setColumns(10);
		txtSerialCip.setInputVerifier(lv50);
		
		JLabel lblZem = new JLabel("Země:");
		GridBagConstraints gbc_lblZem = new GridBagConstraints();
		gbc_lblZem.anchor = GridBagConstraints.EAST;
		gbc_lblZem.insets = new Insets(0, 10, 5, 5);
		gbc_lblZem.gridx = 0;
		gbc_lblZem.gridy = 8;
		panel.add(lblZem, gbc_lblZem);
		
		//////
		
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
		if (dbConnection != null) {
			dbSelCountry = new DBCountry(dbConnection);
			arrayCountry = dbSelCountry.getAll();
			arrayCountry.add(null);
		} else {
			tempFileCountry = new TempFile("countries.txt");
			ArrayList<String> countryList = new ArrayList<String>();
			countryList = tempFileCountry.read();
			for (int i = 0; i < countryList.size(); i++) {
				//System.out.println(countryList.get(i));
				if (countryList.get(i).length() > 0) {
					String[] co = countryList.get(i).split(";;");
					arrayCountry.add(new Country(co[0], co[1], co[2], co[3], co[4]));
				}
			}
		}
		
		ArrayListComboBoxModel comboModel = new ArrayListComboBoxModel(arrayCountry);

		comboBoxCountry = new JComboBox<Country>(comboModel);
		comboBoxCountry.setBackground(new Color(152, 251, 152));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 3;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 8;
		panel.add(comboBoxCountry, gbc_comboBox);
		
		ActionListener comboCountryListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				selectedCountry = (Country) comboBoxCountry.getSelectedItem();
				if (selectedCountry != null) {
					txtZeme.setText(selectedCountry.numCode);
				}
			}
		};

		comboBoxCountry.addActionListener(comboCountryListener);
		
		/////
		
		txtZeme = new JTextField();
		txtZeme.setForeground(new Color(0, 0, 128));
		txtZeme.setBackground(new Color(152, 251, 152));
		txtZeme.setEnabled(false);
		GridBagConstraints gbc_txtZeme = new GridBagConstraints();
		gbc_txtZeme.insets = new Insets(0, 0, 5, 5);
		gbc_txtZeme.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtZeme.gridx = 4;
		gbc_txtZeme.gridy = 8;
		panel.add(txtZeme, gbc_txtZeme);
		txtZeme.setColumns(10);
		
		JLabel lblZemExtra = new JLabel("Země extra:");
		GridBagConstraints gbc_lblZemExtra = new GridBagConstraints();
		gbc_lblZemExtra.anchor = GridBagConstraints.EAST;
		gbc_lblZemExtra.insets = new Insets(0, 10, 5, 5);
		gbc_lblZemExtra.gridx = 0;
		gbc_lblZemExtra.gridy = 4;
		panel.add(lblZemExtra, gbc_lblZemExtra);
		
		txtZemeExtra = new JTextField();
		GridBagConstraints gbc_txtZemeExtra = new GridBagConstraints();
		gbc_txtZemeExtra.insets = new Insets(0, 0, 5, 5);
		gbc_txtZemeExtra.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtZemeExtra.gridx = 1;
		gbc_txtZemeExtra.gridy = 4;
		panel.add(txtZemeExtra, gbc_txtZemeExtra);
		txtZemeExtra.setColumns(10);
		txtZemeExtra.setInputVerifier(lv100);
		
		JLabel lblStt = new JLabel("Stát:");
		GridBagConstraints gbc_lblStt = new GridBagConstraints();
		gbc_lblStt.anchor = GridBagConstraints.EAST;
		gbc_lblStt.insets = new Insets(0, 10, 5, 5);
		gbc_lblStt.gridx = 0;
		gbc_lblStt.gridy = 5;
		panel.add(lblStt, gbc_lblStt);
		
		txtStat = new JTextField();
		txtStat.setBackground(new Color(152, 251, 152));
		GridBagConstraints gbc_txtStat = new GridBagConstraints();
		gbc_txtStat.insets = new Insets(0, 0, 5, 5);
		gbc_txtStat.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtStat.gridx = 1;
		gbc_txtStat.gridy = 5;
		panel.add(txtStat, gbc_txtStat);
		txtStat.setColumns(10);
		txtStat.setInputVerifier(lv100);
		
		JLabel lblVrobce = new JLabel("Výrobce:");
		GridBagConstraints gbc_lblVrobce = new GridBagConstraints();
		gbc_lblVrobce.anchor = GridBagConstraints.EAST;
		gbc_lblVrobce.insets = new Insets(0, 10, 5, 5);
		gbc_lblVrobce.gridx = 0;
		gbc_lblVrobce.gridy = 6;
		panel.add(lblVrobce, gbc_lblVrobce);
		
		txtVyrobce = new JTextField();
		GridBagConstraints gbc_txtVyrobce = new GridBagConstraints();
		gbc_txtVyrobce.insets = new Insets(0, 0, 5, 5);
		gbc_txtVyrobce.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtVyrobce.gridx = 1;
		gbc_txtVyrobce.gridy = 6;
		panel.add(txtVyrobce, gbc_txtVyrobce);
		txtVyrobce.setColumns(10);
		txtVyrobce.setInputVerifier(lv100);
		
		JLabel lblVydavatel = new JLabel("Vydavatel:");
		GridBagConstraints gbc_lblVydavatel = new GridBagConstraints();
		gbc_lblVydavatel.anchor = GridBagConstraints.EAST;
		gbc_lblVydavatel.insets = new Insets(0, 10, 5, 5);
		gbc_lblVydavatel.gridx = 0;
		gbc_lblVydavatel.gridy = 7;
		panel.add(lblVydavatel, gbc_lblVydavatel);
		
		txtVydavatel = new JTextField();
		GridBagConstraints gbc_txtVydavatel = new GridBagConstraints();
		gbc_txtVydavatel.insets = new Insets(0, 0, 5, 5);
		gbc_txtVydavatel.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtVydavatel.gridx = 1;
		gbc_txtVydavatel.gridy = 7;
		panel.add(txtVydavatel, gbc_txtVydavatel);
		txtVydavatel.setColumns(10);
		txtVydavatel.setInputVerifier(lv100);
		
		JLabel lblPidal = new JLabel("Přidal:");
		GridBagConstraints gbc_lblPidal = new GridBagConstraints();
		gbc_lblPidal.anchor = GridBagConstraints.EAST;
		gbc_lblPidal.insets = new Insets(0, 0, 5, 5);
		gbc_lblPidal.gridx = 0;
		gbc_lblPidal.gridy = 9;
		panel.add(lblPidal, gbc_lblPidal);
		
		txtPridal = new JTextField();
		txtPridal.setEnabled(false);
		GridBagConstraints gbc_txtPridal = new GridBagConstraints();
		gbc_txtPridal.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPridal.insets = new Insets(0, 0, 5, 5);
		gbc_txtPridal.gridx = 1;
		gbc_txtPridal.gridy = 9;
		panel.add(txtPridal, gbc_txtPridal);
		txtPridal.setColumns(10);
		
		JLabel lblDne = new JLabel("Dne:");
		GridBagConstraints gbc_lblDne = new GridBagConstraints();
		gbc_lblDne.anchor = GridBagConstraints.EAST;
		gbc_lblDne.insets = new Insets(0, 0, 5, 5);
		gbc_lblDne.gridx = 2;
		gbc_lblDne.gridy = 9;
		panel.add(lblDne, gbc_lblDne);
		
		txtDne = new JTextField();
		txtDne.setEnabled(false);
		GridBagConstraints gbc_txtDne = new GridBagConstraints();
		gbc_txtDne.insets = new Insets(0, 0, 5, 5);
		gbc_txtDne.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDne.gridx = 3;
		gbc_txtDne.gridy = 9;
		gbc_txtDne.gridwidth = 2;
		panel.add(txtDne, gbc_txtDne);
		txtDne.setColumns(10);
		
		JLabel lblPoznmka = new JLabel("Poznámka:");
		GridBagConstraints gbc_lblPoznmka = new GridBagConstraints();
		gbc_lblPoznmka.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblPoznmka.insets = new Insets(0, 0, 10, 5);
		gbc_lblPoznmka.gridx = 0;
		gbc_lblPoznmka.gridy = 10;
		panel.add(lblPoznmka, gbc_lblPoznmka);
		
		JScrollPane scrollPane_Note = new JScrollPane();
		scrollPane_Note.setViewportBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane_Note.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		//scrollPane_Note.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane_Note = new GridBagConstraints();
		gbc_scrollPane_Note.gridheight = 2;
		gbc_scrollPane_Note.gridwidth = 3;
		gbc_scrollPane_Note.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_Note.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_Note.gridx = 1;
		gbc_scrollPane_Note.gridy = 10;
		panel.add(scrollPane_Note, gbc_scrollPane_Note);
		txtPoznamka = new JTextArea();
		txtPoznamka.setFont(UIManager.getFont("EditorPane.font"));
		txtPoznamka.setBackground(new Color(152, 251, 152));
		scrollPane_Note.setViewportView(txtPoznamka);
		txtPoznamka.setRows(4);
		txtPoznamka.setColumns(35);
		txtPoznamka.setInputVerifier(lvta200);
		
		JButton btnVymazat = new JButton("Vymazat");
		btnVymazat.setForeground(Color.RED);
		GridBagConstraints gbc_btnVymazat = new GridBagConstraints();
		gbc_btnVymazat.anchor = GridBagConstraints.EAST;
		gbc_btnVymazat.insets = new Insets(0, 0, 10, 5);
		gbc_btnVymazat.gridx = 4;
		gbc_btnVymazat.gridy = 10;
		panel.add(btnVymazat, gbc_btnVymazat);
		
		JButton btnUlozit = new JButton("Uložit");
		GridBagConstraints gbc_btnUlozit = new GridBagConstraints();
		gbc_btnUlozit.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnUlozit.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnUlozit.insets = new Insets(0, 0, 5, 5);
		gbc_btnUlozit.gridx = 5;
		gbc_btnUlozit.gridy = 10;
		panel.add(btnUlozit, gbc_btnUlozit);
		
		checkObsah = new JCheckBox("Zapamatovat obsah");
		GridBagConstraints gbc_checkObsah = new GridBagConstraints();
		gbc_checkObsah.anchor = GridBagConstraints.NORTH;
		gbc_checkObsah.fill = GridBagConstraints.HORIZONTAL;
		gbc_checkObsah.insets = new Insets(0, 0, 5, 5);
		gbc_checkObsah.gridx = 4;
		gbc_checkObsah.gridy = 11;
		panel.add(checkObsah, gbc_checkObsah);
		checkObsah.setSelected(true);
		
		JButton btnZavrit = new JButton("Zavřít");
		GridBagConstraints gbc_btnZavt = new GridBagConstraints();
		gbc_btnZavt.anchor = GridBagConstraints.NORTH;
		gbc_btnZavt.insets = new Insets(0, 0, 5, 5);
		gbc_btnZavt.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnZavt.gridx = 5;
		gbc_btnZavt.gridy = 11;
		panel.add(btnZavrit, gbc_btnZavt);
		
		txtStatus = new JTextField();
		txtStatus.setEnabled(true);
		txtStatus.setEditable(false);
		GridBagConstraints gbc_txtStatus = new GridBagConstraints();
		gbc_txtStatus.gridwidth = 6;
		gbc_txtStatus.insets = new Insets(0, 1, 1, 1);
		gbc_txtStatus.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtStatus.gridx = 0;
		gbc_txtStatus.gridy = 12;
		panel.add(txtStatus, gbc_txtStatus);
		txtStatus.setColumns(10);
		
		if (dbRec.recId == 0) {
			btnVymazat.setEnabled(false);
		}
		
        btnUlozit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		updateValues();
        		if (dbRec.recId == 0) {
        			if (!dbRec.isAlreadySaved()) {
        				int ari = dbRec.insert();
        				if ((ari == 1) || (ari == 10)) {
        					txtStatus.setText("Záznam byl uložen.");
        					String msg = null;
        					if (ari == 1) {
        						msg = "Nová karta byla uložena.";
        					}
        					if (ari == 10) {
        						msg = "Nová karta byla uložena do dočasného souboru.";
        					}
        					JFrame dialogFrameU = new JFrame();
                    		dialogFrameU.setVisible(false);
                    		JOptionPane.showMessageDialog(dialogFrameU, msg,
                    				"Nově uložená karta", JOptionPane.PLAIN_MESSAGE);
                    		closeWindow();
        				}
        			} else {
        				JFrame dialogFrameA = new JFrame();
                		dialogFrameA.setVisible(false);
                		JOptionPane.showMessageDialog(dialogFrameA, "Tato karta je již v databázi uložena.",
                				"Již uložená karta", JOptionPane.PLAIN_MESSAGE);
        			}
        			
        		} else {
        			int aru = dbRec.update();
        			if (aru == 1) {
        				txtStatus.setText("Záznam byl upraven.");
        			}
        		}
        	}
        });
        
        btnZavrit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JFrame dialogFrame = new JFrame();
        		dialogFrame.setVisible(false);
        		int odp = JOptionPane.showConfirmDialog(dialogFrame, "Opravdu zavřít?",
        				"Potvrzení", JOptionPane.YES_NO_OPTION);
        		//System.out.println("Vratilo to: "+odp);
        		if (odp == 0) {
        			closeWindow();
        		}
        	}
        });
        
        btnVymazat.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JFrame dialogFrame = new JFrame();
        		dialogFrame.setVisible(false);
        		String password;
        		JPasswordField passwordField = new JPasswordField();
        		passwordField.setEchoChar('*');
        		Object stringArray[] = {"OK","Zrušit"};
        		Object[] obj = {"Chcete smazat záznam?\n\n", passwordField};
        		int odp = JOptionPane.showOptionDialog(dialogFrame, obj,
        				"Potvrzení", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, stringArray, obj);
        		if (odp == JOptionPane.YES_OPTION) {
        			password = new String(passwordField.getPassword());
        			if (password.equalsIgnoreCase("LS321")) {

        				int ard = dbRec.delete();
        				if (ard == 1) {
        					JFrame dialogFrameE = new JFrame();
        					dialogFrameE.setVisible(false);
        					JOptionPane.showMessageDialog(dialogFrameE, "Záznam byl smazán.",
        							"Smazáno", JOptionPane.PLAIN_MESSAGE);
        					closeWindow();
        				}
        			} else {
        				JFrame dialogFrameNP = new JFrame();
                		dialogFrameNP.setVisible(false);
                		JOptionPane.showMessageDialog(dialogFrameNP, "Záznam nebyl smazán.",
                				"Nesmazáno", JOptionPane.PLAIN_MESSAGE);
        			}
        		} else {
        			JFrame dialogFrameN = new JFrame();
            		dialogFrameN.setVisible(false);
            		JOptionPane.showMessageDialog(dialogFrameN, "Záznam nebyl smazán.",
            				"Nesmazáno", JOptionPane.PLAIN_MESSAGE);
        		}
        	}
        });
        
        checkObsah.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		toRemember = checkObsah.isSelected();
        	}
        });
        
	}
	
	private void closeWindow() {
		
		this.dispose();
		
	}
	
	private void setValues() {
		
		//txtKatalog.setText(dbRec.getCatalog());
		comboBoxKatalog.setSelectedItem(dbRec.getCatalog());
		comboBoxKatalog.setEditable(true);
		
		txtKatalogCislo.setText(dbRec.getCatalogNumber());
		txtSerialKarty.setText(dbRec.getCardSerial());
		txtSerialCip.setText(dbRec.getChipSerial());
		
		String cCode = dbRec.getCountry();
		Country selCount;
		String selCode;
		int comIndex = -1;
		for (int i = 0; i < comboBoxCountry.getModel().getSize()-1; i++) {
			selCount = (Country) comboBoxCountry.getModel().getElementAt(i);
			selCode = (String) selCount.numCode;
			if (selCode.equals(cCode)) {
				comIndex = i;
				break;
			}
		}
		comboBoxCountry.setSelectedIndex(comIndex);
		
		txtZemeExtra.setText(dbRec.countryExtra);
		txtStat.setText(dbRec.state);
		txtVyrobce.setText(dbRec.manufacturer);
		txtVydavatel.setText(dbRec.issuer);
		txtPridal.setText(dbRec.whoAdd);
		if (dbRec.dateAdd != null) {
			txtDne.setText(dbRec.dateAdd.replaceAll("Saturday", "Sobota").replaceAll("Sunday", "Neděle").replaceAll("Monday", "Pondělí").replaceAll("Tuesday", "Úterý").replaceAll("Wednesday", "Středa").replaceAll("Thursday", "Čtvrtek").replaceAll("Friday", "Pátek"));
		}
		txtPoznamka.setText(dbRec.note);
		txtAreaObsah.setText(dbRec.content);
		

	}
	
	private void setOldParameters() {

		if (oldParameters != null) {
			comboBoxKatalog.setSelectedItem(oldParameters.get(0));
			txtKatalogCislo.setText(oldParameters.get(1));
			txtSerialKarty.setText(oldParameters.get(2));
			String cCode = oldParameters.get(3);
			Country selCount;
			String selCode;
			int comIndex = -1;
			for (int i = 0; i < comboBoxCountry.getModel().getSize()-1; i++) {
				selCount = (Country) comboBoxCountry.getModel().getElementAt(i);
				selCode = (String) selCount.numCode;
				if (selCode.equals(cCode)) {
					comIndex = i;
					break;
				}
			}
			comboBoxCountry.setSelectedIndex(comIndex);
			txtStat.setText(oldParameters.get(4));
			//txtPoznamka.setText(oldParameters.get(5));
			
		}
	}
	
	public ArrayList<String> getOldValues() {
		
		ArrayList<String> oldValues = new ArrayList<String>();
		oldValues.add(0, katalog);
		oldValues.add(1, txtKatalogCislo.getText());
		oldValues.add(2, txtSerialKarty.getText());
		oldValues.add(3, txtZeme.getText());
		oldValues.add(4, txtStat.getText());
		//oldValues.add(5, txtPoznamka.getText());
		
		return oldValues;
		
	}

	private void updateValues() {
		
		dbRec.catalog = (String) comboBoxKatalog.getSelectedItem();
		dbRec.catalogNumber = txtKatalogCislo.getText();
		dbRec.cardSerial = txtSerialKarty.getText();
		dbRec.chipSerial = txtSerialCip.getText();
		dbRec.country = txtZeme.getText();
		dbRec.countryExtra = txtZemeExtra.getText();
		dbRec.issuer = txtVydavatel.getText();
		dbRec.manufacturer = txtVyrobce.getText();
		dbRec.note = txtPoznamka.getText();
		dbRec.state = txtStat.getText();

	}
	
}
