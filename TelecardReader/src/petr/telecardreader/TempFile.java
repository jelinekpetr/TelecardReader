
package petr.telecardreader;

import java.io.*;
import java.util.ArrayList;

public class TempFile extends File {
	
	static String fileSeparator = System.getProperty("file.separator");
	static String tempFileName = System.getProperty("user.home")+fileSeparator+".telecardreader"+fileSeparator+"save_post.sql";
	String newline = "\n";
	boolean notSQL = false;
	
	public TempFile() {
		
		super(tempFileName);
		
	}
	
	public TempFile(String fileName) {
		
		super(System.getProperty("user.home")+fileSeparator+".telecardreader"+fileSeparator+fileName);
		notSQL = true;
		
	}
	
	public void save(String insertString) {
		
		FileWriter writeTemp = null;
		BufferedWriter wt = null;
		
		try {
			if (notSQL) {                         // zapis do jineho nez sql temp souboru
				writeTemp = new FileWriter(this);
			} else {
				writeTemp = new FileWriter(this, true); //v pripade sql se zapisuje na konec - predpoklada se zapis po radcich
			}
			wt = new BufferedWriter(writeTemp);
			wt.write(insertString);
			wt.write(newline);
			wt.close();
			writeTemp.close();
		} catch (IOException e) {
			TelecardReader.recError(new ErrorRecord("Chyba během zápisu do dočasného souboru." + newline + "(" + this.getAbsolutePath() + ")", -8));
		}
		
	}
	
	public ArrayList<String> read() {
		
		ArrayList<String> output = new ArrayList<String>();
		String line;
		
		FileReader readTemp = null;
		BufferedReader rt = null;

		try {
			readTemp = new FileReader(this);
			rt = new BufferedReader(readTemp);
		} catch (FileNotFoundException e) {
			TelecardReader.recError(new ErrorRecord("Dočasný soubor nebyl nalezen." + newline + "(" + getAbsolutePath() + ")", -7));
		}	
		
		try {
			while ((line = rt.readLine()) != null) {
				output.add(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block"<i>"
			e.printStackTrace();
			TelecardReader.recError(new ErrorRecord("Chyba při čtení z dočasného souboru." + newline + "(" + getAbsolutePath() + ")", -6));
		}
		
		return (output.size() > 0) ? output : null;
	}
	
	/*
	public void close() {
		
		this.close();
		//this.deleteOnExit();
		
	}
	*/

}
