package authoringEnvironment.editors;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import authoringEnvironment.Controller;
import authoringEnvironment.objects.Tag;
import authoringEnvironment.objects.TagGroup;
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
public class TileEditor extends Editor {
    private StackPane myContent;
    private Group visuals;
    private TagDisplay tags;
    private static final Color BACKGROUND_COLOR = Color.GRAY;
    private static final int PADDING = 10;

    private List<Node> tileList;
    private List<TagGroup> tagGroupsList;

    public TileEditor (Controller c, String name) {

        super(c, name);
    }

    @Override
    protected Group configureUI () {
        visuals = new Group();
        myContent = new StackPane();
        tileList = new ArrayList<>();
        tagGroupsList = new ArrayList<>();

        HBox test = new HBox(PADDING);
        test.setAlignment(Pos.CENTER);
        test.setTranslateY(PADDING);

        Rectangle background = new Rectangle(CONTENT_WIDTH, CONTENT_HEIGHT, BACKGROUND_COLOR);

        TileView tile = new TileView(Color.BLUE);
        tileList.add(tile);

        tile.getTagsDisplay().setOnMousePressed(e -> {
            Scaler.scaleOverlay(0.0, 1.0, tile.getTagsDisplay().getOverlay());
            myContent.getChildren().add(tile.getTagsDisplay().getOverlay());
        });

        tagGroupsList.add(tile.getTagsDisplay());

        Tag tag = new Tag("POISON");
        Tag tag2 = new Tag("GROUND");
        Tag tag3 = new Tag("AIR");
        Tag tag4 = new Tag("QIAN");
        Tag tag5 = new Tag("BOJIA");
        Tag tag6 = new Tag("CALLIE");

        TagGroup group = new TagGroup();
        group.addTags(tag2, tag3, tag4, tag5);
        group.setOnMousePressed(e -> {
            Scaler.scaleOverlay(0.0, 1.0, group.getOverlay());
            myContent.getChildren().add(group.getOverlay());
        });
        group.addTag(tag6);

        test.getChildren().addAll(tile, tag, group);

        tags = new TagDisplay(myController);
        ObservableList<Tag> list = tags.getTagsList();
        list.addListener(new ListChangeListener<Tag>() {
            @Override
            public void onChanged (ListChangeListener.Change change) {
                tags.setupDraggableTags(visuals, tagGroupsList, tileList);
            }
        });

        StackPane.setAlignment(tags, Pos.TOP_LEFT);
        myContent.getChildren().addAll(background, test);

        visuals.getChildren().addAll(myContent, tags);
        return visuals;

    }

    @Override
    public void update () {
        // TODO Auto-generated method stub

    }
}
