package petr.telecardreader;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;



public class Differ extends JDialog {
	
	String[] oldRec;
	String[] newRec;
	private JTextField textOldCatalog;
	private JTextField textNewCatalog;
	private JTextField textOldCatNumber;
	private JTextField textNewCatNumber;
	private JTextField textOldCardNumber;
	private JTextField textNewCardNumber;
	private JTextField textOldChipNumber;
	private JTextField textNewChipNumber;
	private JTextField textOldCountry;
	private JTextField textNewCountry;
	private JTextField textOldCountryExtra;
	private JTextField textNewCountryExtra;
	private JTextField textOldState;
	private JTextField textNewState;
	private JTextField textOldManufacturer;
	private JTextField textNewManufacturer;
	private JTextField textOldIssuer;
	private JTextField textNewIssuer;
	private JScrollPane scrollPaneOldNote;
	private JTextArea textAreaOldNote;
	private JScrollPane scrollPaneNewNote;
	private JTextArea textAreaNewNote;
	private JButton btnOrig;
	private JButton btnNew;
	
	private int returnValue;
	private JButton btnSkip;
	
	public Differ(String[] oldRec, String[] newRec) {
		
		this.oldRec = oldRec;
		this.newRec = newRec;
		createGUI();

	}
	
	public int showDialog() {
		
		this.setMinimumSize(new Dimension(600, 450));
		setValues();
		this.pack();
		this.setTitle("Porovnání hodnot");
		this.setModal(true);
		this.setModalityType(ModalityType.DOCUMENT_MODAL);
		this.setVisible(true);
		return getValue();
		
	}
	
