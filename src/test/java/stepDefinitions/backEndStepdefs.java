package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class backEndStepdefs {
    @Given("Precondition is given")
    public void preconditionIsGiven() {
        System.out.println("hello 1");
    }

    @When("Something is done")
    public void somethingIsDone() {
        System.out.println("hello 2");
    }

    @Then("Something is expected")
    public void somethingIsExpected() {
        System.out.println("hello 3");
    }
}
