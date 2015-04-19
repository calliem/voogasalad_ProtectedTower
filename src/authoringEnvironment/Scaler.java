package authoringEnvironment;

import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;


public class Scaler {

    public static ScaleTransition scaleOverlay (double from, double to,
                                                   Node overlay) {
        ScaleTransition scale = new ScaleTransition(Duration.millis(200),
                                                    overlay);
        scale.setFromX(from);
        scale.setFromY(from);
        scale.setToX(to);
        scale.setToY(to);
        scale.setCycleCount(1);
        scale.play();

        return scale;
    }
}
