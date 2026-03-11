package com.autolot.qa.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.autolot.qa.factory.DriverFactory;
import com.autolot.qa.pages.SearchResultPage;

public class BaseTest {

	DriverFactory df = new DriverFactory();
	WebDriver driver;
	protected String browserName;
	protected SearchResultPage searchResults;
	
	@BeforeMethod
	public void setUp() {
		df = new DriverFactory();
		browserName = "chrome";
		
		driver = df.browserInit(browserName);
		driver.get("https://auto-sales.usbank.com/vehicles-for-sale");
		
		searchResults = new SearchResultPage(driver);
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
