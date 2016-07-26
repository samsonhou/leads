package com.jiezh.content.base.pub.token;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Token {
	 //生成
	 boolean generate() default false;
	 //验证
	 boolean validate() default false;
}
