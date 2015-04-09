package authoringEnvironment.editors;

import java.util.ArrayList;

import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import authoringEnvironment.MainEnvironment;
import authoringEnvironment.objects.FlowView;
import authoringEnvironment.objects.UnitView;

public class WaveEditor extends MainEditor {
	private Group myRoot;
	private static final double CONTENT_WIDTH = MainEnvironment.getEnvironmentWidth();
    private static final double CONTENT_HEIGHT = 0.89 * MainEnvironment.getEnvironmentHeight();
    
	public WaveEditor(Dimension2D dim, Stage s) {
		super(dim, s);
	}
	
	@Override
	protected void configureUI() {	
		Rectangle background = new Rectangle(CONTENT_WIDTH, CONTENT_HEIGHT, Color.GRAY);
		
		HBox wave1 = makeNewWave();
		getChildren().add(wave1);
	}
	
	private HBox makeNewWave() {
		HBox newWave = new HBox(10);
		Button addUnit = new Button("Add Unit");
		addUnit.setOnAction(e -> {
			addUnitToWave(newWave);
		});
		return newWave;
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

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
