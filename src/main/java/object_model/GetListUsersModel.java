package object_model;

import helpers.ApiActions;
import io.restassured.response.Response;
import models.AssertionType;
import models.HttpRequest;
import models.HttpStatusCodes;
import models.RequestTypes;

import java.util.Map;


public class GetListUsersModel {
    private Response response;
    private final ApiActions apiActions;
    private final int page;

    public GetListUsersModel(int page, HttpStatusCodes statusCode) {
        this.page = page;

        var httpRequest = new HttpRequest(RequestTypes.GET, "users", statusCode);
        httpRequest.setQueryParams(Map.of("page", page));

        apiActions = new ApiActions(httpRequest);
    }

    public Response getResponse() {
        return response;
    }

    public GetListUsersModel getUsersInPage() {
        response = apiActions
                .send()
                .assertStatusCode()
                .assertResponseTime(500)
                .extractResponse();

        return this;
    }

    public GetListUsersModel assertNumOfUsersPerPage(int expected) {
        var actual = response
                .jsonPath()
                .getList("data");

        apiActions
                .assertThat(AssertionType.EQUALS, actual.size(), expected,
                        "Checking the number of users per page to be -> " + expected);

        return this;
    }

    /**
     * Assert the email of the user at index
     *
     * @param index    start form Zero
     * @param expected user email
     * @return current class object
     */
    public GetListUsersModel assertNthUserEmail(int index, String expected) {
        var actual = response
                .jsonPath()
                .getString("data[" + index + "].email");

        apiActions
                .assertThat(AssertionType.EQUALS, actual, expected,
                        "Checking the email of the " + index + " user");

        return this;
    }

    public GetListUsersModel assertPageNumber() {
        var actual = response
                .jsonPath()
                .getInt("page");

        apiActions
                .assertThat(AssertionType.EQUALS, actual, page, "Checking the number of returned page");

        return this;
    }
}
