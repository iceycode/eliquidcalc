package com.icey.apps.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.utils.Array;
import com.icey.apps.assets.Constants;
import com.icey.apps.screens.CalculatorScreen;

/** For temporary storage of flavor data while using calculator
 *
 * Created by Allen on 1/14/15.
 */
public class CalcData {
    
    private static CalcData instance;
    
    String recipeName;
    
    double strengthNic;
    double amountDesired;
    double strengthDesired;
    
    Array<Flavor> flavors;
    int numFlavors;
    
    Array<Double> basePercents;
    Array<Double> desiredPercents;

    Array<Double> finalMills;
    
    SaveManager saveManager = new SaveManager(true);

    public CalcData(){

        recipeName = "New Recipe";
        strengthNic = 50; //strength of nicotine default
        amountDesired = 15; //default ml desired
        strengthDesired = 18; //default strength
        
        flavors = new Array<Flavor>();
        numFlavors = 1;

        Double[] nicPercents = {50.0, 50.0, 0.0}; //default
        basePercents = new Array<Double>(nicPercents);
        
        Double[] desPercents = {30.0, 70.0, 0.0}; //default
        desiredPercents = new Array<Double>(desPercents);
        
        Double[] finalAmounts = {0.0, 0.0, 0.0, 0.0, 0.0}; //default values
        finalMills = new Array<Double>(finalAmounts);

    }
    
    public static CalcData getCalcData(){
        if (instance == null){
            instance = new CalcData();
        }
        return instance;
    }
    
    public void setDesiredPercent(String chars, String type){
        desiredPercents.ensureCapacity(1);
        double percent = Double.parseDouble(chars);
        double change = 100 - basePercents.get(2) - percent;
        
        if (type.equals(Constants.DESIRED_PERC_LABELS[0])){
            desiredPercents.set(0, percent);
            updateVG(change);
        }
        else if (type.equals(Constants.DESIRED_PERC_LABELS[1])){
            desiredPercents.set(1, percent);
            updatePG(change);
        }
        else if (type.equals(Constants.DESIRED_PERC_LABELS[2])){
            desiredPercents.set(2, percent);
            change = change/2;
            updatePG(change);
            updateVG(change);
        }
    }

    public void setBasePercent(String chars, String fieldName){
        double percent = Double.parseDouble(chars);
        double change = 100 - basePercents.get(2) - percent;

        if (fieldName.equals(Constants.BASE_PERC_LABELS[0])){
            basePercents.set(0, percent);
            updateBaseVG(change);
        }
        else if (fieldName.equals(Constants.BASE_PERC_LABELS[1])){
            basePercents.set(1, percent);
            updateBasePG(change);
        }
        else if (fieldName.equals(Constants.BASE_PERC_LABELS[2])){
            basePercents.set(2, percent);
            change = change/2;
            updateBasePG(change);
            updateBaseVG(change);
        }
    }

    public void setFlavorPercent(String chars, int index){
        if (flavors.size > 0) {
            flavors.get(index).setPercent(Double.parseDouble(chars));
        }
    }
    
    public void setFlavorType(CheckBox box, int index){
        if (box.getName().contains("PG")){
            flavors.get(index).setType(0);
        }
        else if (box.getName().contains("VG")){
            flavors.get(index).setType(1);
        }
        else{
            flavors.get(index).setType(2);
        }
        uncheckBoxes(box, index);
        
    }

    public void uncheckBoxes(CheckBox box, int key){
        for (CheckBox b: CalculatorScreen.getCalculatorScreen().checkBoxMap.get(key)){
            if (!box.equals(b) && b.isChecked())
                b.setChecked(false);
        }

    }
    
    public void updatePG(double change){
        desiredPercents.set(0, change);
        CalculatorScreen.getCalculatorScreen().percentTextFields.get(0).setText(Double.toString(change));
    }

    public void updateVG(double change){
        desiredPercents.set(1, change);
        CalculatorScreen.getCalculatorScreen().percentTextFields.get(1).setText(Double.toString(change));
    }

    public void updateBasePG(double change){
        basePercents.set(0, change);
        CalculatorScreen.getCalculatorScreen().percentTextFields.get(3).setText(Double.toString(change));
    }

    public void updateBaseVG(double change){
        basePercents.set(1, change);
        CalculatorScreen.getCalculatorScreen().percentTextFields.get(4).setText(Double.toString(change));
    }


