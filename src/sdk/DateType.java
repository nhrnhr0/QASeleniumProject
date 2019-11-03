package sdk;


/**
 * 
 * used in DateDiffPage to specify if the start date or the end date need to be set 
 * example: boolean isValid = setDate(DateType.START, new MyDate("21","11","1997");
 */
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
