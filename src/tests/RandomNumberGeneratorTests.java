package tests;

import org.testng.annotations.Test;

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

public class RandomNumberGeneratorTests {
	ChromeDriver driver;
	RNGPage page;
	
	
  @BeforeMethod
  public void beforeMethod() {
	  page.openPage();
  }
  
  /// node: there is no precision and int/decimal selection tests 
  @Test(dataProvider = "RandomNumberGeneratorDataProvider")
  public void RandomNumberGeneratorTest(double pLowerLimit, double pUpperLimit, int pGenerateNum, boolean allowDup,
		  						   String sortType, boolean useIntType, String precision) {
	  fillFeilds(pLowerLimit, pUpperLimit, pGenerateNum, allowDup, sortType, useIntType, precision);
	  try {
		Thread.sleep(100);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  page.submit();
	  
	  List<WebElement> results = page.getResults();
	  
	  Assert.assertEquals(results.size(), pGenerateNum, "incorrct number of results: "); // verify the number of results
	  
	  
	  boolean isAscend = true;
	  boolean isDescend = true;
	  for(int i = 0; i < results.size();i++) {
		  double currResult = Double.parseDouble(results.get(i).getText());
		  
		// if out of bounds 
		  if(pLowerLimit > currResult || pUpperLimit < currResult) {
			  FaildAssert("result: " + String.valueOf(currResult) + " is out of bounds: " + pLowerLimit + " - " + pUpperLimit);
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
				  
			  } // if all the results are equal then the sort is ascend and descend
			  
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
	  
	  // test sort type if size above 1:
	  if(pGenerateNum > 1) {
		  // resultSortType = "Ascend"/"Descend"/"None"
		  //String resultSortType = isAscend? "Ascend":isDescend?"Descend":"None";
		  //Assert.assertEquals(resultSortType, sortType, "result sort: " + resultSortType + " requested: " + sortType);
	  }
	  
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
	  List<String> lines = readFileInList("RandomNumberGeneratorDataSet.txt");
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
    
  @BeforeSuite
  public void beforeSuite() {
	  driver = new ChromeDriver();
	  page = new RNGPage(driver);
  }

  @AfterSuite
  public void afterSuite() {
	  driver.close();
  }

}
