package minecraft54.main.server.event;

import java.lang.annotation.*;


@Documented
@Target (ElementType.METHOD)
@Retention (RetentionPolicy.RUNTIME)
public @interface EventHandler{
    //boolean ignoreCancelled() default false;
}
