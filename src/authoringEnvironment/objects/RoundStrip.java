package authoringEnvironment.objects;

import imageselector.util.ScaleImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import util.misc.SetHandler;
import authoringEnvironment.Controller;
import authoringEnvironment.DataFormatException;
import authoringEnvironment.InstanceManager;
import authoringEnvironment.MissingInformationException;
import authoringEnvironment.ProjectReader;


/**
 * Extends FlowEditor
 * 
 * @author Megan Gutter
 * @author Johnny Kumpf
 *
 */
public class RoundStrip extends FlowStrip {
    private UpdatableDisplay mapDisplay;
    private static final int MAP_SELECTOR_HEIGHT = 205;
    private static final int MAP_SELECTOR_WIDTH = 205;
    private static final int PADDING = 10;
    private static final int ROW_SIZE = 3;
    private static final String ROUND= "Round";

    private VBox rowContainer;
    private StackPane mapsAndBackground;
    private List<String> currentPaths;

    public RoundStrip (String type, String componentName, Controller c) {
        super(type, componentName, c);
        currentPaths = new ArrayList<String>();
    }

    @Override
    protected void addAtLeftOfRow (HBox content) {
        content.getChildren().add(generateMapSelector());
    }

    private ScrollPane generateMapSelector () {
        ScrollPane scrollingMapSelector = new ScrollPane();
        scrollingMapSelector.setMaxHeight(MAP_SELECTOR_HEIGHT);
        scrollingMapSelector.setMaxWidth(MAP_SELECTOR_WIDTH);
        scrollingMapSelector.setHbarPolicy(ScrollBarPolicy.NEVER);


        StackPane mapsAndBackground = new StackPane();
        Rectangle background =
                new Rectangle(MAP_SELECTOR_HEIGHT, MAP_SELECTOR_WIDTH, Color.LIGHTYELLOW);
        mapsAndBackground.getChildren().add(background);

        rowContainer = createVBoxWithMapRows();

        mapsAndBackground.getChildren().add(rowContainer);

        scrollingMapSelector.setContent(mapsAndBackground);
        return scrollingMapSelector;
    }

    private VBox createVBoxWithMapRows () {
        VBox rowContainer = new VBox(PADDING);

        ObservableList<String> mapKeys = myController.getKeysForPartType("Map");
        HBox row = new HBox(PADDING);
        int mapsPlacedInRow = 0;
        for (String mapKey : mapKeys) {
            ImageView map = generateMapImage(mapKey);
            if (mapsPlacedInRow == ROW_SIZE) {
                rowContainer.getChildren().add(row);
                row = new HBox(PADDING);
                mapsPlacedInRow = 0;
            }
            row.getChildren().add(map);
            mapsPlacedInRow++;
        }
        rowContainer.getChildren().add(row);
        return rowContainer;
    }

    private ImageView generateMapImage (String mapKey) {
        double mapSideLength = (MAP_SELECTOR_WIDTH - PADDING * ROW_SIZE + 1) / ROW_SIZE;
        Map<String, Object> mapData = myController.getPartCopy(mapKey);
        ImageView map = new ImageView(new Image((String) mapData.get(InstanceManager.IMAGE_KEY)));
        ScaleImage.scale(map, mapSideLength, mapSideLength);
        map.setOnMouseClicked(e -> { // replace selection display with the map the user clicked
            replaceMapSelectorWithMap(mapData, map);
        }); // TODO: what key?
        map.setOnMouseEntered(e -> {
            map.setOpacity(.5);
        });
        map.setOnMouseExited(e -> {
            map.setOpacity(1);
        });
        return map;
    }

    private void replaceMapSelectorWithMap (Map<String, Object> mapData, ImageView map) {
        List<String> pathsInMapClicked = (List<String>) mapData.get("paths");
        // if the map has no paths
        if (pathsInMapClicked.size() == 0) {
            // TODO: display error
            String message = "No paths exist map selected!  Please choose a different map.";
        }
        // make sure the paths on the new map match the paths of the old map, or it's the first map
        // picked
        else if (currentPaths.size() == 0 || SetHandler.setFromList(currentPaths)
                .equals(SetHandler.setFromList(pathsInMapClicked))) {
            currentPaths = pathsInMapClicked;
            mapsAndBackground.getChildren().remove(rowContainer);
            ScaleImage.scale(map, MAP_SELECTOR_WIDTH, MAP_SELECTOR_HEIGHT);
            mapsAndBackground.getChildren().add(map);
            mapsAndBackground.getChildren()
                    .add(changeMapButton((List<String>) mapData.get("paths")));
        }
        // tell the user the paths don't match
        else {
            // TODO: display error, different paths
            System.out.println("Can't change to that map, different paths");
        }
    }

    private Button changeMapButton (List<String> currentPaths) {
        Button changeMap = new Button("Change Map");
        changeMap.setOnAction(e -> warnThenRegenerateMapDisplay(currentPaths));
        return changeMap;
    }

    private void warnThenRegenerateMapDisplay (List<String> currentPaths) {
        promptUserAboutMapChanging(currentPaths);
        mapsAndBackground.getChildren().add(createVBoxWithMapRows());
    }

    private void promptUserAboutMapChanging (List<String> currentPaths) {
        // TODO: some sort of alert
        String message =
                "The paths that exist on the current map are: \n" + currentPaths.toString() +
                        "\nThe map you change to must contain the same paths.";
    }

    private void chooseMap (List<String> paths) {
        // replace the image selection with large display of the map

    }

    @Override
    protected void saveData (String componentName) {
        
        
        List<Object> data = new ArrayList<Object>();
        //data.addAll();
        try {
            if (myKey.equals(Controller.KEY_BEFORE_CREATION))
                myKey = myController.addPartToGame(ROUND, componentName,
                                                   ProjectReader.getParamsNoTypeOrName(ROUND), data);
            else
                myKey =
                        myController.addPartToGame(myKey, ROUND, componentName,
                                                   ProjectReader.getParamsNoTypeOrName(ROUND), data);
        }
        catch (MissingInformationException | DataFormatException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
