package authoringEnvironment.map;

import javafx.geometry.Dimension2D;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import authoringEnvironment.AuthoringEnvironment;
import authoringEnvironment.objects.GameObject;
import authoringEnvironment.objects.TileMap;


public class MapWorkspace extends StackPane {

    private TileMap myActiveMap;
    private static final int DEFAULT_MAP_ROWS =
            (int) (AuthoringEnvironment.getEnvironmentWidth() * .8 / 50);// getWidth()*.8; //TODO:
                                                                         // get the .8 from above
                                                                         // class. also getWidth()
                                                                         // is not static and so it
                                                                         // cannot be used. maybe
                                                                         // make it static or just
                                                                         // mathis this a final
                                                                         // variale?
    private static final int DEFAULT_MAP_COLS =
            (int) (AuthoringEnvironment.getEnvironmentHeight() * .9 / 25); // getHeight();
    private static final int DEFAULT_TILE_SIZE = 30; // based on height since monitor height < width
                                                     // and that is usually the limiting factor

    public static final double MAP_WIDTH_MULTIPLIER = .75;
    public static final double MAP_HEIGHT_PERCENT = 100;

    // TODO: fix all of these constants so there are no more replicates

    public MapWorkspace () { // Dimension2D pass this in?
        super();
        Rectangle background =
                new Rectangle(AuthoringEnvironment.getEnvironmentWidth() * MAP_WIDTH_MULTIPLIER,
                              0.9 * AuthoringEnvironment.getEnvironmentHeight(),
                              Color.web("2A2A29"));
        getChildren().add(background);
        createDefaultMap();

    }

    public TileMap createDefaultMap () {
        TileMap defaultMap = new TileMap(DEFAULT_MAP_ROWS, DEFAULT_MAP_COLS, DEFAULT_TILE_SIZE);
        updateWithNewMap(defaultMap);
        return defaultMap;
    }

    public TileMap getActiveMap () {
        return myActiveMap;
    }

    public void removeMap () {
        if (myActiveMap == null)
            return;
        if (getChildren().contains(myActiveMap)){
            System.out.println("remove active map");
            getChildren().remove(myActiveMap);
            myActiveMap = null;
        }
    }

    public void updateWithNewMap (TileMap object) {
        if (myActiveMap != null) {
            getChildren().remove(myActiveMap);
        }
        myActiveMap = object;
        getChildren().add(myActiveMap);
    }

}
