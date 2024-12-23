package org.prog.testng;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.prog.selenium.pages.GooglePage;
import org.prog.selenium.pages.WikiPage;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GoogleTestNG {
    private WebDriver driver;
    private GooglePage googlePage;
    private WikiPage wikiPage;

    @BeforeSuite
    public void setUp() {
        driver = new ChromeDriver();
        googlePage = new GooglePage(driver);
        wikiPage = new WikiPage(driver);
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(dataProvider = "celebrityNames")
    public void testGoogleSearchClassic(String celebrityName) {
        googlePage.loadPageAndAcceptCookiesIfPresent();
        googlePage.setSearchInputText(celebrityName);
        googlePage.executeSearch();

        long count = googlePage.getSearchHeaders().stream()
                .filter(header -> header.getText().contains(celebrityName))
                .count();

        Assert.assertTrue(count > 3,
                "Expected celebrity search results to be more than 3");
    }

    @Test(dataProvider = "celebrityNames")
    public void testGoogleSearchLucky(String celebrityName) {
        googlePage.loadPageAndAcceptCookiesIfPresent();
        googlePage.setSearchInputText(celebrityName);
        googlePage.feelingLucky();
        Assert.assertTrue(wikiPage.isPageLoaded(),
                "Expected to be at wikipedia, but was" + driver.getCurrentUrl());
    }

    @DataProvider
    public Object[][] celebrityNames() {
        return new Object[][]{
                {"Ben Affleck"},
                {"Margot Robbie"},
                {"Ryan Reynolds"},
                {"McDonald's"}
        };
    }
}
