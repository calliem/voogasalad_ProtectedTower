package authoringEnvironment.editors;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import util.player.ReflectionUtil;
import annotations.parameter;
import authoringEnvironment.Controller;
import authoringEnvironment.InstanceManager;
import authoringEnvironment.MissingInformationException;


public class ModifierStrip extends VBox {
    private static final String SPRITE_TYPES = "resources/sprite_parameter_type";
    private static final ResourceBundle spriteNeeded = ResourceBundle.getBundle(SPRITE_TYPES);

    private String myKey;
    private Controller myController;

    // private static final String;
    private TextField nameField;
    private ChoiceBox<String> type;
    private ChoiceBox<String> authoringObjects;
    private ChoiceBox<String> type2;
    private ChoiceBox<String> objects2;
    private ChoiceBox<String> changeType;
    private ChoiceBox<String> fields;
    private TextField amount;
    
    public ModifierStrip (Controller controller, double width) {
        myKey = Controller.KEY_BEFORE_CREATION;
        myController = controller;
        Text when = new Text("When");
        when.setFont(new Font(20));
        Text collides = new Text("collides with");
        collides.setFont(new Font(20));
        Text to = new Text("to");
        to.setFont(new Font(20));
        Text forText = new Text("for");
        forText.setFont(new Font(20));
        Text seconds = new Text("seconds");
        seconds.setFont(new Font(20));
        HBox row = new HBox(ModifierEditor.PADDING);
        this.getChildren().add(row);
        row.setPrefWidth(width);
        row.setAlignment(Pos.CENTER);
        type = new ChoiceBox<>();
        authoringObjects = new ChoiceBox<>();
        type2 = new ChoiceBox<>();
        objects2 = new ChoiceBox<>();
        changeType = new ChoiceBox<>();
        fields = new ChoiceBox<>();
        amount = new TextField();
        amount.setPrefWidth(50);
        TextField amountSecs = new TextField();
        amountSecs.setPrefWidth(50);
        row.getChildren().addAll(when, type, authoringObjects, collides, type2, objects2,
                                 changeType, fields, to, amount);
        List<String> changes = new ArrayList<>();
        changes.add("change");
        changes.add("set");
        changes.add("fix");
        changeType.setItems(FXCollections.observableList(changes));
        changeType.getSelectionModel()
                .selectedIndexProperty()
                .addListener( (Observe, numb1, numb2) -> {
                    if ((int) numb2 == 2) {
                        row.getChildren().remove(forText);
                        row.getChildren().remove(amountSecs);
                        row.getChildren().remove(seconds);
                        return;
                    }
                    if ((int) numb1 == 2 || (int) numb1 == -1) {
                        row.getChildren().addAll(forText, amountSecs, seconds);
                    }

                });

        ObservableList<String> possibleTypes =
                FXCollections.observableList(new ArrayList<String>(Arrays.asList(spriteNeeded
                        .getString("hasTags").split(","))));

        type.setItems(possibleTypes);
        type.getSelectionModel()
                .selectedIndexProperty()
                .addListener( (Observe, numb1, numb2) -> {
                    authoringObjects.setItems(myController.getTagsList());
                });
        type2.setItems(possibleTypes);
        type2.getSelectionModel()
                .selectedIndexProperty()
                .addListener( (Observe, numb1, numb2) -> {
                    objects2.setItems(myController.getTagsList());
                    try {
                        fields.setItems(generateFieldsList(possibleTypes
                                .get((int) numb2)));
                    }
                    catch (Exception e) {
                        System.err
                                .print("Error Finding Fields! Update Properties File!");
                    }
                });
        HBox save = new HBox(ModifierEditor.PADDING);
        save.setAlignment(Pos.CENTER);
        Button saveButton = new Button("Save");
        saveButton.setOnMouseClicked(e -> save());
        nameField = new TextField();
        nameField.setTooltip(new Tooltip("Enter a modifier name"));
        save.getChildren().addAll(saveButton, nameField);
        this.getChildren().add(save);
    }

    private ObservableList<String> generateFieldsList (String selectedItem)
                                                                           throws ClassNotFoundException {
        System.out.println(selectedItem);
        List<Class<?>> classesWithFields =
                ReflectionUtil.getPackageParentList(Class.forName("engine.element.sprites." +
                                                                  selectedItem));
        List<String> fieldsList = new ArrayList<String>();

        for (Class<?> myClass : classesWithFields) {
            System.out.println(myClass);
            Field[] myFields = myClass.getDeclaredFields();
            for (Field field : myFields) {
                if (field.getAnnotation(parameter.class) != null &&
                    field.getAnnotation(parameter.class).settable()) {
                    fieldsList.add(field.getName());
                }
            }

        }
        return FXCollections.observableArrayList(fieldsList);
    }
    

    private void save () {
        Map<String, Object> myMap = new HashMap<>();
        myMap.put(InstanceManager.NAME_KEY, nameField.getText());
        myMap.put(InstanceManager.PART_TYPE_KEY, "Modifier");
        myMap.put("actorType", type.getSelectionModel().getSelectedItem());
        myMap.put("acteeType", type2.getSelectionModel().getSelectedItem());
        myMap.put("actorTag", authoringObjects.getSelectionModel().getSelectedItem());
        myMap.put("acteeTag", objects2.getSelectionModel().getSelectedItem());
        myMap.put("changeType", changeType.getSelectionModel().getSelectedItem());
        myMap.put("field", fields.getSelectionModel().getSelectedItem());
        myMap.put("amount", amount.getText());

        try {
            if (myKey.equals(Controller.KEY_BEFORE_CREATION)) {

                myKey = myController.addPartToGame(myMap);

            }
            else {
                myKey = myController.addPartToGame(myKey, myMap);
            }
        }
        catch (MissingInformationException e) {
            // TODO Auto-generated catch block
            System.err.print("Insufficient Modifier Data");
        }

    }
}
