package com.example.rentsl.mw;

import android.app.Activity;
 import android.os.Bundle;
 import android.view.MotionEvent;
 import android.view.View;
 import android.view.View.OnClickListener;
 import android.widget.LinearLayout;
 import android.widget.Toast;



 public class Dialog extends Activity {

     private LinearLayout layout;
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_dialog);
         layout=(LinearLayout)findViewById(R.id.dialog_layout);
         layout.setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View v) {
                 // TODO Auto-generated method stub
                 Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！", Toast.LENGTH_SHORT).show();
             }
         });
     }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        finish();
        return true;
    }

     public void exitbutton1(View v) {

         this.finish();
     }

     public void exitbutton0(View v) {

         this.finish();
         tests.instance.finish();//tests 这个Activity
     }
 }
//4.28
//添加 作为伪弹框
//4.28.17.49
//        Activity.finish()
//        Call this when your activity is done and should be closed.
//        在你的activity动作完成的时候，或者Activity需要关闭的时候，调用此方法。
//        当你调用此方法的时候，系统只是将最上面的Activity移出了栈，并没有及时的调用onDestory（）方法，其占用的资源也没有被及时释放。
//        因为移出了栈，所以当你点击手机上面的“back”按键的时候，也不会找到这个Activity。
//
//        Activity.onDestory()
//        the system is temporarily destroying this instance of the activity to save space.
//        系统销毁了这个Activity的实例在内存中占据的空间。
//        在Activity的生命周期中，onDestory()方法是他生命的最后一步，资源空间什么的都没有咯~~。当重新进入此Activity的时候，必须重新创建，执行onCreate()方法。
//
//        System.exit(0)
//        这玩意是退出整个应用程序的，是针对整个Application的。将整个进程直接KO掉。