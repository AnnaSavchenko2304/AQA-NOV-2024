package org.prog.cucumber.steps;

import io.cucumber.java.en.Given;
import org.prog.selenium.dto.PersonDto;
import org.testng.Assert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLSteps {

    public static final List<PersonDto> RANDOM_PERSONS = new ArrayList<>();

    public static String RANDOM_PERSON;

    public static Connection connection;

    @Given("I store those people to database")
    public void storeRandomUsersToDatabase() throws SQLException {
        String insertStatement = "INSERT INTO Persons (FirstName, LastName, Gender, Title, Nat) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
        for (PersonDto personDto : RANDOM_PERSONS) {
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

    @Given("I request database for a random person")
    public void getRandomPersonFromDB() throws SQLException {
        String selectStatement = "select * from Persons ORDER BY RAND() limit 1";
        PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
        ResultSet resultSet = preparedStatement.executeQuery(selectStatement);
        if (resultSet.next()) {
            RANDOM_PERSON = resultSet.getString("FirstName") + " " + resultSet.getString("LastName");
        }
        Assert.assertNotNull(RANDOM_PERSON, "No random person found!");
    }
}
