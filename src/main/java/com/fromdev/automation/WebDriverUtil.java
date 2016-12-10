package com.fromdev.automation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Strings;

public class WebDriverUtil {

	public static void scrollDown(WebDriver driver, int by) {
		try {
			JavascriptExecutor jsx = (JavascriptExecutor) driver;
			jsx.executeScript("window.scrollBy(0," + by + ")", "");
		} catch (Exception e) {
			System.out.println("Scroll down failed " + by);
		}

	}

	public static boolean doClick(WebDriver driver, List elmentXpathList,
			WebDriverWait wait) {
		if (elmentXpathList != null && elmentXpathList.size() > 0) {
			for (Iterator iterator = elmentXpathList.iterator(); iterator
					.hasNext();) {
				String elementXpath = (String) iterator.next();
				if ((!Strings.isNullOrEmpty(elementXpath))
						&& doClick(driver, elementXpath, wait)) {
					return true;
				}
			}
		}
		return false;
	}

	public static void selectOption(WebDriver driver, WebElement select,
			String optionValue) {
		List<WebElement> options = select.findElements(By.tagName("option"));
		for (WebElement option : options) {
			if (option.getText().equals(optionValue)) {
				option.click();
				break;
			}
		}
	}

	public static boolean doClick(WebDriver driver, By by, WebDriverWait wait) {

		boolean status = false;
		try {
			WebElement element = findElement(driver, by, wait);
			if (element != null) {
				element.click();
				status = true;
			}
		} catch (Exception e) {
			System.out.println("Cant find elementXpath: " + by);
		}
		return status;
	}

	public static boolean doClick(WebDriver driver, String elementXpath,
			WebDriverWait wait) {

		boolean status = false;
		try {
			WebElement element = findElement(driver, elementXpath, wait);
			if (element != null) {
				element.click();
				status = true;
			}
		} catch (Exception e) {
			System.out.println("Cant find elementXpath: " + elementXpath);
		}
		return status;
	}

	public static WebElement findElementBy(WebDriver driver,
			ArrayList<By> elmentByList, WebDriverWait wait) {
		WebElement element = null;
		if (elmentByList != null && elmentByList.size() > 0) {
			for (Iterator<By> iterator = elmentByList.iterator(); iterator
					.hasNext();) {
				By elementBy = iterator.next();
				element = findElement(driver, elementBy, wait);
				if (element != null) {
					break;
				}
			}
		}
		return element;
	}

	public static WebElement findElement(WebDriver driver,
			List elmentXpathList, WebDriverWait wait) {
		WebElement element = null;
		if (elmentXpathList != null && elmentXpathList.size() > 0) {
			for (Iterator iterator = elmentXpathList.iterator(); iterator
					.hasNext();) {
				String elementXpath = (String) iterator.next();
				if ((!Strings.isNullOrEmpty(elementXpath))) {
					element = findElement(driver, elementXpath, wait);
				}
				if (element != null) {
					break;
				}
			}
		}
		return element;
	}

	public static WebElement findElement(WebDriver driver, String elementXpath,
			WebDriverWait wait) {
		return findElement(driver, By.xpath(elementXpath), wait);
	}

	public static WebElement findElement(WebDriver driver, By by,
			WebDriverWait wait) {
		WebElement element = null;
		element = findElementClickable(driver, by, wait);
		if (element == null) {
			System.out.println("Clickable was null so trying visibility " + by);
			element = findElementVisible(driver, by, wait);
		}
		return element;
	}

	private static WebElement findElementClickable(WebDriver driver, By by,
			WebDriverWait wait) {
		WebElement element = null;
		if (driver.findElements(by).size() != 0) {
			try {
				element = wait.until(ExpectedConditions
						.elementToBeClickable(by));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return element;
	}

	public static WebElement findElementVisible(WebDriver driver, By by,
			WebDriverWait wait) {
		WebElement element = null;
		if (driver.findElements(by).size() != 0) {
			try {
				element = wait.until(ExpectedConditions
						.visibilityOfElementLocated(by));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return element;
	}
	
	public static void selectValue(WebElement elm, String value) {
		try {
			elm.sendKeys(value);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Select select = new Select(elm);
			select.selectByValue(value);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
