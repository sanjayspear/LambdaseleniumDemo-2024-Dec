package com.selenium.locators;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Locators4 {
	WebDriver driver;

	@BeforeClass
	public void setUp() {
		driver = WebDriverManager.edgedriver().create();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void testSiblingChildParentTraverse() {
		driver.get("https://rahulshettyacademy.com/AutomationPractice/");

		// Sibling - Child to parent traverse
		String siblingButtonText = driver.findElement(By.xpath("//header/div/button[1]/following-sibling::button[1]"))
				.getText();
		System.out.println(siblingButtonText);

		String parentButtonText = driver.findElement(By.xpath("//header/div/button[1]/parent::div/button[2]"))
				.getText();
		System.out.println(parentButtonText);
	}

	@AfterClass
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
