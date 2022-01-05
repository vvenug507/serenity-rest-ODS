package starter.Proposal;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;

import static net.serenitybdd.rest.SerenityRest.restAssuredThat;

public class StepDefinition {
    @Steps
    Api API;

    @Given("I Authenticate to the ODS System")
    public void i_authenticate_to_the_ods_system() {
        API.authentication();
    }
    @When("I Proposal a ODS Product")
    public void i_proposal_a_ods_product() {
        API.proposalRequest();
    }
    @Then("I should able to get the successful response")
    public void i_should_able_to_get_the_successful_response() {
        restAssuredThat(response -> response.statusCode(200));
    }
}
