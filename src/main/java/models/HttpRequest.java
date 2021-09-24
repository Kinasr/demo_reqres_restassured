package models;

import io.restassured.http.ContentType;

import java.util.Map;

public class HttpRequest {
    private final RequestTypes type;
    private final String serviceName;
    private final HttpStatusCodes expectedStatusCode;
    private Map<String, Object> headers = null;
    private ContentType contentType = null;
    private Map<String, Object> formParams = null;
    private Map<String, Object> queryParams = null;
    private String body = null;
    private Map<String, String> cookies = null;

    public HttpRequest(RequestTypes type, String serviceName, HttpStatusCodes expectedStatusCode) {
        this.type = type;
        this.serviceName = serviceName;
        this.expectedStatusCode = expectedStatusCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public HttpStatusCodes getExpectedStatusCode() {
        return expectedStatusCode;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public Map<String, Object> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(Map<String, Object> queryParams) {
        this.queryParams = queryParams;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public RequestTypes getType() {
        return type;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    public Map<String, Object> getFormParams() {
        return formParams;
    }

    public void setFormParams(Map<String, Object> formParams) {
        this.formParams = formParams;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
