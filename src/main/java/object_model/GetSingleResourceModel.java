package object_model;

import helpers.ApiActions;
import io.restassured.response.Response;
import models.AssertionType;
import models.HttpRequest;
import models.HttpStatusCodes;
import models.RequestTypes;
import models.MyDataRecords.ResourceData;

public class GetSingleResourceModel {
    private Response response;
    private final ApiActions apiActions;
    private final int resourceId;

    public GetSingleResourceModel(int resourceId, HttpStatusCodes statusCodes) {
        this.resourceId = resourceId;

        var httpRequest = new HttpRequest(RequestTypes.GET, "unknown/" + resourceId, statusCodes);

        apiActions = new ApiActions(httpRequest);
    }

    public GetSingleResourceModel getResourceData() {
        response = apiActions
                .send()
                .assertStatusCode()
                .assertResponseTime(500)
                .extractResponse();
        return this;
    }

    public GetSingleResourceModel assertResourceData(ResourceData expectedData) {
        var actualData = new ResourceData(
                response.jsonPath().getInt("data.id"),
                response.jsonPath().getString("data.name"),
                response.jsonPath().getInt("data.year"),
                response.jsonPath().getString("data.color"),
                response.jsonPath().getString("data.pantone_value")
        );

        apiActions.assertThat(AssertionType.EQUALS, actualData, expectedData, "Checking The resource data");

        return this;
    }
}
