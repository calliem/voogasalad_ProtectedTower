package authoringEnvironment.objects;

import authoringEnvironment.util.Scaler;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DeleteButton extends ImageView{
    public DeleteButton(int size){
        super(new Image("images/close.png"));
        this.setTranslateX(size/2);
        this.setTranslateY(-size/2);
        this.setFitWidth(size);
        this.setPreserveRatio(true);
    }
    
    public ScaleTransition getDeleteAnimation(Node node){
        return Scaler.scaleOverlay(1.0, 0.0, node);
    }
}
