package object_model;

import helpers.ApiActions;
import io.restassured.response.Response;
import models.AssertionType;
import models.HttpRequest;
import models.HttpStatusCodes;
import models.RequestTypes;

import java.util.Map;

public class GetListResourceModel {
    private Response response;
    private final ApiActions apiActions;
    private final int page;

    public GetListResourceModel(int page, HttpStatusCodes statusCodes) {
        this.page = page;

        var httpRequest = new HttpRequest(RequestTypes.GET, "unknown", statusCodes);
        httpRequest.setQueryParams(Map.of("page", page));

        this.apiActions = new ApiActions(httpRequest);
    }

    public Response getResponse() {
        return response;
    }

    public GetListResourceModel getResourcesInPage() {
        response = apiActions
                .send()
                .assertStatusCode()
                .assertResponseTime(500)
                .extractResponse();
        return this;
    }

    public GetListResourceModel assertPageNumber() {
        var actual = response
                .jsonPath()
                .getInt("page");
        apiActions
                .assertThat(AssertionType.EQUALS, actual, page, "Checking the page number");
        return this;
    }

    public GetListResourceModel assertNumberOfResourcesPerPage(int expected) {
        var actual = response
                .jsonPath()
                .getList("data");
        apiActions
                .assertThat(AssertionType.EQUALS, actual.size(), expected,
                        "Checking the number of resources per page");
        return this;
    }
}
