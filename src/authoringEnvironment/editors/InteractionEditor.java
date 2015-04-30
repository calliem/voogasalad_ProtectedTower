package authoringEnvironment.editors;

import java.util.List;
import javafx.scene.Group;
import javafx.scene.Node;
import authoringEnvironment.Controller;


public class InteractionEditor extends Editor {

    private List<Node> myItems;

    public InteractionEditor (Controller c, String name) {
        super(c, name);
    }

    @Override
    protected Group configureUI () {
        // TODO Auto-generated method stub
        return new Group();
    }

    @Override
    public void update () {
        // TODO Auto-generated method stub
        
    }

}
