package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import sdk.MyDate;
import sdk.DateType;

/**
 * The class DateDiffPage representing the "date difference page" from {@link https://www.calculator.net/date-calculator.html}
 * in a nice helpful API
 */
public class DateDiffPage extends BasePage {

	public DateDiffPage() {
		super("https://www.calculator.net/date-calculator.html");
	}
	
	/**
	 * sets a given date in the requested place 
	 * @param type either {@code DateType.START} or {@code DateType.END}
	 * @param date the date to enter
	 * @return true if the date is valid and inserted, false otherwise.
	 */
	public boolean setDate(DateType type, MyDate date) {
		try {
			markYear(type); // double click on the year field
			getYear(type).sendKeys(date.getYear()); // write the year
			getMonth(type).selectByVisibleText(date.getMonth()); // choose the month
			getDay(type).selectByVisibleText(date.getDay()); // choose the day
			return true; // all works
		}
		/* NoSuchElementException: when given day 31 in an 28 days month for example and it can't be choose by selectByVisibleText */
		/* IllegalArgumentException: when  given an invalid date */
		catch (org.openqa.selenium.NoSuchElementException | java.lang.IllegalArgumentException e) {
			return false;
		}
	}
	
	// double click on one of the year fields
	private void markYear(DateType type) {
		Actions action = new Actions(driver);
		action.doubleClick(getYear(type)).perform();
	}
	
	/**
	 * gets one of the day field  as a Select Object
	 * @param can be DateType.START / DateType.END
	 * @return either the start day or the end day select object
	 */
	private Select getDay(DateType type) { 
		return new Select(driver.findElement(By.id(type + "_Day_ID")));
	}
	
	/**
	 * gets one of the months field as a Select Object
	 * @param can be DateType.START / DateType.END 
	 * @return either the start month or the end month Select object 
	 */
	private Select getMonth(DateType type) {
		return new Select(driver.findElement(By.id(type + "_Month_ID")));
	}
	
	/**
	 * gets once of the years field as a WebElement
	 * @param type can be  DateType.START / DateType.END
	 * @return either the start year or the end year as a WebElement object 
	 */
	private WebElement getYear(DateType type) {
		return driver.findElement(By.id(type + "_Year_ID"));
	}
	
	/**
	 * gets the submit button of the page
	 * @return submit button
	 */
	public WebElement getSubmitButton() {
		return driver.findElement(By.xpath("//*[@id=\"content\"]/form/div/table[2]/tbody/tr/td[1]/input"));
	}
	
	/**
	 * gets the results headline
	 * for (5.1.2019) - (3.11.2019) getResultHeadline() will return:
	 * "Difference between Jan 5, 2019 and Nov 3, 2019:"
	 * @return the result's headline
	 */
	public String getResultHeadline() {
		return driver.findElement(By.xpath("//*[@id=\"content\"]/p[2]/b")).getText();
	}
	
	/**
	 * gets the difference between the dates
	 * @return
	 * example: 
	 * 	9 months 29 days
		or 43 weeks 1 days
		or 302 calendar days
	 */
	public String getResult() {
		return driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]")).getText();
	}
	
	/**
	 * sets the "include end day (add 1 day)" check box on and off 
	 * @param flag true to set the check box on, false to set the check box off
	 */
	public void setAddendday(boolean flag) {
		WebElement ckbox = driver.findElement(By.id("addendday"));
		if(ckbox.isSelected() != flag) {
			ckbox.click();
		}
	}

}
