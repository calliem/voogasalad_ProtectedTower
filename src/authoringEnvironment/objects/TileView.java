package authoringEnvironment.objects;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TileView extends HBox{
    private Rectangle tile;
    private Color tileColor;
    private static final int TILE_SIZE = 100;
    
    public TileView (Color color) {
        super();
        tileColor = color;
        tile = new Rectangle(TILE_SIZE, TILE_SIZE, tileColor);
        
        this.getChildren().add(tile);
    }
}
