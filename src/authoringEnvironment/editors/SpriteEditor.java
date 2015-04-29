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
import authoringEnvironment.Controller;
import authoringEnvironment.objects.ObjectView;
import authoringEnvironment.objects.SpriteView;
import authoringEnvironment.objects.Tag;
import authoringEnvironment.objects.TagGroup;
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
    private boolean editing = false;
    private Text empty;
    protected List<Node> spritesCreated;
    protected List<TagGroup> tagGroupsList;
    protected IntegerProperty numSprites;
    protected NamePrompt prompt;

    private static final int ROW_SIZE = 7;
    private static final Color BACKGROUND_COLOR = Color.GRAY;

    private Node activeOverlay;

    private static final String SPRITE_TYPES = "resources/sprite_parameter_type";
    private static final ResourceBundle spriteNeeded = ResourceBundle.getBundle(SPRITE_TYPES);

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

        // TODO remove magic number
        Rectangle background = new Rectangle(CONTENT_WIDTH, CONTENT_HEIGHT, BACKGROUND_COLOR);

        VBox spriteDisplay = new VBox(20);
        spriteDisplay.setTranslateY(10);

        HBox editControls = setupEditControls();
        spriteDisplay.getChildren().add(editControls);

        ArrayList<HBox> rows = new ArrayList<>();

        // TODO remove magic numbers
        HBox row = new HBox(20);
        currentRow = row;
        spriteDisplay.getChildren().add(row);
        rows.add(row);

        numSprites = new SimpleIntegerProperty(0);
        numSprites.addListener( (obs, oldValue, newValue) -> {
            handleSpritePlacement(spriteDisplay, rows, oldValue, newValue);
        });

        empty = new Text("No " + tabNames.getString(editorType).toLowerCase() + " yet...");
        empty.setFont(new Font(30));
        empty.setFill(Color.WHITE);

        currentRow.setAlignment(Pos.TOP_CENTER);
        currentRow.setMaxHeight(100);

        tagGroupsList = new ArrayList<>();

        TagDisplay tags = new TagDisplay(myController, tagGroupsList, spritesCreated);
        ObservableList<Tag> list = tags.getTagsList();
        list.addListener(new ListChangeListener<Tag>() {
            @Override
            public void onChanged(ListChangeListener.Change change){
                tags.setupDraggableTags(visuals);
                for(TagGroup group : tagGroupsList){
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
            HBox newRow = new HBox(20);
            newRow.setAlignment(Pos.TOP_CENTER);
            currentRow = newRow;
            rows.add(newRow);
            spriteDisplay.getChildren().add(newRow);
        }

        else if ((int) newValue < (int) oldValue) {
            System.out.println("rows: " + rows.size());
        }
    }

    private HBox setupEditControls () {
        HBox editControls = new HBox(10);
        editControls.setAlignment(Pos.CENTER_RIGHT);
        Button edit = new Button("Edit");
        edit.setPrefWidth(100);
        edit.setTranslateX(-10);

        Button add = new Button("+ "
                + partNames.getString(editorType));
        add.setTranslateX(-10);
        add.setPrefWidth(100);
        add.setOnMousePressed( (e) -> {
            promptSpriteCreation();
        });

        edit.setOnAction( (e) -> {
            if (!editing) {
                startEditing(editControls, edit, add);
            }
            else {
                finishEditing(editControls, edit, add);
            }
            editing = !editing;
        });
        editControls.getChildren().add(edit);
        return editControls;
    }

    protected void promptSpriteCreation () {
        Button create = prompt.getCreateButton();
        create.setOnAction( (e) -> {
            if (myController.nameAlreadyExists(partNames.getString(editorType),
                                               prompt.getCurrentText()))
                prompt.displayError("A " + partNames.getString(editorType).toLowerCase() +
                        " with that name already exists!");
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
            if (myController.getKeysForPartType(needed).size() == 0) {
                prompt.displayPermanentError(String.format("Please create %ss first!",
                                                           needed.toLowerCase()));
            }
        }
        catch (MissingResourceException e) {
        }
    }
    
    protected void addPart(){
        try{
            addSprite(prompt.getEnteredName(), prompt.getSelectedImageFile());
        }
        catch(Exception e){
        }
    }

    private void addSprite (String name, String imageFile){
        String className = "authoringEnvironment.objects."
                + partNames.getString(editorType) + "View";
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
            if(!newValue){
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
    }

    private void setupSpriteAction (SpriteView sprite) {
        sprite.getSpriteBody().setOnMousePressed( (e) -> {
            if (sprite.isExisting().getValue() && editing){
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
            Scaler.scaleOverlay(0.0, 1.0, overlay);
            isOverlayActive = true;
        }
    }

    @Override
    public void hideOverlay () {
        if (isOverlayActive) {
            ScaleTransition scale = Scaler.scaleOverlay(1.0, 0.0, getActiveOverlay());
            scale.setOnFinished(e -> {
                visuals.getChildren().remove(getActiveOverlay());
                myContent.getChildren().remove(getActiveOverlay()); // in case I added overlay to the
                // StackPane (prompt)
                isOverlayActive = false;
            });
        }
    }

    private void finishEditing (HBox editControls, Button edit, Button add) {
        TranslateTransition move = transitionButton(add, -10, 90);
        move.setOnFinished(e -> editControls.getChildren().remove(0));
        edit.setText("Edit");
        makeSpritesUneditable();
    }

    protected void makeSpritesUneditable () {
        for (Node sprite : spritesCreated) {
            ((ObjectView) sprite).exitEditableState();
        }
    }

    private void startEditing (HBox editControls, Button edit, Button add) {
        editControls.getChildren().add(0, add);
        transitionButton(add, 90, -10);
        edit.setText("Done");
        makeSpritesEditable();
    }

    protected void makeSpritesEditable () {
        for (Node sprite : spritesCreated) {
            ((ObjectView) sprite).initiateEditableState();
        }
    }

    private TranslateTransition transitionButton (Button add, double from,
                                                  double to) {
        TranslateTransition moveButton = new TranslateTransition(Duration.millis(100), add);
        moveButton.setFromX(from);
        moveButton.setToX(to);
        moveButton.setCycleCount(1);
        moveButton.play();

        return moveButton;
    }

    public Node getActiveOverlay () {
        return activeOverlay;
    }

    public void setActiveOverlay (Node activeOverlay) {
        this.activeOverlay = activeOverlay;
    }
}
