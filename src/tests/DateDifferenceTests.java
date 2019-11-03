package tests;

import java.lang.reflect.Executable;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.DateDiffPage;
import pages.RNGPage;
import sdk.MyDate;
import sdk.DateType;
import sdk.Log;

public class DateDifferenceTests extends BaseTest{
	public static final String RESULT_INVALID = "result invalid";
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
  
  
  @Test(dataProvider = "DateDifference_HardCoded_Sanity_DataProvider", enabled = true)
	public void DateDifference_HardCoded_Sanity_Test(String sDay,String  sMonth,String  sYear,String  eDay,String  eMonth,String  eYear,boolean addendday, String expectedResult) {
	  
	  runTest(sDay, sMonth, sYear, eDay, eMonth, eYear, addendday, expectedResult);
	}
  
  @Test(dataProvider = "DateDifference_HardCoded_EdgeCases_DataProvider", enabled = true)
  public void DateDifference_HardCoded_EdgeCases_Test(String sDay,String  sMonth,String  sYear,String  eDay,String  eMonth,String  eYear,boolean addendday, String expectedResult) {
	  runTest(sDay, sMonth, sYear, eDay, eMonth, eYear, addendday, expectedResult);
  }
  
  private void runTest(String sDay,String  sMonth,String  sYear,String  eDay,String  eMonth,String  eYear,boolean addendday, String expectedResult) {
	  MyDate startDate = new MyDate(sDay, sMonth, sYear);
		MyDate endDate = new MyDate(eDay, eMonth, eYear);
		
		boolean expectValidResult;
		
		expectValidResult = page.setDate(DateType.START, startDate) && page.setDate(DateType.END, endDate);
		page.setAddendday(addendday);
		
		if(expectValidResult) {
			page.getSubmitButton().click();
			Assert.assertEquals(page.getResultHeadline(), String.format("Difference between %s and %s:", startDate.toString(), endDate.toString()));
			
			Assert.assertEquals(page.getResult().replace("\n","").replace("\r", ""), expectedResult.replace("\n","").replace("\r", ""));
		}else {
			Assert.assertEquals(RESULT_INVALID, expectedResult);
		}
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
