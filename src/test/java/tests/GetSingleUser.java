package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import models.HttpStatusCodes;
import object_model.GetSingleUserModel;
import org.testng.annotations.Test;

public class GetSingleUser extends BaseTest{

    @Test(groups = "positive", dataProvider = "single-user", dataProviderClass = data_providers.DataProviders.class)
    @Severity(SeverityLevel.CRITICAL)
    @Epic("Positive")
    public void getUserWithValidId(int userId, String email, String firstName, String lastName) {
        new GetSingleUserModel(userId, HttpStatusCodes.OK)
                .getUserData()
                .assertUserId()
                .assertUserEmail(email)
                .assertUserFirstName(firstName)
                .assertUserLastName(lastName);
    }

    @Test(groups = "negative")
    @Severity(SeverityLevel.NORMAL)
    @Epic("Negative")
    public void getUserWithNotExistId() {
        var userId = jsonReader().get("single-user.not-exist-id").toInt();

        new GetSingleUserModel(userId, HttpStatusCodes.NOT_FOUND)
                .getUserData();
    }

    @Test(groups = "negative")
    @Severity(SeverityLevel.NORMAL)
    @Epic("Negative")
    public void getUserWithInvalidId() {
        var userId = jsonReader().get("single-user.invalid-id").toInt();

        new GetSingleUserModel(userId, HttpStatusCodes.BAD_REQUEST)
                .getUserData();
    }
}
