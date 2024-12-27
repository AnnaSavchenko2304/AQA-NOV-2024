package org.prog.cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.prog.cucumber.steps.GoogleSteps;
import org.prog.cucumber.steps.SQLSteps;
import org.prog.selenium.WebDriverFactory;
import org.prog.selenium.pages.GooglePage;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

@CucumberOptions(
        glue = "org.prog.cucumber.steps",
        features = "src/test/resources/features",
        plugin = {"pretty",
                "json:target/cucumber-reports/Cucumber.json",
                "html:target/cucumber-report.html",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        }
)
public class CucumberRunner extends AbstractTestNGCucumberTests {

    private WebDriver driver;
    private Connection connection;

    @BeforeSuite
    public void setUp() throws SQLException, MalformedURLException {
        String env = System.getProperty("testEnv");

        String dbHost;

        if ("local".equals(env) || "selenoid".equals(env)) {
            dbHost = "jdbc:mysql://localhost:3306/db";
        } else {
            dbHost = "jdbc:mysql://mysql-db-1:3306/db";
        }

        connection = DriverManager.getConnection(dbHost, "user", "password");
        driver = WebDriverFactory.getDriver();

        GoogleSteps.googlePage = new GooglePage(driver);
        SQLSteps.connection = connection;
    }

    @SneakyThrows
    @AfterSuite
    public void tearDown() {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
        if (driver != null) {
            driver.quit();
        }
    }

    private ChromeOptions remoteChrome() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setAcceptInsecureCerts(true);
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.setCapability("selenoid:options", new HashMap<String, Object>() {{
            put("enableVideo", true);
            put("enableVNC", true);
        }});
        return chromeOptions;
    }
}
