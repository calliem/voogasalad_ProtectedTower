package authoring.environment;

import java.util.ArrayList;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import authoring.environment.objects.Tower;


public class TowerEditor extends PropertyEditor {
    private Group myRoot;

    public ArrayList<Tower> getTowers () {
        return new ArrayList<>();
    }

    @Override
    protected Group configureUI () {
        // TODO Auto-generated method stub
        myRoot = new Group();
        StackPane content = new StackPane();

        // TODO remove magic number
        Rectangle background =
                new Rectangle(MainEnvironment.myDimensions.getWidth(),
                              0.9 * MainEnvironment.myDimensions.getHeight(), Color.BLUE);

        // TODO remove magic numbers
        HBox row = new HBox(10);
        row.setAlignment(Pos.TOP_CENTER);
        Rectangle tower = new Rectangle(40, 40, Color.WHITE); // hard-coded placeholders
        tower.setOnMousePressed( (e) -> handleTowerEdit());
        Rectangle tower2 = new Rectangle(40, 40, Color.WHITE);
        Rectangle tower3 = new Rectangle(40, 40, Color.WHITE);
        row.getChildren().addAll(tower, tower2, tower3);

        content.getChildren().addAll(background, row);
        myRoot.getChildren().addAll(content);
        return myRoot;
    }

    private void handleTowerEdit () {
        Rectangle overlay =
                new Rectangle(MainEnvironment.myDimensions.getWidth(),
                              0.9 * MainEnvironment.myDimensions.getHeight());
        overlay.setOpacity(0.6);
        myRoot.getChildren().add(overlay);
        showEditScreen(overlay);
    }

    private void showEditScreen (Rectangle overlay) {
        ScaleTransition scale = new ScaleTransition(Duration.millis(500), overlay);
        scale.setFromX(0.0);
        scale.setFromY(0.0);
        scale.setToX(1.0);
        scale.setToY(1.0);
        scale.setCycleCount(1);
        scale.play();
    }
}
