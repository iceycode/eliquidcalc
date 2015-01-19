package com.icey.apps.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.ObjectMap;

/** This saves the recipes
 * 
 * A lot of this is taken from:
 * http://www.toxsickproductions.com/libgdx/libgdx-intermediate-saving-and-loading-data-from-files/  
 * 
 * Saves the recipes into an ObjectMap which is then written to a json file
 * 
 *
 * Created by Allen on 1/11/15.
 */
public class SaveManager {
    
    private boolean encoded = true; //default is true
    private Save save = getSave();
    
    //local since internal files are read only & want to write to this json file
    public FileHandle saveFile; //bin since classes compiled there

    
    public SaveManager(boolean encoded){
        this.encoded = encoded;
        save = getSave();
    }

    //the save class is an object map (string is the name of the recipe)
    private static class Save{
        private ObjectMap<String, Object> data = new ObjectMap<String, Object>();
    }

    private Save getSave(){
        Save save = new Save();
        saveFile = Gdx.files.local("save.json");

        if(saveFile.exists()){
            Json json = new Json();
            if(encoded)
                save = json.fromJson(Save.class, Base64Coder.decodeString(saveFile.readString()));
            else 
                save = json.fromJson(Save.class, saveFile.readString());
        }
        
        return save;
    }
    
    //saves to a json file
    public void saveToJson(){
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        if(encoded) 
            saveFile.writeString(Base64Coder.encodeString(json.prettyPrint(save)), false);
        else 
            saveFile.writeString(json.prettyPrint(save), false);
        
        log("saved RecipeData into json file as ObjectMap value");
    }

    @SuppressWarnings("unchecked")
    public Object loadDataValue (String key, Class type){
        if(save.data.containsKey(key))
            return save.data.get(key);
        else 
            return null;   //this if() avoids and exception, but check for null on load.
    }
    
    public void saveDataValue(String key, Object object){
        save.data.put(key, object);
        saveToJson(); //Saves current save immediatly.
    }
    
    public ObjectMap<String, Object> getAllData(){
        return save.data;
    }


    private void log(String message){
        Gdx.app.log("SaveManager LOG: ", message);
    }
}
