package tietokanta;

import java.sql.*;
import java.util.*;

public class KantaLeffoille {
	private static Connection kanta;

	/*
	 * T�YSIN AUTOMATISOITU TAULUN LUONTI, ILMAN KOMENTORIVI�, TEHTY HELPOTTAMAAN SALIEN SY�TT�MIST� TIETOKANTAAN
	 */
	public static void taulunAutomaattinenLuonti(String nimi, String pvm, String kesto, String alkamisaika, String sali,
			String yksKoodi) {
		//KantaLeffoille.taulunRakennus(yksKoodi);
		KantaLeffoille.lisaysNaytokset(nimi, pvm, kesto, alkamisaika, sali, yksKoodi);

	}

	// N�YT�S TAULUUN UUDEN ELOKUVAN KUVAUKSEN LIS�YS
	// ----------------------------------------------------------------------------------------

	public static void lisaysNaytokset(String nimi, String pvm, String kesto, String alkamisaika, String sali,
			String yksKoodi) {
		try {
			Statement lause = kanta.createStatement();
			String kysely = "INSERT INTO naytokset (nimi, pvm, kesto, alkamisaika, sali, yks_koodi) VALUES('" + nimi
					+ "','" + pvm + "','" + kesto + "','" + alkamisaika + "','" + sali + "','" + yksKoodi + "');";
			lause.executeUpdate(kysely);
			lause.close();
			System.out.println("lis�ys onnistui");
		} catch (Exception e) {
			System.out.println("Virhe lisaysN�yt�kset" + e);
		}
	}

	// -----------------------------------------------------------------------------------------------------------
	// UUDEN N�YT�KSEN TAULUN LUONTI (JOKAISELLE N�YT�KSELLE OMA TAULU)
	// -------------------------------------------------------------------------------------------------
	public static void taulunRakennus( String sali, String yksKoodi) {
		String s = "";
		int paikkaN = 1;
		String luku = "";
		String lopullinen = "";
		try {

			Statement lause = kanta.createStatement();
			String kysely = "CREATE TABLE " + yksKoodi + "(paikka character varying(30), status character varying(30))  ";
			String kysely2="";
			lause.executeUpdate(kysely);
			
			System.out.println("Taulun luonti onnistui!");
			/*
			 * SALI2 PAIKKOJEN LUONTI, ALUSTUSARVO VAPAA
			 * 
			 */
			if (sali.equals("sali2")) {
				for (int i = 0; i < 2; i++) {
					if (i == 0) {
						s = "A";
					}
					if (i == 1) {
						s = "B";
					}
					for (int a = 0; a < 10; a++) {
						luku = Integer.toString(a + 1);
						lopullinen = s + luku;
						try {

							
							 kysely2 = "INSERT INTO " + yksKoodi + " (paikka, status) VALUES ('" + lopullinen
									+ "','VAPAA'); ";
							lause.execute(kysely2);
							
							System.out.println("Rivin alustus onnistui!");
						} catch (Exception e) {
							System.out.println("Virhe alustamisessa" + e);
						}
					}

					paikkaN++;
				}
			}
			/* SALI 1 LUONTI ALUSTUSARVO VAPAA*/
			if (sali.equals("sali1")) {
				for (int i = 0; i < 3; i++) {
					if (i == 0) {
						s = "A";
					}
					if (i == 1) {
						s = "B";
					}
					if (i == 2) {
						s = "C";
					}
					for (int a = 0; a < 10; a++) {
						luku = Integer.toString(a + 1);
						lopullinen = s + luku;
						try {

							//Statement lause = kanta.createStatement();
							kysely = "INSERT INTO " + yksKoodi + " (paikka, status) VALUES ('" + lopullinen
									+ "','VAPAA'); ";
							lause.execute(kysely);
							
							System.out.println("Rivin alustus onnistui!");
						} catch (Exception e) {
							System.out.println("Virhe alustamisessa" + e);
						}
					}

					paikkaN++;
				}
			}lause.close();
		} catch (Exception e) {
			System.out.println("Virhe taulunrakennuksessa" + e);
		}
	}
// -----------------------------------------------------------------------------------------
//							TIETOKANTA YHTEYDEN AVAAMINEN
//---------------------------------------------------------------------------------------------------------
	public static Connection alusta() {
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			kanta = DriverManager.getConnection("jdbc:postgresql://localhost:5432/leffa", "postgres", "salasana");
		} catch (Exception e) {
			System.out.println("Virhe tietokantakerroksessa: " + e);
		}
		return kanta;
	}
	
