package com.example.yuriy.korean;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuriy on 9/29/16.
 */

public class TippeCanoeService {

    private String fieldsJsonFileName = "fields.json";
    private String markersJsonFileName = "markers.json";

    //allows to customize json's filenames
    public TippeCanoeService(String fieldsJsonFileName, String markersJsonFileName) {
        this.fieldsJsonFileName = fieldsJsonFileName;
        this.markersJsonFileName = markersJsonFileName;
    }

    //uses default JSON's filenames
    public TippeCanoeService() {
    }

    public void generateMbtilesViaTippecanoe(String mbtilesFileName, Context context) {
        Log.e("is it readable: ", Boolean.toString(isExternalStorageReadable()));
        Log.e("is it writtable: ", Boolean.toString(isExternalStorageWritable()));
        File externalDir = Environment.getExternalStorageDirectory().getAbsoluteFile();

        File tempDir = context.getCacheDir();
        if (!tempDir.exists()) {
            tempDir.mkdirs();
            Log.e("TAG", "creating tempDir...");
        }
        String tmpFullPath = tempDir.getAbsolutePath();

        File outPutFile = new File(externalDir, mbtilesFileName);
        String outPutPath = outPutFile.getAbsolutePath();

        File jsnoFile = new File(externalDir, fieldsJsonFileName);
        String path_fieldsJson = jsnoFile.getAbsolutePath();

//------------------------------start preparation: creating array of pathes for JNI method---------------------------------------------------------
        //creating dynamic array with layers
        ArrayList<String> pathesToJson = new ArrayList<>();

        File markersFile = new File(externalDir, markersJsonFileName);

        //absolute path to the layer json file
        String path_markersJson = markersFile.getAbsolutePath();

        pathesToJson.add(path_fieldsJson);
        pathesToJson.add(path_markersJson);

        String[] pathesToJsonFiles_withPrefics = createSimpleArrayOfPathesWithPreffics(pathesToJson);
        //StringData has  elems type like::      "anylayername:/path/to/the/json"

        //-------------------------------------------------------------------------------------------


        Log.e("TAG", "........................From Java................................................");
        for (String elem :
                pathesToJsonFiles_withPrefics) {
            Log.e("javaStringArr elements ", elem);
        }
        Log.e("TAG", "path to outPutFile:" + outPutPath);
        Log.e("TAG", "path to tempDir:" + tmpFullPath);
        Log.e("TAG", "........................................................................");


        //   String result = MyNDK.getMyString(outPutPath, tmpFullPath, path_fieldsJson); //this is simple working method - dont delete

        String result = MyNDK.startTippecanoe(outPutPath, tmpFullPath, 2, pathesToJsonFiles_withPrefics);


        Log.e("RESULT", result);


    }

    private String[] createSimpleArrayOfPathesWithPreffics(List<String> pathesToJson) {
        int size = pathesToJson.size();
        String[] converted_pathesToJson = new String[size];
        int position = 0;
        for (String path :
                pathesToJson) {
            converted_pathesToJson[position++] = "layer" + position + ":" + path;
        }
        return converted_pathesToJson;
    }


    /* Checks if external storage is available for read and write */
    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    private boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public String getFieldsJsonFileName() {
        return fieldsJsonFileName;
    }

    public void setFieldsJsonFileName(String fieldsJsonFileName) {
        this.fieldsJsonFileName = fieldsJsonFileName;
    }

    public String getMarkersJsonFileName() {
        return markersJsonFileName;
    }

    public void setMarkersJsonFileName(String markersJsonFileName) {
        this.markersJsonFileName = markersJsonFileName;
    }
}
