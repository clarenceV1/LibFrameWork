package com.cai.framework.annotation.apt;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by clarence on 2018/2/5.
 */
@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface PassNull {
}
