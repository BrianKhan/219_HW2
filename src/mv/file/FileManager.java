/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mv.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import mv.data.DataManager;
import mv.data.RegionItem;
import saf.components.AppDataComponent;
import saf.components.AppFileComponent;

/**
 *
 * @author McKillaGorilla
 * @author Brian  Khaneyan
 */
public class FileManager implements AppFileComponent {

    boolean loaded;

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
        //Clear old data
        DataManager dataManager = (DataManager) data;
        dataManager.reset();
        // load JSON
        JsonObject json = loadJSONFile(filePath);

        JsonArray jsonItemArray = json.getJsonArray("SUBREGIONS");
        for (int i = 0; i < jsonItemArray.size(); i++) {
            //returns array of subregion polygons
            JsonObject jsonItem = jsonItemArray.getJsonObject(i);
            JsonArray jsonSecond = jsonItem.getJsonArray("SUBREGION_POLYGONS");
            for (int j = 0; j < jsonSecond.size(); j++) {
                // we go through this array and get each item, send it to loaditem
                // note that we are sending an array of points to loaditem, but each array of points is one polygon
                JsonArray jsonRegion = jsonSecond.getJsonArray(j);
                RegionItem item = loadItem(jsonRegion);
                dataManager.addItem(item);
            }
        }
        loaded = true;
    }

    public boolean getLoaded() {
        return loaded;
    }

    public RegionItem loadItem(JsonArray jsonItem) {
        RegionItem region = new RegionItem();
        // we go through each set of points in the polygon and return it as a region item
        for (int i = 0; i < jsonItem.size(); i++) {
            JsonObject myItem = jsonItem.getJsonObject(i);
            double x = myItem.getJsonNumber("X").doubleValue();
            double y = myItem.getJsonNumber("Y").doubleValue();
            region.add(x, y);
        }

        return region;

    }

    public double getDataAsDouble(JsonObject json, String dataName) {
        JsonValue value = json.get(dataName);
        JsonNumber number = (JsonNumber) value;
        return number.bigDecimalValue().doubleValue();
    }

    public int getDataAsInt(JsonObject json, String dataName) {
        JsonValue value = json.get(dataName);
        JsonNumber number = (JsonNumber) value;
        return number.bigIntegerValue().intValue();
    }

    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
        JsonReader jsonReader = Json.createReader(is);
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        is.close();
        return json;
    }

    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
