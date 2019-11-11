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
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

import java.util.*; 
import java.nio.charset.StandardCharsets; 
import java.nio.file.*; 
import java.io.*; 
/**
 * all the tests made for random-number-generator page from {@link https://www.calculator.net/random-number-generator.html}
 */
public class RandomNumberGeneratorTests extends BaseTest {
	private int testCount; // for debug and log
	RNGPage page; // the tested page
	
	// init the page
    @BeforeSuite
    public void beforeSuite() {
  	  page = new RNGPage();
    }
	
	// every test case, reopen the page
  @BeforeMethod
  public void beforeMethod() {
	  page.openPage();
  }

  /**
   * test the result for a given parameters
   * O(N^2) for the duplicates check
   * @param pLowerLimit lower limit
   * @param pUpperLimit upper limit
   * @param pGenerateNum number of numbers to generate
   * @param allowDup true if duplicates is allowed, false if duplicates is not allowed
   * @param sortType Ascend/Descend/None
   * @param useIntType true to use integer type, false to use decimal type
   * @param precision how many numbers will be shown after the dot, useIntType must be false, otherwise the value is ignored
   */
  @Test(dataProvider = "RandomNumberGeneratorDataProvider")
  public void RandomNumberGeneratorTest(double pLowerLimit, double pUpperLimit, int pGenerateNum, boolean allowDup,
		  						   String sortType, boolean useIntType, String precision) {
	  ++testCount;
	  Log.print(formatTestPars(
			  pLowerLimit, pUpperLimit, pGenerateNum, allowDup, sortType, useIntType, precision, testCount));
	  
	  fillFeilds(pLowerLimit, pUpperLimit, pGenerateNum, allowDup, sortType, useIntType, precision);
	  page.submit();
	  List<WebElement> results = page.getResults();
	  
	  // verify the number of results
	  Assert.assertEquals(results.size(), pGenerateNum, "incorrect number of results: "); 
	  
	  // for every result:
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
				  Assert.assertEquals(sortType.contentEquals("Descend")?true:false, false, "the result is not in desending order");
			  }else if(currResult > nextResult) {
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
	  //Log.println("[PASSED]");
  } // done test
  
  @AfterMethod
  public void afterMethod(ITestResult result) {
	  if(result.isSuccess()) {
		  Log.println(" [PASSED]");
	  }else {
		  Log.println(" [FAILED]");
	  }
  }
  
  
  /**
   * formats the test parameters for nice printing
   */
  private String formatTestPars(double pLowerLimit, double pUpperLimit, int pGenerateNum, boolean allowDup,
			   String sortType, boolean useIntType, String precision, int testCount) {
	  
	  String testStr = String.format("RandomNumberGeneratorTest:| %-6d|%s% -4.2f |%s% -4.2f | %-5d|%s|%-6s| %s  |%-5s|",
			  testCount,pLowerLimit>0?" ":"", pLowerLimit, pUpperLimit>0?" ":"",pUpperLimit, pGenerateNum,allowDup?"true ":"false",sortType,useIntType?"int":"dec", precision);
	  return testStr;
  }
  
  private void FaildAssert(String msg) {
	  Assert.fail(msg);
  }
  
  
  public void fillFeilds(double pLowerLimit, double pUpperLimit, int pGenerateNum, boolean allowDup,
		  						   String sortType, boolean useIntType, String precision) {
	  page.clearAll();
	  // sets the used type (int/decimal) and the upper and lower limits
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
		  page.getPrecisionFeild().sendKeys(precision); // set precision if on decimal
	  }
	  // set the number of generated numbers and press tab for more options
	  page.getGenerateNumFeild().clear();
	  page.getGenerateNumFeild().sendKeys(String.valueOf(pGenerateNum));  
	  page.getGenerateNumFeild().sendKeys(Keys.TAB); // open new opens in-case that generate number is grater then 1
	  if(pGenerateNum > 1) {
		  
		  // set allow duplications:
		  page.setAllowDuplication(allowDup);
		  // set the correct sort type:
		  if(sortType.contentEquals("Ascend")) {
			  page.setSortAscend();
		  }else if(sortType.contentEquals("Descend")) {
			  page.setSortDescend();
		  }else {
			  page.setSortNone();
		  }
	  }
  }

  
  /**
   * reads all the test cases from a text file in this format:
   * lines example:
   * lowLim,highLim,num,allowDup,sort,useInt,precision
   * 200,300,1,False,Ascend,True,22
   * 200,300,1,False,Ascend,True,99
   * 200,300,1,False,Ascend,False,1
   */
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
    
    
    /**
     * reads the entire file and put it into a list, every entry is a line
     * @param fileName
     * @return
     */
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
    
    
    // prints the test's headline
    @BeforeTest
    public void beforeTest() {
    	testCount = 0;
    	Log.print("RandomNumberGeneratorTest:|  Indx |   low  |  high  |  num | dup | sort | type | per.|");
    }
    


  
}
