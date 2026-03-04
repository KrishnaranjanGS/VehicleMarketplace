package com.autolot.qa.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.autolot.qa.utils.ElementUtils;

public class SearchResultPage {
	
	private  WebDriver driver;
	private ElementUtils eleUtil;

	private By searchResultsContainer = By.xpath("//div[@class='content']/div");
	private By searchResultFilters = By.xpath("//div[@class='filters']/div");
	
	public SearchResultPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtils(driver);
	}
	
	public String getSearchPageResultTitle() {
		return driver.getTitle();
	}
	
	public  String getSearchPageResultUrl() {
		return driver.getCurrentUrl();
	}
	
	public  List<WebElement> getSearchPageResultContainers() {
		return eleUtil.getElementsNames(searchResultsContainer);
	}
	
	public List<WebElement> getSearchResultFilters(){
		return eleUtil.getElementsNames(searchResultFilters);
	}
	
	
}
