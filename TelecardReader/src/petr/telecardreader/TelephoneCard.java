package petr.telecardreader;

import java.util.ArrayList;


//import petr.telecardreader.ChipyDef.DefFields;

public class TelephoneCard {
	
	ChipyDef chipDef = new ChipyDef();
	
	ArrayList<String> cardContent; 
	ArrayList<InfoTable> infoTable = new ArrayList<InfoTable>();
	int[] cardContentBit;
	private String cardDesc;
	private String cardName;
	private String cardIssuer;
	private String cardCountry;
	private String cardUsage;
	private String cardChecksumState;
	
	//konstruktor
	public TelephoneCard(ArrayList<String> parCardContent) {
		
		this.cardContent = parCardContent;
		cardContentBit = new int[cardContent.size() * 8];
		cardContentBit = contentToBitArray();
		int foundCard = this.findCardVerify();
		if (foundCard != -1) {
			String[] descCard = chipDef.cards[foundCard].split("\\s*,\\s*");
			cardName = descCard[0].substring(descCard[0].indexOf("\"") + 1, descCard[0].length()-1);
			if (descCard.length > 1) {
				cardCountry = descCard[1].substring(descCard[1].indexOf("\"") + 1, descCard[1].length()-1);
			}
			if (descCard.length > 2) {
				cardUsage = descCard[2].substring(descCard[2].indexOf("\"") + 1, descCard[2].length()-1);
			}
			if (descCard.length > 3) {
				cardIssuer = descCard[3].substring(descCard[3].indexOf("\"") + 1, descCard[3].length()-1);
			}
			if (checkChecksum(chipDef.getDefLines(foundCard, "CHECKSUM"))) {
				cardChecksumState = "OK";
			} else {
				cardChecksumState = "Failed";
			}
			//infoTable = getAllInfo(foundCard);
			getAllInfo(foundCard);
		} else {
			cardName = "Card not found";
			cardCountry = "Unknown";
			cardUsage = "Unknown";
			cardIssuer = "Unknown";
			cardChecksumState = "N/A";
		}
	}

	public String getCardDesc() {
		return cardDesc;
	}
	
	public String getCardName() {
		return cardName;
	}
	
	public String getCardIssuer() {
		return cardIssuer;
	}
	
	public String getCardCountry() {
		return cardCountry;
	}
	
	public String getCardUsage() {
		return cardUsage;
	}
	
	public String getCardChecksumState() {
		return cardChecksumState;
	}
	
	private int[] contentToBitArray() {
		
		int[] bitArray = new int[cardContent.size() * 8];
		int bitPointer = 0;
		
		for (int i = 0; i < cardContent.size(); i++) {
			for (int j = 0; j < 8; j++) {
				if (((Integer.parseInt(cardContent.get(i)) & (128>>j)) >> (7-j)) == 1) {
					bitArray[bitPointer] = 1;
					bitPointer++;
				} else {
					bitArray[bitPointer] = 0;
					bitPointer++;
				}
			}
		}
		
		return bitArray;
	}
	
	private String binToHex(int lowestBit, int highestBit) {

		String realResult = "";
		String binResult = "";

		int j = 0;
		try {
			for (int i = lowestBit; i <= highestBit; i++) {
				binResult =  binResult + Integer.toString(cardContentBit[i]);
				j++;
				if (j == 4) {
					realResult = realResult + Integer.toHexString(Integer.parseInt(binResult, 2)) ;
					j = 0;
					binResult = "";
				}	
			}
			realResult = "$"+realResult.toUpperCase();
			////System.out.println(realResult);
		} catch (Exception e) {
			realResult = "$XX";
		}
		return realResult;
	}
	
	private int binToDec(int lowestBit, int highestBit) {
		
		int result = 0;
		int j = 0;
		if (lowestBit < highestBit) {
			for (int i = lowestBit; i <= highestBit; i++) {
				result += ((cardContentBit[i]) * (1<<j));
				j++;
			}
		} else {
			for (int i = lowestBit; i >= highestBit; i--) {
				result += ((cardContentBit[i]) * (1<<j));
				j++;
			}
		}
		return result;
	}
	
