package object_model;

import helpers.ApiActions;
import models.HttpRequest;
import models.HttpStatusCodes;
import models.RequestTypes;

public class DeleteUserModel {
    private final ApiActions apiActions;

    public DeleteUserModel(int userId, HttpStatusCodes statusCodes) {
        apiActions = new ApiActions(new HttpRequest(RequestTypes.DELETE, "users/" + userId, statusCodes));
    }

    public DeleteUserModel deleteUser() {
        apiActions
                .send()
                .assertStatusCode()
                .assertResponseTime(500);
        return this;
    }
}
