package com.icey.apps.assets;

import com.badlogic.gdx.utils.Array;
import com.icey.apps.data.Base;
import com.icey.apps.data.Flavor;

/**
 * Created by Allen on 1/11/15.
 */
public class Constants {

    //--------global constants for app (used anywhere/everywhere)--------
    public static final float SCREEN_HEIGHT = 800f;
    public static final float SCREEN_WIDTH = 480f;

    public static String SAVE_FILE_NAME  = "save.json" ;
    public static String SAVE_FILE_2 = "assets/save/save.json";
    public static String SAVE_FILE_ENCODED = "assets/save/save_encoded.json";


    //=====================Menu Constants=====================-
    public static final String MENU_SKIN = "skins/menu/menuSkin.json"; //skin json file for menu
    public static final String MENU_BACKGROUND_FILE = "textures/main_menu/background.png"; //menu background

    public static final float[] MENU_TITLE_POS = {Constants.SCREEN_WIDTH/2 - 350/2, 400f};
    public static final float[] MENU_TITLE_SIZE = {350, 100};



    //=====================Supply Constants=====================-
    public static final String SUPPLY_MENU_SKIN = "skins/supplies/supplySkin.json";
    public static final String SUPPLY_MENU_BACKGROUND = "textures/supplies/background.png"; //supply menu background
    public static final String SUPPLY_TABLE_BACK = "textures/supplies/supplyWindowBack.png"; //background of table containing current supplies

    public static final String[] PERC_FIELD_NAMES = {"PG %: ", "VG %: ", "Other %: "}; //for percent textfield for base
    public static final String[] SUPPLY_CHECKBOX_TITLES = {"PG ", "VG ", "Other "};

    public static final String DELETE_CHECK = "Are you sure you want to delete?";

    public static final String ERROR_SUPPLY = "Error saving liquid supply.";
    public static final String ERROR_BASE = "Error saving nicotine base.";
    public static final String ERROR_FLAVOR = "Error saving flavor.";
    
    
    public static final String[] SUPPLY_NAMES = {"Propylene Glycol", "Vegetable Glycerin",
            "Other: ", "Nicotine Base", "Flavor: "};


    //=====================Calculator constants =====================
    public static final String CALC_SKIN = "skins/calculator/calcSkin.json"; //calc skin
    public static final String CALC_BACKGROUND = "textures/calculator/calcBackground.png"; //calculator background
    
    public static final String GOAL_TITLE = " Desired Amount, Strength & Ratios";
    public static final String BASE_TITLE = "Nicotine Base";
    public static final String FLAVORS_TITLE = "Flavor(s)";
    
    public static final String FINAL_CALCS_TITLE = "mL (drops)";
    public static final String SUPPLY_AMTS_TITLE = "Supply";
    public static final String[] AMOUNTS_TITLES = {"Amount (mL): ", "Strength (mg): "}; //0=liq ml; 1 = str nicotine

    public static final String[] DESIRED_PERC_LABELS = {"PG %:", "VG %:", "EtOH/H2O/etc %:"};
    public static final String[] BASE_PERC_LABELS = {"Base PG %:", "Base VG %:", "Base EtOH/H2O/etc %:"};
    public static final String[] SLIDER_NAMES = {"desired slider", "base slider"};
    //public static final String[] FLAVOR_PERC_LABELS = {"Flavor %: ", "Flavor PG %:", "Flavor VG %:", "Flavor EtOH/H2O/etc %:"};
    public static final String FLAV_FIELD_NAME = "flavorField_";
    public static final String NEW_FLAVOR_STRING = "New Flavor: ";
    public static Flavor NEW_FLAVOR = new Flavor("New Flavor");
    
    
    
    //error messages for when calulating
    public static final String ERROR_MAIN = "Error";
    public static final String[] ERROR_MSGS = {"A flavor type or percent not set", "Desired percents not at 100%", 
            "Base percents not at 100%", "Desired amount not set"};
    
    /**
     * 0-3 desired PG-->base vg; 4 flavor percent; 5-7 desired amount, desired strength, base strength
     */
    public static final String[] ERROR_DETAILS = {"DESIRED PG", "DESIRED VG",
            "BASE PG", "BASE VG % not set!", "FLAVOR : ", "DESIRED AMOUNT"
            , "DESIRED STRENGTH", "BASE STRENGTH "};
    
    public static final int[] AMOUNT_LISTENER_TYPE = {0, 1}; //0=ml; 1 = mg (strength)
    public static final int[][] PERCENT_LISTENER_TYPES = {{0, 1, 2}, {0, 1}}; //0=PG, 1=VG, 2=Other [1 desired, 2=base]
    public static final int[] TITLE_LISTENER_TYPES = {0, 1 }; //0=recipe, 1=flavor

    //width & height of the text fields (percents)
    public static final float TEXT_FIELD_WIDTH = 50; //previously 150
    public static final float TEXT_FIELD_HEIGHT = 25f;

