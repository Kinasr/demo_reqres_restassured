package tests;

import helpers.JsonReader;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import models.HttpStatusCodes;
import object_model.PostCreateUserModel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Timestamp;

public class PostCreateUser {
    private JsonReader jsonReader;

    @BeforeClass
    public void setUpClass() {
        jsonReader = new JsonReader("users-data");
    }

    @Test(groups = "positive")
    @Severity(SeverityLevel.CRITICAL)
    @Epic("Positive")
    public void createNewUser() {
        var timestamp = String.valueOf(new Timestamp(System.currentTimeMillis()).getTime());
        var userData = jsonReader.
                get("create-user.new-user").toString()
                .replace("###", timestamp);

        new PostCreateUserModel(userData, HttpStatusCodes.CREATED)
                .createUser()
                .assertUserCreation();
    }

    @Test(groups = "negative", dataProvider = "invalid-user-creation",
            dataProviderClass = data_providers.DataProviders.class)
    @Severity(SeverityLevel.NORMAL)
    @Epic("Negative")
    public void createNewUserWithInvalidData(String userData) {
        new PostCreateUserModel(userData, HttpStatusCodes.BAD_REQUEST)
                .createUser();
    }

    @Test(groups = "negative")
    @Severity(SeverityLevel.NORMAL)
    @Epic("Negative")
    public void createdAnExistedUser() {
        var userData = jsonReader.get("create-user.existed-user").toString();

        new PostCreateUserModel(userData, HttpStatusCodes.CONFLICT)
                .createUser();
    }
}
