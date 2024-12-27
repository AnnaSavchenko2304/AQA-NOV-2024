package org.prog.testng;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import org.prog.selenium.dto.PersonDto;
import org.prog.selenium.dto.ResultsDto;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class SQLTest {

    private Connection connection;

    @SneakyThrows
    @BeforeSuite
    public void setUp() {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "user", "password");
    }

    @SneakyThrows
    @Test
    public void mySqlTest() {
        String insertStatement = "INSERT INTO Persons (FirstName, LastName, Gender, Title, Nat) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
        List<PersonDto> persons = retrievePersons(3);
        for (PersonDto personDto : persons) {
            preparedStatement.setString(1, personDto.getName().getFirst());
            preparedStatement.setString(2, personDto.getName().getLast());
            preparedStatement.setString(3, personDto.getGender());
            preparedStatement.setString(4, personDto.getName().getTitle());
            preparedStatement.setString(5, personDto.getNat());
            try {
                preparedStatement.execute();
            } catch (Exception e) {
                System.err.println("Failed to save user " + personDto.getName().getFirst() + " " + personDto.getName().getLast());
            }
        }
    }

    @SneakyThrows
    @Test
    public void mySqlReadRandomPerson() {
        String selectStatement = "select * from Persons ORDER BY RAND() limit 1";
        PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
        ResultSet resultSet = preparedStatement.executeQuery(selectStatement);
        while (resultSet.next()) {
            System.out.println(resultSet.getString("FirstName") +
                    " " + resultSet.getString("LastName"));
        }
    }

    @SneakyThrows
    @AfterSuite
    public void tearDown() {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    private List<PersonDto> retrievePersons(int amount) {
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
        return response.as(ResultsDto.class).getResults();
    }
}
