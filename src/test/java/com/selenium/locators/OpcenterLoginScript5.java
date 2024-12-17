package com.selenium.locators;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class OpcenterLoginScript5 {
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

        // Search for the element in nested frames and interact with it
        boolean elementFound = findElementInNestedFrames(driver, targetLocator, 0);

        if (elementFound) {
            System.out.println("Target element found and interaction completed successfully!");
        } else {
            System.out.println("Target element not found in any nested frames.");
        }
    }

    /**
     * Recursively searches for a user-provided element within all frames and nested frames.
     * Switches to the frame containing the element and performs an interaction.
     *
     * @param driver  WebDriver instance
     * @param locator Locator of the target element to find
     * @param depth   Current depth of the frame hierarchy
     * @return true if the element is found and interacted with, false otherwise
     */
    public static boolean findElementInNestedFrames(WebDriver driver, By locator, int depth) {
        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
        System.out.println("Depth " + depth + ": Found " + iframes.size() + " iframe(s).");

        for (WebElement iframe : iframes) {
            try {
                // Print the frame ID or name (if available)
                String frameName = iframe.getAttribute("name");
                String frameId = iframe.getAttribute("id");
                System.out.println("  Entering Frame: " + (frameName != null ? frameName : "Unnamed")
                        + " | ID: " + (frameId != null ? frameId : "No ID"));

                // Switch to the current iframe
                driver.switchTo().frame(iframe);

                // Wait until the target element is visible and interactable
                WebDriverWait wait = new WebDriverWait(driver, 10);
                try {
                    WebElement targetElement = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                    if (targetElement != null && targetElement.isDisplayed() && targetElement.isEnabled()) {
                        System.out.println("Target element found in Frame: " +
                                (frameName != null ? frameName : "Unnamed") +
                                " | ID: " + (frameId != null ? frameId : "No ID"));

                        // Perform the interaction with the element (e.g., enter "abc")
                        targetElement.clear(); // Optional: Clear the input field first
                        targetElement.sendKeys("abc");

                        // If the standard interaction fails, use JavaScriptExecutor as a fallback
                        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                        jsExecutor.executeScript("arguments[0].value='abc';", targetElement);

                        return true; // Stop searching as the interaction is complete
                    }
                } catch (Exception e) {
                    // Element not found in the current frame, continue
                    System.out.println("Target element not interactable in this frame.");
                }

                // Recursively search in nested frames
                boolean foundInChildFrame = findElementInNestedFrames(driver, locator, depth + 1);
                if (foundInChildFrame) {
                    return true; // Stop searching if found in child frames
                }

                // Switch back to the parent frame
                driver.switchTo().parentFrame();
            } catch (Exception e) {
                System.out.println("  Error accessing frame: " + e.getMessage());
            }
        }
        return false; // Element not found in this frame or its child frames
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
