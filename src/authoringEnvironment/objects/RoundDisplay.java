package authoringEnvironment.objects;

import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RoundDisplay extends HBox{
    
    private VBox allRounds;
    private String roundKey;
    private List<RoundDisplay> myRounds;
    
    public RoundDisplay(String roundName, String key, VBox roundOrder, List<RoundDisplay> rounds, int padding){
        super(padding);
        allRounds = roundOrder;
        roundKey = key;
        myRounds = rounds;
        this.getChildren().add(new Text(roundName));
        this.getChildren().add(removeRoundButton(this));
    }
    
    private Button removeRoundButton(HBox round){
        Button b = new Button ("Remove");
        b.setOnAction(e -> removeRound(round));
        return b;
    }
    
    private void removeRound(HBox roundToRemove){
        allRounds.getChildren().remove(roundToRemove);
        myRounds.remove(roundToRemove);
    }
    
    public String getRoundKey(){
        return roundKey;
    }

}
