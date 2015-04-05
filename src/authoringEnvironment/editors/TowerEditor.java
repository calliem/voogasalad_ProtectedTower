package authoringEnvironment.editors;

import imageSelector.ImageSelector;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Dimension2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import authoringEnvironment.MainEnvironment;
import authoringEnvironment.objects.TowerView;

/**
 * Creates the Tower Editor that allows the user to create
 * and edit towers.
 *  
 * @author Kevin He
 *
 */
public class TowerEditor extends PropertyEditor{
    private Group myRoot;
    private StackPane myContent;
    private HBox currentRow;
    private boolean overlayActive = false;
    private boolean editing = false;
    private Text empty;
    private List<TowerView> towersCreated;
    private IntegerProperty numTowers;

    private static final double CONTENT_WIDTH = MainEnvironment.getEnvironmentWidth();
    private static final double CONTENT_HEIGHT = 0.89 * MainEnvironment.getEnvironmentHeight();
    
    private static final int ROW_SIZE = 2;

    /**
     * Creates a tower object.
     * @param dim       dimensions of the environment
     * @param rb        the resource bundle containing displayed strings
     * @param s the stage on which the authoring environment is displayed
     */
    public TowerEditor(Dimension2D dim, ResourceBundle rb, Stage s) {
        super(dim, rb, s);
    }
    
    /**
     * Gets the list of towers that the user has created.
     * @return towersCreated    the list of towers that the user has created.
     */
    public List<TowerView> getTowers(){
        return towersCreated;
    }

    /**
     * Sets up the editor UI.
     */
    @Override
    public Group configureUI () {
        // TODO Auto-generated method stub
        myRoot = new Group();
        myContent = new StackPane();
        towersCreated = new ArrayList<>();

        // TODO remove magic number
        Rectangle background = new Rectangle(CONTENT_WIDTH, CONTENT_HEIGHT, Color.GRAY);

        VBox towersDisplay = new VBox(20);
        towersDisplay.setTranslateY(10);

        HBox editControls = setupEditControls();
        towersDisplay.getChildren().add(editControls);
        
        ArrayList<HBox> rows = new ArrayList<>();
        
        // TODO remove magic numbers
        HBox row = new HBox(20);
        currentRow = row;
        towersDisplay.getChildren().add(row);
        rows.add(row);

        numTowers = new SimpleIntegerProperty(0);
        numTowers.addListener((obs, oldValue, newValue) -> {
            if((int) newValue == 0){
                myContent.getChildren().add(empty);
            }
            else if((int) newValue > 0 && myContent.getChildren().contains(empty)){
                myContent.getChildren().remove(empty);
            }
            
            // if there's 2 on a row already
            else if(currentRow.getChildren().size() == ROW_SIZE){
                HBox newRow = new HBox(20);
                newRow.setAlignment(Pos.TOP_CENTER);
                currentRow = newRow;
                rows.add(newRow);
                towersDisplay.getChildren().add(newRow);
            }
            
            else if((int)newValue < (int)oldValue){
                System.out.println("rows: " + rows.size());
            }
        });
        
        empty = new Text("No towers have been made...yet.");
        empty.setFont(new Font(30));
        empty.setFill(Color.WHITE);

        currentRow.setAlignment(Pos.TOP_CENTER);
        currentRow.setMaxHeight(100);

        myContent.getChildren().addAll(background, towersDisplay, empty);
        StackPane.setAlignment(towersDisplay, Pos.TOP_CENTER);
        myRoot.getChildren().add(myContent);

        return myRoot;
    }

    private HBox setupEditControls () {
        HBox editControls = new HBox(10);
        editControls.setAlignment(Pos.CENTER_RIGHT);
        Button edit = new Button("Edit");
        edit.setTranslateX(-10);

        Button add = new Button("Add Tower");
        add.setTranslateX(-10);
        add.setOnMousePressed((e) -> {
            promptNewTowerName();
        });

        edit.setOnAction((e) -> {
            if(!editing){
                startEditing(editControls, edit, add);
            }
            else{
                finishEditing(editControls, edit);
            }
            editing = !editing;
        });
        editControls.getChildren().add(edit);
        return editControls;
    }

