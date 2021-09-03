package com.lx.controler;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class IPEditText extends LinearLayout {
    private EditText firstIPEdit;
    private EditText secondIPEdit;
    private EditText thirdIPEdit;
    private EditText fourthIPEdit;

    private String firstIP;
    private String secondIP;
    private String thirdIP;
    private String fourthIP;

    public IPEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        View view = LayoutInflater.from(context).inflate(R.layout.ip_edit_text, this);

        firstIPEdit = (EditText) view.findViewById(R.id.firstIPfield);
        secondIPEdit = (EditText) view.findViewById(R.id.secondIPfield);
        thirdIPEdit = (EditText) view.findViewById(R.id.thirdIPfield);
        fourthIPEdit = (EditText) view.findViewById(R.id.fourthIPfield);

        setIPEditTextListener(context);
    }

    public void setIPEditTextListener(final Context context){
        //设置第一个字段的事件监听
        firstIPEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                Log.i("test",s.toString());
                if(null!=s && s.length()>0){
                    if(s.length() > 2 || s.toString().trim().contains(".")){
                        if(s.toString().trim().contains(".")){
                            firstIP = s.toString().trim().substring(0,s.length()-1);
                        }else{
                            firstIP = s.toString().trim();
                        }
                        if (Integer.parseInt(firstIP) > 255) {
                            Toast.makeText(context, "IP大小在0-255之间",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        secondIPEdit.setFocusable(true);
                        secondIPEdit.requestFocus();
                    }
                    else
                    {
                        firstIP = s.toString().trim();
                    }
                }else {
                    firstIP = "";
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                firstIPEdit.removeTextChangedListener(this);
                firstIPEdit.setText(firstIP);
                firstIPEdit.setSelection(firstIP.length());
                firstIPEdit.addTextChangedListener(this);
            }
        });
        //设置第二个IP字段的事件监听
        secondIPEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if(null!=s && s.length()>0){
                    if(s.length() > 2 || s.toString().trim().contains(".")){
                        if(s.toString().trim().contains(".")){
                            secondIP = s.toString().trim().substring(0,s.length()-1);

                        }else{
                            secondIP = s.toString().trim();
                        }
                        if (Integer.parseInt(secondIP) > 255) {
                            Toast.makeText(context, "IP大小在0-255之间",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        thirdIPEdit.setFocusable(true);
                        thirdIPEdit.requestFocus();
                    }
                    else
                    {
                        secondIP = s.toString().trim();
                    }

                }
                else {
                    secondIP = "";
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                secondIPEdit.removeTextChangedListener(this);
                secondIPEdit.setText(secondIP);
                secondIPEdit.setSelection(secondIP.length());
                secondIPEdit.addTextChangedListener(this);
            }
        });
        //设置第三个IP字段的事件监听
        thirdIPEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

                if(null!=s && s.length()>0){
                    if(s.length() > 2 || s.toString().trim().contains(".")){
                        if(s.toString().trim().contains(".")){
                            thirdIP = s.toString().trim().substring(0,s.length()-1);

                        }else{
                            thirdIP = s.toString().trim();
                        }
                        if (Integer.parseInt(thirdIP) > 255) {
                            Toast.makeText(context, "IP大小在0-255之间",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        fourthIPEdit.setFocusable(true);
                        fourthIPEdit.requestFocus();
                    }else{
                        thirdIP = s.toString().trim();
                    }

                }else {
                    thirdIP = "";
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                thirdIPEdit.removeTextChangedListener(this);
                thirdIPEdit.setText(thirdIP);
                thirdIPEdit.setSelection(thirdIP.length());
                thirdIPEdit.addTextChangedListener(this);

            }
        });
        //设置第四个IP字段的事件监听
        fourthIPEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if(null!=s && s.length()>0){
                    fourthIP = s.toString().trim();
                    if (Integer.parseInt(fourthIP) > 255) {
                        Toast.makeText(context, "请输入合法的ip地址", Toast.LENGTH_LONG)
                                .show();
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
    }

    public String getText() {
        if (TextUtils.isEmpty(firstIP) || TextUtils.isEmpty(secondIP)
                || TextUtils.isEmpty(thirdIP) || TextUtils.isEmpty(fourthIP)) {
            return null;
        }
        return firstIP + "." + secondIP + "." + thirdIP + "." + fourthIP;
    }

    public int getInt(int Index){
        int IP = 0;
        if(Index == 1) {
            IP = Integer.parseInt(firstIP);
        }
        if(Index == 2) {
            IP = Integer.parseInt(secondIP);
        }
        if(Index == 3) {
            IP = Integer.parseInt(thirdIP);
        }
        if(Index == 4) {
            IP = Integer.parseInt(fourthIP);
        }
        return IP;
    }
}