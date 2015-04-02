package authoring.environment;

import java.util.ArrayList;
import javafx.scene.Group;
import authoring.environment.objects.UnitView;

public class WaveEditor extends MainEditor{
    public ArrayList<UnitView> getWaves(){
        return new ArrayList<>();
    }

    @Override
    protected Group configureUI() {
        return null;
    }
}
