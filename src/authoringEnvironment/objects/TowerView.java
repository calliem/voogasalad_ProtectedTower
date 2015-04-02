package authoringEnvironment.objects;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import authoring.environment.setting.IntegerSetting;
import authoring.environment.setting.Setting;
import authoringEnvironment.MainEnvironment;

public class TowerView extends SpriteView {

	private VBox editableContent;
	private StackPane overlayContent;
	private Button overlayCloseButton;
	private BooleanProperty exists;

	private String name;
	private List<Setting> parameterFields;

	private static final double CONTENT_WIDTH = MainEnvironment.getEnvironmentWidth();
	private static final double CONTENT_HEIGHT = 0.89 * MainEnvironment
			.getEnvironmentHeight();

	public TowerView(String name) {
		this.name = name;
		parameterFields = new ArrayList<>();
		exists = new SimpleBooleanProperty(true);

		Rectangle towerBackground = new Rectangle(100, 100, Color.WHITE);
		Text towerName = new Text(name);
		getChildren().addAll(towerBackground, towerName);

		setupEditableContent();
		setupOverlayContent();
		setupTooltipText(getTowerInfo());
	}

	public BooleanProperty isExisting() {
		return exists;
	}

	public List<String[]> getTowerInfo() {
		List<String[]> info = new ArrayList<>();
		for (Setting setting : parameterFields) {
			info.add(new String[] { setting.getParameterName(),
					setting.getParameterValue() });
		}
		return info;
	}

	public void setupTooltipText(List<String[]> info) {
		String tooltipText = String.format("%s: %s\n", "Name", name);
		for (String[] parameter : info) {
			tooltipText += String.format("%s: %s\n", parameter[0], parameter[1]);
		}

		Tooltip tooltip = new Tooltip(tooltipText);
		tooltip.setTextAlignment(TextAlignment.LEFT);
		Tooltip.install(this, tooltip);
	}

	public void initiateEditableState() {
		ImageView close = new ImageView(new Image("images/close.png"));
		close.setTranslateX(10);
		close.setTranslateY(-10);
		close.setFitWidth(20);
		close.setPreserveRatio(true);
		close.setOnMousePressed((e) -> {
			exists.setValue(false);
		});
		this.getChildren().add(close);
		StackPane.setAlignment(close, Pos.TOP_RIGHT);
	}

	public void exitEditableState() {
		// removes the 'x' button.
		this.getChildren().remove(this.getChildren().size() - 1);
	}

	private void setupEditableContent() {
		editableContent = new VBox(10);
		editableContent.setAlignment(Pos.TOP_CENTER);
		editableContent.setTranslateY(10);

		Text title = new Text("Tower");
		title.setFont(new Font(30));
		title.setFill(Color.WHITE);

		Setting test = new IntegerSetting("Health");
		parameterFields.add(test);

		overlayCloseButton = new Button("Close");

		editableContent.getChildren().addAll(title, test, overlayCloseButton);
	}

	private void setupOverlayContent() {
		overlayContent = new StackPane();
		Rectangle overlayBackground = new Rectangle(CONTENT_WIDTH, CONTENT_HEIGHT);
		overlayBackground.setOpacity(0.8);
		overlayContent.getChildren().addAll(overlayBackground, editableContent);
	}

	public StackPane getEditorOverlay() {
		return overlayContent;
	}

	public Button getCloseButton() {
		return overlayCloseButton;
	}
}
