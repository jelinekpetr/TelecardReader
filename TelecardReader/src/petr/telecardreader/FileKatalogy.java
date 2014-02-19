package petr.telecardreader;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileKatalogy extends File {
	
	static String fileSeparator = System.getProperty("file.separator");
	static String configFileName = System.getProperty("user.home")
			+ fileSeparator + ".telecardreader" + fileSeparator
			+ "catalogs.txt";

	String fileName;
	String newline = "\n";

	public FileKatalogy(String parFileName) {

		super(parFileName);
		this.fileName = parFileName;

	}

	public FileKatalogy() {

		super(configFileName);
		this.fileName = configFileName;

	}
	
	public ArrayList<String> getAllOffline() {
		
		FileReader readKatalogy = null;
		BufferedReader rk = null;
		String line;
		ArrayList<String> katalogy = new ArrayList<String>();
		
		if (this.exists() == true) {
			
			try {
				readKatalogy = new FileReader(this);
				rk = new BufferedReader(readKatalogy);
			} catch (FileNotFoundException e) { //zbytecne - testuji this.exists
				TelecardReader.recError(new ErrorRecord(
						"Soubor s katalogy nebyl nalezen." + newline + "("
								+ getAbsolutePath() + ")", -18));
			}
			
			try {
				while ((line = rk.readLine()) != null) {
					katalogy.add(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block"<i>"
				e.printStackTrace();
				TelecardReader.recError(new ErrorRecord("Chyba při čtení ze souboru." + newline + "(" + getAbsolutePath() + ")", -6));
			}
			
		}
	
		return katalogy;
		
	}
	

}
