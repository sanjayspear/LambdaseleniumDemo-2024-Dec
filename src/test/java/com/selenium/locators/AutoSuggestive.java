package com.selenium.locators;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AutoSuggestive {
	WebDriver driver;

	@BeforeClass
	public void setUp() {
		driver = WebDriverManager.edgedriver().create();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/dropdownsPractise/");
	}

	@Test
	public void testAutoSuggestiveDropDown() throws InterruptedException {
		driver.findElement(By.id("autosuggest")).sendKeys("Ind");
		List<WebElement> options = driver.findElements(By.xpath("//li[@class='ui-menu-item']"));

		for (WebElement option : options) {
			String country = option.getText();

			if (country.equalsIgnoreCase("India")) {
				option.click();
			}
		}

		Thread.sleep(2000);

	}

	@AfterClass
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
