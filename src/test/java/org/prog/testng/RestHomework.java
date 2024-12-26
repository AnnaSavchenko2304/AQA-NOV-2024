package org.prog.testng;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RestHomework {

    @Test
    public void testLocationQueryParam() {
        // Imposta i parametri della richiesta
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.queryParam("inc", "location");
        requestSpecification.baseUri("https://randomuser.me/");
        requestSpecification.basePath("/api/");

        // Esegui la richiesta GET
        Response response = requestSpecification.get();
        response.prettyPrint();

        // Validazioni base sulla risposta
        ValidatableResponse validatableResponse = response.then();
        validatableResponse.contentType(ContentType.JSON);
        validatableResponse.statusCode(200);

        // Verifica che location.city non sia null
        String city = response.jsonPath().getString("results[0].location.city");
        Assert.assertNotNull(city, "location.city è null, ma non dovrebbe esserlo!");

        // Stampa la descrizione del timezone
        String timezoneDescription = response.jsonPath().getString("results[0].location.timezone.description");
        System.out.println("Timezone Description: " + timezoneDescription);
    }
}


