package com.example.rentsl.mw;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class CreateTableDialog extends Activity {
    public static final String FILENAME = "SET.set";
    ArrayList<HashMap<String, Object>> mDate;
    private LinearLayout layout;
    public EditText editText;
    public Button cancel;
    public Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_table_dialog);
        editText = (EditText)findViewById(R.id.edittext_id);
        create = (Button)findViewById(R.id.exitBtn1);
        cancel = (Button)findViewById(R.id.exitBtn0);

        mDate = getDate();

        layout=(LinearLayout)findViewById(R.id.dialog_layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！", Toast.LENGTH_SHORT).show();
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<HashMap<String, Object>> addwords = new ArrayList<HashMap<String, Object>>();
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("List", editText.getText().toString());
                map.put("Filename", editText.getText().toString() + ".set");
                addwords.add(map);
                writeFiles(ListToString(addwords));
                SelectTable.instance.finish();
                finish();
                Intent intent = new Intent();
                intent.setClass(SelectTable.instance, SelectTable.class); //关于刷新还有很多疑问
                startActivity(intent);

            }
        });


    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        finish();
        return true;
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
                    Context.MODE_APPEND);//MODE_APPEND表示追加模式保存数据（Context.MODE_PRIVATE覆盖式写入方式）
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
            context = context+mdate.get(i).get("List").toString()+":"+mdate.get(i).get("Filename").toString()+":";
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
                map.put("List",a[i]);
                map.put("Filename",a[i+1]);
                mylist.add(map);
            }
        }
        return mylist;
    }


    public void exitbutton1(View v) {

        this.finish();
    }

    public void exitbutton0(View v) {

        this.finish();
        tests.instance.finish();//tests 这个Activity
    }


}
