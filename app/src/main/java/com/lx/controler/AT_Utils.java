package com.lx.controler;

/**
 * Created by pc01 on 2021/7/31.
 */

public class AT_Utils {
    /**
     * 设置显示刷新时间
     * @param time
     */
    public static void sendSetDisplayTime(final int time){
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
    public static void sendBaud(final int USARTx,final int Baudx,final int length,final int stop,final int check){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+Bound=#";
                command = command + USARTx + Baudx + length + stop + check;
                MyMqttService.publish(command);
            }
        });
        thread.start();
    }

    /**
     * int值转为hex形式的字符串
     * @param value
     * @return
     */
    private static String int2HexString(int value){
        return String.format("%08x",value);
    }
}
