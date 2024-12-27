package org.prog.selenium;

import lombok.SneakyThrows;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class WebDriverFactory {
    private final static String ENV = System.getProperty("testEnv", "");
    private final static String DRIVER_TYPE = System.getProperty("driverType", "");

    @SneakyThrows
    public static WebDriver getDriver() {
        if ("local".equals(ENV)) {
            return getLocalDriver();
        } else {
            return getRemoteDriver();
        }
    }

    private static WebDriver getLocalDriver() {
        switch (DRIVER_TYPE) {
            case "safari":
                return new SafariDriver();
            case "firefox":
                return new FirefoxDriver();
            default:
                return new ChromeDriver();
        }
    }

    private static WebDriver getRemoteDriver() throws MalformedURLException {
        String selenoidHost;
        if ("selenoid".equals(ENV)) {
            selenoidHost = "http://localhost:4444/wd/hub";
        } else {
            selenoidHost = "http://selenoid-selenoid-1:4444/wd/hub";
        }

        Capabilities capabilities;
        switch (DRIVER_TYPE) {
            case "safari":
                capabilities = new SafariOptions();
                break;
            case "firefox":
                capabilities = new FirefoxOptions();
                break;
            default:
                capabilities = new ChromeOptions();
        }

        return new RemoteWebDriver(new URL(selenoidHost), capabilities);
    }
}
