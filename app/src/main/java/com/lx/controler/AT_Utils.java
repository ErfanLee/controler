package com.lx.controler;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Locale;

/**
 * Created by pc01 on 2021/7/31.
 */

public class AT_Utils {
    /**
     * 设置显示刷新时间
     * @param time 刷新时间(ms)
     */
    static void sendSetDisplayTime(final int time){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+SetDisplayTime=B0";
                command += int2HexString(time);
                MyMqttService.publish(command);
            }
        });
        thread.start();
    }

    /**
     * 设置波特率
     * @param USARTx 串口号
     * @param Boundx 波特率
     * @param length 字节长度
     * @param stop   停止位
     * @param check  校验位
     */
    static void sendBaud(final int USARTx,final int Boundx,final int length,final int stop,final int check){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+Bound=#";
                command = command + USARTx + Boundx + length + stop + check;
                MyMqttService.publish(command);
            }
        });
        thread.start();
    }

    /**
     * 发送发送信息请求
     */
    static void sendGetmessgeRequest(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+GetmessgeRequest";
                MyMqttService.publish(command);
            }
        });
        thread.start();
    }

    /**
     * 发送复位请求
     */
    static void sendResetRequest(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+ResetRequest";
                MyMqttService.publish(command);
            }
        });
        thread.start();
    }

    /**
     * 发送休眠请求
     */
    static void sendSleepRequest(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+SleepRequest";
                MyMqttService.publish(command);
            }
        });
        thread.start();
    }

    /**
     * 发送停机请求
     */
    static void sendStopRequest(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+StopRequest";
                MyMqttService.publish(command);
            }
        });
        thread.start();
    }




    /**
     * 设置工作模式
     * @param m_mode 工作模式 1 2 3
     */
    static void sendWorkMode(final int m_mode){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+Bound=B0";
                command = command + String.format(Locale.US,"%02d", m_mode);
                MyMqttService.publish(command);
            }
        });
        thread.start();
    }

    /**
     * AT+Text<行数>=<全擦除标志><单选有效标识><字符个数><协议类型><项目代号><1位向显示器发送数据标志><1位向互联网发送数据标志><1位显示屏序号><1位显示行号>
     * @param rowsNum 行数
     * @param erase 全擦除标志
     * @param singleChoice 单选有效标志
     * @param agreementType 协议类型
     * @param projectNo 项目代号
     * @param sendDisplay 1位向显示器发送数据标志
     * @param sendInternet 1位向互联网发送数据标志
     * @param displayNo 1位显示屏序号
     * @param rowNo 1位显示行号
     */
    static void sendConfigText(int rowsNum,int erase,int singleChoice,int agreementType,int projectNo,int sendDisplay,int sendInternet,int displayNo,int rowNo,int dataNum,int afterPoint,String text1,String text2){

        final String configStr1 = "AT+Text"+String.format(Locale.US,"%02d", rowsNum) +"="+erase+singleChoice;

        try {
            String strGBK1 = URLEncoder.encode(text1,"GBK");
            String strGBK2 = URLEncoder.encode(text2,"GBK");
            int beforePoint = dataNum-afterPoint;//小数点前几位
            String pointFormat = "";
            for(int i = (5-dataNum);i<5;i++){
                if((i-(5-dataNum)) == beforePoint){
                    pointFormat += ".";
                }
                pointFormat = pointFormat+"#"+i;
            }

            final String configStr2 = ""+agreementType+projectNo+sendDisplay+sendInternet+displayNo+rowNo+strGBK1+pointFormat+strGBK2;
            final int bytesNum = configStr1.length()+configStr2.length();

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String command = configStr1 + bytesNum + configStr2;

                    MyMqttService.publish(command);
                }
            });
            thread.start();
        }catch (Exception e){

        }

    }


    /**
     * int值转为hex形式的字符串
     * @param value int值
     * @return String类型的字符串
     */
    private static String int2HexString(int value){
        return String.format(Locale.US,"%08x",value);
    }
}
