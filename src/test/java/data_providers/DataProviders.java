package data_providers;

import helpers.JsonReader;
import models.MyDataRecords.ResourceData;
import models.MyDataRecords.UserData;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class DataProviders {
    @DataProvider(name = "list-users")
    public static Object[][] gerListUsersData() {
        var source = "list-users.positive";

        var dataList = new ArrayList<String>();
        dataList.add("page<int>");
        dataList.add("users-per-page<int>");

        return setDataInTwoDimensionalObject("users-data", source, dataList, null);
    }

    @DataProvider(name = "single-user")
    public static Object[][] getSingleUserData() {
        var source = "single-user.users";
        var dataList = new ArrayList<String>();
        dataList.add("id<int>");
        dataList.add("email");
        dataList.add("first-name");
        dataList.add("last-name");
        dataList.add("avatar");

        return setDataInTwoDimensionalObject("users-data", source, dataList, object -> {
            var obj = new Object[object.length][1];
            for (var i = 0; i < object.length; i++) {
                obj[i][0] = new UserData(
                        (int) object[i][0],
                        (String) object[i][1],
                        (String) object[i][2],
                        (String) object[i][3],
                        (String) object[i][4]
                );
            }
            return obj;
        });
    }

    @DataProvider(name = "list-resources")
    public static Object[][] getListResourcesData() {
        var source = "list-resources.positive";
        var dataList = new ArrayList<String>();
        dataList.add("page<int>");
        dataList.add("number-of-resources-per-page<int>");

        return setDataInTwoDimensionalObject("resources-data", source, dataList, null);
    }

    @DataProvider(name = "single-resource")
    public static Object[][] getSingleResourceData() {
        var source = "single-resource.resources";
        var dataList = new ArrayList<String>();
        dataList.add("id<int>");
        dataList.add("name");
        dataList.add("year<int>");
        dataList.add("color");
        dataList.add("pantone_value");

        return setDataInTwoDimensionalObject("resources-data", source, dataList, object -> {
            var obj = new Object[object.length][1];

            for (var i = 0; i < object.length; i++) {
                obj[i][0] = new ResourceData(
                        (int) object[i][0],
                        (String) object[i][1],
                        (int) object[i][2],
                        (String) object[i][3],
                        (String) object[i][4]
                );
            }
            return obj;
        });
    }

    @DataProvider(name = "invalid-user-creation")
    public static Object[][] getInvalidUserData() {
        var source = "create-user.invalid-parameters";

        return setDataInTwoDimensionalObject("users-data", source, List.of(""), null);
    }

    @DataProvider(name = "invalid-user-update")
    public static Object[][] getInvalidUpdateUserData() {
        var source = "update-user.invalid-parameters";

        return setDataInTwoDimensionalObject("users-data", source, List.of("name", "job"), null);
    }

    @DataProvider(name = "invalid-account-data")
    public static Object[][] getInvalidAccountData() {
        var source = "invalid";

        return setDataInTwoDimensionalObject("account-data", source, List.of(""), null);
    }

    private static Object[][] setDataInTwoDimensionalObject(String fileName, String jsonSource, List<String> jsonPaths,
                                                            Function<Object[][], Object[][]> callback) {
        var jsonReader = new JsonReader(fileName);
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
        return callback == null ? object : callback.apply(object);
    }
}
