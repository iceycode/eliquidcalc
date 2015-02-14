package com.icey.apps.data;

import com.badlogic.gdx.utils.Array;

/** The user defaults
 * - these get set by user in options menu
 * - will set the fields on the calculator screen
 *
 * Created by Allen on 2/7/15.
 */
public class UserDefaults {

    //----User constants---these can be altered by user in settings
    //default base strength & percents - user can change in settings
    public static double DEFAULT_FINAL_AMT = 30.0;
    public static Integer[] DESIRED_PERCENTS = {70, 30, 0};
    public static Array<Integer> DEFAULT_DESIRED_PERCENTS = new Array<Integer>(DESIRED_PERCENTS);
    public static double DESIRED_STR = 16.0; //desired strength (medium)

    //base defaults - user will be able to change in settings
    public static double DEFAULT_BASE_STR = 100.0;
    public static Integer[] BASE_PERCENTS = {0, 0};
    public static Array<Integer> DEFAULT_BASE_PERCENTS = new Array<Integer>(BASE_PERCENTS);
    public static Base DEFAULT_BASE = new Base(0, DEFAULT_BASE_STR, DEFAULT_BASE_PERCENTS);

    //flavor default - the go-to flavor for user, can change in settings
    public static double DEFAULT_FLAV_AMT = 0; //amount of the flavor supply
    public static String DEFAULT_FLAV_NAME = "Flavor1"; //name of flavor
    public static Flavor FLAVOR_DEFAULT = new Flavor(0, DEFAULT_FLAV_NAME);

    public static Double[] DEFAULT_GOAL_AMOUNTS = {0.0, 0.0, 0.0, 0.0, 0.0}; //default values
    public static Array<Double> INITLAL_FINAL_MLS = new Array<Double>(DEFAULT_GOAL_AMOUNTS);
}
