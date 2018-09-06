package com.example.lily.baseframeapplication.utils;

import android.text.method.ReplacementTransformationMethod;


/**
 * Created by lily on 2017/11/8.
 *  desc  : Activity相关工具类
 */
public class AllCapTransformationMethod
        extends ReplacementTransformationMethod {

    @Override protected char[] getOriginal() {
        char[] aa = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                'y', 'z' };
        return aa;
    }


    @Override protected char[] getReplacement() {
        char[] cc = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                'Y', 'Z' };
        return cc;
    }
}
