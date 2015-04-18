package authoringEnvironment.setting;

import imageselectorTEMP.util.ScaleImage;
import java.util.ArrayList;
import java.util.List;
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
import authoringEnvironment.Controller;


public class SpriteSetting extends Setting {
    private static final int DISPLAY_WIDTH = 200;
    private static final int PADDING = 15;
    private static final int IMAGE_SIZE = 50;
    private static final int IMAGES_DISPLAYED = 3; // the amount of images displayed at once in the
                                                   // scrollpane

    private List<ImageView> images;
    private List<String> filePaths;
    private IntegerProperty selectedIndex;
    private String spriteFile;
    private HBox graphicLayout;
    private static final Color BACKGROUND_COLOR = Color.PALEGOLDENROD;
    private Rectangle graphicSelectorBackground;
    private ScrollPane graphicSelectorPane;

    public SpriteSetting (Controller controller, String part, String label, String value) {
        super(controller, part, label, value);
    }

    @Override
    protected void setupInteractionLayout () {
        basicLayout.setAlignment(Pos.CENTER);

        filePaths = new ArrayList<>();
        images = new ArrayList<>();
        selectedIndex = new SimpleIntegerProperty(0);
        selectedIndex.addListener((obs, oldIndex, newIndex) -> {
            makeSelection((int) newIndex);
        });
        
        loadImages();
        layoutSprites();

        this.getChildren().add(graphicSelectorPane);
    }
    
    private void layoutSprites(){
        graphicSelectorPane = new ScrollPane();
        StackPane graphicSelector = new StackPane();
        graphicSelectorBackground = new Rectangle(DISPLAY_WIDTH, IMAGE_SIZE, BACKGROUND_COLOR);
        adjustBackground();
        
        graphicLayout = new HBox(PADDING);
        graphicLayout.setAlignment(Pos.CENTER);
        
        graphicLayout.getChildren().removeAll();
        for (String path : filePaths) {
            ImageView image = new ImageView(new Image(path));
            ScaleImage.scale(image, IMAGE_SIZE, IMAGE_SIZE);
            image.setOnMousePressed( (e) -> {
                selectedIndex.setValue(filePaths.indexOf(path));
            });
            graphicLayout.getChildren().add(image);
            images.add(image);
        }

        makeSelection(0);
        
        graphicSelector.getChildren().addAll(graphicSelectorBackground, graphicLayout);
        setupScrollPane(graphicSelector);
    }

    private void adjustBackground () {
        if (filePaths.size() > 3) {
            graphicSelectorBackground.setWidth(DISPLAY_WIDTH +
                                (filePaths.size() - IMAGES_DISPLAYED) *
                                (IMAGE_SIZE + PADDING));
        }
    }
    
    private void loadImages(){
        filePaths.add("images/redbullet.png");
        filePaths.add("images/greenbullet.png");
        filePaths.add("images/bluefire.png");
        filePaths.add("images/error.png");
        
        spriteFile = filePaths.get(0);
    }

    private void setupScrollPane (StackPane content) {
        graphicSelectorPane.setContent(content);
        graphicSelectorPane.setPannable(true);
        graphicSelectorPane.setMaxWidth(200);
        graphicSelectorPane.setMaxHeight(70);
        graphicSelectorPane.setHbarPolicy(ScrollBarPolicy.NEVER);
    }

    private void makeSelection (int selectedIndex) {
        for (int i = 0; i < images.size(); i++) {
            images.get(i).setOpacity(1);
            if (i != selectedIndex) {
                images.get(i).setOpacity(0.3);
            }
        }
        dataAsString = filePaths.get(selectedIndex);
        spriteFile = filePaths.get(selectedIndex);
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
    public void displaySavedValue () {

    }

    @Override
    public boolean processData () {
        return parseField();
    }
}
