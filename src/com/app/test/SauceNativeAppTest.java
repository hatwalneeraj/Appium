package com.app.test;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SauceNativeAppTest extends SauceBaseTest {

	@Test
	public void testSelendroid() throws InterruptedException {
		findElement("Button").click();
		WebElement textElement = findElement("TextField");
		String msg = "This will end the activity";
		Assert.assertEquals(msg, textElement.getText());

		System.out.println(textElement.getText());
		List<WebElement> buttonList = findElements("Button");
		for (WebElement button : buttonList) {
			System.out.println(button.getText());
		}
		buttonList.get(1).click();
		Thread.sleep(2000);
		findElement("Button").click();
		buttonList.get(0).click();
	}
}
