package com.selenium.locators;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class UpdatedDropdown {
	WebDriver driver;
	
	@BeforeClass
	public void setUp() {
		driver = WebDriverManager.edgedriver().create();
		driver.get("https://rahulshettyacademy.com/dropdownsPractise/");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void tohandleStaticDD() throws InterruptedException {
		WebElement PassengersDropDown = driver.findElement(By.id("divpaxinfo"));
		PassengersDropDown.click();
		
		int i=1;
		while(i<5) {
			 WebElement selectNumberOfPassangers = driver.findElement(By.id("hrefIncAdt"));
			 selectNumberOfPassangers.click();
			Thread.sleep(1000);
			i++;
		}
		
		WebElement doneBtn = driver.findElement(By.id("btnclosepaxoption"));
		doneBtn.click();
		
		Thread.sleep(1000);
	}
	
	@AfterClass
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
