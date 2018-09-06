package com.example.lily.baseframeapplication.dao.retrofit;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by lily on 2017/11/9.
 */

@Retention(RetentionPolicy.RUNTIME)  //这句话代表着将使用该注解类的信息值保持到真正的客户端运行时环境。
public @interface TAG {

    String value() default "";

}
