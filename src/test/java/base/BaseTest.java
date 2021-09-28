package base;

import helpers.JsonReader;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
    private JsonReader jsonReader;

    @BeforeSuite
    public void setUpSuite() {
        jsonReader = new JsonReader("data-provider");
    }

    public JsonReader jsonReader() {
        return jsonReader;
    }
}
