package com.example.rentsl.mw;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class wordslist extends AppCompatActivity {
    public static wordslist instance = null;
    public String FILENAME = "SE.set";
    public String name = null;
    public String Cname = null;
    MyAdapter mAdapter = null;
    private ArrayList<HashMap<String,Object>> mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordslist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        instance = this;
        Intent intent = getIntent();
        name = intent.getStringExtra("listname");
        Cname = intent.getStringExtra("CWLDtoWLname");//从CreateWordsListDialog传来的值
        if(name != null) FILENAME = name;
        if(Cname != null) FILENAME = Cname;


        ListView wordslist = (ListView) findViewById(R.id.Wordslist);
        mData = getDate();

        MyAdapter mAdapter = new MyAdapter(this);
        wordslist.setAdapter(mAdapter);
        //mAdapter.notifyDataSetChanged();
    }

    //得到数据
    public ArrayList<HashMap<String, Object>> getDate() {
        ArrayList<HashMap<String, Object>> mylist = new ArrayList<HashMap<String, Object>>();
        mylist = StringToList(readFiles());
        return mylist;
    }

//自定义适配器
    private class MyAdapter extends BaseAdapter {

        public static final int WORDSTABLE = 0;// 两种不同的布局
        public static final int CREATTABLE = 1;

        private LayoutInflater mInflater; //得到一个LayoutInfalter对象用来导入布局

        /*** 构造函数**/
        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mData.size()+1;//返回数组的长度
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            int type = getItemViewType(position);
            CViewHolder holder = null;
            CViewCreateHolder createholder = null;
            if (convertView == null) {
                switch (type) {
                    case WORDSTABLE:
                        holder = new CViewHolder();
                        convertView = mInflater.inflate(R.layout.words_list_item, null);
                        /**得到各个控件的对象*/
                        holder.Cwords = (TextView) convertView.findViewById(R.id.Textwords);
                        holder.Cexplain = (TextView) convertView.findViewById(R.id.Texttranslate);
                        holder.Cbt = (Button) convertView.findViewById(R.id.textremovebt);
                        holder.Cwords.setText(mData.get(position).get("Words").toString());
                        holder.Cexplain.setText(mData.get(position).get("Explain").toString());
                        holder.Cbt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mData.remove(position);
                                MyAdapter.this.notifyDataSetChanged();
                                writeFiles(ListToString(mData));
                            }
                        });
                        convertView.setTag(holder);//绑定ViewHolder对象
                        break;
                    case CREATTABLE:
                        createholder = new CViewCreateHolder();
                        convertView = mInflater.inflate(R.layout.words_item_create, null);
                        /**得到各个控件的对象*/
                        createholder.Ccreate = (Button) convertView.findViewById(R.id.wordscreate_id);
                        convertView.setTag(createholder);//绑定ViewHolder对象

                        createholder.Ccreate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent();
                                intent.putExtra("fname",FILENAME);//传给CreateWordsListDialog
                                intent.setClass(wordslist.this, CreateWordsListDialog.class);
                                startActivity(intent);
                            }
                        });
                        break;
                    default:
                        break;
                }

            }
            else {
                switch (type) {
                    case WORDSTABLE:
                        holder = (CViewHolder) convertView.getTag();//取出ViewHolder对象
                        holder.Cwords.setText(mData.get(position).get("Words").toString());
                        holder.Cexplain.setText(mData.get(position).get("Explain").toString());

                        holder.Cbt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mData.remove(position);
                                MyAdapter.this.notifyDataSetChanged();
                                writeFiles(ListToString(mData));
                            }
                        });
                        break;
                    case CREATTABLE:
                        createholder = (CViewCreateHolder) convertView.getTag();//取出ViewHolder对象
                        createholder.Ccreate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent=new Intent();
                                intent.putExtra("fname",FILENAME);//传给CreateWordsListDialog
                                intent.setClass(wordslist.this, CreateWordsListDialog.class);
                                startActivity(intent);
                            }
                        });
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
            if(position == mData.size()) type = CREATTABLE;
            else type = WORDSTABLE;
            return type;
        }

        /*** 返回所有的layout的数量***/
        @Override
        public int getViewTypeCount() {
            return 2;
        }
        /*** 存放控件**/
        public final class CViewHolder {
            public TextView Cwords;
            public TextView Cexplain;
            public Button Cbt;
        }
        /*** 存放控件2**/
        public final class CViewCreateHolder {
            public Button Ccreate;
    }

}

//    public void showInfo(final int position) {
//        new AlertDialog.Builder(this).setTitle("我的提示").setMessage("确定要删除吗？")
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        getDate().remove(position);
//                        // 通过程序我们知道删除了，但是怎么刷新ListView呢？
//                        // 只需要重新设置一下adapter
//                        //mAdapter.notifyDataSetChanged();
//                    }
//                }).show();
//    }


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
