package com.example.musicPractice;

import android.util.Log;

import java.io.File;
import java.util.*;

/**
 * Created by root on 15-9-17.
 */
public class HandleFile {
    private HandleFile() {

    }

    private static HandleFile instance = null;

    public static HandleFile getInstance() {
        synchronized (HandleFile.class) {
            if (instance != null)
                return instance;
            instance = new HandleFile();
            return instance;
        }
    }

    public List<Map<String, Object>> getPath(String strPath) {
        Log.d("shanlihou", strPath);
        List<Map<String, Object>> retMap = new ArrayList<>();
        File filePath = new File(strPath);

        if (!filePath.isDirectory()) {
            return retMap;
        }
        File[] files = filePath.listFiles();
        try {
            Log.d("shanlihou", files.length + "");
            for (File i : files) {
                if (i != null) {
                    Map<String, Object> map = new HashMap<>();
                    Log.d("shanlihou", i.getName());
                    map.put("fileName", i.getName());
                    if (i.isDirectory()){
                        map.put("icon", R.drawable.document);
                    }else if (i.isFile() && (i.getName().toLowerCase().endsWith(".mp3")
                            || i.getName().toLowerCase().endsWith(".amr")
                            || i.getName().toLowerCase().endsWith(".wma"))) {
                        map.put("icon", R.drawable.mp3);
                    }else if (i.isFile() && (i.getName().toLowerCase().endsWith(".txt"))){
                        map.put("icon", R.drawable.book);
                    }else{
                        map.put("icon", R.drawable.ic_launcher);
                    }
                    retMap.add(map);
                }
            }
            Collections.sort(retMap, new CompareValues());
            Map<String, Object> map = new HashMap<>();
            map.put("fileName", "..");
            map.put("icon", R.drawable.document);
            retMap.add(0, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retMap;
    }

    public List<String> getMusic(String strPath) {
        List<String> retList = new ArrayList<>();
        File filePath = new File(strPath);
        File[] files = filePath.listFiles();
        for (File i : files) {
            if (i.isFile()) {
                if (i.getName().toLowerCase().endsWith(".mp3")
                        || i.getName().toLowerCase().endsWith(".amr")
                        || i.getName().toLowerCase().endsWith(".wma")) {
                    retList.add(i.getName());
                }
            }
        }
        return retList;
    }

    public String getParent(String strPath) {
        File filePath = new File(strPath);
        return filePath.getParent();
    }

    public boolean isDirectory(String strPath) {
        File file = new File(strPath);
        return file.isDirectory();
    }

}
