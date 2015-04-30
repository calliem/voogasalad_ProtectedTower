package authoringEnvironment.objects;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import authoringEnvironment.Controller;
import authoringEnvironment.NoImageFoundException;
import authoringEnvironment.map.MapWorkspace;


public class LevelUpdatableDisplay extends UpdatableDisplay {

    private MapWorkspace myMapWorkspace;
    private Controller myController;
    private LevelSidebar sidebar;

    public LevelUpdatableDisplay (ObservableList<GameObject> observableList, int rowSize, double thumbnailSizeMultiplier, MapWorkspace mapWorkspace) {
        super(observableList, rowSize, thumbnailSizeMultiplier);
       
        myMapWorkspace = mapWorkspace;
        // TODO Auto-generated constructor stub
    }
    
    public LevelUpdatableDisplay (LevelSidebar l, Controller c,
                                 String partType,
                                 int rowSize,
                                 double thumbnailSizeMultiplier, MapWorkspace mapWorkspace) {
        super(c, partType, rowSize, thumbnailSizeMultiplier);
        sidebar = l;
        myMapWorkspace = mapWorkspace;
        myController = c;
    }

    @Override
    protected void objectClicked (GameObject object, StackPane objectView) {
        super.objectClicked(object, objectView);        // create a new map
        //myMapWorkspace.updateWithNewMap(object);
       // myMapWorkspace.updateWorkspaceWithImg(object);
      //  myMapWorkspace.getChildren().clear();
        try {
            sidebar.setKey(object.getKey());
            myMapWorkspace.getChildren().add(new ImageView(myController.getImageForKey(object.getKey())));
        }
        catch (NoImageFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
