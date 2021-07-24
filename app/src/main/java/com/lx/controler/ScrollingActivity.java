package com.lx.controler;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;


public class ScrollingActivity extends AppCompatActivity {
    public static Context context;//上下文
    public static final String ACTION_UPDATE_UI = "action.updateUI";
    UpdateUIBroadcastReceiver broadcastReceiver;//注册广播

    Toolbar toolbar;
    //最外层线性布局
    private LinearLayout contentLinear1;
    // “+”按钮控件List
    private LinkedList<ImageButton> listIBTNAdd;
    // “+”按钮ID索引
    private int btnIDIndex = 1000;
    // “-”按钮控件List
    private LinkedList<ImageButton> listIBTNDel;
    // 进度条控件List
    private LinkedList<TemperatureProgress> listProgress;

    TemperatureProgress circleProgress_Degree1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        context = this;

        //初始化控件
        initCtrl();

         //动态注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_UPDATE_UI);
        broadcastReceiver = new UpdateUIBroadcastReceiver();
        registerReceiver(broadcastReceiver, filter);

        MyMqttService.startService(this); //开启Mqtt服务
    }

    /*
    初始化控件
     */
    private void initCtrl(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        contentLinear1 = (LinearLayout)findViewById(R.id.contentLinear1);
        listIBTNAdd = new LinkedList<ImageButton>();
        listIBTNDel = new LinkedList<ImageButton>();
        listProgress = new LinkedList<TemperatureProgress>();

        circleProgress_Degree1 = (TemperatureProgress)findViewById(R.id.circle_progress1);
        circleProgress_Degree1.config("temp1","温度1","°C",60,0);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "发送指令", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //MyMqttService.publish("[{\"tag\":\"LockOrder\",\"text\":\"1\"},{\"tag\":\"DeviceOrder\",\"text\":\"1\"}]");

                MyMqttService.publish(string2GBK("哈哈AB"));

                //Uri uri = Uri.parse("http://sj18636631.51mypc.cn:43123/view.html?id=6066c5c2961b50210040accd");
                //Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                ///startActivity(intent);
            }
        });

        ImageButton ibnAdd1 = (ImageButton)this.findViewById(R.id.ibn_add1);
        ibnAdd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContent(view);
            }
        });
        listIBTNAdd.add(ibnAdd1);
        listIBTNDel.add(null);
        listProgress.add(circleProgress_Degree1);
    }

    public static byte[] string2GBK(String str){
        try {
            byte[] a = str.getBytes("GBK");
            String ab = new String(a,"GBK");
            Log.i("DebugTag", ab);
            int i = 0;
            for(;i<a.length;i++){
                Log.i("DebugTag", "发消息"+ i + ":" +a[i]);
            }
            Log.i("DebugTag", "消息:"+ab);
            return a;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static String byte2hex(byte [] buffer){
        String h = "";
        for(int i = 0; i < buffer.length; i++){
            String temp = Integer.toHexString(buffer[i] & 0xFF);
            if(temp.length() == 1){
                temp = "0" + temp;
            }
            h = h + temp;
        }
        return h;
    }

    /**
     * dp转px
     *
     * @param context
     * @param dpVal
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * 添加控件
     * @param v
     */
    private void addContent(View v){
        if(v == null){
            return;
        }
        // 判断第几个“+”按钮触发了事件
        int iIndex = -1;
        for (int i = 0; i < listIBTNAdd.size(); i++) {
            if (listIBTNAdd.get(i) == v) {
                iIndex = i;
                break;
            }
        }
        if (iIndex >= 0) {
            // 控件实际添加位置为当前触发位置点下一位
            iIndex += 1;

            // 开始添加控件

            // 1.创建外围LinearLayout控件
            LinearLayout layout = new LinearLayout(ScrollingActivity.this);
            //LinearLayout控件参数
            LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layout.setLayoutParams(linearLayoutParams);
            // 设置属性
            layout.setPadding(dp2px(context,5), dp2px(context,5),dp2px(context,5),dp2px(context,5));
            layout.setOrientation(LinearLayout.HORIZONTAL);

            // 2.创建内部TemperatureProgress弧形进度条控件
            TemperatureProgress circleProgress_Degree = new TemperatureProgress(ScrollingActivity.this);

            LinearLayout.LayoutParams etParam = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            circleProgress_Degree.setLayoutParams(etParam);
            // 设置属性
            circleProgress_Degree.config("temp"+iIndex,"温度"+iIndex,"°C",60,0);
            // 将circleProgress_Degree放到LinearLayout里
            layout.addView(circleProgress_Degree);
            listProgress.add(iIndex,circleProgress_Degree);

            // 3.创建包含右侧控件的外围控件RelativeLayout
            RelativeLayout rlBtn = new RelativeLayout(ScrollingActivity.this);
            RelativeLayout.LayoutParams rlParam = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            rlBtn.setLayoutParams(rlParam);
            rlBtn.setPadding(dp2px(context,5), dp2px(context,5),dp2px(context,5),dp2px(context,5));

            //4.创建预警值预警值框
            TextView txtWarn = new TextView(ScrollingActivity.this);
            txtWarn.setText("预警值:");
            RelativeLayout.LayoutParams txtWarnParam = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            //靠上放置
            txtWarnParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            txtWarnParam.setMargins(0,dp2px(context,20),0,0);
            txtWarn.setLayoutParams(txtWarnParam);
            rlBtn.addView(txtWarn);

            //5.创建报警值预警值框
            TextView txtAlarm = new TextView(ScrollingActivity.this);
            txtAlarm.setText("报警值:");
            RelativeLayout.LayoutParams txtAlarmParam = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            //靠上放置
            txtAlarmParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            txtWarnParam.setMargins(0,0,0,dp2px(context,20));
            txtAlarm.setLayoutParams(txtAlarmParam);
            rlBtn.addView(txtAlarm);


            // 4.创建“-”按钮
            ImageButton btnDelete = new ImageButton(ScrollingActivity.this);
            btnDelete.setBackgroundResource(android.R.drawable.ic_menu_delete);

            RelativeLayout.LayoutParams btnDeleteAddParam = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            btnDeleteAddParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            btnDeleteAddParam.addRule(RelativeLayout.ALIGN_PARENT_END);

            btnDelete.setId(btnIDIndex);
            btnDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    deleteContent(v);
                }
            });
            // 将“-”按钮放到RelativeLayout里
            rlBtn.addView(btnDelete, btnDeleteAddParam);
            listIBTNDel.add(iIndex, btnDelete);

            // 4.创建“+”按钮
            ImageButton btnAdd = new ImageButton(ScrollingActivity.this);
            RelativeLayout.LayoutParams btnAddParam = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            // 靠右放置
            btnAddParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            btnAddParam.setMargins(0, 0, dp2px(context,12), 0);
            // “”按钮放在“”按钮左侧
            btnAddParam.addRule(RelativeLayout.LEFT_OF, btnIDIndex);
            btnAdd.setLayoutParams(btnAddParam);
            // 设置属性
            btnAdd.setBackgroundResource(android.R.drawable.ic_menu_add);

            // 设置点击操作
            btnAdd.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    addContent(v);
                }
            });
            // 将“+”按钮放到RelativeLayout里
            rlBtn.addView(btnAdd);
            listIBTNAdd.add(iIndex, btnAdd);


            // 6.将RelativeLayout放到LinearLayout里
            layout.addView(rlBtn);

            // 7.将layout同它内部的所有控件加到最外围的llContentView容器里
            contentLinear1.addView(layout, iIndex);

            btnIDIndex++;
        }
    }
    /**
    *删除控件
     */
    void deleteContent(View v){
        Toast.makeText(context, "删除控件", Toast.LENGTH_LONG).show();
        if(v == null){return;}
        // 判断第几个“-”按钮触发了事件
        int iIndex = -1;
        for (int i = 0; i < listIBTNDel.size(); i++) {
            if (listIBTNDel.get(i) == v) {
                iIndex = i;
                break;
            }
        }
        if (iIndex >= 0) {
            listIBTNAdd.remove(iIndex);
            listIBTNDel.remove(iIndex);

            // 从外围contentLinear1容器里删除第iIndex控件
            contentLinear1.removeViewAt(iIndex);
        }
    }

    /**
     * 定义广播接收器（内部类）
     */
    private class UpdateUIBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String json = intent.getExtras().getString("json");
            Map<String, Double> map = Json2Hashmap.getMap(json);//保存读取数据keyValueMap
            for (int i = 0; i < listProgress.size(); i++) {
                listProgress.get(i).setValueByMap(map);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_setting1) {
            Log.d("action_set","set1");
            return true;
        }
        if (id == R.id.action_setting2) {
            Log.d("action_set","set2");
            return true;
        }
        if (id == R.id.action_setting3) {
            Log.d("action_set","set3");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
