package com.example.rentsl.mw;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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

public class CreateWordsListDialog extends Activity {

    public String FILENAME = "SE.set";
    private LinearLayout layout;
    ArrayList<HashMap<String, Object>> mDate;
    public EditText editwords;
    public EditText editexplain;
    public Button submit;
    public String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_words_list_dialog);
        editwords = (EditText) findViewById(R.id.CEditWords);
        editexplain = (EditText) findViewById(R.id.CEditExplain);
        submit = (Button) findViewById(R.id.CSubmit);

        layout=(LinearLayout)findViewById(R.id.ldialog_layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        name = intent.getStringExtra("fname");
        FILENAME = name;

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDate = getDate();
                String str1 = editwords.getText().toString().trim();
                String str2 = editexplain.getText().toString().trim();
                /**判断是否输入完整*/
                if(TextUtils.isEmpty(str1) || TextUtils.isEmpty(str2)){
                    Toast.makeText(getApplicationContext(),"输入有误",Toast.LENGTH_SHORT).show();
                }
                else {
                    writeFiles(ListToString(mDate));
                    wordslist.instance.finish();
                    finish();
                    Intent intent = new Intent();
                    intent.putExtra("CWLDtoWLname", FILENAME);//传到wordslist的值
                    intent.setClass(wordslist.instance, wordslist.class); //关于刷新还有很多疑问
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        finish();
        return true;
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
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("Words", editwords.getText().toString());
        map.put("Explain",editexplain.getText().toString());
        mylist.add(map);

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
}
