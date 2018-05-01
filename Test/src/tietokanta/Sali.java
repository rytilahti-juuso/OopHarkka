package tietokanta;

public class Sali {
private String status;
private String paikkaNum;

public Sali(String paikkaNum, String status){
	this.status=status;
	this.paikkaNum=paikkaNum;
}
public String annaStatus(){
	return status;
}
public void asetaStatus(String status){
	this.status=status;
}
public String toString() {
    return "Status: "+ status+ " PaikkaNumero: "+ paikkaNum;
    		}

}
