package com.fromdev.automation.twitter;

import java.util.HashSet;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fromdev.automation.TimeUtil;

/**
 * Hello world!
 * 
 */
public class BloggerRedirectHopper {
	private static final String baseUrl = "https://www.blogger.com/";
	private String uname = "kzvikzvi1@gmail.com";
	private String pass = "pass";
	private String blogId = "637499528247154499";
	private int existingRedirectCount = 10;
	private WebDriver driver = new FirefoxDriver();
	Actions actions = new Actions(driver);
	private WebDriverWait wait = new WebDriverWait(driver, 30);

	private Set<String> alreadyScreened = new HashSet<String>();

	public static void main(String[] args) {

		BloggerRedirectHopper app = new BloggerRedirectHopper();

		app.initConfig(args);
		app.execute();

	}

	public void execute() {
		try {

			driver.get(BloggerRedirectHopper.baseUrl);
			login();
			TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
			redirect();

		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			// Check the title of the page
			System.out.println("Done all rounds " + driver.getTitle());

			driver.close();
			System.out.println(alreadyScreened);
		}

	}

	private void openBlogSettings() throws InterruptedException {
		driver.get(baseUrl + "blogger.g?blogID=" + blogId + "#searchsettings");
		Thread.sleep(4000);
	}


	private void login() {

		// System.out.println("Opening login page");
		// driver.findElement(By.xpath("//*[@id='Email']")).sendKeys(
		// uname);
		// driver.findElement(By.xpath("//*[@id='Passwd']")).sendKeys(prop.getProperty("blogger.user.password"));
		// Thread.sleep(2000);
		// driver.findElement(By.xpath("//*[@id='signIn']")).click();
		// System.out.println("Hit login button");
		// Thread.sleep(3000);

		// wait.until(
		// ExpectedConditions.elementToBeClickable(By.id(getSignInXPath())))
		// .click();
		TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		// Find the text input element by its name
		WebElement uNameElement = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//*[@id='Email']")));
		// driver.findElement(By.id("signin-email"));
		// Enter something to search for
		TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		uNameElement.sendKeys(uname);

		WebElement passElement = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//*[@id='Passwd']")));
		// driver.findElement(By.id("signin-password"));
		TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		passElement.sendKeys(pass);

		// Now submit the form. WebDriver will find the form for us from the
		// element
		TimeUtil.sleep(TimeUtil.SLEEP_TYPE.TYPING);
		passElement.submit();
	}

