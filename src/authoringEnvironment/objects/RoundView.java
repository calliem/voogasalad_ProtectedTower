package authoringEnvironment.objects;

import java.util.List;
import authoringEnvironment.Controller;


public class RoundView extends SpriteView {

    public RoundView (Controller c, String name, String imageFile) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
        super(c, name, imageFile);
        // TODO Auto-generated constructor stub
    }

    private List<WaveView> myWaves;

}
