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
public class TwiendsHopper extends AbstractHopper {

	private Set<String> alreadyScreened = new HashSet<String>();

	private List followButtonXPathList = null;
	private List discoverButtonXPathList = null;

	public static void main(String[] args) {

		TwiendsHopper app = new TwiendsHopper();
		app.setup();
		app.init(args);
		app.execute();

	}
	
	public void setup() {
		followButtonXPathList = new ArrayList<String>();
		followButtonXPathList
		.add("/html/body/form/div[2]/div/div[7]/div[1]/div[1]/div[5]/div[2]/div[2]/i[2]");
		followButtonXPathList
				.add("//*[@id='column0']/div[1]/div[3]/div[2]/div[2]/i[2]");
		followButtonXPathList
		.add("//*[@id='column0']/div[1]/div[4]/div[2]/div[2]/i[2]");
		followButtonXPathList
		.add("//*[@id='column0']/div[1]/div[3]/div[2]/div[2]/i");
		
		discoverButtonXPathList = new ArrayList<String>();
		discoverButtonXPathList.add("//*[@id='DiscoverNext']/span");
		discoverButtonXPathList.add("//*[@id='DiscoverList']/a[4]");
	}

	public void execute() {
		try {

			driver.get("http://twiends.com/login");
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
			for (int j = 0; j <  TimeUtil.getNumberBetween(10, 15); i++) {
				TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
				boolean success = follow();
				if(!success) {
					System.out.println("Cant find anything to follow, skipping.");
					break;
				}
			}
			TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
			home();
			TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
			for (int j = 0; j < TimeUtil.getNumberBetween(1, 10); i++) {
				TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
				boolean success = discover();
				if(!success) {
					System.out.println("Cant find anything to discover, skipping.");
					break;
				}
			}
			TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Some erron in browsing. Going back to home");
		}
		home();
	}

	private void home() {
		driver.get("http://twiends.com/home");
	}

	private void login() {

		// wait.until(
		// ExpectedConditions.elementToBeClickable(By.id(getSignInXPath())))
		// .click();
		ArrayList<By> byList = new ArrayList<By>();
		byList.add(By.xpath("/html/body/form/div[2]/div/div[5]/div[2]/i"));
		byList.add(By.xpath("//*[@id='Main']/div[5]/div/div[2]/i"));
		TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		WebElement showSignInFormElement = WebDriverUtil.findElementBy(driver, byList, wait);

		showSignInFormElement.click();
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
				.elementToBeClickable(By.id("LoginEnter")));

		loginSubmitButton.click();
	}

	private boolean follow() {
		boolean status = WebDriverUtil.doClick(driver, getFollowButtonXPath(), wait);
		TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		if (driver.findElements(By.xpath(doneMessage())).size() != 0)  {
			JavascriptExecutor jsx = (JavascriptExecutor) driver;
			String elementId = "DoneMessage";
			String html =(String) jsx.executeScript("return document.getElementById('" + elementId + "').innerHTML;");
			if(StringUtil.notNullOrEmpty(html) && html.indexOf("Twitter has limited you") > -1) {
				System.out.println("Time to exit. " + html);
				status = false;
				System.exit(1);
			}
			System.out.println(html);
		}
		return status;
	}

	private String doneMessage() {
		return "/html/body/form/div[2]/div/div[7]/div[2]/div/span[2]";
	}

	private boolean discover() {
		return WebDriverUtil.doClick(driver, discoverButtonXPathList, wait);
	}

	private String getPasswordId() {
		return "LoginPassword";
	}

	private String getUsernameId() {
		return "LoginUsername";
	}


	public List getFollowButtonXPath() {

		return followButtonXPathList; // //*[@id="tb-like"]/a
	}

	



}
