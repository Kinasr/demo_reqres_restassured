package tests;

import helpers.JsonReader;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import models.HttpStatusCodes;
import object_model.PostRegisterModel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PostRegister {
    private JsonReader jsonReader;

    @BeforeClass
    public void setUpClass() {
        jsonReader = new JsonReader("account-data");
    }

    @Test(groups = "positive")
    @Severity(SeverityLevel.CRITICAL)
    @Epic("Positive")
    public void validRegister() {
        var registrationData = jsonReader.get("valid").toString();

        new PostRegisterModel(registrationData, HttpStatusCodes.OK)
                .register()
                .assertRegistration();
    }

    @Test(groups = "negative", dataProvider = "invalid-account-data",
            dataProviderClass = data_providers.DataProviders.class)
    @Severity(SeverityLevel.NORMAL)
    @Epic("Negative")
    public void invalidRegister(String registrationData) {
        var errorMessage = jsonReader.get("error-message").toString();

        new PostRegisterModel(registrationData, HttpStatusCodes.BAD_REQUEST)
                .register()
                .assertErrorMessage(errorMessage);
    }
}
