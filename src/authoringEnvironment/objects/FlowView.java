package authoringEnvironment.objects;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import authoringEnvironment.GameManager;
import authoringEnvironment.ProjectReader;
import authoringEnvironment.setting.SpriteSetting;
import imageselectorTEMP.util.ScaleImage;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

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
	//private TextField partSelector;
	private SpriteSetting unitSelector;
	private SpriteSetting waveSelector;
	private TextField delayTextField;
	private FileChooser fileChooser;
	private int myWidth;
	private int myHeight;
	private final static String WAVE = "Wave";
	private final static String UNIT = "Unit";
	private List<String> partFileNames;
	private List<Double> delays;
	private List<String> params;

	public FlowView(int width, int height) {
		super(10);
		myWidth = width;
		myHeight = height;
		fileChooser = new FileChooser();
		partFileNames = new ArrayList<String>();
		delays = new ArrayList<Double>();
		params = ProjectReader.getParamsNoTypeOrName(WAVE);
		
		VBox partSelector = new VBox(10);
		//partSelector.setMaxHeight(myHeight);
//		unitSelector = new SpriteSetting(UNIT, UNIT);
//		waveSelector = new SpriteSetting(WAVE, WAVE);
//		partSelector.getChildren().add(unitSelector);
//		partSelector.getChildren().add(waveSelector);
		
		Button selectUnitButton = new Button("Select Unit");
		Button selectWaveButton = new Button("Select Wave");
		
		selectUnitButton.setOnAction(e -> {
			selectUnit();
		});
		
		selectWaveButton.setOnAction(e -> {
			selectWave();
		});
		
		partSelector.getChildren().add(selectUnitButton);
		partSelector.getChildren().add(selectWaveButton);
		
		ImageView arrowImage = new ImageView(new Image("images/arrow_icon.png"));
		ScaleImage.scaleByWidth(arrowImage, 120);
		delayTextField = new TextField();
		delayTextField.setMaxWidth(50);

		this.getChildren().add(partSelector);
		this.getChildren().add(new Rectangle());
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
	
	private void selectUnit() {
		SpriteSetting chooseUnit = new SpriteSetting(UNIT, UNIT);
		insertElement(chooseUnit);
		
		List<Double> unitDelay = new ArrayList<Double>();
		List<String> fileNames = new ArrayList<String>();
		try {
			unitDelay.add(Double.parseDouble(delayTextField.getText()));
			delays = unitDelay;
		} catch (NumberFormatException e) {
			
		}
	}
	
	private void selectWave() {
		File file = fileChooser.showOpenDialog(null);
		Text waveNameDisplay = new Text(file.getName());
		insertElement(waveNameDisplay);

		Map<String, Object> lists = GameManager.loadPartFromFileName(WAVE, file.getName());
		delays = (List<Double>) lists.get("Times");
		partFileNames = (List<String>) lists.get("Enemies");
	}

	private void insertElement(Node node) {
		this.getChildren().remove(1);
		this.getChildren().add(1, node);
	}
	

	public List<String> getFileNames() {
		return partFileNames;
	}

	public List<Double> getDelays() {
		return delays;
	}

}
