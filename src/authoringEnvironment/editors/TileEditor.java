package authoringEnvironment.editors;

import imageselectorTEMP.ImageSelector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import authoringEnvironment.Controller;
import authoringEnvironment.InstanceManager;
import authoringEnvironment.ProjectReader;
import authoringEnvironment.objects.FlowView;
import authoringEnvironment.objects.Tile;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


/**
 * This class extends SpriteEditor and allows the user to create a tile object. Because of slow
 * runtimes from large image sizes, users are currently restricted to only being able to utilize
 * rectanglular tiles with customized colors. If applicable, the tiles can be given tags that can
 * later be utilized in the MapEditor to set specific terrain tags, and the tags can later be
 * matched by the user when placing towers.
 * 
 * @author Callie Mao
 *
 */
public class TileEditor extends SpriteEditor {

    private static final String TILE_PART_NAME = "Tile";

    private List<Tile> myTiles;

    public TileEditor (Controller c, String name) {
        super(c, name);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void promptSpriteCreation () {
        myTiles = new ArrayList<Tile>();
        StackPane promptDisplay = new StackPane();
        Rectangle promptBackground = new Rectangle(300, 400);
        promptBackground.setOpacity(0.8);

        VBox promptContent = new VBox(20);
        promptContent.setAlignment(Pos.CENTER);
        Text prompt = new Text("Creating a new "
                               + tabName.toLowerCase().substring(0, tabName.length() - 1)
                               + "...");
        prompt.setFill(Color.WHITE);
        TextField promptField = new TextField();
        promptField.setMaxWidth(225);
        promptField.setPromptText("Enter a name...");

        /*
         * ImageSelector imgSelector = new ImageSelector();
         * imgSelector.addExtensionFilter("png");
         * imgSelector.addExtensionFilter("jpg");
         * imgSelector.addExtensionFilter("gif");
         * imgSelector.setPreviewImageSize(225, 150);
         * 
         * HBox buttons = new HBox(10);
         * Button create = new Button("Create");
         * create.setOnAction((e) -> {
         * addSprite(promptField.getText(),
         * imgSelector.getSelectedImageFile(), currentRow);
         * hideEditScreen(promptDisplay);
         * });
         * 
         * Button cancel = new Button("Cancel");
         * cancel.setOnAction((e) -> {
         * hideEditScreen(promptDisplay);
         * });
         */

    }

    /**
     * Saves the tile's properties (color and tags) to XML
     * 
     * @return the tile's reference key that is saved into XML
     */
    public void saveToXML () {

        for (Tile tile : myTiles) {
            Map<String, Object> mapSettings = tile.saveToXML();
            String key = myController.addPartToGame(TILE_PART_NAME, mapSettings);
            tile.setKey(key);

            /*
             * List<String> partFileNames = new ArrayList<String>();
             * List<Color> colors = new ArrayList<Color>();
             * List<List<String>> tags = new ArrayList<List<String>>();
             * 
             * for (Tile tile: myTiles){
             * // partFileNames.add(tile.getName());
             * colors.add(tile.getColor());
             * tags.add(tile.getTags());
             * }
             * 
             * List<Object> data = new ArrayList<Object>();
             * // data.add(partFileNames);
             * data.add(colors);
             * data.add(tags);
             * myController.addPartToGame(TILE_PART_NAME, waveName,
             * ProjectReader.getParamsNoTypeOrName(WAVE), data);
             */

        }

    }
}
