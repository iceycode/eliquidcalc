package com.icey.apps.data;

import com.badlogic.gdx.utils.Array;

/** Flavor class - stores flavor info
 *
 * TODO: consider making this a subclass of Supply
 * Created by Allen on 1/12/15.
 */
public class Flavor {

    private String name; //name of flavor
    private int type = -1; //1=PG, 2=VG, 3=Other
    
    public int key; //flavor's key when stored in supply
    
    private double totalAmount = 0; //total amount user has in supply

    //the values for calculating
    private int percent = -1; //percent desired
    private double amount = 0; //amount needed

    //no-arg constructor for reconstructing flavors from json
    public Flavor (){
    }
    
    public Flavor(Supply supply){
        this.name = supply.getName();
        this.totalAmount = supply.getTotalAmount();
        this.type = supply.getFlavorType();
    }
    
    //when typing in on Calculator screen
    public Flavor(String name){
        this.name = name;
    }
    
    //constructor for when adding to supply (in SupplyScreen)
    public Flavor(double amount, String name){
        this.totalAmount = amount;
        this.name = name;
    }


    //constructor that initializes with name, percent & type
    public Flavor(String name, int percent, int type){
        this.name = name;
        this.percent = percent;
        this.type = type;
    }


    /** returns the amount of this flavor for recipe, or -1 if cannot
     * - based on percent of flavor & end amount desired
     *
     * @param amountDesired
     * @return amount required b
     */
    public double calcRecipeAmount(double amountDesired){
        if (percent == -1 || type == -1)
            return -1;

        this.amount = amountDesired*((double)percent/100);
        return amount;
    }


    /** sets values in the CalcUtils for finalMills
     * based on index of flavor, number of flavors in list
     *
     * @param finalMills : the final calculations
     */
    public double recalcAmount(double amountDesired, Array<Double> finalMills ){
        if (type == 2){
            amountDesired -= amount;
        }
        else{
            finalMills.set(type, finalMills.get(type).doubleValue() - amount);
        }

        return amountDesired;
    }


    public double recalcAmount_Alt(double amountDesired, Array<Double> finalMills, double otherAmt){
        if (type == 2){
            if (otherAmt >= amount) //only if > 0, then  update
                finalMills.set(type, otherAmt - amount); //recalculate amount of "other" to be used

            amountDesired -= amount;
        }
        else{
            finalMills.set(type, finalMills.get(type).doubleValue() - amount);
        }

        return amountDesired;
    }

    /** checks to see if flavor values are set as supply
     *
     * @return true if meets criteria to being set, false otherwise
     */
    public boolean isFlavorSet(){
        if (type == -1 || name==null || percent==-1)
            return false;

        return true;
    }
    
    
    public String getTypeName(){
        if (type == 0)
            return "PG";
        else if (type == 1)
            return "VG";

        return "Other";
    }
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }


    public double getAmount(){
        return amount;
    }

    //the amount in the supply
    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void log(String message){
        System.out.println("Flavor LOG: " + message);
    }
}
