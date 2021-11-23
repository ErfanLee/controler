package com.lx.controler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.LinkedList;

/**
 * Created by pc02 on 2021/11/23.
 */

public class ATTextConfigList extends LinearLayout {
    Context m_context;
    LinearLayout linear_atList;
    private LinkedList<ATTextConfig> listAT;//存储串口设置控件的链表
    public int atNum = 0;//参数设置控件总数(初始为0个）
    public ATTextConfigList(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        m_context = context;
        // TODO Auto-generated constructor stub
        View view = LayoutInflater.from(context).inflate(R.layout.at_text_config_list, this);
        linear_atList = view.findViewById(R.id.linear_atList);
        listAT = new LinkedList<>();
        ImageButton btn_add = view.findViewById(R.id.btn_atConfigAdd);
        ImageButton btn_del = view.findViewById(R.id.btn_atConfigDel);
        addATConfig(context, attrs);
        btn_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                addATConfig(context, attrs);
            }
        });
        btn_del.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                delATConfig();
            }
        });
    }
        //
        void addATConfig(Context context, AttributeSet attrs){
            ATTextConfig atConfig = new ATTextConfig(context,attrs);
            listAT.add(atConfig);
            linear_atList.addView(atConfig);
            atNum += 1;
        }

        void delATConfig(){
            if(atNum>1){
                linear_atList.removeView(listAT.get(atNum-1));
                listAT.remove(atNum-1);
                atNum -=1;
            }
        }

        public ATTextConfig get(int index){
            return listAT.get(index);
        }
}
