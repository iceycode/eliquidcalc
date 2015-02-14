package com.icey.apps.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.icey.apps.utils.CalcUtils;

/**
 * Created by Allen on 1/19/15.
 */
public class LoadWindow extends Window{

    Skin skin; //window skin (as well as other widgets)
    
    //widgets for window
    ScrollPane scrollPane;
    Table table;
    Array<TextButton> recipeButtons;
    Array<String> recipeNames;

    public CalcUtils calcUtils;
    public boolean recipeChosen = false; //if a recipe chosen, then true
    
    public LoadWindow(String title, Skin skin, String styleName) {
        super(title, skin, styleName);
        this.calcUtils = CalcUtils.getCalcUtil();
        this.skin = skin;
        recipeNames = new Array<String>();
        recipeButtons = new Array<TextButton>();

        table = new Table();
        table.top().left();
        scrollPane = new ScrollPane(table, skin);
        scrollPane.setFillParent(true);
        
        setBounds(100f, 300f, 230f, 150f);
        add(scrollPane).fill().expand();

        addInputListener();
        
        hideWindow();
    }
    
    
    public void updateRecipes(Array<String> recipeNames){
        
        for (String recipe: recipeNames){
            TextButton recipeButton = new TextButton(recipe, skin);
            recipeButtonListener(recipeButton, recipe);
            
            if (!recipeButtons.contains(recipeButton, false)){
                recipeButtons.add(recipeButton);
                table.add(recipeButton).height(20).align(Align.left);
                
                //add a delete button
                TextButton deleteButton = new TextButton("DEL", skin);
                deleteButtonListener(deleteButton, recipe);
                table.add(deleteButton).height(20);
                table.row();
            }
        }

    }
    
    private void recipeButtonListener(TextButton button, final String recipeName){
        button.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                log("load window set to invisible");
                calcUtils.loadData(recipeName, true);

                recipeChosen = true;
                return true;
            }
        });
    }
    
    private void deleteButtonListener(TextButton button, final String recipeName){
        button.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                log("load window set to invisible");
                calcUtils.deleteRecipe(recipeName);

                recipeChosen = true;
                return true;
            }
        });
    }
    
    private void addInputListener(){
        InputListener inputListener = new InputListener(){

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE)
                    hideWindow();
                    log("hid window after escape detected");

                return false;
            }
        };
        
        addListener(inputListener);
        
    }


    public void showWindow(){
        scrollPane.setVisible(true);
        this.setVisible(true);
        toFront();
    }
    
    public void hideWindow(){
        scrollPane.setVisible(false);
        this.setVisible(false);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        //dismisses if click anywhere on outside of dialog box
        if (Gdx.input.isTouched()){
            if (Gdx.input.getX() > (getX() + getWidth()) || Gdx.input.getX() < getX()) {
                remove();
            }
            if (Gdx.input.getY() < getY() || Gdx.input.getY() > (getY() + getHeight())) {
                remove();
            }
        }
    }

    private void log(String message){
        Gdx.app.log("LoadWindow LOG: ", message);
    }
}
