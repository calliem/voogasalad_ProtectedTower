package authoringEnvironment.objects;

import imageselector.util.ScaleImage;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Creates the visual selector for adding a unit/wave and the time delay. 
 * Stores the information for the WaveEditor
 * 
 * @author Megan Gutter
 *
 */

public class FlowView extends HBox {
	// private String partFileName;
	// private Double delay;
	private TextField partSelector;
	private TextField delayTextField;
	private int myWidth;
	private int myHeight;

	public FlowView(int width, int height) {
		super(10);
		myWidth = width;
		myHeight = height;
		partSelector = new TextField();
		partSelector.setMaxHeight(myHeight);
		ImageView arrowImage = new ImageView(new Image("images/arrow_icon.png"));
		ScaleImage.scaleByWidth(arrowImage, 120);
		delayTextField = new TextField();
		delayTextField.setMaxWidth(50);

		this.getChildren().add(partSelector);
		VBox arrow = new VBox(10);
		HBox timeInput = new HBox(10);
		timeInput.getChildren().add(delayTextField);
		timeInput.getChildren().add(new Text("s"));
		timeInput.setAlignment(Pos.CENTER);
		arrow.getChildren().add(timeInput);
		arrow.getChildren().add(arrowImage);
		this.getChildren().add(arrow);
		this.setPrefHeight(myHeight);
	}

	public String getFileName() {
		return partSelector.getText(); // TODO return error if not valid file
	}

	public Double getDelay() {
		try {
			return Double.parseDouble(delayTextField.getText());
		} catch (NumberFormatException e) {
			return 0.0; // TODO return error if not a double
		}
	}

}
