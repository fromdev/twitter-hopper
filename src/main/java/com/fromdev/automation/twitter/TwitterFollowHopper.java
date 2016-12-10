package com.fromdev.automation.twitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.fromdev.automation.TimeUtil;
import com.fromdev.automation.WebDriverUtil;
import com.fromdev.automation.util.Cache;
import com.fromdev.automation.util.StringUtil;

/**
 * Hello world!
 * 
 */
public class TwitterFollowHopper extends AbstractHopper {

	private String[] searchTerms = { "@fromdev", "fromdev", "java", "jquery",
			"javascript", "hadoop", "lucene", "web design", "html", "css",
			"open source", "html5", "css3", "cdn", "nosql", "python", "ruby",
			"programming", "hacking", "developer", "coding", "php",
			"c programming", "linux", "ubuntu" };

	private Set<String> alreadyScreened = new HashSet<String>();
	private static Cache<String> usersCache = new Cache<String>();

	public static void main(String[] args) {
		TwitterFollowHopper app = new TwitterFollowHopper();
		//String[] usersArr = StringUtil.readRemoteFileAsStringArray("");
		//usersCache.cache().addAll(Arrays.asList(usersArr));
		app.init(args);
		app.execute();

	}

	public void execute() {
		try {
			driver.get("https://www.twitter.com");
			login();
			for (int i = 0; i < 100; i++) {
				driver.navigate().refresh();
				// followSuggested();
				// search();
				// retweet();
				// favorite();
				// driver.navigate().refresh();
				int count = TimeUtil.getNumberBetween(2, 5);
				for (int j = 0; j < count; j++) {
					driver.get("https://www.twitter.com/tech_career");// + usersCache.getRandomItem());
					// followSuggested();
					// followOnTrends();
					follow();
				}
			}

		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			// Check the title of the page
			System.out.println("Done all rounds " + driver.getTitle());
			try {
				logout();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			driver.close();
			System.out.println(alreadyScreened);
		}

	}

	private void login() {

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
		passElement.submit();
	}

	private void search() {

		TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		WebElement element = wait.until(ExpectedConditions
				.elementToBeClickable(By.id(getSearchInputId())));
		element.clear();
		element.sendKeys(getSearchTerm());

		TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		element.submit();
	}

	private void retweet() {
		try {
			Actions actions = new Actions(driver);
			TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
			int tweetId = 10;
			WebElement element = null;
			for (int i = 0; i < 20; i++) {
				try {
					tweetId = TimeUtil.getNumberBetween(3, 20);
					element = driver.findElement(By
							.xpath(getTweetContainerXPath(tweetId)));
				} catch (Exception e) {
					System.out.println("Tweet not found for id " + tweetId);
				}

			}
			String tweet = element.getText();
			System.out.println("Think to retweet: " + tweet);
			actions.moveToElement(element)
					.moveToElement(
							driver.findElement(By
									.xpath(getRetweetLinkXPath(tweetId))))
					.click();
			System.out.println("Retweeted");
		} catch (Exception e) {
			System.out.println("Retweet failed: " + e.getMessage());
		}
	}

	private void favorite() {
		try {
			Actions actions = new Actions(driver);
			TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
			int tweetId = 10;
			WebElement element = null;
			for (int i = 0; i < 20; i++) {
				try {
					tweetId = TimeUtil.getNumberBetween(3, 20);
					element = driver.findElement(By
							.xpath(getTweetContainerXPath(tweetId)));
				} catch (Exception e) {
					System.out.println("Tweet not found for id " + tweetId);
				}

			}
			String tweet = element.getText();
			System.out.println("Think to retweet: " + tweet);
			actions.moveToElement(element)
					.moveToElement(
							driver.findElement(By
									.xpath(getFavoriteXPath(tweetId)))).click();
			System.out.println("Favorited");
		} catch (Exception e) {
			System.out.println("Favorite failed: " + e.getMessage());
		}
	}

	private void follow() {

		try {
			TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);

			WebElement followButton = WebDriverUtil.findElement(driver,
					getProfileFollowXPath(), wait);

			System.out.println("Follow Button BG is: "
					+ followButton.getCssValue("background-color"));

			if (ensureFollowButtonAvailable(followButton)) {
				// Not following it yet so follow
				followButton.click();
				System.out.println("followed: ");
			} else {
				if (!mayFollowBack()
						&& ensureUnFollowButtonAvailable(followButton)) {
					// Not sure if we are already following it skip.
					System.out.println("unfollowed: " );
					followButton.click();
				} else {
					System.out.println("skipped: " );
				}
			}
			//alreadyScreened.add(element.getText());

		} catch (Exception e) {
			System.out.println("Follow failed: " + e.getMessage());
		} finally {
			TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
			
		}
	}

	private WebElement openUnfollowProfile() {
		TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		WebElement element = driver.findElement(By
				.xpath(getTweetAuthorXPath(findUnfollowProfileId())));
		element.click();
		System.out.println("Checking user profile: " + element.getText());
		return element;
	}

