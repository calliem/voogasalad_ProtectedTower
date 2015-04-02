package authoring.environment;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.ScaleTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import authoring.environment.objects.TowerView;

public class TowerEditor extends PropertyEditor{
    private Group myRoot;
    private StackPane myContent;
    private HBox currentRow;
    private boolean overlayActive = false;
    private boolean editing = false;
    private Text empty;
    private List<TowerView> towersCreated;

    private static final double CONTENT_WIDTH = MainEnvironment.getEnvironmentWidth();
    private static final double CONTENT_HEIGHT = 0.89 * MainEnvironment.getEnvironmentHeight();

    public ArrayList<TowerView> getTowers(){
        return new ArrayList<>();
    }

    @Override
    protected Group configureUI () {
        // TODO Auto-generated method stub
        myRoot = new Group();
        myContent = new StackPane();
        towersCreated = new ArrayList<>();

        // TODO remove magic number
        Rectangle background = new Rectangle(CONTENT_WIDTH, CONTENT_HEIGHT, Color.GRAY);

        VBox towersDisplay = new VBox(10);
        towersDisplay.setTranslateY(10);

        HBox editControls = setupEditControls();
        towersDisplay.getChildren().add(editControls);

        // TODO remove magic numbers
        currentRow = new HBox(20);
        towersDisplay.getChildren().add(currentRow);

        empty = new Text("No towers have been made...yet.");
        empty.setFont(new Font(30));
        empty.setFill(Color.WHITE);
        currentRow.getChildren().add(empty);

        currentRow.setAlignment(Pos.TOP_CENTER);
        currentRow.setMaxHeight(40);

        myContent.getChildren().addAll(background, towersDisplay);
        StackPane.setAlignment(towersDisplay, Pos.TOP_CENTER);
        myRoot.getChildren().add(myContent);

        return myRoot;
    }

    private HBox setupEditControls () {
        HBox editControls = new HBox(10);
        editControls.setAlignment(Pos.CENTER_RIGHT);
        Button edit = new Button("Edit");
//        edit.setMaxHeight(60);
        edit.setMinHeight(60);
        edit.setMinWidth(100);
        edit.setTranslateX(-10);

        ImageView add = new ImageView(new Image("images/addtower.png"));
        add.setFitHeight(60);
        add.setPreserveRatio(true);
        add.setSmooth(true);
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
        Rectangle promptBackground = new Rectangle(300, 200);
        promptBackground.setOpacity(0.8);
        
        VBox promptContent = new VBox(10);
        promptContent.setAlignment(Pos.CENTER);
        Text prompt = new Text("Creating a new tower...");
        prompt.setFill(Color.WHITE);
        TextField promptField = new TextField();
        promptField.setMaxWidth(200);
        promptField.setPromptText("Enter a name...");
        Button create = new Button("Create");
        create.setOnAction((e) -> {
            addTower(promptField.getText());
            hideEditScreen(promptDisplay);
        });
        promptContent.getChildren().addAll(prompt, promptField, create);
        
        promptDisplay.getChildren().addAll(promptBackground, promptContent);
        showEditScreen(promptDisplay);
    }

    /**
     * @param editControls
     * @param edit
     */
    private void finishEditing (HBox editControls, Button edit) {
        editControls.getChildren().remove(0);
        edit.setText("Edit");
        for(TowerView tower: towersCreated){
            tower.exitEditableState();
        }
    }

    /**
     * @param editControls
     * @param edit
     * @param add
     */
    private void startEditing (HBox editControls, Button edit, ImageView add) {
        editControls.getChildren().add(0, add);
        edit.setText("Done");
        for(TowerView tower: towersCreated){
            tower.initiateEditableState();
        }
    }

    private void addTower(String name){
        TowerView tower = new TowerView(name);
        tower.initiateEditableState();
        setupTowerAction(tower);
        BooleanProperty towerExists = new SimpleBooleanProperty(true);
        towerExists.bind(tower.isExisting());
        towerExists.addListener((obs, oldValue, newValue) -> {
            if(!newValue){
                currentRow.getChildren().remove(tower);
            }
        });

        if(currentRow.getChildren().size() == 1 && currentRow.getChildren().contains(empty)){
            currentRow.getChildren().remove(0);
        }
        currentRow.getChildren().add(tower);
        towersCreated.add(tower);
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
