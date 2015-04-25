package authoringEnvironment.objects;

import imageselectorTEMP.util.ScaleImage;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;


public class Tag extends Group {
    private String tagLabel;
    private ImageView closeButton;

    private static final int TAG_WIDTH = 75;
    private static final int TAG_HEIGHT = 20;
    private static final int TEXT_SIZE = 10;
    private static final int BUTTON_SIZE = 10;
    private static final Color TAG_COLOR = Color.LIGHTGRAY;

    public Tag (String tagName) {
        tagLabel = tagName;

        StackPane tagDisplay = new StackPane();
        closeButton = new ImageView(new Image("images/close.png"));
        ScaleImage.scale(closeButton, BUTTON_SIZE, BUTTON_SIZE);
        closeButton.setTranslateX(TAG_WIDTH);
        // closeButton.setVisible(false);

        Rectangle tagBody = new Rectangle(TAG_WIDTH, TAG_HEIGHT, TAG_COLOR);

        Text label = new Text(tagLabel);
        label.setFont(new Font(TEXT_SIZE));
        label.setFill(Color.WHITE);
        label.setWrappingWidth(TAG_WIDTH);
        label.setTextAlignment(TextAlignment.CENTER);

        tagDisplay.getChildren().addAll(tagBody, label);
        this.getChildren().addAll(tagDisplay, closeButton);

        updateTooltip();
    }

    public ParallelTransition playDeleteAnimation () {
        TranslateTransition move = new TranslateTransition(Duration.millis(300), this);
        move.setFromX(0);
        move.setToX(TAG_WIDTH);

        FadeTransition fade = new FadeTransition(Duration.millis(300), this);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);

        return new ParallelTransition(move, fade);
    }

    public void updateTooltip () {
        Tooltip tooltip = new Tooltip(tagLabel);
        tooltip.setTextAlignment(TextAlignment.LEFT);
        Tooltip.install(this, tooltip);
    }

    public String getLabel () {
        return tagLabel;
    }

    public void showButton () {
        closeButton.setVisible(true);
    }

    public void hideButton () {
        closeButton.setVisible(false);
    }

    public ImageView getButton () {
        return closeButton;
    }
}
