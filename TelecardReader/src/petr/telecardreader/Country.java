package petr.telecardreader;

public class Country {

	String numCode;
	String shortEN;
	String longEN;
	String shortCZ;
	String longCZ;

	public Country(String numCode, String shortEN, String longEN, String shortCZ, String longCZ) {

		this.numCode = numCode;
		this.shortEN = shortEN;
		this.longEN = longEN;
		this.shortCZ = shortCZ;
		this.longCZ = longCZ;

	}

	public String toString() {

		return shortCZ;

	}
	
	public String getCode() {
		
		return numCode;
		
	}

}
