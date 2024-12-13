package com.selenium.locators;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CheckboxTest {
	WebDriver driver;
	@BeforeClass
	public void setUp() {
		driver = WebDriverManager.edgedriver().create();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/dropdownsPractise/");
	}

	@Test
	public void testSiblingChildParentTraverse() throws InterruptedException {
		WebElement SeniorCitizenCB = driver.findElement(By.cssSelector("input[id*='SeniorCitizenDiscount']"));
		SeniorCitizenCB.click();
		boolean flag = SeniorCitizenCB.isSelected();
		Assert.assertEquals(true, flag);
		Thread.sleep(3000);
		int numberOfCB = driver.findElements(By.cssSelector("input[type='checkbox']")).size();
		System.out.println("Number of checkboxes are: "+(numberOfCB - 1));
	}

	@AfterClass
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
