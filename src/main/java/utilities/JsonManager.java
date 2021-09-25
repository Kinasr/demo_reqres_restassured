package utilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public record JsonManager(String filePath) {
    // TODO: add validation if the user request JSONObject but it was array,
    //  and if the user request JSONArray but it was Object

    /**
     * Loads the JSON file from the filePath that will be received form the record
     * @return the content of the JSON file as Object
     */
    private Object loadJsonFile() {
        var jsonParser = new JSONParser();
        Object json = null;

        try (FileReader reader = new FileReader(filePath + ".json")) {
            //Read JSON file
            json = jsonParser.parse(reader);
        } catch (ParseException | IOException e) {
            MyLogger.severe(JsonManager.class.getSimpleName(), "Can't read data from: " + filePath);
            e.printStackTrace();
        }
        return json;
    }

    /**
     * Casting the Object to JSONObject
     * @return JSONObject
     */
    public JSONObject getJsonObject() {
        return (JSONObject) loadJsonFile();
    }

    /**
     * Casting the Object to JSONArray
     * @return JSONArray
     */
    public JSONArray getJsonArray() {
        return (JSONArray) loadJsonFile();
    }


}
