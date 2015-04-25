package protectedtower;

import java.util.List;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Gallery extends Group{
    private Rectangle initialBackground;
    private StackPane imageDisplay;
    private ImageView pic;
    private ImageView left;
    private ImageView right;
    
    private double myWidth;
    private double myHeight;
    private int myDelay;
    
    private String[] galleryImages;
    private List<Circle> navDots;
    private IntegerProperty currentImageIndex;
    
    public Gallery(String[] images, double width, double height, int delay){
        galleryImages = images;
        myWidth = width;
        myHeight = height;
        myDelay = delay;
        
        currentImageIndex = new SimpleIntegerProperty(0);
        setupGalleryLayout();
    }
    
    private void setupGalleryLayout(){
        VBox gallery = new VBox(20);
        gallery.setAlignment(Pos.CENTER);
        
        HBox navControls = new HBox(20);
        navControls.setAlignment(Pos.CENTER);
        
        imageDisplay = new StackPane();
        initialBackground = new Rectangle(myWidth, myHeight, Color.WHITE);
        initialBackground.setOpacity(0.0);
        
        pic = new ImageView(new Image(galleryImages[currentImageIndex.getValue()]));
        pic.setFitHeight(myHeight);
        pic.setFitWidth(myWidth);
        pic.setSmooth(true);
        
        left = new ImageView(new Image("images/leftarrow.png"));
        left.setFitWidth(50);
        left.setPreserveRatio(true);
        left.setOpacity(0.0);
        
        right = new ImageView(new Image("images/rightarrow.png"));
        right.setFitWidth(50);
        right.setPreserveRatio(true);
        right.setOpacity(0.0);
        
        imageDisplay.getChildren().add(initialBackground);
        
        navControls.getChildren().addAll(left, imageDisplay, right);
        gallery.getChildren().addAll(navControls);
        
        this.getChildren().add(gallery);
    }
    
    /**
     * Slightly hard-coded but not sure how else to do this.
     */
    public void showBackground(){
        initialBackground.setOpacity(1.0);
    }
    
    public SequentialTransition playEntryAnimation(){
        ScaleTransition expandY = new ScaleTransition(Duration.millis(300), imageDisplay);
        expandY.setFromY(0.0);
        expandY.setFromX(0.01);
        expandY.setToY(1.0);
        expandY.setToX(0.01);
        
        ScaleTransition expandX = new ScaleTransition(Duration.millis(600), imageDisplay);
        expandX.setFromX(0.01);
        expandX.setToX(1.0);
        expandX.setOnFinished(e -> {
            pic.setOpacity(0.0);
            imageDisplay.getChildren().add(pic);
        });
        
        FadeTransition picAppear = new FadeTransition(Duration.millis(500), pic);
        picAppear.setFromValue(0.0);
        picAppear.setToValue(1.0);
        
        FadeTransition leftArrow = new FadeTransition(Duration.millis(500), left);
        leftArrow.setFromValue(0.0);
        leftArrow.setToValue(1.0);
        
        FadeTransition rightArrow = new FadeTransition(Duration.millis(500), right);
        rightArrow.setFromValue(0.0);
        rightArrow.setToValue(1.0);
        
        ParallelTransition parallel = new ParallelTransition(leftArrow, rightArrow);
        
        SequentialTransition entry = new SequentialTransition(expandY, expandX, picAppear, parallel);
        return entry;
    }
}
