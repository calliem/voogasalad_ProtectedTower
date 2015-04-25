package authoringEnvironment;

import java.util.ResourceBundle;
import authoringEnvironment.map.MapWorkspace;
import authoringEnvironment.objects.GameObject;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;

public class RoundSidebar extends Sidebar{

    public RoundSidebar (ResourceBundle resources,
                         ObservableList<GameObject> dependency,
                         MapWorkspace mapWorkspace) {
        super(resources, dependency, mapWorkspace);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void setContent (GridPane container) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void createMapSettings () {
        // TODO Auto-generated method stub
        
    }

}
