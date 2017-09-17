package com.example.musicPractice.view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.widget.TextView;
import com.example.musicPractice.R;
import com.example.musicPractice.io.ReadFileRandom;
import com.example.musicPractice.paramter.Magic;
import com.example.musicPractice.util.BytesEncodingDetect;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by shanlihou on 2017/8/6.
 */
public class TxtActivity extends Activity{
    private String fileName;
    private String filePath;
    private File mFile;
    private int offset;
    private FileInputStream mInput;
    private ReadFileRandom mReaderBytes;
    private String encoding = "GB2312";
    private int mScreenHeigth, mScreenWidth;
    TextView mTxtDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.txt);
        fileName = this.getIntent().getStringExtra("fileName");
        filePath = this.getIntent().getStringExtra("filePath");
        mFile = new File(filePath + "/" + fileName);
        mTxtDisplay = (TextView) findViewById(R.id.txt_display);
        Log.d("shanlihou", mFile.getTotalSpace() + "");
        init();
    }
    private void init(){
        mScreenHeigth = this.getWindowManager().getDefaultDisplay().getHeight();
        mScreenWidth = this.getWindowManager().getDefaultDisplay().getWidth();

        /** text size color and background */
        mTxtDisplay.setTextColor(Color.BLACK);
        mTxtDisplay.setTextSize(Magic.FONT18);

        //mHelper = new CRDBHelper(this);
        // ���ñ���
        /*
        if ("".equals(Magic.IMAGE_PATH)) {
            mScrollView.setBackgroundResource(R.drawable.defautbg);
        } else {
            mScrollView.setBackgroundDrawable(Drawable
                    .createFromPath(Magic.IMAGE_PATH));
        }*/
        mReaderBytes = new ReadFileRandom(filePath + '/' + fileName);
        byte[] encodings = new byte[400];
        mReaderBytes.readBytes(encodings);
        mReaderBytes.close();
        /** �����Ǽ���ļ��ı��� */
        BytesEncodingDetect be = new BytesEncodingDetect();
        this.encoding = BytesEncodingDetect.nicename[be
                .detectEncoding(encodings)];
        /** ����ļ��ı������ */

        /** load the attribute for font */
        TextPaint tp = mTxtDisplay.getPaint();
//        CR.fontHeight = mTxtDisplay.getLineHeight();

        /** Ascii char width */
        //CR.upperAsciiWidth = (int) tp.measureText(Magic.UPPERASCII);
       // CR.lowerAsciiWidth = (int) tp.measureText(Magic.LOWERASCII);
        /** Chinese char width */
        /*
        CR.ChineseFontWidth = (int) tp.measureText(Magic.CHINESE
                .toCharArray(), 0, 1);

        Log.d("onCreateDialog CR.FontHeight:", "" + CR.fontHeight);
        Log.d("onCreateDialog CR.AsciiWidth:", "" + CR.upperAsciiWidth);
        Log.d("onCreateDialog CR.FontWidth:", "" + CR.ChineseFontWidth);*/
        mTxtReader = new CopyOfTxtReader(mTxtDisplay, this, _mFilePath,
                mScreenWidth, mScreenHeigth, encoding);

        this.setTitle(_mFilePath + "-" + getString(R.string.app_name));
        mScrollView.setOnKeyListener(mUpOrDown);
        mScrollView.setOnTouchListener(mTouchListener);
        //showToast();
    }
}
