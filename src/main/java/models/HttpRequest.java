package models;

import io.restassured.http.ContentType;
import lombok.Data;

import java.util.Map;
import java.util.Optional;

public @Data
class HttpRequest {
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

    public Optional<Map<String, Object>> headers() {
        return Optional.ofNullable(headers);
    }

    public Optional<ContentType> contentType() {
        return Optional.ofNullable(contentType);
    }

    public Optional<Map<String, Object>> formParams() {
        return Optional.ofNullable(formParams);
    }

    public Optional<Map<String, Object>> queryParams() {
        return Optional.ofNullable(queryParams);
    }

    public Optional<String> body() {
        return Optional.ofNullable(body);
    }

    public Optional<Map<String, String>> cookies() {
        return Optional.ofNullable(cookies);
    }
}
