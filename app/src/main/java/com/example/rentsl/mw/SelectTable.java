package com.example.rentsl.mw;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SelectTable extends AppCompatActivity {
    public static SelectTable instance = null;
    public static final String FILENAME = "SET.set";
    ArrayList<HashMap<String, Object>> mDate;
    ListView mwordstablelist;
    MyAdapter myAdapter = null;
    ArrayList<HashMap<String,Object>> mListData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_table);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mwordstablelist = (ListView) findViewById(R.id.wordstablelist_id);
        instance = this;
        mListData = getListDate();//初始化用数据

        if(readFiles() == null)  writeFiles(ListToString(mListData));
        mDate = getDate();//新的数据
        MyAdapter myAdapter = new MyAdapter(this);
        mwordstablelist.setAdapter(myAdapter);

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
    public class MyAdapter extends BaseAdapter{
        public static final int WORDSTABLE = 0;// 两种不同的布局
        public static final int CREATTABLE = 1;

        private LayoutInflater mInflater; //得到一个LayoutInfalter对象用来导入布局

        /*** 构造函数**/
        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount(){
            return mDate.size()+1;//返回数组的长度
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
            ViewCreateHolder createholder = null;
            //观察convertView随ListView滚动情况
            if (convertView == null) {
                switch (type) {
                    case WORDSTABLE:
                        holder = new ViewHolder();
                        convertView = mInflater.inflate(R.layout.item_home, null);
                        /**得到各个控件的对象*/
                        holder.wordslist = (TextView) convertView.findViewById(R.id.id_num);
                        holder.button = (Button)convertView.findViewById(R.id.button_id);
                        convertView.setTag(holder);//绑定ViewHolder对象
                        break;
                    case CREATTABLE:
                        createholder = new ViewCreateHolder();
                        convertView = mInflater.inflate(R.layout.words_table_iteam_create, null);
                        /**得到各个控件的对象*/
                        createholder.create = (Button) convertView.findViewById(R.id.create_id);
                        convertView.setTag(createholder);//绑定ViewHolder对象
                        break;
                    default:
                        break;
                }
            }
            else {
                switch (type) {
                    case WORDSTABLE:
                        holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
                        holder.wordslist.setText(mDate.get(position).get("List").toString());
                        holder.button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mDate.remove(position);
                                MyAdapter.this.notifyDataSetChanged();
                                writeFiles(ListToString(mDate));
                            }
                        });
                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent();
                                intent.putExtra("listname",mDate.get(position).get("Filename").toString());
                                intent.setClass(SelectTable.this, wordslist.class);
                                startActivity(intent);
                            }
                        });
                        break;
                    case CREATTABLE:
                        createholder = (ViewCreateHolder) convertView.getTag();//取出ViewHolder对象
                        createholder.create.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent();
                                intent.setClass(SelectTable.this, CreateTableDialog.class);
                                startActivity(intent);
                            }
                        });
                        //createholder.create.setText("hello");
                        break;
                    default:
                        break;
                }
            }
            return convertView;
        }
        /*** 根据数据源的position返回需要显示的的layout的type
         ** type的值必须从0开始** */
        @Override
        public int getItemViewType(int position) {
            int type = 0;
            if(position == mDate.size()) type = CREATTABLE;
            else type = WORDSTABLE;
            return type;
        }

        /*** 返回所有的layout的数量***/
        @Override
        public int getViewTypeCount() {
            return 2;
        }

        /*** 存放控件1**/
        public final class ViewHolder {
            public TextView wordslist;
            public Button button;
        }
        /*** 存放控件2**/
        public final class ViewCreateHolder {
            public Button create;
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

