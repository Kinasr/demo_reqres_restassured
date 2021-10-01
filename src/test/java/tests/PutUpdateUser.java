package tests;

import helpers.JsonReader;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import models.HttpStatusCodes;
import object_model.PutUpdateUserModel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PutUpdateUser {
    private JsonReader jsonReader;

    @BeforeClass
    public void setUpClass() {
        jsonReader = new JsonReader("users-data");
    }

    @Test(groups = "positive")
    @Severity(SeverityLevel.CRITICAL)
    @Epic("Positive")
    public void updateValidUser() {
        var userId = jsonReader.get("update-user.id").toInt();
        var userName = jsonReader.get("update-user.valid-parameters.name").toString();
        var userJob = jsonReader.get("update-user.valid-parameters.job").toString();

        new PutUpdateUserModel(userId, userName, userJob, HttpStatusCodes.OK)
                .updateUserData()
                .assertUserData();
    }

    @Test(groups = "negative", dataProvider = "invalid-user-update",
            dataProviderClass = data_providers.DataProviders.class)
    @Severity(SeverityLevel.NORMAL)
    @Epic("Negative")
    public void updateUserWithInvalidData(String userName, String userJob) {
        var userId = jsonReader.get("update-user.id").toInt();

        new PutUpdateUserModel(userId, userName, userJob, HttpStatusCodes.BAD_REQUEST)
                .updateUserData();
    }

    @Test(groups = "negative")
    @Severity(SeverityLevel.NORMAL)
    @Epic("Negative")
    public void updateNotExistedUser() {
        var userId = jsonReader.get("update-user.not-exist.id").toInt();
        var userName = jsonReader.get("update-user.not-exist.name").toString();
        var userJob = jsonReader.get("update-user.not-exist.job").toString();

        new PutUpdateUserModel(userId, userName, userJob, HttpStatusCodes.NOT_FOUND)
                .updateUserData();
    }
}
