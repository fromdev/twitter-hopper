package com.fromdev.automation.twitter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.fromdev.automation.TimeUtil;
import com.fromdev.automation.WebDriverUtil;
import com.fromdev.automation.util.StringUtil;

/**
 * Hello world!
 * 
 */
public class JustRetweetHopper extends AbstractHopper {

	private Set<String> alreadyScreened = new HashSet<String>();

	public static void main(String[] args) {

		JustRetweetHopper app = new JustRetweetHopper();
		app.setup();
		app.init(args);
		app.execute();

	}
	
	public void setup() {
	}

	public void execute() {
		try {

			driver.get("http://www.justretweet.com/twitterapp");
			login();
			TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);

			for (int i = 0; i < TimeUtil.getNumberBetween(10, 50); i++) {
				browse(i);
			}

		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			// Check the title of the page
			System.out.println("Done all rounds " + driver.getTitle());
			driver.close();
			System.out.println(alreadyScreened);
		}

	}

	private void browse(int i) {
		try {
			WebDriverUtil
					.scrollDown(driver, TimeUtil.getNumberBetween(10, 100));
			
			filter();
			
			for (int j = 0; j <  TimeUtil.getNumberBetween(10, 15); j++) {
				TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
				boolean success = rt(j+1);
				if(!success) {
					System.out.println("Cant find rt button on " + (j+1));
				} else {
					System.out.println("Just RT - " + (j+1));
				}
			}
			home();
			TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Some erron in browsing. Going back to home");
		}
		home();
	}

	private void home() {
		driver.get("http://www.justretweet.com");
	}

	private void login() {

		// wait.until(
		// ExpectedConditions.elementToBeClickable(By.id(getSignInXPath())))
		// .click();

	
		TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		// Find the text input element by its name
		WebElement uNameElement = wait.until(ExpectedConditions
				.elementToBeClickable(By.id(getUsernameId())));
		// driver.findElement(By.id("signin-email"));
		// Enter something to search for
		TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		uNameElement.sendKeys(uname);

		WebElement passElement = wait.until(ExpectedConditions
				.elementToBeClickable(By.id(getPasswordId())));
		// driver.findElement(By.id("signin-password"));
		TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		passElement.sendKeys(pass);

		// Now submit the form. WebDriver will find the form for us from the
		// element
		TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);

		WebElement loginSubmitButton = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("/html/body/div[2]/div/form/fieldset[2]/input[1]")));

		loginSubmitButton.click();
	}

	private void filter() {
		TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		String filterSelectXPath = "/html/body/div[5]/div/div[1]/div[1]/div[6]/form/fieldset/p[1]/label/select";
		
		WebElement filterSelectElement = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath(filterSelectXPath)));
		
		WebDriverUtil.selectOption(driver,filterSelectElement, "Most Credits");
	}
	private boolean plusOne(int seq) {
		TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		
//		String plus1xpath = "/html/body/div[5]/div/div[1]/div[1]/div[6]/div/div[" + seq + 3 + "]/div[2]/div[3]/div/div";
		
		By plus1By = By.id("___plusone_"+seq);
		WebElement e =  wait.until(ExpectedConditions
					.elementToBeClickable(plus1By));
		actions.moveToElement(e).build().perform();
		
		boolean status = WebDriverUtil.doClick(driver, plus1By, wait);
		
		return status;
	}

	private boolean rt(int seq) {
		By rtBy = By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[6]/div/div[" + (seq + 3) + "]/div[2]/div[2]/div/a");
		return WebDriverUtil.doClick(driver, rtBy, wait);
	}
	private String getPasswordId() {
		return "password";
	}

	private String getUsernameId() {
		return "username_or_email";
	}
}
