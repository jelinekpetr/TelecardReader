package petr.telecardreader;

public class ParamValue {
	
	String param;
	String value;
	String operator;
	String or = "";
	
	public ParamValue(String param, String value, String operator) {
		
		this.param = param;
		this.value = value;
		this.operator = operator;
		
	}

	public ParamValue(String param, String value, String operator, String or) {

		this.param = param;
		this.value = value;
		this.operator = operator;
		this.or = or;

	}
	
	public boolean isOr() {
		
		if (or.isEmpty()) {
			return false;
		} else {
			return true;
		}
		
	}
	
	public String getParam() {
		
		return param;
		
	}

	public String getValue() {
		
		return value;
		
	}
	
	public String getWhereClause() {
		
		if (operator == "=") {
			return param+" = '"+value+"'";
		}
		else if (operator == "like") {
			return param+" like '%"+value+"%'";
		} 
		else {
			return "";
		}
	}

}
