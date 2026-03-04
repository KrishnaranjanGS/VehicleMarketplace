package com.autolot.qa.utils;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ElementUtils {

	WebDriver driver;
	
	public ElementUtils(WebDriver driver) {
		this.driver = driver;
	}
	
	public List<WebElement> getElementsNames(By locator) {
		List<WebElement> elements = driver.findElements(locator);
		List<String> text= new ArrayList<>();
			for(WebElement e: elements) {
				text.add(e.getText());
			}
		System.out.println(text);
		return elements;
	}
}
