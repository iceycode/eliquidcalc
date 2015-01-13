package com.icey.apps.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/** contains skins for creations of screens
 *
 * Created by Allen on 1/10/15.
 */
public class Assets {

    public static AssetManager assetManager;
    public static Skin optionsSkin; //options skin
    public static Skin menuSkin; //main menu skin
    public static Skin calcSkin; //calculator skin
    public static Skin recipeSkin; //recipes skin

    public static void loadAssets(){
        TextureAtlas calcAtlas = new TextureAtlas(Gdx.files.internal(Constants.CALC_ATLAS_FILE));
        calcSkin = new Skin(Gdx.files.internal(Constants.CALC_SKIN_FILE));
        calcSkin.addRegions(calcAtlas);
    }

}
