package authoringEnvironment.editors;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import authoringEnvironment.AuthoringEnvironment;
import authoringEnvironment.Controller;
import authoringEnvironment.objects.ObjectView;
import authoringEnvironment.objects.SpriteView;
import authoringEnvironment.objects.Tag;
import authoringEnvironment.objects.TagGroup;
import authoringEnvironment.util.ErrorAlert;
import authoringEnvironment.util.NamePrompt;
import authoringEnvironment.util.Scaler;


/**
 * General abstract class for editors that allow user interaction in
 * sprite/property creation and editing
 * 
 * @author Kevin He
 * @author Callie Mao
 */

public abstract class SpriteEditor extends Editor {
    private Group visuals;
    protected StackPane myContent;
    protected HBox currentRow;
    protected boolean editing = false;
    private Text empty;
    protected List<Node> spritesCreated;
    protected List<TagGroup> tagGroupsList;
    protected IntegerProperty numSprites;
    protected NamePrompt prompt;
    private Node activeOverlay;

    private static final int ROW_SIZE = 7;
    private static final int BUTTON_OFFSET = -10;
    private static final int BUTTON_WIDTH = 100;
    private static final int SPRITE_SIZE = 100;
    private static final Color BACKGROUND_COLOR = Color.GRAY;
    private static final int PADDING = 20;
    private static final int INITIAL_NUM_SPRITES = 0;
    private static final double EMPTY_TEXT_SIZE = CONTENT_WIDTH / 40;
    private static final int MOVE_BUTTON_DURATION = 100;
    
    private static final double START_VALUE = 0.0;
    private static final double END_VALUE = 1.0;

    private static final String SPRITE_REQUIRED_ERROR = "Please create %ss first!";
    private static final String NAME_EXISTS_ERROR = "A %s with that name already exists!";
    private static final String EMPTY_MESSAGE = "No %s yet...";
    private static final String ADD_SPRITE = "+ %s";
    private static final String SPRITE_PACKAGE_PATH = "authoringEnvironment.objects.%sView";
    
    private static final String SPRITE_TYPES = "resources/sprite_parameter_type";
    private static final ResourceBundle spriteNeeded = ResourceBundle.getBundle(SPRITE_TYPES);
    private static final String INTERFACE_TEXT = "resources/display/interface_text";
    private static final ResourceBundle displayText = ResourceBundle.getBundle(INTERFACE_TEXT);

    /**
     * Creates a tower object.
     * 
     * @param dim
     *        dimensions of the environment
     * @param rb
     *        the resource bundle containing displayed strings
     * @param s
     *        the stage on which the authoring environment is displayed
     */
    public SpriteEditor (Controller c, String name) {
        super(c, name);
    }

    /**
     * Sets up the editor UI.
     */
    @Override
    protected Group configureUI () {
        // TODO Auto-generated method stub
        visuals = new Group();
        myContent = new StackPane();
        spritesCreated = new ArrayList<>();

        prompt = new NamePrompt(partNames.getString(editorType).toLowerCase());
        prompt.setImageChooser(true);

        Rectangle background = new Rectangle(CONTENT_WIDTH, CONTENT_HEIGHT, BACKGROUND_COLOR);

        VBox spriteDisplay = new VBox(PADDING);
        spriteDisplay.setTranslateY(PADDING / 2);

        HBox editControls = setupEditControls();
        spriteDisplay.getChildren().add(editControls);

        ArrayList<HBox> rows = new ArrayList<>();

        HBox row = new HBox(PADDING);
        currentRow = row;
        spriteDisplay.getChildren().add(row);
        rows.add(row);

        numSprites = new SimpleIntegerProperty(INITIAL_NUM_SPRITES);
        numSprites.addListener( (obs, oldValue, newValue) -> {
            handleSpritePlacement(spriteDisplay, rows, oldValue, newValue);
        });

        empty = new Text(String.format(EMPTY_MESSAGE, tabNames.getString(editorType).toLowerCase()));
        empty.setFont(new Font(EMPTY_TEXT_SIZE));
        empty.setFill(Color.WHITE);

        currentRow.setAlignment(Pos.TOP_CENTER);
        currentRow.setMaxHeight(SPRITE_SIZE);

        tagGroupsList = new ArrayList<>();

        TagDisplay tags = new TagDisplay(myController, tagGroupsList, spritesCreated);
        ObservableList<Tag> list = tags.getTagsList();
        list.addListener(new ListChangeListener<Tag>() {
            @Override
            public void onChanged (ListChangeListener.Change change) {
                tags.setupDraggableTags(visuals);
                for (TagGroup group : tagGroupsList) {
                    group.update();
                }
            }
        });
        StackPane.setAlignment(tags, Pos.CENTER_LEFT);

        myContent.getChildren().addAll(background, spriteDisplay, empty);
        StackPane.setAlignment(spriteDisplay, Pos.TOP_CENTER);
        visuals.getChildren().addAll(myContent, tags);

        return visuals;
    }

