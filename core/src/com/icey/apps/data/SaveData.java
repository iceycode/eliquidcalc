package com.icey.apps.data;

import com.badlogic.gdx.utils.*;
import com.icey.apps.utils.SaveManager;

/** Save class & objects which get saved
 * - Save contains maps of objects which get saved
 * - SupplyData & RecipeData encapsulate saved user parameters
 *      - SupplyData - contains users supply properties
 *          ie, amount of PG, VG; name & amount of flavor, base strength, amount
 *      - RecipeData - contains users' previous recipe parameters
 *          ie, amount desired, strength, etc
 *
 *
 * Created by Allen on 2/3/15.
 */
public class SaveData{
    
    private ObjectMap<String, SaveManager.RecipeData> recipes = new ObjectMap<String, SaveManager.RecipeData>();
    
    private IntMap<Supply> supplies = new IntMap<Supply>();
    
    public SaveData(){
    }

    public ObjectMap<String, SaveManager.RecipeData> getRecipes() {
        return recipes;
    }

    public void setRecipes(ObjectMap<String, SaveManager.RecipeData> recipes) {
        this.recipes = recipes;
    }

    public IntMap<Supply> getSupplies() {
        return supplies;
    }

    public void setSupplies(IntMap<Supply> supplies) {
        this.supplies = supplies;
    }
    
    
    public void addSupply(int key, Supply supply){
        this.supplies.put(key, supply);
    }
    
    public void removeSupply(int key){
        this.supplies.remove(key);
        
    }
    
    public void updateSupply(int key, Supply supply){
        this.supplies.remove(key);
        this.supplies.put(key, supply);
    }
    
    public void addRecipe(String name, SaveManager.RecipeData recipe){
        this.recipes.put(name, recipe);
    }
    
    public void removeRecipe(String name){
        this.recipes.remove(name);
    }
    
    public void updateRecipe(String name, SaveManager.RecipeData recipe){
        this.recipes.remove(name);
        this.recipes.put(name, recipe);
    }

    //    /** Supply Data
//     *
//     * */
//    public static class SupplyData{
//        public IntMap<Supply> supplyMap = new IntMap<Supply>();
//    }



    //        public Save(){}

//        public ObjectMap<String, Object> getRecipeData() {
//            return data;
//        }
//
//        public void setRecipeData(ObjectMap<String, Object> data) {
//            this.data = data;
//        }
//
//        public IntMap<Object> getSupplyMap() {
//            return supplyMap;
//        }
//
//        public void setSupplyMap(IntMap<Object> supplyMap) {
//            this.supplyMap = supplyMap;
//        }

//implements Json.Serializable
//        @Override
//        public void write(Json json) {
//            json.setIgnoreUnknownFields(true);
//            json.addClassTag("saveData", SaveData.class);
//
//            json.writeValue("recipes", recipes, ObjectMap.class);
//            json.writeValue("supplies", supplies, IntMap.class);
//        }
//
//        @Override
//        public void read(Json json, JsonValue jsonData) {
//            json.setIgnoreUnknownFields(true);
//            json.addClassTag("save", SaveData.class);
//
//            recipes = json.readValue("recipes", ObjectMap.class, jsonData);
//            supplies = json.readValue("supplies", IntMap.class, jsonData);
//        }
//    /** Recipe Data
//     * - contains data relating to the recipe made
//     */
//    public static class RecipeData{
//        public String recipeName;
//
//        public double amountDesired;
//        public double strengthDesired;
//        public Array<Integer> desiredPercents;
//
//        public Base base;
//        public double strengthNic;
//        public Array<Integer> basePercents;
//
//        public Array<Flavor> flavors;
//
//        public Array<Double> finalMills;
//    }
}
