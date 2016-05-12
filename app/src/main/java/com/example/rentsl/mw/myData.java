package com.example.rentsl.mw;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rentsl on 2016/5/6.
 */
public class myData {
    public  List<String> mDatas ;

    public  myData() {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }
}
