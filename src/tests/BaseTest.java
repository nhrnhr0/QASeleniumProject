package tests;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import sdk.Log;
/**
 * every test need to inherent from BaseTest  
 *
 */
public class BaseTest {
	//ChromeDriver driver;
	//SoftAssert sa = new SoftAssert();

	/**
	 * this mothod runs before every suite
	 * makes sure the driver property is set and initialize the driver
	 */
	@BeforeSuite
    public void baseTestBeforeSuite() {
		/*System.setProperty("webdriver.chrome.driver","chromedriver77.exe");
		driver = new ChromeDriver();
		Log.println("driver initialize");*/

	}

	@AfterSuite
	public void baseTestAfterSuite() {
		Log.println("closeing log file");
		Log.close();
	}
	
	
}
