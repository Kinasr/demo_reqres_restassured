package tests;

import helpers.JsonReader;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import models.HttpStatusCodes;
import object_model.DeleteUserModel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DeleteUser {
    private JsonReader jsonReader;

    @BeforeClass
    public void setUpClass() {
        jsonReader = new JsonReader("users-data");
    }

    @Test(groups = "positive")
    @Severity(SeverityLevel.CRITICAL)
    @Epic("Positive")
    public void deleteValidUser() {
        var userId = jsonReader.get("delete-user.valid").toInt();

        new DeleteUserModel(userId, HttpStatusCodes.NO_CONTENT)
                .deleteUser();
    }

    @Test(groups = "negative")
    @Severity(SeverityLevel.NORMAL)
    @Epic("Negative")
    public void deleteNotExistedUser() {
        var userId = jsonReader.get("delete-user.not-existed").toInt();

        new DeleteUserModel(userId, HttpStatusCodes.NOT_FOUND)
                .deleteUser();
    }
}
