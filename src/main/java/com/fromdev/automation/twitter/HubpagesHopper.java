package com.fromdev.automation.twitter;

import java.util.HashSet;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fromdev.automation.TimeUtil;
import com.fromdev.automation.WebDriverUtil;

/**
 * Hello world!
 * 
 */
public class HubpagesHopper extends AbstractHopper {

	private Set<String> alreadyScreened = new HashSet<String>();

	public static void main(String[] args) {

		HubpagesHopper app = new HubpagesHopper();

		app.init(args);
		app.execute();

	}

	public void execute() {
		try {

			driver.get("https://hubpages.com/signin/");
			login();
			TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
			for (int i = 0; i < 1000; i++) {
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
			nextPage();
			WebDriverUtil
					.scrollDown(driver, TimeUtil.getNumberBetween(10, 100));

			TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
			if (TimeUtil.getNumberBetween(1, 25) % 7 == 0) {
				System.out.println("Doing nothing and goint to next page " + i);
			} else if (TimeUtil.getNumberBetween(1, 25) % 9 == 0) {
				if (canFollow()) {
					unlike();
					System.out.println("I dont like this page " + i);
				}
			} else {
				like();
				TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
				follow();
				System.out.println("I like this page " + i);
			}
			TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Some erron in browsing. Going back to home");
			home();
		}
	}

	private void home() {
		driver.get("http://hubpages.com/feed/");
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
		
		wait.until(
				ExpectedConditions.elementToBeClickable(By
						.xpath(getSignInButtonXPath()))).click();

	}

	private void nextPage() {
		try {
			wait.until(
					ExpectedConditions.elementToBeClickable(By
							.xpath(getHopButtonXPath()))).click();
		} catch (Exception e) {
			// Cant find hop button try the url
			driver.get("http://hubpages.com/hop/classic");
		}

	}

	private void like() {
		wait.until(
				ExpectedConditions.elementToBeClickable(By
						.xpath(getLikeButtonXPath()))).click();
	}

	private void unlike() {
		wait.until(
				ExpectedConditions.elementToBeClickable(By
						.xpath(getUnlikeButtonXPath()))).click();
	}

	private void follow() {
		try {
			if (canFollow()) {
				// we can follow this guy
				wait.until(
						ExpectedConditions.elementToBeClickable(By
								.xpath(getFollowButtonXPath()))).click();
			} else {
				System.out.println("already following");
			}
		} catch (Exception e) {
			System.out.println("Follow failed " + e.getMessage());
		}
	}

	private boolean canFollow() {
		WebElement followSpan = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath(getFollowButtonTextXPath())));
		String txt = followSpan.getText();
		if (txt.toLowerCase().indexOf("unfollow") == -1) {
			return true;
		}
		return false;
	}

	private String getExploreMenuXPath() {
		return "//*[@id='explore_span']";
	}

	private String getHubsLinkXPath() {
		return "//*[@id='explore_menu']/li[1]/a";
	}

	private String getPasswordId() {
		return "us1sisma2";
	}

	private String getUsernameId() {
		// "//*[@id="user"]"
		return "us1shem2";
	}

	// Stumble
	public String getSignInXPath() {
		return "//*[@id='header-top']/div/a[2]/strong";
	}
	
	public String getSignInButtonXPath() {
		return "//*[@id='signin_button']";
	}

	public String getSignoutXPath() {
		return "//*[@id='user_menu']/li[6]/a";
	}

	public String getHopButtonXPath() {
		return "//*[@id='hop_hop']";
	}

	public String getLikeButtonXPath() {
		return "//*[@id='hop_up']"; // //*[@id="tb-like"]/a
	}

	public String getUnlikeButtonXPath() {
		return "//*[@id='hop_down']";
	}

	public String getFollowButtonXPath() {
		return "//*[@id='hop_hubcontainer']/div[12]/div[3]/h2/a";
	}

	public String getFollowButtonTextXPath() {
		return "//*[@id='hop_hubcontainer']/div[12]/div[3]/h2/a/span[2]";
	}

}
