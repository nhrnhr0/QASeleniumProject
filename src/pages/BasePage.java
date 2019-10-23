package pages;

import org.openqa.selenium.chrome.ChromeDriver;

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
