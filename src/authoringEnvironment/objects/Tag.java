package authoringEnvironment.objects;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;


public class Tag extends Group {
    private static final int FADE_DURATION = 300;
    private static final int MOVE_DURATION = 300;
    private String tagLabel;
    private DeleteButton closeButton;

    private static final int TAG_WIDTH = 75;
    private static final int TAG_HEIGHT = 20;
    private static final int TEXT_SIZE = 10;
    private static final int ARC_SIZE = 10;
    private static final int BUTTON_SIZE = 10;
    private static final Color TAG_COLOR = Color.DARKGRAY;

    private double xCoordinate;
    private double yCoordinate;

    private StackPane tagDisplay;

    public Tag (String tagName) {
        tagLabel = tagName;

        tagDisplay = new StackPane();
        closeButton = new DeleteButton(BUTTON_SIZE);
        closeButton.setTranslateX(TAG_WIDTH-BUTTON_SIZE/2);
        closeButton.setTranslateY(-BUTTON_SIZE/2);

        Rectangle tagBody = new Rectangle(TAG_WIDTH, TAG_HEIGHT, TAG_COLOR);
        tagBody.setArcWidth(ARC_SIZE);
        tagBody.setArcHeight(ARC_SIZE);

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
        TranslateTransition move = new TranslateTransition(Duration.millis(MOVE_DURATION), this);
        move.setFromX(0);
        move.setToX(TAG_WIDTH);

        FadeTransition fade = new FadeTransition(Duration.millis(FADE_DURATION), this);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);

        ParallelTransition delete = new ParallelTransition(move, fade);
        delete.play();
        return delete;

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

    public DeleteButton getButton () {
        return closeButton;
    }

    public StackPane getTagBody () {
        return tagDisplay;
    }

    public void setLocation (double x, double y) {
        xCoordinate = x;
        yCoordinate = y;
    }

    public double getX () {
        return xCoordinate;
    }

    public double getY () {
        return yCoordinate;
    }

    public String toString () {
        return tagLabel;
    }
}
