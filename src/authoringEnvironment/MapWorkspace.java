package authoringEnvironment;

import javafx.geometry.Dimension2D;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import authoringEnvironment.objects.TileMap;

public class MapWorkspace extends StackPane{
	
	private TileMap myActiveMap;
	private static final int DEFAULT_MAP_ROWS = (int) (MainEnvironment.getEnvironmentWidth()*.8/50);// getWidth()*.8; //TODO: get the .8 from above class. also getWidth() is not static and so it cannot be used. maybe make it static or just mathis this a final variale? 
    private static final int DEFAULT_MAP_COLS = (int) (MainEnvironment.getEnvironmentHeight()*.9/25); //getHeight();
    private static final int DEFAULT_TILE_SIZE = 30; //based on height since monitor height < width and that is usually the limiting factor
    
	public static final double MAP_WIDTH_MULTIPLIER = .75;
	public static final double MAP_HEIGHT_PERCENT = 100;
	//TODO: fix all of these constants so there are no more replicates
	
	public MapWorkspace(){ //Dimension2D pass this in?
		super();
		Rectangle background = new Rectangle(MainEnvironment.getEnvironmentWidth()*MAP_WIDTH_MULTIPLIER, 0.9 * MainEnvironment.getEnvironmentHeight(), Color.web("2A2A29"));
		getChildren().add(background);
		createDefaultMap();
		
	}
	
	public void createDefaultMap() {
        TileMap defaultMap = new TileMap(DEFAULT_MAP_ROWS, DEFAULT_MAP_COLS, DEFAULT_TILE_SIZE);
        updateWithNewMap(defaultMap);
        
    }
	
	public TileMap getActiveMap(){
		return myActiveMap;
	}
	
	public void removeMap(){
		getChildren().remove(myActiveMap);
		myActiveMap = null;
	}
	
	public void updateWithNewMap(TileMap newMap){
		if (myActiveMap != null){
			getChildren().remove(myActiveMap);
		}
		myActiveMap = newMap;
		getChildren().add(newMap);
	}

}
