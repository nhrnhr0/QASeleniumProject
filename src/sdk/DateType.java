package sdk;

public enum DateType {
	START("today"),
	END("ageat");
	private String dateType;
	private DateType(String dateType) {
		this.dateType = dateType;
	}
	public String getDateType() { return dateType;}
	public String toString() {return dateType; }
}
