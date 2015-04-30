package engine.element.sprites;

import java.lang.reflect.Field;

public interface Modifiable {
    
    public abstract void fixField(String fieldToModify, Object value);
    public abstract void setField(String fieldToModify, String value, Double duration);
    public abstract void changeField(String fieldToModify, String value, Double duration);
}
