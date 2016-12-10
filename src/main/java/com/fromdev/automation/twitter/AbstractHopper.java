package com.fromdev.automation.twitter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class AbstractHopper {
	protected String uname = "kzvikzvi1";
	protected String pass = "test1234";
	
	protected WebDriver driver = new FirefoxDriver();
	protected Actions actions = new Actions(driver);
	protected WebDriverWait wait = new WebDriverWait(driver, 30);

	
	public void init(String[] args) {

		if (args != null && args.length > 0) {
			uname = args[0];
			if (args.length > 1) {
				pass = args[1];
			}
		}
	}
}
