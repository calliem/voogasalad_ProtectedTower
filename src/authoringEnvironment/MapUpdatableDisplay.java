package authoringEnvironment;

import java.util.List;
import authoringEnvironment.map.MapWorkspace;
import authoringEnvironment.objects.GameObject;

public class MapUpdatableDisplay extends UpdatableDisplay {
    
    private MapWorkspace myMapWorkspace;
    
    public MapUpdatableDisplay (List<GameObject> list, int rowSize, MapWorkspace mapWorkspace) {
        super(list, rowSize);
        myMapWorkspace = mapWorkspace;
    }


    @Override
    protected void objectClicked (GameObject object) {
        myMapWorkspace.updateWithNewMap(object);
    }

}
