package authoringEnvironment.objects;

import imageselector.util.ScaleImage;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
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
import authoringEnvironment.AuthoringEnvironment;
import authoringEnvironment.Controller;
import authoringEnvironment.MissingInformationException;
import authoringEnvironment.ProjectReader;
import authoringEnvironment.setting.ImageViewSetting;
import authoringEnvironment.setting.Setting;
import authoringEnvironment.setting.SpriteSetting;
import authoringEnvironment.setting.StringSetting;
import authoringEnvironment.util.Scaler;


/**
 * Displays an editable sprite object instance along with overlay interactions upon click.
 * 
 * @author Kevin He
 * @author Callie Mao
 *
 */

public abstract class SpriteView extends ObjectView {
    private VBox display;
    private VBox editableContent;
    private StackPane overlayContent;

    private Text overlayErrorMessage;

    private String spriteName;
    private String imageFile;
    private List<Setting> parameterFields;

    private Text spriteNameDisplay;
    private Text overlaySpriteNameDisplay;
    private Text saved;

    private static final double CONTENT_WIDTH = AuthoringEnvironment.getEnvironmentWidth();
    private static final double CONTENT_HEIGHT = 0.89 * AuthoringEnvironment.getEnvironmentHeight();

    private static final int IMAGE_INDEX = 0;
    private static final int NAME_INDEX = 1;
    // private static final String DEFAULT_NAME = "Unnamed";

    private String id;
    private ImageView previewImage;
    private StackPane displayPane;
    
    private static final int HALF_PADDING = 5;
    private static final int PADDING = 10;
    private static final int SCALE_IMAGE_WIDTH = 90;
    private static final int SCALE_IMAGE_HEIGHT = 70;
    private static final int DISPLAY_PANE_WIDTH = 300;
    private static final int DISPLAY_PANE_HEIGHT = 300;
    
    /**
     * Creates visual representation of a sprite created by
     * the user in the authoring environment.
     * 
     * @param c controller needed to obtain partKeys from other tabs
     * @param name name of this sprite, designated by user
     * @param image the file path of this sprite's image
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws ClassNotFoundException
     */
    public SpriteView (Controller c, String name, String image) throws ClassNotFoundException,
        IllegalArgumentException, IllegalAccessException {
        super(c);

        spriteName = name;
        imageFile = image;
        previewImage = new ImageView(new Image(imageFile));
        ScaleImage.scale(previewImage, SCALE_IMAGE_WIDTH, SCALE_IMAGE_HEIGHT);

        parameterFields = new ArrayList<>();

        displayPane = new StackPane();
        display = new VBox(HALF_PADDING);
        display.setAlignment(Pos.CENTER);

        Rectangle spriteBackground = new Rectangle(100, 100, Color.WHITE);
        spriteNameDisplay = new Text(spriteName);
        spriteNameDisplay.setFont(new Font(10));
        spriteNameDisplay.setTextAlignment(TextAlignment.CENTER);
        spriteNameDisplay.setWrappingWidth(90);

        display.getChildren().addAll(previewImage, spriteNameDisplay);
        displayPane.getChildren().addAll(spriteBackground, display);
        objectLayout.getChildren().addAll(displayPane, tagGroup);

        try {
            setupEditableContent();
        }
        catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        setupOverlayContent();
        setupTooltipText(getSpriteInfo());
    }

    public StackPane getSpriteBody () {
        return displayPane;
    }

    private String getSpriteType () {
        String path = this.getClass().toString();
        path = path.substring(path.indexOf(".") + 1, path.length());
        path = path.substring(path.indexOf(".") + 1, path.indexOf("View"));
        return path;
    }

    private void setupEditableContent () throws ClassNotFoundException, IllegalArgumentException,
                                        IllegalAccessException {
        editableContent = new VBox(PADDING);
        editableContent.setAlignment(Pos.CENTER);
        editableContent.setMaxWidth(300);

        overlaySpriteNameDisplay = new Text(spriteName);
        overlaySpriteNameDisplay.setFont(new Font(30));
        overlaySpriteNameDisplay.setFill(Color.WHITE);

        overlayErrorMessage = new Text("Please check your parameters for errors.");
        overlayErrorMessage.setFill(Color.RED);
        overlayErrorMessage.setVisible(false);

        editableContent.getChildren().addAll(overlaySpriteNameDisplay, overlayErrorMessage);
        
        ScrollPane settingsDisplayPane = new ScrollPane();
        settingsDisplayPane.setPrefHeight(DISPLAY_PANE_HEIGHT);
        settingsDisplayPane.setMaxWidth(DISPLAY_PANE_WIDTH);
        settingsDisplayPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        settingsDisplayPane.setVbarPolicy(ScrollBarPolicy.NEVER);
        
        StackPane settingsDisplay = new StackPane();
        Rectangle displayBackground = new Rectangle(DISPLAY_PANE_WIDTH, DISPLAY_PANE_HEIGHT);
        displayBackground.setOpacity(OVERLAY_OPACITY);

        VBox settingsObjects = new VBox(PADDING);
        settingsObjects.setMaxWidth(DISPLAY_PANE_WIDTH);
        List<Setting> settings = ProjectReader.generateSettingsList(myController, getSpriteType());
        // move the image to be first in the settings list
        for (int i = 0; i < settings.size(); i++) {
            if (settings.get(i) instanceof ImageViewSetting) {
                parameterFields.add(0, settings.get(i));
                editableContent.getChildren().add(settings.get(i));
                break;
            }
        }
        int spriteSettingCounter = 0; // For background scaling purposes
        for (int j = 0; j < settings.size(); j++) {
            if (settings.get(j) instanceof ImageViewSetting) {
                continue;
            }
            if (settings.get(j) instanceof StringSetting &&
                settings.get(j).getParameterName().equals("name")) {
                ((StringSetting) settings.get(j)).setCheckName(true);
                parameterFields.add(1, settings.get(j));
                editableContent.getChildren().add(settings.get(j));
                continue;
            }
            if(settings.get(j) instanceof SpriteSetting){
                spriteSettingCounter++;
            }
            parameterFields.add(settings.get(j));
            settingsObjects.getChildren().add(settings.get(j));
        }
        
        settingsObjects.setTranslateY(PADDING);
        
        int numSettings = settings.size();
        adjustBackground(displayBackground, spriteSettingCounter, numSettings);
        
        settingsDisplay.getChildren().addAll(displayBackground, settingsObjects);
        settingsDisplayPane.setContent(settingsDisplay);
        initializeSpriteInfo();

        HBox buttons = new HBox(PADDING);

        saved = new Text(getSpriteType() + " saved!");
        saved.setFill(Color.YELLOW);
        saved.setVisible(false);

        saveButton.setOnAction( (e) -> {
            if (saveParameterFields(true)) {
                displaySavedMessage();
            }
        });

        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(saveButton, cancelButton);

        editableContent.getChildren().addAll(settingsDisplayPane, buttons, saved);
    }

