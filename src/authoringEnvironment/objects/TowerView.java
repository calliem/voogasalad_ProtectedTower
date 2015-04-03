package authoringEnvironment.objects;

import imageSelector.util.ScaleImage;
import java.util.ArrayList;
import java.util.List;
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
import authoring.environment.setting.IntegerSetting;
import authoring.environment.setting.Setting;
import authoringEnvironment.MainEnvironment;

public class TowerView extends SpriteView{
    
    private VBox editableContent;
    private StackPane overlayContent;
    private Button overlayCloseButton;
    private Text overlayErrorMessage;
    private BooleanProperty exists;
    
    private String name;
    private String imageFile;
    private List<Setting> parameterFields;
    
    private static final double CONTENT_WIDTH = MainEnvironment.getEnvironmentWidth();
    private static final double CONTENT_HEIGHT = 0.89 * MainEnvironment.getEnvironmentHeight();
    
    public TowerView(String name, String imageFile){
        this.name = name;
        this.imageFile = imageFile;
        ImageView image = new ImageView(new Image(imageFile));
        ScaleImage.scale(image, 90, 70);
        
        parameterFields = new ArrayList<>();
        exists = new SimpleBooleanProperty(true);
        
        VBox display = new VBox(5);
        display.setAlignment(Pos.CENTER);
        
        Rectangle towerBackground = new Rectangle(100, 100, Color.WHITE);
        Text towerName = new Text(name);
        towerName.setFont(new Font(10));
        towerName.setTextAlignment(TextAlignment.CENTER);
        towerName.setWrappingWidth(90);
        
        display.getChildren().addAll(image, towerName);
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
            info.add(new String[]{setting.getParameterName(), setting.getParameterValue()});
        }
        return info;
    }
    
    public void setupTooltipText(List<String[]> info){
        String tooltipText = String.format("%s: %s\n", "Name", name);
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
    
    public void discardUnsavedChanges(){
        for(Setting s : parameterFields){
            s.displaySavedValue();
        }
        saveParameterFields();
    }
    
    private void setupEditableContent(){
        editableContent = new VBox(10);
        editableContent.setAlignment(Pos.TOP_CENTER);
        editableContent.setTranslateY(10);
        
        Text title = new Text("Tower");
        title.setFont(new Font(30));
        title.setFill(Color.WHITE);
        
        ImageView towerImage = new ImageView(new Image(imageFile));
        ScaleImage.scaleByHeight(towerImage, 150);
        
        overlayErrorMessage = new Text("Please check your parameters for errors.");
        overlayErrorMessage.setFill(Color.RED);
        overlayErrorMessage.setVisible(false);
        
        Setting test = new IntegerSetting("Health");
        parameterFields.add(test);
        
        HBox buttons = new HBox(10);
        
        Button save = new Button("Save");
        save.setOnAction((e)-> saveParameterFields());
        
        overlayCloseButton = new Button("Close");
        
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(save, overlayCloseButton);
        
        editableContent.getChildren().addAll(title, towerImage, overlayErrorMessage, test, buttons);
    }
    
    private void saveParameterFields(){
        boolean correctFormat = true;
        for(Setting s : parameterFields){
            boolean correct = s.parseField();
            if(correctFormat && !correct){
                correctFormat = false;
            }
        }
        overlayErrorMessage.setVisible(!correctFormat);
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
