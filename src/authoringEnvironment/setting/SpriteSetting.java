package authoringEnvironment.setting;

import imageselectorTEMP.util.ScaleImage;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import authoringEnvironment.Controller;
import authoringEnvironment.NoImageFoundException;


public class SpriteSetting extends Setting {
    private static final int DISPLAY_WIDTH = 200;
    private static final int PADDING = 15;
    private static final int IMAGE_SIZE = 50;
    private static final int IMAGES_DISPLAYED = 3; // the amount of images displayed at once in the
    // scrollpane

    private List<ImageView> images;
    private ObservableList<String> filePaths;
    private IntegerProperty selectedIndex;
    private String spriteFile;
    private HBox graphicLayout;
    private static final Color BACKGROUND_COLOR = Color.PALEGOLDENROD;
    private Rectangle graphicSelectorBackground;
    private ScrollPane graphicSelectorPane;

    private static final String SPRITE_TYPES = "resources/sprite_parameter_type";
    private static final ResourceBundle spriteNeeded = ResourceBundle.getBundle(SPRITE_TYPES);

    public SpriteSetting (Controller controller, String part, String label, String parameterName, String value) {
        super(controller, part, label, parameterName, value);
    }

    @Override
    protected void setupInteractionLayout () {
        basicLayout.setAlignment(Pos.CENTER);
        
        graphicLayout = new HBox(PADDING);
        graphicLayout.setAlignment(Pos.CENTER);
        
        images = new ArrayList<>();
        filePaths = FXCollections.observableList(new ArrayList<String>());
        setupScrollPane();
        
        selectedIndex = new SimpleIntegerProperty(0);
        selectedIndex.addListener( (obs, oldIndex, newIndex) -> {
            makeSelection((int) newIndex);
        });
        
        setupSelectionPane(0);

        filePaths.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(ListChangeListener.Change change){
                setupSelectionPane(filePaths.indexOf(dataAsString));
            }
        });
        this.getChildren().add(graphicSelectorPane);
    }
    
    private void setupSelectionPane(int defaultValue){
        loadImages(defaultValue);
        try {
            layoutSprites(defaultValue);
        }
        catch (NoImageFoundException e) {
            System.out.println("No image found???");
        }
    }

    /**
     * Creates images from key list "filePaths" and sets up MouseEvent
     * listener to update spriteFile when pressed.
     * 
     * @throws NoImageFoundException
     */
    private void layoutSprites (int defaultValue) throws NoImageFoundException {
        graphicLayout.getChildren().removeAll(graphicLayout.getChildren());
        adjustBackground();
        
        images = new ArrayList<>();
        for (String path : filePaths) {
            ImageView image = new ImageView(new Image(myController.getImageForKey(path)));
            ScaleImage.scale(image, IMAGE_SIZE, IMAGE_SIZE);
            image.setOnMousePressed( (e) -> {
                selectedIndex.setValue(filePaths.indexOf(path));
            });
            graphicLayout.getChildren().add(image);
            images.add(image);
        }

        makeSelection(defaultValue);
    }

    /**
     * Adjusts the background for the scrollpane when new sprites are added to it.
     * 
     */
    private void adjustBackground () {
        if (filePaths.size() > 3) {
            graphicSelectorBackground.setWidth(DISPLAY_WIDTH +
                                               (filePaths.size() - IMAGES_DISPLAYED) *
                                               (IMAGE_SIZE + PADDING));
        }
    }

    private void loadImages (int defaultIndex) {
        filePaths = myController.getKeysForPartType(spriteNeeded
                                                      .getString(partType));
        System.out.println("file paths set: " + filePaths);
        if (filePaths.size() != 0) {
            dataAsString = filePaths.get(0);
        }
    }

    private void setupScrollPane () {
        graphicSelectorPane = new ScrollPane();
        StackPane graphicSelector = new StackPane();
        graphicSelectorBackground = new Rectangle(DISPLAY_WIDTH, IMAGE_SIZE, BACKGROUND_COLOR);
        adjustBackground();

        graphicSelector.getChildren().addAll(graphicSelectorBackground, graphicLayout);
        
        graphicSelectorPane.setContent(graphicSelector);
        graphicSelectorPane.setPannable(true);
        graphicSelectorPane.setMaxWidth(200);
        graphicSelectorPane.setMaxHeight(70);
        graphicSelectorPane.setHbarPolicy(ScrollBarPolicy.NEVER);
    }

    private void makeSelection (int selectedIndex) {
        if (filePaths.size() != 0) {
            for (int i = 0; i < images.size(); i++) {
                images.get(i).setOpacity(1);
                if (i != selectedIndex) {
                    images.get(i).setOpacity(0.3);
                }
            }
            dataAsString = filePaths.get(selectedIndex);
//            spriteFile = filePaths.get(selectedIndex);
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
    public void displaySavedValue () {

    }

    @Override
    public boolean processData () {
        return parseField();
    }
}
