package com.icey.apps.utils;

/**
 * Created by Allen on 1/12/15.
 */
public class CalcTools {

    /** The Calculation for the amount of nicotine needed
     * (Desired Strength / Concentrated Nicotine Strength) x Bottle size = Amount needed (in milliliters)
     *
     * @param baseStr
     * @param desiredStr
     * @param desiredAmt
     * @return
     */
    public static double calcNicBaseStr(double baseStr, double desiredStr, double desiredAmt ){
        return (desiredStr/baseStr)*desiredAmt;
    }

    /** The calculation for amount of PG/VG to be added based on total amount
     *
     *
     * @param baseLiqConc
     * @param baseAmt
     * @param desiredLiqConc
     * @param desiredAmt
     * @return
     */
    public static double calcLiquidAmounts(double[] baseLiqConc, double baseAmt, double[] desiredLiqConc, double desiredAmt){
        return 1;
    }


}
