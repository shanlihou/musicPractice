package com.example.musicPractice;

/**
 * Created by root on 15-9-17.
 */
import java.io.*;
import java.util.*;


/**
 * ���Ÿ�ʷ���
 *
 * @author Administrator
 *
 */
public class PlayLrc
{

    /**
     */
    private List<Map<String, Object>> lyric;
    int cur = -1;
    public PlayLrc(File path, String filename){
        StringBuilder sb = new StringBuilder();
        String lrcfile=filename.substring(0,filename.indexOf("."));
        lyric = new ArrayList<>();
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path+"/"+lrcfile+".lrc"),"UTF-8"));
            String con = "";
            while ((con = br.readLine()) != null)
            {
                sb.append(con + "\n");
            }
            br.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(new StringReader(sb.toString()));
        try{
            String line;
            while((line = br.readLine()) != null){
                long time = timeToInt(line);
                if (time != -1){
                    Map<String, Object> map = new HashMap<>();
                    map.put("time", time);
                    map.put("content", line.substring(line.indexOf(']') + 1));
                    lyric.add(map);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public String getContent(int now){
        int length = lyric.size();
        for (int i = 0; i < length; i++){
            Map<String, Object>map = lyric.get(i);
            long time = (long)map.get("time");
            if (time > now + 500)
                return null;
            if ((time <= now + 500) && (time >= now -500)){
                cur = i;
                return (String)map.get("content");
            }
        }
        return null;
    }

    public int getLength(){
        return lyric.size();
    }

    private long timeToInt(String time){
        long min = 0;
        long sec = 0;
        long ms = 0;
        char c;
        if (time.charAt(0) != '['){
            return -1;
        }
        c = time.charAt(1);
        if (!isNumber(c)){
            return -1;
        }
        min = 10 * (c - '0');

        c = time.charAt(2);
        if (!isNumber(c)){
            return -1;
        }
        min = min + (c - '0');

        if (time.charAt(3) != ':')
            return -1;

        c = time.charAt(4);
        if (!isNumber(c))
            return -1;
        sec = 10 * (c - '0');

        c = time.charAt(5);
        if (!isNumber(c))
            return -1;
        sec = sec + (c - '0');

        if (time.charAt(6) != '.')
            return -1;

        c = time.charAt(7);
        if (!isNumber(c))
            return -1;
        ms = 100 * (c - '0');

        c = time.charAt(8);
        if (!isNumber(c))
            return -1;
        ms = ms + 10 * (c - '0');
        if (time.charAt(9) != ']')
            return -1;
        sec = 60 * min + sec;
        ms = sec * 1000 + ms;
        return ms;
    }

    private boolean isNumber(char c){
        if (c >= '0' && c <= '9')
            return true;
        return false;
    }

    /**
     * ��ȡ����ļ�
     */

}
