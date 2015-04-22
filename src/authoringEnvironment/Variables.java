package authoringEnvironment;

import javafx.scene.paint.Color;

public class Variables {

    public static final double THUMBNAIL_SIZE_MULTIPLIER = 0.07;

    public static final String partsFileDir = "/AllPartData";
    public static final String partTypeKey = "PartType";
    public static final String nameKey = "Name";
    public static final String partKeyKey = "PartKey";
    public static final String imageKey = "Image";
    public static final String savePathKey = "SavePath";
    
    
    //TODO: is there a way to get these from the resource files?
    
    public static final String PARAMETER_HP = "HP";
    public static final String PARAMETER_RANGE = "Range";
    public static final String PARAMETER_DAMAGE = "Damage";
    public static final String PARAMETER_SPEED = "Speed";
    
    public static final String PARTNAME_TOWER = "Tower";
    public final static String PARTNAME_ENEMY = "Enemies"; //Don't think this is done right
    public final static String PARAMETER_TIMES = "Times";
    public static final String PARAMETER_TILESIZE = "TileSize";
    public static final String PARAMETER_BACKGROUND = "Background";
    public static final String PARTNAME_MAP = "GameMap";

    public static final Color DEFAULT_TILE_COLOR = Color.WHITE;

}
