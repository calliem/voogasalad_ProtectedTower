package authoringEnvironment.objects;

import imageselectorTEMP.util.ScaleImage;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import authoringEnvironment.GameManager;
import authoringEnvironment.MainEnvironment;
import authoringEnvironment.ProjectReader;
import authoringEnvironment.setting.Setting;

/**
 * Creates the visual tower object containing
 * the tower's image, name, and the overlay that
 * pops up when the object is clicked.
 * 
 * @author Kevin He
 *
 */
public class TowerView extends SpriteView{
    
    private VBox editableContent;
    private StackPane overlayContent;
    private Button overlayCloseButton;
    private Text overlayErrorMessage;
    private BooleanProperty exists;
    
    private String towerName;
    private String imageFile;
    private List<Setting> parameterFields;
    
    private Text towerNameDisplay;
    private Text overlayTowerNameDisplay;
    private Text saved;
    
    private static final double CONTENT_WIDTH = MainEnvironment.getEnvironmentWidth();
    private static final double CONTENT_HEIGHT = 0.89 * MainEnvironment.getEnvironmentHeight();
    
    private static final int NAME_INDEX = 0;
    private static final String DEFAULT_NAME = "Unnamed";
    
    public TowerView(String name, String imageFile){
        if(name.length() == 0){
            towerName = DEFAULT_NAME;
        }
        else{
            towerName = name;
        }
        this.imageFile = imageFile;
        ImageView image = new ImageView(new Image(imageFile));
        ScaleImage.scale(image, 90, 70);
        
        parameterFields = new ArrayList<>();
        exists = new SimpleBooleanProperty(true);
        
        VBox display = new VBox(5);
        display.setAlignment(Pos.CENTER);
        
        Rectangle towerBackground = new Rectangle(100, 100, Color.WHITE);
        towerNameDisplay = new Text(towerName);
        towerNameDisplay.setFont(new Font(10));
        towerNameDisplay.setTextAlignment(TextAlignment.CENTER);
        towerNameDisplay.setWrappingWidth(90);
        
        display.getChildren().addAll(image, towerNameDisplay);
        getChildren().addAll(towerBackground, display);
        
        setupEditableContent();
        setupOverlayContent();
        setupTooltipText(getTowerInfo());
    }
    
    public BooleanProperty isExisting(){
        return exists;
    }
    
    public List<String[]> getTowerInfo(){
        List<String[]> info = new ArrayList<>();
        for(Setting setting : parameterFields){
            info.add(new String[]{setting.getParameterName(), setting.getDataAsString()});
        }
        return info;
    }
    
    public void setupTooltipText(List<String[]> info){
        String tooltipText = "";
        for(String[] parameter : info){
            tooltipText += String.format("%s: %s\n", parameter[0], parameter[1]);
        }

        Tooltip tooltip = new Tooltip(tooltipText);
        tooltip.setTextAlignment(TextAlignment.LEFT);
        Tooltip.install(this, tooltip);
    }
    
    public void initiateEditableState(){
        ImageView close = new ImageView(new Image("images/close.png"));
        close.setTranslateX(10);
        close.setTranslateY(-10);
        close.setFitWidth(20);
        close.setPreserveRatio(true);
        close.setOnMousePressed((e) -> {
            playDeletionAnimation();
            exists.setValue(false);
        });
        this.getChildren().add(close);
        StackPane.setAlignment(close, Pos.TOP_RIGHT);
    }
    
    private ScaleTransition playDeletionAnimation(){
        ScaleTransition delete = new ScaleTransition(Duration.millis(200), this);
        delete.setFromX(1.0);
        delete.setFromY(1.0);
        delete.setToX(0.0);
        delete.setToY(0.0);
        delete.setCycleCount(1);
        delete.play();
        
        return delete;
    }
    
    public void exitEditableState(){
        //removes the 'x' button.
        this.getChildren().remove(this.getChildren().size()-1);
    }
    
    private void setupEditableContent(){
        editableContent = new VBox(10);
        editableContent.setAlignment(Pos.CENTER);
//        editableContent.setTranslateY(10);
        
        overlayTowerNameDisplay = new Text(towerName);
        overlayTowerNameDisplay.setFont(new Font(30));
        overlayTowerNameDisplay.setFill(Color.WHITE);
        
        ImageView towerImage = new ImageView(new Image(imageFile));
        ScaleImage.scaleByHeight(towerImage, 150);
        
        overlayErrorMessage = new Text("Please check your parameters for errors.");
        overlayErrorMessage.setFill(Color.RED);
        overlayErrorMessage.setVisible(false);
        
        VBox settingsObjects = new VBox(10);
        settingsObjects.setMaxWidth(150);
        
        List<Setting> settings = ProjectReader.generateSettingsList("Tower");
        for(Setting s : settings){
            parameterFields.add(s);
            settingsObjects.getChildren().add(s);
        }
        
        if(towerName.length() >= 1){
            parameterFields.get(NAME_INDEX).setParameterValue(towerName);
        }
        
        HBox buttons = new HBox(10);
        
        saved = new Text("Tower saved!");
        saved.setFill(Color.YELLOW);
        saved.setVisible(false);
        
        Button save = new Button("Save");
        save.setOnAction((e)-> {
            saveParameterFields(true);
        });
        
        overlayCloseButton = new Button("Cancel");
        
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(save, overlayCloseButton);
        
        editableContent.getChildren().addAll(overlayTowerNameDisplay, towerImage, overlayErrorMessage, settingsObjects, buttons, saved);
    }
    
    private void saveParameterFields(boolean save){
        boolean correctFormat = true;
        for(Setting s : parameterFields){
            boolean correct = s.processData();
            if(correctFormat && !correct){
                correctFormat = false;
            }
        }
        overlayErrorMessage.setVisible(!correctFormat);
        if(parameterFields.get(0).processData()){
            towerName = parameterFields.get(0).getDataAsString();
            updateTowerName();
        }
        
        if(correctFormat && save){
            GameManager.addPartToGame("Tower", parameterFields);
            displaySavedMessage();
        }
    }

    private void displaySavedMessage () {
        saved.setVisible(true);
        PauseTransition pause = new PauseTransition(Duration.millis(1000));
        pause.play();
        pause.setOnFinished(ae -> saved.setVisible(false));
    }
    
    private void updateTowerName(){
        towerNameDisplay.setText(towerName);
        overlayTowerNameDisplay.setText(towerName);
    }
    
    public void discardUnsavedChanges(){
        for(Setting s : parameterFields){
            s.displaySavedValue();
        }
        saveParameterFields(false);
    }
    
    private void setupOverlayContent(){
        overlayContent = new StackPane();
        Rectangle overlayBackground = new Rectangle(CONTENT_WIDTH, CONTENT_HEIGHT);
        overlayBackground.setOpacity(0.8);
        overlayContent.getChildren().addAll(overlayBackground, editableContent);
    }
    
    public StackPane getEditorOverlay(){
        return overlayContent;
    }
    
    public Button getCloseButton(){
        return overlayCloseButton;
    }
}
