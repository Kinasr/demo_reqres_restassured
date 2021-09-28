package tests;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import object_model.GetSingleResourceModel;
import models.MyDataRecords.ResourceData;
import models.HttpStatusCodes;
import org.testng.annotations.Test;

public class GetSingleResource extends BaseTest {

    @Test(groups = "positive", dataProvider = "single-resource", dataProviderClass = data_providers.DataProviders.class)
    @Severity(SeverityLevel.CRITICAL)
    @Epic("Positive")
    public void getUserWithValidId(ResourceData resourceData) {
        new GetSingleResourceModel(resourceData.id(), HttpStatusCodes.OK)
                .getResourceData()
                .assertResourceData(resourceData);
    }
}
