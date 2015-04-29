package authoringEnvironment.editors;

import java.awt.ScrollPane;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import authoringEnvironment.Controller;


public class RowEditor extends Editor {

    public RowEditor (Controller c, String name, String nameWithoutEditor) {
        super(c, name, nameWithoutEditor);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Group configureUI () {
        ScrollPane foundation = new ScrollPane();
        Rectangle background = new Rectangle();

        // TODO Auto-generated method stub
        return new Group();
    }

}
