package com.handleStaleElement;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Demonstrates three ways to handle StaleElementReferenceException in Selenium.
 * Uses Cheteshwar Pujara's retirement article from ESPN Cricinfo as a tribute.
 */
public class Browser {

    public static void main(String[] args) {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();

        driver.get("https://www.espncricinfo.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Uncomment any method to test different approaches
        handleUsingExplicitWait(driver, wait);
        // handleByRefetchingElement(driver);
        // handleUsingRetry(driver);

        driver.quit();
    }

    /**
     * Approach 1: Re-fetch the element after catching the stale exception.
     */
    public static void handleByRefetchingElement(WebDriver driver) {
        By pujiDefence = By.xpath("(//a[contains(@href,'pujara-retires')])[1]");

        try {
            WebElement pujaraStale = driver.findElement(pujiDefence);
            driver.navigate().refresh();
            pujaraStale.click(); // This will throw StaleElementReferenceException
        } catch (StaleElementReferenceException e) {
            System.out.println("Caught StaleElementReferenceException: " + e.getMessage());

            WebElement freshElement = driver.findElement(pujiDefence);
            freshElement.click();
            System.out.println("Clicked fresh element after catching stale.");
        }
    }

    /**
     * Approach 2: Retry clicking the element in a loop until successful.
     */
    public static void handleUsingRetry(WebDriver driver) {
        By pujiDefence = By.xpath("(//a[contains(@href,'pujara-retires')])[1]");
        driver.navigate().refresh();

        while (true) {
            try {
                WebElement pujaraElement = driver.findElement(pujiDefence);
                pujaraElement.click();
                System.out.println(" Clicked element using retry loop.");
                break;
            } catch (StaleElementReferenceException e) {
                System.out.println("Retrying due to stale element...");
            }
        }
    }

    /**
     * Approach 3: Use explicit wait to detect staleness and re-locate the element.
     */
    public static void handleUsingExplicitWait(WebDriver driver, WebDriverWait wait) {
        By pujiDefence = By.xpath("(//a[contains(@href,'pujara-retires')])[1]");
        WebElement pujaraElement = driver.findElement(pujiDefence);

        driver.navigate().refresh();
        wait.until(ExpectedConditions.stalenessOf(pujaraElement));

        pujaraElement = driver.findElement(pujiDefence);
        pujaraElement.click();
        System.out.println("Clicked element using explicit wait.");
    }
}