	private boolean verify(String parVerify) {
		
		String[] ver1 = new String[2];
		String[] ver2 = new String[4];
		
		int lowestBit, highestBit;
		ver1 = parVerify.split("\\s+");
		ver2 = ver1[1].split("\\s*,\\s*");
		try {
			lowestBit = Integer.parseInt(ver2[0]);
		} catch (Exception e) {
			//System.out.println("Chyba pri lowest");
			e.printStackTrace();
			return false;
		}
		try {
			highestBit = Integer.parseInt(ver2[1]);
		} catch (Exception e) {
			//System.out.println("Chyba pri highest");
			e.printStackTrace();
			return false;
		}
		
		String preresult = ver2[3];
		////System.out.println(lowestBit + "-" + highestBit + "-" + preresult);
		////System.out.println("Vysledek: "+realResult);
		if (binToHex(lowestBit, highestBit).equals(preresult.toUpperCase())) {
			////System.out.println("Shoda: " + preresult.toUpperCase());
			return true;
		} else {
			return false;
		}
	}
	
	private int findCardVerify() {
		
		int procId = -2;
		int lastId = -1;
		String lastLine = "-";
		boolean orFound = false;
		boolean andFound = false;
		
		ArrayList<ChipyDef.DefFields> verifyFields = new ArrayList<ChipyDef.DefFields>();
		
		verifyFields = chipDef.getVerifyLines();

		for (ChipyDef.DefFields tableVer : verifyFields) {
			procId = tableVer.idCard;
			if ((lastId != procId) && ((lastLine.equals("VERIFY") == true) || (lastLine.equals("OR") == true))) {
				andFound = (andFound & orFound);
			}
			if ((andFound == true) && (lastId != procId)) {
				//break;
				cardDesc = chipDef.cards[lastId];
				return lastId;
			}
			if (procId != lastId) {
				lastLine = "-";
				orFound = false;
				andFound = true;
			}
			if (tableVer.fieldDef.matches("^VERIFY\\s+.*") == true) {
				if (lastLine.equals("-") == true) {
					////System.out.println("Last line = " + lastLine + ", orFound = " + orFound +  "andFound = " + andFound);
					orFound = verify(tableVer.fieldDef);
				}
				if ((lastLine.equals("VERIFY") == true) || (lastLine.equals("OR") == true)) {
					////System.out.println("Last line = " + lastLine + ", orFound = " + orFound + " andFound = " + andFound);
					andFound = (orFound & andFound);
					orFound = verify(tableVer.fieldDef);
				}
				lastLine = "VERIFY";
			}
			if (tableVer.fieldDef.matches("^OR\\s+.*") == true) {
				////System.out.println("Last line = " + lastLine + ", orFound = " + orFound + " andFound = " + andFound);
				orFound = (orFound | verify(tableVer.fieldDef));
				lastLine = "OR";
			}
			lastId = tableVer.idCard;
		}
		return -1;
	}
	
