package authoring.environment;

import java.util.ArrayList;
import javafx.scene.Group;
import authoring.environment.objects.Unit;

public class WaveEditor extends MainEditor{
    public ArrayList<Unit> getWaves(){
        return new ArrayList<>();
    }

    @Override
    protected Group configureUI() {
        return null;
    }
}
