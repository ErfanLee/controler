package com.lx.controler;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pc02 on 2021/11/21.
 */

public class ATTextConfig extends LinearLayout{
    Context m_context;

    //设置项目
    final  String[] projectStrArray = {"风向","风速","温度","湿度","噪音","PM2.5","PM10",
            "大气压","光照度","雨量"};
    final  String[] agreementTypeArray = {"modbus协议","自定义16字节协议"};

    final int PROJECT_BASE  = 22;
    final int AGREEMENT_BASE= 1;
    final int DISPLAY_NO_BASE  = 1;
    final int ROWS_NO_BASE = 1;

    final TextView text_project;
    final TextView text_agreement;
    final CheckBox checkBoxErase;
    final CheckBox checkBoxSingle;
    final CheckBox checkBoxDisplay;
    final CheckBox checkBoxInternet;
    final TextView text_rowNum;
    final TextView text_display_no;
    final TextView text_row_no;
    final TextView text_num;
    final TextView text_point_num;
    final TextView text_before;
    final TextView text_after;
    final CheckBox checkbox_at;





    public int erase = 1;
    public int single = 1;
    public int display = 1;
    public int internet = 1;
    public int projectInt = PROJECT_BASE;
    public int agreementInt = AGREEMENT_BASE;
    public int displayNoInt = DISPLAY_NO_BASE;
    public int rowsNoInt = ROWS_NO_BASE;
    public int dataNum = 0;
    public int afterPointInt = 0;
    public int rowsNum = 1;
    public String data_before = "";
    public String data_after = "";
    ATTextConfig(Context context, AttributeSet attrs){
        super(context, attrs);
        m_context = context;
        // TODO Auto-generated constructor stub
        View view = LayoutInflater.from(context).inflate(R.layout.at_text_config, this);
        text_project = view.findViewById(R.id.text_project);
        text_agreement = view.findViewById(R.id.text_agreement);
        checkBoxErase    = view.findViewById(R.id.chk_erase);
        checkBoxSingle   = view.findViewById(R.id.chk_single);
        checkBoxDisplay  = view.findViewById(R.id.chk_display);
        checkBoxInternet = view.findViewById(R.id.chk_internet);
        text_rowNum      = view.findViewById(R.id.text_rowNum);
        text_display_no  = view.findViewById(R.id.text_display_no);
        text_row_no      = view.findViewById(R.id.text_row_no);
        text_num         = view.findViewById(R.id.text_num);
        text_point_num   = view.findViewById(R.id.text_point_num);
        text_before      = view.findViewById(R.id.text_before_data);
        text_after       = view.findViewById(R.id.text_after_data);
        checkbox_at      = view.findViewById(R.id.checkbox_at);


        refreshDisplay();
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setATTextDialog();
            }
        });
    }

    /**
     * 刷新显示控件
     */
    private void refreshDisplay(){
        //设置显示控件显示参数
        text_project.setText(projectStrArray[projectInt-PROJECT_BASE]);
        text_agreement.setText(agreementTypeArray[agreementInt-AGREEMENT_BASE]);
        if (erase == 1) {
            checkBoxErase.setChecked(true);
        }else {
            checkBoxErase.setChecked(false);
        }
        if (single == 1) {
            checkBoxSingle.setChecked(true);
        }else {
            checkBoxSingle.setChecked(false);
        }
        if (display == 1) {
            checkBoxDisplay.setChecked(true);
        }else{
            checkBoxDisplay.setChecked(false);
        }
        if (internet == 1) {
            checkBoxInternet.setChecked(true);
        }else{
            checkBoxInternet.setChecked(false);
        }
        text_rowNum.setText(""+rowsNum);
        text_display_no.setText(""+displayNoInt);
        text_row_no.setText(""+rowsNoInt);
        text_num.setText(""+dataNum);
        text_point_num.setText(""+afterPointInt);
        text_before.setText(data_before);
        text_after.setText(data_after);

    }

    /**
     *设置配置参数弹窗
     * AT+Text<行数>=<全擦除标志><单选有效标识><字符个数><协议类型><项目代号><1位向显示器发送数据标志><1位向互联网发送数据标志><1位显示屏序号><1位显示行号>
     */
    private void setATTextDialog() {
        //设置本弹窗的布局文件
        LayoutInflater inflater = LayoutInflater.from(m_context);
        View configSetView = inflater.inflate(R.layout.set_at_text_layout,null);

        //设置项目
        final Spinner spinner0=configSetView.findViewById(R.id.spinner_project);
        ArrayAdapter<String> arrayAdapterProject=new ArrayAdapter<>(m_context,android.R.layout.simple_spinner_item,projectStrArray);
        spinner0.setAdapter(arrayAdapterProject);
        spinner0.setSelection(projectInt-PROJECT_BASE);

        //设置协议类型
        final Spinner spinner1=configSetView.findViewById(R.id.spinner_agreement);
        ArrayAdapter<String> agreementTypeAdapterProject=new ArrayAdapter<>(m_context,android.R.layout.simple_spinner_item,agreementTypeArray);
        spinner1.setAdapter(agreementTypeAdapterProject);
        spinner1.setSelection(agreementInt-AGREEMENT_BASE);

        //设置行数
        final EditText editText = configSetView.findViewById(R.id.edit_rowNum);
        editText.setHint("请输入行数");
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setText(""+rowsNum);

        //全擦除
        final CheckBox checkErase = configSetView.findViewById(R.id.chk1);
        //单选
        final CheckBox checkSingle = configSetView.findViewById(R.id.chk2);
        //向显示器发送
        final CheckBox checkSendDis = configSetView.findViewById(R.id.chk3);
        //向互联网发送
        final CheckBox checkSendInt = configSetView.findViewById(R.id.chk4);

        if (erase == 1) {
            checkErase.setChecked(true);
        }else {
            checkErase.setChecked(false);
        }
        if (single == 1) {
            checkSingle.setChecked(true);
        }else {
            checkSingle.setChecked(false);
        }
        if (display == 1) {
            checkSendDis.setChecked(true);
        }else{
            checkSendDis.setChecked(false);
        }
        if (internet == 1) {
            checkSendInt.setChecked(true);
        }else{
            checkSendInt.setChecked(false);
        }


        //设置显示屏序号
        final  String[] displayNoStrArray = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16"};
        final Spinner spinner2=configSetView.findViewById(R.id.spinner_display_no);
        ArrayAdapter<String> arrayAdapterDisplayNo=new ArrayAdapter<>(m_context,android.R.layout.simple_spinner_item,displayNoStrArray);
        spinner2.setAdapter(arrayAdapterDisplayNo);
        spinner2.setSelection(displayNoInt - DISPLAY_NO_BASE);

        //前面
        final EditText edtTextBefore = configSetView.findViewById(R.id.edit_before_data);
        //后面
        final EditText edtTextAfter = configSetView.findViewById(R.id.edit_after_data);
        edtTextBefore.setText(data_before);
        edtTextAfter.setText(data_after);

        //设置行号
        final  String[] rowsNoStrArray = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16"};
        final Spinner spinner3=configSetView.findViewById(R.id.spinner_row_no);
        ArrayAdapter<String> arrayAdapterRowsNo=new ArrayAdapter<>(m_context,android.R.layout.simple_spinner_item,rowsNoStrArray);
        spinner3.setAdapter(arrayAdapterRowsNo);
        spinner3.setSelection(rowsNoInt - ROWS_NO_BASE);

        //设置小数点前位数
        final  String[] beforePointStrArray = {"0","1","2","3","4","5"};//无，奇校验，偶校验
        final Spinner spinner4=configSetView.findViewById(R.id.spinner_before_point);
        ArrayAdapter<String> arrayAdapterBeforePoint=new ArrayAdapter<>(m_context,android.R.layout.simple_spinner_item,beforePointStrArray);
        spinner4.setAdapter(arrayAdapterBeforePoint);
        spinner4.setSelection(dataNum);

        //设置小数点后位数
        final   String[] afterPointStrArray = {"0","1","2","3","4","5"};//无，奇校验，偶校验
        final Spinner spinner5=configSetView.findViewById(R.id.spinner_after_point);
        ArrayAdapter<String> arrayAdapterAfterPoint=new ArrayAdapter<>(m_context,android.R.layout.simple_spinner_item,afterPointStrArray);
        spinner5.setAdapter(arrayAdapterAfterPoint);
        spinner5.setSelection(afterPointInt);


        AlertDialog.Builder inputDialogBuilder =
                new AlertDialog.Builder(m_context);
        inputDialogBuilder.setTitle("配置参数设置").setView(configSetView);
        inputDialogBuilder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(editText.getText().toString().equals("")) {
                            Toast.makeText(m_context,
                                    "输入行数不能为空(0-99)",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            projectInt      = (int) spinner0.getSelectedItemId() + PROJECT_BASE;
                            agreementInt    = (int) spinner1.getSelectedItemId() + AGREEMENT_BASE;
                            rowsNum         = Integer.parseInt(editText.getText().toString());
                            displayNoInt    = (int) spinner2.getSelectedItemId() + DISPLAY_NO_BASE;
                            rowsNoInt       = (int) spinner3.getSelectedItemId() + ROWS_NO_BASE;
                            dataNum  = (int) spinner4.getSelectedItemId();
                            afterPointInt   = (int) spinner5.getSelectedItemId();
                            erase = single = display = internet = 0;
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
                            data_before = edtTextBefore.getText().toString();
                            data_after  = edtTextAfter.getText().toString();


                            //设置显示控件显示参数
                            refreshDisplay();



                            Toast.makeText(m_context,
                                    "项目类型" + spinner0.getSelectedItem().toString()
                                            + "擦除标志" + checkErase.isChecked(),
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
        final AlertDialog inputDialog = inputDialogBuilder.create();

        inputDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                final Button btn = ((AlertDialog) dialogInterface).getButton(DialogInterface.BUTTON_POSITIVE);   //*2
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
                            Toast.makeText(m_context, "请输入正确的数值(0-99)", Toast.LENGTH_SHORT).show();
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

    boolean isChecked(){
        return checkbox_at.isChecked();
    }

}
