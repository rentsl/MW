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
    public static int learningfinish = 0;
    public static void setLearningfinish(int num){
        learningfinish = num;
    }
}
