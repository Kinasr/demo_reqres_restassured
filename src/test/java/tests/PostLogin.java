package tests;

import helpers.JsonReader;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import models.HttpStatusCodes;
import object_model.PostLoginModel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PostLogin {
    private JsonReader jsonReader;

    @BeforeClass
    public void setUpClass() {
        jsonReader = new JsonReader("account-data");
    }

    @Test(groups = "positive")
    @Severity(SeverityLevel.CRITICAL)
    @Epic("Positive")
    public void validLogin() {
        var loginData = jsonReader.get("valid").toString();

        new PostLoginModel(loginData, HttpStatusCodes.OK)
                .login()
                .assertRegistration();
    }

    @Test(groups = "negative", dataProvider = "invalid-account-data",
            dataProviderClass = data_providers.DataProviders.class)
    @Severity(SeverityLevel.NORMAL)
    @Epic("Negative")
    public void invalidRegister(String loginData) {
        var errorMessage = jsonReader.get("error-message").toString();

        new PostLoginModel(loginData, HttpStatusCodes.BAD_REQUEST)
                .login()
                .assertErrorMessage(errorMessage);
    }
}
