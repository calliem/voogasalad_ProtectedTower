package authoringEnvironment.objects;

import imageselectorTEMP.util.ScaleImage;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.PauseTransition;
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
import authoringEnvironment.AuthoringEnvironment;
import authoringEnvironment.Controller;
import authoringEnvironment.MissingInformationException;
import authoringEnvironment.ProjectReader;
import authoringEnvironment.setting.Setting;
import authoringEnvironment.util.Scaler;


/**
 * Displays an editable sprite object instance along with overlay interactions upon click.
 * 
 * @author Kevin He
 * @author Callie Mao
 *
 */

public abstract class SpriteView extends StackPane {
    private VBox display;
    private VBox editableContent;
    private StackPane overlayContent;
    private Button overlayCloseButton;
    private Text overlayErrorMessage;
    private BooleanProperty exists;

    private String spriteName;
    private String imageFile;
    private List<Setting> parameterFields;

    private String myKey;

    private Text spriteNameDisplay;
    private Text overlaySpriteNameDisplay;
    private Text saved;

    private static final double CONTENT_WIDTH = AuthoringEnvironment.getEnvironmentWidth();
    private static final double CONTENT_HEIGHT = 0.89 * AuthoringEnvironment.getEnvironmentHeight();

    private static final int IMAGE_INDEX = 0;
    private static final int NAME_INDEX = 1;
    // private static final String DEFAULT_NAME = "Unnamed";

    private Controller myController;
    private String id;
    private ImageView previewImage;

    /**
     * Creates visual representation of a sprite created by
     * the user in the authoring environment.
     * 
     * @param c controller needed to obtain partKeys from other tabs
     * @param name name of this sprite, designated by user
     * @param image the file path of this sprite's image
     */
    public SpriteView (Controller c, String name, String image) {
        myKey = Controller.KEY_BEFORE_CREATION;
        myController = c;

        spriteName = name;
        imageFile = image;
        previewImage = new ImageView(new Image(imageFile));
        ScaleImage.scale(previewImage, 90, 70);

        parameterFields = new ArrayList<>();
        exists = new SimpleBooleanProperty(true);

        display = new VBox(5);
        display.setAlignment(Pos.CENTER);

        Rectangle spriteBackground = new Rectangle(100, 100, Color.WHITE);
        spriteNameDisplay = new Text(spriteName);
        spriteNameDisplay.setFont(new Font(10));
        spriteNameDisplay.setTextAlignment(TextAlignment.CENTER);
        spriteNameDisplay.setWrappingWidth(90);

        display.getChildren().addAll(previewImage, spriteNameDisplay);
        getChildren().addAll(spriteBackground, display);

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

    private String getSpriteType () {
        String path = this.getClass().toString();
        path = path.substring(path.indexOf(".") + 1, path.length());
        path = path.substring(path.indexOf(".") + 1, path.indexOf("View"));
        return path;
    }

    private void setupEditableContent () throws ClassNotFoundException, IllegalArgumentException,
                                        IllegalAccessException {
        editableContent = new VBox(10);
        editableContent.setAlignment(Pos.CENTER);

        overlaySpriteNameDisplay = new Text(spriteName);
        overlaySpriteNameDisplay.setFont(new Font(30));
        overlaySpriteNameDisplay.setFill(Color.WHITE);

        overlayErrorMessage = new Text("Please check your parameters for errors.");
        overlayErrorMessage.setFill(Color.RED);
        overlayErrorMessage.setVisible(false);

        VBox settingsObjects = new VBox(10);
        settingsObjects.setMaxWidth(150);

        List<Setting> settings = ProjectReader.generateSettingsList(myController, getSpriteType());
        for (Setting s : settings) {
            parameterFields.add(s);
            settingsObjects.getChildren().add(s);
        }

        initializeSpriteInfo();

        HBox buttons = new HBox(10);

        saved = new Text(getSpriteType() + " saved!");
        saved.setFill(Color.YELLOW);
        saved.setVisible(false);

        Button save = new Button("Save");
        save.setOnAction( (e) -> {
            if (saveParameterFields(true)) {
                displaySavedMessage();
            }
        });

        overlayCloseButton = new Button("Cancel");

        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(save, overlayCloseButton);

        editableContent.getChildren().addAll(overlaySpriteNameDisplay,
                                             overlayErrorMessage, settingsObjects, buttons, saved);
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
                else myKey = myController.addPartToGame(myKey, getSpriteType(), parameterFields);
            }
            catch (MissingInformationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            myController.specifyPartImage(myKey, imageFile);
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
        overlayBackground.setOpacity(0.8);
        overlayContent.getChildren().addAll(overlayBackground, editableContent);
    }

    /**
     * Make this sprite editable (i.e. allow the user to delete it)
     */
    public void initiateEditableState () {
        ImageView close = new ImageView(new Image("images/close.png"));
        close.setTranslateX(10);
        close.setTranslateY(-10);
        close.setFitWidth(20);
        close.setPreserveRatio(true);
        close.setOnMousePressed( (e) -> {
            Scaler.scaleOverlay(1.0, 0.0, this);
            exists.setValue(false);
        });
        this.getChildren().add(close);
        StackPane.setAlignment(close, Pos.TOP_RIGHT);
    }

    public void exitEditableState () {
        // removes the 'x' button.
        this.getChildren().remove(this.getChildren().size() - 1);
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
        Tooltip.install(this, tooltip);
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

    public Button getCloseButton () {
        return overlayCloseButton;
    }

    public String getName () {
        return spriteName;
    }

    public BooleanProperty isExisting () {
        return exists;
    }
}
