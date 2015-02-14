package com.icey.apps.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.icey.apps.data.Supply;

/** Class holds listeners & common elements for UI
 *  
 * Created by Allen on 1/28/15.
 */
public class UIUtils {


    public static TextField.TextFieldListener nameTextFieldListener(){
        TextField.TextFieldListener nameTextFieldListener = new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                log("Name typed: " + c);
                if ((c == '\r' || c == '\n')){
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }

                if (textField.getName().contains("flavor")){
                    int i = Integer.parseInt(textField.getName().substring(textField.getName().length()-1))-1;
                    CalcUtils.getCalcUtil().setFlavorName(String.valueOf(textField.getText()), i);
                }
                else if (textField.getName().contains("recipe")){
                    CalcUtils.getCalcUtil().setRecipeName(textField.getText());
                    log("recipe name: " + textField.getText());
                }

            }
        };

        return nameTextFieldListener;
    }

    public static TextField.TextFieldListener numTextFieldListener(final int type){
        TextField.TextFieldListener numTextFieldListener = new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c){
                log( "Amount typed: " + c);

//                if (c == '\n') textField.getOnscreenKeyboard().show(false);

                if ((c == '\r' || c == '\n')) {
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) ||
                            Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }
                
                if ((c >= '0' && c <= '9') || c == '.'){
                    if (type == 0){
                        CalcUtils.getCalcUtil().setAmountDesired(Double.parseDouble(textField.getText()));
                    }
                    else if (type == 1){
                        CalcUtils.getCalcUtil().setStrengthDesired(Double.parseDouble(textField.getText()));
                    }
                }

            }
        };

        return numTextFieldListener;
    }

    
    //percent listener for desired or base
    public static TextField.TextFieldListener percentListener(){
        TextField.TextFieldListener percentFieldListener = new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                log("Percentage typed: " + c + ", for " + textField.getName());
                if ((c == '\r' || c == '\n')) {
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }

                if (c >= '0' && c <= '9'){
                    CalcUtils.getCalcUtil().setPercent(Integer.parseInt(textField.getText()), textField.getName());
                }
            }
        };

        return percentFieldListener;
    }

    //flavor name listener
    public static TextField.TextFieldListener flavorNameListener(final int id){

        TextField.TextFieldListener nameTextFieldListener = new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                log( "Name typed: " + c);
                if ((c == '\r' || c == '\n')){
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)
                            || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }

//                int i = Integer.parseInt(textField.getName().substring(textField.getName().length()-1))-1;
                
                CalcUtils.getCalcUtil().setFlavorName(String.valueOf(textField.getText()), id);
            }
        };

        return nameTextFieldListener;
    }

    
    
    //flavor percent listener
    public static TextField.TextFieldListener flavorPercentListener(final int id){

        TextField.TextFieldListener percentFieldListener = new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                log( "Percentage typed: " + c + ", for " + textField.getName());
                if ((c == '\r' || c == '\n')) {
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }
                else{
                    //int i = Integer.parseInt(textField.getName().substring(textField.getName().length()-1))-1;
                    CalcUtils.getCalcUtil().setFlavorPercent(textField.getText(), id);
                }
            }
        };

        return percentFieldListener;
    }

    //flavor type listener
    public static ChangeListener flavorBoxListener(final int id){
        ChangeListener flavorBoxListener = new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                CheckBox box = ((CheckBox)actor);
                if (box.isChecked()){
                    //int i = Integer.parseInt(box.getName().substring(box.getName().length()-1))-1;


                    CalcUtils.getCalcUtil().setFlavorType(box, id);
                }
            }
        };

        return flavorBoxListener;
    }

    /** a customized inputlistener for keyboard
     *
     * @param supply
     * @param textField
     * @param type
     * @return
     */
    public static InputListener amountValueListener(final Supply supply, final TextField textField, final int type){
        InputListener listener = new InputListener(){
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                if ((character == '\r' || character == '\n')) {
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) ||
                            Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }
                else if (Character.isDigit(character) || Character.getType(character)==Character.DECIMAL_DIGIT_NUMBER) {
                    if (textField.getText().matches("\\\\d+(\\\\.\\\\d+)*")){
                        if (type == 0) {
                            supply.setTotalAmount(Double.parseDouble(textField.getText()));
                        }
                        else {
                            supply.setBaseStrength(Double.parseDouble(textField.getText()));
                        }
                    }
                }

                return true;
            }
        };


        return listener;
    }



    //this is a customized TextFieldFilter for double values (not just numbers)
    public static class MyTextFieldFilter extends TextField.TextFieldFilter.DigitsOnlyFilter {
        
        @Override
        public boolean acceptChar(TextField textField, char c) {
            //((c >= '0' && c <= '9') || c == '.')  && isDecimalDigit(c)
            if (textField.getText().contains(".") && c == '.')
                return false;
            
            return isDecimalDigit(c);
        }
        
        public static boolean isDecimalDigit(char c){
            return c == '.' || (c >= '0' && c <= '9');
        }
    }

    private static void log(String message){
        Gdx.app.log("UIUtils LOG: ", message);
    }


}
