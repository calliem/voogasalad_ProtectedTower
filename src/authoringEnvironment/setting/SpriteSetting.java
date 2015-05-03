package authoringEnvironment.setting;

import imageselector.util.ScaleImage;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import authoringEnvironment.Controller;
import authoringEnvironment.InstanceManager;
import authoringEnvironment.NoImageFoundException;


public class SpriteSetting extends Setting {
    private static final int DISPLAY_WIDTH = 200;
    private static final int DISPLAY_HEIGHT = 70;
    private static final int PADDING = 15;
    private static final int IMAGE_SIZE = 50;
    private static final int IMAGES_DISPLAYED = 3; // the amount of images displayed at once in the
                                                   // scrollpane

    private List<ImageView> images;
    private ObservableList<String> filePaths;
    private HBox imageLayout;
    private static final Color BACKGROUND_COLOR = Color.PALEGOLDENROD;
    private static final double SELECTED = 1.0;
    private static final double UNSELECTED = 0.3;
    private static final int INITIAL = 0;

    private Rectangle graphicSelectorBackground;
    private ScrollPane graphicSelectorPane;
    private List<String> selectedFiles;
    private static final String SPRITE_TYPES = "resources/sprite_parameter_type";
    private static final ResourceBundle spriteNeeded = ResourceBundle.getBundle(SPRITE_TYPES);
    private static final int REFRESH_SIZE = 20;
    
    private boolean singularChoice = false;
    protected String spriteDisplayed;
    
    public SpriteSetting (Controller controller, String part, String label, String value) {
        super(controller, part, label, value);
    }

    @Override
    protected void setupInteractionLayout () {
        basicLayout.setAlignment(Pos.CENTER);
        ImageView refresh = new ImageView(new Image("images/refresh.png"));
        ScaleImage.scale(refresh, REFRESH_SIZE, REFRESH_SIZE);
        basicLayout.getChildren().add(refresh);
        
        setSpriteDisplayed();
        selectedFiles = new ArrayList<>();

        HBox graphicLayout = new HBox(PADDING);
        graphicLayout.setAlignment(Pos.CENTER_RIGHT);

        imageLayout = new HBox(PADDING);
        imageLayout.setAlignment(Pos.CENTER);

        images = new ArrayList<>();
        filePaths = FXCollections.observableList(new ArrayList<String>());
        setupScrollPane();
        setupSelectionPane();
        
        refresh.setOnMousePressed(e -> setupSelectionPane());

        filePaths.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged (ListChangeListener.Change change) {
                setupSelectionPane();
            }
        });
        
        if(!images.isEmpty()){
            images.get(INITIAL).setOpacity(SELECTED);
            selectedFiles.add(filePaths.get(INITIAL));
        }
        
        graphicLayout.getChildren().addAll(graphicSelectorPane, error);
        this.getChildren().addAll(graphicLayout);
    }
    
    protected void setSpriteDisplayed(){
        
    }
    
    @Override
    public void setParameterValue(Object value){
        selectedFiles = (ArrayList<String>) value;
        displaySavedValue();
    }
    
    private void setupSelectionPane(){
        retrieveSprites();
        try {
            layoutSprites();
            displaySavedValue();
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
    private void layoutSprites () throws NoImageFoundException {
        imageLayout.getChildren().removeAll(imageLayout.getChildren());
        adjustBackground();

        images = new ArrayList<>();
        for (String path : filePaths) {
//            System.out.println("trying to get image at: " + myController.getImageForKey(path));
            ImageView image = new ImageView(myController.getImageForKey(path));
            ScaleImage.scale(image, IMAGE_SIZE, IMAGE_SIZE);
            image.setOnMousePressed( (e) -> {
                if (image.getOpacity() == SELECTED && !singularChoice) {
                    image.setOpacity(UNSELECTED);
                    selectedFiles.remove(path);
                    dataAsString = convertDataToString();
                    System.out.println("SELECTED FILES: " + selectedFiles);
                }
                else if(image.getOpacity() == UNSELECTED && !singularChoice){
                    image.setOpacity(SELECTED);
                    selectedFiles.add(path);
                    dataAsString = convertDataToString();
                    System.out.println("SELECTED FILES: " + selectedFiles);
                }
                else if(image.getOpacity() == UNSELECTED && singularChoice){
                    makeSingleSelection(filePaths.indexOf(path));
                }
            });
            imageLayout.getChildren().add(image);
            images.add(image);

            Tooltip tooltip =
                    new Tooltip((String) myController.getPartCopy(path)
                            .get(InstanceManager.NAME_KEY));
            Tooltip.install(image, tooltip);
        }
    }

    private void makeSingleSelection (int index) {
        for (Node node : imageLayout.getChildren()) {
            if (imageLayout.getChildren().indexOf(node) != index) {
                node.setOpacity(UNSELECTED);
                selectedFiles.remove(filePaths.get(imageLayout.getChildren().indexOf(node)));
            }
            else {
                node.setOpacity(SELECTED);
                selectedFiles.add(filePaths.get(index));
            }
        }
        dataAsString = convertDataToString();
    }

    /**
     * Adjusts the background for the scrollpane when new sprites are added to it.
     * 
     */
    private void adjustBackground () {
        if (filePaths.size() > IMAGES_DISPLAYED) {
            graphicSelectorBackground.setWidth(DISPLAY_WIDTH +
                                               (filePaths.size() - IMAGES_DISPLAYED) *
                                               (IMAGE_SIZE + PADDING));
        }
    }

    private void retrieveSprites () {
        filePaths = myController.getKeysForPartType(spriteDisplayed);
    }

    private void setupScrollPane () {
        graphicSelectorPane = new ScrollPane();
        StackPane graphicSelector = new StackPane();
        graphicSelectorBackground = new Rectangle(DISPLAY_WIDTH, IMAGE_SIZE, BACKGROUND_COLOR);
        adjustBackground();

        graphicSelector.getChildren().addAll(graphicSelectorBackground, imageLayout);

        graphicSelectorPane.setContent(graphicSelector);
        graphicSelectorPane.setPannable(true);
        graphicSelectorPane.setMaxWidth(DISPLAY_WIDTH);
        graphicSelectorPane.setMaxHeight(DISPLAY_HEIGHT);
        graphicSelectorPane.setHbarPolicy(ScrollBarPolicy.NEVER);
    }

    @Override
    public String getDataAsString () {
        return dataAsString;
    }

    private String convertDataToString () {
        String result = "";
        for (String file : selectedFiles) {
            if (selectedFiles.indexOf(file) < selectedFiles.size() - 1) {
                result += String.format("%s, ", file);
            }
        }
        return result;
    }

    @Override
    public Object getParameterValue () {
        return selectedFiles;
    }

    @Override
    public boolean parseField () {
//        if(selectedFiles.size() == 0){
//            displayErrorAlert("Select at least one!");
//            return false;
//        }
        dataAsString = convertDataToString();
        System.out.println("data: " + selectedFiles);
        System.out.println("dataAsString: " + dataAsString);
//        hideErrorAlert();
        return true;
    }

    @Override
    public void displaySavedValue () {
        for (Node node : imageLayout.getChildren()) {
            if (selectedFiles.contains(filePaths.get(imageLayout.getChildren().indexOf(node)))) {
                node.setOpacity(SELECTED);
            }
            else {
                node.setOpacity(UNSELECTED);
            }
        }
    }

    public void setSingularChoice (boolean singular) {
        singularChoice = singular;
    }

    @Override
    public boolean processData () {
        return parseField();
    }
}
