package com.example.rentsl.mw;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class TestSelectTable extends AppCompatActivity {
    public static final String FILENAME = "SET.set";
    ArrayList<HashMap<String, Object>> mDate;
    ArrayList<HashMap<String, Object>> firstmDate;
    ListView mwordstable;
    MyAdapter myAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_select_table);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mwordstable = (ListView)findViewById(R.id.test_select_list);
        firstmDate = getListDate();//初始化用数据
        if(readFiles() == null)  writeFiles(ListToString(firstmDate));
        mDate = getDate();//新的数据
        MyAdapter myAdapter = new MyAdapter(this);
        mwordstable.setAdapter(myAdapter);
    }

    public ArrayList<HashMap<String,Object>> getListDate(){
        ArrayList<HashMap<String, Object>> mmylist = new ArrayList<HashMap<String, Object>>();
        HashMap<String,Object> map1 = new HashMap<String, Object>();
        map1.put("List","五十音图");
        map1.put("Filename","SE.set");
        mmylist.add(map1);
        return mmylist;
    }
    //适配器
    public class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater; //得到一个LayoutInfalter对象用来导入布局

        /*** 构造函数**/
        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount(){
            return mDate.size();//返回数组的长度
        }
        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent){
            int type = getItemViewType(position);
            ViewHolder holder = null;
            //观察convertView随ListView滚动情况
            if (convertView == null) {
                        holder = new ViewHolder();
                        convertView = mInflater.inflate(R.layout.test_select_table_item, null);
                        /**得到各个控件的对象*/
                        holder.wordslist = (TextView) convertView.findViewById(R.id.test_table_item_text);
                        holder.wordslist.setText(mDate.get(position).get("List").toString());
                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent();
                                intent.putExtra("tablename",mDate.get(position).get("Filename").toString());
                                intent.setClass(TestSelectTable.this, tests.class);
                                startActivity(intent);
                            }
                        });
                        convertView.setTag(holder);//绑定ViewHolder对象
                }

            else {
                        holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
                        holder.wordslist.setText(mDate.get(position).get("List").toString());
                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent();
                                intent.putExtra("tablename",mDate.get(position).get("Filename").toString());
                                intent.setClass(TestSelectTable.this, tests.class);
                                startActivity(intent);
                            }
                        });
                }
               return convertView;
            }
        /*** 存放控件1**/
        public final class ViewHolder {
            public TextView wordslist;
        }

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


}
