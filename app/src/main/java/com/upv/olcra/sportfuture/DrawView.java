package com.upv.olcra.sportfuture;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.UUID;

public class DrawView extends View{

    Paint paint = new Paint();
    Paint paintBlack = new Paint();

    public DrawView(Context context) {
        super(context);
        init();
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(10);
        paintBlack.setColor(Color.BLACK);
        paintBlack.setStrokeWidth(10);

        GlobalVariables.values.put(UUID.fromString("0000FF61-0000-1000-8000-00805f9b34fb"),-90.0);
        GlobalVariables.values.put(UUID.fromString("0000FF62-0000-1000-8000-00805f9b34fb"),-90.0);
        GlobalVariables.values.put(UUID.fromString("0000FF63-0000-1000-8000-00805f9b34fb"),-90.0);
        GlobalVariables.values.put(UUID.fromString("0000FF64-0000-1000-8000-00805f9b34fb"),-90.0);
        GlobalVariables.values.put(UUID.fromString("0000FF65-0000-1000-8000-00805f9b34fb"),-90.0);
        GlobalVariables.values.put(UUID.fromString("0000FF66-0000-1000-8000-00805f9b34fb"),-90.0);
        GlobalVariables.values.put(UUID.fromString("0000FF67-0000-1000-8000-00805f9b34fb"),-90.0);
    }

