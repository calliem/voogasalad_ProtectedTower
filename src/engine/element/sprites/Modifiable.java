package engine.element.sprites;

import java.lang.reflect.Field;

public interface Modifiable {
    
    public abstract void fixField(Field fieldToModify, String value);
    public abstract void setField(Field fieldToModify, String value);
    public abstract void changeField(Field fieldToModify, String value);
}
