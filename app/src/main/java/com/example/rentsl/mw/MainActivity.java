package com.example.rentsl.mw;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static final String FILENAME = "SE.set";
    ArrayList<HashMap<String, Object>> mDate;

    String[] mywords = new String[]
            {"ア","イ","ウ","エ","オ",
                    "カ","キ","ク","ケ","コ",
                    "サ","シ","ス","セ","ソ",
                    "タ","チ","ツ","テ","ト",
                    "ナ","ニ","ヌ","ネ","ノ",
                    "ハ","ヒ","フ","ヘ","ホ",
                    "マ","ミ","ム","メ","モ",
                    "ヤ","ユ","ヨ",
                    "ラ","リ","ル","レ","ロ",
                    "ワ","ヲ"};
    String[] myexplain = new String[]
            {"あ","い","う","え","お",
                    "か","き","く","け","こ",
                    "さ","し","す","せ","そ",
                    "た","ち","つ","て","と",
                    "な","に","ぬ","ね","の",
                    "は","ひ","ふ","へ","ほ",
                    "ま","み","む","め","も",
                    "や","ゆ","よ",
                    "ら","り","る","れ","ろ",
                    "わ","を"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDate = getDate();
        if(readFiles() == null)  writeFiles(ListToString(mDate));


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton buttonstart = (ImageButton) findViewById(R.id.Button_start);
        ImageButton buttontable = (ImageButton) findViewById(R.id.Button_table);


        buttonstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this, TestSelectTable.class);
                startActivity(intent);

            }
        });

        buttontable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this, SelectTable.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this, wordslist.class);
                startActivity(intent);
            }
        });

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


    // 得到数据
    public ArrayList<HashMap<String, Object>> getDate(){
        ArrayList<HashMap<String, Object>> mylist = new ArrayList<HashMap<String, Object>>();

        for(int i = 0;i < 45;i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("Words", mywords[i]);
            map.put("Explain", myexplain[i]);
            mylist.add(map);
        }

        return mylist;
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




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
//4.29.12.45
//利用SharedPreferences保存单词和解释，供其他activity使用和修改。
//成功打印出了简单的单词表
//5.1.20.27
//实现了向一张列表中添加单词
//5.6.21.03
//将单词有关的数据全部转移到文件来储存
//放弃SharedPreferences
//解决若干bug
//实现删除单词

//存在的bug
//输入不完整就提交的话程序会崩溃
//单词列表重名问题（会共享一个文件）
//单词少于4个 需要：不能进行测试