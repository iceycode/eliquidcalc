package com.icey.apps.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;
import com.icey.apps.assets.Assets;
import com.icey.apps.assets.Constants;
import com.icey.apps.enums.FlavorType;

/** the main calculator app
 * 3 main categories of info to fill out:
 * 1) Amount desired: target amount, strength, drops/ml
 * 2) Base nicotine amount/strength
 * 3) Flavor percent PG/VG/other
 *
 *
 * The Calculations for the amount of nicotine needed
 * (Desired Strength / Concentrated Nicotine Strength) x Bottle size = Amount needed (in milliliters)
 *
 * For flavor calculations:
 *  flavorPercent/100 * AmountTotal
 *
 * For amount of PG, VG & Other:
 *  AmountTotal*(desired PG%/100) - BaseNeeded*(Base PG/100)
 *  AmoutnTotal * (desired VG/100) - BaseNeeded*(Base VG/100)
 *  AmountTotal
 *
 * Created by Allen on 1/10/15.
 */
public class CalculatorScreen implements Screen {
    final String LOG = "CalcScreen LOG";

//    MainApp mainApp;

    Skin skin;
    Stage stage; //main stage
    Table table;
    Table flavScrollTable;

    Array<Label> calcLabels; //index: 1=PG, 2=VG, 3=other, 4=Base, 5=Flavor1, 6=Flavor2...N=FlavorN

    InputMultiplexer in;
    Array<InputProcessor> processors;

    //default values set initially
    String recipeName = "Recipe Name Here";
    String flavorName = "Flavor Name Here";
    String tempRecipeName;
    String tempPercent; //temporary percent string, which parsed to double

    double amount = 15; //desired amount default= 15
    double strength = 18; //desired strength
    double baseAmt = 0; //total base amount needed
    Array<Double> flavorAmounts;
    double pgAmt = 0;
    double vgAmt = 0;
    double otherAmt = 0;

    double strengthNic = 50; //default set to 50mg
    Array<Double> basePercents; //pg, vg, other (in that order)

    //desired percentage
    Array<Double> desiredPercents; //desired percentage of PG, VG & other

    String tempFlavorName;
    OrderedMap<String, Double> flavorsMap; //contains the flavor percentages
    int numFlavors = 1; //start with 1
    String flavorFieldName = "flavorTextField_";

    Array<Double> finalMills; //default 5 amounts: PG, VG, Other, Nic Base, Flavor1, Flavor2...
    Array<Double> finalDrops;


    private TextButton calcButton; //calcultor button
    private TextButton saveButton; //save button
    private TextButton loadButton; //load button

    private TextField.TextFieldListener nameTextFieldListener;
    private TextField.TextFieldListener numTextFieldListener;
    private TextField.TextFieldListener percentFieldListener;


    public CalculatorScreen(){

        skin = Assets.calcSkin;
        stage = new Stage();

        desiredPercents = new Array<Double>(3);
        flavorsMap = new OrderedMap<String, Double>();

        finalMills = new Array<Double>();
        finalDrops = new Array<Double>();

        createFieldListeners();
        setupTable();
    }


