package petr.telecardreader;

class ComRes {  // trida pres kterou budou cteci a zapisovaci vlakna predavat hodnoty
	
	String command;
	String answer;
	
	public ComRes() {}
	
	public ComRes(String parCommand, String parAnswer) {
		this.command = parCommand;
		this.answer = parAnswer;
	}
	
	public void setCommand(String command) {
		this.command = command;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public String getCommand() {
		return command;
	}
	
	public String getAnswer() {
		return answer;
	}
	
}
