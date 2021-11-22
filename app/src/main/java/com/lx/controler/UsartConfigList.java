package com.lx.controler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.LinkedList;

/**
 * Created by pc02 on 2021/11/20.
 * UsartConfigList
 * 多个串口设置控件合并的串口设置链表控件
 */

public class UsartConfigList extends LinearLayout{
    Context m_context;
    LinearLayout linear_usartList;
    private LinkedList<UsartConfig> listUsart;//存储串口设置控件的链表
    public int usartNum = 0;//串口设置总数(初始为0个）
    public UsartConfigList(final Context context, final AttributeSet attrs){
        super(context, attrs);
        m_context = context;
        // TODO Auto-generated constructor stub
        View view = LayoutInflater.from(context).inflate(R.layout.usart_config_list, this);
        linear_usartList = view.findViewById(R.id.linear_usartList);
        listUsart = new LinkedList<>();
        ImageButton btn_add = view.findViewById(R.id.btn_usartConfigAdd);
        ImageButton btn_del = view.findViewById(R.id.btn_usartConfigDel);
        addUsartConfig(context, attrs);
        btn_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                addUsartConfig(context, attrs);
            }
        });
        btn_del.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                delUsartConfig();
            }
        });

    }
    //
    void addUsartConfig(Context context, AttributeSet attrs){
        UsartConfig usartConfig = new UsartConfig(context,attrs);
        listUsart.add(usartConfig);
        linear_usartList.addView(usartConfig);
        usartNum += 1;
    }

    void delUsartConfig(){
        if(usartNum>1){
            linear_usartList.removeView(listUsart.get(usartNum-1));
            listUsart.remove(usartNum-1);
            usartNum -=1;
        }
    }

    public UsartConfig get(int index){
        return listUsart.get(index);
    }

}

