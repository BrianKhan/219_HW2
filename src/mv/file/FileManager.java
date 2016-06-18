/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mv.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
 */
public class FileManager implements AppFileComponent {

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
        //Clear old data
        DataManager dataManager = (DataManager) data;
        dataManager.reset();
        // load JSON
        JsonObject json = loadJSONFile(filePath);
        
        JsonArray jsonItemArray = json.getJsonArray("SUBREGIONS");
        for(int i = 0; i < jsonItemArray.size();i++) {
            //returns array of subregion polygons
            JsonObject jsonItem = jsonItemArray.getJsonObject(i);
            JsonArray jsonSecond = jsonItem.getJsonArray("SUBREGION_POLYGONS");            
            for (int j = 0; j < jsonSecond.size();j++) {
                JsonArray jsonRegion = jsonSecond.getJsonArray(j);
                
                 RegionItem item = loadItem(jsonSecond);
                 dataManager.addItem(item);
            }
        }
        
    }
    public RegionItem loadItem(JsonArray jsonItem) {
        for(int i = 0; i < jsonItem.size(); i++) {
            
        }
        RegionItem test = new RegionItem() ;
            return test;
        
    }
    
    public double getDataAsDouble(JsonObject json, String dataName) {
	JsonValue value = json.get(dataName);
	JsonNumber number = (JsonNumber)value;
	return number.bigDecimalValue().doubleValue();	
    }
    
    public int getDataAsInt(JsonObject json, String dataName) {
        JsonValue value = json.get(dataName);
        JsonNumber number = (JsonNumber)value;
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
