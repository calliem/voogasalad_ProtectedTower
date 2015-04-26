package annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * This annotation is used to specify parameters which can either be set by the authoring
 * environment, and/or displayed by the player
 * 
 * @author Greg McKeon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface parameter {
    boolean playerDisplay() default false;

    boolean settable() default false;

    String defaultValue() default "null";
}
