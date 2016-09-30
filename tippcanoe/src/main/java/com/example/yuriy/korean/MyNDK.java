package com.example.yuriy.korean;

import java.util.List;

/**
 * Created by yuriy on 9/19/16.
 */

public class MyNDK {

    static {
        System.loadLibrary("MyLibrary");
    }
    public static native String getMyString(String output, String temp, String JSONpath);
//    public static native String getMyString(String output, String temp, String JSONpath,         int layerCount,String[] layerNameWithPath);

    public static native String startTippecanoe(String output, String temp, int size, String[] pathesWithPreffics);
}