	private boolean checksum(String parCheckSumLine) {
		// startchecksum,endchecksum,startzone,endzone,seed,value
		
		String[] check1 = new String[2];
		String[] check2 = new String[6];
		String seed;
		int startChecksum, endChecksum, startZone, endZone, value;
		
		int cardCheckValue;
		int numberOne = 0;
		int realCheckSum = -1;
		
		check1 = parCheckSumLine.split("\\s+");
		check2 = check1[1].split("\\s*,\\s*");
		try {
			startChecksum = Integer.parseInt(check2[0]);
		} catch (Exception e) {
			//System.out.println("Chyba pri startChecksum");
			e.printStackTrace();
			return false;
		}
		try {
			endChecksum = Integer.parseInt(check2[1]);
		} catch (Exception e) {
			//System.out.println("Chyba pri endChecksum");
			e.printStackTrace();
			return false;
		}
		try {
			startZone = Integer.parseInt(check2[2]);
		} catch (Exception e) {
			//System.out.println("Chyba pri startZone");
			e.printStackTrace();
			return false;
		}
		try {
			endZone = Integer.parseInt(check2[3]);
		} catch (Exception e) {
			//System.out.println("Chyba pri endZone");
			e.printStackTrace();
			return false;
		}
		try {
			seed = check2[4];
		} catch (Exception e) {
			//System.out.println("Chyba pri seed");
			e.printStackTrace();
			return false;
		}
		try {
			value = Integer.parseInt(check2[5]);
		} catch (Exception e) {
			//System.out.println("Chyba pri value");
			e.printStackTrace();
			return false;
		}
		String tmpHex = binToHex(startChecksum, endChecksum);
		cardCheckValue = Integer.parseInt(tmpHex.substring(1), 16);
		for (int i = startZone; i <= endZone; i++) {
			if (cardContentBit[i] == 1) {
				numberOne++;
			}
		}
		realCheckSum = Integer.parseInt(seed.substring(1), 16) + (value * numberOne);
		if (realCheckSum == cardCheckValue) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean checkChecksum(ArrayList<ChipyDef.DefFields> checksumLines) {
		
		boolean result = false;
		
		if (checksumLines.size() > 0) {
			for (ChipyDef.DefFields cl : checksumLines) {
				if (checksum(cl.fieldDef) == true) {
					result = true;
				} else {
					result = false;
				}
			}
		} else {
			return true;
		}
		return result;
	}
	
	class InfoTable {
		
		String parameter;
		String value;
		
		public InfoTable(String parameter, String value) {
			this.parameter = parameter;
			this.value = value;
		}
		
		public String getParameter() {
			return this.parameter;
		}
		
		public String getValue() {
			return this.value;
		}
	}
	
	private int[] getInitValue(int idCard) {
		
		String[] bcdParams = new String[5];
		String[] tableLine = new String[3];
		int decValue = 0;
		int lowestBit = 0;
		int highestBit = 0;
		int[] result = new int[2];
		String hexValue = "";
		
		ArrayList<ChipyDef.DefFields> valueFields = new ArrayList<ChipyDef.DefFields>();
		valueFields = chipDef.getDefLines(idCard, "VALUE");
		if (valueFields.get(0).fieldDef.matches(".*BCD.*")) {
			bcdParams = valueFields.get(0).fieldDef.split("\\s*,\\s*");
			lowestBit = Integer.parseInt(bcdParams[1]);
			highestBit = Integer.parseInt(bcdParams[2]);
			hexValue = binToHex(lowestBit, highestBit).substring(1);
			result[0] = (Integer.parseInt(hexValue) * Integer.parseInt(bcdParams[3])) + Integer.parseInt(bcdParams[4]);
			if (bcdParams.length > 3) {
				result[1] = Integer.parseInt(bcdParams[3]);
			} else {
				result[1] = 0;
			}
			return result;
		}
		if (valueFields.get(0).fieldDef.matches(".*BINARY.*")) {
			bcdParams = valueFields.get(0).fieldDef.split("\\s*,\\s*");
			lowestBit = Integer.parseInt(bcdParams[1]);
			highestBit = Integer.parseInt(bcdParams[2]);
			for (int i = lowestBit; i <= highestBit; i++) {
				decValue =+ (cardContentBit[i] * (1<<(i-lowestBit)));
			}
			result[0] = (decValue * Integer.parseInt(bcdParams[3])) + Integer.parseInt(bcdParams[4]);
			if (bcdParams.length > 3) {
				result[1] = Integer.parseInt(bcdParams[3]);
			} else {
				result[1] = 0;
			}
			return result;
		}
		if (valueFields.get(0).fieldDef.matches(".*TABLE.*")) {
			bcdParams = valueFields.get(0).fieldDef.split("\\s*,\\s*");
			lowestBit = Integer.parseInt(bcdParams[1]);
			highestBit = Integer.parseInt(bcdParams[2]);
			hexValue = binToHex(lowestBit, highestBit);
			for (int i = 1; i < valueFields.size(); i++) {
				tableLine = valueFields.get(i).fieldDef.split("\\s*,\\s*");
				if (tableLine[0].contains(hexValue) == true) {
					result[0] = Integer.parseInt(tableLine[1]);
					if (tableLine.length > 2) {
						result[1] = Integer.parseInt(tableLine[2]);
					} else {
						result[1] = 0;
					}
					return result;
				}
			}
			
		}
		return result;
	}
	
	private int getCreditValue(int idCard) {
		
		String[] creditLine = new String[7];
		int lowestBit = 0;
		int highestBit = 0;
		int countOnes = 0;
		int cardCredit = 0;
		boolean token = false;
		int bitAreaStart = 0;
		int bitAreaEnd = 0;
		int bitAreaSize = 0;
		int bitTokensCount = 0;
		int multiplier = 0;
		
		ArrayList<ChipyDef.DefFields> creditFields = new ArrayList<ChipyDef.DefFields>();
		creditFields = chipDef.getDefLines(idCard, "CREDIT");
		
		for (int i = 0; i < creditFields.size(); i++) {
			countOnes = 0;
			creditLine = creditFields.get(i).fieldDef.split("\\s*,\\s*");
			if (creditLine[0].contains("UNIT") || creditLine[0].contains("COUNT")) {
				lowestBit = Integer.parseInt(creditLine[1]);
				highestBit = Integer.parseInt(creditLine[2]);
				for (int j = lowestBit; j <= highestBit; j++) {
					if (cardContentBit[j] == 1) {
						countOnes++;
					}
				}
				cardCredit =+ (Integer.parseInt(creditLine[3]) * countOnes);
			}
			if (creditLine[0].contains("GROUP")) {
				lowestBit = Integer.parseInt(creditLine[2]);
				highestBit = Integer.parseInt(creditLine[1]);
				int ii = highestBit;
				while (ii >= lowestBit) {
					if (cardContentBit[ii] == 1) {
						break;
					}
					ii--;
				}
				cardCredit =+ (Integer.parseInt(creditLine[3]) * (ii - lowestBit));
			}
			if (creditLine[0].contains("TOKEN")) {
				token = true;
				lowestBit = Integer.parseInt(creditLine[1]);
				highestBit = Integer.parseInt(creditLine[2]);
				bitAreaStart = Integer.parseInt(creditLine[3]);
				bitAreaEnd = Integer.parseInt(creditLine[4]);
				bitAreaSize = Integer.parseInt(creditLine[5]);
				bitTokensCount = Integer.parseInt(creditLine[6]);
				break;
			}
		}
		if (token == true) {
			for (int iii = 0; iii < bitTokensCount; iii++) {
				String tokenType = binToHex(lowestBit + (iii*bitAreaSize), highestBit + (iii*bitAreaSize));
				for (int jjj = 1; jjj < creditFields.size(); jjj++) {
					creditLine = creditFields.get(jjj).fieldDef.split("\\s*,\\s*");
					multiplier = creditLine[0].contains(tokenType) ? Integer.parseInt(creditLine[1]) : 0;
					if (multiplier != 0) {
						break;
					}
				}
				countOnes = 0;
				for (int kkk = (bitAreaStart + (iii*bitAreaSize)); kkk < (bitAreaEnd + (iii*bitAreaSize)); kkk++) {
					if (cardContentBit[kkk] == 1) {
						countOnes++;
					}
				}
				cardCredit =+ (countOnes * multiplier);
			}
		}
		return cardCredit;
	}
	
	private void addDisplayValues(int idCard) {
		
		String label = "-";
		String value = "";
		String format = "";
		//int len = 0;
		int decResult = 0;
		String hexaResult = "";
		String textResult = "";
		
		ArrayList<ChipyDef.DefFields> defFields = new ArrayList<ChipyDef.DefFields>();
		
		defFields = chipDef.getDefLines(idCard, "DISPLAY");
		//Iterator<DefFields> linesIter = defFields.iterator();

		for (ChipyDef.DefFields defLine : defFields) {
			String displayLine[] = defLine.fieldDef.split("\\s*,\\s*");
			//System.out.println("-----"+displayLine[0]+"-----");
			if (displayLine[0].matches("^.+\".+\"")) {
				if (!label.equals("-")) {
					infoTable.add(new InfoTable(label, value));
				}
				label = displayLine[0].substring(displayLine[0].indexOf("\"")+1, displayLine[0].length() - 1);
			}
			if (displayLine[0].matches("^.*FORMAT.*")) {
				if (format.matches("^.*DEC.*")) {
					value = value + Integer.toString(decResult);
				}
				if (format.matches("^.*HEXA.*")) {
					value = value + hexaResult;
				}
				value = value + textResult;
				textResult = "";
				format = displayLine[1];
				//System.out.println("Format: "+format);
				//len = Integer.parseInt(displayLine[2]);
				decResult = 0;
				hexaResult = "";
			}
			if (displayLine[0].matches("^.*TEXT.*")) {
				textResult = textResult + displayLine[1].substring(displayLine[1].indexOf("\"")+1, displayLine[1].length() - 1);
			}
			if (displayLine[0].matches("^.*BINARY.*")) {
				if (format.matches("^.*DEC.*")) {
					//decResult += (binToDec(Integer.parseInt(displayLine[1]), Integer.parseInt(displayLine[2])) * Integer.parseInt(displayLine[3]));
					decResult += (binToDec(Integer.parseInt(displayLine[2]), Integer.parseInt(displayLine[1])));
					//decResult = (binToDec(Integer.parseInt(displayLine[1]), Integer.parseInt(displayLine[2])) * Integer.parseInt(displayLine[3]));
					//System.out.println("++d+++"+decResult+"+++++");
				}
				if (format.matches("^.*HEXA.*")) {
					//System.out.println("Hexa");
					hexaResult = binToHex(Integer.parseInt(displayLine[1]), Integer.parseInt(displayLine[2])).substring(1);
					
				}
			}
		}
		if (format.matches("^.*DEC.*")) {
			value = value + Integer.toString(decResult);
			value = value + textResult;
		}
		if (format.matches("^.*HEXA.*")) {
			value = value + hexaResult;
			value = value + textResult;
		}
		if (!label.equals("-")) {
			infoTable.add(new InfoTable(label, value));
		}
	}
	
	private void addTextValues(int idCard) {
		
		ArrayList<ChipyDef.DefFields> textFields = chipDef.getDefLines(idCard, "TEXT");
		
		for (ChipyDef.DefFields tf : textFields) {
			String textParams[] = tf.fieldDef.split("^TEXT\\s+")[1].split("\\s*,\\s*");
			if ((textParams.length >= 5) && textParams[2].equals("EQ")) {
				if ( binToHex(Integer.parseInt(textParams[0]), Integer.parseInt(textParams[1])).equals(textParams[3]) ) {
					String note = (textParams.length == 6) ? textParams[5].substring(textParams[5].indexOf("\"")+1, textParams[5].length() - 1) : "Extra info";
					infoTable.add(new InfoTable(note, textParams[4].substring(textParams[4].indexOf("\"")+1, textParams[4].length() - 1)));
				}
			} else if (textParams.length == 2) {
				infoTable.add(new InfoTable(textParams[1].substring(textParams[1].indexOf("\"")+1, textParams[1].length() - 1), textParams[0].substring(textParams[0].indexOf("\"")+1, textParams[0].length() - 1)));
			} else if (textParams.length == 1) {
				infoTable.add(new InfoTable(textParams[0].substring(textParams[0].indexOf("\"")+1, textParams[0].length() - 1), ""));
			}
		}
		
	}
	
	//private ArrayList<InfoTable> getAllInfo(int idCard) {
	private void getAllInfo(int idCard) {
		
		int[] cardInitialValue = {-1, 0};
		int cardCreditValue = -1;
		boolean display = false;
		boolean text = false;
		
		ArrayList<ChipyDef.DefFields> defFields = new ArrayList<ChipyDef.DefFields>();
		//ArrayList<InfoTable> infoTablel = new ArrayList<InfoTable>();
		
		defFields = chipDef.getDefLines(idCard);
		for (ChipyDef.DefFields defLine : defFields) {
			////System.out.println(defLine.idCard + "-" + defLine.fieldDef);
			if ((defLine.fieldDef.matches("^VALUE\\s+.*")) && (cardInitialValue[0] == -1)) {
				cardInitialValue = getInitValue(idCard);
				infoTable.add(new InfoTable("Initial value", Integer.toString(cardInitialValue[0])));
			}
			if (defLine.fieldDef.matches("^CURRENCY\\s+.*")) {
				String[] currLine = new String[3];
				currLine = defLine.fieldDef.split("\\s*,\\s*");
				infoTable.add(new InfoTable("Price for 1 unit", currLine[0].substring(currLine[0].indexOf(" ") + 1) + " " + currLine[2].substring(1, currLine[2].length()-1)));
			}
			if ((defLine.fieldDef.matches("^CREDIT\\s+.*")) && (cardCreditValue == -1)) {
				cardCreditValue = getCreditValue(idCard);
				infoTable.add(new InfoTable("Current credit", Integer.toString(cardInitialValue[0] + cardCreditValue + cardInitialValue[1])));
			}
			if ((defLine.fieldDef.matches("^DISPLAY\\s+.*")) && !display) {
				addDisplayValues(idCard);
				display = true;
			}
			if ((defLine.fieldDef.matches("^TEXT\\s+.*")) && !text) {
				addTextValues(idCard);
				text = true;
			}
		}
		//return infoTablel;
	}
	
}
