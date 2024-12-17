package com.selenium.opcenter;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

public class FrameUtils {

    /**
     * Recursively searches for a user-provided element within all frames and nested frames.
     * Switches to the frame containing the element and performs an interaction.
     *
     * @param driver  WebDriver instance
     * @param locator Locator of the target element to find
     * @param depth   Current depth of the frame hierarchy
     * @param input   Input string to send to the element if found
     * @return true if the element is found and interacted with, false otherwise
     */
    public static boolean findElementInNestedFrames(WebDriver driver, By locator, int depth, String input) {
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

                        // Perform the interaction with the element
                        targetElement.clear(); // Optional: Clear the input field first
                        targetElement.sendKeys(input);

                        // If the standard interaction fails, use JavaScriptExecutor as a fallback
                        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                        jsExecutor.executeScript("arguments[0].value='" + input + "';", targetElement);

                        return true; // Stop searching as the interaction is complete
                    }
                } catch (Exception e) {
                    // Element not found in the current frame, continue
                    System.out.println("Target element not interactable in this frame.");
                }

                // Recursively search in nested frames
                boolean foundInChildFrame = findElementInNestedFrames(driver, locator, depth + 1, input);
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
}

