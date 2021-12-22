package com.lx.controler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

/**
 * Created by pc02 on 2021/11/30.
 */

public class DataTissueConfig extends LinearLayout {
    Context m_context;
    CheckBox data_tissue_special;
    CheckBox data_tissue_general;
    CheckBox data_tissue_modbus;
    CheckBox data_tissue_IIC;
    CheckBox data_tissue_SPI;
    CheckBox checkbox_data_tissue;

    public DataTissueConfig(Context context, AttributeSet attrs){
        super(context, attrs);
        m_context = context;
        // TODO Auto-generated constructor stub
        View view = LayoutInflater.from(context).inflate(R.layout.data_tissue_config, this);
        data_tissue_special =view.findViewById(R.id.data_tissue_special);
        data_tissue_general =view.findViewById(R.id.data_tissue_general);
        data_tissue_modbus  =view.findViewById(R.id.data_tissue_modbus);
        data_tissue_IIC     =view.findViewById(R.id.data_tissue_IIC);
        data_tissue_SPI     =view.findViewById(R.id.data_tissue_SPI);
        checkbox_data_tissue=view.findViewById(R.id.checkbox_data_tissue);
    }

    /**
     *
     *    0x01  自定义专用
     *    0x02  自定义通用
     *    0x08  MODBUS
     *    0x10  IIC
     *    0x20  SPI
     *    例  同时选自定义专用和MODBUS 设置值为：0x01&0x08=09
     * @return
     */
    int getResult(){
        int result;
        int special;
        int general;
        int modbus;
        int IIC;
        int SPI;
        if(data_tissue_special.isChecked()){special=0x01;}else {special = 0;}
        if(data_tissue_general.isChecked()){general=0x02;}else {general = 0;}
        if(data_tissue_modbus.isChecked()){modbus=0x08;}else {modbus = 0;}
        if(data_tissue_IIC.isChecked()){IIC=0x10;}else {IIC = 0;}
        if(data_tissue_SPI.isChecked()){SPI=0x20;}else {SPI = 0;}
        result = special+general+modbus+IIC+SPI;
        return result;
    }

    boolean isChecked(){
        return checkbox_data_tissue.isChecked();
    }
}
