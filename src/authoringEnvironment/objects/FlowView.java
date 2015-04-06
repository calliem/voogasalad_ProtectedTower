package authoringEnvironment.objects;

import imageselector.util.ScaleImage;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class FlowView extends HBox {
	private String partFileName;
	private Double delay;
	private int myWidth;
	private int myHeight;
	
	public FlowView(int width, int height) {
		super(10);
		myWidth = width;
		myHeight = height;
		TextField partSelector = new TextField();
		partSelector.setMaxHeight(myHeight);
		ImageView arrowImage = new ImageView(new Image("images/arrow_icon.png"));
		ScaleImage.scaleByWidth(arrowImage, 120);
		TextField timeTextField = new TextField();
		timeTextField.setMaxWidth(50);
		
		this.getChildren().add(partSelector);
		VBox arrow = new VBox(10);
		HBox timeInput = new HBox(10);
		timeInput.getChildren().add(timeTextField);
		timeInput.getChildren().add(new Text("s"));
		timeInput.setAlignment(Pos.CENTER);
		arrow.getChildren().add(timeInput);
		arrow.getChildren().add(arrowImage);
		this.getChildren().add(arrow);
		this.setPrefHeight(myHeight);
	}
	
	public String getFileName() {
		return partFileName;
	}
	
}
