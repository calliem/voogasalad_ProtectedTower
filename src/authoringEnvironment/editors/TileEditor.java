package authoringEnvironment.editors;

import javafx.scene.paint.Color;
import authoringEnvironment.Controller;
import authoringEnvironment.objects.ObjectView;
import authoringEnvironment.objects.TileView;
import authoringEnvironment.util.Scaler;


/**
 * This class extends SpriteEditor and allows the user to create a tile object. Because of slow
 * runtimes from large image sizes, users are currently restricted to only being able to utilize
 * rectanglular tiles with customized colors. If applicable, the tiles can be given tags that can
 * later be utilized in the MapEditor to set specific terrain tags, and the tags can later be
 * matched by the user when placing towers.
 * 
 * @author Kevin He
 *
 */
public class TileEditor extends SpriteEditor {
    private static final int TILE_SIZE = 100;

    public TileEditor (Controller c, String name) {
        super(c, name);
        prompt.setImageChooser(false);
        prompt.setColorPicker(true);
    }

    @Override
    protected void checkNeededParts () {

    }

    @Override
    protected ObjectView addPart () {
        return createTile(prompt.getEnteredName(), prompt.getColorChosen());
    }

    private ObjectView createTile (String name, Color color) {
        TileView tile = new TileView(myController, name, color);
        tile.initiateEditableState();
        tile.getTileBody().setOnMousePressed(e -> {
            if (editing) {
                showOverlay(tile);
            }
        });
        tile.getCloseButton().setOnAction(e -> {
            hideTileOverlay(tile);
        });
        updateOnExists(tile);

        tile.saveTile();
        return tile;
    }

    private void showOverlay (TileView tile) {
        Scaler.scaleOverlay(0.0, 1.0, tile.getOverlay());
        myContent.getChildren().add(tile.getOverlay());
    }

    private void hideTileOverlay (TileView tile) {
        Scaler.scaleOverlay(1.0, 0.0, tile.getOverlay())
                .setOnFinished(e -> myContent.getChildren().remove(tile.getOverlay()));
    }
}
