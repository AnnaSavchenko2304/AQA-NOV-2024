package org.prog.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.prog.selenium.pages.GooglePage;
import org.prog.selenium.pages.WikiPage;

import java.util.List;

public class SeleniumDemo {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        GooglePage googlePage = new GooglePage(driver);
        WikiPage wikiPage = new WikiPage(driver);
        wikiPage.loadPage();

        try {
            executeGoogleSearch(googlePage, "Margot Robbie");
            executeGoogleSearch(googlePage, "Ryan Reynolds");
            executeGoogleSearch(googlePage, "McDonald's");

            luckySearch(googlePage, "Ben Affleck");
            wikiPage.isPageLoaded();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    public static void executeGoogleSearch(GooglePage googlePage, String celebrityName) {
        googlePage.loadPageAndAcceptCookiesIfPresent();
        googlePage.setSearchInputText(celebrityName);
        googlePage.executeSearch();
        List<WebElement> searchHeaders = googlePage.getSearchHeaders();

        int count = 0;
        for (WebElement webElement : searchHeaders) {
            if (webElement.getText().contains(celebrityName)) {
                count++;
            }
        }

        if (count > 3) {
            System.out.println(celebrityName + " found!");
        } else {
            throw new RuntimeException(celebrityName + " not found!");
        }
    }

    public static void luckySearch(GooglePage googlePage, String celebrityName) {
        googlePage.loadPageAndAcceptCookiesIfPresent();
        googlePage.setSearchInputText(celebrityName);
        googlePage.feelingLucky();
    }
}
