package com.example.rentsl.mw;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class ErrorLearing extends AppCompatActivity {
    int Tx;
    public static final String FILENAME = "SE.set";
    ArrayList<HashMap<String, Object>> mDate;
//    SharedPreferences Wordslist;
//    SharedPreferences Explainlist;
//    SharedPreferences keynum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_learing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Tx = intent.getIntExtra("number", -1);
        TextView ewords = (TextView) findViewById(R.id.errorwords);
        TextView explain = (TextView) findViewById(R.id.errorexplain);
        Button ebutton = (Button) findViewById(R.id.errorbutton);
        mDate = getDate();
//        Wordslist = getSharedPreferences("wordslist1",MODE_PRIVATE);
//        Explainlist = getSharedPreferences("explainlist1",MODE_PRIVATE);
        ewords.setText(mDate.get(Tx-1).get("Words").toString());
        explain.setText(mDate.get(Tx-1).get("Explain").toString());





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void exitbutton3(View v) {

        this.finish();
    }

    //得到数据
    public ArrayList<HashMap<String, Object>> getDate() {
        ArrayList<HashMap<String, Object>> mylist = new ArrayList<HashMap<String, Object>>();
        mylist = StringToList(readFiles());
        return mylist;
    }
    // 保存文件内容
    public void writeFiles(String content) {
        try {
            // 打开文件获取输出流，文件不存在则自动创建
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);//MODE_APPEND表示追加模式保存数据（Context.MODE_PRIVATE覆盖式写入方式）
            fos.write(content.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 读取文件内容
    public String readFiles() {
        String content = null;
        try {
            FileInputStream fis = openFileInput(FILENAME);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //baos = null;
            byte[] buffer = new byte[1024];
            int len = 0;
            int flag = 0;
            while ((len = fis.read(buffer)) != -1) {
                flag = 1;
                baos.write(buffer, 0, len);
            }
            if(flag == 1) content = baos.toString();
            else content = null;
            fis.close();
            baos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }
    // 将ArrayList转成String字符串
    public String ListToString(ArrayList<HashMap<String, Object>> mdate){
        String context = "";// null时 会打印出null
        for(int i = 0;i < mdate.size();i++){
            context = context+mdate.get(i).get("Words").toString()+":"+mdate.get(i).get("Explain").toString()+":";
        }
        return context;
    }
    // 将String字符串转成ArrayList
    public ArrayList<HashMap<String, Object>> StringToList(String conText){
        ArrayList<HashMap<String, Object>> mylist = new ArrayList<HashMap<String, Object>>();
        if(conText != null){
            String[] a = conText.split(":");
            int len = a.length;
            for(int i = 0;i < len;i=i+2){
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("Words",a[i]);
                map.put("Explain",a[i+1]);
                mylist.add(map);
            }
        }
        return mylist;
    }


}
