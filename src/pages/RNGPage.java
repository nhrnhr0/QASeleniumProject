package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
/**
 * RNGPage represents the random-number-generator page from {@link https://www.calculator.net/random-number-generator.html}
 * with a helpful API
 */
public class RNGPage extends BasePage {

	public RNGPage() {
		super("https://www.calculator.net/random-number-generator.html");
	}
	
	
	// clicks the submit button
	public void submit() {
		driver.findElement(By.xpath("//*[@id=\"content\"]/form[2]/table/tbody/tr/td/table/tbody/tr[5]/td/input[3]")).click();;
	}
	
	// clicks the clear button
	public void clearAll() {
		driver.findElement(By.xpath("//*[@id=\"content\"]/form[2]/table/tbody/tr/td/table/tbody/tr[5]/td/input[4]")).click();
	}
	
	public WebElement getLowerLimitFeild() {
		return driver.findElement(By.name("clower"));
	}
	
	public WebElement getUpperLimitFeild() {
		return driver.findElement(By.name("cupper"));
	}
	
	public WebElement getGenerateNumFeild() {
		return driver.findElement(By.name("cnums"));
	}
	
	public void selectInteger() {
		driver.findElement(By.id("cnumt1")).click();
	}
	
	public void selectDecimal() {
		driver.findElement(By.id("cnumt2")).click();
	}
	
	public WebElement getPrecisionFeild() {
		return driver.findElement(By.name("cprec"));
	}
	
	public void setAllowDuplication(boolean allow) {
		if(allow) {
			driver.findElement(By.id("cdup1")).click();
		}else {
			driver.findElement(By.id("cdup2")).click();
		}
	}
	
	public void setSortAscend() {
		driver.findElement(By.id("csort1")).click();
	}
	
	public void setSortDescend() {
		driver.findElement(By.id("csort2")).click();
	}
	
	public void setSortNone() {
		driver.findElement(By.id("csort3")).click();
	}
	
	
	// gets a list full with the random numbers based on the given parameters
	public List<WebElement> getResults() {
		return driver.findElements(By.className("verybigtext"));
	}

}
