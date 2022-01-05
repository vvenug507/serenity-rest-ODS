package starter.healthcheck;

import config.Config;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import net.serenitybdd.junit.spring.integration.SpringIntegrationMethodRule;
import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


@SpringBootApplication(scanBasePackageClasses ={starter.healthcheck.HealthCheck.class, Config.class})
@SpringBootTest
public class HealthCheck {

    @Rule
    public SpringIntegrationMethodRule springMethodIntegration = new SpringIntegrationMethodRule();

    @Autowired
    HealthCheckRest healthcheck;

    @Value("${ExcelPath}")
    private String excelPath;



    @Given("All Required Service URL in Excel")
    public void allRequiredServiceURLInExcel() {


        healthcheck.readServiceURLs();



    }

    @Then("Execute the Services URL")
    public void executeTheServicesURL() {
        healthcheck.executeServiceURLs();
    }

    @When("The Execution is complete print the result")
    public void theExecutionIsCompletePrintTheResult() throws IOException {
        healthcheck.writeExcelResult();

        //Path generatedReport = Paths.get(excelPath);
        //Serenity.recordReportData().withTitle("Detailed Status Refer Below Local Link").fromFile(generatedReport);
        //Serenity.recordReportData().withTitle("Detailed Status Refer Below Local Link").andContents(excelPath);

    }
}
