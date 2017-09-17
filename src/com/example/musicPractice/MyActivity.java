package com.example.musicPractice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.musicPractice.view.TxtActivity;

import java.io.File;
import java.util.List;
import java.util.Map;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private final String CURPATH = "curPath";
    private ListView fileList = null;
    private String curPath = null;
    private DBHelper dbHelper = null;
    private List<String> fileStrList = null;
    private Context mContext = null;
    private SimpleAdapter simpleAdapter;
    private List<Map<String, Object>> fileInfoList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        fileList = (ListView) findViewById(R.id.fileList);
        dbHelper = new DBHelper(this);
        curPath = dbHelper.getByKey(CURPATH);
        mContext = this;
        if (curPath == null) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File path;
                path = Environment.getExternalStorageDirectory();
                curPath = path.getPath();
            }
        }
        Log.d("shanlihou", curPath);

        fileInfoList = HandleFile.getInstance().getPath(curPath);
        simpleAdapter = new SimpleAdapter(this, fileInfoList, R.layout.file_list_item, new String[]{"fileName", "icon"},
                new int[]{R.id.fileListName, R.id.fileListImage});
        fileList.setAdapter(simpleAdapter);
        fileList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView fileNameText = (TextView) view.findViewById(R.id.fileListName);
                String fileName = fileNameText.getText().toString();
                boolean bUpdate = false;
                if (fileName == "..") {
                    String tempCur = HandleFile.getInstance().getParent(curPath);
                    if (tempCur != null) {
                        curPath = tempCur;
                        fileInfoList = HandleFile.getInstance().getPath(curPath);
                        bUpdate = true;
                    }
                } else {
                    String curFileName = curPath + "/" + fileName;
                    if (HandleFile.getInstance().isDirectory(curFileName)) {
                        curPath = curFileName;
                        fileInfoList = HandleFile.getInstance().getPath(curPath);
                        bUpdate = true;
                    } else if (curFileName.toLowerCase().endsWith(".mp3")
                            || curFileName.toLowerCase().endsWith(".amr")
                            || curFileName.toLowerCase().endsWith(".wma")) {
                        Intent intent = new Intent(MyActivity.this, MediaPlayerActivity.class);
                        intent.putExtra("fileName", fileName);
                        intent.putExtra("filePath", curPath);
                        startActivity(intent);
                    }else if(curFileName.toLowerCase().endsWith(".txt")){
                        Intent intent = new Intent(MyActivity.this, TxtActivity.class);
                        intent.putExtra("fileName", fileName);
                        intent.putExtra("filePath", curPath);
                        startActivity(intent);
                    }
                }
                if (bUpdate) {
                    simpleAdapter = new SimpleAdapter(mContext, fileInfoList, R.layout.file_list_item, new String[]{"fileName", "icon"},
                            new int[]{R.id.fileListName, R.id.fileListImage});
                    fileList.setAdapter(simpleAdapter);
                    dbHelper.insert(CURPATH, curPath);
                }
            }
        });
    }
}
