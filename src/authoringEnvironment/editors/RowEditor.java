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
        // TODO Auto-generated constructor stub
    }

    @Override
    public Group configureUI() {
        ScrollPane foundation = new ScrollPane();
        Rectangle background = new Rectangle();

        // TODO Auto-generated method stub
        return new Group();
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Node> getObjects() {
        // TODO Auto-generated method stub
        return null;
    }

}
