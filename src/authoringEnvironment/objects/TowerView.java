package authoringEnvironment.objects;
//
//import imageselectorTEMP.util.ScaleImage;
//import java.util.ArrayList;
//import java.util.List;
//import javafx.animation.PauseTransition;
//import javafx.animation.ScaleTransition;
//import javafx.beans.property.BooleanProperty;
//import javafx.beans.property.SimpleBooleanProperty;
//import javafx.geometry.Pos;
//import javafx.scene.control.Button;
//import javafx.scene.control.Tooltip;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.StackPane;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//import javafx.scene.text.Font;
//import javafx.scene.text.Text;
//import javafx.scene.text.TextAlignment;
//import javafx.util.Duration;
//import authoringEnvironment.AuthoringEnvironment;
//import authoringEnvironment.ProjectReader;
//import authoringEnvironment.setting.Setting;

/**
 * Creates the visual tower object containing
 * the tower's image, name, and the overlay that
 * pops up when the object is clicked.
 * 
 * @author Kevin He
 *
 */
public class TowerView extends SpriteView{
    
    public TowerView(String name, String imageFile){
        super(name, imageFile);
    }
}
