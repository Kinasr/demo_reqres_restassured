package object_model;

import helpers.ApiActions;
import io.restassured.response.Response;
import models.AssertionType;
import models.HttpRequest;
import models.HttpStatusCodes;
import models.RequestTypes;
import models.MyDataRecords.UserData;

public class GetSingleUserModel {
    private Response response;
    private final ApiActions apiActions;
    private final int userId;


    public GetSingleUserModel(int userId, HttpStatusCodes statusCodes) {
        this.userId = userId;

        var httpRequest = new HttpRequest(RequestTypes.GET, "users/" + userId, statusCodes);
        apiActions = new ApiActions(httpRequest);
    }

    public GetSingleUserModel getUserData() {
        response = apiActions
                .send()
                .assertStatusCode()
                .assertResponseTime(500)
                .extractResponse();
        return this;
    }

    public GetSingleUserModel assertUserId() {
        var actual = response.jsonPath().getInt("data.id");
        apiActions.assertThat(AssertionType.EQUALS, actual, userId, "Checking user Id");
        return this;
    }

    public GetSingleUserModel assertUserEmail(String expected) {
        var actual = response.jsonPath().getString("data.email");
        apiActions
                .assertThat(AssertionType.EQUALS, actual, expected, "Checking user Email");
        return this;
    }

    public GetSingleUserModel assertUserFirstName(String expected) {
        var actual = response.jsonPath().getString("data.first_name");
        apiActions
                .assertThat(AssertionType.EQUALS, actual, expected, "Checking user First name");
        return this;
    }

    public GetSingleUserModel assertUserLastName(String expected) {
        var actual = response.jsonPath().getString("data.last_name");
        apiActions
                .assertThat(AssertionType.EQUALS, actual, expected, "Checking user Last name");
        return this;
    }

    public GetSingleUserModel assertUserData(UserData expectedData) {
        var actual = new UserData(
                response.jsonPath().getInt("data.id"),
                response.jsonPath().getString("data.email"),
                response.jsonPath().getString("data.first_name"),
                response.jsonPath().getString("data.last_name"),
                response.jsonPath().getString("data.avatar")
        );

        apiActions.assertThat(AssertionType.EQUALS, actual, expectedData, "Checking the user data");

        return this;
    }
}
