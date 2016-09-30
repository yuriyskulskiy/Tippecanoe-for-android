//
// Created by yuriy on 9/23/16.
//

#include "com_example_yuriy_korean_MyNDK.h"
#include "main_tiopecanoe.hpp"
#include <android/log.h>

JNIEXPORT jstring JNICALL Java_com_example_yuriy_korean_MyNDK_getMyString
        (JNIEnv *env, jobject obj, jstring output_path, jstring tmp_full_path, jstring json_path) {
//        (JNIEnv *env, jobject obj, jstring output_path, jstring tmp_full_path, jstring json_path,jint layers_count,jobjectArray stringArray) {
    __android_log_write(ANDROID_LOG_INFO, "asd",
                        "Java_com_example_yuriy_korean_MyNDK_getMyString ");

    const char *native_output_path = (*env).GetStringUTFChars(output_path, 0);
    const char *native_tmp_full_path = (*env).GetStringUTFChars(tmp_full_path, 0);
    const char *native_json_path = (*env).GetStringUTFChars(json_path, 0);

    __android_log_write(ANDROID_LOG_INFO, "native_output_path", native_output_path);
    __android_log_write(ANDROID_LOG_INFO, "native_tmp_full_path", native_tmp_full_path);
    __android_log_write(ANDROID_LOG_INFO, "native_json_path", native_json_path);
//    argv[2] = (char *) "-rg";
//    char *argv[7];
//    argv[0] = (char *) "tippecanoe";
//    argv[1] = (char *) "-f";
//
//    argv[2] = (char *) "-o";
//    argv[3] = (char *)native_output_path;
//    argv[4] = (char *) native_json_path;
//    argv[5] = (char *) "-t";
//    argv[6] = (char *) native_tmp_full_path;

    //tippecanoe -f -rg -o outputPath pathToGeoJsonFile -t TemporaryDirectory
//    if (runMain(7, argv) == 0) {
//        __android_log_write(ANDROID_LOG_INFO, "runMain",  "success");
//    } else {
//        __android_log_write(ANDROID_LOG_INFO, "runMain",  "fail");
//    }


    //-----------------------start layer integration-----------------------------------------


//    int layersCount = env->GetArrayLength(stringArray);
//    int layersCount = layers_count;
//    char *layers[layersCount];
//    for (int i=0; i<layersCount; i++) {
//        jstring layer_with_path = (jstring) (env->GetObjectArrayElement(stringArray, i));
//        const char *rawString = env->GetStringUTFChars(layer_with_path, 0);
//        layers[i] = (char *)rawString;
    //    TODO  should call DeleteLocalRef(string) at the end of the loop to avoid creating an unbounded number of object references. Also, it should be ReleaseStringUTFChars(string, myarray).
//    }


    int layersCount = 2;

    int valuesCount = 10;



    char *layers[layersCount];
    layers[0] = (char *) "main_layer:/storage/sdcard/fields.json";
    layers[1] = (char *) "marker_layer:/storage/sdcard/markers.json";


    char *argv[valuesCount];
    argv[0] = (char *) "tippecanoe";
    argv[1] = (char *) "-f";
    argv[2] = (char *) "-rg";
    argv[3] = (char *) "-o";
    argv[4] = (char *) native_output_path;
    argv[5] = (char *) "-t";
    argv[7] = (char *) native_tmp_full_path;
    //bonus
    argv[8] = (char *) "main_layer:/storage/sdcard/fields.json";

    argv[9] = (char *) "marker_layer:/storage/sdcard/markers.json";






    //    render_vector_tiles( true, true, (char*)[outputPath UTF8String], (char*)[NSTemporaryDirectory() UTF8String], files.allKeys.count, layars, values.count, args);
    if(render_vector_tiles(true, true, (char*) native_output_path, (char*) native_tmp_full_path, layersCount, layers, valuesCount, argv)==0){
        __android_log_write(ANDROID_LOG_INFO, "runMain",  "success");
    } else {
        __android_log_write(ANDROID_LOG_INFO, "runMain",  "fail");
    }
    //--------------------------------------------------------------------------------------
    return (*env).NewStringUTF("Hey, Babe!");
}

JNIEXPORT jstring JNICALL Java_com_example_yuriy_korean_MyNDK_startTippecanoe
        (JNIEnv *env, jobject obj, jstring pathToOutPut, jstring pathToTmpDir, jint stringArray_size, jobjectArray stringArray) {
    __android_log_write(ANDROID_LOG_INFO, "c++ log:",  "startTippecanoe() is running...");
//    __android_log_write(ANDROID_LOG_INFO, "c++ log:",  jint); так нельзя

    //converting incomming data jstrings
    const char *native_output_path = (*env).GetStringUTFChars(pathToOutPut, 0);
    const char *native_tmp_full_path = (*env).GetStringUTFChars(pathToTmpDir, 0);
    __android_log_write(ANDROID_LOG_INFO, "native_output_path", native_output_path);
    __android_log_write(ANDROID_LOG_INFO, "native_tmp_full_path", native_tmp_full_path);

   //creating array of args for render_vector_tiles() method : 6th argument
    int jsonFilesCount = stringArray_size;
    char *pathes[jsonFilesCount];

    //creating array of args for render_vector_tiles() method : 8th argument
    int valuesCount = 7+stringArray_size;
    char *argv[valuesCount];

    // hardcoding data args
    argv[0] = (char *) "tippecanoe";
    argv[1] = (char *) "-f";
    argv[2] = (char *) "-rg";
    argv[3] = (char *) "-o";
    argv[4] = (char *) native_output_path;
    argv[5] = (char *) "-t";
    argv[6] = (char *) native_tmp_full_path;

    //retreaving additional string data from Java jobjectArray stringArray -(it is simple string array in java code)
    for (int i=0; i<stringArray_size; i++) {
        jstring string = (jstring) (env->GetObjectArrayElement(stringArray, i));
        const char *rawString = env->GetStringUTFChars(string, 0);
        // TODO Don't forget to call `ReleaseStringUTFChars` when you're done.

        __android_log_write(ANDROID_LOG_INFO, "c++ log:",  rawString);

       //adding additional incomming from Java args to the argv array that has 7hardcoded arguments
        argv[7+i] = (char *) rawString;
        //fullfill empty pathes array with incomming data from java
        pathes[i]= (char *) rawString;
    }

    if(render_vector_tiles(true, true, (char*) native_output_path, (char*) native_tmp_full_path, jsonFilesCount, pathes, valuesCount, argv)==0){
        __android_log_write(ANDROID_LOG_INFO, "render_vector_tiles()",  "success");
    } else {
        __android_log_write(ANDROID_LOG_INFO, "render_vector_tiles()",  "fail");
    }

    return (*env).NewStringUTF("startTippecanoe has been finished");
}