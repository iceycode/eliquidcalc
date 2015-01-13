package com.icey.apps;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.icey.apps.assets.Assets;
import com.icey.apps.assets.Constants;
import com.icey.apps.screens.CalculatorScreen;

public class MainApp implements ApplicationListener {
	public SpriteBatch batch;
	BitmapFont font;
	CalculatorScreen calcScreen;

	@Override
	public void create () {

        Assets.loadAssets();
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal(Constants.DEFAULT_FONT_FILE));
        calcScreen = new CalculatorScreen();

//        this.setScreen(new CalculatorScreen(this));
	}

	@Override
	public void render () {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        calcScreen.render(Gdx.graphics.getDeltaTime());
//        super.render();
	}

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void resize(int width, int height) {
        if (getScreen()!=null)
            getScreen().resize(width, height);
    }

    public Screen getScreen(){
        if (calcScreen!=null){
            return calcScreen;
        }

        return null;
    }
}
