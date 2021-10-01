package tests;

import helpers.JsonReader;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import object_model.GetSingleResourceModel;
import models.MyDataRecords.ResourceData;
import models.HttpStatusCodes;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GetSingleResource {
    private JsonReader jsonReader;

    @BeforeClass
    public void setUpClass() {
        jsonReader = new JsonReader("resources-data");
    }

    @Test(groups = "positive", dataProvider = "single-resource", dataProviderClass = data_providers.DataProviders.class)
    @Severity(SeverityLevel.CRITICAL)
    @Epic("Positive")
    public void getResourceWithValidId(ResourceData resourceData) {
        new GetSingleResourceModel(resourceData.id(), HttpStatusCodes.OK)
                .getResourceData()
                .assertResourceData(resourceData);
    }

    @Test(groups = "negative")
    @Severity(SeverityLevel.NORMAL)
    @Epic("Negative")
    public void getResourceWithNotExistId() {
        var resourceId = jsonReader.get("single-resource.not-exist-id").toInt();

        new GetSingleResourceModel(resourceId, HttpStatusCodes.NOT_FOUND)
                .getResourceData();
    }

    @Test(groups = "negative")
    @Severity(SeverityLevel.NORMAL)
    @Epic("Negative")
    public void getResourceWithInvalidId() {
        var resourceId = jsonReader.get("single-resource.invalid-id").toInt();

        new GetSingleResourceModel(resourceId, HttpStatusCodes.BAD_REQUEST)
                .getResourceData();
    }
}
