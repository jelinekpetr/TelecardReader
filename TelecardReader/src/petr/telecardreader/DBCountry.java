package petr.telecardreader;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

public class DBCountry {
	
	Connection dbConnection;
	int selected = 0;
	
	public DBCountry(Connection dbConnection) {
		
		this.dbConnection = dbConnection;
		
	}
	
	public DBCountry(Connection dbConnection, int selected) {

		this.dbConnection = dbConnection;
		this.selected = selected;

	}
	
	public ArrayList<Country> getAll() {
		
		ArrayList<Country> countries = new ArrayList<Country>();
		String query = null;
		
		if (dbConnection != null) {
			try {
				if (selected == 1) {
					query = "select cz.kod_3, cz.en_kr, cz.en_dl, cz.cs_kr, cz.cs_dl from CISEL_ZEMI cz where cz.kod_3 in ( select distinct(country) from TEL_KARTY ) order by cz.cs_kr;";
				} else {
					query = "select kod_3, en_kr, en_dl, cs_kr, cs_dl from CISEL_ZEMI order by cs_kr;";
				}
				Statement stmt = dbConnection.createStatement();
				ResultSet rsCountries = stmt.executeQuery(query);
				
				while (rsCountries.next()) {
					countries.add(new Country(rsCountries.getString("kod_3"), rsCountries.getString("en_kr"), rsCountries.getString("en_dl"), rsCountries.getString("cs_kr"), rsCountries.getString("cs_dl")));
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				TelecardReader.recError(new ErrorRecord("Chyba při čtení číselníku zemí", -14));
				FileCountry fileCountries = new FileCountry();
				countries = fileCountries.getAllOffline();
			}
		} else {
			//doplnit plneni ze souboru
			FileCountry fileCountries = new FileCountry();
			countries = fileCountries.getAllOffline();
		}
		
		return countries;
	}

}
