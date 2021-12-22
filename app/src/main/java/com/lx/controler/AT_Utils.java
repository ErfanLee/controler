package com.lx.controler;
import java.net.URLEncoder;
import java.util.Locale;

/**
 * Created by pc01 on 2021/7/31.
 */

public class AT_Utils {

    public static boolean isMQTT  = true;


    static SocketClient socketClient;

    static void setPort(final String ipString,final int port){
        if(socketClient!=null){
            if(socketClient.isConnected()){
                return;
            }
        }
        socketClient = new SocketClient(ipString,port);
    }

    static boolean getWifiState(){
        return socketClient.isConnected();
    }

    static void sendByWifi(final String message){

        socketClient.send(message);
    }

    static void sendAtCommand(final String com){
        if(isMQTT) {
            MyMqttService.publish(com);
        }else
        {
            sendByWifi(com);
        }
    }

    /**
     * 发送单选类型的指令
     * @param m_command 指令内容
     * @param x 选项x
     */
    static void sendSelectCommand(final String m_command,final int x){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+"+m_command+"=@";
                command = command + String.format(Locale.US,"%02d", x);

                    sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * 发送四位数字参数
     * @param m_command 指令内容
     * @param x 数字
     */
    static void send4NumberCommand(final String m_command,final int x){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+"+ m_command +"=@";
                command = command + String.format(Locale.US,"%04d", x);
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * 发送String参数类型的指令
     * @param m_command 指令内容
     * @param text 选项x
     */
    static void textCommand(final String m_command,final String text){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+"+m_command+"=#";
                command = command + text;
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * 设置时间间隔
     * @param time 刷新时间(ms)
     */
    static void SetTime(final int time,final String m_command){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+"+ m_command +"=@";
                command += int2HexString(time);
                sendAtCommand(command);
            }
        });
        thread.start();
    }