	private void createGUI() {
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{5, 0, 0, 0, 173, 5, 0};
		gbl_panel.rowHeights = new int[]{10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblPorovnn = new JLabel("Porovnání");
		GridBagConstraints gbc_lblPorovnn = new GridBagConstraints();
		gbc_lblPorovnn.gridwidth = 3;
		gbc_lblPorovnn.insets = new Insets(0, 0, 5, 5);
		gbc_lblPorovnn.gridx = 2;
		gbc_lblPorovnn.gridy = 1;
		panel.add(lblPorovnn, gbc_lblPorovnn);
		
		JLabel lblDBHodnota = new JLabel("Hodnota v databázi");
		GridBagConstraints gbc_lblDBHodnota = new GridBagConstraints();
		gbc_lblDBHodnota.insets = new Insets(0, 0, 5, 5);
		gbc_lblDBHodnota.gridx = 2;
		gbc_lblDBHodnota.gridy = 2;
		panel.add(lblDBHodnota, gbc_lblDBHodnota);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.fill = GridBagConstraints.VERTICAL;
		gbc_separator.gridheight = 14;
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 3;
		gbc_separator.gridy = 2;
		panel.add(separator, gbc_separator);
		
		JLabel lblHodnotaVDatabzi = new JLabel("Vkládaná hodnota");
		GridBagConstraints gbc_lblHodnotaVDatabzi = new GridBagConstraints();
		gbc_lblHodnotaVDatabzi.insets = new Insets(0, 0, 5, 5);
		gbc_lblHodnotaVDatabzi.gridx = 4;
		gbc_lblHodnotaVDatabzi.gridy = 2;
		panel.add(lblHodnotaVDatabzi, gbc_lblHodnotaVDatabzi);
		
		JLabel lblKatalog = new JLabel("Katalog:");
		GridBagConstraints gbc_lblKatalog = new GridBagConstraints();
		gbc_lblKatalog.anchor = GridBagConstraints.EAST;
		gbc_lblKatalog.insets = new Insets(0, 0, 5, 5);
		gbc_lblKatalog.gridx = 1;
		gbc_lblKatalog.gridy = 3;
		panel.add(lblKatalog, gbc_lblKatalog);
		
		textOldCatalog = new JTextField();
		textOldCatalog.setEnabled(false);
		GridBagConstraints gbc_textOldCatalog = new GridBagConstraints();
		gbc_textOldCatalog.insets = new Insets(0, 0, 5, 5);
		gbc_textOldCatalog.fill = GridBagConstraints.HORIZONTAL;
		gbc_textOldCatalog.gridx = 2;
		gbc_textOldCatalog.gridy = 3;
		panel.add(textOldCatalog, gbc_textOldCatalog);
		textOldCatalog.setColumns(10);
		
		textNewCatalog = new JTextField();
		textNewCatalog.setEnabled(false);
		GridBagConstraints gbc_textNewCatalog = new GridBagConstraints();
		gbc_textNewCatalog.insets = new Insets(0, 0, 5, 5);
		gbc_textNewCatalog.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNewCatalog.gridx = 4;
		gbc_textNewCatalog.gridy = 3;
		panel.add(textNewCatalog, gbc_textNewCatalog);
		textNewCatalog.setColumns(10);
		
		JLabel lblKatalogovslo = new JLabel("Katalogové číslo:");
		GridBagConstraints gbc_lblKatalogovslo = new GridBagConstraints();
		gbc_lblKatalogovslo.anchor = GridBagConstraints.EAST;
		gbc_lblKatalogovslo.insets = new Insets(0, 0, 5, 5);
		gbc_lblKatalogovslo.gridx = 1;
		gbc_lblKatalogovslo.gridy = 4;
		panel.add(lblKatalogovslo, gbc_lblKatalogovslo);
		
		textOldCatNumber = new JTextField();
		textOldCatNumber.setEnabled(false);
		GridBagConstraints gbc_textOldCatNumber = new GridBagConstraints();
		gbc_textOldCatNumber.insets = new Insets(0, 0, 5, 5);
		gbc_textOldCatNumber.fill = GridBagConstraints.HORIZONTAL;
		gbc_textOldCatNumber.gridx = 2;
		gbc_textOldCatNumber.gridy = 4;
		panel.add(textOldCatNumber, gbc_textOldCatNumber);
		textOldCatNumber.setColumns(10);
		
		textNewCatNumber = new JTextField();
		textNewCatNumber.setEnabled(false);
		GridBagConstraints gbc_textNewCatNumber = new GridBagConstraints();
		gbc_textNewCatNumber.insets = new Insets(0, 0, 5, 5);
		gbc_textNewCatNumber.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNewCatNumber.gridx = 4;
		gbc_textNewCatNumber.gridy = 4;
		panel.add(textNewCatNumber, gbc_textNewCatNumber);
		textNewCatNumber.setColumns(10);
		
		JLabel lblSriovsloKarty = new JLabel("Sériové číslo karty:");
		GridBagConstraints gbc_lblSriovsloKarty = new GridBagConstraints();
		gbc_lblSriovsloKarty.anchor = GridBagConstraints.EAST;
		gbc_lblSriovsloKarty.insets = new Insets(0, 5, 5, 5);
		gbc_lblSriovsloKarty.gridx = 1;
		gbc_lblSriovsloKarty.gridy = 5;
		panel.add(lblSriovsloKarty, gbc_lblSriovsloKarty);
		
		textOldCardNumber = new JTextField();
		textOldCardNumber.setEnabled(false);
		GridBagConstraints gbc_textOldCardNumber = new GridBagConstraints();
		gbc_textOldCardNumber.insets = new Insets(0, 0, 5, 5);
		gbc_textOldCardNumber.fill = GridBagConstraints.HORIZONTAL;
		gbc_textOldCardNumber.gridx = 2;
		gbc_textOldCardNumber.gridy = 5;
		panel.add(textOldCardNumber, gbc_textOldCardNumber);
		textOldCardNumber.setColumns(10);
		
		textNewCardNumber = new JTextField();
		textNewCardNumber.setEnabled(false);
		GridBagConstraints gbc_textNewCardNumber = new GridBagConstraints();
		gbc_textNewCardNumber.insets = new Insets(0, 0, 5, 5);
		gbc_textNewCardNumber.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNewCardNumber.gridx = 4;
		gbc_textNewCardNumber.gridy = 5;
		panel.add(textNewCardNumber, gbc_textNewCardNumber);
		textNewCardNumber.setColumns(10);
		
		JLabel lblSriovsloipu = new JLabel("Sériové číslo čipu:");
		GridBagConstraints gbc_lblSriovsloipu = new GridBagConstraints();
		gbc_lblSriovsloipu.anchor = GridBagConstraints.EAST;
		gbc_lblSriovsloipu.insets = new Insets(0, 0, 5, 5);
		gbc_lblSriovsloipu.gridx = 1;
		gbc_lblSriovsloipu.gridy = 6;
		panel.add(lblSriovsloipu, gbc_lblSriovsloipu);
		
		textOldChipNumber = new JTextField();
		textOldChipNumber.setEnabled(false);
		GridBagConstraints gbc_textOldChipNumber = new GridBagConstraints();
		gbc_textOldChipNumber.insets = new Insets(0, 0, 5, 5);
		gbc_textOldChipNumber.fill = GridBagConstraints.HORIZONTAL;
		gbc_textOldChipNumber.gridx = 2;
		gbc_textOldChipNumber.gridy = 6;
		panel.add(textOldChipNumber, gbc_textOldChipNumber);
		textOldChipNumber.setColumns(10);
		
		textNewChipNumber = new JTextField();
		textNewChipNumber.setEnabled(false);
		GridBagConstraints gbc_textNewChipNumber = new GridBagConstraints();
		gbc_textNewChipNumber.insets = new Insets(0, 0, 5, 5);
		gbc_textNewChipNumber.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNewChipNumber.gridx = 4;
		gbc_textNewChipNumber.gridy = 6;
		panel.add(textNewChipNumber, gbc_textNewChipNumber);
		textNewChipNumber.setColumns(10);
		
		JLabel lblZem = new JLabel("Země:");
		GridBagConstraints gbc_lblZem = new GridBagConstraints();
		gbc_lblZem.anchor = GridBagConstraints.EAST;
		gbc_lblZem.insets = new Insets(0, 0, 5, 5);
		gbc_lblZem.gridx = 1;
		gbc_lblZem.gridy = 7;
		panel.add(lblZem, gbc_lblZem);
		
		textOldCountry = new JTextField();
		textOldCountry.setEnabled(false);
		GridBagConstraints gbc_textOldCountry = new GridBagConstraints();
		gbc_textOldCountry.insets = new Insets(0, 0, 5, 5);
		gbc_textOldCountry.fill = GridBagConstraints.HORIZONTAL;
		gbc_textOldCountry.gridx = 2;
		gbc_textOldCountry.gridy = 7;
		panel.add(textOldCountry, gbc_textOldCountry);
		textOldCountry.setColumns(10);
		
		textNewCountry = new JTextField();
		textNewCountry.setEnabled(false);
		GridBagConstraints gbc_textNewCountry = new GridBagConstraints();
		gbc_textNewCountry.insets = new Insets(0, 0, 5, 5);
		gbc_textNewCountry.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNewCountry.gridx = 4;
		gbc_textNewCountry.gridy = 7;
		panel.add(textNewCountry, gbc_textNewCountry);
		textNewCountry.setColumns(10);
		
		JLabel lblZemExtra = new JLabel("Země extra:");
		GridBagConstraints gbc_lblZemExtra = new GridBagConstraints();
		gbc_lblZemExtra.anchor = GridBagConstraints.EAST;
		gbc_lblZemExtra.insets = new Insets(0, 0, 5, 5);
		gbc_lblZemExtra.gridx = 1;
		gbc_lblZemExtra.gridy = 8;
		panel.add(lblZemExtra, gbc_lblZemExtra);
		
		textOldCountryExtra = new JTextField();
		textOldCountryExtra.setEnabled(false);
		GridBagConstraints gbc_textOldCountryExtra = new GridBagConstraints();
		gbc_textOldCountryExtra.insets = new Insets(0, 0, 5, 5);
		gbc_textOldCountryExtra.fill = GridBagConstraints.HORIZONTAL;
		gbc_textOldCountryExtra.gridx = 2;
		gbc_textOldCountryExtra.gridy = 8;
		panel.add(textOldCountryExtra, gbc_textOldCountryExtra);
		textOldCountryExtra.setColumns(10);
		
		textNewCountryExtra = new JTextField();
		textNewCountryExtra.setEnabled(false);
		GridBagConstraints gbc_textNewCountryExtra = new GridBagConstraints();
		gbc_textNewCountryExtra.insets = new Insets(0, 0, 5, 5);
		gbc_textNewCountryExtra.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNewCountryExtra.gridx = 4;
		gbc_textNewCountryExtra.gridy = 8;
		panel.add(textNewCountryExtra, gbc_textNewCountryExtra);
		textNewCountryExtra.setColumns(10);
		
		JLabel lblStt = new JLabel("Stát:");
		GridBagConstraints gbc_lblStt = new GridBagConstraints();
		gbc_lblStt.anchor = GridBagConstraints.EAST;
		gbc_lblStt.insets = new Insets(0, 0, 5, 5);
		gbc_lblStt.gridx = 1;
		gbc_lblStt.gridy = 9;
		panel.add(lblStt, gbc_lblStt);
		
		textOldState = new JTextField();
		textOldState.setEnabled(false);
		GridBagConstraints gbc_textOldState = new GridBagConstraints();
		gbc_textOldState.insets = new Insets(0, 0, 5, 5);
		gbc_textOldState.fill = GridBagConstraints.HORIZONTAL;
		gbc_textOldState.gridx = 2;
		gbc_textOldState.gridy = 9;
		panel.add(textOldState, gbc_textOldState);
		textOldState.setColumns(10);
		
		textNewState = new JTextField();
		textNewState.setEnabled(false);
		GridBagConstraints gbc_textNewState = new GridBagConstraints();
		gbc_textNewState.insets = new Insets(0, 0, 5, 5);
		gbc_textNewState.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNewState.gridx = 4;
		gbc_textNewState.gridy = 9;
		panel.add(textNewState, gbc_textNewState);
		textNewState.setColumns(10);
		
		JLabel lblVrobce = new JLabel("Výrobce:");
		GridBagConstraints gbc_lblVrobce = new GridBagConstraints();
		gbc_lblVrobce.anchor = GridBagConstraints.EAST;
		gbc_lblVrobce.insets = new Insets(0, 0, 5, 5);
		gbc_lblVrobce.gridx = 1;
		gbc_lblVrobce.gridy = 10;
		panel.add(lblVrobce, gbc_lblVrobce);
		
		textOldManufacturer = new JTextField();
		textOldManufacturer.setEnabled(false);
		GridBagConstraints gbc_textOldManufacturer = new GridBagConstraints();
		gbc_textOldManufacturer.insets = new Insets(0, 0, 5, 5);
		gbc_textOldManufacturer.fill = GridBagConstraints.HORIZONTAL;
		gbc_textOldManufacturer.gridx = 2;
		gbc_textOldManufacturer.gridy = 10;
		panel.add(textOldManufacturer, gbc_textOldManufacturer);
		textOldManufacturer.setColumns(10);
		
		textNewManufacturer = new JTextField();
		textNewManufacturer.setEnabled(false);
		GridBagConstraints gbc_textNewManufacturer = new GridBagConstraints();
		gbc_textNewManufacturer.insets = new Insets(0, 0, 5, 5);
		gbc_textNewManufacturer.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNewManufacturer.gridx = 4;
		gbc_textNewManufacturer.gridy = 10;
		panel.add(textNewManufacturer, gbc_textNewManufacturer);
		textNewManufacturer.setColumns(10);
		
		JLabel lblVydavatel = new JLabel("Vydavatel:");
		GridBagConstraints gbc_lblVydavatel = new GridBagConstraints();
		gbc_lblVydavatel.anchor = GridBagConstraints.EAST;
		gbc_lblVydavatel.insets = new Insets(0, 0, 5, 5);
		gbc_lblVydavatel.gridx = 1;
		gbc_lblVydavatel.gridy = 11;
		panel.add(lblVydavatel, gbc_lblVydavatel);
		
		textOldIssuer = new JTextField();
		textOldIssuer.setEnabled(false);
		GridBagConstraints gbc_textOldIssuer = new GridBagConstraints();
		gbc_textOldIssuer.insets = new Insets(0, 0, 5, 5);
		gbc_textOldIssuer.fill = GridBagConstraints.HORIZONTAL;
		gbc_textOldIssuer.gridx = 2;
		gbc_textOldIssuer.gridy = 11;
		panel.add(textOldIssuer, gbc_textOldIssuer);
		textOldIssuer.setColumns(10);
		
		textNewIssuer = new JTextField();
		textNewIssuer.setEnabled(false);
		GridBagConstraints gbc_textNewIssuer = new GridBagConstraints();
		gbc_textNewIssuer.insets = new Insets(0, 0, 5, 5);
		gbc_textNewIssuer.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNewIssuer.gridx = 4;
		gbc_textNewIssuer.gridy = 11;
		panel.add(textNewIssuer, gbc_textNewIssuer);
		textNewIssuer.setColumns(10);
		
		JLabel lblPoznmka = new JLabel("Poznámka:");
		GridBagConstraints gbc_lblPoznmka = new GridBagConstraints();
		gbc_lblPoznmka.fill = GridBagConstraints.VERTICAL;
		gbc_lblPoznmka.anchor = GridBagConstraints.EAST;
		gbc_lblPoznmka.insets = new Insets(0, 0, 5, 5);
		gbc_lblPoznmka.gridx = 1;
		gbc_lblPoznmka.gridy = 12;
		panel.add(lblPoznmka, gbc_lblPoznmka);
		
		scrollPaneOldNote = new JScrollPane();
		scrollPaneOldNote.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GridBagConstraints gbc_scrollPaneOldNote = new GridBagConstraints();
		gbc_scrollPaneOldNote.gridheight = 3;
		gbc_scrollPaneOldNote.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPaneOldNote.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneOldNote.gridx = 2;
		gbc_scrollPaneOldNote.gridy = 12;
		panel.add(scrollPaneOldNote, gbc_scrollPaneOldNote);
		
		textAreaOldNote = new JTextArea();
		textAreaOldNote.setEnabled(false);
		scrollPaneOldNote.setViewportView(textAreaOldNote);
		
		scrollPaneNewNote = new JScrollPane();
		scrollPaneNewNote.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GridBagConstraints gbc_scrollPaneNewNote = new GridBagConstraints();
		gbc_scrollPaneNewNote.gridheight = 3;
		gbc_scrollPaneNewNote.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPaneNewNote.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneNewNote.gridx = 4;
		gbc_scrollPaneNewNote.gridy = 12;
		panel.add(scrollPaneNewNote, gbc_scrollPaneNewNote);
		
		textAreaNewNote = new JTextArea();
		textAreaNewNote.setEnabled(false);
		scrollPaneNewNote.setViewportView(textAreaNewNote);
		
		btnOrig = new JButton("Ponechat původní");
		GridBagConstraints gbc_btnOrig = new GridBagConstraints();
		gbc_btnOrig.anchor = GridBagConstraints.EAST;
		gbc_btnOrig.insets = new Insets(0, 0, 5, 5);
		gbc_btnOrig.gridx = 2;
		gbc_btnOrig.gridy = 15;
		panel.add(btnOrig, gbc_btnOrig);
		btnOrig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setReturnValue(0);
				close();
			}
		});
		
		btnNew = new JButton("Přepsat novou");
		GridBagConstraints gbc_btnNew = new GridBagConstraints();
		gbc_btnNew.anchor = GridBagConstraints.EAST;
		gbc_btnNew.insets = new Insets(0, 0, 5, 5);
		gbc_btnNew.gridx = 4;
		gbc_btnNew.gridy = 15;
		panel.add(btnNew, gbc_btnNew);
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setReturnValue(1);
				close();
			}
		});
		
		btnSkip = new JButton("Přeskočit");
		GridBagConstraints gbc_btnSkip = new GridBagConstraints();
		gbc_btnSkip.anchor = GridBagConstraints.EAST;
		gbc_btnSkip.insets = new Insets(0, 0, 5, 5);
		gbc_btnSkip.gridx = 4;
		gbc_btnSkip.gridy = 16;
		panel.add(btnSkip, gbc_btnSkip);
		btnSkip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setReturnValue(2);
				close();
			}
		});
		
		
	}
	
	private void setReturnValue(int value) {
		this.returnValue = value;
	}

	public int getValue() {
		return this.returnValue;
	}
	
	private void close() {
		this.setVisible(false);
		this.dispose();
	}
	
	private void setValues() {
		
		textOldCatalog.setText(oldRec[1]);
		textNewCatalog.setText(newRec[1].substring(newRec[1].indexOf("'")+1, newRec[1].length()-1).replace("null", ""));
		textOldCatNumber.setText(oldRec[2]);
		textNewCatNumber.setText(newRec[2].substring(newRec[2].indexOf("'")+1, newRec[2].length()-1));
		textOldCardNumber.setText(oldRec[3]);
		textNewCardNumber.setText(newRec[3].substring(newRec[3].indexOf("'")+1, newRec[3].length()-1));
		textOldChipNumber.setText(oldRec[4]);
		textNewChipNumber.setText(newRec[4].substring(newRec[4].indexOf("'")+1, newRec[4].length()-1));
		textOldCountry.setText(oldRec[5]);
		textNewCountry.setText(newRec[5].substring(newRec[5].indexOf("'")+1, newRec[5].length()-1));
		textOldCountryExtra.setText(oldRec[6]);
		textNewCountryExtra.setText(newRec[6].substring(newRec[6].indexOf("'")+1, newRec[6].length()-1));
		textOldState.setText(oldRec[7]);
		textNewState.setText(newRec[7].substring(newRec[7].indexOf("'")+1, newRec[7].length()-1));
		textOldManufacturer.setText(oldRec[8]);
		textNewManufacturer.setText(newRec[8].substring(newRec[8].indexOf("'")+1, newRec[8].length()-1));
		textOldIssuer.setText(oldRec[9]);
		textNewIssuer.setText(newRec[9].substring(newRec[9].indexOf("'")+1, newRec[9].length()-1));
		textAreaOldNote.setText(oldRec[10]);
		textAreaNewNote.setText(newRec[14].substring(newRec[14].indexOf("'")+1, newRec[14].length()-3).replace("<CR>", "\n"));
		
	}

}
