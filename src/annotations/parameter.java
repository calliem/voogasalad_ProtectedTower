package annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface parameter {
    boolean playerDisplay() default false;
    boolean settable() default false;
    String defaultValue() default "null";
  }