    public void createFieldListeners(){
        numTextFieldListener = new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c){
                Gdx.app.log(LOG, "Amount typed: " + c);

                if (c == '\n') textField.getOnscreenKeyboard().show(false);

                if ((c == '\r' || c == '\n')) {
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }

                if (c >= '0' && c <= '9' ){
                    tempPercent += c;
                }
                else{
                    textField.setMessageText("Wrong value! Needs to be a number!");
                }
            }
        };

        nameTextFieldListener = new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                Gdx.app.log(LOG, "Name typed: " + c);
                if ((c == '\r' || c == '\n')){
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));

                    if (textField.getName().contains(flavorFieldName)){
//                        String last = textField.getName().substring(flavorFieldName.length()-2, flavorFieldName.length()-1);
//                        int keysIndex = Integer.parseInt(last);
//                        String flavorName = flavorsMap.keys().toArray().get(keysIndex);
                        flavorsMap.put(flavorName, null);
                    }
                }

                if (textField.getName().contains(flavorFieldName)){
                    tempFlavorName += c;
                }
                else{
                    tempRecipeName += c;
                }
            }
        };

        percentFieldListener = new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                Gdx.app.log(LOG, "Percentage typed: " + c + ", for " + textField.getName());
                if ((c == '\r' || c == '\n')) {
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }

                if (textField.getName().contains("Flavor")){

                }
                else{

                }

                if (c >= '0' && c <= '9' ){
                    tempPercent += c;
                }
                else{
                    textField.setMessageText("Wrong value! Needs to be a number!");
                }

            }
        };
    }

    // STYLES used from skin
    //textbutton styles: flavor, menu;
    //scrollpane style: default
    //labelstyles: default, titleLabel, goalLabel, baseLabel, calcsLabel, flavorLabel
    //textfieldstyle: numTextField, nameTextField
    //font: default-font
    //scrollPane: default
    public void setupTable(){
        stage = new Stage();
        table = new Table();
        table.setBounds(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        //table.setBackground(new TextureRegionDrawable(skin.getRegion("calcBackground")));
        table.debug();
        table.setLayoutEnabled(true);
        table.setFillParent(false);
        table.setBackground(skin.getDrawable("calcBackground"));

        //create final calculations labels (4 total)
        calcLabels = new Array<Label>();
        for (int labelCount = 0; labelCount < 5; labelCount++){
            calcLabels.add(new Label("", skin)); //adds a default label
        }

        Label titleTFLabel = new Label("Recipe Name:", skin);
        TextField titleTextField = new TextField("", skin, "nameTextField"); //title of recipe at top
        titleTextField.setMessageText(recipeName);
        titleTextField.setTextFieldListener(nameTextFieldListener);
        table.add(titleTFLabel).width(titleTFLabel.getWidth()).height(titleTFLabel.getHeight());
        table.add(titleTextField).width(Constants.SCREEN_WIDTH).height(50f);
        table.row();

        Label goalLabel = new Label("Desired Amount", skin, "goalLabel");
        table.add(goalLabel).width(100).height(50).align(Align.left);
        Label calcsLabelTitle = new Label(" mL (drops)", skin, "calcsLabel");
        table.add(calcsLabelTitle).width(100).height(calcsLabelTitle.getHeight()).colspan(2);
        table.row();

        Label amtLabel = new Label("Amount (mL): ", skin);
        TextField amtDesTextField = new TextField("", skin, "numTextField");
        amtDesTextField.setMessageText("enter amount here");
        amtDesTextField.setTextFieldListener(numTextFieldListener);

        Label strLabel = new Label("Amount (mL): ", skin);
        TextField strTextField = new TextField("Strength (mg): ", skin, "numTextField");
        strTextField.setTextFieldListener(numTextFieldListener);
        strTextField.setMessageText("enter amount here");


        TextField dropmlTextField = new TextField("Drops per mL: ", skin, "numTextField");
        dropmlTextField.setTextFieldListener(numTextFieldListener);
        dropmlTextField.setMessageText("25"); //default will be 25 drops per ml

        //width & height of the fields
        final float fieldsWidth = Constants.SCREEN_WIDTH - calcsLabelTitle.getWidth();
        final float fieldsHeight = 25f;

        table.add(amtLabel).width(50f).height(25f);
        table.add(amtDesTextField).width(fieldsWidth).height(fieldsHeight);
        table.row();
        table.add(strLabel).width(50f).height(25f);
        table.add(strTextField).width(fieldsWidth).height(fieldsHeight);
        table.row();
        table.add(dropmlTextField).width(fieldsWidth).height(fieldsHeight);
        table.row();
        addPercentFields(table, fieldsWidth, fieldsHeight, Constants.DESIRED_PERC_LABELS, true);

        //---NICOTINE BASE---second category
        Double[] nicPercents = {50.0, 50.0, 0.0};
        basePercents = new Array<Double>(nicPercents);

        Label baseLabel = new Label("Nicotine Base", skin, "baseLabel");
        table.add(baseLabel).width(100).height(50).align(Align.left);
        table.add(amtLabel).width(50f).height(25f);
        table.row();
        TextField baseStrTF = strTextField;
        baseStrTF.setName("base strength field");
        table.add(strLabel).width(50f).height(25f);
        table.add(strTextField).width(fieldsWidth).height(fieldsHeight);
        table.add(calcLabels.get(3));
        table.row();
        addPercentFields(table, fieldsWidth, fieldsHeight, Constants.BASE_PERC_LABELS, false);


        //---FLAVORS--- third category: initially adds 1 FLavor by name of name
        Label flavorLabel = new Label("Flavor(s)", skin, "flavorLabel");
        table.add(flavorLabel).width(100).height(50).align(Align.left);
        table.row();
        createFlavorScrollTable(fieldsWidth, fieldsHeight);
        table.add(flavScrollTable).width(fieldsWidth).height(200f);
        table.row();
        //the flavor button
        TextButton flavorButton = new TextButton("Add flavor", skin, "flavor");
        flavorButton.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                addFlavor(fieldsWidth, fieldsHeight);//add new flavor
                return false;
            }

        });
        table.add(flavorButton).align(Align.center);
        table.row();

        //the calculator button
        calcButton = new TextButton("Calculator", skin);
        calcButton.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //calculate amounts if touchdown
                calcAmounts();
                return false;
            }

        });

        table.add(calcButton).align(Align.center);


        stage.addActor(table);

    }

    //the percentage fields that go in all categories (PG, VG, and other)
    public void addPercentFields(Table table, float width, float height, String[] percentFieldLabels, boolean desired){
        int i = 0;

        for (final String field : percentFieldLabels){
            Label pgLabel = new Label(field, skin);
            TextField percentField = new TextField(field, skin, "numTextField");
            percentField.setName(field);
            percentField.setMaxLength(2);
            percentField.setTextFieldListener(percentFieldListener);
            table.add(pgLabel).width(50f).height(25f);
            table.add(percentField).width(width).height(height);
            if (desired){
                table.add(calcLabels.get(i));
                i++;
            }
            table.row();
        }

    }

    //a nested table with a scrollpane in it
    private void createFlavorScrollTable(float fieldsWidth, float fieldsHeight){
        flavScrollTable = new Table();
        ScrollPane scroll = new ScrollPane(flavScrollTable, skin, "default");
        scroll.setBounds(0,0, 480, 400);

        addFlavor(fieldsWidth, fieldsHeight);
    }


    private void addFlavor(float fieldsWidth, float fieldsHeight){
        TextField flavorTextField = new TextField("Flavor: ", skin, "nameTextField");
        flavorTextField.setTextFieldListener(nameTextFieldListener);
        flavorTextField.setName(flavorFieldName + Integer.toString(numFlavors));
        flavorTextField.setMessageText(flavorName);

        //set up the 1st flavor in map
        flavorsMap.put(flavorName, null);

        flavScrollTable.add(flavorTextField);
        flavScrollTable.add(calcLabels.get(4)); //where the calculated value goes
        flavScrollTable.row();

        CheckBox pgCheck = new CheckBox("PG ", skin);
        CheckBox vgCheck = new CheckBox("VG ", skin);
        CheckBox otherCheck = new CheckBox("EtOH/H2O/etc ", skin);

        flavScrollTable.add(pgCheck).width(50f).height(20f);
        flavScrollTable.add(vgCheck).width(50f).height(20f);
        flavScrollTable.add(otherCheck).width(50f).height(20f);
        flavScrollTable.row();
    }


    private void recordPercents(String chars, String type, Array<Double> percents){
        if (type.contains("PG")){
            percents.ensureCapacity(1);
            percents.insert(0, Double.parseDouble(chars));
        }
        else if (type.contains("VG")){
            percents.insert(1, Double.parseDouble(chars));
        }
        else if (type.contains("EtOH")){
            percents.insert(2, Double.parseDouble(chars));
        }

    }

    private void recordFlavorPercents(String chars, String type, Array<Double> percents){
        if (type.contains("PG")){
            percents.ensureCapacity(1);
            percents.insert(1, Double.parseDouble(chars));
        }
        else if (type.contains("VG")){
            percents.insert(2, Double.parseDouble(chars));
        }
        else if (type.contains("EtOH")){
            percents.insert(3, Double.parseDouble(chars));
        }
        else{
            percents.insert(0, Double.parseDouble(chars));
        }


    }


    /** adds to the calculations
     *
     * @param c
     * @param type
     */
    private void addCharToValue(char c, boolean type){

        if (type){

        }
    }

    private void calcAmounts(){
        baseAmt = (strength/strengthNic)*amount;

        double otherPGAmts = baseAmt*(basePercents.get(0).doubleValue()/100);
        double otherVGAmts = baseAmt*(basePercents.get(1).doubleValue()/100);
        double otherAmts = baseAmt*(basePercents.get(2).doubleValue()/100);

        //get nicotine PG amounts
        for (int i = 0; i < flavorsMap.size; i++){
            for (String f : flavorsMap.keys()){
                if (flavorsMap.get(f) != null) {
                    switch(flavorsMap.get(f)){
                        case PG:
                            break;
                        case VG:
                            break;
                        case OTHER:;
                    }

                    double percFlavor = flavPercents.get(0).doubleValue();
                    flavAmt = amount * (percFlavor / 100);
                    flavorAmounts.ensureCapacity(1);
                    flavorAmounts.insert(i, flavAmt);

                    otherPGAmts += flavAmt * (flavPercents.get(1).doubleValue() / 100);
                    otherVGAmts += flavAmt * (flavPercents.get(2).doubleValue() / 100);
                    otherAmts += flavAmt * (flavPercents.get(3).doubleValue() / 100);
                }
            }
        }

        pgAmt = amount*(desiredPercents.get(0).doubleValue()/100) - otherPGAmts;
        vgAmt = amount*(desiredPercents.get(1).doubleValue()/100) - otherVGAmts;
        otherAmt = amount*(desiredPercents.get(2).doubleValue()/100) - otherAmts;
    }


    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(stage);
        show();
    }


    @Override
    public void show() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
}
