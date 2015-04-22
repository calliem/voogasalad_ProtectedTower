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
import authoringEnvironment.NoImageFoundException;
import authoringEnvironment.objects.SpriteView;
import authoringEnvironment.util.NamePrompt;
import authoringEnvironment.util.Scaler;



/**
 * General abstract class for editors that allow user interaction in
 * sprite/property creation and editing
 * 
 * @author Kevin He
 */
public abstract class SpriteEditor extends Editor {
    private StackPane myContent;
    private HBox currentRow;
    private boolean editing = false;
    private Text empty;
    private List<Node> spritesCreated;
    private IntegerProperty numSprites;
    private NamePrompt prompt;

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
        Group visuals = new Group();
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

        myContent.getChildren().addAll(background, spriteDisplay, empty);
        StackPane.setAlignment(spriteDisplay, Pos.TOP_CENTER);
        visuals.getChildren().add(myContent);
        
        return visuals;
    }

    /**
     * @param spriteDisplay
     * @param rows
     * @param oldValue
     * @param newValue
     */
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
            if(myController.nameAlreadyExists(partNames.getString(editorType), prompt.getCurrentText()))
                prompt.displayError("A " + partNames.getString(editorType).toLowerCase() + " with that name already exists!");
            else{
                try{
                    addSprite(prompt.getEnteredName(), prompt.getSelectedImageFile(), currentRow);
                    hideOverlay();
                }
                catch(NoImageFoundException error){
                    error.printStackTrace();
                }
            }
        });

        Button cancel = prompt.getCancelButton();
        cancel.setOnAction( (e) -> {
            hideOverlay();
        });
        
        //TODO DUPLICATED
        prompt.showPrompt(myContent);
        String type = partNames.getString(editorType);
        try{
            String needed = spriteNeeded.getString(type);
            if(myController.getKeysForPartType(needed).size() == 0){
                prompt.displayPermanentError(String.format("Please create %ss first!", needed.toLowerCase()));
            }
        }
        catch (MissingResourceException e){
        }
        
        isOverlayActive = true;
        activeOverlay = prompt;
    }

    private void addSprite (String name, String imageFile, HBox row) throws NoImageFoundException{
        String className = "authoringEnvironment.objects."
                + partNames.getString(editorType) + "View";
        SpriteView sprite = generateSpriteView(myController, name, imageFile, className);
        sprite.initiateEditableState();
        setupSpriteAction(sprite);
        BooleanProperty spriteExists = new SimpleBooleanProperty(true);
        spriteExists.bind(sprite.isExisting());
        spriteExists.addListener( (obs, oldValue, newValue) -> {
            deleteSprite(row, sprite, newValue);
        });

        row.getChildren().add(sprite);
        spritesCreated.add(sprite);
        sprite.saveParameterFields(true);

        numSprites.setValue(spritesCreated.size());
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

    private void deleteSprite (HBox row, SpriteView sprite, Boolean newValue) {
        if (!newValue) {
            PauseTransition wait = new PauseTransition(Duration.millis(200));
            wait.setOnFinished( (e) -> row.getChildren().remove(sprite));
            wait.play();
            spritesCreated.remove(sprite);
            numSprites.setValue(spritesCreated.size());
        }
    }

    private void setupSpriteAction (SpriteView sprite) {
        sprite.setOnMousePressed( (e) -> {
            if (sprite.isExisting().getValue() && editing){
                showOverlay(sprite.getEditorOverlay());
                activeOverlay = sprite.getEditorOverlay();
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
            myContent.getChildren().add(overlay);
            Scaler.scaleOverlay(0.0, 1.0, overlay);
            isOverlayActive = true;
        }
    }
    
    @Override
    public void hideOverlay(){
        if(isOverlayActive){
            ScaleTransition scale = Scaler.scaleOverlay(1.0, 0.0, activeOverlay);
            scale.setOnFinished(e -> {
                myContent.getChildren().remove(activeOverlay);
                isOverlayActive = false;
            });
        }
    }

    private void finishEditing (HBox editControls, Button edit, Button add) {
        TranslateTransition move = transitionButton(add, -10, 90);
        move.setOnFinished(e -> editControls.getChildren().remove(0));
        edit.setText("Edit");
        for (Node sprite : spritesCreated) {
            ((SpriteView) sprite).exitEditableState();
        }
    }

    private void startEditing (HBox editControls, Button edit, Button add) {
        editControls.getChildren().add(0, add);
        transitionButton(add, 90, -10);
        edit.setText("Done");
        for (Node sprite : spritesCreated) {
            ((SpriteView) sprite).initiateEditableState();
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
}
