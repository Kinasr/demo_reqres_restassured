package object_model;

import helpers.ApiActions;
import io.restassured.response.Response;
import models.AssertionType;
import models.HttpRequest;
import models.HttpStatusCodes;
import models.RequestTypes;

public class PatchUpdateUserModel {
    private Response response;
    private final ApiActions apiActions;
    private final String userName;
    private final String userJob;

    public PatchUpdateUserModel(int userId, String userName, String userJob, HttpStatusCodes statusCodes) {
        this.userName = userName;
        this.userJob = userJob;

        var body = "{\n";
        if (userName != null) body += "\"name\": \"" + userName + "\"" + (userJob != null ? ",\n" : "");
        if (userJob != null) body += "\"job\": \"" + userJob + "\"";
        body += "\n}";

        var httpRequest = new HttpRequest(RequestTypes.PATCH, "users/" + userId, statusCodes);
        httpRequest.setBody(body);

        apiActions = new ApiActions(httpRequest);
    }

    public PatchUpdateUserModel updateUserData() {
        response = apiActions
                .send()
                .assertStatusCode()
                .assertResponseTime(500)
                .extractResponse();
        return this;
    }

    public PatchUpdateUserModel assertUserData() {
        if (userName != null) {
            var actualName = response.jsonPath().get("name");
            apiActions.assertThat(AssertionType.EQUALS, actualName, userName, "Checking user name");
        }
        if (userJob != null) {
            var actualJob = response.jsonPath().get("job");
            apiActions.assertThat(AssertionType.EQUALS, actualJob, userJob, "Checking user job");
        }
        return this;
    }
}
