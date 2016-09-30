package com.example.yuriy.korean;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class MainUselessActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.text_view);



        String mbtilesFileName = "outputTestClass.mbtiles";
//        generateMbtilesViaTippecanoe(mbtilesFileName);
        TippeCanoeService tippeCanoeService = new TippeCanoeService();
        tippeCanoeService.generateMbtilesViaTippecanoe(mbtilesFileName,this);



    }

    public void generateMbtilesViaTippecanoe(String mbtilesFileName) {

        File externalDir = Environment.getExternalStorageDirectory().getAbsoluteFile();

        File tempDir = this.getCacheDir();
        if (!tempDir.exists()) {
            tempDir.mkdirs();
            Log.e("TAG", "creating tempDir...");
        }
        String tmpFullPath = tempDir.getAbsolutePath();

        File outPutFile = new File(externalDir, mbtilesFileName);
        String outPutPath = outPutFile.getAbsolutePath();

        File jsnoFile = new File(externalDir, "fields.json");
        String path_fieldsJson = jsnoFile.getAbsolutePath();

//------------------------------start preparation: creating array of pathes for JNI method---------------------------------------------------------
        //creating dynamic array with layers
        ArrayList<String> pathesToJson = new ArrayList<>();

        File markersFile = new File(externalDir, "markers.json");

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

    public String[] createSimpleArrayOfPathesWithPreffics(List<String> pathesToJson) {
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
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }


}
