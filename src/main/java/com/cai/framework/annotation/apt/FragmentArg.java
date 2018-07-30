package com.cai.framework.annotation.apt;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by clarence on 2018/2/5.
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
public @interface FragmentArg {
    String value() default "";
}
