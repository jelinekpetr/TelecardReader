package petr.telecardreader;

import java.io.*;
import java.util.ArrayList;

public class ChipyDef {

	File chipyDef;
	FileReader readDef, readDef1;
	BufferedReader chipyReader, chipyReader1;
	String[] cards;
	String[][] cardFields;
	int cardId, idField;

	public int cardDefCount = 0;

	// konstruktory
	public ChipyDef() {
		String fileSeparator = System.getProperty("file.separator");
		String chipyFileName = System.getProperty("user.home")+fileSeparator+".telecardreader"+fileSeparator+"CHIPYDEF.TXT";
		readDefFile(chipyFileName);
	}

	public ChipyDef(String defFile) {
		readDefFile(defFile);
	}

	/////////////////////

	public int readDefFile(String definitionFile) {

		int lineCount = 0;
		int idField;
		// int cardDefCount = 0;
		String line = null;

		chipyDef = new File(definitionFile);
		try {
			readDef = new FileReader(chipyDef);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			TelecardReader.recError(new ErrorRecord("Nenalezen soubor s definicemi karet. ("+ chipyDef.getAbsolutePath() +")", -1));
			return -1;
		}
		chipyReader = new BufferedReader(readDef);
		try {
			while ((line = chipyReader.readLine()) != null) {
				if (line.matches("^\\*.*") == false) {
					lineCount++;
					if (line.matches("^CARD\\s*.*") == true) {
						cardDefCount++;
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		// znovuotevreni od zacatku
		try {
			chipyReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}

		try {
			readDef1 = new FileReader(chipyDef);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		chipyReader1 = new BufferedReader(readDef1);
		// //////
		cards = new String[cardDefCount];
		cardFields = new String[lineCount - cardDefCount][2];
		cardId = 0;
		idField = 0;
		try {
			while ((line = chipyReader1.readLine()) != null) {
				if (line.matches("^\\*.*") == false) {
					if (line.matches("^CARD\\s*.*") == true) {
						cards[cardId] = line;
						cardId++;
					} else {
						cardFields[idField][0] = Integer.toString(cardId-1);
						cardFields[idField][1] = line;
						idField++;
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	class DefFields {
		int idCard;
		String fieldDef;
		
		public DefFields(int parIdCard, String parFieldDef) {
			this.idCard = parIdCard;
			this.fieldDef = parFieldDef;
		}
		
		public int getIdCard() {
			return idCard;
		}
		
		public String getFieldDef() {
			return fieldDef;
		}
		
	}
	
	public ArrayList<DefFields> getVerifyLines() {
		
		String lastLine = "I";
		ArrayList<DefFields> verifyFields = new ArrayList<DefFields>();
		
		for (int i = 0; i < cardFields.length; i++) {
			if ((cardFields[i][1].matches("^VERIFY\\s*.*") == true) && ((lastLine
					.equals("VERIFY") == true) || (lastLine.equals("OR") == true) || (lastLine.equals("I") == true))) {
				lastLine = "VERIFY";
				verifyFields.add(new DefFields(Integer.parseInt(cardFields[i][0]), cardFields[i][1]));
			}
			if ((cardFields[i][1].matches("^OR\\s*.*") == true)
				&& ((lastLine.equals("VERIFY") == true) || (lastLine
						.equals("OR")))) {
				lastLine = "OR";
				verifyFields.add(new DefFields(Integer.parseInt(cardFields[i][0]), cardFields[i][1]));
			}
		}
		return verifyFields;
	}
	
	public ArrayList<DefFields> getVerifyLines(int cardIndex) {
		
		String lastLine = "I";
		ArrayList<DefFields> verifyFields = new ArrayList<DefFields>();
		
		for (int i = 0; i < cardFields.length; i++) {
			if ((cardFields[i][0].equals(Integer.toString(cardIndex))) && ((cardFields[i][1].matches("^VERIFY\\s+.*") == true) && ((lastLine
					.equals("VERIFY") == true) || (lastLine.equals("OR") == true) || (lastLine.equals("I") == true)))) {
				lastLine = "VERIFY";
				verifyFields.add(new DefFields(Integer.parseInt(cardFields[i][0]), cardFields[i][1]));
			}
			if ((cardFields[i][0].equals(Integer.toString(cardIndex))) && ((cardFields[i][1].matches("^OR\\s+.*") == true)
				&& ((lastLine.equals("VERIFY") == true) || (lastLine
						.equals("OR"))))) {
				lastLine = "OR";
				verifyFields.add(new DefFields(Integer.parseInt(cardFields[i][0]), cardFields[i][1]));
			}
		}
		return verifyFields;
	}
	
	public ArrayList<DefFields> getDefLines(int cardIndex, String lineName) {
		
		ArrayList<DefFields> checksumFields = new ArrayList<DefFields>();
		
		for (int i = 0; i < cardFields.length; i++) {
			if ((cardFields[i][0].equals(Integer.toString(cardIndex))) && ((cardFields[i][1].matches("^" + lineName + "\\s+.*") == true))) {
				checksumFields.add(new DefFields(Integer.parseInt(cardFields[i][0]), cardFields[i][1]));
			}
		}
		return checksumFields;
	}
	
	public ArrayList<DefFields> getDefLines(int cardIndex) {
		
		ArrayList<DefFields> checksumFields = new ArrayList<DefFields>();
		
		for (int i = 0; i < cardFields.length; i++) {
			if (cardFields[i][0].equals(Integer.toString(cardIndex))
					&& ( (cardFields[i][1].matches("^VERIFY\\s+.*") == false)
					&& (cardFields[i][1].matches("^OR\\s+.*") == false) 
					&& (cardFields[i][1].matches("^CHECKSUM\\s+.*") == false)
					) ) {
				checksumFields.add(new DefFields(Integer.parseInt(cardFields[i][0]), cardFields[i][1]));
			}
		}
		return checksumFields;
	}

}
