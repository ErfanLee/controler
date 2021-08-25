package com.lx.controler;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
    // “扳手”按钮控件List
    private LinkedList<ImageButton> listIBTNCon;
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
        listIBTNCon = new LinkedList<ImageButton>();
        listProgress = new LinkedList<TemperatureProgress>();

        circleProgress_Degree1 = (TemperatureProgress)findViewById(R.id.circle_progress1);
        circleProgress_Degree1.config("temp1","温度1","°C",60,0);
        setSupportActionBar(toolbar);

        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "发送指令1", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //MyMqttService.publish("[{\"tag\":\"LockOrder\",\"text\":\"1\"},{\"tag\":\"DeviceOrder\",\"text\":\"1\"}]");
                setBaudRateDialog();


                //Uri uri = Uri.parse("http://sj18636631.51mypc.cn:43123/view.html?id=6066c5c2961b50210040accd");
                //Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                ///startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "发送指令", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //MyMqttService.publish("[{\"tag\":\"LockOrder\",\"text\":\"1\"},{\"tag\":\"DeviceOrder\",\"text\":\"1\"}]");
                setDisplayTimeDialog();


                //Uri uri = Uri.parse("http://sj18636631.51mypc.cn:43123/view.html?id=6066c5c2961b50210040accd");
                //Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                ///startActivity(intent);
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "发送指令2", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                 setATTextDialog();
            }
        });



        ImageButton ibnAdd1 = (ImageButton)this.findViewById(R.id.ibn_add1);
        ibnAdd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContent(view);
            }
        });

        ImageButton ibnCon = (ImageButton)this.findViewById(R.id.ibn_config);
        ibnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayConfigDialog(view);
            }
        });

        listIBTNAdd.add(ibnAdd1);
        listIBTNDel.add(null);
        listIBTNCon.add(ibnCon);
        listProgress.add(circleProgress_Degree1);
    }
    /**
     *设置间隔时间弹窗
     */
    private void setDisplayTimeDialog() {
        final EditText editText = new EditText(ScrollingActivity.this);
        editText.setHint("请输入间隔时间");
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        AlertDialog.Builder inputDialogBuilder =
                new AlertDialog.Builder(ScrollingActivity.this);
        inputDialogBuilder.setTitle("显示间隔时间").setView(editText);
        inputDialogBuilder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String timeStr = editText.getText().toString();
                        if(timeStr.equals("")) {
                            Toast.makeText(ScrollingActivity.this,
                                    "输入不能为空",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }else{
                            Toast.makeText(ScrollingActivity.this,
                                    editText.getText().toString(),
                                    Toast.LENGTH_SHORT).show();
                            AT_Utils.sendSetDisplayTime(Integer.parseInt(editText.getText().toString()));
                        }

                    }
                });
        AlertDialog inputDialog = inputDialogBuilder.create();
        inputDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                final Button btn = ((AlertDialog) dialogInterface).getButton(DialogInterface.BUTTON_POSITIVE);   //*2
                btn.setEnabled(false);
                editText.addTextChangedListener(new TextWatcher(){
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // TODO Auto-generated method stub
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // TODO Auto-generated method stub
                        if(("").equals(s.toString())){
                            btn.setEnabled(false);
                        }
                        else{
                            btn.setEnabled(true);
                        }
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub

                    }});
            }
        });
        inputDialog.show();
    }

    /**
     *设置波特率弹窗
     */
    private void setBaudRateDialog() {
        //设置一个线性布局参数
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置一个线性布局控件装下所有下拉框
        LinearLayout layout=new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(linearLayoutParams);

        //设置串口号控件
        final  TextView textUSARTx = new TextView(context);
        textUSARTx.setText(R.string.usart_x);
        layout.addView(textUSARTx);
        final  String[] USARTxStrArray = {"1","2","3","4","5","6"};
        final Spinner spinner0=new Spinner(this);
        spinner0.setLayoutParams(linearLayoutParams);
        ArrayAdapter<String> arrayAdapterUsart=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,USARTxStrArray);
        spinner0.setAdapter(arrayAdapterUsart);
        layout.addView(spinner0);

        //设置波特率
        final  TextView textBaud = new TextView(context);
        textBaud.setText(R.string.baud);
        layout.addView(textBaud);
        //波特率下拉框
        final  String[] BaudStrArray = {"300","600","1200","2400","4800","9600","14400","19200","38400","57600","115200"};
        final Spinner spinner1=new Spinner(this);
        spinner1.setLayoutParams(linearLayoutParams);
        ArrayAdapter<String> arrayAdapterBaud=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,BaudStrArray);
        spinner1.setAdapter(arrayAdapterBaud);
        layout.addView(spinner1);

        //设置字节长度
        final  TextView textLength = new TextView(context);
        textLength.setText(R.string.length);
        layout.addView(textLength);
        //字节长度下拉框
        final  String[] lengthStrArray = {"5","6","7","8"};
        final Spinner spinner2=new Spinner(this);
        spinner2.setLayoutParams(linearLayoutParams);
        ArrayAdapter<String> arrayAdapterLength=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,lengthStrArray);
        spinner2.setAdapter(arrayAdapterLength);
        layout.addView(spinner2);

        //设置停止位
        final  TextView textStop = new TextView(context);
        textStop.setText(R.string.stop);
        layout.addView(textStop);
        //停止位下拉框
        final  String[] stopStrArray = {"1","2"};
        final Spinner spinner3=new Spinner(this);
        spinner3.setLayoutParams(linearLayoutParams);
        ArrayAdapter<String> arrayAdapterStop=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,stopStrArray);
        spinner3.setAdapter(arrayAdapterStop);
        layout.addView(spinner3);

        //设置校验位
        final  TextView textCheck = new TextView(context);
        textCheck.setText(R.string.check);
        layout.addView(textCheck);
        //校验位下拉框
        final  String[] checkStrArray = {"NONE","ODD","EVEN"};//无，奇校验，偶校验
        final Spinner spinner4=new Spinner(this);
        spinner4.setLayoutParams(linearLayoutParams);
        ArrayAdapter<String> arrayAdapterCheck=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,checkStrArray);
        spinner4.setAdapter(arrayAdapterCheck);
        layout.addView(spinner4);

        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(ScrollingActivity.this);
        inputDialog.setTitle("设置波特率").setView(layout);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ScrollingActivity.this,
                                "串口"+spinner0.getSelectedItem().toString()
                                +"波特率"+spinner1.getSelectedItem().toString()
                                +"字节长度"+spinner2.getSelectedItem().toString()
                                +"停止位"+spinner3.getSelectedItem().toString()
                                +"校验位"+spinner4.getSelectedItem().toString(),
                                Toast.LENGTH_SHORT).show();
                        AT_Utils.sendBaud((int)spinner0.getSelectedItemId()+1,(int)spinner1.getSelectedItemId(),(int)spinner2.getSelectedItemId()+5,(int) spinner3.getSelectedItemId()+1,(int)spinner4.getSelectedItemId());
                    }
                }).show();
    }




    /**
     * 设置工作模式弹窗
     */
    private void setWorkModeDialog() {
        //设置本弹窗的布局文件
        LayoutInflater inflater = LayoutInflater.from(this);
        View configSetView = inflater.inflate(R.layout.work_mode,null);
        final Spinner spinner0=configSetView.findViewById(R.id.spinner_work_mode);
        AlertDialog.Builder inputDialogBuilder =
                new AlertDialog.Builder(ScrollingActivity.this);
        inputDialogBuilder.setTitle("配置参数设置").setView(configSetView);
        inputDialogBuilder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(ScrollingActivity.this,
                                    "工作模式:" + spinner0.getSelectedItemId(),
                                    Toast.LENGTH_SHORT).show();
                            AT_Utils.sendWorkMode((int) spinner0.getSelectedItemId()+1);
                    }
                }).show();


    }

    /**
     *设置配置参数弹窗
     * AT+Text<行数>=<全擦除标志><单选有效标识><字符个数><协议类型><项目代号><1位向显示器发送数据标志><1位向互联网发送数据标志><1位显示屏序号><1位显示行号>
     */
    private void setATTextDialog() {
        //设置本弹窗的布局文件
        LayoutInflater inflater = LayoutInflater.from(this);
        View configSetView = inflater.inflate(R.layout.set_at_text_layout,null);

        //设置项目
        final  String[] projectStrArray = {"风向","风速","温度","湿度","噪音","PM2.5","PM10",
                "大气压","光照度","雨量"};
        final Spinner spinner0=configSetView.findViewById(R.id.spinner_project);
        ArrayAdapter<String> arrayAdapterProject=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,projectStrArray);
        spinner0.setAdapter(arrayAdapterProject);

        //设置协议类型
        final  String[] agreementTypeArray = {"modbus协议","自定义16字节协议"};
        final Spinner spinner1=configSetView.findViewById(R.id.spinner_agreement);
        ArrayAdapter<String> agreementTypeAdapterProject=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,agreementTypeArray);
        spinner1.setAdapter(agreementTypeAdapterProject);


        //设置行数
        final EditText editText = configSetView.findViewById(R.id.edit_rowNum);
        editText.setHint("请输入行数");
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);



        //全擦除
        final CheckBox checkErase = configSetView.findViewById(R.id.chk1);
        //单选
        final CheckBox checkSingle = configSetView.findViewById(R.id.chk2);
        //向显示器发送
        final CheckBox checkSendDis = configSetView.findViewById(R.id.chk3);
        //向互联网发送
        final CheckBox checkSendInt = configSetView.findViewById(R.id.chk4);
        //设置显示屏序号
        final  String[] displayNoStrArray = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16"};
        final Spinner spinner2=configSetView.findViewById(R.id.spinner_display_no);
        ArrayAdapter<String> arrayAdapterDisplayNo=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,displayNoStrArray);
        spinner2.setAdapter(arrayAdapterDisplayNo);

        //前面
        final EditText edtTextBefore = configSetView.findViewById(R.id.edit_before_data);
        //后面
        final EditText edtTextAfter = configSetView.findViewById(R.id.edit_after_data);

        //设置行号
        final  String[] rowsNoStrArray = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16"};
        final Spinner spinner3=configSetView.findViewById(R.id.spinner_row_no);
        ArrayAdapter<String> arrayAdapterRowsNo=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,rowsNoStrArray);
        spinner3.setAdapter(arrayAdapterRowsNo);

        //设置小数点前位数
        final  String[] beforePointStrArray = {"0","1","2","3","4","5"};//无，奇校验，偶校验
        final Spinner spinner4=configSetView.findViewById(R.id.spinner_before_point);
        ArrayAdapter<String> arrayAdapterBeforePoint=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,beforePointStrArray);
        spinner4.setAdapter(arrayAdapterBeforePoint);

        //设置小数点后位数
        final   String[] afterPointStrArray = {"0","1","2","3","4","5"};//无，奇校验，偶校验
        final Spinner spinner5=configSetView.findViewById(R.id.spinner_after_point);
        ArrayAdapter<String> arrayAdapterAfterPoint=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,afterPointStrArray);
        spinner5.setAdapter(arrayAdapterAfterPoint);


        AlertDialog.Builder inputDialogBuilder =
                new AlertDialog.Builder(ScrollingActivity.this);
        inputDialogBuilder.setTitle("配置参数设置").setView(configSetView);
        inputDialogBuilder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(editText.getText().toString().equals("")) {
                            Toast.makeText(ScrollingActivity.this,
                                    "输入行数不能为空(0-99)",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            int erase = 0;
                            int single = 0;
                            int display = 0;
                            int internet = 0;
                            int projectInt = (int) spinner0.getSelectedItemId() + 22;
                            int agreementInt = (int) spinner1.getSelectedItemId() + 1;
                            int displayNoInt = (int) spinner2.getSelectedItemId() + 1;
                            int rowsNoInt = (int) spinner3.getSelectedItemId() + 1;
                            int beforePointInt = (int) spinner4.getSelectedItemId();
                            int afterPointInt = (int) spinner5.getSelectedItemId();
                            if (checkErase.isChecked()) {
                                erase = 1;
                            }
                            if (checkSingle.isChecked()) {
                                single = 1;
                            }
                            if (checkSendDis.isChecked()) {
                                display = 1;
                            }
                            if (checkSendInt.isChecked()) {
                                internet = 1;
                            }
                            Toast.makeText(ScrollingActivity.this,
                                    "项目类型" + spinner0.getSelectedItem().toString()
                                            + "擦除标志" + checkErase.isChecked(),
                                    Toast.LENGTH_SHORT).show();
                            AT_Utils.sendConfigText(Integer.parseInt(editText.getText().toString()), erase, single, agreementInt, projectInt, display, internet, displayNoInt, rowsNoInt, beforePointInt, afterPointInt, edtTextBefore.getText().toString(), edtTextAfter.getText().toString());
                        }
                    }
                });
        final AlertDialog inputDialog = inputDialogBuilder.create();

        inputDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                final Button btn = ((AlertDialog) dialogInterface).getButton(DialogInterface.BUTTON_POSITIVE);   //*2
                btn.setEnabled(false);
                editText.addTextChangedListener(new TextWatcher(){
                    int l=0;////////记录字符串被删除字符之前，字符串的长度
                    int location=0;//记录光标的位置
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // TODO Auto-generated method stub
                        l=s.length();
                        location=editText.getSelectionStart();
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // TODO Auto-generated method stub
                        Pattern p = Pattern.compile("^(99|[1-9]\\d|\\d)$");
                        Matcher m =p.matcher(s.toString());
                        if(m.find() ){
                            System.out.print("OK!");
                            btn.setEnabled(true);
                        }else if(("").equals(s.toString())){
                            btn.setEnabled(false);
                        }
                        else{
                            System.out.print("False!");
                            Toast.makeText(context, "请输入正确的数值(0-99)", Toast.LENGTH_SHORT).show();
                            editText.setText("");
                            btn.setEnabled(false);
                        }
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub

                    }});
            }
        });
        inputDialog.show();
    }


    /**
     * 显示设置弹窗
     */
    private void displayConfigDialog(View v) {
        Toast.makeText(context, "设置显示", Toast.LENGTH_LONG).show();

        if(v == null){return;}
        // 判断第几个“-”按钮触发了事件
        int iIndex = -1;
        for (int i = 0; i < listIBTNCon.size(); i++) {
            if (listIBTNCon.get(i) == v) {
                iIndex = i;
                break;
            }
        }
        final TemperatureProgress progress1 = listProgress.get(iIndex);





        //设置本弹窗的布局文件
        LayoutInflater inflater = LayoutInflater.from(this);
        View configSetView = inflater.inflate(R.layout.set_display,null);

        //设置
        final EditText editTag = configSetView.findViewById(R.id.edit_tag);
        final EditText editName = configSetView.findViewById(R.id.edit_name);
        final EditText editUnit = configSetView.findViewById(R.id.edit_unit);
        final EditText editMax = configSetView.findViewById(R.id.edit_max);
        final EditText editMin = configSetView.findViewById(R.id.edit_min);
        final EditText editWarn = configSetView.findViewById(R.id.edit_warn);
        final EditText editAlrm = configSetView.findViewById(R.id.edit_alrm);



        AlertDialog.Builder inputDialogBuilder =
                new AlertDialog.Builder(ScrollingActivity.this);
        inputDialogBuilder.setTitle("项目设置显示").setView(configSetView);
        inputDialogBuilder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tag = editTag.getText().toString();
                        String name= editName.getText().toString();
                        String unit= editUnit.getText().toString();
                        double max = Double.valueOf(editMax.getText().toString());
                        double min = Double.valueOf(editMin.getText().toString());
                        progress1.config(tag,name,unit,max,min);
                    }
                });
        final AlertDialog inputDialog = inputDialogBuilder.create();
        inputDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                final Button btn = ((AlertDialog) dialogInterface).getButton(DialogInterface.BUTTON_POSITIVE);   //*2
                btn.setEnabled(false);
                TextWatcher textWatcher1 = new TextWatcher(){
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // TODO Auto-generated method stub
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // TODO Auto-generated method stub
                        if(editTag.getText().toString().equals("")||
                                editName.getText().toString().equals("")||
                                editUnit.getText().toString().equals("")||
                                editMax.getText().toString().equals("")||
                                editMin.getText().toString().equals("")||
                                editWarn.getText().toString().equals("")||
                                editAlrm.getText().toString().equals("")){
                            btn.setEnabled(false);
                        }
                        else{
                            btn.setEnabled(true);
                        }
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub

                    }};
                editTag.addTextChangedListener(textWatcher1);
                editName.addTextChangedListener(textWatcher1);
                editUnit.addTextChangedListener(textWatcher1);
                editMax.addTextChangedListener(textWatcher1);
                editMin.addTextChangedListener(textWatcher1);
                editWarn.addTextChangedListener(textWatcher1);
                editAlrm.addTextChangedListener(textWatcher1);
            }
        });
        inputDialog.show();
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
            //txtAlarmParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            txtAlarm.setLayoutParams(txtAlarmParam);
            rlBtn.addView(txtAlarm);



            // 4.创建“-”按钮
            ImageButton btnDelete = new ImageButton(ScrollingActivity.this);
            btnDelete.setBackgroundResource(android.R.drawable.ic_menu_delete);

            RelativeLayout.LayoutParams btnDeleteAddParam = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            btnDeleteAddParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            btnDeleteAddParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

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

            //创建"设置"按钮
            ImageButton ibnCon = new ImageButton(ScrollingActivity.this);
            RelativeLayout.LayoutParams btnConParam = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            // 靠右放置
            btnConParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

            // “”按钮放在“”按钮左侧
            btnConParam.addRule(RelativeLayout.LEFT_OF, btnDelete.getId());
            ibnCon.setLayoutParams(btnConParam);

            ibnCon.setBackgroundResource(android.R.drawable.ic_menu_preferences);

            ibnCon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    displayConfigDialog(view);
                }
            });
            //按钮放到RelativeLayout里
            rlBtn.addView(ibnCon);
            listIBTNCon.add(iIndex,ibnCon);

            // 4.创建“+”按钮
            ImageButton btnAdd = new ImageButton(ScrollingActivity.this);
            RelativeLayout.LayoutParams btnAddParam = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            // 靠右放置
            btnAddParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            // “”按钮放在“”按钮左侧
            btnAddParam.addRule(RelativeLayout.LEFT_OF, ibnCon.getId());
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
            AT_Utils.sendGetmessgeRequest();
            return true;
        }
        if (id == R.id.action_setting2) {
            AT_Utils.sendResetRequest();
            return true;
        }
        if (id == R.id.action_setting3) {
            AT_Utils.sendSleepRequest();
            return true;
        }
        if (id == R.id.action_setting4) {
            AT_Utils.sendStopRequest();
            return true;
        }
        if (id == R.id.action_setting5) {
            setWorkModeDialog();
            return true;
        }
        if (id == R.id.action_setting6) {
            return true;
        }
        if (id == R.id.action_setting7) {
            return true;
        }
        if (id == R.id.action_setting8) {
            return true;
        }
        if (id == R.id.action_setting9) {
            return true;
        }
        if (id == R.id.action_setting10) {
            setWorkModeDialog();
            return true;
        }
        if (id == R.id.action_setting11) {
            return true;
        }
        if (id == R.id.action_setting12) {
            return true;
        }
        if (id == R.id.action_setting13) {
            setWorkModeDialog();
            return true;
        }
        if (id == R.id.action_setting14) {
            return true;
        }
        if (id == R.id.action_setting15) {
            return true;
        }
        if (id == R.id.action_setting16) {
            setWorkModeDialog();
            return true;
        }
        if (id == R.id.action_setting17) {
            AT_Utils.sendGetmessgeRequest();
            return true;
        }
        if (id == R.id.action_setting18) {
            return true;
        }
        if (id == R.id.action_setting19) {
            setWorkModeDialog();
            return true;
        }
        if (id == R.id.action_setting20) {
            AT_Utils.sendGetmessgeRequest();
            return true;
        }
        if (id == R.id.action_setting21) {
            return true;
        }
        if (id == R.id.action_setting22) {
            setWorkModeDialog();
            return true;
        }
        if (id == R.id.action_setting23) {
            AT_Utils.sendGetmessgeRequest();
            return true;
        }
        if (id == R.id.action_setting24) {
            return true;
        }
        if (id == R.id.action_setting25) {
            setWorkModeDialog();
            return true;
        }
        if (id == R.id.action_setting26) {
            AT_Utils.sendGetmessgeRequest();
            return true;
        }
        if (id == R.id.action_setting27) {
            return true;
        }
        if (id == R.id.action_setting28) {
            setWorkModeDialog();
            return true;
        }
        if (id == R.id.action_setting29) {
            AT_Utils.sendGetmessgeRequest();
            return true;
        }
        if (id == R.id.action_setting30) {
            return true;
        }
        if (id == R.id.action_setting31) {
            setWorkModeDialog();
            return true;
        }
        if (id == R.id.action_setting32) {
            AT_Utils.sendGetmessgeRequest();
            return true;
        }
        if (id == R.id.action_setting33) {
            return true;
        }
        if (id == R.id.action_setting34) {
            setWorkModeDialog();
            return true;
        }
        if (id == R.id.action_setting35) {
            AT_Utils.sendGetmessgeRequest();
            return true;
        }
        if (id == R.id.action_setting36) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
