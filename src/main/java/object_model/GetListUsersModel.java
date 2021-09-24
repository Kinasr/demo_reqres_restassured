package object_model;

import helpers.ApiActions;
import io.restassured.response.Response;
import models.HttpRequest;
import models.HttpStatusCodes;
import models.RequestTypes;

import java.util.Map;

import static org.testng.Assert.assertEquals;

public class GetListUsersModel {
    private Response response;

    public Response getResponse() {
        return response;
    }

    public GetListUsersModel getUsersInPage(int page) {
        var httpRequest = new HttpRequest(RequestTypes.GET, "users", HttpStatusCodes.OK);
        httpRequest.setQueryParams(Map.of("page", page));

        response = new ApiActions(httpRequest)
                .send()
                .assertStatusCode()
                .extractResponse();

        return this;
    }

    public GetListUsersModel assertNumOfUsersPerPage(int expected) {
        var actual = response
                .jsonPath()
                .getList("data");
        assertEquals(actual.size(), expected);

        return this;
    }

    /**
     * Assert the email of the user at index
     * @param index start form Zero
     * @param expected user email
     * @return current class object
     */
    public GetListUsersModel assertNthUserEmail(int index, String expected) {
        var actual = response
                .jsonPath()
                .getString("data[" + index + "].email");
        assertEquals(actual, expected);

        return this;
    }
}
