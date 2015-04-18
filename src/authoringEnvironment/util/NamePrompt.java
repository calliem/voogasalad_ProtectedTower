package authoringEnvironment.util;

import imageselectorTEMP.ImageSelector;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import authoringEnvironment.NoImageFoundException;

public class NamePrompt extends StackPane{
    private Button create;
    private Button cancel;
    private VBox promptContent;
    private Rectangle promptBackground;
    private TextField promptField;
    private Text errorMessage;
    private ImageSelector imgSelector;
    
    private int numUnnamed = 0;
    private static final int PROMPT_WIDTH = 300;
    private static final int PROMPT_HEIGHT = 200;
    private static final int EXPANDED_HEIGHT = 400;
    private static final double OPACITY = 0.8;
    private static final int PREVIEW_WIDTH = 225;
    private static final int PREVIEW_HEIGHT = 150;
    private static final int PADDING = 20;
    
    public NamePrompt (String partName){
        promptBackground = new Rectangle(PROMPT_WIDTH, PROMPT_HEIGHT);
        promptBackground.setArcWidth(10);
        promptBackground.setArcHeight(10);
        promptBackground.setOpacity(OPACITY);

        promptContent = new VBox(PADDING);
        promptContent.setAlignment(Pos.CENTER);
        Text prompt = new Text("Creating a new " + partName + "...");
        prompt.setFont(new Font(18));
        prompt.setFill(Color.WHITE);
        
        VBox nameEntry = new VBox(PADDING/4);
        nameEntry.setAlignment(Pos.CENTER);
        
        errorMessage = new Text();
        errorMessage.setFill(Color.RED);
        errorMessage.setVisible(false);
        
        promptField = new TextField();
        promptField.setMaxWidth(PREVIEW_WIDTH);
        promptField.setPromptText("Enter a name...");
        
        nameEntry.getChildren().addAll(promptField, errorMessage);

        HBox buttons = new HBox(PADDING/2);
        create = new Button("Create");
        cancel = new Button("Cancel");

        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(create, cancel);
        promptContent.getChildren().addAll(prompt, nameEntry, buttons);

        this.getChildren().addAll(promptBackground, promptContent);
    }
    
    public void displayError(String message){
        errorMessage.setText(message);
        errorMessage.setVisible(true);
        PauseTransition pause = new PauseTransition(Duration.millis(1000));
        
        FadeTransition fade = new FadeTransition(Duration.millis(300), errorMessage);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        
        SequentialTransition display = new SequentialTransition(pause, fade);
        display.play();
        display.setOnFinished(e -> {
            errorMessage.setVisible(false);
        });
    }
    
    public ScaleTransition showPrompt(StackPane root){
        promptField.setText("");
        if(imgSelector!=null){
            imgSelector.clear();
        }
        
        root.getChildren().add(this);
        promptField.requestFocus();
        return Scaler.scaleOverlay(0.0, 1.0, this);
    }
    
    public ScaleTransition hidePrompt(){
        return Scaler.scaleOverlay(1.0, 0.0, this);
    }
    
    public ScaleTransition hidePrompt(StackPane root){
        ScaleTransition hide = Scaler.scaleOverlay(1.0, 0.0, this);
        hide.setOnFinished(e -> root.getChildren().remove(this));
        return hide;
    }
    
    public void setImageChooser(boolean show){
        if(show){
            promptBackground.setHeight(EXPANDED_HEIGHT);
            
            imgSelector = new ImageSelector();
            imgSelector.addExtensionFilter("Image");
            imgSelector.setPreviewImageSize(PREVIEW_WIDTH, PREVIEW_HEIGHT);
            
            promptContent.getChildren().add(2, imgSelector);
        }
    }
    
    public Button getCreateButton(){
        return create;
    }
    
    public Button getCancelButton(){
        return cancel;
    }
    
    public void requestFieldFocus(){
        promptField.requestFocus();
    }
    
    public String getCurrentText(){
        return promptField.getText();
    }
    
    public String getEnteredName(){
        if(promptField.getText().length() == 0){
            numUnnamed++;
            return String.format("Unnamed%d", numUnnamed);
        }
        return promptField.getText();
    }
    
    public String getSelectedImageFile() throws NoImageFoundException {
        if(imgSelector == null){
            throw new NoImageFoundException("No image selector associated with this prompt");
        }
        return imgSelector.getSelectedImageFile();
    }
}
