package com.fromdev.automation.twitter;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fromdev.automation.TimeUtil;
import com.fromdev.automation.WebDriverUtil;
import com.fromdev.automation.feed.model.ShareableItem;
import com.fromdev.automation.util.FeedCache;
import com.fromdev.automation.util.FeedReader;
import com.fromdev.automation.util.StringUtil;

/**
 * Hello world!
 * 
 */
public class StumbleHopper extends AbstractHopper {
	//private String uname = "priyanka.trivedi.trivedi@gmail.com";
	//private String pass = "test1234";
	DesiredCapabilities capabilities = DesiredCapabilities.firefox();
	private WebDriver driver =   new MarionetteDriver(capabilities);

	Actions actions = new Actions(driver);
	private WebDriverWait wait = new WebDriverWait(driver, 30);

	private Set<String> alreadyScreened = new HashSet<String>();

	public static void main(String[] args) {
		System.setProperty("webdriver.gecko.driver","./lib/geckodriver");
		StumbleHopper app = new StumbleHopper();

		app.init(args);
		app.execute();

	}

	public void execute() {
		ExecutorService pool = Executors.newFixedThreadPool(1);
		try {

			pool.submit(new FeedReader());

			driver.get("https://www.stumbleupon.com/login");
			login();
			TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
			//add();
			for (int i = 0; i < 1000; i++) {
				browse(i);
			}

		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			// Check the title of the page
			System.out.println("Done all rounds " + driver.getTitle());
			try {
				pool.shutdown();
				// logout();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			driver.close();
			System.out.println(alreadyScreened);
		}

	}

	private void browse(int i) {
		try {
			nextPage();
			WebDriverUtil.scrollDown(driver,
					TimeUtil.getNumberBetween(10, 100));

			TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
			
			if (TimeUtil.getNumberBetween(1, 25) % 17 == 0) {
				unlike();
				System.out.println("I dont like this page " + i);
			} else if (TimeUtil.getNumberBetween(1, 25) % 9 == 0) {
				add();
			} else {
				TimeUtil.sleep(TimeUtil.SLEEP_TYPE.READING);
				like();
			}
			// add();
			TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Some erron in browsing. Going back to home");
			home();
		}
	}

	private void home() {
		driver.get("https://www.stumbleupon.com");
	}
	private void login() {
		 wait.until(
		 ExpectedConditions.elementToBeClickable(By.xpath(getLoginButton())))
		 .click();
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
		passElement.submit();
	}

	private void nextPage() {
		home();
	}

	private void like() {
		ArrayList<By> byList = new ArrayList<By>();
		byList.add(By.className("fa-thumbs-up"));
		byList.add(By
				.xpath(getLikeButtonXPath()));
		WebElement elm = WebDriverUtil.findElementBy(driver, byList, wait);
		if(elm!=null) {
			System.out.println("found like button");
			elm.click();
		} else {
			System.out.println("cant find like button");
		}
	}

	private void unlike() {
		ArrayList<By> byList = new ArrayList<By>();
		byList.add(By.className("fa-thumbs-down"));
		byList.add(By
				.xpath(getUnlikeButtonXPath()));
		WebElement elm = WebDriverUtil.findElementBy(driver, byList, wait);
		if(elm!=null) {
			System.out.println("found unlike button");
			elm.click();
		} else {
			System.out.println("cant find unlike button");
		}
	}

	private void add() throws Exception {

		// wait.until(
		// ExpectedConditions.elementToBeClickable(By
		// .xpath(getSettingsXPath()))).click();
		// TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		//
		// WebElement frame = wait.until(
		// ExpectedConditions.elementToBeClickable(By
		// .id(getiFrameId())));
		// driver.switchTo().frame(frame);
		// wait.until(
		// ExpectedConditions.elementToBeClickable(By
		// .xpath(getAddPageLinkXPath()))).click();
		//
		

		ShareableItem item = null;
		if (FeedCache.cache().size() > 0) {
			item = FeedCache.getRandomItem();
			if (item != null) {
				String url = StringUtil.getRedirectedUrl(item.getUrl());
				System.out.println("Adding : " + url);
				String submitURL = "http://www.stumbleupon.com/submit" + "?nsfw=0&url=" + URLEncoder.encode(url,"UTF-8") + "&title=" + URLEncoder.encode(item.getDescription(),"UTF-8"); 
				driver.get(submitURL );
				TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
				driver.get("http://www.stumbleupon.com");
			}
		} else {
			System.out.println("No cache available yet");
		}

	}

	private void selectValue(WebElement elm) {
		try {
			elm.sendKeys("Internet");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Select select = new Select(elm);
			select.selectByValue("Internet");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private String getAddPageUrlId() {
		return "url";
	}

	private String getSafeForWorkId() {
		return "safe";
	}

	private String getCategoryId() {
		return "tags";
	}

	private String getUserTagsId() {
		return "user-tags";
	}


	private String getPasswordId() {
		return "password-login";
	}

	private String getUsernameId() {
		// "//*[@id="user"]"
		return "email-username";
	}

	private String getLoginButton() {
		return "/html/body/div[1]/div[1]/div[1]/div[2]/div";
	}

	// Stumble
	public String getSignInXPath() {
		return "//*[@id='header-top']/div/a[2]/strong";
	}

	public String getStumbleButtonXPath() {
		return "//*//*[@id='tb-stumble']/a";
	}

	public String getLikeButtonXPath() {
		return "//*[@id='tb-like']/a"; // //*[@id="tb-like"]/a
	}

	public String getUnlikeButtonXPath() {
		return "//*[@id='tb-notlike']/a";
	}

}
