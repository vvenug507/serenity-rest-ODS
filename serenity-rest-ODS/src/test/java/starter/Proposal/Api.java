package starter.Proposal;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.util.EnvironmentVariables;

public class Api {

    public EnvironmentVariables env;
    public String accessToken;

    @Step("I authenticate to the ODS system")
    public void authentication(){
        accessToken = SerenityRest.given().
                header("Content-Type","application/x-www-form-urlencoded").
                header("Authorization", "Basic ZGFhc19vZHNfZGV2OjhjMjVmZjdkYjFjYjRmMmRiOWEyYTdhOWJlNzNhOWM3").
                body(String.format("grant_type=%s&scope=%s", "client_credentials", "daas:ods-dev"))
                .when().post(EnvironmentSpecificConfiguration.from(env).getProperty("oauth.base.url"))
                .body().path("access_token");
    }

    @Step("I Proposal a ODS Product")
    public void proposalRequest(){
        RequestBuilder requestBuilder = new RequestBuilder();

        SerenityRest.given().queryParam("access_token", accessToken)
                .header("Content-type","application/json")
                .header("source","3").header("trackingid","2345678")
                .body(requestBuilder.proposalPayload().toString()).when()
                .post(EnvironmentSpecificConfiguration.from(env).getProperty("gateway.base.url") + "/ods/products/v1/proposal");
    }

}
