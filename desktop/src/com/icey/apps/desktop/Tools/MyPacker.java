package com.icey.apps.desktop.Tools;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

/** for packing textures to be into one image in order to use with json for skins
 *
 * Created by Allen on 1/10/15.
 */
public class MyPacker {

    public static String inputDir = "textures/calculator"; //input directory
    public static String outputDir = "skins/calculator";     //output directory of atlas & packed directories
    public static String packFileName = "skins/calcSkin"; //atlas file which will coincide with skin

    public static void main(String[] args) throws Exception{
        //packs the texture to directory
        TexturePacker.process(inputDir, outputDir, packFileName);
    }
}
