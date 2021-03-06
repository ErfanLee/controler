package com.lx.controler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.Map;

/**
 * Created by pc02 on 2021/9/14.
 */

public class ConfigActivity extends AppCompatActivity{

    public static Context context;//上下文
    String wifiIP;


    /*需要设置的物联网关参数*/
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
    int DataTransmission;
    int DataPrint;
    int DisplayTime;
    int SendToInternetTime;
    int GetMegTime;
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
    String PubTopic;
    String SubTopic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gate_way_congfig);
        context = this;

        //获取wifi网关地址
        wifiIP =  getLocalHost();

        Toast.makeText(ConfigActivity.this,
                wifiIP,
                Toast.LENGTH_SHORT).show();

        MyMqttService.startService(this); //开启Mqtt服务
        initCtrl();

    }

    private void initCtrl() {


        //数据设置使用连接
        final Switch switch_connect_mode = findViewById(R.id.switch_connect_mode);

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
        //分组数目
        final EditText edit_group_number        = findViewById(R.id.edit_group_number);
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
        final DataTissueConfig checkbox_data_tissue = findViewById(R.id.dataTissue_config);
        //数据透传接口
        final DataTransmission checkbox_data_transmisson = findViewById(R.id.data_transmission);
        //数据打印接口
        final DataPrintConfig checkbox_data_print = findViewById(R.id.data_print_config);
        //屏显间隔时间
        final EditText edit_display_time          = findViewById(R.id.edit_display_time);
        //上云间隔时间
        final EditText edit_internet_time         = findViewById(R.id.edit_internet_time);
        //采集信息间隔
        final EditText edit_message_time          = findViewById(R.id.edit_message_time);

        //串口设置多个串口
        final UsartConfigList usartConfigList     = findViewById(R.id.usartConfigList);
        //参数设置动态数量复数个控件
        final ATTextConfigList atConfigList     = findViewById(R.id.atTextConfigList);

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
        final IPEditText ip_edit_ipa              = findViewById(R.id.ip_edit_ipa);
        final EditText   ip_port_ipa              = findViewById(R.id.ip_port_ipa);
        //IP地址B设置
        final IPEditText ip_edit_ipb              = findViewById(R.id.ip_edit_ipb);
        final EditText   ip_port_ipb              = findViewById(R.id.ip_port_ipb);
        //产品密钥设置
        //设备名称设置
        //设备秘钥设置
        //发布名称
        final EditText edit_pub_topic             = findViewById(R.id.edit_pub_topic);
        //订阅名称
        final EditText edit_sub_topic             = findViewById(R.id.edit_sub_topic);
        //询问模块编号
        //物联信息采集

        //勾选发送选项
        final CheckBox checkbox_work_mode           = findViewById(R.id.checkbox_work_mode);
        final CheckBox checkbox_connect_mode        = findViewById(R.id.checkbox_connect_mode);
        final CheckBox checkbox_master_slave_mode   = findViewById(R.id.checkbox_master_slave_mode);
        final CheckBox checkbox_function_mode       = findViewById(R.id.checkbox_function_mode);
        final CheckBox checkbox_transfer_mode       = findViewById(R.id.checkbox_transfer_mode);
        final CheckBox checkbox_display_mode        = findViewById(R.id.checkbox_display_mode);
        final CheckBox checkbox_group_number        = findViewById(R.id.checkbox_group_number);
        final CheckBox checkbox_connect_target      = findViewById(R.id.checkbox_connect_target);
        final CheckBox checkbox_wifi_mode           = findViewById(R.id.checkbox_wifi_mode);
        final CheckBox checkbox_wifi_name           = findViewById(R.id.checkbox_wifi_name);
        final CheckBox checkbox_wifi_password       = findViewById(R.id.checkbox_wifi_password);
        final CheckBox checkbox_transfer_protocol   = findViewById(R.id.checkbox_transfer_protocol);

        final CheckBox checkbox_display_time        = findViewById(R.id.checkbox_display_time);
        final CheckBox checkbox_internet_time       = findViewById(R.id.checkbox_internet_time);
        final CheckBox checkbox_message_time        = findViewById(R.id.checkbox_message_time);
        final CheckBox checkbox_sleep_time          = findViewById(R.id.checkbox_sleep_time);
        final CheckBox checkbox_group_id            = findViewById(R.id.checkbox_group_id);
        final CheckBox checkbox_user_id             = findViewById(R.id.checkbox_user_id);
        final CheckBox checkbox_project_warn        = findViewById(R.id.checkbox_project_warn);
        final CheckBox checkbox_project_alarm       = findViewById(R.id.checkbox_project_alarm);
        final CheckBox checkbox_produce_name        = findViewById(R.id.checkbox_produce_name);
        final CheckBox checkbox_location            = findViewById(R.id.checkbox_location);
        final CheckBox checkBox_ipa                 = findViewById(R.id.checkbox_ipa);
        final CheckBox checkBox_ipb                 = findViewById(R.id.checkbox_ipb);
        final CheckBox checkbox_pub_topic           = findViewById(R.id.checkbox_pub_topic);
        final CheckBox checkbox_sub_topic           = findViewById(R.id.checkbox_sub_topic);

        FloatingActionButton fab_exit  =  findViewById(R.id.fab_exit);
        fab_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "fab_exit", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(ConfigActivity.this,ScrollingActivity.class);
                startActivity(intent);
                ConfigActivity.this.finish();
            }
        });




        FloatingActionButton fab_send  =  findViewById(R.id.fab_send);
        fab_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "fab_send", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if(!AT_Utils.isMQTT){
                    if(!AT_Utils.getWifiState()){
                        Toast.makeText(ConfigActivity.this,
                                "Wifi连接失败",
                                Toast.LENGTH_SHORT).show();
                        switch_connect_mode.setChecked(false);
                        return;
                    }

                }


                WorkMode            = (int)spinner_work_mode.getSelectedItemId()+1;
                ConnectMode         = (int)spinner_connect_mode.getSelectedItemId();
                MasterSlaveMode     = (int)spinner_master_slave_mode.getSelectedItemId()+1;
                FunctionMode        = (int)spinner_function_mode.getSelectedItemId()+1;
                TransferMode        = (int)spinner_transfer_mode.getSelectedItemId()+1;
                DisplayMode         = (int)spinner_display_mode.getSelectedItemId()+1;
                GroupNumber         = Integer.parseInt(edit_group_number.getText().toString());
                ConnectTarget       = (int)spinner_connect_target.getSelectedItemId()+1;
                WifiMode            = (int)spinner_wifi_mode.getSelectedItemId()+1;
                WifiName            = edit_wifi_name.getText().toString();
                WifiPassword        = edit_wifi_password.getText().toString();
                TransferProtocol    = (int)spinner_transfer_protocol.getSelectedItemId()+1;
                DataTissue          = checkbox_data_tissue.getResult();
                DataTransmission    = checkbox_data_transmisson.getResult();
                DataPrint           = checkbox_data_print.getResult();
                DisplayTime         = Integer.parseInt(edit_display_time.getText().toString());
                SendToInternetTime  = Integer.parseInt(edit_internet_time.getText().toString());
                GetMegTime          = Integer.parseInt(edit_message_time.getText().toString());
                SleepTime           = Integer.parseInt(edit_sleep_time.getText().toString());
                GroupID             = Integer.parseInt(edit_group_id.getText().toString());
                UnitID              = Integer.parseInt(edit_user_id.getText().toString());
                Project_warn        = (int)spinner_project_warn.getSelectedItemId()+21;
                Warn_value          = Integer.parseInt(edit_warn.getText().toString());
                Project_alarm       = (int)spinner_project_alarm.getSelectedItemId()+21;
                Alarm_value         = Integer.parseInt(edit_alarm.getText().toString());
                ProduceName         = edit_produce_name.getText().toString();
                SubTopic            = edit_sub_topic.getText().toString();
                PubTopic            = edit_pub_topic.getText().toString();



                if(checkbox_work_mode.isChecked()){
                    AT_Utils.WorkMode(WorkMode);
                }
                if(checkbox_connect_mode.isChecked()){
                    AT_Utils.ConnectMode(ConnectMode);
                }
                if(checkbox_master_slave_mode.isChecked()){
                    AT_Utils.MasterSlaveMode(MasterSlaveMode);
                }
                if(checkbox_function_mode.isChecked()){
                    AT_Utils.FunctionMode(FunctionMode);
                }
                if(checkbox_transfer_mode.isChecked()){
                    AT_Utils.TransferMode(TransferMode);
                }
                if(checkbox_display_mode.isChecked()){
                    AT_Utils.DisplayMode(DisplayMode);
                }
                if(checkbox_group_number.isChecked()){
                    AT_Utils.GroupNumber(GroupNumber);
                }
                if(checkbox_connect_target.isChecked()){
                    AT_Utils.ConnectTarget(ConnectTarget);
                }
                if(checkbox_wifi_mode.isChecked()){
                    AT_Utils.WifiMode(WifiMode);
                }
                if(checkbox_wifi_name.isChecked()){
                    AT_Utils.WifiName(WifiName);
                }
                if(checkbox_wifi_password.isChecked()){
                    AT_Utils.WifiPassword(WifiPassword);
                }
                if(checkbox_transfer_protocol.isChecked()){
                    AT_Utils.TransferProtocol(TransferProtocol);
                }
                if(checkbox_data_tissue.isChecked()){
                    AT_Utils.DataTissue(DataTissue);
                }
                if(checkbox_data_transmisson.isChecked()){
                    AT_Utils.DataTransmission(DataTransmission);
                }
                if(checkbox_data_print.isChecked()){
                    AT_Utils.DataPrint(DataPrint);
                }
                if(checkbox_display_time.isChecked()){
                    AT_Utils.SetTime(DisplayTime,"DisplayTime");
                }
                if(checkbox_internet_time.isChecked()){
                    AT_Utils.SetTime(SendToInternetTime,"SendToInternetTime");
                }
                if(checkbox_message_time.isChecked()){
                    AT_Utils.SetTime(GetMegTime,"GetMegTime");
                }
                //多个串口设置控件
                for(int i = 0;i<usartConfigList.usartNum;i++){
                    final UsartConfig usartConfig = usartConfigList.get(i);
                    if(usartConfig.isChecked()){
                        AT_Utils.Baud(usartConfig.USARTx,usartConfig.Boundx,usartConfig.length,usartConfig.stop,usartConfig.check);
                    }
                }
                //多个at text参数设置控件
                for(int i =0;i<atConfigList.atNum;i++){
                    final ATTextConfig atTextConfig = atConfigList.get(i);
                    if(atTextConfig.isChecked()){
                        AT_Utils.ConfigText(atTextConfig.rowsNum,
                                atTextConfig.erase,
                                atTextConfig.single,
                                atTextConfig.agreementInt,
                                atTextConfig.projectInt,
                                atTextConfig.display,
                                atTextConfig.internet,
                                atTextConfig.displayNoInt,
                                atTextConfig.rowsNoInt,
                                atTextConfig.dataNum,
                                atTextConfig.afterPointInt,
                                atTextConfig.data_before,
                                atTextConfig.data_after);
                    }
                }


                if(checkbox_sleep_time.isChecked()){
                    AT_Utils.SetTime(SleepTime,"SleepTime");
                }
                if(checkbox_group_id.isChecked()){
                    AT_Utils.GroupID(GroupID);
                }
                if(checkbox_user_id.isChecked()){
                    AT_Utils.UnitID(UnitID);
                }
                if(checkbox_project_warn.isChecked()){
                    AT_Utils.WarningAlarmValue(Project_warn ,Warn_value ,"WarningValue");
                }
                if(checkbox_project_alarm.isChecked()){
                    AT_Utils.WarningAlarmValue(Project_alarm,Alarm_value,"AlarmValue");
                }
                if(checkbox_produce_name.isChecked()){
                    AT_Utils.ProduceName(ProduceName);
                }
                if(checkBox_ipa.isChecked()){
                    int ip1 = ip_edit_ipa.getInt(1);
                    int ip2 = ip_edit_ipa.getInt(2);
                    int ip3 = ip_edit_ipa.getInt(3);
                    int ip4 = ip_edit_ipa.getInt(4);
                    int port =Integer.parseInt(ip_port_ipa.getText().toString());
                    AT_Utils.IPA_B(ip1,ip2,ip3,ip4,port,"IPA");
                }
                if(checkBox_ipb.isChecked()){
                    int ip1 = ip_edit_ipb.getInt(1);
                    int ip2 = ip_edit_ipb.getInt(2);
                    int ip3 = ip_edit_ipb.getInt(3);
                    int ip4 = ip_edit_ipb.getInt(4);
                    int port =Integer.parseInt(ip_port_ipb.getText().toString());
                    AT_Utils.IPA_B(ip1,ip2,ip3,ip4,port,"IPB");
                }
                if(checkbox_pub_topic.isChecked()){
                    AT_Utils.PubTopic(PubTopic);
                }
                if(checkbox_sub_topic.isChecked()){
                    AT_Utils.SubTopic(SubTopic);
                }
            }
        });




        switch_connect_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Toast.makeText(ConfigActivity.this,
                            "WIFI模式",
                            Toast.LENGTH_SHORT).show();
                    AT_Utils.isMQTT = false;
                    wifiIP = getLocalHost();
                    Ip_port = 8080;
                    AT_Utils.setPort(wifiIP,Ip_port);

                    Toast.makeText(ConfigActivity.this,
                            "获取网关地址"+wifiIP+"端口号"+Ip_port,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ConfigActivity.this,
                            "MQTT模式",
                            Toast.LENGTH_SHORT).show();
                    AT_Utils.isMQTT = true;
                }
            }
        });






    }
    private String getLocalHost(){
        WifiManager wifiManager = (WifiManager)getApplication().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String strHost = ((ipAddress & 0xff)+"."+(ipAddress>>8 & 0xff)+"."
                +(ipAddress>>16 & 0xff)+".1");//修改最末尾的地址
        if(ipAddress==0)return "未连接wifi";
        return strHost;
    }


}


