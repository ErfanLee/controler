package com.lx.controler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

/**
 * 数据透传接口
 * Created by pc02 on 2021/12/5.
 *
 */

public class DataTransmission extends LinearLayout {
    Context m_context;
    CheckBox checkBoxUsart1;
    CheckBox checkBoxUsart2;
    CheckBox checkBoxUsart3;
    CheckBox checkBoxUsart4;
    CheckBox checkBoxUsart5;
    CheckBox checkBoxUsart6;
    CheckBox checkBoxIIC;
    CheckBox checkBoxSPI;
    CheckBox checkbox_data_transmission;
    public DataTransmission(final Context context,final  AttributeSet attrs){
        super(context, attrs);
        m_context = context;
        // TODO Auto-generated constructor stub
        View view = LayoutInflater.from(context).inflate(R.layout.data_transmission_config, this);
        checkBoxUsart1 =view.findViewById(R.id.data_transmission_usart1);
        checkBoxUsart2 =view.findViewById(R.id.data_transmission_usart2);
        checkBoxUsart3 =view.findViewById(R.id.data_transmission_usart3);
        checkBoxUsart4 =view.findViewById(R.id.data_transmission_usart4);
        checkBoxUsart5 =view.findViewById(R.id.data_transmission_usart5);
        checkBoxUsart6 =view.findViewById(R.id.data_transmission_usart6);
        checkBoxIIC =view.findViewById(R.id.data_transmission_IIC);
        checkBoxSPI =view.findViewById(R.id.data_transmission_SPI);
        checkbox_data_transmission=view.findViewById(R.id.checkbox_data_transmission);

    }
    /**
     *
     * “数据透传接口”
     * 0X01 串口1
     * 0X02 串口2
     * 0X04 串口3
     * 0X08 串口4
     * 0X10 串口5
     * 0X20 串口6
     * 0X40  IIC接口
     * 0X80  SPI接口
     * @return 例  同时选同时选串口1 串口2和SPI 为透传接口 设置值为：0x01&0x02&0x80=0x89
     */
    int getResult(){
        int usart1;
        int usart2;
        int usart3;
        int usart4;
        int usart5;
        int usart6;
        int IIC;
        int SPI;
        int result;
        if(checkBoxUsart1.isChecked()){usart1=0x01;}else {usart1 = 0;}
        if(checkBoxUsart2.isChecked()){usart2=0x02;}else {usart2 = 0;}
        if(checkBoxUsart3.isChecked()){usart3=0x04;}else {usart3 = 0;}
        if(checkBoxUsart4.isChecked()){usart4=0x08;}else {usart4 = 0;}
        if(checkBoxUsart5.isChecked()){usart5=0x10;}else {usart5 = 0;}
        if(checkBoxUsart6.isChecked()){usart6=0x20;}else {usart6 = 0;}
        if(checkBoxIIC.isChecked()){IIC=0x40;}else {IIC = 0;}
        if(checkBoxSPI.isChecked()){SPI=0x80;}else {SPI = 0;}
        result = usart1+usart2+usart3+usart4+usart5+usart6+IIC+SPI;
        return result;
    }

    boolean isChecked(){
        return checkbox_data_transmission.isChecked();
    }
}
