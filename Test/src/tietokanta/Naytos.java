package tietokanta;
public class Naytos {
    final String nimi;
    final String kesto;
    final String pvm;
    final String klo;
    final String sali;
    final String koodi;
   
    public Naytos(String pvm, String nimi, String kesto, String klo, String sali, String koodi) {
        this.nimi = nimi;
        this.kesto = kesto;
        this.pvm = pvm;
        this.klo = klo;
        this.sali = sali;
        this.koodi = koodi;
    }
   
    public String annaNimi() {
        return nimi;
    }
   
    public String annaKesto() {
        return kesto;
    }
   
    public String annaPvm() {
        return pvm;
    }
   
    public String annaKlo() {
        return klo;
    }
   
    public String annaSali() {
        return sali;
    }
   
    public String annaKoodi() {
        return koodi;
    }
   
    @Override
    public String toString() {
        return "Nimi: " + nimi
                + ", Kesto: " + kesto + ", Näytöksen päivämäärä: " + pvm
                + ", Näytöksen kellonaika: " + klo + ", Näytöksen salinumero: " + sali+ ", Näytöksen koodi: "+ koodi;
    }
}