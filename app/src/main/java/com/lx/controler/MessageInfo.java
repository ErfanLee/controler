package com.lx.controler;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by pc01 on 2021/6/21.
 * 项目类
 * tag标签 value值 unit 单位
 */

public class MessageInfo {
    private String tag;          //标识符
    private double  nowValue = 0.0;//当前值
    private String name = "名称";//项目显示名称
    private double  maxValue = 240.0;//满量程
    private double  minValue = 0.0;//最小值
    private double  warningVlaue;//预警值
    private double  alarmValue;  //报警值
    private String unit;        //单位
    MessageInfo(String m_tag,String m_unit){
        setTag(m_tag);
        setUnit(m_unit);
    }
    public void setName(String m_name){name = m_name;}
    public String getName(){
        return name;
    }
    public void setTag(String m_tag){tag = m_tag;}
    public String getTag(){
        return tag;
    }
    public void setMaxValue(double m_maxValue){maxValue = m_maxValue;}
    public Double getMaxValue(){return maxValue;}

    public void setMinValue(double m_minValue){minValue = m_minValue;}
    public double getMinValue(){return minValue;}

    public void setNowValue(double m_nowValue){nowValue = m_nowValue;}
    public double getNowValue(){return nowValue;}
    public void setUnit(String m_unit){unit = m_unit;}
    public String getUnit(){return unit;}
}
