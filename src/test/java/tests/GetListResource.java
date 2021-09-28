package tests;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import models.HttpStatusCodes;
import object_model.GetListResourceModel;
import org.testng.annotations.Test;

public class GetListResource extends BaseTest {

    @Test(groups = "positive",dataProvider = "list-resources", dataProviderClass = data_providers.DataProviders.class)
    @Severity(SeverityLevel.CRITICAL)
    @Epic("Positive")
    public void getResourcesInPage(int page, int resourcesPerPage) {
        new GetListResourceModel(page, HttpStatusCodes.OK)
                .getResourcesInPage()
                .assertPageNumber()
                .assertNumberOfResourcesPerPage(resourcesPerPage);
    }

    @Test(groups = "negative")
    @Severity(SeverityLevel.NORMAL)
    @Epic("Negative")
    public void getResourcesInANotExistingPage() {
        var page = jsonReader().get("list-resources.not-existed-page").toInt();
        new GetListResourceModel(page, HttpStatusCodes.NOT_FOUND)
                .getResourcesInPage();
    }

    @Test(groups = "negative")
    @Severity(SeverityLevel.NORMAL)
    @Epic("Negative")
    public void getResourcesInAnInvalidPageNumber() {
        var page = jsonReader().get("list-resources.invalid-page").toInt();
        new GetListResourceModel(page, HttpStatusCodes.BAD_REQUEST)
                .getResourcesInPage();
    }
}
