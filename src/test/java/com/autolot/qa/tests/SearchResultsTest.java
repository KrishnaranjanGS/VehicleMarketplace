package com.autolot.qa.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.autolot.qa.base.BaseTest;
import com.autolot.qa.constants.AutolotConstants;

public class SearchResultsTest extends BaseTest{

	
	@Test(priority=1)
	public void SearchResultsTitleTest() {
		String pageTitle = searchResults.getSearchPageResultTitle();
		Assert.assertEquals(pageTitle, AutolotConstants.PAGE_TITLE);
	}
	
	@Test(priority=2)
	public void SearchResultsUrlTest() {
		String pageUrl = searchResults.getSearchPageResultUrl();
		Assert.assertEquals(pageUrl, AutolotConstants.PAGE_URL);
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
	public void FilterByDistsanceTest() {
		boolean flag = searchResults.doValidateSearchResultHeaders("10001", "25");
		Assert.assertTrue(flag);
	}
	
	@Test(priority=6)
	public void SearchResultsFiltersValidationTest() {
		boolean flag = searchResults.doValidateSRPfilters("10002", "75");
		Assert.assertTrue(flag);
	}
	
	@Test(priority=7)
	public void SortTest() throws InterruptedException {
		boolean flag = searchResults.doSortValidation(AutolotConstants.SORT_OPTION);
		Assert.assertTrue(flag);
	}
	
	@Test(priority=8)
	public void paginationTextValidationTest() {
		String[] paginationText = searchResults.doValidatePaginationText();
		softAssert.assertTrue(paginationText[0].startsWith(AutolotConstants.PAGINATION_TEXT));
		softAssert.assertTrue(paginationText[1].contains(AutolotConstants.PAGINATION_PREV_DISABLED));
		softAssert.assertTrue(paginationText[2].contains(AutolotConstants.PAGINATION_NEXT));
		softAssert.assertAll();
	}
	
	@Test(priority=9)
	public void paginationFunctionValidationTest() {
		boolean flag = searchResults.doValidatePaginationFunction();
		Assert.assertTrue(flag);
	}
	
}
