package com.autolot.qa.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.autolot.qa.base.BaseTest;

public class SearchResultsTest extends BaseTest{

	
	@Test(priority=1)
	public void SearchResultsTitleTest() {
		String pageTitle = searchResults.getSearchPageResultTitle();
		Assert.assertEquals(pageTitle, "New and Used Cars for Sale in all locations - U.S. Bank Auto Sales");
	}
	
	@Test(priority=2)
	public void SearchResultsUrlTest() {
		String pageUrl = searchResults.getSearchPageResultUrl();
		Assert.assertEquals(pageUrl, "https://auto-sales.usbank.com/vehicles-for-sale");
	}
	
	@Test(priority=3)
	public void SearchResultsFilterContainersTest() {
		int containerSize = searchResults.getSearchPageResultFilterContainer().size();
		Assert.assertEquals(containerSize, 18);
	}
	
	@Test(priority=3)
	public void SearchResultsVehiclesContainersTest() {
		int containerSize = searchResults.getSearchPageResultVehiclesContainer().size();
		Assert.assertEquals(containerSize, 9);
	}
	
	@Test(priority=4)
	public void SearchResultsFilterTest() {
		int filterSize = searchResults.getSearchResultFilters().size();
		Assert.assertEquals(filterSize, 18);
	}
	
	@Test(priority=5)
	public void FilterByDistsanceTest() throws InterruptedException {
		boolean flag = searchResults.doValidateSearchResultHeaders("10001", "25");
		Assert.assertTrue(flag);
	}
	
	@Test(priority=6)
	public void SearchResultsFiltersValidationTest() {
		boolean flag = searchResults.doValidateSRPfilters("10002", "75");
		Assert.assertTrue(flag);
	}
}
