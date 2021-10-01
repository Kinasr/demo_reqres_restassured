package object_model;

import helpers.ApiActions;
import io.restassured.response.Response;
import models.AssertionType;
import models.HttpRequest;
import models.HttpStatusCodes;
import models.RequestTypes;

public class PostLoginModel {
    private Response response;
    private final ApiActions apiActions;

    public PostLoginModel(String loginData, HttpStatusCodes statusCodes) {
        var httpRequest = new HttpRequest(RequestTypes.POST, "login", statusCodes);

        httpRequest.setBody(loginData);

        apiActions = new ApiActions(httpRequest);
    }

    public PostLoginModel login() {
        response = apiActions
                .send()
                .assertStatusCode()
                .assertResponseTime(500)
                .extractResponse();
        return this;
    }

    public PostLoginModel assertRegistration() {
        var token = response.jsonPath().getInt("token");
        apiActions.assertThat(AssertionType.TRUE, null, token != 0,
                "Checking that the Registration is successes");
        return this;
    }

    public PostLoginModel assertErrorMessage(String expectedMsg) {
        var actualMsg = response.jsonPath().getString("error");
        apiActions.assertThat(AssertionType.EQUALS, actualMsg, expectedMsg, "Checking the error message");

        return this;
    }
}
