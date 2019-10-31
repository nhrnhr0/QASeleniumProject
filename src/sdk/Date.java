package sdk;

public class Date {
	private static final String MONTHS []= {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	String day, month, year;

	public Date(String day, String month, String year) {
		this.day = day;
		try {
			this.month = MONTHS[Integer.parseInt(month)];
		}catch(NumberFormatException e) {
			this.month = month;
		}
		this.year = year;
	}
	
	public String getDay() {return day;}
	public String getMonth() {return month;}
	public String getYear() {return year;}
	
	
	public String toString() {
		return "" + month + " " + day + ", " + year;
	}

}
