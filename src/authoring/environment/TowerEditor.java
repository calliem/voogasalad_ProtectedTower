package authoring.environment;

import java.util.ArrayList;
import javafx.scene.layout.GridPane;
import authoring.environment.objects.Tower;

public class TowerEditor extends PropertyEditor{
    public ArrayList<Tower> getTowers(){
        return new ArrayList<>();
    }

    @Override
    protected GridPane configureUI () {
        // TODO Auto-generated method stub
        return null;
    }

}



