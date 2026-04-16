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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.autolot.qa.constants.AutolotConstants;

public class ElementUtils {

	WebDriver driver;
	WebDriverWait wait;
	Actions act;
	JavascriptExecutor js;
	
	public ElementUtils(WebDriver driver) {
		this.driver = driver;
		this.act = new Actions(driver);
		this.js = (JavascriptExecutor)driver;
	}
	
	public WebDriverWait waitInit(int timeOut) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait;
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
	
	public WebElement waitAndGetWebElement(By locator, int timeOut) {
		waitInit(timeOut);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
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
	
	public WebElement waitForElementVisible(By locator, int timeOut) {
		waitInit(timeOut);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		return element;
	}
	
	public List<WebElement> waitForElementsVisible(By locator, int timeOut) {
		waitInit(timeOut);
		List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		return elements;
	}
	
	public WebElement waitForElementClickable(By locator, int timeOut) {
		waitInit(timeOut);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		return element;
	}
	
	public void clickUsingActionsClass(By locator) {
		act.moveToElement(getWebElement(locator)).click().build().perform();
	}
	
	public void clickUsingActionsClass(WebElement element) {
		act.moveToElement(element).click().build().perform();
	}
	
	public void scrollToElementUsingActions(WebElement element) {
		act.moveToElement(element).build().perform();
		
	}
	
	 public void clickUsingJS(WebElement element) { 
	 js.executeScript("arguments[0].click();", element); 
	 }
	 
	 public void clickUsingJS(By locator) { 
	 js.executeScript("arguments[0].click();", getWebElement(locator)); 
	 }
	 
	 public void doSelectDropdownByText(By locator, String sortText) {
		 Select select = new Select(getWebElement(locator));
		 select.selectByVisibleText(sortText);
	 }
	 
	public void waitForPageLoad(int timeOut) {
		waitInit(timeOut);
		wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
	}	
	
	public void clickAndRetry(By parentLocator, By childLocator, int timeout) {
		boolean isOpen = false;
	    int counter = 0;
	    while (!isOpen && counter < 5) {
	        try {
	            // Wait for element and click 
	        	WebElement ele = waitForElementClickable(parentLocator, timeout);
	        	scrollToElementUsingActions(ele);
	            ele.click(); 
	            // Check if the child element is now visible
	            waitForElementsVisible(childLocator, timeout);
	            scrollToElementUsingActions(getWebElement(childLocator));
	            isOpen = true;
	        } catch (Exception e) {
	            counter++;
	            System.out.println("Element click didn't work for " + e + ", retrying... attempt " + counter);
	        }
	    }
	}
	
	public void waitForElementRefresh(By locator, String value, int timeout) {
		int counter = 5;
		do {
			waitForElementVisible(locator, timeout);
			String eleValue = driver.findElement(locator).getText().trim().split(" ")[1];
			System.out.println("proximity value in SRP is: " + eleValue);
			if (eleValue.equals(value)){
				break;
			}
			counter--;
		} while (counter>0);

	}
	

	
}
