package com.lx.controler;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by pc02 on 2021/11/1.
 */

public class SocketClient {
    public Socket socket;
    String ipString;
    int    port;
    SocketClient(final String m_ipString,final int m_port){
        new Thread(new Runnable(){
            @Override
            public void run() {
                try{
                    socket = new Socket(m_ipString,m_port);
                    ipString = m_ipString;
                    port = m_port;
                }catch (Exception e){

                }
            }
        }).start();
    }

    public boolean isConnected(){
        try {
            return socket.isConnected();
        }catch (Exception e){

        }
        return false;
    }

    public  void send(final String msg){

        new Thread(new Runnable(){
            @Override
            public void run() {
                try
                {
                    if(!socket.isConnected())
                    {
                        socket = new Socket(ipString,port);
                    }
                        //向服务器端发送消息
                        PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);
                        out.println(msg);

                        //接收来自服务器端的消息
                        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String msg = br.readLine();

                        if ( msg != null )
                        {
                            Log.i("DebugTag", "msg");

                        }
                        else
                        {

                        }
                        //关闭流
                        out.close();
                        br.close();

                }
                catch (Exception e)
                {
                    // TODO: handle exception
                    Log.e("DebugTag", e.toString());
                }
            }
        }).start();
    }

}
