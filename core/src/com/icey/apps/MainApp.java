package com.icey.apps;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
import com.icey.apps.assets.Assets;
import com.icey.apps.screens.CalculatorScreen;
import com.icey.apps.screens.MenuScreen;
import com.icey.apps.utils.SaveManager;


/** Main application class
 * - in render method, shows & renders the screens
 * - also responsible to taking "back" events (keyboard back, android back button)
 *
 *
 * @author Allen , created 01/06
 */
public class MainApp implements ApplicationListener{

    Screen screen; //the current screen being shown
    Array<Screen> screens; //all the screens (0=menu, 1 = calc )
    boolean screensLoaded = false; //whether screens are loaded or not

    public static SaveManager saveManager = new SaveManager(false, true);

    private static boolean screenSet = false; //value determines whether screen is set

    //the apps state & previous state
    // -1: causes exit; 0: menu, 1: calc, 2: supplies (more to come)
    public static int state;
    public static int prevState; //the previous state

	@Override
	public void create () {
        Assets.loadAssets();
        setState(-1); //so that if
    }

    private void setScreens(){
        screens = new Array<Screen>();

        screens.add(new MenuScreen());
        screens.add(new CalculatorScreen());

        setState(0);
        screensLoaded = true;
    }

	@Override
	public void render () {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND); //enables color blending

        //for catching the back button/escape button
        Gdx.input.setCatchBackKey(true); //enables android back key usage
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            updateState(); //updates state if back hit
        }

        if(Assets.manager.update()) {
            if (!screensLoaded){
                setScreens();
            }
            else{
                showScreen();
            }
        }
	}


    //updates state if back button or escape hit
    private void updateState(){
        if (prevState == 1)
            setState(0); //to prevent user being stuck on calc & supply screens
        else
            exitApp();
    }


    //shows the screens
    private void showScreen(){
        //set the screen if it has not been set recently or at all
        if (!screenSet && state >= 0){
            setScreen(screens.get(state));
            screenSet = true;
        }

        //renders the current screen
        if (this.screen != null) {
            this.screen.show();
            //screen.render(Gdx.graphics.getDeltaTime());
            this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }


    //TODO: set up a splash screen
    private void showSplashScreen(){

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
        if (this.screen!=null) screen.resize(width, height);
    }





    /** sets the screen that will be rendered & hides previous ones
     * 
     * @param screen
     */
    private void setScreen(Screen screen){
        if (this.screen != null) this.screen.hide(); //previous screen hidden
        
        this.screen = screen; //set the screen

        screenSet = true;
    }
    
    //the new state
    public static void setState(int newState){
        prevState = state; //previous state set to current one

        state = newState; //set to the new state
        screenSet = false; //set to false, so switch screen can occur
    }


    //exit app methods of disposal
    private void exitApp(){

        for (Screen s : screens){
            s.dispose();
        }

        //on android, will cause dispose/pause in near future (see doc)
        Gdx.app.exit(); //exit the app here
    }
}
