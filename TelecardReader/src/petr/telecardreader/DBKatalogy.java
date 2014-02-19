package petr.telecardreader;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

public class DBKatalogy {
	
	Connection dbConnection;
	
	public DBKatalogy(Connection dbConnection) {
		
		this.dbConnection = dbConnection;
		
	}
	
	public ArrayList<String> getAll() {
		
		ArrayList<String> katalogy = new ArrayList<String>();
		
		if (dbConnection != null) {
			try {
				String query = "select distinct(catalog) from TEL_KARTY;";
				Statement stmt = dbConnection.createStatement();
				ResultSet rsKatalogy = stmt.executeQuery(query);
				
				while (rsKatalogy.next()) {
					katalogy.add(rsKatalogy.getString(1));
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				TelecardReader.recError(new ErrorRecord("Chyba při čtení katalogů", -15));
				//nacteni ze souboru
				FileKatalogy fileKatalogy = new FileKatalogy();
				katalogy = fileKatalogy.getAllOffline();
				//return null;
			}
		} else {
			//plneni ze souboru
			FileKatalogy fileKatalogy = new FileKatalogy();
			katalogy = fileKatalogy.getAllOffline();
		}
		
		return katalogy;
		
	}

}
