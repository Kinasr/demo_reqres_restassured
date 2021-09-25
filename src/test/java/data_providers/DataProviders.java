package data_providers;

import helpers.JsonReader;
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
        dataList.add("user.index<int>");
        dataList.add("user.email");

        return setDataInTwoDimensionalObject(source, dataList);
    }

    @DataProvider(name = "single-user")
    public static Object[][] getSingleUserData() {
        var source = "single-user.users";

        var dataList = new ArrayList<String>();
        dataList.add("id<int>");
        dataList.add("email");
        dataList.add("first_name");
        dataList.add("last_name");

        return setDataInTwoDimensionalObject(source, dataList);
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
