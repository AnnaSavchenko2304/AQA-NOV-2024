package org.prog.cucumber.steps;

import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import org.prog.selenium.dto.ResultsDto;

public class RestSteps {

    @SneakyThrows
    @Given("I request {int} random people from random user service")
    public void requestRandomPeople(int amount) {
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.queryParam("inc", "gender,name,nat");
        requestSpecification.queryParam("noinfo");
        requestSpecification.queryParam("results", amount);
        requestSpecification.baseUri("https://randomuser.me/");
        requestSpecification.basePath("/api/");

        Response response = requestSpecification.get();
        response.prettyPrint();

        ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(200);
        SQLSteps.RANDOM_PERSONS.addAll(response.as(ResultsDto.class).getResults());
    }
}
