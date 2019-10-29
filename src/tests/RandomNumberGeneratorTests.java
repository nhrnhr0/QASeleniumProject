package tests;

import org.testng.annotations.Test;
import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

import pages.RNGPage;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;
import sdk.Log;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

import java.util.*; 
import java.nio.charset.StandardCharsets; 
import java.nio.file.*; 
import java.io.*; 

public class RandomNumberGeneratorTests extends BaseTest {
	private int testCount;
	RNGPage page;
	
    @BeforeSuite
    public void beforeSuite() {
  	  page = new RNGPage(driver);
    }
	
	
  @BeforeMethod
  public void beforeMethod() {
	  page.openPage();
  }
  
  /// node: there is no precision and int/decimal selection tests 
  @Test(dataProvider = "RandomNumberGeneratorDataProvider")
  public void RandomNumberGeneratorTest(double pLowerLimit, double pUpperLimit, int pGenerateNum, boolean allowDup,
		  						   String sortType, boolean useIntType, String precision) {
	  ++testCount;
	  //LogTest(pLowerLimit, pUpperLimit,pGenerateNum, allowDup, sortType, useIntType, precision, testCount);
	  Log.println(formatTestPars(
			  pLowerLimit, pUpperLimit, pGenerateNum, allowDup, sortType, useIntType, precision, testCount)
			  + "[RUNNING]");
	  fillFeilds(pLowerLimit, pUpperLimit, pGenerateNum, allowDup, sortType, useIntType, precision);
	  page.submit();
	  List<WebElement> results = page.getResults();
	  
	  // verify the number of results
	  sa.assertEquals(results.size(), pGenerateNum, "incorrect number of results: "); 
	  
	  		
	  boolean isAscend = true;
	  boolean isDescend = true;
	  for(int i = 0; i < results.size();i++) {
		  double currResult = Double.parseDouble(results.get(i).getText());
		  
		// if out of bounds 
		  if(pLowerLimit > currResult || pUpperLimit < currResult) {
			  FaildAssert("result: " + String.valueOf(currResult) + " is out of bounds: " + pLowerLimit + " - " + pUpperLimit);
		  }
		  
		  // test precision
		  if(!useIntType) {
			  String afterPoint = results.get(i).getText().substring(results.get(i).getText().indexOf(".")+1);
			  Assert.assertEquals(afterPoint.length(), Double.parseDouble(precision));
		  }
		  
		  
		  
		  // if(not in the last result)
		  // need to check if the next result is in some kind of order
		  if(i != (results.size()-1)) {
			  double nextResult = Double.parseDouble(results.get(i).getText());
			  if(currResult < nextResult) {
				  //isDescend = false;
				  Assert.assertEquals(sortType.contentEquals("Descend")?true:false, false, "the result is not in desending order");
			  }else if(currResult > nextResult) {
				  //isAscend = false;
				  Assert.assertEquals(sortType.contentEquals("Ascend")?true:false, false, "the result is not in ascending order");
				  
			  }
			  
			  // iterate all the next results to find match results for dup test
			  if(!allowDup) {
				  for(int j = i+1; j < results.size(); j++) {
					  double tempResult = Double.parseDouble(results.get(j).getText());
					  if(tempResult == currResult) {
						  FaildAssert("found duplicate when not allowed: " + currResult);
					  }
				  }
			  } // done duplicate test
			  
			  
		  } // done if(not in the last result) 
	  } // done iterate results 
	  Log.println(formatTestPars(
			  pLowerLimit, pUpperLimit, pGenerateNum, allowDup, sortType, useIntType, precision, testCount)
			  + "[PASSED]");
  } // done test
  
  private String formatTestPars(double pLowerLimit, double pUpperLimit, int pGenerateNum, boolean allowDup,
			   String sortType, boolean useIntType, String precision, int testCount) {
	  
	  String testStr = String.format("RandomNumberGeneratorTest:| %-6d|%s% -4.2f |%s% -4.2f | %-5d|%s|%-6s| %s  |%-5s|",
			  testCount,pLowerLimit>0?" ":"", pLowerLimit, pUpperLimit>0?" ":"",pUpperLimit, pGenerateNum,allowDup?"true ":"false",sortType,useIntType?"int":"dec", precision);
	  return testStr;
  }
  
  private void FaildAssert(String msg) {
	  Assert.assertEquals(true, false, msg);
  }
  
  
  public void fillFeilds(double pLowerLimit, double pUpperLimit, int pGenerateNum, boolean allowDup,
		  						   String sortType, boolean useIntType, String precision) {
	  page.clearAll();
	  if(useIntType) {
		  page.selectInteger();
		  page.getLowerLimitFeild().sendKeys(String.valueOf((int)pLowerLimit));
		  page.getUpperLimitFeild().sendKeys(String.valueOf((int)pUpperLimit));
	  }
	  else {
		  page.selectDecimal();
		  page.getLowerLimitFeild().sendKeys(String.valueOf(pLowerLimit));
		  page.getUpperLimitFeild().sendKeys(String.valueOf(pUpperLimit));
		  page.getPrecisionFeild().clear();
		  page.getPrecisionFeild().sendKeys(precision);
	  }
	  page.getGenerateNumFeild().clear();
	  page.getGenerateNumFeild().sendKeys(String.valueOf(pGenerateNum));  
	  page.getGenerateNumFeild().sendKeys(Keys.TAB); // open new opens in-case that generate number is grater then 1
	  if(pGenerateNum > 1) {
		  
		  // set the correct sort
		  page.setAllowDuplication(allowDup);
		  if(sortType.contentEquals("Ascend")) {
			  page.setSortAscend();
		  }else if(sortType.contentEquals("Descend")) {
			  page.setSortDescend();
		  }else {
			  page.setSortNone();
		  }
	  }
  }

  @DataProvider
  public Object[][] RandomNumberGeneratorDataProvider() {
	  final String filename =  "RandomNumberGeneratorDataSet.txt";
	  List<String> lines = readFileInList(filename);
	  Object[][] ret = new Object[lines.size()][7];
	  for(int i = 0;i < lines.size();i++) {
		  String line = lines.get(i);
		  String[] feilds = line.split(",");
		  Assert.assertEquals(feilds.length, 7); 
		  
		  ret[i][0] = Double.parseDouble(feilds[0]); // low limit
		  ret[i][1] = Double.parseDouble(feilds[1]); // high limit
		  ret[i][2] = Integer.parseInt(feilds[2]); //	generate number
		  ret[i][3] = feilds[3].contentEquals("True") ? true: false; // allow duplicates
		  ret[i][4] = feilds[4];  // sort type: Ascend/Decend/None
		  ret[i][5] = feilds[5].contentEquals("True") ? true: false; // useIntType, if false then use decimal
		  ret[i][6] = feilds[6]; // precision
	  }
	  //System.out.println("read " + ret.length + " TC from " + filename);
	 Log.println(ret.length + " TCs in: " + filename);
    return ret;
  }
    
    
    
    public List<String> readFileInList(String fileName)  { 
    
      List<String> lines = Collections.emptyList(); 
      try
      { 
        lines = 
         Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8); 
      } 
    
      catch (IOException e) 
      { 
    
        // do something 
        e.printStackTrace(); 
      } 
      return lines; 
    }
    
    @BeforeTest
    public void beforeTest() {
    	testCount = 0;
    	Log.print("RandomNumberGeneratorTest:|  Indx |   low  |  high  |  num | dup | sort | type | per.|");
    }
    


  
}
