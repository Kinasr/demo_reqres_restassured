package helpers;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.HttpRequest;

public class ApiActions {
    private final HttpRequest httpRequest;
    private Response response;

    public ApiActions(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public ApiActions send() {
        var url = Constants.BASE_URL + httpRequest.getServiceName();

        var request = RestAssured.given();

        if (httpRequest.getHeaders() != null) request.headers(httpRequest.getHeaders());

        if (httpRequest.getContentType() != null) request.contentType(httpRequest.getContentType());

        if (httpRequest.getFormParams() != null) request.formParams(httpRequest.getFormParams());

        if (httpRequest.getQueryParams() != null) request.queryParams(httpRequest.getQueryParams());

        if (httpRequest.getBody() != null) request.body(httpRequest.getBody());

        if (httpRequest.getCookies() != null) request.cookies(httpRequest.getCookies());

        switch (httpRequest.getType()) {
            case GET-> response = request.get(url);
            case POST -> response = request.post(url);
            case PUT -> response = request.put(url);
            case PATCH -> response = request.patch(url);
            case DELETE -> response = request.delete(url);
        }

        return this;
    }

    public ApiActions assertStatusCode() {
        response.then().statusCode(httpRequest.getExpectedStatusCode().getValue());

        return this;
    }

    public Response extractResponse() {
        return response.then().extract().response();
    }
}
