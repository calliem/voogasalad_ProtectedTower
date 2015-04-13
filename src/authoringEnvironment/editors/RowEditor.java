package authoringEnvironment.editors;

import java.awt.ScrollPane;
import java.util.List;

import authoringEnvironment.Controller;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class RowEditor extends Editor {

    public RowEditor(Controller c, String name) {
        super(c, name);
    }

    @Override
    public Group configureUI() {
        ScrollPane foundation = new ScrollPane();
        Rectangle background = new Rectangle();
        return new Group();
    }



}
