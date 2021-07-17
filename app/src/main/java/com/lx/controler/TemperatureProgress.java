package com.lx.controler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.nfc.Tag;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Map;

/**
 * Created by JF on 2016/10/10.
 * 根据温度绘制圆形的View
 * 图形类似石英钟表，起点在12点
 */
public class TemperatureProgress extends View{

    //会话相关的变量
    private Paint circlePaint = new Paint();                //画圆的笔
    private Paint arcPaint = new Paint();                   //画弧线的笔
    private Paint nowTemperaturePaint = new Paint();        //绘制中间当前温度的笔
    private Paint maxAndMinTemperaturePaint = new Paint();  //绘制最大温度以及最小温度的笔
    private RectF arcRectF = new RectF();                   //画弧线用的矩形
    private float nowTemperatureTextY;                      //绘制中间当前温度文字用的Y轴坐标
    private int circleWidth = 60;                           //圆的宽度，也是弧线的宽度

    //温度值
    private int nowTemperature = 1;                    //当前温度

    //显示用信息类
    private MessageInfo msgInfo = new MessageInfo("TAG","");
    //绘制进度，在重绘布局的时候用来记录绘制进度，该变量每次增加一角度
    private int degreeProgress = 0;
    //为true时才在"onDraw()"方法里面执行绘制，用来控制在用户设置温度前不要绘制
    private boolean isDraw = false;

    public TemperatureProgress(Context context) {
        super(context);
        initPaint();
    }

    public TemperatureProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public void setName(String m_name) {
        msgInfo.setName(m_name);
    }

    //初始化各个画笔
    private void initPaint(){
        //绘制圆圈的画笔
        circlePaint.setColor(Color.BLUE);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(circleWidth);

        //绘制弧线的画笔
        arcPaint.setColor(Color.RED);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(circleWidth);

        //绘制当前温度值的画笔
        nowTemperaturePaint = new Paint();
        nowTemperaturePaint.setColor(Color.BLACK);
        nowTemperaturePaint.setTextSize(50);
        nowTemperaturePaint.setStyle(Paint.Style.FILL);
        nowTemperaturePaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = nowTemperaturePaint.getFontMetrics();
        nowTemperatureTextY = (-fontMetrics.top-fontMetrics.bottom)/2;//计算基线Y轴坐标

        //绘制最大最小温度值的画笔
        maxAndMinTemperaturePaint = new Paint();
        maxAndMinTemperaturePaint.setColor(Color.BLACK);
        maxAndMinTemperaturePaint.setTextSize(50);
        maxAndMinTemperaturePaint.setStyle(Paint.Style.FILL);
        maxAndMinTemperaturePaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics2 = maxAndMinTemperaturePaint.getFontMetrics();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getMeasuredSize(widthMeasureSpec), getMeasuredSize(heightMeasureSpec));
    }

    //返回控件最终尺寸
    private int getMeasuredSize(int measureSpec){
        int measuredMode = MeasureSpec.getMode(measureSpec);
        int measuredSize = 500;
        if(measuredMode == MeasureSpec.EXACTLY){
            measuredSize = MeasureSpec.getSize(measureSpec);
        }else if(measuredMode == MeasureSpec.AT_MOST){
            measuredSize = Math.min(measuredSize, MeasureSpec.getSize(measureSpec));
        }
        return measuredSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(!isDraw){
            super.onDraw(canvas);
            return;
        }
        int halfWidth = getMeasuredWidth()/2;
        int halfHeight = getMeasuredHeight()/2;
        //绘制原理：先画圆形，再画弧线，不断变化弧线扫过角度，以此实现温度动态变化效果
        canvas.translate(halfWidth, halfHeight);
        //画圆形
        canvas.drawCircle(0, 0, halfWidth-circleWidth/2, circlePaint);
        //画弧线
        arcRectF.set(circleWidth /2-halfWidth, circleWidth /2-halfHeight, halfWidth- circleWidth /2, halfHeight- circleWidth /2);
        //温度的有效范围是0~360度
        canvas.drawArc(arcRectF, -90, degreeProgress, false, arcPaint);
        //绘制中间所显示的当前温度
        canvas.drawText(msgInfo.getName()+":"+msgInfo.getNowValue()+msgInfo.getUnit(), 0, nowTemperatureTextY, nowTemperaturePaint);
        //绘制最高温文字
        canvas.drawText("MAX:"+msgInfo.getMaxValue()+msgInfo.getUnit(), 0, 0-nowTemperatureTextY*2, nowTemperaturePaint);
        canvas.save();
        //float rotateDegree = 180;
        //canvas.rotate(rotateDegree);
        //canvas.translate(0, halfWidth-circleWidth-10);
        //canvas.rotate(-rotateDegree);
        //canvas.drawText("满量程"+msgInfo.getMaxValue()+"℃", 0, maxAndMinTemperatureTextY, maxAndMinTemperaturePaint);
        //canvas.restore();
        //canvas.save();


        //"degreeProgress"的不断变化是动画效果关键
        if(degreeProgress<nowTemperature) {
            degreeProgress += (nowTemperature+1-degreeProgress)/2;
            invalidate();
        }else if(degreeProgress>nowTemperature){
            degreeProgress-= (degreeProgress+1-nowTemperature)/2;
            invalidate();
        }else{

/*
            rotateDegree = 180+nowTemperature;
            canvas.rotate(rotateDegree);
            canvas.translate(0, halfWidth-circleWidth-10);
            canvas.rotate(-rotateDegree);
            canvas.drawText(nowTemperature+"℃", 0, nowTemperatureTextY, nowTemperaturePaint);
            canvas.restore();
            canvas.save();
*/
        }
        super.onDraw(canvas);
    }

    public void config(String m_tag,String m_name,String m_unit,double m_max,double m_min){
        msgInfo.setTag(m_tag);
        msgInfo.setUnit(m_unit);
        msgInfo.setName(m_name);
        msgInfo.setMaxValue(m_max);
        msgInfo.setMinValue(m_min);
        display();
    }

    public void setValueByMap(Map<String, Double> map){
        int nowProgress;//0-360度
        double max,min,now;
        max = msgInfo.getMaxValue();
        min = msgInfo.getMinValue();

        if(map.get(msgInfo.getTag()) != null) {
            msgInfo.setNowValue(map.get(msgInfo.getTag()));
            now = msgInfo.getNowValue();
            nowProgress = (int)((now-min)*360.0/(max-min));
            setTemperature(nowProgress);
        }
    }


    //设置显示值
    public void setTemperature(int now){
        this.nowTemperature = now;
        display();
    }
    //触发绘制图形
    private void display(){
        isDraw = true;
        invalidate();
    }
}
