
package petr.telecardreader;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class CardDBSearch {
	
	private Connection dbConnection;
	private int idRec = 0;
	private ArrayList<ParamValue> whereClause;
	private int type = 0;
	
	public CardDBSearch(Connection dbConnection, int idRec) {
		
		this.dbConnection = dbConnection;
		this.idRec = idRec;
		this.type = 0;
		
	}
	
	public CardDBSearch(Connection dbConnection, ArrayList<ParamValue> whereClause) {
		
		this.dbConnection = dbConnection;
		this.whereClause = whereClause;
		this.type = 1;
		
	}
	
	private String assemblyWhere() {

		String wholeWhere = "";
		String con = "";

		if (type == 1) {

			for (ParamValue pm: whereClause) {
				if (pm.isOr()) {
					con = " or  ";
				} else {
					con = " and ";
				}
				wholeWhere += (con + pm.getWhereClause());
			}

			return wholeWhere.substring(4, wholeWhere.length());

		}

		if (type == 0) {
			if (idRec != 0) {
				return "rec_id = "+idRec;
			} else {
				return "1";
			}
		}

		return "";
		
	}

	public ResultSet getResultSet() {

		String query = null;
		ResultSet rsKarty = null;

		if (dbConnection != null) {
			try {
				if (idRec == 0) {
					query = "select tk.rec_id," +
							"tk.catalog, tk.catalog_number, tk.card_serial, tk.chip_serial, tk.country, tk.country_extra, tk.state," +
							"tk.manufacturer, tk.issuer, tk.content, tk.content_hash, date_format(tk.date_add, '%W %e.%c.%Y, %k:%i') as fdate_add, tk.who_add, tk.note," +
							"cz.cs_kr from TEL_KARTY tk LEFT JOIN CISEL_ZEMI cz on cz.kod_3 = tk.country where "+assemblyWhere()+" order by catalog, catalog_number, card_serial";
				} else {
					query = "select tk.rec_id," +
							"tk.catalog, tk.catalog_number, tk.card_serial, tk.chip_serial, tk.country, tk.country_extra, tk.state," +
							"tk.manufacturer, tk.issuer, tk.content, tk.content_hash, date_format(tk.date_add, '%W %e.%c.%Y, %k:%i') as fdate_add, tk.who_add, tk.note," +
							"cz.cs_kr from TEL_KARTY tk LEFT JOIN CISEL_ZEMI cz on cz.kod_3 = tk.country where rec_id = "+idRec+" order by catalog, catalog_number, card_serial";
				}
				//System.out.println(assemblyWhere());
				Statement stmt = dbConnection.createStatement();
				rsKarty = stmt.executeQuery(query);

			} catch (SQLException e) {
				e.printStackTrace();
				TelecardReader.recError(new ErrorRecord("Chyba při čtení tabulky karet", -13));
				return null;
			}
		}
		
		return rsKarty;
	}
	
	public CardDBRecord getDBRecord() throws SQLException {
		
		ArrayList<CardDBRecord> recList = null;
		
		recList = getArrayList();
		return recList.get(0);
		
	}
	
	public ArrayList<CardDBRecord> getArrayList() throws SQLException {
		
		ArrayList<CardDBRecord> cardRecords = new ArrayList<CardDBRecord>();
		
		ResultSet rs = getResultSet();
		
		while (rs.next()) {
			cardRecords.add(new CardDBRecord(dbConnection,
					rs.getInt("rec_id"),
					rs.getString("catalog"),
					rs.getString("catalog_number"),
					rs.getString("card_serial"),
					rs.getString("chip_serial"),
					rs.getString("country"),
					rs.getString("country_extra"),
					rs.getString("state"),
					rs.getString("manufacturer"),
					rs.getString("issuer"),
					rs.getString("content"),
					rs.getString("content_hash"),
					rs.getString("fdate_add"),
					rs.getString("who_add"),
					rs.getString("note"),
					rs.getString("cs_kr")));
		}
		
		return cardRecords;
		
	}

}
