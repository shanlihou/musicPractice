package com.example.musicPractice;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 15-9-17.
 */
public class CompareValues implements Comparator<Object>{
    @Override
    public int compare(Object left, Object right) {
        Map <String, Object> t1 = (HashMap)left;
        Map <String, Object> t2 = (HashMap)right;
        String s1 = (String)t1.get("fileName");
        String s2 = (String)t2.get("fileName");
        return s1.compareTo(s2);
    }
}
