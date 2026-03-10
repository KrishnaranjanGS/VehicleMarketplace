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

public class ElementUtils {

	WebDriver driver;
	WebDriverWait wait;
	Actions act;
	JavascriptExecutor js;
	
	public ElementUtils(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		act = new Actions(driver);
		js = (JavascriptExecutor)driver;
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
	
	public String getElementText(By locator) {
		return waitForElementVisible(locator).getText();
	}
	
	public List<WebElement> getElementsNames(By locator) {
		List<WebElement> elements = waitForElementsVisible(locator);
		List<String> text = new ArrayList<>();
			for(WebElement e: elements) {
				text.add(e.getText());
			}
		System.out.println(text);
		return elements;
	}
	
	public WebElement waitForElementVisible(By locator) {
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		return element;
	}
	
	public List<WebElement> waitForElementsVisible(By locator) {
		List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		return elements;
	}
	
	public WebElement waitForElementClickable(By locator) {
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		return element;
	}
	
	public void clickUsingActionsClass(By locator) {
		act.moveToElement(getWebElement(locator)).click().build().perform();
	}
	
	public void waitForPageLoad() {
	    wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
	}	
	
	 public void clickUsingJS(WebElement element) { 
	 js.executeScript("arguments[0].click();", element); 
	 }
	
}
