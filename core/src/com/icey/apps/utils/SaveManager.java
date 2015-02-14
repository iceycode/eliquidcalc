package com.icey.apps.utils;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.*;
import com.google.gson.Gson;
import com.icey.apps.assets.Constants;
import com.icey.apps.data.Base;
import com.icey.apps.data.Flavor;


//incorrect String - causes serialization trace
//since CalcData changed to CalcUtils & RecipeData nested class in CalcData
/** Manages save data for recipes
 * 
 * A lot of this is taken from:
 * http://www.toxsickproductions.com/libgdx/libgdx-intermediate-saving-and-loading-data-from-files/
 *  - tweaked out a bit to fit my own needs
 * - saves into class Save which is encoded into jsonEnable file
 *  TODO: setup FB account, google plus & email account options to store in Save or Preferences
 *
 * Created by Allen on 1/11/15.
 */
public class SaveManager {
    
//    private static SaveManager instance;
    
    private boolean encoded = true; //Bas64Encoder enabled by default
    private boolean jsonEnable = false; //the type of serializer used - Json vs Gson

    private Save save;

    private Preferences userPrefs; //user preferences store basic info (name, email, etc)

    //local since internal files are read only & want to write to this jsonEnable file
    private FileHandle saveFile;

    
    public SaveManager(boolean encoded, boolean json){
        this.encoded = encoded;
        this.jsonEnable = json;
        
        if (encoded){
            this.saveFile = new FileHandle(Constants.SAVE_FILE_ENCODED);
        }
        else{
            this.saveFile = new FileHandle(Constants.SAVE_FILE_NAME);
        }
        
        this.save = getSave();
    }
    
    
    //alterantive constructor, for saving data
    public SaveManager(boolean encoded, boolean json, FileHandle file){
        this.encoded = encoded;
        this.jsonEnable = json;
        this.saveFile = file;
        
        this.save = getSave();
    }

    public Save getSave(){
        Save save = new Save();

        if(saveFile.exists()){
            if (jsonEnable){
                Json json = new Json();

                if (encoded) 
                    save = json.fromJson(Save.class, Base64Coder.decodeString(saveFile.readString()));
                else 
                    save = json.fromJson(Save.class, saveFile.readString());
            }
            else{
                Gson gson = new Gson();

                if (encoded) 
                    save = gson.fromJson(Base64Coder.decodeString(saveFile.readString()), Save.class);
                else 
                    save = gson.fromJson(saveFile.readString(), Save.class);
            }
        }

        if (save == null)
            return new Save();

        return save;
    }
    
    //saves to a jsonEnable file
    public void saveToJson(){
        if (this.jsonEnable){
            Json json = new Json();
            json.setOutputType(JsonWriter.OutputType.json);
            
            if (encoded) 
                saveFile.writeString(Base64Coder.encodeString(json.prettyPrint(save)), false);
            else 
                saveFile.writeString(json.prettyPrint(save), false);
            
        }
        else{
            Gson gson = new Gson();
//            Type dataType = new TypeToken<Save>() {}.getType();

            if (encoded) 
                saveFile.writeString(Base64Coder.encodeString(gson.toJson(save)), false);
            else 
                saveFile.writeString(gson.toJson(save), false);
            
        }
        
        log("saved with Json : " + jsonEnable + "/ Gson : " + !jsonEnable);
    }
    
    /** Loads the data from Save class
     *
     * @param key
     * @return
     */
    public Object loadRecipeData(String key) {
        log("Key = " + key);

        if (save.data.containsKey(key))
            return save.data.get(key);
        else
            return null;   //this if() avoids an exception, but check for null on load.
    }

    /** gets the recipe data into the CalcUtil class
     *
     * @param name
     */
    public void getRecipeData(String name, CalcUtils calcUtils){

        try {
            RecipeData data = (RecipeData) loadRecipeData(name);
            calcUtils.setRecipeName(data.recipeName);
            calcUtils.setAmountDesired(data.amountDesired);
            calcUtils.setStrengthDesired(data.strengthDesired);
            calcUtils.setDesiredPercents(data.desiredPercents);

            //Base
            calcUtils.setBase(data.base);

            //base strengths
            calcUtils.setBaseStrength(data.strengthNic);
            calcUtils.setBasePercents(data.basePercents);

            //flavors
            calcUtils.setFlavors(data.flavors);

            calcUtils.setFinalMills(data.finalMills); //final amounts

            calcUtils.loaded = true; //calcUtils now loaded with values
        }
        catch (NullPointerException e){
            log("Missing recipe data!" + e.toString());
        }
    }


    //------------Methods for saving recipes-------------

    //saves using Json as serializer
    public void saveRecipeData(String key, RecipeData data){
        save.data.put(key, data);

        saveToJson();
    }


//
//    public void renameSavedRecipe(String newName, String oldName){
//        Object data = save.data.get(oldName);
//        save.data.remove(oldName);
//        save.data.put(newName, data);
//
//        if (jsonEnable) saveToJson();
//        else save_Gson();
//    }
    
    public void deleteRecipe(String name){
        save.data.remove(name);
    }
    

    public ObjectMap<String, Object> getRecipeData(){
        return save.data;
    }
    
    
    public void setEncoded(boolean encoded) {
        this.encoded = encoded;
    }

    public void setSave(Save save){
        this.save = save;
    }


    private void log(String message){
        System.out.println("SaveManager LOG: " + message);
    }


    //==================Nested save classes for accessing data==================
    // Save class which becomes serialized into JSON format
    public static class Save {
        public ObjectMap<String, Object> data = new ObjectMap<String, Object>();
    }

    /** Recipe Data
     * - contains data relating to the recipe made
     */
    public static class RecipeData{
        public String recipeName;

        public double amountDesired;
        public double strengthDesired;
        public Array<Integer> desiredPercents;

        public Base base;
        public double strengthNic;
        public Array<Integer> basePercents;

        public Array<Flavor> flavors;

        public Array<Double> finalMills;
    }
}