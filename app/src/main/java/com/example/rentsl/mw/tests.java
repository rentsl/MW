package com.example.rentsl.mw;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class tests extends AppCompatActivity {
    public static tests instance = null;
    public String FILENAME = "SE.set";
    ArrayList<HashMap<String, Object>> mDate;
    public String testname = null;
    TextView words = null;
    Button button1 = null;
    Button button2 = null;
    Button button3 = null;
    Button button4 = null;

    int Bu[] = new int[4];
    int Tx;
    int Wrunflag = 0;

    int Wrandom[] = null;
    int WWrandom[] = null;
    int Trandom[] = new int[4];
    boolean P;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**状态栏透明*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

        Intent intent = getIntent();
        testname = intent.getStringExtra("tablename");
        if(testname != null) FILENAME = testname;
        mDate = getDate();
        P = checkwordsnumber();
        if(!P) {
            Toast.makeText(getApplicationContext(),"单词少于4个",Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            Wrandom = new int[getDate().size()];
            WWrandom = new int[getDate().size()];
            instance = this;//用于在伪弹窗中关闭test这个activity
            WcreatRandom();
            System.arraycopy(Wrandom, 0, WWrandom, 0, Wrandom.length);
            WcreatRandom();
            TcreatRandom();
            Link();
            words = (TextView) findViewById(R.id.Words);
            button1 = (Button) findViewById(R.id.Button1);
            button2 = (Button) findViewById(R.id.Button2);
            button3 = (Button) findViewById(R.id.Button3);
            button4 = (Button) findViewById(R.id.Button4);
            buttonassign();//初始化测试页面的数据
            /**监听函数*/
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Bu[0] == Tx)
                        Toast.makeText(getApplicationContext(), "你答对了！！" + button1.getText(), Toast.LENGTH_SHORT).show();
                    else {
                        Data.setLearningfinish(1); //1
                        Intent intent = new Intent();
                        intent.putExtra("number", Tx);
                        intent.putExtra("truewords",mDate.get(Tx-1).get("Words").toString());
                        intent.putExtra("trueexplain",mDate.get(Tx-1).get("Explain").toString());
                        intent.setClass(tests.this, ErrorLearing.class);
                        startActivity(intent);
                    }
                    Link();
                    /**button赋值*/
                    buttonassign();
                    /**判断单词是否用完*/
                    if (wordsOver() == 1) {
                        Wrunflag = 1;
                        Intent intent = new Intent();
                        intent.setClass(tests.this, Dialog.class);
                        startActivity(intent);//弹窗
                    }
                    /**产生随机数*/
                    WcreatRandom();
                    TcreatRandom();

                }
            });

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Bu[1] == Tx)
                        Toast.makeText(getApplicationContext(), "你答对了！！" + button2.getText(), Toast.LENGTH_SHORT).show();
                    else {
                        Data.setLearningfinish(1); //1
                        Intent intent = new Intent();
                        intent.putExtra("number", Tx);
                        intent.putExtra("truewords", mDate.get(Tx - 1).get("Words").toString());
                        intent.putExtra("trueexplain",mDate.get(Tx-1).get("Explain").toString());
                        intent.setClass(tests.this, ErrorLearing.class);
                        startActivity(intent);
                    }
                    Link();
                    buttonassign();
                    if (wordsOver() == 1) {
                        Wrunflag = 1;
                        Intent intent = new Intent();
                        intent.setClass(tests.this, Dialog.class);
                        startActivity(intent);
                    }
                    WcreatRandom();
                    TcreatRandom();

                }
            });

            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Bu[2] == Tx)
                        Toast.makeText(getApplicationContext(), "你答对了！！" + button3.getText(), Toast.LENGTH_SHORT).show();
                    else {
                        Data.setLearningfinish(1); //1
                        Intent intent = new Intent();
                        intent.putExtra("number", Tx);
                        intent.putExtra("truewords", mDate.get(Tx - 1).get("Words").toString());
                        intent.putExtra("trueexplain",mDate.get(Tx-1).get("Explain").toString());
                        intent.setClass(tests.this, ErrorLearing.class);
                        startActivity(intent);
                    }
                    Link();
                    buttonassign();
                    if (wordsOver() == 1) {
                        Wrunflag = 1;
                        Intent intent = new Intent();
                        intent.setClass(tests.this, Dialog.class);
                        startActivity(intent);
                    }
                    WcreatRandom();
                    TcreatRandom();

                }
            });

            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Bu[3] == Tx)
                        Toast.makeText(getApplicationContext(), "你答对了！！" + button4.getText(), Toast.LENGTH_SHORT).show();
                    else {
                        Data.setLearningfinish(1); //1
                        Intent intent = new Intent();
                        intent.putExtra("number", Tx);
                        intent.putExtra("truewords",mDate.get(Tx-1).get("Words").toString());
                        intent.putExtra("trueexplain",mDate.get(Tx-1).get("Explain").toString());
                        intent.setClass(tests.this, ErrorLearing.class);
                        startActivity(intent);
                    }
                    Link();
                    buttonassign();
                    if (wordsOver() == 1) {
                        Wrunflag = 1;
                        Intent intent = new Intent();
                        intent.setClass(tests.this, Dialog.class);
                        startActivity(intent);
                    }
                    WcreatRandom();
                    TcreatRandom();

                }
            });

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(tests.this, Dialog.class);
                    startActivity(intent);
                }
            });
        }


    }

    private int[] WcreatRandom(){
        int L = mDate.size();
        int number = mDate.size(); //控制随机数产生的范围
        List arr = new ArrayList();
        for(int i = 0;i < L;i++) arr.add(i+1);//为ArrayList添加元素
        if(Wrunflag!=0){
            arr.remove(WWrandom[Wrunflag-1]-1);
            number--;
            L = mDate.size()-1;
        }
        for(int j = 0;j < L;j++){
            int index = (int)(Math.random()*number);//产生一个随机数索引
            Wrandom[j] = (int)arr.get(index);
            arr.remove(index);//移除已取过的数
            number--;
        }
        Wrunflag++;
        return Wrandom;
    }

    private int[] TcreatRandom(){
        int number = 4; //控制随机数产生的范围
        List arr = new ArrayList();
        for(int i = 0;i < 4;i++) arr.add(i+1);//为ArrayList添加元素
        for(int j = 0;j < Trandom.length;j++){
            int index = (int)(Math.random()*number);//产生一个随机数索引
            Trandom[j] =  (int)arr.get(index);
            arr.remove(index);//移除已取过的数
            number--;

        }
        return Trandom;
    }

