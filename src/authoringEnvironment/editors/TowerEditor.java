package authoringEnvironment.editors;

import imageselectorTEMP.ImageSelector;
import java.util.ArrayList;
import java.util.List;
import util.reflection.Reflection;
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
import javafx.scene.control.TextField;
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
import authoringEnvironment.objects.SpriteView;
import authoringEnvironment.objects.TowerView;

/**
 * Creates the Tower Editor that allows the user to create
 * and edit towers.
 *  
 * @author Kevin He
 *
 */
public class TowerEditor extends PropertyEditor{
//    private StackPane myContent;
//    private HBox currentRow;
//    private boolean overlayActive = false;
//    private boolean editing = false;
//    private Text empty;
//    private List<Node> spritesCreated;
//    private IntegerProperty numSprites;
//
//    private static final double CONTENT_WIDTH = AuthoringEnvironment.getEnvironmentWidth();
//    private static final double CONTENT_HEIGHT = 0.89 * AuthoringEnvironment.getEnvironmentHeight();
//
//    private static final int ROW_SIZE = 7;

    /**
     * Creates a tower object.
     * @param dim       dimensions of the environment
     * @param rb        the resource bundle containing displayed strings
     * @param s the stage on which the authoring environment is displayed
     */
    public TowerEditor(Controller c, String name) {
        super(c, name);
    }
}
