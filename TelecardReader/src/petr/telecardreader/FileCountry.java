package petr.telecardreader;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileCountry extends File {

	static String fileSeparator = System.getProperty("file.separator");
	static String configFileName = System.getProperty("user.home")
			+ fileSeparator + ".telecardreader" + fileSeparator
			+ "countries.txt";

	String fileName;
	String newline = "\n";

	public FileCountry(String parFileName) {

		super(parFileName);
		this.fileName = parFileName;

	}

	public FileCountry() {

		super(configFileName);
		this.fileName = configFileName;

	}
	
	public ArrayList<Country> getAllOffline() {
		
		FileReader readCountries = null;
		BufferedReader rc = null;
		String line;
		Country country;
		ArrayList<Country> countries = new ArrayList<Country>();
		
		if (this.exists() == true) {
			
			try {
				readCountries = new FileReader(this);
				rc = new BufferedReader(readCountries);
			} catch (FileNotFoundException e) { //zbytecne - testuji this.exists
				TelecardReader.recError(new ErrorRecord(
						"Soubor se zeměmi nebyl nalezen." + newline + "("
								+ getAbsolutePath() + ")", -19));
			}
			
			try {
				while ((line = rc.readLine()) != null) {
					if (line.length() > 0) {
						country = new Country(line.split(";;")[0], line.split(";;")[1], line.split(";;")[2], line.split(";;")[3], line.split(";;")[4]);
						countries.add(country);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block"<i>"
				e.printStackTrace();
				TelecardReader.recError(new ErrorRecord("Chyba při čtení ze souboru." + newline + "(" + getAbsolutePath() + ")", -6));
			}
			
		}
		
		return countries;
	}
	
}
