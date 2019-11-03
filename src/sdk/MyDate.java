package sdk;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MyDate {
	private static final String MONTHS []= {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	private static final Map<String, Integer> monthsToIndex = new HashMap<String, Integer>() {{
		put("Jan", 0);
		put("Feb", 1);
		put("Mar", 2);
		put("Apr", 3);
		put("May", 4);
		put("Jun",5);
		put("Jul",6);
		put("Aug",7);
		put("Sep", 8);
		put("Oct",9);
		put("Nov",10);
		put("Dec",11);}};
		
	//String day, month, year;
	Calendar c;
	
	public void addDay() {
		c.add(Calendar.DATE, 1);
		
	}
	
	private Calendar getCal() {
		return c;
	}
	
	private void setDate(String day, String month, String year) {
		String _day, _month, _year;
		_day = day;
		try {
			_month = MONTHS[Integer.parseInt(month) - 1];
		}catch(NumberFormatException e) {
			_month = month;
		}
		_year = year;
		c = Calendar.getInstance();
		c.setLenient(false);
		c.set(
				Integer.parseInt(_year), 
				monthsToIndex.get(_month), 
				Integer.parseInt(_day));
	}

	public MyDate(String day, String month, String year) {
		setDate(day, month, year);
	}
	private MyDate(int day, int month, int year) {
		this(String.valueOf(day),String.valueOf(month),String.valueOf(year));
	}
	
	public boolean equals(MyDate other) {
		return  this.getCal().get(Calendar.DATE) == other.getCal().get(Calendar.DATE) &&
				this.getCal().get(Calendar.MONTH) == other.getCal().get(Calendar.MONTH) &&
				this.getCal().get(Calendar.YEAR) == other.getCal().get(Calendar.YEAR) ;
	}
	
	public MyDate(MyDate date) {
		this(date.getCal().get(Calendar.DATE), date.getCal().get(Calendar.MONTH) + 1, date.getCal().get(Calendar.YEAR));
	}

	public java.util.Date getJDate() {
		try {
		return c.getTime();
		}catch(java.lang.IllegalArgumentException e) {
			return null;
		}
	}
	
	
	public String getDay() {return String.valueOf(c.get(Calendar.DATE));}
	public String getMonth() {return MONTHS[c.get(Calendar.MONTH)];}
	public String getYear() {return String.valueOf(c.get(Calendar.YEAR));}
	
	
	public String toString() {
		return "" + getMonth() + " " + getDay()+ ", " + getYear();
	}

}
