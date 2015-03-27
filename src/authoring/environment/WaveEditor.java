package authoring.environment;

import java.util.ArrayList;

import authoring.environment.objects.Unit;
import javafx.scene.layout.GridPane;

public class WaveEditor extends MainEditor{
    public ArrayList<Unit> getWaves(){
        return new ArrayList<>();
    }

    @Override
    protected GridPane configureUI() {
        return null;
    }
}
