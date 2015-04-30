package engine.element;

import java.lang.reflect.Field;
import java.util.Map;
import engine.element.sprites.GameElement;
import javafx.scene.shape.Path;
import annotations.parameter;

/**
 * This class represents an object which may carry a modification to set upon another object, such
 * as a status effect.
 * 
 * @author Qian Wang
 * @author Greg McKeon
 *
 */
public class Modifier {

    private String name;
    private String actorTag;
    private String acteeTag;
    private String applyType;
    private String fieldName;
    private String amount;

    public Modifier() {

    }

    public void addInstanceVariables (Map<String, Object> parameters) {
        name = (String) parameters.get("name");
        actorTag = (String) parameters.get("actorTag");
        acteeTag = (String) parameters.get("acteeTag");
        fieldName = (String) parameters.get("fieldName");
        applyType = (String) parameters.get("applyType");
        amount = (String) parameters.get("amount");  
    }

    public String getFieldName(){
        return fieldName;
    }
    public String getApplyType(){
        return applyType;
    }
    public String getActor(){
        return actorTag;
    }
    public String getActee(){
        return acteeTag;
    }
    public String getAmount(){
        return amount;
    }
}
