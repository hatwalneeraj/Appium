package com.app.test;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class SauceWebTest extends SauceBaseTest {

	@Test
	public void Signup() throws InterruptedException {
		driver.get("https://www.foodpanda.in/");
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.presenceOfElementLocated(By.id("button")));
		Thread.sleep(2000);
		findElement("SearchButton").click();
		findElement("SearchField").sendKeys("Gurgaon");
		findElement("Suggestions").click();
		findElement("OrderArea").sendKeys("Sector 21(Gurgaon)");
		findElement("SubmitSearch").click();
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.elementToBeClickable(findElement("Button")));
		findElement("Button").click();
	}
}