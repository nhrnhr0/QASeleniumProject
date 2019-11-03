package sdk;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * represents a date in the calendar
 */
public class MyDate {
	// to change from index to String format:
	private static final String MONTHS []= {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"}; 
	// to change from String format to index:
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
		
	Calendar c; // holds the date
	
	// add one day to the object
	public void addDay() {
		c.add(Calendar.DATE, 1);
	}
	
	/**
	 * @return holds the date
	 */
	private Calendar getCal() {
		return c;
	}
	
	/**
	 * given a day, month and year, sets them in Calendar for future use
	 * @param day (1-28/31)
	 * @param month (Jan, Nov...) / (1-12)
	 * @param year 1-9999
	 */
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

	/**
	 * {@see #setDate(String, String, String)}
	 * @param day (1-28/31)
	 * @param month (Jan, Nov...) or (1-12
	 * @param year 
	 */
	public MyDate(String day, String month, String year) {
		setDate(day, month, year);
	}
	
	/**
	 * {@see #MyDate(String, String, String)} 
	 * all the values are passed through String.valueOf({@value})
	 * @param day 
	 * @param month
	 * @param year
	 */
	private MyDate(int day, int month, int year) {
		this(String.valueOf(day),String.valueOf(month),String.valueOf(year));
	}
	
	/**
	 * @return true if the 2 dates represent the same dates, false otherwise
	 */
	public boolean equals(MyDate other) {
		return  this.getCal().get(Calendar.DATE) == other.getCal().get(Calendar.DATE) &&
				this.getCal().get(Calendar.MONTH) == other.getCal().get(Calendar.MONTH) &&
				this.getCal().get(Calendar.YEAR) == other.getCal().get(Calendar.YEAR) ;
	}
	
	/**
	 * copy constructor 
	 * @param date the object to copy
	 */
	public MyDate(MyDate date) {
		//this(date.getCal().get(Calendar.DATE), date.getCal().get(Calendar.MONTH) + 1, date.getCal().get(Calendar.YEAR));
		this(date.getDay(), date.getMonth(), date.getYear());
	}

	/*
	public java.util.Date getJDate() {
		try {
		return c.getTime();
		}catch(java.lang.IllegalArgumentException e) {
			return null;
		}
	}*/
	
	
	public String getDay() {return String.valueOf(c.get(Calendar.DATE));}
	public String getMonth() {return MONTHS[c.get(Calendar.MONTH)];}
	public String getYear() {return String.valueOf(c.get(Calendar.YEAR));}
	
	
	public String toString() {
		return "" + getMonth() + " " + getDay()+ ", " + getYear();
	}

}
