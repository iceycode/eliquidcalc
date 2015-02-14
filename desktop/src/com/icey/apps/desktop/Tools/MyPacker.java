package com.icey.apps.desktop.tools;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

import java.util.Scanner;

/** for packing textures to be into one image in order to use with json for skins
 *
 * Created by Allen on 1/10/15.
 */
public class MyPacker {
    private static String choiceMessage = "Choose which textures to pack: \n 1: Calculator textures \n 2: Main Menu textures " +
            "\n 3: Supply Menu textures \n ENTER NUMBER HERE:";
    private static String errorMsg = "Wrong value entered, try again";

    //input & output directories and packfile names
    private static String inputDir_calc = "textures/calculator";
    private static String outputDir_calc = "skins/calculator";
    private static String packFileName_calc = "skins/calculator/calcSkin";
    
    private static String inputDir = "textures/main_menu"; //input directory
    private static String outputDir = "skins/menu";     //output directory of atlas & packed directories
    private static String packFileName = "skins/menu/menuSkin"; //atlas file which will coincide with skin
    
    private static String inputDir_supplies = "textures/supplies";
    private static String outputDir_supplies = "skins/supplies";
    private static String packFileName_supplies = "skins/supplies/supplySkin";

    public static void main(String[] args) throws Exception{
        getUserInput(choiceMessage);
    }
    
    //get user input
    private static void getUserInput(String message){
        System.out.print(message);
        Scanner reader = new Scanner(System.in);
        int num = reader.nextInt();
        
        packTextures(num);
    }

    //packs the textures in input directory to designated file in output directory
    private static void packTextures(int choice){
        switch (choice){
            case 1:
                TexturePacker.process(inputDir_calc, outputDir_calc, packFileName_calc);
                break;
            case 2:
                TexturePacker.process(inputDir, outputDir, packFileName);
                break;
            case 3:
                TexturePacker.process(inputDir_supplies, outputDir_supplies, packFileName_supplies);
                break;
            default:
                getUserInput(errorMsg); //error, try again
                break;
        }

    }
}
