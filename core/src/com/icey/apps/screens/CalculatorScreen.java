package com.icey.apps.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;
import com.icey.apps.assets.Assets;
import com.icey.apps.assets.Constants;
import com.icey.apps.data.CalcData;
import com.icey.apps.data.Flavor;

/** the main calculator app
 *
 * 3 main categories of info to fill out:
 * 1) Amount desired: target amount, strength, percents
 * 2) Base nicotine amount/strength, percents
 * 3) Flavors: names, percents
 *
 * The Calculations for the amount of nicotine needed
 * (Desired Strength / Concentrated Nicotine Strength) x Bottle size = Amount needed (in milliliters)
 *
 * For flavor calculations:
 *  flavorPercent/100 * AmountTotal
 *
 * For amount of PG, VG & Other:
 *  AmountTotal*(desired PG%/100) - BaseNeeded*(Base PG/100)
 *  AmountTotal * (desired VG/100) - BaseNeeded*(Base VG/100)
 *  ...
 *
 * STYLES used from skin
 * textbutton styles: flavor, menu;
 * scrollpane style: default
 * labelstyles: default, titleLabel, goalLabel, baseLabel, calcsLabel, flavorLabel
 * textfieldstyle: numTextField, nameTextField
 * font: default-font
 * scrollPane: default
 * 
 *
 * Created by Allen on 1/10/15.
 */
public class CalculatorScreen implements Screen {
    final String LOG = "CalcScreen LOG";

    private static CalculatorScreen instance;

    //width & height of the text fields (percents)
    final float fieldsWidth = 200f;
    final float fieldsHeight = 25f;

    Skin skin;
    Stage stage; //main stage
    Table table;
    Table flavScrollTable;
    Array<Label> calcLabels; //index: 0=PG, 1=VG, 2=other, 3=Base, 4=Flavor1, 5=Flavor2...N=FlavorN

    TextField titleTextField;
    TextField strTextField;
    TextField amtDesTextField;
    TextField baseStrTF;
    public Array<TextField> percentTextFields; //0-2 Desired; 3-5 base; 6 flavor
    public Array<TextField> flvrPercsTFs;
    public Array<TextField> flvrTitleTFs;
    public OrderedMap<Integer, Array<CheckBox>> checkBoxMap; //0=PG, 1=VG, 2 =EtOH/H2O/etc

    //default values set initially
    String recipeName = "Recipe Name Here";
    String flavorName = "Flavor Name Here";

    CalcData calcData; //stores amounts to be calculated
    int numFlavors = 0;

    private TextButton calcButton; //calcultor button
    private TextButton saveButton; //save button
    private TextButton loadButton; //load button
    

    public CalculatorScreen(){
        instance = CalculatorScreen.this;
        
        skin = Assets.calcSkin;
        stage = new Stage();

        flvrTitleTFs = new Array<TextField>();
        flvrPercsTFs = new Array<TextField>();
        percentTextFields = new Array<TextField>();
        checkBoxMap = new OrderedMap<Integer, Array<CheckBox>>(1);
        
        calcData = CalcData.getCalcData(); //where it will be stored
        
        setupTable();
    }

