package com.icey.apps.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/** contains skins for creations of screens
 * TODO: add splash screen for when loading assets
 *
 * Created by Allen on 1/10/15.
 */
public class Assets {

    public static AssetManager manager;
    public static boolean loaded = false;


    //loads the assets as Textures
    public static void loadAssets(){
        manager = new AssetManager();

        loadSkins();
        loadTextures();
    }

    //loads the skins of screens
    private static void loadSkins(){
        manager.load(Constants.CALC_SKIN, Skin.class);
        manager.load(Constants.MENU_SKIN, Skin.class);
    }


    private static void loadTextures(){
        manager.load(Constants.MENU_BACKGROUND_FILE, Texture.class);
        manager.load(Constants.CALC_BACKGROUND, Texture.class);
    }



    public static boolean isLoaded(){
        return loaded;
    }
    
    
    public static class AssetHelper{
        
        public static TextureRegion createRegion(int width, int height, Color color){
            Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
            pixmap.setColor(color);
            
            Texture texture = new Texture(pixmap);
            
            return new TextureRegion(texture);
        }
    }

}
