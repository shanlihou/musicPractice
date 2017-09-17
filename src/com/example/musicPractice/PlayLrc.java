package com.example.musicPractice;

/**
 * Created by root on 15-9-17.
 */

import android.util.Log;

import java.io.*;
import java.util.*;


/**
 * ���Ÿ�ʷ���
 *
 * @author Administrator
 */
public class PlayLrc {

    /**
     */
    private List<Map<String, Object>> lyric;
    int cur = -1;

    public PlayLrc(File path, String filename) {
        StringBuilder sb = new StringBuilder();
        String lrcfile = filename.substring(0, filename.indexOf("."));
        lyric = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path + "/" + lrcfile + ".lrc"), "UTF-8"));
            String con = "";
            while ((con = br.readLine()) != null) {
                Log.d("shanlihou", con);
                long time = timeToInt(con);
                Log.d("shanlihou", time + "");
                if (time > -1) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("time", time);
                    map.put("content", con.substring(con.indexOf(']') + 1));
                    lyric.add(map);
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public long getLastTime() {
        if (cur < 1)
            cur = 1;
        return (long) lyric.get(cur - 1).get("time");
    }

    public long getNextTime() {
        if (cur >= lyric.size() - 1)
            cur = lyric.size() - 2;
        return (long) lyric.get(cur + 1).get("time");
    }

    public String getContent(int now) {
        int length = lyric.size();
        for (int i = 0; i < length; i++) {
            Map<String, Object> map = lyric.get(i);
            long time = (long) map.get("time");
            if (time > now + 500)
                return null;
            if ((time <= now + 500) && (time >= now - 500)) {
                cur = i;
                return (String) map.get("content");
            }
        }
        return null;
    }

    public int getLength() {
        return lyric.size();
    }

    private long timeToInt(String time) {
        long min = 0;
        long sec = 0;
        long ms = 0;
        char c;
        if (time.length() < 10)
            return -1;
        if (time.charAt(0) != '[') {
            return -2;
        }
        c = time.charAt(1);
        if (!isNumber(c)) {
            return -3;
        }
        min = 10 * (c - '0');

        c = time.charAt(2);
        if (!isNumber(c)) {
            return -4;
        }
        min = min + (c - '0');

        if (time.charAt(3) != ':')
            return -5;

        c = time.charAt(4);
        if (!isNumber(c))
            return -6;
        sec = 10 * (c - '0');

        c = time.charAt(5);
        if (!isNumber(c))
            return -7;
        sec = sec + (c - '0');

        if (time.charAt(6) != '.')
            return -8;
        Integer index = 7;
        ms = getNum(time, index);
        Log.d("shanlihou", "index is:" + index + "ms:" + ms);
        sec = 60 * min + sec;
        ms = sec * 1000 + ms;
        return ms;
    }
    private int getNum(String str, Integer index){
        int num = 0;
        int length = str.length();
        for (; index < length; index++){
            char c = str.charAt(index);
            if (isNumber(c)){
                num = num * 10 + c - '0';
            }else{
                break;
            }
        }
        return num;
    }

    private boolean isNumber(char c) {
        if (c >= '0' && c <= '9')
            return true;
        return false;
    }

    /**
     * ��ȡ����ļ�
     */

}
