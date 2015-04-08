package authoringEnvironment.editors;

import java.util.ArrayList;

import javafx.geometry.Dimension2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import authoringEnvironment.objects.FlowView;
import authoringEnvironment.objects.UnitView;

/**
 * Creates the Wave Editor that allows the user to create
 * and edit waves made out of units or other waves.
 *  
 * @author Megan Gutter
 *
 */

public class WaveEditor extends MainEditor {
	private Group myRoot;
	private ArrayList<String> myWaves;

	public WaveEditor(Dimension2D dim, Stage s) {
		super(dim, s);
	}
	
	@Override
	public Node configureUI() {
		myRoot = new Group();		
		HBox newWavePanel = new HBox(10);
		VBox contents = new VBox(10);
		
		Button makeNewWave = new Button("Create New Wave");
		makeNewWave.setOnAction(e -> {
			makeNewWave(contents);
		});
		
		newWavePanel.getChildren().add(makeNewWave);
		contents.getChildren().add(newWavePanel);
		
		myRoot.getChildren().add(contents);
		return myRoot;
	}
	
	private void makeNewWave(VBox contents) {
		ScrollPane newWave = new ScrollPane();
		newWave.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		newWave.setVbarPolicy(ScrollBarPolicy.NEVER);
		
		HBox waveContent = new HBox(10);

		Button addUnit = new Button("Add Unit");
		addUnit.setOnAction(e -> {
			addUnitToWave(waveContent);
		});
		
		Button save = new Button("Save");
		save.setOnAction(e -> {
			
		});
		
		VBox buttons = new VBox(10);
		buttons.getChildren().add(addUnit);
		buttons.getChildren().add(save);
		
		waveContent.getChildren().add(buttons);
		newWave.setContent(waveContent);
		
		contents.getChildren().add(newWave);
		//return newWave;
	}
	
	private void addUnitToWave(HBox wave) {
		wave.getChildren().add(new FlowView(100, 100));		
	}

	public ArrayList<UnitView> getWaves() {
		return new ArrayList<>();
	}

	@Override
	protected void createMap() {
	}
}