/*---------------------------------------------------------------------------------------------------
 * TIETOKANTA YHTEYDEN SULKEMINEN
 * ---------------------------------------------------------------------------------------------------------
 */

	public static void sulje() {
		try {
			kanta.close();
		} catch (SQLException e) {
			System.out.println("Virhe tietokantakerroksessa: " + e);
		}
	}
/*-------------------------------------------------------------------------------------------------
 * ASIAKKAAN LIS��MINEN ASIAKAS TAULUUN
 * -----------------------------------------------------------------------------------------------------
 */
	public static void lisaaAsiakas(Asiakas a) {
		try {

			Statement lause = kanta.createStatement();
			String kysely = "INSERT INTO asiakas (nimi,syntymavuosi,sotu ) VALUES ('" + a.annaNimi() + "','"
					+ a.annaIka() + "','" + a.annaSotu() + "');";
			lause.executeUpdate(kysely);
			lause.close();
			System.out.println("Lis�ys onnistui!");

		} catch (Exception e) {
			System.out.println("e" + e);
		}

	}
	/*-----------------------------------------------------------------------------------------------------
	 * VARAUKSEN TEKEMINEN AIEMMIN SY�TETTYYN N�YT�KSEEN
	 * ----------------------------------------------------------------------------------------------------
	 */

	public static void teeVaraus(String sotu, String koodi, String paikkaNum) {
		try {
			Statement lause = kanta.createStatement();
			String varmistus = "SELECT status FROM " + koodi + " WHERE paikka = '" + paikkaNum + "';";
			String varmistuksenLopputulos = "VAPAA";
			ResultSet tulos = lause.executeQuery(varmistus);
			while (tulos.next()) {
				varmistuksenLopputulos = tulos.getString(1);

			}
			if (varmistuksenLopputulos.equals("VAPAA")) {

				String kysely = "UPDATE " + koodi + " SET STATUS = '" + sotu + "' WHERE paikka = '" + paikkaNum + "';";
				lause.executeUpdate(kysely);
				lause.close();
				System.out.println("Paikka varattu!");
			} else {
				System.out.println("Paikka on jo varattu toiselle henkil�lle");
			}

		} catch (Exception e) {
			System.out.println("e" + e);
		}
	}
/*---------------------------------------------------------------------------------------------------
 * YKSITT�ISEN SALIN VARAUSTILANTEEN TALLENTAMINEN SALI-OLIOON, JOKA TULOSTETAAN TOISESSA METODISSA K�YTT�J�LLE 
 * ---------------------------------------------------------------------------------------------------
 */
	public static ArrayList<Sali> annaSalinVaraustilanne(String koodi) {
		ArrayList<Sali> varausTilanne = new ArrayList<Sali>();
		try {
			Statement lause = kanta.createStatement();
			String kysely = "SELECT * FROM " + koodi + ";";
			ResultSet tulos = lause.executeQuery(kysely);
			while (tulos.next()) {
				varausTilanne.add(new Sali(tulos.getString(1), tulos.getString(2)));

			}
			tulos.close();
			lause.close();
			System.out.println("Varaustilanne annettu!");
		} catch (Exception e) {
			System.out.println("N�yt�st� ei l�ytynyt: " );
		}

		return varausTilanne;
	}
/*---------------------------------------------------------------------------------------------------------
 * TALLENTAA TAULUSTA  N�YT�KSET YKSITT�ISET N�YT�KSET N�YT�S-ARRAYLIST OLIOON JOSTA TIEDOT TULOSTETAAN K�YTT�J�LLE TOSTRING-METODILLA
 * -----------------------------------------------------------------------------------------------------------
 */
	public static ArrayList<Naytos> annaNaytokset(String nimi) {
		ArrayList<Naytos> nimenMukaan = new ArrayList<Naytos>();
		try {
			Statement lause = kanta.createStatement();
			String kysely = "SELECT * FROM naytokset WHERE nimi LIKE '%" + nimi + "%';";
			ResultSet tulos = lause.executeQuery(kysely);
			while (tulos.next()) {
				nimenMukaan.add(new Naytos(tulos.getString(1), tulos.getString(2), tulos.getString(3),
						tulos.getString(4), tulos.getString(5), tulos.getString(6)));
			}

			tulos.close();
			lause.close();
			System.out.println("haku tehty");
		} catch (Exception e) {
			System.out.println("e" + e);
		}
		return nimenMukaan;
	}
	
	public static boolean onkoTunnusta(String sotu){
		try {
			Statement lause = kanta.createStatement();
			String kysely= "SELECT sotu FROM asiakas WHERE sotu='"+sotu+"';";
			ResultSet tulos = lause.executeQuery(kysely);
			if(tulos.next()){
				return true;
			}
		}
		catch(Exception e){
			System.out.println("Tunnusta ei l�ytynyt ");
		}
	return false;}
	}