package Utility;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONUtility {
    /**
     * Read JSON File
     * @param filePath
     * @return
     */
    public JSONArray readJSON(String filePath) {
        JSONParser jsonParser = new JSONParser();
        JSONArray itemsList = new JSONArray();
        try {
            FileReader reader = new FileReader(filePath);
            //Read JSON File
            Object obj = jsonParser.parse(reader);
            itemsList = (JSONArray) obj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return itemsList;
    }

    /**
     * write JSON file
     * @param filePath
     * @param itemsList
     */
    public void writeJSON(String filePath, JSONArray itemsList) {
        try (FileWriter file = new FileWriter(filePath)) {
            file.append(itemsList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
