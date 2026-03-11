package com.autolot.qa.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.autolot.qa.utils.ElementUtils;

public class SearchResultPage {
	
	private  WebDriver driver;
	private ElementUtils eleUtil;

	private By searchResultsContainer = By.xpath("//div[@class='content']/div");
	private By filtersPanel = By.xpath("//div[@class='filters']");
	private By searchResultFilters = By.xpath("//div[@class='filters']/div");
	
	private By filterByDistance = By.xpath("//div[@class='acc-item'][1]");
		private By cityOrZipcode = By.xpath("//div[contains(@class,'search-input')]/input");
		private By citySuggestion = By.xpath("//ul[@class='suggestions-list']/li[1]");
		private By proximityFilter = By.xpath("//select[contains(@id,'srp-select-within')]");
	private By filterByCondition = By.xpath("//div[@class='acc-item'][2]");
	private By filterByPrice = By.xpath("//div[@class='acc-item'][3]");
	private By filterByMake = By.xpath("//div[@class='acc-item'][4]");
	private By filterByModel = By.xpath("//div[@class='acc-item'][5]");
	private By filterByTrim = By.xpath("//div[@class='acc-item'][6]");
	private By filterByYear = By.xpath("//div[@class='acc-item'][7]");
	private By filterByMileage = By.xpath("//div[@class='acc-item'][8]");
	private By filterByFeatures = By.xpath("//div[@class='acc-item'][9]");
	private By filterByBodyStyles = By.xpath("//div[@class='acc-item'][10]");
	private By filterByExteriorColor = By.xpath("//div[@class='acc-item'][11]");
	private By filterByInteriorColor = By.xpath("//div[@class='acc-item'][12]");
	private By filterByDriveTrain = By.xpath("//div[@class='acc-item'][13]");
	private By filterByTransmission = By.xpath("//div[@class='acc-item'][14]");
	private By filterByEngine = By.xpath("//div[@class='acc-item'][15]");
	private By filterByFuelType = By.xpath("//div[@class='acc-item'][16]");
	private By filterByFurlEconomy = By.xpath("//div[@class='acc-item'][17]");

	private By searchResults = By.xpath("//div[contains(@class,'g-listings')]/article");
	private By vehicleState = By.xpath("//div[@class='footer']");	
	private By searchResultvehicleCount = By.xpath("//div//h1[contains(@class,'page-heading')]/following-sibling::div/p/span[1]");
	private By searchResultCity = By.xpath("//div//h1[contains(@class,'page-heading')]/following-sibling::div/p/span[2]");
	private By searchResultProximity = By.xpath("//div//h1[contains(@class,'page-heading')]/following-sibling::div/p/span[3]");
	private By searchResultZipCode = By.xpath("//div//h1[contains(@class,'page-heading')]/following-sibling::div/p/span[4]");
	
	public SearchResultPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtils(driver);
	}
	
	public String getSearchPageResultTitle() {
		return eleUtil.getPageTitle();
	}
	
	public  String getSearchPageResultUrl() {
		return eleUtil.getPageUrl();
	}
	
	public  List<WebElement> getSearchPageResultContainers() {
		return eleUtil.getElementsNames(searchResultsContainer);
	}
	
	public List<WebElement> getSearchResultFilters(){
		return eleUtil.getElementsNames(searchResultFilters);
	}
	
	public void doFilterByDistance(String zipCode, String proximityValue) {
		eleUtil.waitForPageLoad();
		eleUtil.waitForElementVisible(filtersPanel);
		
//		boolean isOpen = false;
//	    int counter = 0;
//	    while (!isOpen && counter < 5) {
//	        try {
//	            // Wait for element and click 
//	        	WebElement ele = eleUtil.waitForElementClickable(filterByDistance);
//	            ele.click(); 
//	            // Check if the zipcode input is now visible (Timeout of 2 seconds here)
//	            eleUtil.waitForElementsVisible(cityOrZipcode);
////	            new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.visibilityOfElementLocated(cityOrZipcode));
//	            isOpen = true;
//	        } catch (Exception e) {
//	            counter++;
//	            System.out.println("Accordion didn't open, retrying click... attempt " + counter);
//	        }
//	    }
		eleUtil.clickAndRetry(filterByDistance, cityOrZipcode);
		eleUtil.waitForElementClickable(filterByDistance).click();
		WebElement cityZipcode =eleUtil.waitForElementVisible(cityOrZipcode);
		cityZipcode.sendKeys(zipCode);
		WebElement citySuggest = eleUtil.waitForElementVisible(citySuggestion);
		citySuggest.click();
		eleUtil.waitForElementClickable(proximityFilter);
		Select select = new Select(eleUtil.getWebElement(proximityFilter));
		select.selectByValue(proximityValue);
	}
	
	
	
	public boolean doValidateSearchResultHeaders(String zipCode, String proximityValue) throws InterruptedException {
		doFilterByDistance(zipCode, proximityValue);
		eleUtil.waitForElementsVisible(searchResultZipCode);
		String srpZipCode = eleUtil.getElementText(searchResultZipCode);
		eleUtil.waitForElementsVisible(searchResultProximity);
		Thread.sleep(2000);
		String srpProximity = eleUtil.getElementText(searchResultProximity);
		System.out.println("Input zipcode: " + zipCode + "; Filtered zipcode: " + srpZipCode + 
				"\nInput proximity: " + proximityValue + "; Filtered proximity: " + srpProximity );
			if(srpZipCode.equals(zipCode) && srpProximity.contains(proximityValue)) {
				System.out.println("Input zipcode and proximity are validated against filtered values in header");
				return true;
			}
			else {
				System.out.println(">>>>>>>>>> Encountered error while validating zipcode and proximity <<<<<<<<<<");
				return false;
			}
	}
	
	
}
