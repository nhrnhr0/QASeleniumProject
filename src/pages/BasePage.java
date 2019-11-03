package pages;

import org.openqa.selenium.chrome.ChromeDriver;
/**
 * 
 * {@code #BasePage(ChromeDriver, String)} represents a basic web page API to inherit from.
 * each page has: 
 * {@code #driver}  to control the page 
 * {@code #url} for method {@code #openPage()} 
 */
public class BasePage {
	ChromeDriver driver;
	String url;
	
	BasePage(ChromeDriver driver, String url) {
		this.driver =driver;
		this.url = url;
	}
	
	public void openPage() {
		this.driver.navigate().to(url);
	}

}
