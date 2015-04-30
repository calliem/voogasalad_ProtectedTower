package authoringEnvironment.objects;

import java.util.HashMap;
import java.util.Map;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import authoringEnvironment.Controller;
import authoringEnvironment.InstanceManager;
import authoringEnvironment.MissingInformationException;
import authoringEnvironment.Variables;
import authoringEnvironment.util.Screenshot;

public class TileView extends ObjectView{
    private String myName;
    private Rectangle tile;
    private Color tileColor;
    private StackPane overlay;
    private Text message;
    private static final int TILE_SIZE = 100;
    private static final int PADDING = 10;
    private static final String COLOR_KEY = "Color";
    private static final Color SAVED_COLOR = Color.YELLOW;
    private static final Color ERROR_COLOR = Color.RED;
    private static final String SAVED_MESSAGE = "Tile saved!";
    private static final String ERROR_MESSAGE = "Already exists!";
    private static final int PAUSE_TIME = 1000;
    
    public TileView (Controller controller, String name, Color color) {
        super(controller);
        myName = name;
        tileColor = color;
        tile = new Rectangle(TILE_SIZE, TILE_SIZE, tileColor);
        message = new Text();
        message.setVisible(false);
        
        setupOverlay();
        objectLayout.getChildren().addAll(tile, tagGroup);
    }
    
    private void setupOverlay(){
        overlay = new StackPane();
        Rectangle background = new Rectangle(2*TILE_SIZE, 2*TILE_SIZE);
        background.setOpacity(0.8);
        VBox overlayContent = new VBox(PADDING);
        overlayContent.setAlignment(Pos.CENTER);
        TextField nameField = new TextField(myName);
        nameField.setMaxWidth(2*TILE_SIZE - 4*PADDING);
        ColorPicker colorPicker = new ColorPicker(tileColor);
        colorPicker.setOnAction(e -> {
            tileColor = colorPicker.getValue();
        });
        HBox buttons = new HBox(PADDING);
        buttons.setAlignment(Pos.CENTER);
        saveButton.setOnAction(e -> {
            if(!myController.nameAlreadyExists(Variables.PARTNAME_TILE, nameField.getText()) || nameField.getText().equals(myName)){
                myName = nameField.getText();
                tile.setFill(tileColor);
                saveTile();
            }
            else{
                displayErrorMessage();
            }
        });
        buttons.getChildren().addAll(saveButton, cancelButton);
        
        overlayContent.getChildren().addAll(nameField, colorPicker, message, buttons);
        overlay.getChildren().addAll(background, overlayContent);
    }

    public StackPane getOverlay(){
        return overlay;
    }
    
    public Rectangle getTileBody(){
        return tile;
    }
    
    /**
     * Saves the tile to the Controller and displays a message in the overlay.
     */
    public void saveTile () {
        try{
            if (myKey.equals(Controller.KEY_BEFORE_CREATION))
                myKey = myController.addPartToGame(getTileInfo());
            else
                myKey = myController.addPartToGame(myKey, getTileInfo());
        }
        catch(MissingInformationException exception){
        }
        myController.specifyPartImage(myKey, Screenshot.snap(tile, (int) tile.getWidth(), (int) tile.getHeight()).getImage());
        displaySaveMessage();
    }
    
    private void displaySaveMessage(){
        message.setFill(SAVED_COLOR);
        message.setText(SAVED_MESSAGE);
        showMessage();
    }
    
    private void displayErrorMessage(){
        message.setFill(ERROR_COLOR);
        message.setText(ERROR_MESSAGE);
        showMessage();
    }
    
    private void showMessage(){
        message.setVisible(true);
        PauseTransition pause = new PauseTransition(Duration.millis(PAUSE_TIME));
        pause.play();
        pause.setOnFinished(e -> message.setVisible(false));
    }
    
    public Map<String, Object> getTileInfo(){
        Map<String, Object> info = new HashMap<>();
        info.put(InstanceManager.PART_TYPE_KEY, Variables.PARTNAME_TILE);
        info.put(InstanceManager.NAME_KEY, myName);
        info.put(COLOR_KEY, tileColor);
        return info;
    }
}
