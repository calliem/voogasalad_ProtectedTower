package authoringEnvironment.setting;

import imageselector.util.ScaleImage;
import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class SpriteSetting extends Setting {

    public SpriteSetting (String label, String value) {
        super(label, value);
    }
    
    @Override
    protected void setupInteractionLayout(){
        StackPane spriteFileDisplay = new StackPane();
        Rectangle displayBackground = new Rectangle(150, 24, Color.WHITE);
        displayBackground.setArcWidth(5);
        displayBackground.setArcHeight(5);
        
        ArrayList<String> imagePaths = new ArrayList<>();
        imagePaths.add("images/redbullet.png");
        imagePaths.add("images/greenbullet.png");
        imagePaths.add("images/bluefire.png");
        imagePaths.add("images/error.png");
//        imagePaths.add("images/tower.png");
        
        ArrayList<ImageView> images = new ArrayList<>();
        
        Text spriteFile = new Text(imagePaths.get(0));
        spriteFileDisplay.getChildren().addAll(displayBackground, spriteFile);
        
        ScrollPane graphicSelectorPane = new ScrollPane();
        StackPane graphicSelector = new StackPane();
        Rectangle graphicSelectorBackground = new Rectangle(200, 50, Color.PALEGOLDENROD);
        HBox graphicLayout = new HBox(15);
        graphicLayout.setAlignment(Pos.CENTER);
        
        if(imagePaths.size() > 3){
            graphicSelectorBackground.setWidth(200 + (imagePaths.size()-3)*(50+15));
        }
        
        IntegerProperty selectedIndex = new SimpleIntegerProperty(0);
        
        for(String path : imagePaths){
            ImageView image = new ImageView(new Image(path));
            ScaleImage.scale(image, 50, 50);
            image.setOnMousePressed((e) -> {
                selectedIndex.setValue(imagePaths.indexOf(path));
            });
            graphicLayout.getChildren().add(image);
            images.add(image);
        }
        
        makeSelection(selectedIndex.getValue(), images);
        dataAsString = imagePaths.get(selectedIndex.getValue());
        selectedIndex.addListener((obs, oldValue, newValue) -> {
            makeSelection((int) newValue, images);
            dataAsString = imagePaths.get((int) newValue);
        });
        
        graphicSelector.getChildren().addAll(graphicSelectorBackground, graphicLayout);
        
        graphicSelectorPane.setContent(graphicSelector);
        graphicSelectorPane.setPannable(true);
        graphicSelectorPane.setMaxWidth(200);
        graphicSelectorPane.setMaxHeight(70);
        graphicSelectorPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        
        this.getChildren().add(graphicSelectorPane);
    }
    
    private void makeSelection(int selectedIndex, ArrayList<ImageView> images){
        for(int i = 0; i < images.size(); i++){
            images.get(i).setOpacity(1);
            if(i != selectedIndex){
                images.get(i).setOpacity(0.3);
            }
        }
    }

    @Override
    public Object getParameterValue () {
        return dataAsString;
    }

    @Override
    public boolean parseField () {
        return true;
    }
    
    @Override
    public void displaySavedValue(){
        
    }
    
    @Override
    public boolean processData(){
        return parseField();
    }
}
