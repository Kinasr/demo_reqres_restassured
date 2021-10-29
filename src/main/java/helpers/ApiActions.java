package helpers;

import io.qameta.allure.model.Status;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.AssertionType;
import models.HttpRequest;
import models.VerifyRecord;
import org.testng.asserts.SoftAssert;
import utilities.MyReport;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class ApiActions {
    private final HttpRequest httpRequest;
    private Response response;
    private SoftAssert verification = null;

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

        var stepId = MyReport.startStep( this.getClass().getSimpleName(), "Sending request to --> " + url);
        switch (httpRequest.getType()) {
            case GET-> response = request.get(url);
            case POST -> response = request.post(url);
            case PUT -> response = request.put(url);
            case PATCH -> response = request.patch(url);
            case DELETE -> response = request.delete(url);
        }
        if (response != null)
            MyReport.updateStepStatus(stepId, Status.PASSED);

        MyReport.stopStep(stepId);

        MyReport.attach(this.getClass().getSimpleName(), response.asPrettyString());
        return this;
    }

    public ApiActions assertStatusCode() {
        var stepId = MyReport.startStep(this.getClass().getSimpleName(), "Checking the status code to be -> "
                + httpRequest.getExpectedStatusCode());
        response.then().statusCode(httpRequest.getExpectedStatusCode().getValue());
        MyReport.updateStepToBePassed(stepId);

        return this;
    }

    public Response extractResponse() {
        return response.then().extract().response();
    }

    public ApiActions assertResponseTime(long expected) {
        var actual = response.time();
        var stepId = MyReport.startStep(this.getClass().getSimpleName(),
                "Checking the response time to be less or equal " + expected +
                        "ms //<Actual is: " + actual + "ms>");

        if (actual <=  expected)
            MyReport.updateStepToBePassed(stepId);

        MyReport.stopStep(stepId);

        return this;
    }

    /**
     * If we assert with true type we need to pass actual = null and expected as boolean condition
     * @param assertType the type of assertion we want to do
     * @param actual the actual result
     * @param expected the expected result
     * @param msg the message that will appear in both logger and report.
     */
    @Deprecated
    public void assertThat(AssertionType assertType, Object actual, Object expected, String msg) {
        var stepId = MyReport.startStep(this.getClass().getSimpleName(), msg);

        switch (assertType) {
            case TRUE -> assertTrue((boolean) expected);
            case EQUALS -> assertEquals(actual, expected);
            case NOT_EQUALS -> assertNotEquals(actual, expected);
        }

        MyReport.updateStepToBePassed(stepId);
    }

    public void assertThat(String message, Runnable runnable) {
        var stepId = MyReport.startStep(this.getClass().getSimpleName(), message);
        runnable.run();

        MyReport.updateStepToBePassed(stepId);
    }

    public void performVerification(List<VerifyRecord> records) {
        var softAssertion = new SoftAssert();
        records.forEach(record -> {
            var stepId = MyReport.startStep(this.getClass().getSimpleName(), record.getMessage());
            record.getConsumer().accept(softAssertion);

            if (record.condition().orElse(true))
                MyReport.updateStepStatus(stepId, Status.PASSED);
            else
                MyReport.updateStepStatus(stepId, Status.FAILED);

            MyReport.stopStep(stepId);
        });
    }

    public ApiActions startVerification(VerifyRecord record) {
        if (verification == null)
            verification = new SoftAssert();

        var stepId = MyReport.startStep(this.getClass().getSimpleName(), record.getMessage());
        record.getConsumer().accept(verification);

        if (record.condition().orElse(true))
            MyReport.updateStepStatus(stepId, Status.PASSED);
        else
            MyReport.updateStepStatus(stepId, Status.FAILED);

        MyReport.stopStep(stepId);

        return this;
    }

    public void verify() {
        verification.assertAll();
        verification = null;
    }
}