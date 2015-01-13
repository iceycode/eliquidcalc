package com.icey.apps.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.icey.apps.MainApp;

/**
 * Created by Allen on 1/11/15.
 */
public class DesktopLauncherPacker {

    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

//        Settings settings = new Settings();
//        TexturePacker.process(settings, "/textures", "/skins/calculator", "calcSkin");
        config.title = "E-liquid Calculator";
        config.width = 480;
        config.height = 800;

        new LwjglApplication(new MainApp(), config);
    }
}
