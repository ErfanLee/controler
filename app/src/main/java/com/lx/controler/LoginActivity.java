package com.lx.controler;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    private AutoCompleteTextView ipAddressView;
    private AutoCompleteTextView userNameView;
    private EditText mPasswordView;
    private AutoCompleteTextView touristView;
    private AutoCompleteTextView responseView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 获取控件对象
        ipAddressView   = (AutoCompleteTextView) findViewById(R.id.text_ip);
        userNameView    = (AutoCompleteTextView) findViewById(R.id.text_username);
        mPasswordView   = (EditText)             findViewById(R.id.text_password);
        touristView     = (AutoCompleteTextView) findViewById(R.id.text_tourist);
        responseView    = (AutoCompleteTextView) findViewById(R.id.text_response);

        this.setTitle(R.string.title_activity_login);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        //设置按键
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    /**
     * 登录
     */
    private void attemptLogin() {
        // Reset errors.
        ipAddressView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String ipAddress    = ipAddressView.getText().toString();
        String userName     = userNameView.getText().toString();
        String password    = mPasswordView.getText().toString();
        String tourist      = touristView.getText().toString();
        String response     = responseView.getText().toString();
        Toast.makeText(LoginActivity.this,
                ipAddress + userName + password + tourist + response,
                Toast.LENGTH_SHORT).show();
        MyMqttService.config(ipAddress,userName,password,tourist,response);
        Intent intent = new Intent(this,ScrollingActivity.class);
        startActivity(intent);
    }

}

