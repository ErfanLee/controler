package com.lx.controler;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by pc02 on 2021/9/14.
 */

public class ConfigActivity extends AppCompatActivity{

    public static Context context;//上下文

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gate_way_congfig);
        context = this;

    }
}
