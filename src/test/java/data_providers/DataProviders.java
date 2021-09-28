package data_providers;

import helpers.JsonReader;
import models.MyDataRecords.ResourceData;
import models.MyDataRecords.UserData;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class DataProviders {
    private static final JsonReader jsonReader;

    static {
        jsonReader = new JsonReader("data-provider");
    }

    @DataProvider(name = "list-users")
    public static Object[][] gerListUsersData() {
        var source = "list-users.positive";

        var dataList = new ArrayList<String>();
        dataList.add("page<int>");
        dataList.add("users-per-page<int>");

        return setDataInTwoDimensionalObject(source, dataList);
    }

    @DataProvider(name = "single-user")
    public static Object[][] getSingleUserData() {
        var source = "single-user.users";
        var numOfRows = jsonReader.get(source).toJsonArray().size();
        var object = new Object[numOfRows][1];

        for (var i = 0; i < numOfRows; i++) {
            object[i][0] = new UserData(
                    jsonReader.get(source + "[" + i + "].id").toInt(),
                    jsonReader.get(source + "[" + i + "].email").toString(),
                    jsonReader.get(source + "[" + i + "].first-name").toString(),
                    jsonReader.get(source + "[" + i + "].last-name").toString(),
                    jsonReader.get(source + "[" + i + "].avatar").toString()
            );
        }

        return object;
    }

    @DataProvider(name = "list-resources")
    public static Object[][] getListResourcesData() {
        var source = "list-resources.positive";
        var dataList = new ArrayList<String>();
        dataList.add("page<int>");
        dataList.add("number-of-resources-per-page<int>");

        return setDataInTwoDimensionalObject(source, dataList);
    }

    @DataProvider(name = "single-resource")
    public static Object[][] getSingleResourceData() {
        var source = "single-resource.resources";
        var numOfRows = jsonReader.get(source).toJsonArray().size();
        var object = new Object[numOfRows][1];

        for (var i = 0; i < numOfRows; i++) {
            object[i][0] = new ResourceData(
                    jsonReader.get(source + "[" + i + "].id").toInt(),
                    jsonReader.get(source + "[" + i + "].name").toString(),
                    jsonReader.get(source + "[" + i + "].year").toInt(),
                    jsonReader.get(source + "[" + i + "].color").toString(),
                    jsonReader.get(source + "[" + i + "].pantone_value").toString()
            );
        }
        return object;
    }

    private static Object[][] setDataInTwoDimensionalObject(String jsonSource, List<String> jsonPaths) {
        var numOfRows = jsonReader.get(jsonSource).toJsonArray().size();
        var numOfColumns = jsonPaths.size();
        var object = new Object[numOfRows][numOfColumns];

        for (var i = 0; i < numOfRows; i++) {
            for (var j = 0; j < numOfColumns; j++) {
                var path = jsonSource + "[" + i + "]." + jsonPaths.get(j);
                if (path.contains("<int>")) {
                    path = path.replace("<int>", "");
                    object[i][j] = jsonReader.get(path).toInt();
                } else
                    object[i][j] = jsonReader.get(path).toString();
            }
        }
        return object;
    }
}
