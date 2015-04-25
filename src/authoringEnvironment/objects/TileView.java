package authoringEnvironment.objects;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TileView extends VBox{
    private Rectangle tile;
    private Color tileColor;
    private TagGroup tagsRecord;
    private static final int TILE_SIZE = 100;
    private static final int PADDING = 5;
    
    public TileView (Color color) {
        super(PADDING);
        tileColor = color;
        tile = new Rectangle(TILE_SIZE, TILE_SIZE, tileColor);
        
        tagsRecord = new TagGroup();
        
        this.getChildren().addAll(tile, tagsRecord);
        this.setAlignment(Pos.CENTER);
    }
    
    public TagGroup getTagsDisplay(){
        return tagsRecord;
    }
    
    public void addTagToObject(Tag tag){
        tagsRecord.addTag(tag);
    }
}
