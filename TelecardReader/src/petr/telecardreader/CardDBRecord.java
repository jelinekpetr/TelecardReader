package petr.telecardreader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;


public class CardDBRecord {

	Connection dbConnection;
	int recId;
	String catalog;
	String catalogNumber;
	String cardSerial;
	String chipSerial;
	String country;
	String countryExtra;
	String state;
	String manufacturer;
	String issuer;
	String content;
	String contentHash;
	String dateAdd;
	String whoAdd;
	String note;
	String csCountry;


	public CardDBRecord(Connection dbConnection,
			int recId,
			String catalog,
			String catalogNumber,
			String cardSerial,
			String chipSerial,
			String country,
			String countryExtra,
			String state,
			String manufacturer,
			String issuer,
			String content,
			String contentHash,
			String dateAdd,
			String whoAdd,
			String note,
			String csCountry) {

		this.dbConnection = dbConnection;
		this.recId = recId;
		this.catalog = catalog;
		this.catalogNumber = catalogNumber;
		this.cardSerial = cardSerial;
		this.chipSerial = chipSerial;
		this.country = country;
		this.countryExtra = countryExtra;
		this.state = state;
		this.manufacturer = manufacturer;
		this.issuer = issuer;
		this.content = content;
		this.contentHash = contentHash;
		this.dateAdd = dateAdd;
		this.whoAdd = whoAdd;
		this.note = note;
		this.csCountry = csCountry;

	}

	public int insert() {
		
		PreparedStatement pstmt = null;
		int ar = 0; //affected rows

		if (dbConnection != null) {
			try {
				String prepInsert = "insert into TEL_KARTY (" +
						"catalog, " +
						"catalog_number," +
						"card_serial," +
						"chip_serial," +
						"country," +
						"country_extra," +
						"state," +
						"manufacturer," +
						"issuer," +
						"content," +
						"content_hash," +
						"who_add," +
						"note" +
						")" +
						" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				pstmt = dbConnection.prepareStatement(prepInsert);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				TelecardReader.recError(new ErrorRecord("Chyba při vytváření objektu Statement", -11));
				return -1;
			}

			try {
				pstmt.setString(1, catalog);
				pstmt.setString(2, catalogNumber);
				pstmt.setString(3, cardSerial);
				pstmt.setString(4, chipSerial);
				pstmt.setString(5, country);
				pstmt.setString(6, countryExtra);
				pstmt.setString(7, state);
				pstmt.setString(8, manufacturer);
				pstmt.setString(9, issuer);
				pstmt.setString(10, content);
				pstmt.setString(11, contentHash);
				pstmt.setString(12, whoAdd);
				pstmt.setString(13, note);
				ar = pstmt.executeUpdate();
				return ar;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}

		} else {

			String postInsert = "insert into TEL_KARTY values (null, '"+catalog+"','" +
					catalogNumber+"','" +
					cardSerial+"','" +
					chipSerial+"','" +
					country+"','" +
					countryExtra+"','" +
					state+"','"+
					manufacturer+"','" +
					issuer+"','" +
					content.replace("\n", "<CR>")+"','" +
					contentHash+"','" +
					new Timestamp(System.currentTimeMillis())+"','" +
					whoAdd+"','" +
					note.replace("\n", "<CR>")+"');";
			TempFile tmpSave = new TempFile();
			tmpSave.save(postInsert);
			//tmpSave.close();
			return 10;

		}

	}
	
	public int update() {
		
		PreparedStatement pstmt = null;
		int ar = 0; //affected rows
		
		if (dbConnection != null) {

			try {
				String prepInsert = "update TEL_KARTY set " +
						"catalog = ?, " +
						"catalog_number = ?," +
						"card_serial = ?," +
						"chip_serial = ?," +
						"country = ?," +
						"country_extra = ?," +
						"state = ?," +
						"manufacturer = ?," +
						"issuer = ?," +
						"content = ?," +
						"content_hash = ?," +
						"who_add = ?," +
						"note = ?" +
						" where rec_id = " + recId + ";";
				pstmt = dbConnection.prepareStatement(prepInsert);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				TelecardReader.recError(new ErrorRecord("Chyba při vytváření objektu Statement", -11));
				return -1;
			}

			try {
				pstmt.setString(1, catalog);
				pstmt.setString(2, catalogNumber);
				pstmt.setString(3, cardSerial);
				pstmt.setString(4, chipSerial);
				pstmt.setString(5, country);
				pstmt.setString(6, countryExtra);
				pstmt.setString(7, state);
				pstmt.setString(8, manufacturer);
				pstmt.setString(9, issuer);
				pstmt.setString(10, content);
				pstmt.setString(11, contentHash);
				pstmt.setString(12, whoAdd);
				pstmt.setString(13, note);
				ar = pstmt.executeUpdate();
				return ar;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}

		} else {
			return -2;
		}

	}//end update()
	
	public int delete() {
		
		int ar = 0; //affected rows
		
		if (dbConnection != null) {
			
			String delete = "delete from TEL_KARTY where rec_id = "+recId;
			
			try {
				Statement stmt = dbConnection.createStatement();
				ar = stmt.executeUpdate(delete);
				return ar;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}
			
		} else {
			return -2;
		}		
	}
	
	public boolean isAlreadySaved() {
		
		if (dbConnection != null) {
			try {
				String query = "select rec_id from TEL_KARTY where content_hash = '"+contentHash+"';";
				Statement stmt = dbConnection.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				
				int id = 0;
				while (rs.next()) {
					id = rs.getInt("rec_id");
				}
				
				return (id != 0) ? true : false;
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				TelecardReader.recError(new ErrorRecord("Chyba při čtení katalogů", -12));
				return false;
			}
		} else {
			return false;
		}
		
	}
	
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	public String getCatalog() {
		return this.catalog;
	}
	public void setCatalogNumber(String catalogNumber) {
		this.catalogNumber = catalogNumber;
	}
	public String getCatalogNumber() {
		return this.catalogNumber;
	}
	public void setCardSerial(String cardSerial) {
		this.cardSerial = cardSerial;
	}
	public String getCardSerial() {
		return this.cardSerial;
	}
	public void setChipSerial(String chipSerial) {
		this.chipSerial = chipSerial;
	}
	public String getChipSerial() {
		return this.chipSerial;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCountry() {
		return this.country;
	}
	public void setCountryExtra(String countryExtra) {
		this.countryExtra = countryExtra;
	}
	public String getCountryExtra() {
		return this.countryExtra;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getState() {
		return this.state;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getManufacturer() {
		return this.manufacturer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public String getIssuer() {
		return this.issuer;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent() {
		return this.content;
	}
	public void setContentHash(String contentHash) {
		this.contentHash = contentHash;
	}
	public String getContentHash() {
		return this.contentHash;
	}
	public void setWhoAdd(String whoAdd) {
		this.whoAdd = whoAdd;
	}
	public String getWhoAdd() {
		return this.whoAdd;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getNote() {
		return this.note;
	}
	
}

