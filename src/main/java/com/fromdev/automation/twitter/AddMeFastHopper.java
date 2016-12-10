package com.fromdev.automation.twitter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fromdev.automation.TimeUtil;
import com.fromdev.automation.WebDriverUtil;

/**
 * Hello world!
 * 
 */
public class AddMeFastHopper extends AbstractHopper {
	private static final String homeURL = "http://addmefast.com";
	private String fbpass = "p0k0s1ng";
	private WebDriver driver = new FirefoxDriver();
	Actions actions = new Actions(driver);
	private WebDriverWait wait = new WebDriverWait(driver, 30);
	private String mainWindowHandle;

	private Set<String> alreadyScreened = new HashSet<String>();

	public static void main(String[] args) {

		AddMeFastHopper app = new AddMeFastHopper();

		app.init(args);
		app.execute();

	}

	public void execute() {
		ExecutorService pool = Executors.newFixedThreadPool(1);
		try {
			loginToFacebook();
			driver.get(AddMeFastHopper.homeURL);
			login();
			TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
			mainWindowHandle = driver.getWindowHandle();
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
			fbLikesPage();
			TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
			like();
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
		driver.get(AddMeFastHopper.homeURL);
	}

	private void loginToFacebook() {
		driver.get("https://www.facebook.com");
		TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		WebDriverUtil.findElement(driver, By.id("email"), wait).sendKeys(uname);
		WebElement passElement = WebDriverUtil.findElement(driver,
				By.id("pass"), wait);
		passElement.sendKeys(fbpass);
		TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		passElement.submit();
	}

	private void login() {

		// wait.until(
		// ExpectedConditions.elementToBeClickable(By.id(getSignInXPath())))
		// .click();
		TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		// Find the text input element by its name
		WebElement uNameElement = wait.until(ExpectedConditions
				.elementToBeClickable(By.className("email")));
		// driver.findElement(By.id("signin-email"));
		// Enter something to search for
		TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		uNameElement.sendKeys(uname);

		WebElement passElement = wait.until(ExpectedConditions
				.elementToBeClickable(By.className("password")));
		// driver.findElement(By.id("signin-password"));
		TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		passElement.sendKeys(pass);

		// Now submit the form. WebDriver will find the form for us from the
		// element
		TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		ArrayList<By> elmentByList = new ArrayList<By>();
		elmentByList.add(By.name("login_button"));
		elmentByList.add(By
				.xpath("/html/body/div/section[2]/div/form/ul/li[3]/input"));
		WebElement submitButton = WebDriverUtil.findElementBy(driver,
				elmentByList, wait);
		submitButton.click();
	}

	private void fbLikesPage() {
		driver.get("http://addmefast.com/free_points/facebook_likes");
	}

	private void like() {
		ArrayList<By> byList = new ArrayList<By>();
		byList.add(By
				.xpath("/html/body/div[3]/div[5]/div/div/div/div[2]/div/div/div[2]/form/div/div[2]/div/div/div/div/div/center[2]/a"));
		byList.add(By.className("single_like_button btn3-wrap"));
		WebElement elm = WebDriverUtil.findElementBy(driver, byList, wait);
		if (elm != null) {
			System.out.println("found launch like page button");
			elm.click();
		} else {
			System.out.println("cant find launch like page button");
		}

		Set<String> winHandles = driver.getWindowHandles();
		winHandles.remove(mainWindowHandle);
		System.out.println("mainWindowHandle " + mainWindowHandle);
		System.out.println("winHandles " + winHandles);
		TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		for (String winHandle : winHandles) {
			try {
				driver.switchTo().window(winHandle);
				TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
				ArrayList<By> byListLike = new ArrayList<By>();
				byListLike
						.add(By.xpath("/html/body/div/div[2]/div[1]/div/div[2]/div[2]/div[2]/div/div/div/div/div[1]/div[2]/div/div/div[2]/div[2]/div/span/button"));
				byListLike.add(By
						.xpath("//*[@id='pagesHeaderLikeButton']/button"));
				byListLike.add(By.id("pagesHeaderLikeButton"));
				WebElement likeBtn = WebDriverUtil.findElementBy(driver,
						byListLike, wait);
				if (likeBtn != null) {
					System.out.println("found like button in new window");
					likeBtn.click();
				} else {
					System.out.println("Cant find like button in new window");
				}
				TimeUtil.sleep(TimeUtil.SLEEP_TYPE.READING);

				driver.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				driver.switchTo().window(mainWindowHandle);
			}
			System.out.println("closed new window");
		}

	}

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
