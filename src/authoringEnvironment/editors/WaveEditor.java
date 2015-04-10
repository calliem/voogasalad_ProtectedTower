package authoringEnvironment.editors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Dimension2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import authoringEnvironment.GameManager;
import authoringEnvironment.ProjectReader;
import authoringEnvironment.objects.FlowView;
import authoringEnvironment.objects.UnitView;

/**
 * Creates the Wave Editor that allows the user to create and edit waves made
 * out of units or other waves.
 * 
 * @author Megan Gutter
 *
 */

public class WaveEditor extends MainEditor {
	private Dimension2D myDimensions;
	private Group myRoot;
	private Map<String, ArrayList<FlowView>> myWaves;
	private final String WAVE = "Wave";

	public WaveEditor(Dimension2D dim, Stage s) {
		super(dim, s);
		myWaves = new HashMap<String, ArrayList<FlowView>>();
		myDimensions = dim;
	}

	@Override
	public Node configureUI() {
		myRoot = new Group();
		StackPane editor = new StackPane();
		HBox newWavePanel = new HBox(10);
		VBox contents = new VBox(10);

		ScrollPane contentScrollPane = new ScrollPane();
		contentScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		contentScrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		contentScrollPane.setMaxHeight(myDimensions.getHeight());
		contentScrollPane.setMaxWidth(myDimensions.getWidth());

		Button makeNewWave = new Button("Create New Wave");
		makeNewWave.setOnAction(e -> {
			promptNewWaveName(editor, contents);
			// makeNewWave(contents);
			});

		newWavePanel.getChildren().add(makeNewWave);
		contents.getChildren().add(newWavePanel);
		contentScrollPane.setContent(contents);

		editor.getChildren().add(contentScrollPane);
		myRoot.getChildren().add(editor);
		return myRoot;
	}

	private void promptNewWaveName(StackPane editor, VBox contents) {
		StackPane promptDisplay = new StackPane();
		Rectangle promptBackground = new Rectangle(300, 400);
		promptBackground.setOpacity(0.8);

		VBox promptContent = new VBox(20);
		promptContent.setAlignment(Pos.CENTER);
		Text prompt = new Text("Creating a new wave...");
		prompt.setFill(Color.WHITE);
		TextField promptField = new TextField();
		promptField.setMaxWidth(225);
		promptField.setPromptText("Enter a name...");

		HBox buttons = new HBox(10);
		Button create = new Button("Create");
		create.setOnAction((e) -> {
			makeNewWave(contents, promptField.getText());
			myWaves.put(promptField.getText(), new ArrayList<FlowView>());
			editor.getChildren().remove(promptDisplay);
		});

		Button cancel = new Button("Cancel");
		cancel.setOnAction((e) -> {
			editor.getChildren().remove(promptDisplay);
		});

		buttons.setAlignment(Pos.CENTER);
		buttons.getChildren().addAll(create, cancel);
		promptContent.getChildren().addAll(prompt, promptField, buttons);

		promptDisplay.getChildren().addAll(promptBackground, promptContent);

		editor.getChildren().add(promptDisplay);
	}

	private void makeNewWave(VBox contents, String waveName) {
		ScrollPane newWave = new ScrollPane();
		newWave.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		newWave.setVbarPolicy(ScrollBarPolicy.NEVER);
		newWave.setMaxWidth(myDimensions.getWidth());

		HBox waveContent = new HBox(10);

		Button addUnit = new Button("Add Unit");
		addUnit.setOnAction(e -> {
			addUnitToWave(waveContent, waveName);
		});

		Button save = new Button("Save");
		save.setOnAction(e -> {
			List<String> partFileNames = new ArrayList<String>();
			List<Double> delays = new ArrayList<Double>();
			List<Double> times = new ArrayList<Double>();
			times.add(0.0);

			for (FlowView unit : myWaves.get(waveName)) {
				partFileNames.addAll(unit.getFileNames());
				delays.addAll(unit.getDelays());
			}

			for (Double d : delays) {
				Double all = 0.0;
				for (Double t : times)
					all += t;
				times.add(all + d);
			}

			List<Object> data = new ArrayList<Object>();
			data.add(partFileNames);
			data.add(times);

			GameManager.addPartToGame(WAVE, waveName,
					ProjectReader.getParamsNoTypeOrName(WAVE), data);
		});

		VBox buttons = new VBox(10);
		buttons.getChildren().add(addUnit);
		buttons.getChildren().add(save);

		waveContent.getChildren().add(buttons);
		newWave.setContent(waveContent);

		contents.getChildren().add(newWave);
	}

	private void addUnitToWave(HBox wave, String waveName) {
		FlowView unit = new FlowView(100, 100);
		wave.getChildren().add(unit);
		myWaves.get(waveName).add(unit);
	}

	public ArrayList<UnitView> getWaves() {
		return new ArrayList<>();
	}

	@Override
	protected void createMap() {
	}

	@Override
	protected void update() {
		// TODO Auto-generated method stub

	}
}
