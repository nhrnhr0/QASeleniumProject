package pages;

import org.openqa.selenium.chrome.ChromeDriver;

import sdk.Log;
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
	
	BasePage(String url) {
		System.setProperty("webdriver.chrome.driver","chromedriver77.exe");
		this.driver = new ChromeDriver();
		Log.println("driver initialize");
		this.url = url;
	}
	
	// navigate the driver to open the url
	public void openPage() {
		try {
			this.driver.navigate().to(url);
		}catch(Exception ex) {
			Log.print("openPage error: " + ex.getMessage());
		}
	}
	
	
	public void close() {
		this.driver.close();
		Log.println("driver closed");
	}


}
