package object_model;

import helpers.ApiActions;
import io.restassured.response.Response;
import models.AssertionType;
import models.HttpRequest;
import models.HttpStatusCodes;
import models.RequestTypes;

public class PostCreateUserModel {
    private Response response;
    private final ApiActions apiActions;
    private final String userData;

    public PostCreateUserModel(String userData, HttpStatusCodes statusCodes) {
        this.userData = userData;

        var httpRequest = new HttpRequest(RequestTypes.POST, "users", statusCodes);
        httpRequest.setBody(userData);

        apiActions = new ApiActions(httpRequest);
    }

    public PostCreateUserModel createUser() {
        response = apiActions.send()
                .assertStatusCode()
                .assertResponseTime(500)
                .extractResponse();
        return this;
    }

    public PostCreateUserModel assertUserCreation() {
        var userId = response.jsonPath().get("id");
        apiActions.assertThat(AssertionType.TRUE, null, userId != null,
                "Checking that the user is created successfully");
        return this;
    }
}
