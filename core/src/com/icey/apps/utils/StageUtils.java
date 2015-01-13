package com.icey.apps.utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

/**
 * Created by Allen on 1/11/15.
 */
public class StageUtils {


    public static ScrollPane scrollTable(Skin skin, String scrollStyle){
        Table container = new Table();

        ScrollPane scrollPane = new ScrollPane(container, skin, scrollStyle);

        return scrollPane;
    }

    public static TextField createTextField(String label, Skin skin, String style){
        TextField textField = new TextField(label, skin, style);

        textField.setTextFieldListener(new TextField.TextFieldListener() {
            public void keyTyped (TextField textField, char key) {
                if (key == '\n') textField.getOnscreenKeyboard().show(false);
            }
        });

        return textField;
    }


    public static InputListener fieldListener = new InputListener(){

        @Override
        public boolean handle(Event e) {
            return super.handle(e);
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            return super.touchDown(event, x, y, pointer, button);
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            super.touchUp(event, x, y, pointer, button);
        }

        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            super.touchDragged(event, x, y, pointer);
        }

        @Override
        public boolean mouseMoved(InputEvent event, float x, float y) {
            return super.mouseMoved(event, x, y);
        }

        @Override
        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            super.enter(event, x, y, pointer, fromActor);
        }

        @Override
        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            super.exit(event, x, y, pointer, toActor);
        }

        @Override
        public boolean scrolled(InputEvent event, float x, float y, int amount) {
            return super.scrolled(event, x, y, amount);
        }

        @Override
        public boolean keyDown(InputEvent event, int keycode) {
            return super.keyDown(event, keycode);
        }

        @Override
        public boolean keyUp(InputEvent event, int keycode) {
            return super.keyUp(event, keycode);
        }

        @Override
        public boolean keyTyped(InputEvent event, char character) {
            return super.keyTyped(event, character);
        }
    };

}
