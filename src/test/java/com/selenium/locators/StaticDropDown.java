package com.selenium.locators;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StaticDropDown {
	WebDriver driver;

	@Test
	public void tohandleStaticDD() {
		driver = WebDriverManager.firefoxdriver().create();
		driver.get("https://rahulshettyacademy.com/dropdownsPractise/");

		WebElement staticDD = driver.findElement(By.id("ctl00_mainContent_DropDownListCurrency"));
		Select dropdown = new Select(staticDD);

		dropdown.selectByIndex(0);
		dropdown.selectByVisibleText("USD");
		String option = dropdown.getFirstSelectedOption().getText();
		System.out.println("The first selected option is " + option);
		dropdown.selectByValue("AED");
		driver.quit();
	}
}
