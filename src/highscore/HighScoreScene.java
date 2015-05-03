// This entire file is part of my masterpiece.
// Greg McKeon
package highscore;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


/**
 * Class representing the Scene containing a TableView displayed by the displayHighScores() method.
 * Dynamically gets the column names, sets their sort methods, and generates the correct Scene.
 * 
 * @author Greg McKeon
 *
 */
public class HighScoreScene {
    private Scene output;
    private VBox root;
    private List<HighScoreHolder> highScores;
    private Properties propFile;
    private String sortBy;

    public HighScoreScene (List<HighScoreHolder> data, String sortScoresBy) {
        root = new VBox();
        output = new Scene(root);
        propFile = HighScoreController.loadResources();
        highScores = data;
        sortBy = sortScoresBy;
        makeTable();
    }

    /**
     * Generate the TableView from the given columns. This method also sets the sort policy
     * for the table.
     */
    private void makeTable () {
        TableColumn<HighScoreHolder, String> sortColumn = null;
        TableView<HighScoreHolder> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        Set<String> columnNames = getColNames();
        TableColumn<HighScoreHolder, String> nameColumn =
                new TableColumn<>(propFile.getProperty("name"));
        nameColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue()
                .getUsername()));
        table.getColumns().add(nameColumn);
        for (String name : columnNames) {
            TableColumn<HighScoreHolder, String> currentColumn = makeColumn(name);
            currentColumn.setSortType(SortType.DESCENDING);
            currentColumn.setResizable(true);
            if (name.equals(sortBy)) {
                sortColumn = currentColumn;
            }
            table.getColumns().add(currentColumn);
        }
        ObservableList<HighScoreHolder> observableScores = FXCollections.observableList(highScores);
        table.setItems(observableScores);
        root.getChildren().add(table);
        VBox.setVgrow(table, Priority.ALWAYS);
        if (sortColumn != null) {
            table.getSortOrder().add(sortColumn);
        }
    }

    private TableColumn<HighScoreHolder, String> makeColumn (String name) {
        TableColumn<HighScoreHolder, String> column = new TableColumn<>(name);
        column.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue()
                .getValueAt(name)));
        column.setComparator( (obj1, obj2) -> {
            try {
                return new Double(obj1).compareTo(new Double(obj2));
            }
            catch (NumberFormatException e) {
                String notFound = propFile.getProperty("notFound");
                if (obj1.equals(notFound)) {
                    return -1;
                }
                if (obj2.equals(notFound)) {
                    return 1;
                }
            }
            return 0;
        });
        return column;
    }

    private Set<String> getColNames () {
        LinkedHashSet<String> columnNames = new LinkedHashSet<>();
        for (HighScoreHolder row : highScores) {
            for (HighScoreObject singleCell : row.getObjectList()) {
                columnNames.add(singleCell.getName());
            }
        }
        return columnNames;
    }

    /**
     * 
     * @return the generated Scene
     */
    public Scene getScene () {
        return output;
    }
}
