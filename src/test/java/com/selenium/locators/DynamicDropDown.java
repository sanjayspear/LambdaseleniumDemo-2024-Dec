package com.selenium.locators;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DynamicDropDown {
	WebDriver driver;
	@BeforeClass
	public void setUp() {
		driver = WebDriverManager.edgedriver().create();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void testSiblingChildParentTraverse() throws InterruptedException {
		driver.get("https://rahulshettyacademy.com/dropdownsPractise/");
		WebElement fromDropDown = driver.findElement(By.id("ctl00_mainContent_ddl_originStation1_CTXTaction"));
		fromDropDown.click();
		
		Actions action = new Actions(driver);
		WebElement fromCity = driver.findElement(By.xpath("//a[text()=' Bengaluru (BLR)']"));
		action.moveToElement(fromCity).click().build().perform();
		Thread.sleep(3000);
		
		WebElement toDropDown = driver.findElement(By.id("ctl00_mainContent_ddl_destinationStation1_CTXT"));
		toDropDown.click();
		
		WebElement toCity = driver.findElement(By.xpath("//div[@id='glsctl00_mainContent_ddl_destinationStation1_CTNR']//a[text()=' Delhi (DEL)']"));
		action.moveToElement(toCity).click().build().perform();
		Thread.sleep(3000);		
	}

	@AfterClass
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
