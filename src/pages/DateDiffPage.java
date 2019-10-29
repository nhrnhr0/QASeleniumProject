package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import sdk.Date;

public class DateDiffPage extends BasePage {

	public DateDiffPage(ChromeDriver driver) {
		super(driver, "https://www.calculator.net/date-calculator.html");
	}
	
	public boolean fillStartDate(Date date) {
		clearYear(getStartYear());
		getStartYear().sendKeys(date.getYear());
		getStartMonth().selectByVisibleText(date.getMonth());
		getStartDay().selectByVisibleText(date.getDay());
		
		return validateStartDateInsertion(date);
	}
	
	public boolean fillEndDate(Date date) {
		clearYear(getEndYear());
		getEndYear().sendKeys(date.getYear());
		getEndMonth().selectByVisibleText(date.getMonth());
		getEndDay().selectByVisibleText(date.getDay());
		
		return validateEndDateInsertion(date);
	}
	
	private boolean validateStartDateInsertion(Date date) {
		return  date.getDay().contentEquals(getStartDay().getAllSelectedOptions().get(0).getText()) &&
				date.getMonth().contentEquals(getStartMonth().getAllSelectedOptions().get(0).getText()) &&
				date.getYear().contentEquals(getStartYear().getText());
	}
	
	private boolean validateEndDateInsertion(Date date) {
		return  date.getDay().contentEquals(getEndDay().getAllSelectedOptions().get(0).getText()) &&
				date.getMonth().contentEquals(getEndMonth().getAllSelectedOptions().get(0).getText()) &&
				date.getYear().contentEquals(getEndYear().getText());
	}
	
	private void clearYear(WebElement we) {
		Actions action = new Actions(driver);
		action.doubleClick(we);
		we.sendKeys("");
	}
	
	
	private Select getStartDay() {
		return new Select(driver.findElement(By.id("today_Day_ID")));
	}
	
	private Select getStartMonth() {
		return new Select(driver.findElement(By.id("today_Month_ID")));
	}
	
	private WebElement getStartYear() {
		return driver.findElement(By.id("today_Year_ID"));
	}
	
	private  Select getEndDay() {
		return new Select(driver.findElement(By.id("ageat_Day_ID")));
	}
	
	private Select getEndMonth() {
		return new Select(driver.findElement(By.id("ageat_Month_ID")));
	}
	
	private WebElement getEndYear() {
		return driver.findElement(By.id("ageat_Year_ID"));
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

}