    protected NamePrompt getPrompt () {
        return prompt;
    }

    private void handleSpritePlacement (VBox spriteDisplay,
                                        ArrayList<HBox> rows,
                                        Number oldValue,
                                        Number newValue) {
        if ((int) newValue == 0) {
            myContent.getChildren().add(empty);
        }
        else if ((int) newValue > 0
                 && myContent.getChildren().contains(empty)) {
            myContent.getChildren().remove(empty);
        }

        // if there's 2 on a row already
        else if (currentRow.getChildren().size() == ROW_SIZE) {
            HBox newRow = new HBox(PADDING);
            newRow.setAlignment(Pos.TOP_CENTER);
            currentRow = newRow;
            rows.add(newRow);
            spriteDisplay.getChildren().add(newRow);
        }

        else if ((int) newValue < (int) oldValue) {
//            System.out.println("rows: " + rows.size());
        }
    }

    private HBox setupEditControls () {
        HBox editControls = new HBox(PADDING / 2);
        // editControls.setTranslateX(-10);
        editControls.setAlignment(Pos.CENTER_RIGHT);
        Button edit = new Button(displayText.getString("Edit"));
        edit.setPrefWidth(BUTTON_WIDTH);
        edit.setTranslateX(BUTTON_OFFSET);

        Button load = new Button(displayText.getString("Load"));
        load.setPrefWidth(BUTTON_WIDTH);
        load.setTranslateX(BUTTON_OFFSET);
        load.setOnAction(e -> {
            loadSprite();
        });

        Button add = new Button(String.format(ADD_SPRITE, partNames.getString(editorType)));
        add.setPrefWidth(BUTTON_WIDTH);
        add.setTranslateX(BUTTON_OFFSET);
        add.setOnMousePressed( (e) -> {
            promptSpriteCreation();
        });

        edit.setOnAction( (e) -> {
            if (!editing) {
                startEditing(editControls, edit, add, load);
            }
                else {
                    finishEditing(editControls, edit, add, load);
                }
                editing = !editing;
            });
        editControls.getChildren().add(edit);
        return editControls;
    }

    protected void loadSprite () {
        ErrorAlert test = new ErrorAlert("This is coming soon!");
        test.showError();
        myContent.getChildren().add(test);
        test.getOkButton().setOnAction(e -> {
            test.hideError().setOnFinished(ae -> {
                myContent.getChildren().remove(test);
            });
        });
    }

    protected void promptSpriteCreation () {
        Button create = prompt.getCreateButton();
        create.setOnAction( (e) -> {
            if (myController.nameAlreadyExists(partNames.getString(editorType),
                                               prompt.getCurrentText()))
                prompt.displayError(String.format(NAME_EXISTS_ERROR, partNames
                        .getString(editorType).toLowerCase()));
                else {
                    addPart();
                    hideOverlay();
                }
            });

        Button cancel = prompt.getCancelButton();
        cancel.setOnAction( (e) -> {
            hideOverlay();
        });

        // TODO DUPLICATED
        prompt.showPrompt(myContent);
        checkNeededParts();

        isOverlayActive = true;
        setActiveOverlay(prompt);
    }

    protected void checkNeededParts () {
        String type = partNames.getString(editorType);
        try {
            String needed = spriteNeeded.getString(type);
            if (myController.getKeysForPartType(needed).isEmpty() && !needed.equals(type)) {
                prompt.displayPermanentError(String.format(SPRITE_REQUIRED_ERROR,
                                                           needed.toLowerCase()));
            }
        }
        catch (MissingResourceException e) {
        }
    }

    protected void addPart () {
        try {
            addSprite(prompt.getEnteredName(), prompt.getSelectedImageFile());
        }
        catch (Exception e) {
        }
    }

    private void addSprite (String name, String imageFile) {
        String className = String.format(SPRITE_PACKAGE_PATH, partNames.getString(editorType));
        SpriteView sprite = generateSpriteView(myController, name, imageFile, className);
        sprite.initiateEditableState();
        setupSpriteAction(sprite);
        updateOnExists(sprite);

        sprite.saveParameterFields(true);
    }

