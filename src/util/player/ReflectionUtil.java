package util.player;

import java.util.ArrayList;
import java.util.List;


public class ReflectionUtil {

    public static List<Class<?>> getPackageParentList (Class<?> baseClass) {
        List<Class<?>> output = new ArrayList<>();
        while(baseClass!=Object.class){
            output.add(baseClass);
            baseClass=baseClass.getSuperclass();
        }
        return output;

    }
}
