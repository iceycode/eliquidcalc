package com.icey.apps.utils;

import com.icey.apps.data.CalcData;
import com.icey.apps.data.Flavor;

/**
 * Created by Allen on 1/16/15.
 */
public class Calculator {


    public static void calcAmounts(CalcData calcData){
        //calcData.getFinalMills().ensureCapacity(4 + flavors.size);
        double baseAmt = (calcData.getStrengthDesired()/calcData.getStrengthNic())*calcData.getAmountDesired();
        calcData.getFinalMills().set(3, baseAmt);

        double otherPGAmts = baseAmt*(calcData.getBasePercents().get(0).doubleValue()/100);
        double otherVGAmts = baseAmt*(calcData.getBasePercents().get(1).doubleValue()/100);
        double otherAmts = baseAmt*(calcData.getBasePercents().get(2).doubleValue()/100);

        double flavAmt = 0;
        //get nicotine PG amounts
        for (Flavor f : calcData.getFlavors()){
            flavAmt = calcData.getAmountDesired()*(f.getPercent()/100);
            if (calcData.getFlavors().size > 1) //since start with 1
                calcData.getFinalMills().add(flavAmt);
            else
                calcData.getFinalMills().set(4, flavAmt);

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

        double pgAmt = calcData.getAmountDesired()*(calcData.getDesiredPercents().get(0).doubleValue()/100) - otherPGAmts;
        double vgAmt = calcData.getAmountDesired()*(calcData.getDesiredPercents().get(1).doubleValue()/100) - otherVGAmts;
        double otherAmt = calcData.getAmountDesired()*(calcData.getDesiredPercents().get(2).doubleValue()/100) - otherAmts;

        calcData.getFinalMills().set(0, pgAmt);
        calcData.getFinalMills().set(1, vgAmt);
        calcData.getFinalMills().set(2, otherAmt);
    }

}
