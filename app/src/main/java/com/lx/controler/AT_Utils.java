package com.lx.controler;

/**
 * Created by pc01 on 2021/7/31.
 */

public class AT_Utils {
    /**
     * 设置显示刷新时间
     * @param time 刷新时间(ms)
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
     * AT+Text<行数>=<全擦除标志><单选有效标识><字符个数><协议类型><项目代号><1位向显示器发送数据标志><1位向互联网发送数据标志><1位显示屏序号><1位显示行号>
     * @param rowsNum 行数
     * @param erase 全擦除标志
     * @param singleChoice 单选有效标志
     * @param bytesNum 字符个数
     * @param agreementType 协议类型
     * @param projectNo 项目代号
     * @param sendDisplay 1位向显示器发送数据标志
     * @param sendInternet 1位向互联网发送数据标志
     * @param display 1位显示屏序号
     * @param rowNo 1位显示行号
     */
    public static void sendConfigText(int rowsNum,boolean erase,boolean singleChoice,int bytesNum,int agreementType,int projectNo,boolean sendDisplay,boolean sendInternet,int display,int rowNo){

    }


    /**
     * int值转为hex形式的字符串
     * @param value int值
     * @return String类型的字符串
     */
    private static String int2HexString(int value){
        return String.format("%08x",value);
    }
}
