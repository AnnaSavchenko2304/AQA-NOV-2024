package org.prog.testng;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class AlloUaTestNG {
    private WebDriver driver;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
    }

    @AfterClass
    public void teardown() {
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

        // Check that there is at least one product at position 9 (10th product)
        if (searchResults.size() > 9) {
            // Get the product at position 9
            WebElement product = searchResults.get(9);

            // Retrieve the product ID (assuming the ID is in the 'data-product-sku' attribute)
            String productId = product.getAttribute("data-product-sku");

            // Print only the product ID if it is not null or empty
            if (productId != null && !productId.isEmpty()) {
                System.out.println("Product ID at position 9: " + productId);
            } else {
                System.out.println("Product ID at position 9 is null or empty.");
            }
        } else {
            System.out.println("Product at position 9 does not exist.");
        }
    }
}


