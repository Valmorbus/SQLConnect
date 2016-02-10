package ovningsuppgifter2SQL;

public class Elev {
	private String personnummer;
	private String fornamn;
	private String efternamn;
	private String epost;
	private String telefon;
	private String klasskod;
	public Elev(String personnummer, String fornamn, String efternamn, String epost, String telefon, String klasskod) {
		super();
		this.personnummer = personnummer;
		this.fornamn = fornamn;
		this.efternamn = efternamn;
		this.epost = epost;
		this.telefon = telefon;
		this.klasskod = klasskod;
	}
	public String getPersonnummer() {
		return personnummer;
	}
	public void setPersonnummer(String personnummer) {
		this.personnummer = personnummer;
	}
	public String getFornamn() {
		return fornamn;
	}
	public void setFornamn(String fornamn) {
		this.fornamn = fornamn;
	}
	public String getEfternamn() {
		return efternamn;
	}
	public void setEfternamn(String efternamn) {
		this.efternamn = efternamn;
	}
	public String getEpost() {
		return epost;
	}
	public void setEpost(String epost) {
		this.epost = epost;
	}
	public String getTelefon() {
		return telefon;
	}
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	public String getKlasskod() {
		return klasskod;
	}
	public void setKlasskod(String klasskod) {
		this.klasskod = klasskod;
	}
	@Override
	public String toString() {
		return "Elev " + fornamn + " " + efternamn;
	}
	
	public String printStudent() {
		return "Personnummer: " + personnummer + "\nfornamn: " + fornamn + "\nefternamn: " + efternamn + "\nepost: "
				+ epost + "\ntelefon: " + telefon + "\nklasskod: " + klasskod;
	}
	
	
	

}