	public void redirect() throws Exception {
		try {

			openBlogSettings();
			String[] errorUrlList =

			{
					"/2008/08/what-are-steps-involved-in-establishing.html"	,
					"/feeds/455848126338947339/comments/default"	,
					"/2014/04/top-mysql-books-developers.html"	,
					"/2012/11/B"	,
					"/2014/03/best"	,
					"/python-tutorials-re..."	,
					"/2014/04/akamai-vs-incapsula-cdn-comparison.html"	,
					"/2008/08/hacking-by-reflection-"	,
					"/2012/05/15-awesome-open-source-j"	,
					"/2013/10/c-programming-tuto"	,
					"/2009/07/playing-with-java-str_obj-trim-basics.html"	,
					"/2013/10/Best-"	,
					"/2014/04/best-free-jquery-tutorials-ebooks"	,
					"/2008/09/javasqlsqlexception-ora-00911-invalid.html2014-5-3NativeException"	,
					"/2012/01/25-best-free-eclipse-plug-ins-for-java.htmlEclipseoffersanintegrateddevelopmentenvironmenthavinganextensibleplug-insystem.ThisenablesEclipsetoprovideallfunctionalityonthetopofitsrun-timesystem.It"	,
					"/2011/08/11-most-influential-"	,
					"/2010/12/interview-questions-hadoop-mapreduce"	,
					"/2013/10/Best-Python"	,
					"/2012/09/Free"	,
					"/2014/03/p"	,
					"/2012/09/Free-Open"	,
					"/2012/08/Best-Open-Source"	,
					"/2011/04/5-best-core-%3Cb%3Ejava%3C/b%3E-%3Cb%3Ebooks%3C/b%3E-you-must-read-as.html"	,
					"/2012/04/8-best-software-testing-"	,
					"/2013/11/s"	,
					"/2013/07/g"	,
					"/2010/12/interview-questions-hadoop-mapreduce.htmlA"	,
					"/2012/07/b"	,
					"/2012/05/t"	,
					"/2012/05/1"	,
					"/2013/10/best-"	,
					"/2012/08/Best-HTML5-Books-For-Beginne"	,
					"/2013/02/Hacking"	,
					"/2012/06/how-to-send-auto-reply"	,
					"/2014/04/best-mindmap-tools-brain-storming.html"	,
					"/feeds/5270924511190370971/comments/default"	,
					"/2012/09/Free-Open-Source-Java-Ch"	,
					"/2012/09/Best-Parental-Control-and-Monitoring"	,
					"/2014/01/Best-Free-Photo-Editing-Apps"	,
					"/2013/02/Android"	,
					"/2013/10/Best"	,
					"/2008/12/debugging-java-on-unixlinux-my-favorite.htmlLinuxoperatingsystemisverypowerfulandifweknowthecorrecttoolstodebugitsmucheasierthenanyotheroperatingsystem.Sometimesitgetsverydifficulttoinvestigateissuesonproductionsystemwherewecanuseaverylittledebuggingtools.ThistutorialcontainsfewgoodoperatingsystemcommandstobeusedwhiledebuggingJavaprocessesonLinux/Unixenvironment.lsofCommand-CheckforopenfiledescriptorsThiscommandliststheopenfiledescriptorsbyaprocessonunixoperatingsystem.Hereisshortdescriptionfrommanoutputoflsof.Anopenfilemaybearegularfile"	,
					"/2010/08/10-jdbc-questions-for-java-beginners.htmlJavaDatabaseConnectivityAPIcontainscommonlyaskedJavainterviewquestions.AgoodunderstandingofJDBCAPIisrequiredtounderstandandleveragemanypowerfulfeaturesofJavatechnology.HerearefewimportantpracticalquestionsandanswerswhichcanbeaskedinaCoreJavaJDBCinterview.MostofthejavadevelopersarerequiredtouseJDBCAPIinsometypeofapplication.Thoughitsreallycommon"	,
					"/2011/06/content-delivery-network-alternative.htmlList"	,
					"/2010/08/10-jdbc-questions-for-ja"	,
					"/2011/05/10"	,
					"/2014/03/python-tutorials"	,
					"/2008/09/javasqlsqlexception-ora-00911-invalid.html2014-4-9java.sql.SQLException"	,
					"/2012/01/25-best-free-eclipse-plug-ins-for-java.html-27%20Best%20Free%20Eclipse%20Plug"	,
					"/2013/05/Speed-Up-Eclipse.htmlSelf"	,
					"/2012/03/7-excellent-open"	,
					"/2012/06/15-java-serialization-interview"	,
					"/2012/08/Best-HTML5"	,
					"/2013/08/Liferay-Portal-Question"	,
					"/2012/01/25-best-free-eclipse-plug-ins-for-java.htmAn"	,
					"/2012/05/top-7-open-source-free-p"	,
					"/2011/06/create-cdn-content-delivery-network.htmlList"	,
					"/2013/08/Liferay-Portal-Question-Answer.htmlLiferayisoneofthemostpopularJSRcompliantopensourceportal.Thiscanbeusedtodrivemultiplewebsiteswithvarietyoffeaturesandlookandfeel.LiferayisdevelopedinJavaandthemajorportletdevelopmentonitisalsosupportedbyJavaandfewotherlanguages.Thelatestreleasesofliferayhasbeenreallyfeaturerichandseenaincreasingdemandinliferaydevelopersinrecentpast.Managingmultiplewebsitesusingsingleliferayserverinstallationisoneofthemainreasonofliferaygainingpopularity.Liferayjobsarereallyhighpayingjobsduetobeingspecializedinportletsegmentofjavawebapplicationdevelopment.Therearemanyotherportalsavailablewithfreeandpaidoptions.TobehighlyvaluabledeveloperyoumustfocusyourknowledgetotheJSRwayofportletdevelopment"	,
					"/2011/07/opensource-web-application-firewall-waf"	,
					"/2012/09/Best-Parental-Control"	,
					"/2013/09/Best-Programming-Languages-Web"	,
					"/2008/05/java-threading-questions.htmlQuestion"	,
					"/2010/06/5-best"	,
					"/2013/10/c-programming-tutorials"	,
					"/2008/05/java-collections-questions.htmlJavaCollectionsFrameworkcontainsmostcommonlyaskedJavainterviewquestions.AgoodunderstandingofCollectionsframeworkisrequiredtounderstandandleveragemanypowerfulfeaturesofJavatechnology.JavaCollectionsframeworkisfundamentalutilitytoolsprovidedbyJavathatarenotonlytopicofinterviewbutalsoheavilyusedinjavaprogrammingonalltypesofprogramsincludingwebbasedanddesktopapplications.YourknowledgeofJavawillbeconsideredincompletewithoutagoodworkingexperienceofcollectionsAPI"	,
					"/2012/08/Best-HTML5-Books-For-Beginners"	,
					"/2012/06/15-java-serialization-interview.htmlA"	,
					"/2011/07/opensource-web-applicati"	,
					"/2012/10/learn-java.htmlWant"	,
					"/2012/08/Best-Open-Source-Web-Based-File-Explorer"	,
					"/2012/08/Best-Open-Source-Web"	,
					"/JavaDeveloper"	,
					"/2012/06/15-java-serialization-interview.htmlDefine"	,
					"/2012/09/Free-Open-Source-Java-Charting-Library.htmlBest"	,
					"/2013/02/iPad-Book-Reader"	,
					"/2008/05/java-collections-questions.html1"	,
					"/2012/09/Java-Path-Classpath-Questions-Answers.htmlI"	,
					"/2012/05/top-7-open-source-free-php-ide-for"	,
					"/2011/04/5-best-core-java-books-you-must-read-as.htmlHP"	,
					"/2013/07/interview-questions-book.htmltarget="	,
					"/2011/12/10"	,
					"/2011/04/5-best-core-java-books-you-must"	,
					"/2013/09/Best-Programming"	,
					"/2013/10/c-programming"	,
					"/2013/10/c-programming-tutorials.html2014-3-19learn"	,
					"/2008/05/java-collections-quest"	,
					"/2013/09/Best-Programming-Languages-Web-Development"	,
					"/2013/10/best"	,
					"/2008/05/java-collections-questions.html2014-3-1Java"	,
					"/2013/08/how-to-be-hacker.htmlhttp://www.fromdev.com/2013/08/how-to-be-hacker.html"	,
					"/2013/10/3D-Game-Programming"	,
					"/2013/02/iPad-Book-Reader.html2014-2-23best"	,
					"/2008/12/debugging-java-on-unixlinux-my-favorite.htmlList"	,
					"/2008/07/struts-2"	,
					"/2014/01/Best"	,
					"/2012/08/Best-Open-Source-Web-Based"	,
					"/2012/04/creating-weak-learner-with-decision.html2014-2-7Decision"	,
					"/2012/05/top-7-open-source-free-php-ide-for.htmlBest"	,
					"/2013/07/best-jenkins"	,
					"/2013/06/jquery"	,
					"/2012/01/25-best-free-eclipse-plu"	,
					"/2014/01/Best-YouTube-Downloader-Software.ht"	,
					"/2012/03/7-excellent-open-source-enterprise.html2014-2-7Open"	,
					"/feeds/posts/default/-/"	,
					"/%E2%80%9D]FromDev:"	,
					"/2009/07/playing-with-java-?m=1"	,
					"/2013/08/how-to-be-hacker.htmlhttp://www.fromdev.com/2013/08/how-to-be-hacker.html?m=1"	,
					"/2012/08/Best-Open-Source-Web-Based-File-Explorer?m=1"	,
					"/2012/03/7-excellent-open-source-enterprise.htmlAnEnterpriseServiceBus?m=1"	,
					"/8691100/fromdev_large_rec_premium_ATF_336x280_bsazone_1278323?m=1"	,
					"/2012/05/15-awesome-open-source-javascript.html-Drill-Down%20Example?m=1"	,
					"/2012/01/25-best-free-eclipse-plug-ins-for-java?m=1"	,
					"/2010/12/interview?m=1"	,
					"/8691100/fromdev_leaderboard_BTF_728x90_bsazone_1278541?m=1"	,
					"/2013/09/Best-Programming-Languages-Web-Development?m=1"	,
					"/2012/09/Best?m=1"	,
					"/robots.txt?m=1"	,
					"/8691100/fromdev_button_BTF_125x125_bsazone_1275075?m=1"	,
					"/2010/08/10-jdbc-questions-for-java-beginners.htmlQuestion?m=1"};
			/*
			 * after 10 redirects are added every redired input xpath is at tr
			 * 10 This may change after another redesign of blogger.
			 */
			int start = 10;
			try {
				if (existingRedirectCount < 10) {
					start = existingRedirectCount;
				}
			} catch (Exception e) {
			}

			for (int i = 0; i < errorUrlList.length; i++) {
				try {
					// Loop starts here
					driver.findElement(
							By.xpath("/html/body/div[3]/div[4]/div/div/div[2]/div[2]/div[2]/div[6]/div/div[2]/table/tbody/tr[2]/td[2]/div/span/a"))
							.click();
					Thread.sleep(TimeUtil.getTypingTimeSeconds());
					driver.findElement(
							By.xpath("/html/body/div[3]/div[4]/div/div/div[2]/div[2]/div[2]/div[6]/div/div[2]/table/tbody/tr[2]/td[2]/div/div/div[1]/div[1]/div[1]/button[1]"))
							.click();
					Thread.sleep(TimeUtil.getTypingTimeSeconds());
					String errorUrlInputXPath = "/html/body/div[3]/div[4]/div/div/div[2]/div[2]/div[2]/div[6]/div/div[2]/table/tbody/tr[2]/td[2]/div/div/div[1]/div[2]/table/tbody[1]/tr["
							+ start + "]/td[2]/div/span[2]/span/input";
					driver.findElement(By.xpath(errorUrlInputXPath)).sendKeys(
							errorUrlList[i]);
					Thread.sleep(TimeUtil.getTypingTimeSeconds());
					String redirectUrlInputXPath = "/html/body/div[3]/div[4]/div/div/div[2]/div[2]/div[2]/div[6]/div/div[2]/table/tbody/tr[2]/td[2]/div/div/div[1]/div[2]/table/tbody[1]/tr["
							+ start + "]/td[2]/div/span[5]/span/input";
					driver.findElement(By.xpath(redirectUrlInputXPath))
							.sendKeys("/");
					Thread.sleep(TimeUtil.getTypingTimeSeconds());
					String permanetCheckboxXPath = "/html/body/div[3]/div[4]/div/div/div[2]/div[2]/div[2]/div[6]/div/div[2]/table/tbody/tr[2]/td[2]/div/div/div[1]/div[2]/table/tbody[1]/tr["
							+ start + "]/td[2]/div/span[8]/span/input";
					if (!driver.findElement(By.xpath(permanetCheckboxXPath))
							.isSelected()) {
						driver.findElement(By.xpath(permanetCheckboxXPath))
								.click();
					}
					Thread.sleep(TimeUtil.getTypingTimeSeconds());
					String localSaveButtonXPath = "/html/body/div[3]/div[4]/div/div/div[2]/div[2]/div[2]/div[6]/div/div[2]/table/tbody/tr[2]/td[2]/div/div/div[1]/div[2]/table/tbody[1]/tr["
							+ start + "]/td[2]/div/span[10]/span/div/a[1]";
					driver.findElement(By.xpath(localSaveButtonXPath)).click();
					Thread.sleep(TimeUtil.getTypingTimeSeconds());
					driver.findElement(
							By.xpath("/html/body/div[3]/div[4]/div/div/div[2]/div[2]/div[2]/div[6]/div/div[2]/table/tbody/tr[2]/td[2]/div/div/div[2]/button[1]"))
							.click();
					start++;
					if (start > 10) {
						start = 10;
					}
					Thread.sleep(TimeUtil.getTypingTimeSeconds() * 15);
				} catch (Exception e) {
					System.out.println("Last Failed URL " + errorUrlList[i]);
					throw e;

				}
			}
			System.out.println("Successfully Completed this round");
			// *[@id='signIn']

			/*
			 * driver.get(baseUrl +
			 * "/rma.htm?_flowExecutionKey=_c81136274-639B-9405-F8E2-380B87D14744_k95A78D42-64A1-417D-A116-297BA18D40A1"
			 * ); driver.findElement(By.id("manual_ps3_slim")).click();
			 * driver.findElement(By.id("terms")).click();
			 * driver.findElement(By.cssSelector("img")).click(); new
			 * Select(driver.findElement
			 * (By.id("modelNum"))).selectByVisibleText
			 * ("CECH-2501B (PS3 320GB)");
			 * driver.findElement(By.id("txtSerial")).clear();
			 * driver.findElement(By.id("txtSerial")).sendKeys("cd334243223");
			 * driver.findElement(By.id("proofOfPurchase1")).click();
			 * driver.findElement(By.cssSelector("a.continue > span")).click();
			 */
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
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
		return "pass";
	}

	private String getUsernameId() {
		// "//*[@id="user"]"
		return "user";
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

	private void initConfig(String[] args) {
		// Properties prop = new Properties();
		// prop.load(this.getClass().getClassLoader()
		// .getResourceAsStream("config.properties"));
		// pass = prop.getProperty("user.password");
		// uname = prop.getProperty("user.name");
		if (args != null && args.length > 0) {
			uname = args[0];
			if (args.length > 1) {
				pass = args[1];
				if (args.length > 2) {
					blogId = args[2];
					if (args.length > 3) {
						existingRedirectCount = Integer.parseInt(args[3]);
					}
				}
			}
		}
	}

}
