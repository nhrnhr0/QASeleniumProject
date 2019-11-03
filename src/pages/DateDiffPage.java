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

	public DateDiffPage(ChromeDriver driver) {
		super(driver, "https://www.calculator.net/date-calculator.html");
	}
	
	/**
	 * sets a given date in the requested place 
	 * @param type either {@code DateType.START} or {@code DateType.END}
	 * @param date the date to enter
	 * @return {@code true} if the date is valid and inserted, {@code false} otherwise.
	 */
	
	public boolean setDate(DateType type, MyDate date) {
		try {
			markYear(type);
			getYear(type).sendKeys(date.getYear());
			getMonth(type).selectByVisibleText(date.getMonth());
			getDay(type).selectByVisibleText(date.getDay());
			return true;
		}
		catch (org.openqa.selenium.NoSuchElementException | java.lang.IllegalArgumentException e) {
			return false;
		}
	}
	
	private void markYear(DateType type) {
		Actions action = new Actions(driver);
		action.doubleClick(getYear(type)).perform();
	}
	
	private Select getDay(DateType type) { 
		return new Select(driver.findElement(By.id(type + "_Day_ID")));
	}
	
	private Select getMonth(DateType type) {
		return new Select(driver.findElement(By.id(type + "_Month_ID")));
	}
	
	private WebElement getYear(DateType type) {
		return driver.findElement(By.id(type + "_Year_ID"));
	}
	
	public WebElement getSubmitButton() {
		return driver.findElement(By.xpath("//*[@id=\"content\"]/form/div/table[2]/tbody/tr/td[1]/input"));
	}
	
	public String getResultHeadline() {
		return driver.findElement(By.xpath("//*[@id=\"content\"]/p[2]/b")).getText();
	}
	
	public String getResult() {
		return driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]")).getText();
	}
	
	public void setAddendday(boolean flag) {
		WebElement ckbox = driver.findElement(By.id("addendday"));
		if(ckbox.isSelected() != flag) {
			ckbox.click();
		}
			
	}

}
