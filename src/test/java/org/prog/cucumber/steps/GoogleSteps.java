package org.prog.cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.prog.selenium.pages.GooglePage;
import org.testng.Assert;

public class GoogleSteps {

    public static GooglePage googlePage;

    @Given("I load google page")
    public void loadGooglePage() {
        googlePage.loadPage();
    }

    @Given("I accept Google cookies if present")
    public void acceptCookiesIfPresent() {
        if (googlePage.isCookiePresent()) {
            googlePage.acceptCookies();
        }
    }

    @When("I search for random person")
    public void searchForRandomPerson() {
        googlePage.setSearchInputText(SQLSteps.RANDOM_PERSON);
        googlePage.executeSearch();
    }

    @Then("I can see at least {int} search results")
    public void validateGoogleSearchResultsCount(int amount) {
        long count = googlePage.getSearchHeaders().stream()
                .filter(header -> header.getText().contains(SQLSteps.RANDOM_PERSON))
                .count();

        Assert.assertTrue(count >= amount,
                "Expected celebrity search results to be more than 3");
    }
}
