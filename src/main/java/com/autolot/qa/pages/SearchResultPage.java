package com.autolot.qa.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.autolot.qa.constants.AutolotConstants;
import com.autolot.qa.utils.ElementUtils;

public class SearchResultPage {
	
	private  WebDriver driver;
	private ElementUtils eleUtil;
	private Select select;

	private By searchResultsFilterContainer = By.xpath("//div[@class='content']/div[@class='left-section']/div/div");
	private By searchResultsVehiclesContainer = By.xpath("//div[@class='content']/div[@class='right-section']/div/div[3]/article");
	private By filtersPanel = By.xpath("//div[@class='filters']");
	private By searchResultFilters = By.xpath("//div[@class='filters']/div");
		private By filterByDistance = By.xpath("//div[@class='acc-item'][1]");
		private By cityOrZipcode = By.xpath("//div[@class='search-input']/input");
		private By citySuggestion = By.xpath("//ul[@class='suggestions-list']/li[1]");
		private By proximityFilter = By.xpath("//select[contains(@id,'srp-select-within')]");
	private By filterByMake = By.xpath("//div[@class='acc-item'][4]");
		private By makesContainer = By.xpath("//div[@id='acc-content-accordion_make']");
		private By makes = By.xpath("//input[@name='make']");
	private By searchResultProximity = By.xpath("//div//h1[contains(@class,'page-heading')]/following-sibling::div/p/span[3]");
	private By searchResultZipCode = By.xpath("//div//h1[contains(@class,'page-heading')]/following-sibling::div/p/span[4]");
	private By srpFiltersSelected = By.xpath("//div[@class='filters-inner']/span");
	private By srpSort = By.xpath("//div[@class='right-top']//label[contains(text(),'Sort by')]/following-sibling::select");
	private By srpFirstVehicleLink = By.xpath("//a[@class='article-link'][1]");
	private By srpFirstVehicleInfo = By.xpath("//article[@class='listing']/div[@class='main-info'][1]");
	private By srpFirstVehicleFooter = By.xpath("//article[@class='listing']/div[@class='footer'][1]");
	private By vehiclesPrices = By.xpath("//div[@class='price']");
	private By paginationContainer = By.xpath("//section[@aria-label='Pagination']");
	private By paginationBtn = By.xpath("//ul[@class='pagination-list']/li");
	
	
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
	
	public  List<WebElement> getSearchPageResultFilterContainer() {
		return eleUtil.getElementsNamesUsingWait(searchResultsFilterContainer, AutolotConstants.DEFAULT_SHORT_TIME_OUT);
	}
	
	public  List<WebElement> getSearchPageResultVehiclesContainer() {
		return eleUtil.getElementsNamesUsingWait(searchResultsVehiclesContainer, AutolotConstants.DEFAULT_SHORT_TIME_OUT);
	}
	
	public List<WebElement> getSearchResultFilters(){
		return eleUtil.getElementsNamesUsingWait(searchResultFilters, AutolotConstants.DEFAULT_SHORT_TIME_OUT);
	}
	
	public void doFilterByDistance(String zipCode, String proximityValue) {
		eleUtil.waitForPageLoad(AutolotConstants.DEFAULT_MEDIUM_TIME_OUT);
		eleUtil.waitForElementVisible(filtersPanel, AutolotConstants.DEFAULT_SHORT_TIME_OUT);
		eleUtil.clickAndRetry(filterByDistance, cityOrZipcode, AutolotConstants.DEFAULT_RETRY_TIME_OUT);
		WebElement cityZipcode =eleUtil.waitForElementVisible(cityOrZipcode, AutolotConstants.DEFAULT_SHORT_TIME_OUT);
		cityZipcode.sendKeys(zipCode);
		WebElement citySuggest = eleUtil.waitForElementVisible(citySuggestion, AutolotConstants.DEFAULT_SHORT_TIME_OUT);
		citySuggest.click();
		eleUtil.waitForElementClickable(proximityFilter, AutolotConstants.DEFAULT_SHORT_TIME_OUT);
		select = new Select(eleUtil.getWebElement(proximityFilter));
		select.selectByValue(proximityValue);
	}
	
	public void doFilterByMake(String makeName) {
		eleUtil.waitForPageLoad(AutolotConstants.DEFAULT_MEDIUM_TIME_OUT);
		eleUtil.waitForElementVisible(filtersPanel, AutolotConstants.DEFAULT_SHORT_TIME_OUT);
		eleUtil.clickAndRetry(filterByMake, makesContainer , AutolotConstants.DEFAULT_SHORT_TIME_OUT);
		List<WebElement> makesList = eleUtil.waitForElementsVisible(makes, AutolotConstants.DEFAULT_SHORT_TIME_OUT);
			for(WebElement e: makesList) {
				eleUtil.scrollToElementUsingActions(e);
				String text = e.getText().trim();
				System.out.println(text);
					if(text.contains(makeName)) {
						eleUtil.clickUsingActionsClass(e);
						break;
					}
					else {
						System.out.println("Invalid make");
					}
			}
	}
	
