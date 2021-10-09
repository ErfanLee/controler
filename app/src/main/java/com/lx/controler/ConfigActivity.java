package com.lx.controler;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

/**
 * Created by pc02 on 2021/9/14.
 */

public class ConfigActivity extends AppCompatActivity{

    public static Context context;//上下文

    /*需要设置的物联网关参数*/
    int WorkMode;
    int ConnectMode;
    int MasterSlaveMode;
    int FunctionMode;
    int TransferMode;
    int DisplayMode;
    int GroupNumber;
    int ConnectTarget;
    int WifiMode;
    int TransferProtocol;
    int DataTissue;

    int SendToInternetTime;
    int GetMegTime;
    int SleepTime;
    int GroupID;
    int UnitID;
    String WifiName;
    String WifiPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gate_way_congfig);
        context = this;

        MyMqttService.startService(this); //开启Mqtt服务
        initCtrl();

    }

    private void initCtrl() {

        //工作模式
        final Spinner spinner_work_mode           = findViewById(R.id.spinner_work_mode);
        //连接模式
        final Spinner spinner_connect_mode        = findViewById(R.id.spinner_connect_mode);
        //主从模式
        final Spinner spinner_master_slave_mode   = findViewById(R.id.spinner_master_slave_mode);
        //功能模式
        final Spinner spinner_function_mode       = findViewById(R.id.spinner_function_mode);
        //数据传输模式
        final Spinner spinner_transfer_mode       = findViewById(R.id.spinner_transfer_mode);
        //显卡驱动卡
        final Spinner spinner_display_mode        = findViewById(R.id.spinner_display_mode);
        //分组疏密
        final Spinner spinner_group_number        = findViewById(R.id.spinner_group_number);
        //上云目的地址
        final Spinner spinner_connect_target      = findViewById(R.id.spinner_connect_target);
        //wifi连接模式
        final Spinner spinner_wifi_mode           = findViewById(R.id.spinner_wifi_mode);
        //wifi名称
        final EditText edit_wifi_name = findViewById(R.id.edit_wifi_name);
        //wifi密码
        final EditText edit_wifi_password = findViewById(R.id.edit_wifi_password);
        //数据传输协议
        final Spinner spinner_transfer_protocol   = findViewById(R.id.spinner_transfer_protocol);
        //数据组合协议
        final Spinner spinner_data_tissue         = findViewById(R.id.spinner_data_tissue);
        //屏显间隔时间
        //上云间隔时间
        //采集信息间隔

        //波特率设置

        //休眠时间设定
        //分组ID设置
        //用户ID设置
        //预警值
        //报警值
        //产品名称设置
        //位置信息
        //IP地址A设置
        //IP地址B设置
        //产品密钥设置
        //设备名称设置
        //设备秘钥设置
        //发布名称名称
        //订阅消息名称
        //询问模块编号
        //物联信息采集

        FloatingActionButton fab_send  =  findViewById(R.id.fab_send);
        fab_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "fab_send", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                WorkMode        = (int)spinner_work_mode.getSelectedItemId()+1;
                ConnectMode     = (int)spinner_connect_mode.getSelectedItemId()+1;
                MasterSlaveMode = (int)spinner_master_slave_mode.getSelectedItemId()+1;
                FunctionMode    = (int)spinner_function_mode.getSelectedItemId()+1;
                TransferMode    = (int)spinner_transfer_mode.getSelectedItemId()+1;
                DisplayMode     = (int)spinner_display_mode.getSelectedItemId()+1;
                GroupNumber     = (int)spinner_group_number.getSelectedItemId()+1;
                ConnectTarget   = (int)spinner_connect_target.getSelectedItemId()+1;
                WifiMode        = (int)spinner_wifi_mode.getSelectedItemId()+1;
                TransferProtocol= (int)spinner_transfer_protocol.getSelectedItemId()+1;
                DataTissue      = (int)spinner_data_tissue.getSelectedItemId()+1;

                WifiName        = edit_wifi_name.getText().toString();
                WifiPassword    = edit_wifi_password.getText().toString();

                AT_Utils.WorkMode(WorkMode);
                AT_Utils.ConnectMode(ConnectMode);
                AT_Utils.MasterSlaveMode(MasterSlaveMode);
                AT_Utils.FunctionMode(FunctionMode);
                AT_Utils.TransferMode(TransferMode);
                AT_Utils.DisplayMode(DisplayMode);
                AT_Utils.GroupNumber(GroupNumber);
                AT_Utils.ConnectTarget(ConnectTarget);
                AT_Utils.WifiMode(WifiMode);
                AT_Utils.TransferProtocol(TransferProtocol);
                AT_Utils.DataTissue(DataTissue);
                AT_Utils.WifiName(WifiName);
                AT_Utils.WifiPassword(WifiPassword);
            }
        });

        Switch switch_connect_mode = findViewById(R.id.switch_connect_mode);
        switch_connect_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Toast.makeText(ConfigActivity.this,
                            "WIFI模式",
                            Toast.LENGTH_SHORT).show();
                    AT_Utils.isMQTT = false;
                } else {
                    Toast.makeText(ConfigActivity.this,
                            "MQTT模式",
                            Toast.LENGTH_SHORT).show();
                    AT_Utils.isMQTT = true;
                }
            }
        });






    }
}
