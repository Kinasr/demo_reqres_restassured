package tests;

import helpers.JsonReader;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import models.HttpStatusCodes;
import object_model.GetSingleUserModel;
import models.MyDataRecords.UserData;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GetSingleUser {
    private JsonReader jsonReader;

    @BeforeClass
    public void setUpClass() {
        jsonReader = new JsonReader("users-data");
    }

    @Test(groups = "positive", dataProvider = "single-user", dataProviderClass = data_providers.DataProviders.class)
    @Severity(SeverityLevel.CRITICAL)
    @Epic("Positive")
    public void getUserWithValidId(UserData userData) {
        new GetSingleUserModel(userData.id(), HttpStatusCodes.OK)
                .getUserData()
                .assertUserId()
                .assertUserData(userData);
    }

    @Test(groups = "negative")
    @Severity(SeverityLevel.NORMAL)
    @Epic("Negative")
    public void getUserWithNotExistId() {
        var userId = jsonReader.get("single-user.not-exist-id").toInt();

        new GetSingleUserModel(userId, HttpStatusCodes.NOT_FOUND)
                .getUserData();
    }

    @Test(groups = "negative")
    @Severity(SeverityLevel.NORMAL)
    @Epic("Negative")
    public void getUserWithInvalidId() {
        var userId = jsonReader.get("single-user.invalid-id").toInt();

        new GetSingleUserModel(userId, HttpStatusCodes.BAD_REQUEST)
                .getUserData();
    }
}
