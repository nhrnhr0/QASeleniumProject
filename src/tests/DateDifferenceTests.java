package tests;


import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import pages.BasePage;
import pages.DateDiffPage;
import pages.RNGPage;
import sdk.MyDate;
import sdk.DateType;
import sdk.Log;

/**
 * all the tests made for {@link https://www.calculator.net/date-calculator.html}
 */
public class DateDifferenceTests extends BaseTest{
	public static final String RESULT_INVALID = "result invalid"; // constant representing an invalid result
	private int testCount; // for debug and log
	DateDiffPage page; // the tested page
	
	// init the page 
    @BeforeSuite
    public void beforeSuite() {
  	  page = new DateDiffPage();
    }
	
	// before every test case, reopen the page 
  @BeforeMethod
  public void beforeMethod(java.lang.reflect.Method m, Object[] params) {
	  
	  Log.print("run: " + m.getName() + 
			  "(" + (String)params[0] + ", " + (String)params[1] + ", " + (String)params[2].toString() + ")" + 
			  "-(" + (String)params[3] + ", " + (String)params[4] + ", " + (String)params[5].toString() + ")" + 
			  " add end day: " + ((boolean)params[6] == true?"true":"false"));
	  
	  page.openPage();
  }
  
  @AfterMethod
  public void afterMethod(ITestResult result) {
	  if(result.isSuccess()) {
		  Log.println(" [PASSED]");
	  }else {
		  Log.println(" [FAILED]");
	  }
  }
  
  
  
  
  
//run sanity tests:
 @Test(dataProvider = "DateDifference_HardCoded_Sanity_DataProvider", enabled = true, alwaysRun = true)
	public void DateDifference_HardCoded_Sanity_Test(String sDay,String  sMonth,String  sYear,String  eDay,String  eMonth,String  eYear,boolean addendday, String expectedResult) {
	  runTest(sDay, sMonth, sYear, eDay, eMonth, eYear, addendday, expectedResult);
	}
  
 
  // run edge cases:
  @Test(dataProvider = "DateDifference_HardCoded_EdgeCases_DataProvider", enabled = true, alwaysRun = true)
  public void DateDifference_HardCoded_EdgeCases_Test(String sDay,String  sMonth,String  sYear,String  eDay,String  eMonth,String  eYear,boolean addendday, String expectedResult) {
	  runTest(sDay, sMonth, sYear, eDay, eMonth, eYear, addendday, expectedResult);
  }
  

  private void runTest(String sDay,String  sMonth,String  sYear,String  eDay,String  eMonth,String  eYear,boolean addendday, String expectedResult) {
	  try {
		  MyDate startDate = new MyDate(sDay, sMonth, sYear);
			MyDate endDate = new MyDate(eDay, eMonth, eYear);
			
			// true if both dates are valid, false otherwise
			boolean expectValidResult = page.setDate(DateType.START, startDate) && page.setDate(DateType.END, endDate);
			page.setAddendday(addendday);
			
			// if both dates are valid, click the submit button and test the result:
			if(expectValidResult) {
				page.getSubmitButton().click(); 
				
				// make sure we test for the right thing, test that the result headline is equal to the given dates
				Assert.assertEquals(page.getResultHeadline(), String.format("Difference between %s and %s:", startDate.toString(), endDate.toString()));
				
				// make sure the date difference is as expected by the expectedResult (ignore \n and \r)
				Assert.assertEquals(page.getResult().replace("\n","").replace("\r", ""), expectedResult.replace("\n","").replace("\r", ""));
			}else {
				// if one of the dates is invalid, then we expect for an invalid result
				Assert.assertEquals(RESULT_INVALID, expectedResult);
			}
	  }catch(Exception ex) {
		  Log.print(" " + ex.getMessage());
		  FaildAssert("Exception throwen: " + ex.getMessage());
	  }catch(Throwable th) {
		  Log.print(th.getMessage());
		  FaildAssert("Throwable throwen: " + th.getMessage());
	  }
  }
  
  @AfterSuite
  public void AfterSuite() {
	  page.close();
  }
  
  private void FaildAssert(String msg) {
	  Assert.fail(msg);
  }
	
	@DataProvider
	public Object[][] DateDifference_HardCoded_Sanity_DataProvider() {
		Object[][] ret = new Object[][] {
			{"21","Nov","1997", "30","Oct","2019", false, "21 years 11 months 9 days\n" + 
					"or 263 months 9 days\n" + 
					"or 1144 weeks 5 days\n" + 
					"or 8013 calendar days"},
			{"21","Nov","1997", "30","Oct","2019",true, "21 years 11 months 10 days\n" + 
					"or 263 months 10 days\n" + 
					"or 1144 weeks 6 days\n" + 
					"or 8014 calendar days"},
			{"32", "Nov", "1997", "30", "Oct", "2019", false, RESULT_INVALID }
		};
		return ret;
	}
	
	@DataProvider
	public Object[][] DateDifference_HardCoded_EdgeCases_DataProvider() {
		Object[][] ret = new Object[][] {
			{"1","1","2000","2","1","2000", false, "1 calendar days\n\r"},
			{"1","1","2000","2","1","2000", true, "2 calendar days\n"},
			{"1","1","2000","7","1","2000", false, "6 calendar days\n"},
			{"1","1","2000","7","1","2000", true, "1 weeks 0 days\nor 7 calendar days"},
			{"1","1","2000","31","1","2000", false, "4 weeks 2 days\nor 30 calendar days"},
			{"1","1","2000","31","1","2000", true, "1 months 0 days\n" + 
													"or 4 weeks 3 days\n" + 
													"or 31 calendar days"},
			{"1","1","2000","1","1","2001", false, "1 years 0 months 0 days\n" + 
													"or 12 months 0 days\n" + 
													"or 52 weeks 2 days\n" + 
													"or 366 calendar days"},
			{"1","1","2000","1","1","2001", true, "1 years 0 months 1 days\n" + 
													"or 12 months 1 days\n" + 
													"or 52 weeks 3 days\n" + 
													"or 367 calendar days"}
		};
		return ret;
	}
}
