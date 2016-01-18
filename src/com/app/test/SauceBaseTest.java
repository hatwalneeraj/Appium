package com.app.test;

import io.appium.java_client.android.AndroidDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class SauceBaseTest {
	public WebDriver driver;
	private DesiredCapabilities capabilities;
	private Properties properties;
	Map<String, String> locatorMap;
	Map<String, String> inputMap;

	@BeforeTest
	public void setUp() throws IOException {
		File file = new File("resources/sauceConfig.properties");
		FileInputStream fileInput = new FileInputStream(file);
		properties = new Properties();
		properties.load(fileInput);
		capabilities = new DesiredCapabilities();
		capabilities.setCapability("name", "SauceTest");
		capabilities.setCapability("username",
				properties.getProperty("sauce.username"));
		capabilities.setCapability("accessKey",
				properties.getProperty("sauce.accesskey"));
		capabilities.setCapability("device",
				properties.getProperty("sauce.device"));
		capabilities.setCapability("deviceName",
				properties.getProperty("android.deviceName"));
		capabilities.setCapability(CapabilityType.VERSION,
				properties.getProperty("android.version"));
		capabilities.setCapability(CapabilityType.PLATFORM,
				properties.getProperty("capability.platform"));
		if ("Web".endsWith(properties.getProperty("sauce.testType"))) {
			getLocators("Web");
			// getInput("Web");
			// capabilities.setCapability("platformName", "Android");
			capabilities.setCapability(CapabilityType.BROWSER_NAME,
					properties.getProperty("capability.browser"));
		} else if ("HybridApp".equals(properties.getProperty("sauce.testType"))) {
			getLocators("Hybrid");
			// getInput("Hybrid");
			capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
			capabilities.setCapability("app",
					properties.getProperty("sauce.gmApkPath"));
			capabilities.setCapability("app-package",
					properties.getProperty("sauce.gmApkPackage"));
			capabilities.setCapability("app-activity",
					properties.getProperty("sauce.gmApkActivity"));
		} else if ("NativeApp".equals(properties.getProperty("sauce.testType"))) {
			getLocators("Native");
			// getInput("Native");
			capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
			capabilities.setCapability("app",
					properties.getProperty("sauce.testApkPath"));
			capabilities.setCapability("app-package",
					properties.getProperty("sauce.testApkPackage"));
			capabilities.setCapability("app-activity",
					properties.getProperty("sauce.testApkActivity"));
		}
		driver = new AndroidDriver(
				new URL(properties.getProperty("sauce.Url")), capabilities);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public void getLocators(String sheetName) throws IOException {
		locatorMap = new HashMap<String, String>();
		FileInputStream inputStream = new FileInputStream(
				"resources/locators.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = workbook.getSheet(sheetName);
		for (Row row : sheet) {
			locatorMap.put(row.getCell(0).getStringCellValue(), row.getCell(1)
					.getStringCellValue());
		}
	}

	public void getInput(String sheetName) throws IOException {
		inputMap = new HashMap<String, String>();
		FileInputStream inputStream = new FileInputStream(
				"resources/locators.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = workbook.getSheet(sheetName);
		for (Row row : sheet) {
			inputMap.put(row.getCell(0).getStringCellValue(), row.getCell(2)
					.getStringCellValue());
		}
	}

	public WebElement findElement(String elementName) {
		WebElement element = null;
		String[] keyValue = locatorMap.get(elementName).split("=", 2);
		String type = keyValue[0];
		String value = keyValue[1];

		if ("id".equals(type)) {
			element = driver.findElement(By.id(value));
		} else if ("class".equals(type)) {
			element = driver.findElement(By.className(value));
		} else if ("xpath".equals(type)) {
			element = driver.findElement(By.xpath(value));
		} else if ("text".equals(type)) {
			element = driver.findElement(By.linkText(value));
		} else if ("name".equals(type)) {
			element = driver.findElement(By.name(value));
		} else if ("css".equals(type)) {
			element = driver.findElement(By.cssSelector(value));
		}
		return element;
	}

	public List<WebElement> findElements(String elementName) {
		List<WebElement> elements = new ArrayList<WebElement>();
		String[] keyValue = locatorMap.get(elementName).split("=", 2);
		String type = keyValue[0];
		String value = keyValue[1];

		if ("id".equals(type)) {
			elements = driver.findElements(By.id(value));
		} else if ("class".equals(type)) {
			elements = driver.findElements(By.className(value));
		} else if ("xpath".equals(type)) {
			elements = driver.findElements(By.xpath(value));
		} else if ("text".equals(type)) {
			elements = driver.findElements(By.linkText(value));
		} else if ("name".equals(type)) {
			elements = driver.findElements(By.name(value));
		}
		return elements;
	}

	@AfterTest
	public void tearDown() {
		if ("Web".endsWith(properties.getProperty("sauce.testType"))) {
			driver.close();
		}
		driver.quit();
	}
}
