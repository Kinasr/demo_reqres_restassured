package helpers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utilities.JsonManager;
import utilities.MyLogger;

import java.util.regex.Pattern;

/**
 * A helper class to manage reading form JSON files
 */
public class JsonReader {
    private final String fileName;
    private final JsonManager jsonManger;
    // to trace the JSON series
    private Object currentObject = null;

    /**
     * Contractor to initialize the JsonManager instance.
     * @param fileName the name of the json file without path and file extension ".json"
     */
    public JsonReader(String fileName) {
        this.fileName = fileName;
        jsonManger = new JsonManager(Constants.TEST_RESOURCES_PATH + fileName);
    }

    /**
     * @return all the JSON file content as Object
     */
    public JSONObject getJsonAsObject() {
        // TODO: Handle if this file called but the JSON file contains Array
        return jsonManger.getJsonObject();
    }

    /**
     * @return all the JSON file content as array
     */
    public JSONArray getJsonAsArray() {
        // TODO: Handle if this file called but the JSON file contains Object
        return jsonManger.getJsonArray();
    }

    /**
     * Check if the currentObject is null it will fetch the content of the JSON file as Object then get
     * the value of key then put it in the currentObject.
     * And if the currentObject has a value it will get the JSON value by key and put it in the currentObject.
     * @param key the key of the JSON value
     * @return the current instance from JsonReader
     */
    public JsonReader getObject(String key) {
        currentObject = currentObject == null ? getJsonAsObject().get(key)
                : ((JSONObject) currentObject).get(key);

        return this;
    }

    /**
     * Check if the currentObject is null it will fetch the content of the JSON file as Array then get
     * the value of key then put it in the currentObject.
     * And if the currentObject has a value it will get the JSON value by key and put it in the currentObject.
     * @param index the index of the JSON Object in the Array
     * @return the current instance from JsonReader
     */
    public JsonReader getFromArray(int index) {
        var arrayObject = currentObject == null ? getJsonAsArray()
                : (JSONArray) currentObject;

        if (index < arrayObject.size())
            currentObject = arrayObject.get(index);
        else {
            MyLogger.severe(JsonReader.class.getSimpleName(), "The Index is out of border in "
                    + fileName + ".json file");
        }

        return this;
    }

    /**
     * Decode the JSON using the JSON key series like "key1.key2[index].key3"
     * @param series the JSON series
     * @return the current instance from JsonReader
     */
    public JsonReader get(String series) {
        var jsonList = series.split("\\.");

        for (String json : jsonList) {
            if (json.matches("[a-z-]*\\[\\d+]")){
                var matcher = Pattern.compile("\\d+").matcher(json);

                json = json.replaceAll("\\[\\d+]", "");
                this.getObject(json);

                while (matcher.find())
                    this.getFromArray(Integer.parseInt(matcher.group(0)));
            } else {
                this.getObject(json);
            }
        }

        return this;
    }

    /**
     * Converting the JSONArray to an Array then reset the currentObject
     * @return Array
     */
    public Object[] toArray() {
        var array = ((JSONArray) currentObject).toArray();
        currentObject = null;

        return array;
    }

    /**
     * Converting the JSONObject to a String then reset the currentObject
     * @return String
     */
    @Override
    public String toString() {
        var string = currentObject.toString();
        currentObject = null;

        return string;
    }

    public int toInt() {
        var number = Integer.parseInt(currentObject.toString());
        currentObject = null;

        return number;
    }

    public JSONObject toJsonObject() {
        var jsonObject = (JSONObject) currentObject;
        currentObject = null;

        return jsonObject;
    }

    public JSONArray toJsonArray() {
        var jsonArray = (JSONArray) currentObject;
        currentObject = null;

        return jsonArray;
    }
}
