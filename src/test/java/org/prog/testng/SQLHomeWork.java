package org.prog.testng;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class SQLHomeWork {

    private Connection connection;
    private WebDriver driver;

    @SneakyThrows
    @BeforeSuite
    public void setUp() {
        // Set up the database connection
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "your_user", "your_password");

        // Set up WebDriver
        System.setProperty("webdriver.chrome.driver", "path_to_chromedriver");
        driver = new ChromeDriver();
    }

    @SneakyThrows
    @Test
    public void fetchAndSavePhones() {
        // Step 1: Open allo.ua
        driver.get("https://allo.ua");

        // Step 2: Search for "iphone"
        WebElement searchBox = driver.findElement(By.name("search"));
        searchBox.sendKeys("iphone");
        searchBox.submit();

        // Wait for search results to load
        Thread.sleep(5000);

        // Step 3: Get the first 3 items
        List<WebElement> items = driver.findElements(By.cssSelector(".product-card"));
        int itemCount = Math.min(items.size(), 3);

        String insertStatement = "INSERT INTO Phones (PhoneName, GoodsId) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);

        for (int i = 0; i < itemCount; i++) {
            WebElement item = items.get(i);

            // Step 4: Extract name and GoodsId
            String name = item.findElement(By.cssSelector(".product-card__title")).getText().trim();
            String goodsId = item.findElement(By.cssSelector(".product-card__code"))
                    .getText().replace("Код товара:", "").trim();

            // Step 5: Save to database
            if (!isPhoneInDatabase(name, goodsId)) {
                try {
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, goodsId);
                    preparedStatement.execute();
                    System.out.println("Inserted: " + name + " with GoodsId: " + goodsId);
                } catch (Exception e) {
                    System.err.println("Failed to save phone: " + name + " with GoodsId: " + goodsId);
                }
            } else {
                System.out.println("Already exists: " + name + " with GoodsId: " + goodsId);
            }
        }
    }

    @SneakyThrows
    @Test
    public void readRandomPhone() {
        String selectStatement = "SELECT * FROM Phones ORDER BY RAND() LIMIT 1";
        PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString("PhoneName") + " (GoodsId: " + resultSet.getString("GoodsId") + ")");
        }
    }

    @SneakyThrows
    @AfterSuite
    public void tearDown() {
        // Close the database connection
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }

        // Quit the WebDriver
        if (driver != null) {
            driver.quit();
        }
    }

    @SneakyThrows
    private boolean isPhoneInDatabase(String name, String goodsId) {
        String checkQuery = "SELECT COUNT(*) FROM Phones WHERE PhoneName = ? AND GoodsId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(checkQuery);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, goodsId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1) > 0;
    }
}
