package authoringEnvironment;

import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import authoringEnvironment.map.MapWorkspace;
import authoringEnvironment.objects.GameObject;


/**
 * This class is the generic sidebar method that contains the resource file and general methods
 * shared by all sidebars. Sidebars throughout the authoring environment will be of consistent
 * proportional size and have consistency in how information (ie. titles and lists) are displayed.
 * 
 * @author Callie Mao
 *
 */

public abstract class Sidebar extends Accordion { // extend gridpane pls

    private ResourceBundle myResources;
    private ObservableList<GameObject> myMaps; // can't seem to use list with this
    private MapWorkspace myMapWorkspace; // TODO: or use more general StackPane?

    private static final double PADDING = AuthoringEnvironment.getEnvironmentWidth() / 128; // maybe
                                                                                            // set
                                                                                            // the
                                                                                            // spacing
                                                                                            // dynamically
                                                                                            // instead
    private static final double LISTVIEW_HEIGHT = AuthoringEnvironment.getEnvironmentHeight() / 6;
    private static final double TITLE_FONT_SIZE = AuthoringEnvironment.getEnvironmentWidth() / 85;

    public Sidebar (ResourceBundle resources,
                    ObservableList<GameObject> dependency,
                    MapWorkspace mapWorkspace) {

        myResources = resources;
        myMaps = FXCollections.observableList(dependency);
        myMapWorkspace = mapWorkspace;
        setDimensionRestrictions();
        // setSpacing(10);
        // createMapSettings();
    }

    protected MapWorkspace getMapWorkspace () {
        return myMapWorkspace;
    }

    protected ResourceBundle getResources () {
        return myResources;
    }

    protected ObservableList<GameObject> getMaps () {
        return myMaps;
    }

    protected abstract void createMapSettings ();

    protected VBox createTitleText (String s) {
        Text title = new Text(s);
        title.setFont(new Font(TITLE_FONT_SIZE));
        title.setUnderline(true);
        VBox context = new VBox();
        TitledPane organizer = new TitledPane(s, context);
        getPanes().add(organizer);
        return context;
    }

    protected ListView<Node> createListView (ObservableList<Node> items, int height) {
        ListView<Node> list = new ListView<Node>();
        list.setItems(items);
        list.setMaxWidth(Double.MAX_VALUE);
        list.setPrefHeight(LISTVIEW_HEIGHT);
        return list;
    }

    private void setDimensionRestrictions () {
        // setPadding(new Insets(PADDING));
        // setSpacing(3);
        // setMaxWidth(Double.MAX_VALUE);
    }

}
