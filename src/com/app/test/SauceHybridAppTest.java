package com.app.test;

import org.testng.annotations.Test;


public class SauceHybridAppTest extends SauceBaseTest {

	@Test
	public void login() throws InterruptedException{
		Thread.sleep(20000);
		System.out.println("Hybrid App test");
	}
}