    /**
     * 发送预警值或报警值
     * @param value 预警值
     */
    static void WarningAlarmValue(final int project,final int value,final String m_command){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+"+m_command+"="+project+"@";
                command += int2HexString(value);
                sendAtCommand(command);
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
    static void Baud(final int USARTx,final int Boundx,final int length,final int stop,final int check){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+Bound=#";
                command = command + USARTx + Boundx + length + stop + check;
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * 发送发送信息请求
     */
    static void GetmessgeRequest(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+GetmessgeRequest";
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * 发送复位请求
     */
    static void ResetRequest(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+ResetRequest";
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * 发送休眠请求
     */
    static void SleepRequest(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+SleepRequest";
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * 发送停机请求
     */
    static void StopRequest(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+StopRequest";
                sendAtCommand(command);
            }
        });
        thread.start();
    }




    /**
     * 设置工作模式
     * @param m_mode 工作模式 1 2 3
     */
    static void WorkMode(final int m_mode){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+WorkMode=@";
                command = command + String.format(Locale.US,"%02d", m_mode);
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * 设置连接模式
     * @param m_mode 1,公网模组；2，网口，3,WiFi,4热点
     */
    static void ConnectMode(final int m_mode){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+ConnectMode=@";
                String mode = "";
                if(m_mode == 0){
                    mode="01";
                }
                if(m_mode == 1){
                    mode="02";
                }
                if(m_mode == 2){
                    mode="20";
                }
                if(m_mode == 3){
                    mode="80";
                }
                command = command + mode;
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * 设置主从模式
     * @param m_mode 1：主机；2：从机
     */
    static void MasterSlaveMode(final int m_mode){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+MasterSlaveMode=@";
                command = command + String.format(Locale.US,"%02d", m_mode);
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * 设置功能模式
     * @param m_mode 1：主机；2：从机
     */
    static void FunctionMode(final int m_mode){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+FunctionMode=@";
                command = command + String.format(Locale.US,"%02d", m_mode);
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * 设置数据传输模式
     * @param m_mode 1：数据透传；2：定点传输；3：广播式发送
     */
    static void TransferMode(final int m_mode){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+TransferMode=@";
                command = command + String.format(Locale.US,"%02d", m_mode);
                sendAtCommand(command);
            }
        });
        thread.start();
    }



    /**
     * 设置显示驱动卡
     * @param m_mode 0：中航卡；1：灵信卡；
     */
    static void DisplayMode(final int m_mode){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+DisplayMode=@";
                command = command + String.format(Locale.US,"%02d", m_mode);
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * 设置分组数目
     * @param m_project 项目代号
     */
    static void GroupNumber(final int m_project){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+GroupNumber=@";
                command = command + String.format(Locale.US,"%02d", m_project);
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * 设置上云目的地址
     * @param m_target 1：阿里云；2自有服务器
     */
    static void ConnectTarget(final int m_target){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+ConnectTarget=@";
                command = command + String.format(Locale.US,"%02d", m_target);
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * 设置WIFI模式
     * @param m_mode 1、直连模式；2、LAN模式
     */
    static void WifiMode(final int m_mode){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+WifiMode=@";
                command = command + String.format(Locale.US,"%02d", m_mode);
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * 设置WIFI名称
     * @param m_name wifi名称
     * */
    static void WifiName(final String m_name){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+WifiName=#";
                command = command + m_name;
                sendAtCommand(command);
            }
        });
        thread.start();
    }
    /**
     * 设置WIFI密码
     * @param m_password WIFI密码
     * */
    static void WifiPassword(final String m_password){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+WifiPassword=#";
                command = command + m_password;
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * 设置数据传输协议
     * @param m_protocol 1：SPI ；2：IIC；3：232
     */
    static void TransferProtocol(final int m_protocol){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+TransferProtocol=@";
                command = command + String.format(Locale.US,"%02d", m_protocol);
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    static void DataTissue(final int m_dataTissue){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+DataTissue=@";
                command = command + String.format(Locale.US,"%02x", m_dataTissue);
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    static void DataTransmission(final int m_DataTransmission){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+DataTransmission=@";
                command = command + String.format(Locale.US,"%02x", m_DataTransmission);
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    static void DataPrint(final int m_DataPrint){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+DataPrint=@";
                command = command + String.format(Locale.US,"%02x", m_DataPrint);
                sendAtCommand(command);
            }
        });
        thread.start();
    }



    /**
     * 设置上云间隔时间
     * @param time 时间(ms)
     */
    static void SendToInternetTime(final int time){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+SendToInternetTime=@";
                command += int2HexString(time);
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * 设置采集信息间隔
     * @param time 时间(ms)
     */
    static void GetMegTime(final int time){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+GetMegTime=@";
                command += int2HexString(time);
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * 设置休眠时间设定
     * @param time 时间(ms)
     */
    static void SleepTime(final int time){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+SleepTime=@";
                command += int2HexString(time);
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * 分组ID设置
     * @param ID 分组ID
     */
    static void GroupID(final int ID){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+GroupID=@";
                command = command + String.format(Locale.US,"%04d", ID);
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * 用户ID设置
     * @param ID 用户ID
     */
    static void UnitID(final int ID){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+UnitID=@";
                command = command + String.format(Locale.US,"%04d", ID);
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * 设置预警值
     * @param value 预警值
     */
    static void WarningValue(final int project,final int value){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+WarningValue="+project+"@";
                command += int2HexString(value);
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * 设置报警值
     * @param value 报警值
     */
    static void AlarmValue(final int project,final int value){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+AlarmValue="+project+"@";
                command += int2HexString(value);
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * 产品名称设置
     * @param m_name 产品名称设置
     * */
    static void ProduceName(final String m_name){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+ProduceName=#";
                command = command + m_name;
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * 位置信息
     * @param x 经度
     * @param y 纬度
     */
    static void location(final String strX,final String strY){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+location=#";
                command = command + strX + "_" + strY;
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * IP地址A设置
     * @param ip1 ip第一段
     * @param ip2 ip第二段
     * @param ip3 ip第三段
     * @param ip4 ip第四段
     * @param port 端口号
     */
    static void IPA_B(final int ip1,final int ip2,final int ip3,final int ip4,final int port,final String m_command){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+"+m_command+"=#";
                String ip1Str = String.format(Locale.US,"%03d", ip1);
                String ip2Str = String.format(Locale.US,"%03d", ip2);
                String ip3Str = String.format(Locale.US,"%03d", ip3);
                String ip4Str = String.format(Locale.US,"%03d", ip4);
                String portStr= String.format(Locale.US,"%05d", port);
                command = command + ip1Str + ip2Str + ip3Str + ip4Str + portStr;
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * IP地址B设置
     * @param ip1 ip第一段
     * @param ip2 ip第二段
     * @param ip3 ip第三段
     * @param ip4 ip第四段
     * @param port 端口号
     */
    static void IPB(final int ip1,final int ip2,final int ip3,final int ip4,final int port){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+IPB=#";
                String ip1Str = String.format(Locale.US,"%03d", ip1);
                String ip2Str = String.format(Locale.US,"%03d", ip2);
                String ip3Str = String.format(Locale.US,"%03d", ip3);
                String ip4Str = String.format(Locale.US,"%03d", ip4);
                String portStr= String.format(Locale.US,"%05d", port);
                command = command + ip1Str + ip2Str + ip3Str + ip4Str + portStr;
                sendAtCommand(command);
            }
        });
        thread.start();
    }


    /**
     * 产品密钥设置(上云为阿里时有效）
     * @param m_key 产品名称设置
     * */
    static void ProduceKey(final String m_key){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+ProduceKey=#";
                command = command + m_key;
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * 设备名称设置(上云为阿里时有效）
     * @param m_name 产品名称设置
     * */
    static void DeviceName(final String m_name){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+DeviceName=#";
                command = command + m_name;
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * 设备秘钥设置(上云为阿里时有效）
     * @param m_secret 设备秘钥
     * */
    static void Devicesecret(final String m_secret){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+Devicesecret=#";
                command = command + m_secret;
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * 设置发布名称
     * @param m_topic 设置发布名称
     * */
    static void PubTopic(final String m_topic){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+PubTopic=#";
                command = command + m_topic;
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * 设置订阅消息名称
     * @param m_topic 订阅消息名称
     * */
    static void SubTopic(final String m_topic){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+SubTopic=#";
                command = command + m_topic;
                sendAtCommand(command);
            }
        });
        thread.start();
    }

    /**
     * 询问模块编号
     * */
    static void IMEI(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "AT+IMEI?";
                sendAtCommand(command);
            }
        });
        thread.start();
    }




    /**
     * AT+Text=<全擦除标志><单选有效标识><字符个数><协议类型><项目代号><1位向显示器发送数据标志><1位向互联网发送数据标志><1位显示屏序号><1位显示行号>
     * @param rowNum
     * @param erase 全擦除标志
     * @param singleChoice 单选有效标志
     * @param agreementType 协议类型
     * @param projectNo 项目代号
     * @param sendDisplay 1位向显示器发送数据标志
     * @param sendInternet 1位向互联网发送数据标志
     * @param displayNo 1位显示屏序号
     * @param rowNo 1位显示行号
     */
    static void ConfigText(int rowNum,int erase,int singleChoice,int agreementType,int projectNo,int sendDisplay,int sendInternet,int displayNo,int rowNo,int dataNum,int afterPoint,String text1,String text2){

        final String configStr1 = "AT+Text"+String.format(Locale.US,"%02d", rowNum)+"="+erase+singleChoice;

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

            //显示屏号和行号码用16进制显示
            final String configStr2 = ""+agreementType+projectNo+sendDisplay+sendInternet+String.format(Locale.US,"%x",displayNo)+String.format(Locale.US,"%x",rowNo)+strGBK1+pointFormat+strGBK2;
            final int bytesNum = strGBK1.length()+strGBK2.length();

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String command = configStr1 + String.format(Locale.US,"%02d", bytesNum) + configStr2;

                    sendAtCommand(command);
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
