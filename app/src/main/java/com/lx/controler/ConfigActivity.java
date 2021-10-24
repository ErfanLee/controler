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
    String Ip_addr;
    int Ip_port;

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
    int DisplayTime;
    int SendToInternetTime;
    int GetMegTime;
    int Usart;
    int Bound;
    int Byte_length;
    int Stop_bit;
    int Check_bit;
    int SleepTime;
    int GroupID;
    int UnitID;
    int Project_warn;
    int Warn_value;
    int Project_alarm;
    int Alarm_value;
    String WifiName;
    String WifiPassword;
    String ProduceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gate_way_congfig);
        context = this;

        MyMqttService.startService(this); //开启Mqtt服务
        initCtrl();

    }

    private void initCtrl() {

        //设置IP地址
        final IPEditText ip_edit_addr = findViewById(R.id.ip_edit_addr);
        final EditText   edit_ip_port = findViewById(R.id.edit_ip_port);
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
        final EditText edit_display_time          = findViewById(R.id.edit_display_time);
        //上云间隔时间
        final EditText edit_internet_time         = findViewById(R.id.edit_internet_time);
        //采集信息间隔
        final EditText edit_message_time          = findViewById(R.id.edit_message_time);
        //波特率设置
        final Spinner spinner_usart               = findViewById(R.id.spinner_usart);
        final Spinner spinner_bound               = findViewById(R.id.spinner_bound);
        final Spinner spinner_byte_length         = findViewById(R.id.spinner_byte_length);
        final Spinner spinner_stop_bit            = findViewById(R.id.spinner_stop_bit);
        final Spinner spinner_check_bit           = findViewById(R.id.spinner_check_bit);
        //休眠时间设定
        final EditText edit_sleep_time            = findViewById(R.id.edit_sleep_time);
        //分组ID设置
        final EditText edit_group_id              = findViewById(R.id.edit_group_id);
        //用户ID设置
        final EditText edit_user_id               = findViewById(R.id.edit_user_id);
        //预警值
        final Spinner  spinner_project_warn       = findViewById(R.id.spinner_project_warn);
        final EditText edit_warn                  = findViewById(R.id.edit_warn_value);
        //报警值
        final Spinner  spinner_project_alarm      = findViewById(R.id.spinner_project_alarm);
        final EditText edit_alarm                 = findViewById(R.id.edit_alarm_value);
        //产品名称设置
        final EditText edit_produce_name          = findViewById(R.id.edit_produce_name);
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


                Ip_addr             = ip_edit_addr.getText();
                Ip_port             = Integer.parseInt(edit_ip_port.getText().toString());

                WorkMode            = (int)spinner_work_mode.getSelectedItemId()+1;
                ConnectMode         = (int)spinner_connect_mode.getSelectedItemId()+1;
                MasterSlaveMode     = (int)spinner_master_slave_mode.getSelectedItemId()+1;
                FunctionMode        = (int)spinner_function_mode.getSelectedItemId()+1;
                TransferMode        = (int)spinner_transfer_mode.getSelectedItemId()+1;
                DisplayMode         = (int)spinner_display_mode.getSelectedItemId()+1;
                GroupNumber         = (int)spinner_group_number.getSelectedItemId()+1;
                ConnectTarget       = (int)spinner_connect_target.getSelectedItemId()+1;
                WifiMode            = (int)spinner_wifi_mode.getSelectedItemId()+1;
                WifiName            = edit_wifi_name.getText().toString();
                WifiPassword        = edit_wifi_password.getText().toString();
                TransferProtocol    = (int)spinner_transfer_protocol.getSelectedItemId()+1;
                DataTissue          = (int)spinner_data_tissue.getSelectedItemId()+1;
                DisplayTime         = Integer.parseInt(edit_display_time.getText().toString());
                SendToInternetTime  = Integer.parseInt(edit_internet_time.getText().toString());
                GetMegTime          = Integer.parseInt(edit_message_time.getText().toString());
                Usart               = (int)spinner_usart.getSelectedItemId()+1;
                Bound               = (int)spinner_bound.getSelectedItemId();
                Byte_length         = (int)spinner_byte_length.getSelectedItemId()+5;
                Stop_bit            = (int)spinner_stop_bit.getSelectedItemId()+1;
                Check_bit           = (int)spinner_check_bit.getSelectedItemId()+1;
                SleepTime           = Integer.parseInt(edit_sleep_time.getText().toString());
                GroupID             = Integer.parseInt(edit_group_id.getText().toString());
                UnitID              = Integer.parseInt(edit_user_id.getText().toString());
                Project_warn        = (int)spinner_project_warn.getSelectedItemId()+21;
                Warn_value          = Integer.parseInt(edit_warn.getText().toString());
                Project_alarm       = (int)spinner_project_alarm.getSelectedItemId()+21;
                Alarm_value         = Integer.parseInt(edit_alarm.getText().toString());
                ProduceName         = edit_produce_name.getText().toString();

                AT_Utils.setIP(Ip_addr,Ip_port);
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
                AT_Utils.SetTime(DisplayTime,"DisplayTime");
                AT_Utils.SetTime(SendToInternetTime,"SendToInternetTime");
                AT_Utils.SetTime(GetMegTime,"GetMegTime");
                AT_Utils.Baud(Usart,Bound,Byte_length,Stop_bit,Check_bit);
                AT_Utils.SetTime(SleepTime,"SleepTime");
                AT_Utils.GroupID(GroupID);
                AT_Utils.UnitID(UnitID);
                AT_Utils.WarningAlarmValue(Project_warn ,Warn_value ,"WarningValue");
                AT_Utils.WarningAlarmValue(Project_alarm,Alarm_value,"AlarmValue");
                AT_Utils.ProduceName(ProduceName);

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
