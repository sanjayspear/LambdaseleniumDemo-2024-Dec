package com.selenium.locators;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Locators3 {
	WebDriver driver;
    String name = "rahul";

    @BeforeClass
    public void setUp() {
        // Set up the WebDriver for Chrome
		driver = WebDriverManager.firefoxdriver().create();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void loginTest() throws InterruptedException {
        // Retrieve password and navigate to the login page
        String password = getPassword(driver);
        driver.get("https://rahulshettyacademy.com/locatorspractice/");

        // Perform login actions
        driver.findElement(By.id("inputUsername")).sendKeys(name);
        driver.findElement(By.name("inputPassword")).sendKeys(password);
        driver.findElement(By.className("signInBtn")).click();

        // Validate successful login
        Thread.sleep(2000); // Optional, replace with WebDriverWait in real tests
        String loginMessage = driver.findElement(By.tagName("p")).getText();
        Assert.assertEquals(loginMessage, "You are successfully logged in.");

        String greetingMessage = driver.findElement(By.cssSelector("div[class='login-container'] h2")).getText();
        Assert.assertEquals(greetingMessage, "Hello " + name + ",");
        
        // Perform logout
        driver.findElement(By.xpath("//*[text()='Log Out']")).click();
    }

    public String getPassword(WebDriver driver) throws InterruptedException {
        // Navigate to the Forgot Password page
        driver.get("https://rahulshettyacademy.com/locatorspractice/");
        driver.findElement(By.linkText("Forgot your password?")).click();
        Thread.sleep(1000); // Optional, replace with WebDriverWait in real tests

        // Request password reset
        driver.findElement(By.cssSelector(".reset-pwd-btn")).click();

        // Extract the password from the text
        String passwordText = driver.findElement(By.cssSelector("form p")).getText();
        String[] passwordArray = passwordText.split("'");

        // Return the password
        return passwordArray[1].split("'")[0];
    }

    @AfterClass
    public void tearDown() {
        // Close the browser
        driver.close();
    }
}
