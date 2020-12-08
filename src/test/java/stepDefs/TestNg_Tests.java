package stepDefs;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import managers.Utilities;

public class TestNg_Tests {

    @When("I create a file name using <funnyName> and <number>")
    public void iCreateAFileNameUsingFunnyNameAndNumber() {

       // System.out.println(Utilities.CreateAuser("",1));
    }
}
