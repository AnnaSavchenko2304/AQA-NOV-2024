package org.prog.testng;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class AlloUaTestNG {

    private WebDriver driver;

    @BeforeClass
    public void setup() {
        // Initialize the WebDriver before the tests
        driver = new ChromeDriver();
    }

    @AfterClass
    public void teardown() {
        // Close the WebDriver after the tests
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testCheckProductIdAtPosition9() {
        driver.get("https://allo.ua/");

        // Search for a product (example: "iphone")
        WebElement searchInput = driver.findElement(By.id("search-form__input"));
        searchInput.sendKeys("iphone");
        searchInput.sendKeys(Keys.ENTER);

        // Wait for the results to load (at least 10 products)
        List<WebElement> searchResults =
                new WebDriverWait(driver, Duration.ofSeconds(30))
                        .until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className("products-layout__item"), 9));

        int X = 9; // Product position you want to check

        // Replace exceptions with assertions to check if X is within bounds
        Assert.assertTrue(X >= 0, "Position X cannot be less than 0.");
        Assert.assertTrue(X < searchResults.size(), "Position X exceeds the available products.");

        // If X is valid, get the product at position X
        WebElement product = searchResults.get(X);

        // Scroll to the product
        new Actions(driver).scrollToElement(product).perform();

        // Retrieve the product ID (assuming product ID is in 'data-product-sku')
        String productId = product.getAttribute("data-product-sku"); // Modify this if needed based on the actual HTML structure

        // If product ID is found, print it
        if (productId != null && !productId.isEmpty()) {
            System.out.println("Product ID at position " + X + ": " + productId);
        } else {
            System.out.println("Product ID at position " + X + " is null or empty.");
        }
    }
}

