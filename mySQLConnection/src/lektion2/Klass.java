package lektion2;

public class Klass {

	private String kod;
	private String namn;
	private int arskurs;

	public Klass(String kod, String namn, int arskurs) {
		super();
		this.kod = kod;
		this.namn = namn;
		this.arskurs = arskurs;
	}

	public String getKod() {
		return kod;
	}

	public void setKod(String kod) {
		this.kod = kod;
	}

	public String getNamn() {
		return namn;
	}

	public void setNamn(String namn) {
		this.namn = namn;
	}

	public int getArskurs() {
		return arskurs;
	}

	public void setArskurs(int arskurs) {
		this.arskurs = arskurs;
	}

	@Override
	public String toString() {
		return "Klassnamn " + namn + ", arskurs " + arskurs ;
	}
	

}
