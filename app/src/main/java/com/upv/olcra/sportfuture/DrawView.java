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

    private void init() {

        //inicializar a verde y strokewidth 10.

        //System.out.println("DRAW VIEW");
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(10);
        paintBlack.setColor(Color.BLACK);
        paintBlack.setStrokeWidth(10);

        //Borrar cuando se cogen los valores del dispositivo.
        GlobalVariables.values.put(UUID.fromString("0000FF61-0000-1000-8000-00805f9b34fb"),-90.0);
        GlobalVariables.values.put(UUID.fromString("0000FF62-0000-1000-8000-00805f9b34fb"),-90.0);
        GlobalVariables.values.put(UUID.fromString("0000FF63-0000-1000-8000-00805f9b34fb"),-90.0);
        GlobalVariables.values.put(UUID.fromString("0000FF64-0000-1000-8000-00805f9b34fb"),-90.0);
        GlobalVariables.values.put(UUID.fromString("0000FF65-0000-1000-8000-00805f9b34fb"),-90.0);
        GlobalVariables.values.put(UUID.fromString("0000FF66-0000-1000-8000-00805f9b34fb"),-90.0);
        GlobalVariables.values.put(UUID.fromString("0000FF67-0000-1000-8000-00805f9b34fb"),-90.0);
    }

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

        //son 5 sensores, a 2 lineas por sensor -> height / 12.
        int h = displayHeight / 12;

        //Color segun nivel de monitorización.
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

        //iterar sobre las claves del hashmap

        value = GlobalVariables.values.get(UUID.fromString("0000FF65-0000-1000-8000-00805f9b34fb")) - 90;
        //empezar en el centro de la pantalla
        startX11 = startX12 = displayWidth/2;
        startY11 = startY12 = h*6/4;
        //linea hacia arriba a partir del punto pivote
        endX11  = (int) (startX11 + h * Math.sin(value * Math.PI/180));
        endY11  = (int) (startY11 + h * Math.cos(value * Math.PI/180));
        //linea hacia abajo a partir del punto pivote
        endX12  = (int) (startX12 - h * Math.sin(value * Math.PI/180));
        endY12  = (int) (startY12 - h * Math.cos(value * Math.PI/180));

        /*
        calcular el valor entre el angulo recibido y 180.
        180 porque en el movil el angulo hacia abajo es 0, derecha 90, arriba 180, izquierda 270
        dispositivo esta con el eje y arriba - abajo, x izq - der
        valor aux positivo es hacia la izq, negativo hacia la derecha. para facilitar el proceso se convierte a positivo
        el eje de coordenadas empieza en la esquina superior 0,0
        hacia la derecha es +x, hacia abajo +y
        */

        anguloAjustado = value + 180;
        if(anguloAjustado < 0 ){
            anguloAjustado *= -1;
        }

        //elegir el color y pintar con los valores obtenidos anteriormente.
        if (anguloAjustado <= valueGreen) {
            paint.setColor(Color.GREEN);
            canvas.drawLine(startX11,startY11,endX11,endY11,paint);
            canvas.drawLine(startX12,startY12,endX12,endY12,paint);
        }
        else if(anguloAjustado <= valueOrange) {
            paint.setColor(Color.rgb(255, 152, 0));
            canvas.drawLine(startX11, startY11, endX11, endY11, paint);
            canvas.drawLine(startX12,startY12,endX12,endY12,paint);
        }else{
            paint.setColor(Color.RED);
            canvas.drawLine(startX11,startY11,endX11,endY11,paint);
            canvas.drawLine(startX12,startY12,endX12,endY12,paint);
        }

        value = GlobalVariables.values.get(UUID.fromString("0000FF64-0000-1000-8000-00805f9b34fb"))- 90;
        startX21 = endX12;
        //startX = displayWidth/2;
        startY21 = endY12;
        //startY = h*3;

        endX21 = (int) (startX21 - h * Math.sin(value * Math.PI/180));
        endY21 = (int) (startY21 - h * Math.cos(value * Math.PI/180));

        startX22 = endX21;
        startY22 = endY21;
        endX22 = (int) (endX21 - h * Math.sin(value * Math.PI/180));
        endY22 = (int) (endY21 - h * Math.cos(value * Math.PI/180));

                /*endX1  = (int) (startX + h * Math.sin(value * Math.PI/180));
                endY1  = (int) (startY + h * Math.cos(value * Math.PI/180));
                endX2  = (int) (startX - h * Math.sin(value * Math.PI/180));
                endY2  = (int) (startY - h * Math.cos(value * Math.PI/180));*/

        anguloAjustado = value + 180;
        if(anguloAjustado < 0 ){
            anguloAjustado *= -1;
        }

        if (anguloAjustado <= valueGreen) {
            paint.setColor(Color.GREEN);
            canvas.drawLine(startX21,startY21,endX21,endY21,paint);
            canvas.drawLine(startX22,startY22,endX22,endY22,paint);
        }
        else if(anguloAjustado <= valueOrange) {
            paint.setColor(Color.rgb(255, 152, 0));
            canvas.drawLine(startX21, startY21, endX21, endY21, paint);
            canvas.drawLine(startX22,startY22,endX22,endY22,paint);
        }else{
            paint.setColor(Color.RED);
            canvas.drawLine(startX21,startY21,endX21,endY21,paint);
            canvas.drawLine(startX22,startY22,endX22,endY22,paint);
        }

        value = GlobalVariables.values.get(UUID.fromString("0000FF63-0000-1000-8000-00805f9b34fb"))- 90;
        startX31 = endX22;
        //startX = displayWidth/2;
        startY31 = endY22;
        //startY = h*3;

        endX31 = (int) (startX31 - h * Math.sin(value * Math.PI/180));
        endY31 = (int) (startY31 - h * Math.cos(value * Math.PI/180));

        startX32 = endX31;
        startY32 = endY31;
        endX32 = (int) (endX31 - h * Math.sin(value * Math.PI/180));
        endY32 = (int) (endY31 - h * Math.cos(value * Math.PI/180));

                /*endX1  = (int) (startX + h * Math.sin(value * Math.PI/180));
                endY1  = (int) (startY + h * Math.cos(value * Math.PI/180));
                endX2  = (int) (startX - h * Math.sin(value * Math.PI/180));
                endY2  = (int) (startY - h * Math.cos(value * Math.PI/180));*/

        anguloAjustado = value + 180;
        if(anguloAjustado < 0 ){
            anguloAjustado *= -1;
        }

        if (anguloAjustado <= valueGreen) {
            paint.setColor(Color.GREEN);
            canvas.drawLine(startX31,startY31,endX31,endY31,paint);
            canvas.drawLine(startX32,startY32,endX32,endY32,paint);
        }
        else if(anguloAjustado <= valueOrange) {
            paint.setColor(Color.rgb(255, 152, 0));
            canvas.drawLine(startX31,startY31,endX31,endY31,paint);
            canvas.drawLine(startX32,startY32,endX32,endY32,paint);
        }else{
            paint.setColor(Color.RED);
            canvas.drawLine(startX31,startY31,endX31,endY31,paint);
            canvas.drawLine(startX32,startY32,endX32,endY32,paint);
        }

        value = GlobalVariables.values.get(UUID.fromString("0000FF62-0000-1000-8000-00805f9b34fb"))- 90;
        startX41 = endX32;
        //startX = displayWidth/2;
        startY41 = endY32;
        //startY = h*3;

        endX41 = (int) (startX41 - h * Math.sin(value * Math.PI/180));
        endY41 = (int) (startY41 - h * Math.cos(value * Math.PI/180));

        startX42 = endX41;
        startY42 = endY41;
        endX42 = (int) (endX41 - h * Math.sin(value * Math.PI/180));
        endY42 = (int) (endY41 - h * Math.cos(value * Math.PI/180));

                /*endX1  = (int) (startX + h * Math.sin(value * Math.PI/180));
                endY1  = (int) (startY + h * Math.cos(value * Math.PI/180));
                endX2  = (int) (startX - h * Math.sin(value * Math.PI/180));
                endY2  = (int) (startY - h * Math.cos(value * Math.PI/180));*/

        anguloAjustado = value + 180;
        if(anguloAjustado < 0 ){
            anguloAjustado *= -1;
        }

        if (anguloAjustado <= valueGreen) {
            paint.setColor(Color.GREEN);
            canvas.drawLine(startX41,startY41,endX41,endY41,paint);
            canvas.drawLine(startX42,startY42,endX42,endY42,paint);
        }
        else if(anguloAjustado <= valueOrange) {
            paint.setColor(Color.rgb(255, 152, 0));
            canvas.drawLine(startX41,startY41,endX41,endY41,paint);
            canvas.drawLine(startX42,startY42,endX42,endY42,paint);
        }else{
            paint.setColor(Color.RED);
            canvas.drawLine(startX41,startY41,endX41,endY41,paint);
            canvas.drawLine(startX42,startY42,endX42,endY42,paint);
        }

        value = GlobalVariables.values.get(UUID.fromString("0000FF61-0000-1000-8000-00805f9b34fb"))- 90;
        startX51 = endX42;
        //startX = displayWidth/2;
        startY51 = endY42;
        //startY = h*3;

        endX51 = (int) (startX51 - h * Math.sin(value * Math.PI/180));
        endY51 = (int) (startY51 - h * Math.cos(value * Math.PI/180));

        startX52 = endX51;
        startY52 = endY51;
        endX52 = (int) (endX51 - h * Math.sin(value * Math.PI/180));
        endY52 = (int) (endY51 - h * Math.cos(value * Math.PI/180));

                /*endX1  = (int) (startX + h * Math.sin(value * Math.PI/180));
                endY1  = (int) (startY + h * Math.cos(value * Math.PI/180));
                endX2  = (int) (startX - h * Math.sin(value * Math.PI/180));
                endY2  = (int) (startY - h * Math.cos(value * Math.PI/180));*/

        anguloAjustado = value + 180;
        if(anguloAjustado < 0 ){
            anguloAjustado *= -1;
        }

        if (anguloAjustado <= valueGreen) {
            paint.setColor(Color.GREEN);
            canvas.drawLine(startX51,startY51,endX51,endY51,paint);
            canvas.drawLine(startX52,startY52,endX52,endY52,paint);
        }
        else if(anguloAjustado <= valueOrange) {
            paint.setColor(Color.rgb(255, 152, 0));
            canvas.drawLine(startX51,startY51,endX51,endY51,paint);
            canvas.drawLine(startX52,startY52,endX52,endY52,paint);
        }else{
            paint.setColor(Color.RED);
            canvas.drawLine(startX51,startY51,endX51,endY51,paint);
            canvas.drawLine(startX52,startY52,endX52,endY52,paint);
        }



        /*for (UUID key : GlobalVariables.values.keySet()){
            //extraer valor
            double value = GlobalVariables.values.get(key);

        Generar lineas de cuadricula.
        for(int i = 0; i < displayHeight; i+= displayHeight/12){
            canvas.drawLine(0,i,displayWidth,i,paintBlack);
        }
        canvas.drawLine(displayWidth/2,0,displayWidth/2,displayHeight,paintBlack);
        */

        new Handler().postDelayed(new Runnable(){
            public void run(){

                //Borrar cuando se cogen los valores del dispositivo.
                /*GlobalVariables.values.put(UUID.fromString("0000FF61-0000-1000-8000-00805f9b34fb"),Math.random()*(200-160)+160);
                GlobalVariables.values.put(UUID.fromString("0000FF62-0000-1000-8000-00805f9b34fb"),Math.random()*(220-140)+140);
                GlobalVariables.values.put(UUID.fromString("0000FF63-0000-1000-8000-00805f9b34fb"),Math.random()*(220-140)+140);
                GlobalVariables.values.put(UUID.fromString("0000FF64-0000-1000-8000-00805f9b34fb"),Math.random()*(220-140)+140);
                GlobalVariables.values.put(UUID.fromString("0000FF65-0000-1000-8000-00805f9b34fb"),Math.random()*(220-140)+140);
                GlobalVariables.values.put(UUID.fromString("0000FF66-0000-1000-8000-00805f9b34fb"),Math.random()*(270-90)+90);
                GlobalVariables.values.put(UUID.fromString("0000FF67-0000-1000-8000-00805f9b34fb"),Math.random()*(270-90)+90);*/

                //redibujar Canvas.
                invalidate();
            }
        }, 500);

    }

}
