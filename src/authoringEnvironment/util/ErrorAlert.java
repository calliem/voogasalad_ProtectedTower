package authoringEnvironment.util;

import imageselector.util.ScaleImage;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ErrorAlert extends StackPane{
    private String myMessage;
    private Button okButton;
    private static final int BACKGROUND_WIDTH = 200;
    private static final int BACKGROUND_HEIGHT = 150;
    private static final String ALERT_IMAGE = "images/alert.png";
    private static final int IMAGE_SIZE = 25;
    private static final int PADDING = 20;
    
    public ErrorAlert(String message){
        myMessage = message;
        Text alertMessage = new Text(myMessage);
        alertMessage.setWrappingWidth(BACKGROUND_WIDTH - PADDING);
        alertMessage.setTextAlignment(TextAlignment.CENTER);
        
        Rectangle alertBackground = new Rectangle(BACKGROUND_WIDTH, BACKGROUND_HEIGHT, Color.DARKGRAY);
        alertBackground.setStroke(Color.BLACK);
        alertBackground.setStrokeWidth(2);
        ImageView alertImage = new ImageView(new Image(ALERT_IMAGE));
        ScaleImage.scale(alertImage, IMAGE_SIZE, IMAGE_SIZE);
        
        okButton = new Button("Ok");
        
        VBox layout = new VBox(PADDING);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(alertImage, alertMessage, okButton);
        
        getChildren().addAll(alertBackground, layout);
    }
    
    public Button getOkButton(){
        return okButton;
    }
    
    public ScaleTransition showError(){
        return Scaler.scaleOverlay(0.0, 1.0, this);
    }

    public ScaleTransition hideError(){
        return Scaler.scaleOverlay(1.0, 0.0, this);
    }
}
