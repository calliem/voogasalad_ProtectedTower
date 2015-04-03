package authoringEnvironment;

import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

/**
 * Right sidebar containing display properties that allows a user to interactively set grid size, 
 * tile size, specific tiles / tile colors, paths, and other visual elements of the map. 
 * 
 * @author Callie
 *
 */

public class Sidebar extends VBox {

	private ResourceBundle myResources;
	
	public Sidebar(ResourceBundle resources) {
		myResources = resources;
		setDimensionRestrictions();
		createTitleText("sidebar test");
	}

	private void setDimensionRestrictions() {
		setPadding(new Insets(10, 10, 10, 10));
		setSpacing(3);
		setMaxWidth(Double.MAX_VALUE);
	}

	private void createTitleText(String s) {
		Text title = new Text(s);
		title.setFont(new Font(13));
		title.setUnderline(true);
		getChildren().add(title);
	}

	
	private void createUserCommandsPane() {
		createTitleText("hi");//myResources.getString("UserDefinedCommandsHeader"));
		//getChildren().add(createListView(myCommandItems, 130));
	}

	private ListView<String> createListView(ObservableList<String> items, int height) {
		ListView<String> list = new ListView<String>();
		list.setItems(items);
		list.setMaxWidth(Double.MAX_VALUE);
		list.setPrefHeight(130);
		return list;
	}
}



//set grid size
//slider to set tile size 

// 