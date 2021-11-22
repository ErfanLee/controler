package com.lx.controler;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by pc02 on 2021/11/19.
 */

public class UsartConfig extends LinearLayout {
    Context m_context;
    TextView text_usart;//串口号
    TextView text_bound;//波特率
    TextView text_byte_length;//字节长度
    TextView text_stop_bit;//停止位
    TextView text_check_bit;//校验位
    CheckBox checkbox_bound;
    public int USARTx;
    public int Boundx;
    public int length;
    public int stop;
    public int check;
    final  String[] USARTxStrArray = {"1","2","3","4","5","6"};
    final  String[] BaudStrArray = {"300","600","1200","2400","4800","9600","14400","19200","38400","57600","115200"};
    final  String[] lengthStrArray = {"5","6","7","8"};
    final  String[] stopStrArray = {"1","2"};
    final  String[] checkStrArray = {"NONE","ODD","EVEN"};//无，奇校验，偶校验
    public UsartConfig(Context context, AttributeSet attrs){
        super(context, attrs);
        m_context = context;
        // TODO Auto-generated constructor stub
        View view = LayoutInflater.from(context).inflate(R.layout.usart_config, this);
        text_usart = view.findViewById(R.id.text_usart);
        text_bound = view.findViewById(R.id.text_bound);
        text_byte_length = view.findViewById(R.id.text_byte_length);
        text_stop_bit = view.findViewById(R.id.text_stop_bit);
        text_check_bit=  view.findViewById(R.id.text_check_bit);
        checkbox_bound  = view.findViewById(R.id.checkbox_bound);
        setParameter(0,0,0,0,0);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setUsartConfigDialog();
            }
        });
    }

    public void setParameter(final int m_USARTx,final int m_Boundx,final int m_length,final int m_stop,final int m_check){
        USARTx = m_USARTx;
        Boundx = m_Boundx;
        length = m_length;
        stop = m_stop;
        check = m_check;
        text_usart.setText(USARTxStrArray[USARTx]);
        text_bound.setText(BaudStrArray[Boundx]);
        text_byte_length.setText(lengthStrArray[length]);
        text_stop_bit.setText(stopStrArray[stop]);
        text_check_bit.setText(checkStrArray[check]);
    }

    /**
     * 设置串口弹窗
     */
    private void setUsartConfigDialog() {
        //设置本弹窗的布局文件
        LayoutInflater inflater = LayoutInflater.from(m_context);
        View usartSetView = inflater.inflate(R.layout.usart_dialog,null);
        //下拉框
        final Spinner spinner_usart        = usartSetView.findViewById(R.id.spinner_usart);
        final Spinner spinner_bound        = usartSetView.findViewById(R.id.spinner_bound);
        final Spinner spinner_byte_length  = usartSetView.findViewById(R.id.spinner_byte_length);
        final Spinner spinner_stop_bit     = usartSetView.findViewById(R.id.spinner_stop_bit);
        final Spinner spinner_check_bit    = usartSetView.findViewById(R.id.spinner_check_bit);

       spinner_usart.setSelection(USARTx);
       spinner_bound.setSelection(Boundx);
       spinner_byte_length.setSelection(length);
       spinner_stop_bit.setSelection(stop);
       spinner_check_bit.setSelection(check);

        AlertDialog.Builder inputDialogBuilder =
                new AlertDialog.Builder(m_context);
        inputDialogBuilder.setTitle("工作模式设置").setView(usartSetView);
        inputDialogBuilder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setParameter((int)spinner_usart.getSelectedItemId(),
                                     (int)spinner_bound.getSelectedItemId(),
                                     (int)spinner_byte_length.getSelectedItemId(),
                                     (int)spinner_stop_bit.getSelectedItemId(),
                                     (int)spinner_check_bit.getSelectedItemId());
                    }
                }).show();


    }

    boolean isChecked(){
        return checkbox_bound.isChecked();
    }

}
