package com.example.musicPractice.helper;

import android.util.Log;

import java.io.*;

/**
 * Created by shanlihou on 2017/8/9.
 */
public class readerHelper {
    private String mFilename;
    private String mEncodeType;
    public readerHelper(String filename){
        mFilename = filename;
        parseFileInfo();
    }
    private void parseFileInfo(){
        try {
            File file = new File(mFilename);
            FileInputStream input = new FileInputStream(file);
            int pre = (input.read() << 8) + input.read();
            mEncodeType = "US-ASCII";
            switch (pre) {
                case 0xefbb:
                    if (input.read() == 0xbf) {
                        mEncodeType = "UTF-8";
                    }
                    break;
                case 0xfffe:
                    mEncodeType = "Unicode";
                    break;
                case 0xfeff:
                    mEncodeType = "UTF-16BE";
                    break;
                default:
                    mEncodeType = "GBK";   // "US-ASCII"
                    break;
            }
            input.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void readFile(){
        try {
            File file = new File(mFilename);
            FileInputStream input = new FileInputStream(file);
            int pre  = (input.read() << 8) + input.read();
            String code = "US-ASCII";
            switch (pre) {
                case 0xefbb:
                    if (input.read() == 0xbf){
                        code = "UTF-8";
                    }
                    break;
                case 0xfffe:
                    code = "Unicode";
                    break;
                case 0xfeff:
                    code = "UTF-16BE";
                    break;
                default:
                    code = "GBK";   // "US-ASCII"
                    break;
            }
            Log.d("shanlihou", "CodeType: " + code);
            input.close();

            InputStreamReader reader = new InputStreamReader(new FileInputStream(file), code);
            BufferedReader bufReader = new BufferedReader(reader);
            String line;
            while ((line = bufReader.readLine()) != null)
            {
                System.out.print(line);
            }

            System.out.println("");
            reader.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
