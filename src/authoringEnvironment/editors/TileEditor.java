package authoringEnvironment.editors;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import authoringEnvironment.Controller;
import authoringEnvironment.objects.Tag;
import authoringEnvironment.objects.TagGroup;
import authoringEnvironment.objects.TileView;


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
public class TileEditor extends Editor {
    private Group myRoot;
    private static final Color BACKGROUND_COLOR = Color.GRAY;
    private static final int PADDING = 10;

    public TileEditor (Controller c, String name, String nameWithoutEditor) {
        super(c, name, nameWithoutEditor);
    }

    @Override
    protected Group configureUI () {
        // TODO Auto-generated method stub
        myRoot = new Group();
        HBox test = new HBox(PADDING);
        test.setAlignment(Pos.CENTER);
        Rectangle background = new Rectangle(CONTENT_WIDTH, CONTENT_HEIGHT, BACKGROUND_COLOR);

        TileView tile = new TileView(Color.BLUE);

        Tag tag = new Tag("POISON");
        Tag tag2 = new Tag("GROUND");
        Tag tag3 = new Tag("AIR");

        TagGroup group = new TagGroup();
        group.addTag(tag2);
        group.addTag(tag3);

        test.getChildren().addAll(tile, tag, group);

        myRoot.getChildren().addAll(background, test);
        return myRoot;
    }
}
