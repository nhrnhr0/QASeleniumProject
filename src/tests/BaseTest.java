package tests;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import sdk.Log;

public class BaseTest {
	ChromeDriver driver;
	SoftAssert sa = new SoftAssert();

	
	@BeforeSuite
    public void bestTestBeforeSuite() {
		System.setProperty("webdriver.chrome.driver","chromedriver.exe");
		driver = new ChromeDriver();
		Log.FlushInfo("driver initialize");

	}
	
	@AfterSuite
	  public void afterSuite() {
		  driver.close();
		  Log.FlushInfo("driver closed");
	  }

	
	
}
