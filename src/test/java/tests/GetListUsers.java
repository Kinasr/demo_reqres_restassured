package tests;

import helpers.JsonReader;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import models.HttpStatusCodes;
import object_model.GetListUsersModel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GetListUsers {
    private JsonReader jsonReader;

    @BeforeClass
    public void setUpClass() {
        jsonReader = new JsonReader("users-data");
    }

    @Test(groups = "positive", dataProvider = "list-users", dataProviderClass = data_providers.DataProviders.class)
    @Severity(SeverityLevel.CRITICAL)
    @Epic("Positive")
    public void getUsersInThePage(int page, int usersPerPage) {
        new GetListUsersModel(page, HttpStatusCodes.OK)
                .getUsersInPage()
                .assertPageNumber()
                .assertNumOfUsersPerPage(usersPerPage);
    }

    @Test(groups = "negative")
    @Severity(SeverityLevel.NORMAL)
    @Epic("Negative")
    public void getUsersInANotExistingPage() {
        var page = jsonReader.get("list-users.not-existed-page").toInt();

        new GetListUsersModel(page, HttpStatusCodes.NOT_FOUND)
                .getUsersInPage();
    }

    @Test(groups = "negative")
    @Severity(SeverityLevel.NORMAL)
    @Epic("Negative")
    public void getUsersInAnInvalidPageNumber() {
        var page = jsonReader.get("list-users.invalid-page").toInt();

        new GetListUsersModel(page, HttpStatusCodes.BAD_REQUEST)
                .getUsersInPage();
    }
}
