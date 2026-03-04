package com.autolot.qa.factory;

import org.apache.commons.math3.geometry.spherical.twod.Edge;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

	WebDriver driver;
	
	public WebDriver browserInit(String browserName) {
		
		switch (browserName) {
		case "chrome":
			driver  = new ChromeDriver();
			break;
			
		case "firefox":
			driver  = new FirefoxDriver();
			break;
			
		case "edge":
			driver  = new EdgeDriver();
			break;

		default:
			System.out.println("Invalid browser name. Valid names: chrome, firefox, edge");
			break;
		}
		
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		return driver;
	}
	
}