    public void calcAmounts(){

        finalMills.ensureCapacity(4 + flavors.size);
        double baseAmt = (strengthDesired/strengthNic)*amountDesired;
        finalMills.set(3, baseAmt);

        double otherPGAmts = baseAmt*(basePercents.get(0).doubleValue()/100);
        double otherVGAmts = baseAmt*(basePercents.get(1).doubleValue()/100);
        double otherAmts = baseAmt*(basePercents.get(2).doubleValue()/100);

        double flavAmt = 0;
        //get nicotine PG amounts
        for (Flavor f : flavors){
            flavAmt = amountDesired*(f.getPercent()/100);
            if (flavors.size > 1) //since start with 1
                finalMills.add(flavAmt);
            else
                finalMills.set(4, flavAmt);

            switch(f.getType()){
                case 1:
                    otherPGAmts += flavAmt;
                    break;
                case 2:
                    otherVGAmts += flavAmt;
                    break;
                case 3:
                    otherAmts += flavAmt;
                    break;
            }
        }

        double pgAmt = amountDesired*(desiredPercents.get(0).doubleValue()/100) - otherPGAmts;
        double vgAmt = amountDesired*(desiredPercents.get(1).doubleValue()/100) - otherVGAmts;
        double otherAmt = amountDesired*(desiredPercents.get(2).doubleValue()/100) - otherAmts;

        finalMills.set(0, pgAmt);
        finalMills.set(1, vgAmt);
        finalMills.set(2, otherAmt);
    }
    
    public boolean areValuesSet(){
        for (Flavor f : flavors){
            if (f.getType() == -1)
                return false;
            if (f.getPercent() == -1)
                return false;
        }

        return true;
    }
   

    public void setRecipeName(String name){
        this.recipeName = name;
    }
        
    public void setAmountDesired(double amountDesired){
        this.amountDesired = amountDesired;
    }

    public void setFlavorName(String name, int index){
        flavors.get(index).setName(name);
    }
    
    public void addFlavor(Flavor flavor){
        flavors.add(flavor);
        numFlavors = flavors.size;
        log("Added a new flavor, " + flavor.getName() + "; size of flavors array = " + flavors.size);
    }

    public Array<Flavor> getFlavors() {
        return flavors;
    }

    public Array<Double> getFinalMills() {
        return finalMills;
    }

    public double getStrengthNic() {
        return strengthNic;
    }

    public double getAmountDesired() {
        return amountDesired;
    }

    public double getStrengthDesired() {
        return strengthDesired;
    }

    public Array<Double> getBasePercents() {
        return basePercents;
    }

    public Array<Double> getDesiredPercents() {
        return desiredPercents;
    }

    public String getRecipeName() {
        return recipeName;
    }


    public static class RecipeData{
        public String recipeName;
        public double strengthNic;
        public double amountDesired;
        public double strengthDesired;
        public Array<Double> basePercents;
        public Array<Double> desiredPercents;
        public Array<Flavor> flavors;
        public Array<Double> finalMills;
    }

    public void saveData(boolean encode){
        RecipeData recipeData = new RecipeData();
        recipeData.recipeName = this.recipeName;
        recipeData.strengthNic = this.strengthNic;
        recipeData.amountDesired = this.amountDesired;
        recipeData.strengthDesired = this.strengthDesired;
        recipeData.flavors = this.flavors;
        recipeData.basePercents = this.basePercents;
        recipeData.desiredPercents = this.desiredPercents;
        recipeData.finalMills = this.finalMills;

        saveManager.saveDataValue(recipeName, new RecipeData());
    }
    
    public Array<String> getAllRecipes(){
        Array<String> recipeTitles = new Array<String>();
        
        for (String title : saveManager.getAllData().keys())
            recipeTitles.add(title);
        
        return recipeTitles;
    }
    
    public void loadData(String name, boolean encoded){
        RecipeData data = (RecipeData)saveManager.loadDataValue(name, RecipeData.class);
        recipeName = data.recipeName;
        amountDesired = data.amountDesired;
        strengthDesired = data.strengthDesired;
        desiredPercents = data.desiredPercents;
        
        //base strengths
        strengthNic = data.strengthNic;
        basePercents = data.basePercents;
        
        //flavors
        flavors = data.flavors;
        
        //final amounts
        finalMills = data.finalMills;
    }


    private void log(String message){
        Gdx.app.log("CalcData LOG: ", message);
    }
}
