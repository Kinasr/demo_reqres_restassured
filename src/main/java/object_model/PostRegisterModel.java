package object_model;

import helpers.ApiActions;
import io.restassured.response.Response;
import models.AssertionType;
import models.HttpRequest;
import models.HttpStatusCodes;
import models.RequestTypes;

public class PostRegisterModel {
    private Response response;
    private final ApiActions apiActions;

    public PostRegisterModel(String registrationData, HttpStatusCodes statusCodes) {
        var httpRequest = new HttpRequest(RequestTypes.POST, "register", statusCodes);

        httpRequest.setBody(registrationData);

        apiActions = new ApiActions(httpRequest);
    }

    public PostRegisterModel register() {
        response = apiActions
                .send()
                .assertStatusCode()
                .assertResponseTime(500)
                .extractResponse();
        return this;
    }

    public PostRegisterModel assertRegistration() {
        var id = response.jsonPath().getInt("id");
        apiActions.assertThat(AssertionType.TRUE, null, id != 0,
                "Checking that the Registration is successes");
        return this;
    }

    public PostRegisterModel assertErrorMessage(String expectedMsg) {
        var actualMsg = response.jsonPath().getString("error");
        apiActions.assertThat(AssertionType.EQUALS, actualMsg, expectedMsg, "Checking the error message");

        return this;
    }
}
