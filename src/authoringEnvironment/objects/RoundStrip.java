package authoringEnvironment.objects;

import imageselector.util.ScaleImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import util.misc.SetHandler;
import authoringEnvironment.Controller;
import authoringEnvironment.InstanceManager;
import authoringEnvironment.NoImageFoundException;


/**
 * Extends FlowEditor
 * 
 * @author Megan Gutter
 * @author Johnny Kumpf
 *
 */
public class RoundStrip extends FlowStrip {
    private static final int MAP_SELECTOR_HEIGHT = 205;
    private static final int MAP_SELECTOR_WIDTH = 205;
    private static final int PADDING = 10;
    private static final int ROW_SIZE = 3;
    private static final String ROUND = "Round";
    private static final String WAVES_KEY = "Waves";
    private static final String TIMES_KEY = "Times";
    private static final String PATHS_KEY = "PathKeys"; 

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

    @Override
    protected void addComponentToRow (ScrollPane displayPane, HBox content, String name) {
        if (currentPaths.size() > 0) {
            System.out.println("add called with: " + currentPaths);
            FlowView flow = new RoundFlowView(100, myController, currentPaths);
            content.getChildren().add(flow);
            myComponents.add(flow);

            displayPane.setHvalue(2.0);
        }
    }

    private ScrollPane generateMapSelector () {
        ScrollPane scrollingMapSelector = new ScrollPane();
        scrollingMapSelector.setMaxHeight(MAP_SELECTOR_HEIGHT);
        scrollingMapSelector.setMaxWidth(MAP_SELECTOR_WIDTH);
        scrollingMapSelector.setHbarPolicy(ScrollBarPolicy.NEVER);

        mapsAndBackground = new StackPane();
        Rectangle background =
                new Rectangle(MAP_SELECTOR_HEIGHT, MAP_SELECTOR_WIDTH, Color.LIGHTYELLOW);
        mapsAndBackground.getChildren().add(background);

        try {
            rowContainer = createVBoxWithMapRows();
        }
        catch (NoImageFoundException e) {
            e.printStackTrace();
        }

        mapsAndBackground.getChildren().add(rowContainer);

        scrollingMapSelector.setContent(mapsAndBackground);
        return scrollingMapSelector;
    }

    private VBox createVBoxWithMapRows () throws NoImageFoundException {
        VBox rowContainer = new VBox(PADDING);

        ObservableList<String> mapKeys =
                myController.getKeysForPartType(InstanceManager.GAMEMAP_PARTNAME);
        System.out.println("maps: " + mapKeys);
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

    private ImageView generateMapImage (String mapKey) throws NoImageFoundException {
        double mapSideLength = (MAP_SELECTOR_WIDTH - PADDING - PADDING * (ROW_SIZE + 1)) / ROW_SIZE;
        Map<String, Object> mapData = myController.getPartCopy(mapKey);
        ImageView map;
        try {
            map = new ImageView(myController.getImageForKey(mapKey));
            ScaleImage.scale(map, mapSideLength, mapSideLength);
            map.setOnMouseClicked(e -> { // replace selection display with the map the user clicked
                replaceMapSelectorWithMap(mapData, map);
            });
            map.setOnMouseEntered(e -> {
                map.setOpacity(.5);
            });
            map.setOnMouseExited(e -> {
                map.setOpacity(1);
            });
            return map;
        }
        catch (NoImageFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw new NoImageFoundException("map image not found");
        }

    }

    private void replaceMapSelectorWithMap (Map<String, Object> mapData, ImageView map) {
        List<String> pathsInMapClicked = (List<String>) mapData.get(PATHS_KEY);
        // if the map has no paths
        if (pathsInMapClicked == null || pathsInMapClicked.size() == 0) {
            // TODO: display error
            System.out.println("NO PATS ~~~~~~~~~~~~~~~~~");
            // String message = "No paths exist map selected!  Please choose a different map.";
        }
        // make sure the paths on the new map match the paths of the old map, or it's the first map
        // picked
        else if (currentPaths.size() == 0 || SetHandler.setFromList(currentPaths)
                .equals(SetHandler.setFromList(pathsInMapClicked))) {
            currentPaths = pathsInMapClicked;
            System.out.println("currentpaths: " + currentPaths);
            mapsAndBackground.getChildren().remove(rowContainer);
            ScaleImage.scale(map, MAP_SELECTOR_WIDTH, MAP_SELECTOR_HEIGHT);
            mapsAndBackground.getChildren().add(map);
            mapsAndBackground.getChildren()
                    .add(changeMapButton((List<String>) mapData.get(PATHS_KEY), map));

            for (FlowView component : myComponents) {
                ((RoundFlowView) component).changePathSelection(currentPaths);
            }
        }
        // tell the user the paths don't match
        else {
            // TODO: display error, different paths
            System.out.println("Can't change to that map, different paths");
        }
    }

    private Button changeMapButton (List<String> currentPaths, ImageView map) {
        Button changeMap = new Button("Change Map");
        changeMap.setOnAction(e -> regenerateMapDisplay(currentPaths, changeMap, map));
        return changeMap;
    }

    private void regenerateMapDisplay (List<String> currentPaths,
                                       Button changeMap,
                                       ImageView map) {
        try {
            mapsAndBackground.getChildren().remove(changeMap);
            mapsAndBackground.getChildren().remove(map);
            mapsAndBackground.getChildren().add(createVBoxWithMapRows());
        }
        catch (NoImageFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void saveData (String componentName) {
        List<String> waveKeys = new ArrayList<String>();
        List<String> pathKeys = new ArrayList<String>();
        List<Double> delays = new ArrayList<Double>();

        for (FlowView unit : myComponents) {
            waveKeys.add(unit.getWaveKey());
            pathKeys.add(unit.getPathKey());
            delays.add(unit.getDelay());
        }

        List<Double> times = getTimesFromZero(waveKeys, delays);

        List<Object> data = new ArrayList<Object>();
        data.add(waveKeys);
        data.add(pathKeys);
        data.add(times);
        List<String> params = new ArrayList<String>();
        params.add(WAVES_KEY);
        params.add(PATHS_KEY);
        params.add(TIMES_KEY);

        saveToGame(ROUND, componentName, params, data);
    }
}