    public static CalculatorScreen getCalculatorScreen(){
        return instance;
    }
    
    
    public void setupTable(){
        stage = new Stage();
        table = new Table();
        
        table.debug();

        //table properties
        table.setBounds(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        table.setLayoutEnabled(true);
        table.setFillParent(true);
        table.right().top();
        table.defaults().padRight(2f);
        //table.columnDefaults(0).width(100f).align(Align.right);
        //table.setBackground(skin.getDrawable("calcBackground"));

        //create final calculations labels (4 total)
        calcLabels = new Array<Label>();
        for (int labelCount = 0; labelCount < 4; labelCount++){
            calcLabels.add(new Label("", skin)); //adds a default label
        }



        Label titleTFLabel = new Label("Recipe Name:", skin);
        titleTextField = new TextField("", skin, "titleTextField"); //title of recipe at top
        titleTextField.setMessageText(recipeName);
        titleTextField.setName("recipeTextField");
        titleTextField.setTextFieldListener(nameTextFieldListener());
        table.add(titleTFLabel).width(100).height(50).align(Align.right);
        table.add(titleTextField).height(50f).colspan(2).fillX(); //.width(Constants.SCREEN_WIDTH - titleTFLabel.getWidth())
        table.row();

        Label goalLabel = new Label(" Desired Amount, Strength & Ratios", skin, "goalLabel");
        Label calcsLabelTitle = new Label(" mL (drops)", skin, "calcsLabel");
        table.add(goalLabel).width(300).height(50).align(Align.center).colspan(2);
        //table.add(new Label("", skin)); //an empty cell - for spacing purposes
        table.add(calcsLabelTitle).width(100).height(calcsLabelTitle.getHeight()).align(Align.left) ;
        table.row();

        Label amtLabel = new Label("Amount (mL): ", skin);
        table.add(amtLabel).align(Align.right);
        amtDesTextField = new TextField("", skin, "numTextField");
        amtDesTextField.setName("amountTextField");
        amtDesTextField.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
        amtDesTextField.setTextFieldListener(numTextFieldListener());
        amtDesTextField.setMessageText(Double.toString(calcData.getAmountDesired()));
        table.add(amtDesTextField).width(fieldsWidth).height(fieldsHeight).align(Align.left);
        table.row();
        Label strLabel = new Label("Strength (mg): ", skin);
        table.add(strLabel).align(Align.right);
        strTextField = new TextField("", skin, "numTextField");
        strTextField.setName("strengthTextField");
        strTextField.setTextFieldListener(numTextFieldListener());
        strTextField.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
        strTextField.setMessageText(Double.toString(calcData.getStrengthDesired()));
        table.add(strTextField).width(fieldsWidth).height(fieldsHeight).align(Align.left);
        table.row();
//        table.add(dropmlTextField).width(fieldsWidth).height(fieldsHeight);
//        table.row();
        addPercentFields(table, Constants.DESIRED_PERC_LABELS, true);

        //---NICOTINE BASE---second category
        Label baseLabel = new Label(" Nicotine Base", skin, "baseLabel");
        table.add(baseLabel).width(300).height(50).align(Align.center).colspan(2);
        table.add(calcLabels.get(3)); //.width(50f).height(25f).align(Align.right)
        table.row();
        baseStrTF = new TextField("", skin, "numTextField");
        baseStrTF.setName("basestrengthTextField");
        baseStrTF.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
        baseStrTF.setMessageText(Double.toString(calcData.getStrengthNic()));
        table.add(new Label("Strength (mg): ", skin)).align(Align.right); //width(120).height(25f).
        table.add(baseStrTF).width(fieldsWidth).height(fieldsHeight).align(Align.left);
        table.row();
        addPercentFields(table, Constants.BASE_PERC_LABELS, false);


        //---FLAVORS--- third category: initially adds 1 FLavor by name of name
        Label flavorLabel = new Label(" Flavor(s)", skin, "flavorLabel");
        table.add(flavorLabel).width(300).height(50).align(Align.center).colspan(2);
        table.row();
        setupFlavorScrollTable();

        //the flavor button
        TextButton flavorButton = new TextButton("Add flavor", skin, "flavor");
        flavorButton.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                addFlavorFields();//add new flavor
                return false;
            }

        });
        table.add(flavorButton).width(200).height(50).colspan(3).align(Align.center);
        table.row();
        
        //the calculator button
        calcButton = new TextButton("Calculator", skin);
        calcButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //calculate amounts if touchdown
                if (!calcData.areValuesSet()){
                    log("wrong values!");
                    new Dialog("", skin).text("Wrong Values Entered!").button("Fix it!").show(stage); //, Actions.fadeOut(2f)
                }
                else{
                    calcData.calcAmounts();
                }

                return false;
            }
        });

        table.add(calcButton).width(200).height(50).colspan(3).align(Align.center);
        table.row();
        
        saveButton = new TextButton("Save", skin, "menu");
        saveButton.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                calcData.saveData(true);
                
                return true;
            }
        });
        table.add(saveButton);
        
        loadButton = new TextButton("Load", skin, "menu");
        table.add(loadButton);

        stage.addActor(table);

    }

    
    /** adds percentage fields to the
     *
     * @param table
     * @param percentFieldLabels
     * @param desired
     */
    private void addPercentFields(Table table, String[] percentFieldLabels, boolean desired){
        int i = 0;

        for (final String field : percentFieldLabels){
            Label pgLabel = new Label(field, skin);
            TextField percentField = new TextField("", skin, "numTextField");
            percentField.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
            percentField.setName(field);
            percentField.setMaxLength(2);
            percentField.setTextFieldListener(percentListener());
            percentTextFields.add(percentField);

            //set the default messages
            if (desired && field.equals(Constants.DESIRED_PERC_LABELS[0]))
                percentField.setMessageText(Double.toString(calcData.getDesiredPercents().get(0)));
            else if (desired && field.equals(Constants.DESIRED_PERC_LABELS[1]))
                percentField.setMessageText(Double.toString(calcData.getDesiredPercents().get(1)));
            else if (desired && field.equals(Constants.DESIRED_PERC_LABELS[2]))
                percentField.setMessageText(Double.toString(calcData.getDesiredPercents().get(2)));
            else if (!desired && field.equals(Constants.BASE_PERC_LABELS[0]))
                percentField.setMessageText(Double.toString(calcData.getBasePercents().get(0)));
            else if (!desired && field.equals(Constants.BASE_PERC_LABELS[1]))
                percentField.setMessageText(Double.toString(calcData.getBasePercents().get(1)));
            else if (!desired && field.equals(Constants.BASE_PERC_LABELS[2]))
                percentField.setMessageText(Double.toString(calcData.getBasePercents().get(2)));


            table.add(pgLabel).align(Align.right);
            table.add(percentField).width(fieldsWidth).height(fieldsHeight).align(Align.left);
            if (desired){
                table.add(calcLabels.get(i));
                i++;
            }
            table.row();
        }

    }

    //a nested table with a scrollpane in it
    private void setupFlavorScrollTable(){
        flavScrollTable = new Table();
        flavScrollTable.setBounds(0,0, 480, 200f);
        flavScrollTable.right().top();
        ScrollPane scroll = new ScrollPane(flavScrollTable, skin, "default");
        scroll.setBounds(0,0, 480, 400);

        addFlavorFields();

        table.add(flavScrollTable).height(200f).colspan(3);
        table.row();
    }


    private void addFlavorFields(){
        numFlavors++;
        calcData.addFlavor(new Flavor("New Flavor"));
        calcLabels.add(new Label("", skin));
        
        //adding the flavor name label & text field
        Label flavNameLabel = new Label("Flavor Name: ", skin);
        flavScrollTable.add(flavNameLabel);
        TextField flavorTextField = new TextField("New Flavor", skin, "nameTextField");
        flavorTextField.setTextFieldListener(nameTextFieldListener());
        flavorTextField.setName("flavorFieldName_" + Integer.toString(numFlavors));
        flavorTextField.setMessageText(flavorName);
        flvrTitleTFs.add(flavorTextField);
        flavScrollTable.add(flavorTextField).width(fieldsWidth).height(fieldsHeight);
        flavScrollTable.add(calcLabels.get(4)); //where the calculated value goes
        flavScrollTable.row();

        //adding the flavor percent label & textfield
        Label flavPercLabel = new Label("Flavor %: ", skin);
        flavScrollTable.add(flavPercLabel).align(Align.right);
        TextField flavorPercentField = new TextField("", skin, "numTextField");
        flavorPercentField.setName("flavorPercentField_"+ Integer.toString(numFlavors));
        flavorPercentField.setTextFieldListener(percentListener());
        flvrPercsTFs.add(flavorPercentField);
        flavScrollTable.add(flavorPercentField).width(fieldsWidth).height(fieldsHeight);
        flavScrollTable.row();
        
        //adding the checkboxes
        CheckBox pgCheck = new CheckBox("PG ", skin);
        pgCheck.setName("flavorCheckBoxPG_" + Integer.toString(numFlavors));
        pgCheck.addListener(flavorBoxListener());
        flavScrollTable.add(pgCheck).width(80f).height(20f);
        CheckBox vgCheck = new CheckBox("VG ", skin);
        vgCheck.setName("flavorCheckBoxVG_" + Integer.toString(numFlavors));
        vgCheck.addListener(flavorBoxListener());
        flavScrollTable.add(vgCheck).width(80f).height(20f);
        CheckBox otherCheck = new CheckBox("EtOH/H2O/etc ", skin);
        otherCheck.setName("flavorCheckBoxOther_"+Integer.toString(numFlavors));
        otherCheck.addListener(flavorBoxListener());
        flavScrollTable.add(otherCheck).width(180f).height(20f);
        flavScrollTable.row();

        //add to flavor checkBox array and into map
        Array<CheckBox> boxes = new Array<CheckBox>();
        boxes.add(pgCheck);
        boxes.add(vgCheck);
        boxes.add(otherCheck);
        checkBoxMap.put(numFlavors-1, boxes);
    }


    private void displayResults(){
        for (int i = 0; i < calcData.getFinalMills().size; i++){
            String text = String.format("%.1f", calcData.getFinalMills().get(i))+" (" +
                    (int)(calcData.getFinalMills().get(i).doubleValue()*20)+")";
            calcLabels.get(i).setText(text);
        }
    }

    private TextField.TextFieldListener nameTextFieldListener(){
        TextField.TextFieldListener nameTextFieldListener = new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                Gdx.app.log(LOG, "Name typed: " + c);
                if ((c == '\r' || c == '\n')){
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }

                if (textField.getName().contains("flavor")){
                    int i = Integer.parseInt(textField.getName().substring(textField.getName().length()-1))-1;
                    calcData.setFlavorName(String.valueOf(textField.getText()), i);
                }
                else if (textField.getName().contains("recipe")){
                    calcData.setRecipeName(textField.getText());
                    log("recipe name: " + textField.getText());
                }

            }
        };

        return nameTextFieldListener;
    }

    private TextField.TextFieldListener percentListener(){
        TextField.TextFieldListener percentFieldListener = new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                Gdx.app.log(LOG, "Percentage typed: " + c + ", for " + textField.getName());
                if ((c == '\r' || c == '\n')) {
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }

                if (c >= '0' && c <= '9'){
                    if (textField.getName().contains("flavor")){
                        int i = Integer.parseInt(textField.getName().substring(textField.getName().length()-1))-1;
                        calcData.setFlavorPercent(textField.getText(), i);
                    }
                    else if (textField.getName().contains("Base")){
                        calcData.setBasePercent(textField.getText(), textField.getName());
                    }
                    else{
                        calcData.setDesiredPercent(textField.getText(), textField.getName());
                    }
                }
            }
        };

        return percentFieldListener;
    }

    private TextField.TextFieldListener numTextFieldListener(){
        TextField.TextFieldListener numTextFieldListener = new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c){
                Gdx.app.log(LOG, "Amount typed: " + c);

//                if (c == '\n') textField.getOnscreenKeyboard().show(false);

                if ((c == '\r' || c == '\n')) {
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || 
                            Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }
                else if (textField.getName().contains("amount") && textField.getText()!=null){
                    calcData.setAmountDesired(Integer.parseInt(textField.getText()));
                }
                else if (textField.getName().contains("strength") && textField.getText()!=null){
                    calcData.setAmountDesired(Integer.parseInt(textField.getText()));
                }
            }
        };

        return numTextFieldListener;
    }
    

    private ChangeListener flavorBoxListener(){
        ChangeListener flavorBoxListener = new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                CheckBox box = ((CheckBox)actor);
                if (box.isChecked()){
                    int i = Integer.parseInt(box.getName().substring(box.getName().length()-1))-1;
                    Gdx.app.log(LOG, " the index of flavors = " + i);
                    
                    calcData.setFlavorType(box, i);
                }
            }
        };

        return flavorBoxListener;
    }
    
    private void loadWindow(){
        final Window loadWindow = new Window("Saved Recipes", skin, "load");
        Array<String> recipeTitles = calcData.getAllRecipes();
        Table table = new Table();
        ScrollPane scrollPane = new ScrollPane(table, skin);
        
        for (String title: recipeTitles){
            final TextButton titleButton = new TextButton(title, skin);
            titleButton.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (titleButton.isPressed()){
                        calcData.loadData(titleButton.getText().toString(), true);
                        loadWindow.remove();
                    }
                    return false;
                }
            });
            table.add(titleButton).height(20f);
            table.row();
        }
        
        loadWindow.add(scrollPane);
        
        stage.addActor(loadWindow);
        loadWindow.setPosition(40f, 40f);
    }

    private void displayLoadedData(){
        
        titleTextField.setMessageText(calcData.getRecipeName());
        amtDesTextField.setMessageText(Double.toString(calcData.getAmountDesired()));
        strTextField.setMessageText(Double.toString(calcData.getStrengthDesired()));
        baseStrTF.setMessageText(Double.toString(calcData.getStrengthNic()));
        
        //set up the percent text fields
        Array<Double> percents = new Array<Double>();
        for (Double percent: calcData.getDesiredPercents())
            percents.add(percent);
        for (Double percent : calcData.getBasePercents())
            percents.add(percent);
        
        for (int i = 0; i < percents.size; i ++){
            percentTextFields.get(i).setMessageText(Double.toString(percents.get(i)));
        }
        
        //set up the flavors
        for (Flavor f : calcData.getFlavors()){
            if (calcData.getFlavors().size > flvrTitleTFs.size)
                addFlavorFields();
            
            for (TextField tf: flvrTitleTFs)
                tf.setMessageText(f.getName());
            
            for (TextField tf : flvrPercsTFs)
                tf.setMessageText(Double.toString(f.getPercent()));
        }

        displayResults(); //display the loaded recipe results
    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(stage);
        show();
    }


    @Override
    public void show() {
        Gdx.gl.glClearColor(.5f, 1, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        if (calcButton.isPressed()){
            displayResults();
        }
        
        if (loadButton.isPressed()){
            log("load data - needs to be finished"); //TODO: finish setting this up
            loadWindow();
        }

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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
        Gdx.app.log(LOG, message);
    }
}
