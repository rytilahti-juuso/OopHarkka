package tietokanta;

public class Asiakas {
    private String nimi;
    private int synt;
    private String sotu;
   
    public Asiakas(String nimi, int ika, String sotu) {
        this.nimi = nimi;
        this.synt = ika;
        this.sotu = sotu;
    }
   
    public String annaNimi() {
        return nimi;
    }
   
    public int annaIka() {
        return synt;
    }
   
    public String annaSotu() {
        return sotu;
    }
}