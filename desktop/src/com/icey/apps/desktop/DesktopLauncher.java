package com.icey.apps.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.icey.apps.MainApp;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "E-liquid Calculator";
        config.width = 480;
        config.height = 800;

		new LwjglApplication(new MainApp(), config);
	}
}