    protected void updateOnExists (ObjectView sprite) {
        BooleanProperty spriteExists = new SimpleBooleanProperty(true);
        spriteExists.bind(sprite.isExisting());
        spriteExists.addListener( (obs, oldValue, newValue) -> {
            if (!newValue) {
                deleteSpriteFromRow(sprite);
            }
        });
        addSpriteToRow(sprite);
    }

    private SpriteView generateSpriteView (Controller c, String name, String imageFile,
                                           String className) {
        SpriteView sprite = null;
        try {
            sprite = (SpriteView) Class.forName(className)
                    .getConstructor(Controller.class, String.class, String.class)
                    .newInstance(c, name, imageFile);
        }
        catch (InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException
                | ClassNotFoundException e1) {
            System.err
                    .println("Class: "
                             + className
                             +
                             "\nCouldn't be created with constructor (Controller, String, String)");
            e1.printStackTrace();
        }
        return sprite;
    }

    protected void addSpriteToRow (ObjectView sprite) {
        currentRow.getChildren().add(sprite);
        spritesCreated.add(sprite);
        tagGroupsList.add(sprite.getTagGroup());
        numSprites.setValue(spritesCreated.size());
    }

    protected void deleteSpriteFromRow (ObjectView sprite) {
        currentRow.getChildren().remove(sprite);
        spritesCreated.remove(sprite);
        tagGroupsList.remove(sprite.getTagGroup());
        numSprites.setValue(spritesCreated.size());
        
        //TODO: delete from controller
    }

    private void setupSpriteAction (SpriteView sprite) {
        sprite.getSpriteBody().setOnMousePressed( (e) -> {
            if (sprite.isExisting().getValue() && editing) {
                showOverlay(sprite.getEditorOverlay());
                setActiveOverlay(sprite.getEditorOverlay());
            }
        });

        sprite.getCloseButton().setOnAction( (e) -> {
            hideOverlay();
            sprite.discardUnsavedChanges();
            sprite.setupTooltipText(sprite.getSpriteInfo());
        });
    }

    private void showOverlay (StackPane overlay) {
        if (!isOverlayActive) {
            visuals.getChildren().add(overlay);
            Scaler.scaleOverlay(START_VALUE, END_VALUE, overlay);
            isOverlayActive = true;
        }
    }

    @Override
    public void hideOverlay () {
        if (isOverlayActive) {
            ScaleTransition scale = Scaler.scaleOverlay(END_VALUE, START_VALUE, getActiveOverlay());
            scale.setOnFinished(e -> {
                visuals.getChildren().remove(getActiveOverlay());
                myContent.getChildren().remove(getActiveOverlay()); // in case I added overlay to
                                                                    // the
                // StackPane (prompt)
                isOverlayActive = false;
            });
        }
    }

    private void finishEditing (HBox editControls, Button edit, Button add, Button load) {
        TranslateTransition moveAdd =
                transitionButton(add, BUTTON_OFFSET, BUTTON_WIDTH + BUTTON_OFFSET);
        TranslateTransition moveLoad =
                transitionButton(load, BUTTON_OFFSET, BUTTON_WIDTH + BUTTON_OFFSET);
        moveAdd.setOnFinished(e -> editControls.getChildren().remove(add));
        moveLoad.setOnFinished(e -> editControls.getChildren().remove(load));
        edit.setText(displayText.getString("Edit"));
        makeSpritesUneditable();
    }

    private void startEditing (HBox editControls, Button edit, Button add, Button load) {
        editControls.getChildren().add(0, add);
        editControls.getChildren().add(1, load);
        transitionButton(load, BUTTON_WIDTH + BUTTON_OFFSET, BUTTON_OFFSET);
        transitionButton(add, 2 * BUTTON_WIDTH + BUTTON_OFFSET, BUTTON_OFFSET);
        edit.setText(displayText.getString("Done"));
        makeSpritesEditable();
    }

    protected void makeSpritesUneditable () {
        for (Node sprite : spritesCreated) {
            ((ObjectView) sprite).exitEditableState();
        }
    }

    protected void makeSpritesEditable () {
        for (Node sprite : spritesCreated) {
            ((ObjectView) sprite).initiateEditableState();
        }
    }

    private TranslateTransition transitionButton (Button add, double from,
                                                  double to) {
        TranslateTransition moveButton = new TranslateTransition(Duration.millis(MOVE_BUTTON_DURATION), add);
        moveButton.setFromX(from);
        moveButton.setToX(to);
        moveButton.play();

        return moveButton;
    }

    public Node getActiveOverlay () {
        return activeOverlay;
    }

    public void setActiveOverlay (Node activeOverlay) {
        this.activeOverlay = activeOverlay;
    }
    
    public void update(){}
}
