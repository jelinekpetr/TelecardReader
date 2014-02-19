package petr.telecardreader;

public class ErrorRecord {

	String errorText;
	int errorCode;

	public ErrorRecord(String errorText, int errorCode) {

		this.errorText = errorText;
		this.errorCode = errorCode;

	}

	public String getErrorText() {
		return errorText;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
