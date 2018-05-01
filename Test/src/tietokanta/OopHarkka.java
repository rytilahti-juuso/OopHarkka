package tietokanta;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class OopHarkka {
	private static String sotu;
	private static Connection kanta = null;

	public static void main(String[] args) {

		
		KantaLeffoille.alusta();
		//	TIETOKANNAN TIETOJEN ALUSTUS, POISTA KOMMENTIT AJA OHJELMA, POISTA RIVIT 16-27 ENNEN TOISTA AJOKERTAA!
		 /*
		 KantaLeffoille.lisaysNaytokset("Titanic","20.04.2017","2h30min","16:00","sali1","sali1240420171600");
		 KantaLeffoille.taulunRakennus("sali1","sali1240420171600");
		 KantaLeffoille.lisaysNaytokset("Titanic","20.04.2017","2h30min","16:00","sali2","sali2240420171600");
		 KantaLeffoille.taulunRakennus("sali2","sali2240420171600");
		 KantaLeffoille.lisaysNaytokset("Titanic","01.04.2017","2h30min","16:30","sali1","sali1010420171630");
		 KantaLeffoille.taulunRakennus("sali1","sali1010420171630");
		 KantaLeffoille.lisaysNaytokset("Titanic","29.04.2017","2h30min","16:00","sali1","sali1290420171600");
		 KantaLeffoille.taulunRakennus("sali1","sali1290420171600");
		 KantaLeffoille.lisaysNaytokset("Aladdin","01.08.2017","2h30min","12:30","sali1","sali1010820171630");
		 KantaLeffoille.taulunRakennus("sali1","sali1010820171630");
		 */
		 				
		
		onkoTunnusta();
		KantaLeffoille.sulje();
	}

	

	public static void onkoTunnusta() {
		Scanner s = new Scanner(System.in);
		System.out.print("Onko teillä jo asiakastunnukset? (K/E): ");
		String vastaus = s.nextLine();
		if (vastaus.equals("K")) {
			kirjautuminen();
		} else if (vastaus.equals("E")) {
			uusiAsiakas();


		} else {
			System.out.println("Tarkistathan vastauksesi. Vastaa joko 'K'(kyllä) tai 'E'(ei)");
			onkoTunnusta();
		}
	}

	public static void kirjautuminen() {
		Scanner s = new Scanner(System.in);
		System.out.print("Syötä sotusi: ");
		sotu = s.nextLine();
		if((KantaLeffoille.onkoTunnusta(sotu))==false){
			System.out.println("tunnusta ei löytynyt");
		}
		else{
			System.out.println("HAETAAN TIEDOILLA ASIAKASTA TIETOKANNASTA");
		System.out.println("Kirjautuminen onnistui!");
		haeElokuvaa();
		
		}
		
	}

	public static void uusiAsiakas() {
		String nimi;
		int synt;
		String sotu;

		Scanner s = new Scanner(System.in);
		System.out.print("Syötä nimesi: ");
		nimi = s.nextLine();
		System.out.print("Syötä syntymävuotesi: ");
		synt = s.nextInt();
		System.out.print("Syötä henkilötunnuksesi: ");
		sotu = s.next();

		s.close();
		System.out.println("LUODAAN UUSI ASIAKAS TIETOKANTAAN");
		Asiakas a1 = new Asiakas(nimi, synt, sotu);
		KantaLeffoille.lisaaAsiakas(a1);

	}

	public static void haeElokuvaa() {
		Scanner s = new Scanner(System.in);
		System.out.print("Syötä halumasi elokuvan nimi: ");
		String elokuva = s.nextLine();
		System.out.println("HAETAAN ELOKUVAA NIMELLÄ " + elokuva);
		ArrayList<Naytos> lista = new ArrayList<Naytos>();
		lista = (KantaLeffoille.annaNaytokset(elokuva));
		for (Naytos a : lista) {
			System.out.println(a.toString());
		}
		System.out.println("Syötä haluamasi näytöksen koodi");
		String naytoksenKoodi = s.nextLine();
		ArrayList<Sali> listaSaleista = new ArrayList<Sali>();
		listaSaleista = KantaLeffoille.annaSalinVaraustilanne(naytoksenKoodi);
		for (Sali a : listaSaleista) {
			if (!(a.annaStatus().equals("VAPAA")) && !(a.annaStatus().equals(sotu))) {
				a.asetaStatus("VARATTU");
			}
			System.out.println(a.toString());
		}
		System.out.println("Syötä haluamasi istumapaikka");
		String istumapaikka = s.nextLine();
		KantaLeffoille.teeVaraus(sotu, naytoksenKoodi, istumapaikka);
		s.close();
	}

	public static Connection alusta() {
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			kanta = DriverManager.getConnection("jdbc:postgresql://localhost:5432/company", "postgres", "salasana");
		} catch (Exception e) {
			System.out.println("Virhe tietokantakerroksessa: " + e);
		}
		return kanta;
	}

	public static void sulje() {
		try {
			kanta.close();
		} catch (SQLException e) {
			System.out.println("Virhe tietokantakerroksessa: " + e);
		}
	}

	
}