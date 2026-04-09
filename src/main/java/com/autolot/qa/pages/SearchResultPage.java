package com.autolot.qa.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.autolot.qa.constants.AutolotConstants;
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
		return eleUtil.getElementsNamesUsingWait(searchResultsContainer, AutolotConstants.DEFAULT_SHORT_TIME_OUT);
	}
	
	public List<WebElement> getSearchResultFilters(){
		return eleUtil.getElementsNamesUsingWait(searchResultFilters, AutolotConstants.DEFAULT_SHORT_TIME_OUT);
	}
	
	public void doFilterByDistance(String zipCode, String proximityValue) {
		eleUtil.waitForPageLoad(AutolotConstants.DEFAULT_SHORT_TIME_OUT);
		eleUtil.waitForElementVisible(filtersPanel, AutolotConstants.DEFAULT_SHORT_TIME_OUT);
		eleUtil.clickAndRetry(filterByDistance, cityOrZipcode, AutolotConstants.DEFAULT_RETRY_TIME_OUT);
		eleUtil.waitForElementClickable(filterByDistance, AutolotConstants.DEFAULT_SHORT_TIME_OUT).click();
		WebElement cityZipcode =eleUtil.waitForElementVisible(cityOrZipcode, AutolotConstants.DEFAULT_SHORT_TIME_OUT);
		cityZipcode.sendKeys(zipCode);
		WebElement citySuggest = eleUtil.waitForElementVisible(citySuggestion, AutolotConstants.DEFAULT_SHORT_TIME_OUT);
		citySuggest.click();
		eleUtil.waitForElementClickable(proximityFilter, AutolotConstants.DEFAULT_SHORT_TIME_OUT);
		Select select = new Select(eleUtil.getWebElement(proximityFilter));
		select.selectByValue(proximityValue);
	}
	
	public boolean doValidateSearchResultHeaders(String zipCode, String proximityValue) throws InterruptedException {
		doFilterByDistance(zipCode, proximityValue);
		eleUtil.waitForElementsVisible(searchResultZipCode, AutolotConstants.DEFAULT_SHORT_TIME_OUT);
		String srpZipCode = eleUtil.getElementTextUsingWait(searchResultZipCode, AutolotConstants.DEFAULT_SHORT_TIME_OUT);
		eleUtil.waitForElementRefresh(searchResultProximity, proximityValue, AutolotConstants.DEFAULT_SHORT_TIME_OUT);
		String srpProximity = eleUtil.getElementTextUsingWait(searchResultProximity, AutolotConstants.DEFAULT_SHORT_TIME_OUT);
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
