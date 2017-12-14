package com.example.musicPractice.view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.example.musicPractice.R;
import com.example.musicPractice.io.ReadFile;
import com.example.musicPractice.io.ReadFileRandom;
import com.example.musicPractice.paramter.Magic;
import com.example.musicPractice.util.BytesEncodingDetect;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shanlihou on 2017/8/6.
 */
public class TxtActivity extends Activity{
    private String fileName;
    private String filePath;
    private File mFile;
    ReadFile mReader;
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
        mTxtDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    private void init(){
        try{
            mReader = new ReadFile(filePath + "/" + fileName);
            mTxtDisplay.setText(mReader.read(1000), TextView.BufferType.SPANNABLE);
            getEachWord(mTxtDisplay);
            mTxtDisplay.setMovementMethod(LinkMovementMethod.getInstance());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void getEachWord(TextView textView){
        Spannable spans = (Spannable)textView.getText();
        Integer[] indices = getIndices(
                textView.getText().toString(), ' ');
        Log.d("shanlihou", "trim len:" + textView.getText().toString().trim().length());
        Log.d("shanlihou", "span len:" + spans.length());
        int start = 0;
        int end = 0;
        // to cater last/only word loop will run equal to the length of indices.length
        for (int i = 0; i <= indices.length; i++) {
            ClickableSpan clickSpan = getClickableSpan();
            // to cater last/only word
            end = (i < indices.length ? indices[i] : spans.length());
            spans.setSpan(clickSpan, start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = end + 1;
        }
        //改变选中文本的高亮颜色
        textView.setHighlightColor(Color.BLUE);
    }
    private ClickableSpan getClickableSpan(){
        return new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                TextView tv = (TextView) widget;
                String s = tv
                        .getText()
                        .subSequence(tv.getSelectionStart(),
                                tv.getSelectionEnd()).toString();
                Log.d("tapped on:", s);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.BLACK);
                ds.setUnderlineText(false);
            }
        };
    }

    public static Integer[] getIndices(String s, char c) {
        int pos = s.indexOf(c, 0);
        List<Integer> indices = new ArrayList<>();
        while (pos != -1) {
            indices.add(pos);
            pos = s.indexOf(c, pos + 1);
        }
        return indices.toArray(new Integer[0]);
    }
    /*
    private void init(){
        mScreenHeigth = this.getWindowManager().getDefaultDisplay().getHeight();
        mScreenWidth = this.getWindowManager().getDefaultDisplay().getWidth();

        /** text size color and background
        mTxtDisplay.setTextColor(Color.BLACK);
        mTxtDisplay.setTextSize(Magic.FONT18);

        //mHelper = new CRDBHelper(this);
        // ���ñ���

        if ("".equals(Magic.IMAGE_PATH)) {
            mScrollView.setBackgroundResource(R.drawable.defautbg);
        } else {
            mScrollView.setBackgroundDrawable(Drawable
                    .createFromPath(Magic.IMAGE_PATH));
        }
        mReaderBytes = new ReadFileRandom(filePath + '/' + fileName);
        byte[] encodings = new byte[400];
        mReaderBytes.readBytes(encodings);
        mReaderBytes.close();
        /** �����Ǽ���ļ��ı���
        BytesEncodingDetect be = new BytesEncodingDetect();
        this.encoding = BytesEncodingDetect.nicename[be
                .detectEncoding(encodings)];
        /** ����ļ��ı������
        Log.d("shanlihou", this.encoding);
        mTxtDisplay.setText(mReaderBytes.readBytes(1000).toString());
        /** load the attribute for font
        TextPaint tp = mTxtDisplay.getPaint();
//        CR.fontHeight = mTxtDisplay.getLineHeight();

        /** Ascii char width
        //CR.upperAsciiWidth = (int) tp.measureText(Magic.UPPERASCII);
       // CR.lowerAsciiWidth = (int) tp.measureText(Magic.LOWERASCII);
        /** Chinese char width */
        /*
        CR.ChineseFontWidth = (int) tp.measureText(Magic.CHINESE
                .toCharArray(), 0, 1);

        Log.d("onCreateDialog CR.FontHeight:", "" + CR.fontHeight);
        Log.d("onCreateDialog CR.AsciiWidth:", "" + CR.upperAsciiWidth);
        Log.d("onCreateDialog CR.FontWidth:", "" + CR.ChineseFontWidth);*/
        /*
        mTxtReader = new CopyOfTxtReader(mTxtDisplay, this, _mFilePath,
                mScreenWidth, mScreenHeigth, encoding);

        this.setTitle(_mFilePath + "-" + getString(R.string.app_name));
        mScrollView.setOnKeyListener(mUpOrDown);
        mScrollView.setOnTouchListener(mTouchListener);
        //showToast();
    }*/
}
