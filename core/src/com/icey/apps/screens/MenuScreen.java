package com.icey.apps.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.icey.apps.MainApp;
import com.icey.apps.assets.Assets;
import com.icey.apps.assets.Constants;

/** A simple menu screen
 * TODO: add ability to connect with FB, google+, email (others maybe)
 *
 * Created by Allen on 1/11/15.
 */
public class MenuScreen implements Screen{

    Skin skin;
    Stage stage;

    //dimensions & positions of labels, textbutton
    private final float[] TITLE_POS = Constants.MENU_TITLE_POS;
    private final float[] TITLE_SIZE = Constants.MENU_TITLE_SIZE;

    public MenuScreen(){
        setupStage(); //setup the stage
    }
    
    private void setupStage(){
        this.skin = Assets.manager.get(Constants.MENU_SKIN, Skin.class);
        stage = new Stage();

        stage.addActor(new Image(Assets.manager.get(Constants.MENU_BACKGROUND_FILE, Texture.class))); //set image background

        //title label
        Label title = new Label("E-Liquid CALC", skin);
        title.setAlignment(Align.center);
        title.setBounds(TITLE_POS[0], TITLE_POS[1], TITLE_SIZE[0], TITLE_SIZE[1]);
        stage.addActor(title);

        //button which sends user to calculator screen
        final TextButton calcButton = new TextButton("CALCULATOR", skin, "calc");
        calcButton.setPosition(Constants.SCREEN_WIDTH/2 - 230/2, 300f);
        calcButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (calcButton.isPressed())
                    MainApp.setState(1);
            }
        });
        stage.addActor(calcButton);

    }



    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        stage.act();
        stage.draw();

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
    
    private void log(String message){
        Gdx.app.log("MenuScreen LOG: ", message);
    }
}
