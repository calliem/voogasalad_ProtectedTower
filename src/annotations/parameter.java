package annotations;

public @interface parameter {
    boolean playerDisplay() default false;
    boolean settable() default false;
  }