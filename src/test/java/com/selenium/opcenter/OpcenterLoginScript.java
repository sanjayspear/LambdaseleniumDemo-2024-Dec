package com.selenium.opcenter;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class OpcenterLoginScript {
    WebDriver driver;

    @BeforeClass
    public void setUp() {
        // Set EdgeOptions to ignore SSL certificate errors
        EdgeOptions options = new EdgeOptions();
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);

        // Initialize WebDriver with EdgeOptions
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver(options);

        // Configure browser settings
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        // Open the login page
        driver.get("https://192.168.1.66/CamstarPortal/default.htm#/login");
    }

    @Test
    public void testFindAndInteractWithElementInNestedFrames() throws InterruptedException {
        // Perform login
        driver.findElement(By.xpath("//input[@aria-label='User Name']")).sendKeys("Camstaradmin");
        driver.findElement(By.xpath("//input[@aria-label='Password']")).sendKeys("Cam1star");
        driver.findElement(By.xpath("//button[@action='requestLogin']")).click();

        // Navigate to Inventory
        driver.findElement(By.xpath("(//div[text()='Inventory'])[2]")).click();
        Thread.sleep(10000); // Wait for Inventory options to load

        // Click on "Lot Start"
        driver.findElement(By.xpath("//a[text()='Lot Start']")).click();
        Thread.sleep(10000); // Wait for Lot Start to load

        // Define the locator of the target element to find in nested frames
        By targetLocator = By.xpath("//input[contains(@id, 'LotStart_ContainerName')]"); // Replace with your desired locator

        // Search for the element in nested frames and interact with it using FrameUtils
        boolean elementFound = FrameUtils.findElementInNestedFrames(driver, targetLocator, 0, "abc");

        if (elementFound) {
            System.out.println("Target element found and interaction completed successfully!");
        } else {
            System.out.println("Target element not found in any nested frames.");
        }
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

