package com.example.rentsl.mw;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by rentsl on 2016/5/1.
 */


public class Data {
    private static SharedPreferences keynum;

    public static ArrayList wordsnumber = new ArrayList();
    public static void setWordsnumber(Context context){
        keynum = context.getSharedPreferences("Keynum",context.MODE_PRIVATE);
        //keynum.edit();

        int num = keynum.getInt("number",0);
        int listsize = wordsnumber.size();
        //初始化前先清除所有项
            for(int j = 0;j < listsize;j++){
                wordsnumber.remove(0);
            }

        for(int i = 0;i < num;i++)
        wordsnumber.add(i);
    }

}