    public static final float TITLE_HEIGHT = 50f;
    public static final float TITLE_WIDTH = 250f;
    public static final float CALC_BTN_WIDTH = 200f;
    public static final float ADD_FLAVOR_BTN_WITH = 200f;

    public static final double DROPS_PER_ML = 20; //standard is about 20 drops per ml



    //----these are empty initial values---
    //default base strength & percents - user can change in settings
    public static final double ZERO_FINAL_AMOUNT = 0.0;
    public static final Integer[] ZERO_DPs = {0, 0, 0};
    public static final Array<Integer> ZERO_DESIRED_PERCENTS = new Array<Integer>(ZERO_DPs);
    public static final double ZERO_STRENGTH = 0.0; //desired strength (medium)
    
    //base defaults - user will be able to change in settings
    public static final double EMPTY_BASE_STR = 100.0;
    public static final Integer[] EMPTY_BPs = {0, 0};
    public static final Array<Integer> ZERO_BASE_PERCENTS = new Array<Integer>(EMPTY_BPs);
    public static final Base EMPTY_BASE = new Base(0, EMPTY_BASE_STR, ZERO_BASE_PERCENTS);
    
    
    //TODO: transfer these to userdefaults when finished setting it up
    public static Integer[] BP = {50, 50};
    public static Array<Integer> BASE_PERCS = new Array<Integer>(BP);
    public static double BASE_STR = 100;
    public static Base DEFAULT_BASE = new Base(BASE_STR, BASE_PERCS);

    //flavor default - the go-to flavor for user, can change in settings
    public static final  double DEFAULT_FLAV_AMT = 0; //amount of the flavor supply
    public static final String DEFAULT_FLAV_NAME = "Flavor1"; //name of flavor
    public static final Flavor EMPTY_FLAVOR = new Flavor(0, "Flavor1");


    public static Double[] DEFAULT_GOAL_AMOUNTS = {0.0, 0.0, 0.0, 0.0, 0.0}; //default values
    public static Array<Double> INITLAL_FINAL_MLS = new Array<Double>(DEFAULT_GOAL_AMOUNTS);



    /** constants for tests
     *
     */
    public static class Tests {
        //=============Constants for CalcUtilTests (6 expected final amounts)============
        public static double DESIRED_AMT = 100.0;
        public static double DESIRED_STR = 10.0;
        public static Integer[][] DPs = {{30, 70, 0}, {30, 70, 10}};
        public static Array<Integer> DESIRED_PERCS = new Array<Integer>(DPs[0]);
        public static Array<Integer> ALT_DESIRED_PERCS = new Array<Integer>(DPs[1]);

        public static int FLAV_PERC = 10;
        public static int[] FLAV_TYPES = {0, 1, 2};
        public static String[] FLAV_NAMES = {"Flavor1", "Flavor2", "Flavor3"};
        public static Flavor FLAV1 = new Flavor(FLAV_NAMES[0], FLAV_PERC, FLAV_TYPES[0]);
        public static Flavor FLAV2 = new Flavor(FLAV_NAMES[1], FLAV_PERC/2, FLAV_TYPES[1]);
        public static Flavor FLAV3 = new Flavor(FLAV_NAMES[2], FLAV_PERC/2, FLAV_TYPES[2]);
        public static Flavor[] flavs = {FLAV1, FLAV2, FLAV3};
        public static Array<Flavor> TEST_FLAVORS = new Array<Flavor>(flavs);

        public static Integer[] BP = {50, 50};
        public static Array<Integer> BASE_PERCS = new Array<Integer>(BP);
        public static double BASE_STR = 100;
        public static Base TEST_BASE = new Base(BASE_STR, BASE_PERCS);


        /** 6 Total Final Amounts to Tests for ----based on 20 drops/ml
         * expect0 = PG/VG, 1 Flavor(PG); expect1 = PG/VG + 2 Flavors (PG, VG); expect2= PG/VG + 3 Flavors;
         *  expect3= PG/VG/Other + 1 Flavor(PG); expect4 = PG/VG/Other + 2 Flavors (PG, Other)
         *     expect5 = PG/VG/Other + 3 Flavors (PG, VG, Other)
         */
        public final static Double[][] EXPECTED_FAs = {{15.0, 65.0, 0.0, 10.0, 10.0},{15.0, 60.0, 0.0, 10.0, 10.0, 5.0},
                {13.5, 56.5, 0.0, 10.0, 10.0, 5.0, 5.0}, {12.0, 58.0, 10.0, 10.0, 10.0},
                {10.5, 54.5, 10.0, 10.0, 10.0, 5.0},{10.5, 49.5, 10.0, 10.0, 10.0, 5.0, 5.0}};
    }


    /** TODO: figure out how to (or if to) implement this class
     *  User defaults
     *  - will be able to be altered using the options menu
     */
    public static class UserDefaults{
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
    
}