/*****************链接两组随机数******************/
    private void Link(){
        Tx = WWrandom[Wrunflag-1];
        for(int i = 0;i < 4;i++){
            if(Trandom[i] == 1) Bu[i] = WWrandom[Wrunflag-1];
            if(Trandom[i] == 2) Bu[i] = Wrandom[0];
            if(Trandom[i] == 3) Bu[i] = Wrandom[1];
            if(Trandom[i] == 4) Bu[i] = Wrandom[2];
        }
    }
/*****************判断单词表是否遍历完**************/
    private int wordsOver(){
        int flag;
        if(Wrunflag == (mDate.size()-1)) flag = 1;
        else flag = 0;
        return flag;
    }
/*****************判断单词表内单词个数是否达到4个**************/
    private boolean checkwordsnumber(){
        if(mDate.size() < 4)
            return false;
        else
            return true;
    }
/*****************Button赋值**************/
    private void buttonassign(){
        words.setText(mDate.get(Tx - 1).get("Words").toString());
        button1.setText(mDate.get(Bu[0] - 1).get("Explain").toString());
        button2.setText(mDate.get(Bu[1] - 1).get("Explain").toString());
        button3.setText(mDate.get(Bu[2] - 1).get("Explain").toString());
        button4.setText(mDate.get(Bu[3] - 1).get("Explain").toString());
    }


    /**得到数据*/
    public ArrayList<HashMap<String, Object>> getDate() {
        ArrayList<HashMap<String, Object>> mylist = new ArrayList<HashMap<String, Object>>();
        mylist = StringToList(readFiles());
        return mylist;
    }
    /** 保存文件内容*/
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
    /** 读取文件内容*/
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
    /** 将ArrayList转成String字符串*/
    public String ListToString(ArrayList<HashMap<String, Object>> mdate){
        String context = "";// null时 会打印出null
        for(int i = 0;i < mdate.size();i++){
            context = context+mdate.get(i).get("Words").toString()+":"+mdate.get(i).get("Explain").toString()+":";
        }
        return context;
    }
    /** 将String字符串转成ArrayList*/
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

//4.28
//实现遍历单词表，即乱序测试所有单词。
//选项中还会出现重复单词！？？
//4.28.18.53
//乱序测试所有单词后弹出窗口问是否继续√