    private void promptNewTowerName(){
        StackPane promptDisplay = new StackPane();
        Rectangle promptBackground = new Rectangle(300, 400);
        promptBackground.setOpacity(0.8);

        VBox promptContent = new VBox(20);
        promptContent.setAlignment(Pos.CENTER);
        Text prompt = new Text("Creating a new tower...");
        prompt.setFill(Color.WHITE);
        TextField promptField = new TextField();
        promptField.setMaxWidth(225);
        promptField.setPromptText("Enter a name...");
        
        ImageSelector imgSelector = new ImageSelector(myStage);
        imgSelector.addExtensionFilter("png");
        imgSelector.addExtensionFilter("jpg");

        HBox buttons = new HBox(10);
        Button create = new Button("Create");
        create.setOnAction((e) -> {
            addTower(promptField.getText(), imgSelector.getSelectedImageFile(), currentRow);
            hideEditScreen(promptDisplay);
        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction((e) -> {
            hideEditScreen(promptDisplay);
        });

        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(create, cancel);
        promptContent.getChildren().addAll(prompt, promptField, imgSelector, buttons);

        promptDisplay.getChildren().addAll(promptBackground, promptContent);
        showEditScreen(promptDisplay);
    }

    private void finishEditing (HBox editControls, Button edit) {
        editControls.getChildren().remove(0);
        edit.setText("Edit");
        for(TowerView tower: towersCreated){
            tower.exitEditableState();
        }
    }

    private void startEditing (HBox editControls, Button edit, Button add) {
        editControls.getChildren().add(0, add);
        edit.setText("Done");
        for(TowerView tower: towersCreated){
            tower.initiateEditableState();
        }
    }

    private void addTower(String name, String imageFile, HBox row){
        TowerView tower = new TowerView(name, imageFile);
        tower.initiateEditableState();
        setupTowerAction(tower);
        BooleanProperty towerExists = new SimpleBooleanProperty(true);
        towerExists.bind(tower.isExisting());
        towerExists.addListener((obs, oldValue, newValue) -> {
            if(!newValue){
                PauseTransition wait = new PauseTransition(Duration.millis(200));
                wait.setOnFinished((e) -> row.getChildren().remove(tower));
                wait.play();
                towersCreated.remove(tower);
                numTowers.setValue(towersCreated.size());
            }
        });

        row.getChildren().add(tower);
        towersCreated.add(tower);
        numTowers.setValue(towersCreated.size());
    }

    private void showEditScreen(StackPane overlay){
        if(!overlayActive){
            myContent.getChildren().add(overlay);
            scaleEditScreen(0.0, 1.0, overlay);
            overlayActive = true;
        }
    }

    private void hideEditScreen(StackPane overlay){
        if(overlayActive){
            ScaleTransition scale = scaleEditScreen(1.0, 0.0, overlay);
            scale.setOnFinished((e) -> {
                myContent.getChildren().remove(overlay);
                overlayActive = false;
            });
        }
    }

    private void setupTowerAction(TowerView tower){
        tower.setOnMousePressed((e) -> {
            if(tower.isExisting().getValue())
                showEditScreen(tower.getEditorOverlay());
        });
        tower.getCloseButton().setOnAction((e) -> {
            hideEditScreen(tower.getEditorOverlay());
            tower.discardUnsavedChanges();
            tower.setupTooltipText(tower.getTowerInfo());
        });
    }

    private ScaleTransition scaleEditScreen(double from, double to, StackPane overlay){
        ScaleTransition scale = new ScaleTransition(Duration.millis(200), overlay);
        scale.setFromX(from);
        scale.setFromY(from);
        scale.setToX(to);
        scale.setToY(to);
        scale.setCycleCount(1);
        scale.play();

        return scale;
    }
}
