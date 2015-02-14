package com.icey.apps.data;

import com.badlogic.gdx.utils.Array;

/** The class which stores info about nicotine base solution
 * - stores info about the percentage PG/VG (other - this is rare)
 * - base strength
 * - the amount that user has of this supply
 * - calculates the amount needed for recipe
 *      - also returns an array of modified plain PG/VG/Other values
 *
 * TODO: keep this as subclass of Supply IF it works
 *
 * Created by Allen on 1/22/15.
 */
public class Base extends Supply{

//    //strength & percents set to default values
//    double baseStrength = -1;
//    Array<Integer> basePercents = new Array<Integer>();
//
//    double totalAmount = -1; //the amoutn in the supply
//    double amountNeeded = 0; //the amount needed for recipe

    //no-arg constructor for json reader (default base)
    public Base(){} 


    
    public Base(double strength, Array<Integer> percents) {
        this.baseStrength = strength;
        this.basePercents = percents;
    }

    //constructor for supply of base
    public Base(double amount, double strength, Array<Integer> percents){
        this.totalAmount = amount;
        this.baseStrength = strength;
        this.basePercents = percents;
    }
    
    //for getting Base from Supply
    public Base (Supply supply){
        this.totalAmount = supply.getTotalAmount();
        this.basePercents = supply.getBasePercents();
        this.baseStrength = supply.getBaseStrength();
    }

    //returns the amount of nicotine base needed
    public double getRecipeAmount(double amountDesired, double strengthDesired){
        this.amountNeeded = (strengthDesired/baseStrength)*amountDesired;
        return amountNeeded;
    }

    /** modifies the final amount of PG, VG amounts,
     *  & the amount of base needed
     *
     * @param finalMills: final amount - will get updated
     * @return - updated PG, VG & Other amounts in array
     */
    public void recalcLiquidAmts(Array<Double> finalMills){
        for (int i = 0; i < 2; i++){
            double amt = finalMills.get(i).doubleValue() - (amountNeeded*(basePercents.get(i).doubleValue()/100));
            finalMills.set(i, amt);
        }
    }

    /** checks to see that base is set
     *
     * @return
     */
    public boolean isBaseSet(){
        if (!areBasePercentsSet() || baseStrength == -1 || totalAmount == -1)
            return false;

        return true;
    }


    /** checks to see if the base percents are set
     *
     * @return
     */
    public boolean areBasePercentsSet(){
        int maxPercent = 0;

        for (Integer i : basePercents) maxPercent += i;

        if (maxPercent != 100)
            return false;

        return true;
    }


//    public double getBaseStrength() {
//        return baseStrength;
//    }
//
//    public void setBaseStrength(double baseStrength) {
//        this.baseStrength = baseStrength;
//    }
//
//    public double getTotalAmount() {
//        return totalAmount;
//    }
//
//    public void setTotalAmount(double totalAmount) {
//        this.totalAmount = totalAmount;
//    }
//
//
//    public Array<Integer> getBasePercents() {
//        return basePercents;
//    }
//
//    //sets all the base percents in one shot
//    public void setBasePercents(Array<Integer> basePercents) {
//        this.basePercents = basePercents;
//    }


    /** sets the propylene glycol percent
     * 
     * @param pgPercent : the pgPercent user entered
     */
    public void setPgPercent(int pgPercent) {
        int change = 100 - pgPercent;
        basePercents.set(0, pgPercent);
        basePercents.set(1, change);

    }


    /** sets the vegetable glycerin
     *
     * @param vgPercent : vgPercent user entered
     */
    public void setVgPercent(int vgPercent) {
        int change = 100 - vgPercent;
        basePercents.set(1, vgPercent);
        basePercents.set(0, change);

    }

}