    @Override
    public void onDraw(Canvas canvas) {

        int startX11,startY11,startX12,startY12,endX11,endY11,endX12,endY12;
        int startX21,startY21,startX22,startY22,endX21,endY21,endX22,endY22;
        int startX31,startY31,startX32,startY32,endX31,endY31,endX32,endY32;
        int startX41,startY41,startX42,startY42,endX41,endY41,endX42,endY42;
        int startX51,startY51,startX52,startY52,endX51,endY51,endX52,endY52;
        int valueGreen, valueOrange;

        int displayHeight = GlobalVariables.displayHeight;
        int displayWidth = GlobalVariables.displayWidth;
        double value;
        double anguloAjustado;
        int h = displayHeight / 12;

        switch(GlobalVariables.notificationValue){
            case 0:
                valueGreen = 15;
                valueOrange = 30;
                break;
            case 1:
                valueGreen = 10;
                valueOrange = 20;
                break;
            case 2:
                valueGreen = 5;
                valueOrange = 10;
                break;
            default:
                valueGreen = 0;
                valueOrange = 0;
                break;
        }

        value = GlobalVariables.values.get(UUID.fromString("0000FF63-0000-1000-8000-00805f9b34fb")) + 90;
        startX31 = startX32 = displayWidth / 2;
        startY31 = startY32 = displayHeight / 2;

        endX31 = (int) (startX31 + h * Math.sin(value * Math.PI / 180));
        endY31 = (int) (startY31 + h * Math.cos(value * Math.PI / 180));

        endX32 = (int) (startX32 - h * Math.sin(value * Math.PI / 180));
        endY32 = (int) (startY32 - h * Math.cos(value * Math.PI / 180));

        anguloAjustado = value - 180;
        if(anguloAjustado < 0 ){
            anguloAjustado *= -1;
        }

        if (anguloAjustado <= valueGreen) {
            paint.setColor(Color.GREEN);
            canvas.drawLine(startX31, startY31, endX31, endY31, paint);
            canvas.drawLine(startX32, startY32, endX32, endY32, paint);
        } else if (anguloAjustado <= valueOrange) {
            paint.setColor(Color.rgb(255, 152, 0));
            canvas.drawLine(startX31, startY31, endX31, endY31, paint);
            canvas.drawLine(startX32, startY32, endX32, endY32, paint);
        }else{
            paint.setColor(Color.RED);
            canvas.drawLine(startX31, startY31, endX31, endY31, paint);
            canvas.drawLine(startX32, startY32, endX32, endY32, paint);
        }

        value = GlobalVariables.values.get(UUID.fromString("0000FF64-0000-1000-8000-00805f9b34fb")) + 90;
        startX41 = endX31;
        startY41 = endY31;

        endX41 = startX42 = (int) (startX41 + h * Math.sin(value * Math.PI / 180));
        endY41 = startY42 = (int) (startY41 + h * Math.cos(value * Math.PI / 180));

        endX42 = (int) (startX42 + h * Math.sin(value * Math.PI / 180));
        endY42 = (int) (startY42 + h * Math.cos(value * Math.PI / 180));

        anguloAjustado = value - 180;
        if(anguloAjustado < 0 ){
            anguloAjustado *= -1;
        }

        if (anguloAjustado <= valueGreen) {
            paint.setColor(Color.GREEN);
            canvas.drawLine(startX41, startY41, endX41, endY41, paint);
            canvas.drawLine(startX42, startY42, endX42, endY42, paint);
        } else if (anguloAjustado <= valueOrange) {
            paint.setColor(Color.rgb(255, 152, 0));
            canvas.drawLine(startX41, startY41, endX41, endY41, paint);
            canvas.drawLine(startX42, startY42, endX42, endY42, paint);
        }else{
            paint.setColor(Color.RED);
            canvas.drawLine(startX41, startY41, endX41, endY41, paint);
            canvas.drawLine(startX42, startY42, endX42, endY42, paint);
        }

        value = GlobalVariables.values.get(UUID.fromString("0000FF65-0000-1000-8000-00805f9b34fb")) + 90;
        startX51 = endX42;
        startY51 = endY42;

        endX51 = startX52 = (int) (startX51 + h * Math.sin(value * Math.PI / 180));
        endY51 = startY52 = (int) (startY51 + h * Math.cos(value * Math.PI / 180));

        endX52 = (int) (startX52 + h * Math.sin(value * Math.PI / 180));
        endY52 = (int) (startY52 + h * Math.cos(value * Math.PI / 180));

        anguloAjustado = value - 180;
        if(anguloAjustado < 0 ){
            anguloAjustado *= -1;
        }

        if (anguloAjustado <= valueGreen) {
            paint.setColor(Color.GREEN);
            canvas.drawLine(startX51, startY51, endX51, endY51, paint);
            canvas.drawLine(startX52, startY52, endX52, endY52, paint);
        } else if (anguloAjustado <= valueOrange) {
            paint.setColor(Color.rgb(255, 152, 0));
            canvas.drawLine(startX51, startY51, endX51, endY51, paint);
            canvas.drawLine(startX52, startY52, endX52, endY52, paint);
        }else{
            paint.setColor(Color.RED);
            canvas.drawLine(startX51, startY51, endX51, endY51, paint);
            canvas.drawLine(startX52, startY52, endX52, endY52, paint);
        }

        value = GlobalVariables.values.get(UUID.fromString("0000FF62-0000-1000-8000-00805f9b34fb")) + 90;
        startX21 = endX32;
        startY21 = endY32;

        endX21 = startX22 = (int) (startX21 - h * Math.sin(value * Math.PI / 180));
        endY21 = startY22 = (int) (startY21 - h * Math.cos(value * Math.PI / 180));

        endX22 = (int) (startX22 - h * Math.sin(value * Math.PI / 180));
        endY22 = (int) (startY22 - h * Math.cos(value * Math.PI / 180));

        anguloAjustado = value - 180;
        if(anguloAjustado < 0 ){
            anguloAjustado *= -1;
        }

        if (anguloAjustado <= valueGreen) {
            paint.setColor(Color.GREEN);
            canvas.drawLine(startX21, startY21, endX21, endY21, paint);
            canvas.drawLine(startX22, startY22, endX22, endY22, paint);
        } else if (anguloAjustado <= valueOrange) {
            paint.setColor(Color.rgb(255, 152, 0));
            canvas.drawLine(startX21, startY21, endX21, endY21, paint);
            canvas.drawLine(startX22, startY22, endX22, endY22, paint);
        }else{
            paint.setColor(Color.RED);
            canvas.drawLine(startX21, startY21, endX21, endY21, paint);
            canvas.drawLine(startX22, startY22, endX22, endY22, paint);
        }

        value = GlobalVariables.values.get(UUID.fromString("0000FF61-0000-1000-8000-00805f9b34fb")) + 90;
        startX11 = endX22;
        startY11 = endY22;

        endX11 = startX12 = (int) (startX11 - h * Math.sin(value * Math.PI / 180));
        endY11 = startY12 = (int) (startY11 - h * Math.cos(value * Math.PI / 180));

        endX12 = (int) (startX12 - h * Math.sin(value * Math.PI / 180));
        endY12 = (int) (startY12 - h * Math.cos(value * Math.PI / 180));

        anguloAjustado = value - 180;
        if(anguloAjustado < 0 ){
            anguloAjustado *= -1;
        }

        if (anguloAjustado <= valueGreen) {
            paint.setColor(Color.GREEN);
            canvas.drawLine(startX11, startY11, endX11, endY11, paint);
            canvas.drawLine(startX12, startY12, endX12, endY12, paint);
        } else if (anguloAjustado <= valueOrange) {
            paint.setColor(Color.rgb(255, 152, 0));
            canvas.drawLine(startX11, startY11, endX11, endY11, paint);
            canvas.drawLine(startX12, startY12, endX12, endY12, paint);
        }else{
            paint.setColor(Color.RED);
            canvas.drawLine(startX11, startY11, endX11, endY11, paint);
            canvas.drawLine(startX12, startY12, endX12, endY12, paint);
        }


        new Handler().postDelayed(new Runnable(){
            public void run(){
                invalidate();
            }
        }, 500);

    }

}
