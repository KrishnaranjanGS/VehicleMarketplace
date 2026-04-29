package com.autolot.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.autolot.qa.utils.ElementUtils;

public class VehicleDetailPage {
	
	private  WebDriver driver;
	private ElementUtils eleUtil;
	private SearchResultPage srp;

	private By vdpTopInfo = By.xpath("//div[@class='top-info-wrap']");

	public VehicleDetailPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtils(driver);
		srp = new SearchResultPage(driver);
	}
	
	public void doValidateVehicleInfo() {
		
	}

}
