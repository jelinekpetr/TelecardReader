
/*
 * $LastChangedDate$
 * $LastChangedRevision$
 */


package petr.telecardreader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CardDatabase {

	Connection dbConnection;
	ConfigFile configuration;
	DesEncrypter desEncrypt;
	DatabaseMetaData metaData;
	boolean connected = false;
	boolean dbCreated = false;
	Statement stmt;
	PreparedStatement pstmt;


	public CardDatabase() {

		String fileSeparator = System.getProperty("file.separator");
		String configFileName = System.getProperty("user.home")+fileSeparator+".telecardreader"+fileSeparator+"telecardreader.conf";
		configuration = new ConfigFile(configFileName);
		desEncrypt = new DesEncrypter("telecard");

		String remDBAddress = configuration.getPropertyValue("remoteDBAddress");
		String remDBPort = configuration.getPropertyValue("remoteDBPort");
		String remDBName = configuration.getPropertyValue("remoteDBName").toUpperCase();
		String remDBUser = configuration.getPropertyValue("remoteDBUsername");
		String remDBPass = desEncrypt.decrypt(configuration.getPropertyValue("remoteDBPassword"));

		String remUrl = "jdbc:mysql://"+remDBAddress+":"+remDBPort+"/"+remDBName+"?"
				+ "user="+remDBUser+"&"+"password="+remDBPass+"&useEncoding=true&characterEncoding=UTF-8";

		try {
			dbConnection = DriverManager.getConnection(remUrl);
			TelecardReader.statusRegistr = "Připojeno ke vzdálené MySQL databázi."; 
			connected = true;
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			dbConnection = null;
			TelecardReader.recError(new ErrorRecord("MySQL databáze není k dispozici...", -9));
		}

		if (connected) {
			try {
				metaData = dbConnection.getMetaData();
				ResultSet rs = metaData.getTables(null, null, "TEL_KARTY", new String[] {"TABLE"});

				while (rs.next()) {
					dbCreated = true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				TelecardReader.recError(new ErrorRecord("MySQL databáze není k dispozici...", -9));
			}

			if (!dbCreated) {
				TelecardReader.recError(new ErrorRecord("V databázi nejsou vytvořeny potřebné tabulky", -10));
			}
		}

		if (connected && dbCreated) {
			insertTemp();
			fillOfflineFiles();
		}
	}

	private int insertTemp() {
		String err = "";
		ArrayList<String> sqlList;
		ArrayList<String> newSqlList = new ArrayList<String>();
		TempFile tempFile = new TempFile();
		String[] sqlFields;
		String[] oldRecord = new String[12];

		if (tempFile.exists()) {
			showInfoDialog("Do databáze budou vloženy záznamy z dočasného souboru.");
			sqlList = tempFile.read();
			int s = sqlList.size();
			int p = 0;
			int c = 0;
			int ins = 0;
			for (int i = 0; i < s; i++) {
				sqlFields = sqlList.get(i).split(",");
				String contentHash = sqlFields[11].substring(1, sqlFields[11].length()-1);
				try {
					Statement stmt = dbConnection.createStatement();
					ResultSet rs = stmt.executeQuery("select * from TEL_KARTY where content_hash = '"+contentHash+"';");
					while (rs.next()) {
						oldRecord[0] = String.valueOf(rs.getInt("rec_id"));
						oldRecord[1] = rs.getString("catalog");
						oldRecord[2] = rs.getString("catalog_number");
						oldRecord[3] = rs.getString("card_serial");
						oldRecord[4] = rs.getString("chip_serial");
						oldRecord[5] = rs.getString("country");
						oldRecord[6] = rs.getString("country_extra");
						oldRecord[7] = rs.getString("state");
						oldRecord[8] = rs.getString("manufacturer");
						oldRecord[9] = rs.getString("issuer");
						oldRecord[10] = rs.getString("note");
						c++;
					}
				} catch (SQLException e) {
					TelecardReader.recError(new ErrorRecord("Chyba při čtení tabulky karet", -13)); 
				}
				if (c > 0) { // v db uz je karta ulozena
					JFrame dialogFrame = new JFrame();
					dialogFrame.setVisible(false);
					Object stringArray[] = {"Zobrazit rozdíly", "Přeskočit"};
					Object[] obj = {"Karta s číslem "+sqlFields[2]+" je již v databázi uložena. Co si přejete udělat?\n\n"};
					int odp = JOptionPane.showOptionDialog(dialogFrame, obj,
							"Existující karta", JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE, null, stringArray, obj);
					if (odp == 0) { //zobrazit rozdily
						Differ diff = new Differ(oldRecord, sqlFields);
						int odp1 = diff.showDialog(); // 0 - ponechat puvodni, 1 - prepsat novou
						//System.out.println(odp1); //debug
						if (odp1 == 0) {
							//vymazat ze souboru
						} else if (odp1 == 1) {
							try {
								//delete old
								Statement stmt = dbConnection.createStatement();
								stmt.executeUpdate("delete from TEL_KARTY where content_hash = '"+contentHash+"';");
								//insert new
								stmt.executeUpdate(sqlList.get(i).replace("<CR>", "\n"));
								ins++;
							} catch (SQLException e) {
								err += "Karta pod katalogovým číslem "+sqlFields[2]+" nešla vložit do databáze. Chyba: "+e.getMessage()+"\n";
								TelecardReader.recError(new ErrorRecord(err, -16));
								p++; //pocet chyb
							}
							//vymazat ze souboru
						} else if (odp1 == 2) {
							newSqlList.add(sqlList.get(i)); //ponechat v souboru
						}

					} else if (odp == 1) {
						newSqlList.add(sqlList.get(i)); //ponechat v souboru
					}
				} else { //karta v db jeste neni
					try {
						//insert new
						Statement stmt = dbConnection.createStatement();
						stmt.executeUpdate(sqlList.get(i).replace("<CR>", "\n"));
						ins++;
					} catch (SQLException e) {
						err += "Karta pod katalogovým číslem "+sqlFields[2]+" nešla vložit do databáze. Chyba: "+e.getMessage()+"\n";
						TelecardReader.recError(new ErrorRecord(err, -16));
						p++; //pocet chyb
					}
				}
			}
			if (tempFile.delete()) {
				if (newSqlList.size() > 0) {
					tempFile = new TempFile();
					for (int j = 0; j < newSqlList.size(); j++) {
						tempFile.save(newSqlList.get(j));
					}
				}
			}
			return ins; // pocet nove ulozenych zaznamu
		}

		return 0;

	}

	public void showInfoDialog(String text) {
		JFrame dialogFrame = new JFrame();
		dialogFrame.setVisible(false);
		JOptionPane.showMessageDialog(dialogFrame, text,
				"Informace", JOptionPane.INFORMATION_MESSAGE);
	}

	private int fillOfflineFiles() {

		String newline = "\n";
		String tempCountriesName = "countries.txt";
		String tempKatName = "catalogs.txt";

		ArrayList<String> katalogy = new ArrayList<String>();
		ArrayList<Country> countries = new ArrayList<Country>();

		TempFile tempCountries = new TempFile(tempCountriesName);
		TempFile tempKatalog = new TempFile (tempKatName);

		DBKatalogy dbKat = new DBKatalogy(dbConnection);
		DBCountry dbCountry = new DBCountry(dbConnection);

		katalogy = dbKat.getAll();
		countries = dbCountry.getAll();

		// Countries
		String content = "";
		for (int i = 0; i < countries.size(); i++) {
			content += countries.get(i).numCode+";;"+countries.get(i).shortEN+";;"+countries.get(i).longEN+";;"+countries.get(i).shortCZ+";;"+countries.get(i).longCZ+newline;
		}
		tempCountries.save(content);

		// Katalogy
		content = "";
		for (int i = 0; i < katalogy.size(); i++) {
			content += katalogy.get(i)+newline;
		}
		tempKatalog.save(content);

		return 0;

	}

	public boolean isConnected() {

		return connected;

	}

	public boolean isCreated() {

		return dbCreated;

	}

	public Connection connection() {

		return dbConnection;

	}

	public void close() {

		if (dbConnection != null) {
			try {
				dbConnection.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