    /**
     * Adjusts the height of the background for the ScrollPane that displays the settings objects
     * @param displayBackground the Rectangle object that is the background
     * @param spriteSettingCounter      the number of spriteSettings that are being displayed
     * @param numSettings       the number of settings objects that are being displayed
     */
    private void adjustBackground (Rectangle displayBackground,
                                   int spriteSettingCounter,
                                   int numSettings) {
        int settingHeight = 24;
        int spriteSettingHeight = 79;
        int newHeight = (numSettings - spriteSettingCounter) * settingHeight + spriteSettingCounter * spriteSettingHeight + numSettings*PADDING + PADDING;
        if(newHeight > DISPLAY_PANE_HEIGHT){
            displayBackground.setHeight(newHeight);
        }
    }

    private void initializeSpriteInfo () {
        if (spriteName.length() >= 1) {
            parameterFields.get(NAME_INDEX).setParameterValue(spriteName);
        }
        parameterFields.get(IMAGE_INDEX).setParameterValue(imageFile);
    }

    public boolean saveParameterFields (boolean save) {
        boolean correctFormat = true;
        for (Setting s : parameterFields) {
            boolean correct = s.processData();
            if (correctFormat && !correct) {
                correctFormat = false;
            }
        }
        overlayErrorMessage.setVisible(!correctFormat);
        if (parameterFields.get(NAME_INDEX).processData()) {
            updateSpriteName();
        }

        if (parameterFields.get(IMAGE_INDEX).processData()) {
            updateImageFile();
        }

        if (correctFormat && save) {
            try {
                if (myKey.equals(Controller.KEY_BEFORE_CREATION))
                    myKey = myController.addPartToGame(getSpriteType(),
                                                       parameterFields);
                else
                    myKey = myController.addPartToGame(myKey, getSpriteType(), parameterFields);
            }
            catch (MissingInformationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            myController.addImageToPart(myKey, new Image(imageFile));
            tagGroup.setKey(myKey);
            displaySavedMessage();
        }
        return correctFormat && save;
    }

    private void updateImageFile () {
        imageFile = parameterFields.get(IMAGE_INDEX).getDataAsString();
        previewImage = new ImageView(new Image(imageFile));
        ScaleImage.scale(previewImage, 90, 70);
        display.getChildren().remove(0);
        display.getChildren().add(0, previewImage);
    }

    private void updateSpriteName () {
        spriteName = parameterFields.get(NAME_INDEX).getDataAsString();
        spriteNameDisplay.setText(spriteName);
        overlaySpriteNameDisplay.setText(spriteName);
    }

    private void displaySavedMessage () {
        saved.setVisible(true);
        PauseTransition pause = new PauseTransition(Duration.millis(1000));
        pause.play();
        pause.setOnFinished(ae -> saved.setVisible(false));
    }

    /**
     * Revert to previous parameter values.
     */
    public void discardUnsavedChanges () {
        for (Setting s : parameterFields) {
            s.displaySavedValue();
        }
        saveParameterFields(false);
    }

    private void setupOverlayContent () {
        overlayContent = new StackPane();
        Rectangle overlayBackground = new Rectangle(CONTENT_WIDTH, CONTENT_HEIGHT);
        overlayBackground.setOpacity(OVERLAY_OPACITY);
        overlayContent.getChildren().addAll(overlayBackground, editableContent);
    }

    /**
     * Returns the sprite's parameter fields as an array of two strings, the parameter name
     * and the parameter field value.
     * 
     * @return info list of arrays containing parameter information for this sprite.
     */
    public List<String[]> getSpriteInfo () {
        List<String[]> info = new ArrayList<>();
        for (Setting setting : parameterFields) {
            info.add(new String[] { setting.getParameterName(), setting.getDataAsString() });
        }
        return info;
    }

    /**
     * Setup the tooltip text for this sprite.
     * 
     * @param info this sprite's parameter information
     */
    public void setupTooltipText (List<String[]> info) {
        String tooltipText = "";
        for (String[] parameter : info) {
            tooltipText += String.format("%s: %s\n", parameter[0], parameter[1]);
        }

        Tooltip tooltip = new Tooltip(tooltipText);
        tooltip.setTextAlignment(TextAlignment.LEFT);
        Tooltip.install(displayPane, tooltip);
    }

    public String getImageFilePath () {
        return imageFile;
    }

    public List<Setting> getParameterFields () {
        return parameterFields;
    }

    public StackPane getEditorOverlay () {
        return overlayContent;
    }

    public String getName () {
        return spriteName;
    }
}
