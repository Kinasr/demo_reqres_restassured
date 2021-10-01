package object_model;

import helpers.ApiActions;
import helpers.JsonReader;
import io.restassured.response.Response;
import models.AssertionType;
import models.HttpRequest;
import models.HttpStatusCodes;
import models.RequestTypes;

public class PutUpdateUserModel {
    private Response response;
    private final ApiActions apiActions;
    private final String userName;
    private final String userJob;

    public PutUpdateUserModel(int userId, String userName, String userJob, HttpStatusCodes statusCodes) {
        this.userName = userName;
        this.userJob = userJob;

        var body = new JsonReader("users-data")
                .get("update-user.model").toString()
                .replace("#name#", userName)
                .replace("#jobb#", userJob);

        var httpRequest = new HttpRequest(RequestTypes.PUT, "users/" + userId, statusCodes);
        httpRequest.setBody(body);

        apiActions = new ApiActions(httpRequest);
    }

    public PutUpdateUserModel updateUserData() {
        response = apiActions
                .send()
                .assertStatusCode()
                .assertResponseTime(500)
                .extractResponse();
        return this;
    }

    public PutUpdateUserModel assertUserData() {
        var actualName = response.jsonPath().get("name");
        var actualJob = response.jsonPath().get("job");

        apiActions.assertThat(AssertionType.EQUALS, actualName, userName, "Checking user name");
        apiActions.assertThat(AssertionType.EQUALS, actualJob, userJob, "Checking user job");

        return this;
    }
}