	public boolean doValidateSearchResultHeaders(String zipCode, String proximityValue) {
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
	
	public boolean doValidateSRPfilters(String zipCode, String proximityValue) {
		doFilterByDistance(zipCode, proximityValue);
		boolean flag = false;
		List<WebElement> srpFilters = eleUtil.waitForElementsVisible(srpFiltersSelected, AutolotConstants.DEFAULT_SHORT_TIME_OUT);
			for (WebElement e : srpFilters) {
				String text = e.getText();
				if(text.contains(zipCode) || text.contains(proximityValue)) {
					flag=true;
				}
			}
		return flag;
	}
	
	public boolean doSortValidation(String sortByText) throws InterruptedException {
		eleUtil.waitForPageLoad(AutolotConstants.DEFAULT_LONG_TIME_OUT);
		eleUtil.waitForElementVisible(srpSort, AutolotConstants.DEFAULT_SHORT_TIME_OUT);
		eleUtil.waitForElementsVisible(vehiclesPrices, AutolotConstants.DEFAULT_SHORT_TIME_OUT);
		String firstPriceBeforeSort = eleUtil.getElementsNamesUsingWait(vehiclesPrices, AutolotConstants.DEFAULT_SHORT_TIME_OUT).get(0).getText();
		eleUtil.doSelectDropdownByText(srpSort, sortByText);
		eleUtil.waitInit(AutolotConstants.DEFAULT_MEDIUM_TIME_OUT).until(ExpectedConditions.stalenessOf(driver.findElement(vehiclesPrices)));
		List<WebElement> prices = eleUtil.waitForElementsVisible(vehiclesPrices, AutolotConstants.DEFAULT_SHORT_TIME_OUT);
			int prevPrice = 0;
			for(WebElement e: prices) {
				String text = e.getText().trim().replaceAll("[^0-9]", "");
				if(text.isEmpty()) continue;
				int currPrice = Integer.parseInt(text);
					if(prevPrice<=currPrice) {
						System.out.println(prevPrice+"<="+currPrice);
						prevPrice=currPrice;
					}
					else {
						System.out.println(prevPrice+">"+currPrice);
						return false;
					}
			}
			System.out.println("Sort by price: low to high is PASSED!");
			return true;
	}
	
	public String doGetVehicleDetailsFromSrp() {
		eleUtil.waitForPageLoad(AutolotConstants.DEFAULT_LONG_TIME_OUT);
		String text;
		text = eleUtil.getElementTextUsingWait(srpFirstVehicleInfo, AutolotConstants.DEFAULT_LONG_TIME_OUT);
		text = text + eleUtil.getElementTextUsingWait(srpFirstVehicleFooter, AutolotConstants.DEFAULT_SHORT_TIME_OUT);
		System.out.println(text);
		return text;
	}
	
	public VehicleDetailPage goToVehicleDetailPage() {
		eleUtil.waitForPageLoad(AutolotConstants.DEFAULT_LONG_TIME_OUT);
		eleUtil.waitForElementClickable(srpFirstVehicleLink, AutolotConstants.DEFAULT_SHORT_TIME_OUT).click();
		return new VehicleDetailPage(driver);
	}
	
	public String[] doValidatePaginationText() {
		eleUtil.waitForPageLoad(AutolotConstants.DEFAULT_MEDIUM_TIME_OUT);
		String[] paginationText = eleUtil.getElementTextUsingWait(paginationContainer, AutolotConstants.DEFAULT_SHORT_TIME_OUT)
				.split("\n");
			for(String s: paginationText) {
				System.out.println(s);
			}
		return paginationText;
	}
	
	public boolean doClickPaginationButton(String xPath, String option) {
		By locator = By.xpath(xPath+option+"/a");
		try {
			System.out.println("Checking '" + eleUtil.getWebElement(locator).getText() + "' button");
			eleUtil.scrollToElementUsingActions(eleUtil.waitForElementClickable(locator, AutolotConstants.DEFAULT_SHORT_TIME_OUT));
			eleUtil.waitForElementClickable(locator, AutolotConstants.DEFAULT_SHORT_TIME_OUT).click();
			System.out.println("Clicked on " + eleUtil.getWebElement(locator).getText());
		} catch (Exception e) {
		    System.out.println("FAILED to click: " + option);
		    e.printStackTrace();
		    return false;
		}
		return true;
	}

	public boolean doValidatePaginationFunction() {
		eleUtil.waitForPageLoad(AutolotConstants.DEFAULT_MEDIUM_TIME_OUT);
		String xPath = "//ul[@class='pagination-list']/li";
		boolean flag1 = doClickPaginationButton(xPath, "[1]");
		boolean flag2 = doClickPaginationButton(xPath, "[2]");
		String[] text = doValidatePaginationText();
		boolean flag3 = false;
		System.out.println("text[1] = " + text[1]);
		System.out.println("text[2] = " + text[2]);
			if (text[1].contains(AutolotConstants.PAGINATION_PREV) && text[2].contains(AutolotConstants.PAGINATION_NEXT)) {
				flag3 = true;
			}

		return !flag1 && flag2 && flag3;
	}
	
}
