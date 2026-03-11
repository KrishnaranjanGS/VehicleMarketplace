package com.autolot.qa.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.autolot.qa.constants.AutolotConstants;

public class ElementUtils {

	WebDriver driver;
	WebDriverWait wait;
	Actions act;
	JavascriptExecutor js;
	
	public ElementUtils(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(AutolotConstants.DEFAULT_MEDIUM_TIME_OUT));
		this.act = new Actions(driver);
		this.js = (JavascriptExecutor)driver;
	}
	
	// A helper to get a wait object only when a custom timeout is needed
	private WebDriverWait getWait(int timeout) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }
	
	public String getPageTitle() {
	String title = driver.getTitle();
	return title;
	}
	
	public String getPageUrl() {
	String url = driver.getCurrentUrl();
	return url;
	}
	
	public WebElement getWebElement(By locator) {
		return driver.findElement(locator);
	}
	
	public String getElementTextUsingWait(By locator, int timeout) {
		return waitForElementVisible(locator, timeout).getText();
	}
	
	public List<WebElement> getElementsNamesUsingWait(By locator, int timeout) {
		List<WebElement> elements = waitForElementsVisible(locator, timeout);
		List<String> text = new ArrayList<>();
			for(WebElement e: elements) {
				text.add(e.getText());
			}
		System.out.println(text);
		return elements;
	}
	
	public WebElement waitForElementVisible(By locator, int timeout) {
		WebElement element = getWait(timeout).until(ExpectedConditions.visibilityOfElementLocated(locator));
		return element;
	}
	
	public List<WebElement> waitForElementsVisible(By locator, int timeout) {
		List<WebElement> elements = getWait(timeout).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		return elements;
	}
	
	public WebElement waitForElementClickable(By locator, int timeout) {
		WebElement element = getWait(timeout).until(ExpectedConditions.elementToBeClickable(locator));
		return element;
	}
	
	public void clickUsingActionsClass(By locator) {
		act.moveToElement(getWebElement(locator)).click().build().perform();
	}
	
	public void waitForPageLoad(int timeout) {
		getWait(timeout).until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
	}	
	
	public void clickAndRetry(By parentLocator, By childLocator, int timeout) {
		boolean isOpen = false;
	    int counter = 0;
	    while (!isOpen && counter < 5) {
	        try {
	            // Wait for element and click 
	        	WebElement ele = waitForElementClickable(parentLocator, timeout);
	            ele.click(); 
	            // Check if the child element is now visible
	            waitForElementsVisible(childLocator, timeout);
	            isOpen = true;
	        } catch (Exception e) {
	            counter++;
	            System.out.println("Element click didn't work, retrying... attempt " + counter);
	        }
	    }
	}
	
	 public void clickUsingJS(WebElement element) { 
	 js.executeScript("arguments[0].click();", element); 
	 }
	 
	 public void clickUsingJS(By locator) { 
	 js.executeScript("arguments[0].click();", getWebElement(locator)); 
	 }
	
}
