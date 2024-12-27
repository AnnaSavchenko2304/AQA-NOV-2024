package org.prog.cucumber.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.util.Map;
import java.util.Set;

public class DataSteps {

    @Then("I can see that person's name")
    public void printRandomPersonsName() {
        System.out.println(SQLSteps.RANDOM_PERSON);
    }

    @Given("I print a String {string}")
    public void printString(String s) {
        System.out.println(s);
    }

    @Given("I print a String {string} {int} times")
    public void printString(String s, int times) {
        for (int i = 0; i < times; i++) {
            System.out.println(s);
        }
    }

    @Given("Data Table {string} values:")
    public void printTable(String s, DataTable dataTable) {
        System.out.println("Print table " + s);
        dataTable.asList().forEach(item -> System.out.println(item));
    }

    @Given("Data Table {string} values as map:")
    public void printTableAsMap(String s, DataTable dataTable) {
        System.out.println("Print table " + s);
        Set<Map.Entry<String, String>> entries = dataTable.asMap().entrySet();
        entries.forEach(e -> {
            System.out.println(e.getKey() + " : " + e.getValue());
        });
    }
}
