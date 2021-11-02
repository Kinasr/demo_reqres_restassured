package models;

import io.restassured.response.Response;
import io.restassured.specification.RequestSenderOptions;
import io.restassured.specification.RequestSpecification;

import java.util.function.BiFunction;

public enum RequestTypes {
    GET(RequestSenderOptions::get), POST(RequestSenderOptions::post), PUT(RequestSenderOptions::put),
    PATCH(RequestSenderOptions::patch), DELETE(RequestSenderOptions::delete);

    private final BiFunction<RequestSpecification, String, Response> function;

    RequestTypes(BiFunction<RequestSpecification, String, Response> function) {
        this.function = function;
    }

    public BiFunction<RequestSpecification, String, Response> send() {
        return function;
    }
}