	private WebElement openAnyProfile() {
		TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		WebElement element = driver.findElement(By
				.xpath(getTweetAuthorXPath(findAvailableProfileId())));
		element.click();
		System.out.println("Checking user profile: " + element.getText());
		return element;
	}

	private void unfollow() {
		try {
			TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);

			driver.get("https://twitter.com/following");

			TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
			WebElement element = openUnfollowProfile();
			TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);

			WebElement unfollowButton = WebDriverUtil.findElement(driver,
					getProfileFollowXPath(), wait);

			System.out.println("Follow Button BG is: "
					+ unfollowButton.getCssValue("background-color"));

			if (ensureUnFollowButtonAvailable(unfollowButton)) {
				// Not following it yet so unfollow
				if (!mayFollowBack()) {
					unfollowButton.click();
					System.out.println("unfollowed: ");
				} else {
					System.out.println("skipped : may follow back");
				}
			} else {
				// Not sure if we are already following it skip.
				System.out.println("skipped: " + element.getText());
			}
			alreadyScreened.add(element.getText());

		} catch (Exception e) {
			System.out.println("UnFollow failed: " + e.getMessage());
		} finally {
			TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
			wait.until(
					ExpectedConditions.elementToBeClickable(By
							.xpath(getProfileDialogCloseButtonXPath())))
					.click();
		}
	}

	private int findUnfollowProfileId() {
		int tweetId = 10;
		for (int i = 2; i < 20; i++) {
			try {
				tweetId = TimeUtil.getNumberBetween(3, 20);
				WebElement element = wait.until(ExpectedConditions
						.elementToBeClickable(By
								.xpath(getTweetAuthorXPath(tweetId))));
				// driver.findElement(By.xpath(getTweetAuthorXPath(tweetId)));
				if (element != null
						&& (!alreadyScreened.contains(element.getText()))) {
					// Found a good profile to inspect
					break;
				}
				driver.navigate().refresh();
				TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
				scrollDown(tweetId * 50);

			} catch (Exception e) {
				System.out.println("Profile not found for id " + tweetId);
			}
		}
		return tweetId;

	}

	private int findAvailableProfileId() {
		int tweetId = 10;
		for (int i = 0; i < 20; i++) {
			try {
				tweetId = TimeUtil.getNumberBetween(3, 20);
				WebElement element = wait.until(ExpectedConditions
						.elementToBeClickable(By
								.xpath(getTweetAuthorXPath(tweetId))));
				// driver.findElement(By.xpath(getTweetAuthorXPath(tweetId)));
				if (element != null
						&& (!alreadyScreened.contains(element.getText()))) {
					// Found a good profile to inspect
					break;
				}
				driver.navigate().refresh();
				TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
			} catch (Exception e) {
				System.out.println("Tweet not found for id " + tweetId);
			}
		}
		return tweetId;

	}

	private void followSuggested() {
		TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		wait.until(
				ExpectedConditions.elementToBeClickable(By
						.xpath(getDiscoverLinkXPath()))).click();
		TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		wait.until(
				ExpectedConditions.elementToBeClickable(By
						.xpath(getFindFriendsXPath()))).click();
		TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		scrollDown(450);
		for (int i = 0; i < 5; i++) {

			follow();
			TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
			driver.navigate().refresh();
			scrollDown(450);
		}

	}

	private void scrollDown(int by) {
		// String filePath = "path\\to\\file\for\\upload";
		JavascriptExecutor jsx = (JavascriptExecutor) driver;
		// String elementId = "element-id";
		// String html =(String)
		// jsx.executeScript("return document.getElementById('" + elementId +
		// "').innerHTML;");
		// jsx.executeScript("document.getElementById('fileName').value='" +
		// filePath + "';");
		jsx.executeScript("window.scrollBy(0," + by + ")", "");

	}

	private void followOnTrends() {
		TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		int id = TimeUtil.getNumberBetween(2, 10);
		wait.until(
				ExpectedConditions.elementToBeClickable(By
						.xpath(getTrendsXPath(id)))).click();

		TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		WebElement element = driver.findElement(By
				.xpath(getTweetContainerXPath(id)));
		Actions actions = new Actions(driver);
		actions.moveToElement(element);

		scrollDown(450);
		for (int i = 0; i < 5; i++) {

			follow();
			TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
			driver.navigate().refresh();
			int n = TimeUtil.getNumberBetween(1, 20);
			scrollDown(n);
		}

	}

	private void logout() {
		wait.until(
				ExpectedConditions.elementToBeClickable(By
						.xpath(getUserDropdownXPath()))).click();
		TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		wait.until(
				ExpectedConditions.elementToBeClickable(By
						.xpath(getSignoutButtonXPath()))).click();

	}

	private String getUserDropdownXPath() {
		return "//*[@id='user-dropdown']";
	}

	private String getSignoutButtonXPath() {
		return "//*[@id='signout-button']";
	}

	private WebElement findUsefulTweetElement() {
		int tweetId = 10;
		WebElement element = null;
		for (int i = 0; i < 20; i++) {
			try {
				tweetId = TimeUtil.getNumberBetween(3, 20);
				element = driver.findElement(By
						.xpath(getTweetContainerXPath(tweetId)));
			} catch (Exception e) {
				System.out.println("Tweet not found for id " + tweetId);
			}
		}
		return element;
	}

	private boolean ensureFollowButtonAvailable(WebElement followButton) {

		String color = followButton.getCssValue("background-color");

		return "DDD".equalsIgnoreCase(color)

		|| "rgba(221, 221, 221, 1)".equalsIgnoreCase(color);
		// color is followable go for next condition.

	}

	private boolean ensureUnFollowButtonAvailable(WebElement followButton) {

		String color = followButton.getCssValue("background-color");
		Set<String> colors = new HashSet();
		colors.add("rgba(85, 172, 238, 1)");
		colors.add("rgba(1, 154, 210, 1)");
		return colors.contains(color);
	}

	private boolean mayFollowBack() {
		boolean status = false;
		try {
			String followerCount = wait.until(
					ExpectedConditions.elementToBeClickable(By
							.xpath(getFollowerCountXPath()))).getText();
			String followingCount = wait.until(
					ExpectedConditions.elementToBeClickable(By
							.xpath(getFollowingCountXPath()))).getText();
			int follower = Integer.parseInt(followerCount.replaceAll(",", ""));
			int following = Integer
					.parseInt(followingCount.replaceAll(",", ""));
			if (following > follower || ((following * 100) / follower) > 75) {
				status = true;
			} else {
				System.out.println("May not follow back:" + following
						+ ", followers:" + follower);

			}
		} catch (Exception e) {
			System.out.println("error reading follow stats: " + e.getMessage());
		}
		return status;

	}

	private String getPasswordId() {
		return "signin-password";
	}

	private String getUsernameId() {
		return "signin-email";
	}

	private String getSearchInputId() {
		return "search-query";
	}

	private String getSearchTerm() {
		return searchTerms[TimeUtil.getNumberBetween(0, searchTerms.length - 1)];
	}

	private String getTweetContainerXPath(int id) {
		// return "/html/body/div/div[2]/div/div[3]/div[2]/div[2]/ol/li[" + id +
		// "]/div/div";
		return "//*[@id='stream-items-id']/li[" + id + "]/div";
	}

	private String getRetweetLinkXPath(int id) {
		return "//*[@id='stream-items-id']/li[" + id
				+ "]/div/div/div[2]/ul/li[2]/a[1]/b";
	}

	private String getTweetAuthorXPath(int id) {
		// return "/html/body/div/div[2]/div/div[3]/div[2]/div[2]/ol/li[" + id
		// + "]/div/div/div/a/strong";
		return "//*[@id='stream-items-id']/li[" + id
				+ "]/div/div/div[1]/a/strong";

	}

	private List<String> getProfileFollowXPath() {
		// return
		// "/html/body/div[15]/div[2]/div/div[2]/div/div[2]/div/div/button";
		// /html/body/div[16]/div[2]/div[2]/div[2]/div/div[2]/div/div/div/button
		List<String> xPaths = new ArrayList();
		xPaths.add("/html/body/div[2]/div[2]/div/div[1]/div/div[2]/div/div/div[2]/div/div/ul/li[4]/div/div/button/span[1]/span");
		xPaths.add("//*[@id='profile_popup-body']/div[1]/div[2]/div/div/div/button");
		xPaths.add("//*[@id='profile_popup']/div[2]/div/div[2]/div[1]/div[2]/div/div/button");
		return xPaths;
	}

	private String getProfileDialogCloseButtonXPath() {
		return "//*[@id='profile_popup']/div[2]/div/button/span";
	}

	private String getDiscoverLinkXPath() {
		return "//*[@id='global-actions']/li[3]/a/span[2]";
	}

	private String getFindFriendsXPath() {
		return "//*[@id='page-container']/div[1]/div[1]/ul/li[4]/a";
	}

	private String getDiscoverFollowButtonXpath(int id) {
		return "//*[@id='stream-items-id']/li[" + id + "]/div/div[1]/button";
	}

	private String getFollowingCountXPath() {
		return "//*[@id='profile_popup']/div[2]/div/div[2]/div[1]/div[2]/div/ul/li[2]/a/strong";
	}

	private String getFollowerCountXPath() {
		return "//*[@id='profile_popup']/div[2]/div/div[2]/div[1]/div[2]/div/ul/li[3]/a/strong";
	}

	private String getHomeXPath() {
		return "//*[@id='global-nav-home']/a/span[2]";
	}

	private String getFollowingXPath() {
		return "//*[@id='page-container']/div[1]/div[1]/div[2]/ul/li[2]/a";
	}

	private String getTrendsXPath(int id) {
		// between 2 to 10
		return "//*[@id='page-container']/div[1]/div[3]/div/div/div[2]/ul/li["
				+ id + "]/a";
	}

	public String getFavoriteXPath(int id) {
		return "//*[@id='stream-items-id']/li[" + id
				+ "]/div/div/div[2]/ul/li[4]/a[1]/b";
	}

}
