package com.fromdev.automation.twitter;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LinkChecker {

	private WebDriver driver = new FirefoxDriver();
	private WebDriverWait wait = new WebDriverWait(driver, 30);

	public static void main(String[] args) {
		LinkChecker lc = new LinkChecker();
		lc.execute();
	}

	public void execute() {

		driver.get("http://www.fromdev.com/2013/07/Hacking-Tutorials.html");
		checkAllLinks(findAllLinks());
		driver.close();

	}

	public Set<WebElement> findAllLinks() {
		List<WebElement> link = driver.findElements(By.tagName("a"));
		Set<WebElement> uniqueLinks = new HashSet<WebElement>();

		for (WebElement ele : link) {
			String href = ele.getAttribute("href");
			if (!canSkip(href)) {
				// System.out.println(ele.getText() + "href:" + href);
				uniqueLinks.add(ele);
			}
		}
		System.out.println("found " + uniqueLinks.size());
		return uniqueLinks;
	}

	public void checkAllLinks(Set<WebElement> uniqueLinks) {
		for (Iterator iterator = uniqueLinks.iterator(); iterator.hasNext();) {
			WebElement webElement = (WebElement) iterator.next();
			String href = webElement.getAttribute("href");
			boolean isLive = isLive(href);
			if (isLive) {
				System.out.println(href + " : " + isLive);
			} else {
				System.out.println(href + " : ***BROKEN*** " + isLive);
			}

		}
	}

	public boolean canSkip(String url) {
		if (url == null || "".equals(url)) {
			return true;
		}
		url = url.trim().toLowerCase();
		if ((url.indexOf("http://www.fromdev.com") > -1)
				|| (url.indexOf("plus.google.com") > -1)
				|| (url.indexOf("buysellads.com") > -1)
				|| (url.indexOf("www.blogger.com") > -1)
				|| (url.indexOf("stats.buysellads.com") > -1)
				|| (url.indexOf("www.facebook.com") > -1)
				|| (url.indexOf("www.linkedin.com") > -1)
				|| (url.indexOf("www.pinterest.com") > -1)
				|| (url.indexOf("feeds.feedburner.com") > -1)
				|| (url.indexOf("javabynataraj.blogspot.in") > -1)
				|| (url.indexOf("www.mkyong.com") > -1)
				|| (url.indexOf("bp.blogspot.com") > -1)
				|| (url.indexOf("translate.google.com") > -1)
				|| (url.indexOf("feedproxy.google.com") > -1)

		) {
			return true;
		}
		return false;
	}

	private static boolean isLive(String link) {

		HttpURLConnection urlconn = null;
		int res = -1;
		String msg = null;
		try {

			URL url = new URL(link);
			urlconn = (HttpURLConnection) url.openConnection();
			urlconn.setConnectTimeout(10000);
			urlconn.setRequestMethod("GET");
			urlconn.connect();
			String redirlink = urlconn.getHeaderField("Location");
			System.out.println(urlconn.getHeaderFields());
			if (redirlink != null && !url.toExternalForm().equals(redirlink))
				return false;
			else
				return urlconn.getResponseCode() == HttpURLConnection.HTTP_OK;

		} catch (Exception e) {

			System.out.println(e.getMessage());
			return false;

		} finally {

			if (urlconn != null)
				urlconn.disconnect();

		}

	}

}
