package tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.DateDiffPage;
import pages.RNGPage;
import sdk.Date;

public class DateDifferenceTests extends BaseTest{
	private int testCount;
	DateDiffPage page;
	
    @BeforeSuite
    public void beforeSuite() {
  	  page = new DateDiffPage(driver);
    }
	
	
  @BeforeMethod
  public void beforeMethod() {
	  page.openPage();
  }
  
  
  @Test
	public void DateDifferenceTest(String sDay,String  sMonth,String  sYear,String  eDay,String  eMonth,String  eYear) {
		Date startDate = new Date(sDay, sMonth, sYear);
		Date endDate = new Date(eDay, eMonth, eYear);
		
		boolean expectValidResult;
		
		expectValidResult = page.fillStartDate(startDate) && page.fillEndDate(endDate);
		
		if(expectValidResult) {
			page.getSubmitButton().click();
			
			
		}
		
	}
	
	@DataProvider
	public Object[][] DateDifferenceDataProvider() {
		Object[][] ret = new Object[][] {
			{"21","Nov","1997", "30","Oct","2019"}
		};
		return ret;
	}
}
