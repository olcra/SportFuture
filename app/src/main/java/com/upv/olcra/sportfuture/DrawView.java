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

        System.out.println("ESTA EN EL DRAW VIEW");
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(10);
        paintBlack.setColor(Color.BLACK);
        paintBlack.setStrokeWidth(10);

        //Borrar cuando se cogen los valores del dispositivo.
        GlobalVariables.values.put(UUID.fromString("0000FF61-0000-1000-8000-00805f9b34fb"),90.0);
        GlobalVariables.values.put(UUID.fromString("0000FF62-0000-1000-8000-00805f9b34fb"),120.0);
        GlobalVariables.values.put(UUID.fromString("0000FF63-0000-1000-8000-00805f9b34fb"),200.0);
        GlobalVariables.values.put(UUID.fromString("0000FF64-0000-1000-8000-00805f9b34fb"),350.0);
        GlobalVariables.values.put(UUID.fromString("0000FF65-0000-1000-8000-00805f9b34fb"),220.0);
        GlobalVariables.values.put(UUID.fromString("0000FF66-0000-1000-8000-00805f9b34fb"),220.0);
        GlobalVariables.values.put(UUID.fromString("0000FF67-0000-1000-8000-00805f9b34fb"),220.0);
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

        System.out.println("CANVAS ONDRAW");
        int startX, startY, endX1, endY1, endX2, endY2, valueGreen = 0, valueOrange = 0;
        int displayHeight = GlobalVariables.displayHeight;
        int displayWidth = GlobalVariables.displayWidth;

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
        for (UUID key : GlobalVariables.values.keySet()){
            //extraer valor
            double value = GlobalVariables.values.get(key);
            //comparar key UUID
            if(key.equals(UUID.fromString("0000FF61-0000-1000-8000-00805f9b34fb"))){
                //empezar en el centro de la pantalla
                startX = displayWidth/2;
                startY = h*1;
                //linea hacia arriba a partir del punto pivote
                endX1  = (int) (startX + h * Math.sin(value * Math.PI/180));
                endY1  = (int) (startY + h * Math.cos(value * Math.PI/180));
                //linea hacia abajo a partir del punto pivote
                endX2  = (int) (startX - h * Math.sin(value * Math.PI/180));
                endY2  = (int) (startY - h * Math.cos(value * Math.PI/180));

                /*
                calcular el valor entre el angulo recibido y 180.
                180 porque en el movil el angulo hacia abajo es 0, derecha 90, arriba 180, izquierda 270
                dispositivo esta con el eje y arriba - abajo, x izq - der
                valor aux positivo es hacia la izq, negativo hacia la derecha. para facilitar el proceso se convierte a positivo
                el eje de coordenadas empieza en la esquina superior 0,0
                hacia la derecha es +x, hacia abajo +y
                 */
                double aux = value - 180;
                if(aux < 0 ){
                    aux *= -1;
                }

                //elegir el color y pintar con los valores obtenidos anteriormente.
                if (aux <= valueGreen) {
                    paint.setColor(Color.GREEN);
                    canvas.drawLine(startX,startY,endX1,endY1,paint);
                    canvas.drawLine(startX,startY,endX2,endY2,paint);
                }
                else if(aux <= valueOrange) {
                    paint.setColor(Color.rgb(255, 152, 0));
                    canvas.drawLine(startX, startY, endX1, endY1, paint);
                    canvas.drawLine(startX,startY,endX2,endY2,paint);
                }else{
                    paint.setColor(Color.RED);
                    canvas.drawLine(startX,startY,endX1,endY1,paint);
                    canvas.drawLine(startX,startY,endX2,endY2,paint);
                }
            }
            if(key.equals(UUID.fromString("0000FF62-0000-1000-8000-00805f9b34fb"))){
                startX = displayWidth/2;
                startY = h*3;
                endX1  = (int) (startX + h * Math.sin(value * Math.PI/180));
                endY1  = (int) (startY + h * Math.cos(value * Math.PI/180));
                endX2  = (int) (startX - h * Math.sin(value * Math.PI/180));
                endY2  = (int) (startY - h * Math.cos(value * Math.PI/180));

                double aux = value - 180;
                if(aux < 0 ){
                    aux *= -1;
                }

                if (aux <= valueGreen) {
                    paint.setColor(Color.GREEN);
                    canvas.drawLine(startX,startY,endX1,endY1,paint);
                    canvas.drawLine(startX,startY,endX2,endY2,paint);
                }
                else if(aux <= valueOrange) {
                    paint.setColor(Color.rgb(255, 152, 0));
                    canvas.drawLine(startX, startY, endX1, endY1, paint);
                    canvas.drawLine(startX,startY,endX2,endY2,paint);
                }else{
                    paint.setColor(Color.RED);
                    canvas.drawLine(startX,startY,endX1,endY1,paint);
                    canvas.drawLine(startX,startY,endX2,endY2,paint);
                }
            }
            if(key.equals(UUID.fromString("0000FF63-0000-1000-8000-00805f9b34fb"))){
                startX = displayWidth/2;
                startY = h*5;
                endX1  = (int) (startX + h * Math.sin(value * Math.PI/180));
                endY1  = (int) (startY + h * Math.cos(value * Math.PI/180));
                endX2  = (int) (startX - h * Math.sin(value * Math.PI/180));
                endY2  = (int) (startY - h * Math.cos(value * Math.PI/180));

                double aux = value - 180;
                if(aux < 0 ){
                    aux *= -1;
                }

                if (aux <= valueGreen) {
                    paint.setColor(Color.GREEN);
                    canvas.drawLine(startX,startY,endX1,endY1,paint);
                    canvas.drawLine(startX,startY,endX2,endY2,paint);
                }
                else if(aux <= valueOrange) {
                    paint.setColor(Color.rgb(255, 152, 0));
                    canvas.drawLine(startX, startY, endX1, endY1, paint);
                    canvas.drawLine(startX,startY,endX2,endY2,paint);
                }else{
                    paint.setColor(Color.RED);
                    canvas.drawLine(startX,startY,endX1,endY1,paint);
                    canvas.drawLine(startX,startY,endX2,endY2,paint);
                }
            }
            if(key.equals(UUID.fromString("0000FF64-0000-1000-8000-00805f9b34fb"))){
                startX = displayWidth/2;
                startY = h*7;
                endX1  = (int) (startX + h * Math.sin(value * Math.PI/180));
                endY1  = (int) (startY + h * Math.cos(value * Math.PI/180));
                endX2  = (int) (startX - h * Math.sin(value * Math.PI/180));
                endY2  = (int) (startY - h * Math.cos(value * Math.PI/180));

                double aux = value - 180;
                if(aux < 0 ){
                    aux *= -1;
                }

                if (aux <= valueGreen) {
                    paint.setColor(Color.GREEN);
                    canvas.drawLine(startX,startY,endX1,endY1,paint);
                    canvas.drawLine(startX,startY,endX2,endY2,paint);
                }
                else if(aux <= valueOrange) {
                    paint.setColor(Color.rgb(255, 152, 0));
                    canvas.drawLine(startX, startY, endX1, endY1, paint);
                    canvas.drawLine(startX,startY,endX2,endY2,paint);
                }else{
                    paint.setColor(Color.RED);
                    canvas.drawLine(startX,startY,endX1,endY1,paint);
                    canvas.drawLine(startX,startY,endX2,endY2,paint);
                }
            }
            if(key.equals(UUID.fromString("0000FF65-0000-1000-8000-00805f9b34fb"))){
                startX = displayWidth/2;
                startY = h*9;
                endX1  = (int) (startX + h * Math.sin(value * Math.PI/180));
                endY1  = (int) (startY + h * Math.cos(value * Math.PI/180));
                endX2  = (int) (startX - h * Math.sin(value * Math.PI/180));
                endY2  = (int) (startY - h * Math.cos(value * Math.PI/180));

                double aux = value - 180;
                if(aux < 0 ){
                    aux *= -1;
                }

                if (aux <= valueGreen) {
                    paint.setColor(Color.GREEN);
                    canvas.drawLine(startX,startY,endX1,endY1,paint);
                    canvas.drawLine(startX,startY,endX2,endY2,paint);
                }
                else if(aux <= valueOrange) {
                    paint.setColor(Color.rgb(255, 152, 0));
                    canvas.drawLine(startX, startY, endX1, endY1, paint);
                    canvas.drawLine(startX,startY,endX2,endY2,paint);
                }else{
                    paint.setColor(Color.RED);
                    canvas.drawLine(startX,startY,endX1,endY1,paint);
                    canvas.drawLine(startX,startY,endX2,endY2,paint);
                }
            }
            if(key.equals(UUID.fromString("0000FF66-0000-1000-8000-00805f9b34fb"))){
                startX = (displayWidth/4) * 3;;
                startY = h*5;
                endX1  = (int) (startX + h * Math.sin(value * Math.PI/180));
                endY1  = (int) (startY + h * Math.cos(value * Math.PI/180));
                endX2  = (int) (startX - h * Math.sin(value * Math.PI/180));
                endY2  = (int) (startY - h * Math.cos(value * Math.PI/180));

                double aux = value - 180;
                if(aux < 0 ){
                    aux *= -1;
                }

                if (aux <= valueGreen) {
                    paint.setColor(Color.GREEN);
                    canvas.drawLine(startX,startY,endX1,endY1,paint);
                    canvas.drawLine(startX,startY,endX2,endY2,paint);
                }
                else if(aux <= valueOrange) {
                    paint.setColor(Color.rgb(255, 152, 0));
                    canvas.drawLine(startX, startY, endX1, endY1, paint);
                    canvas.drawLine(startX,startY,endX2,endY2,paint);
                }else{
                    paint.setColor(Color.RED);
                    canvas.drawLine(startX,startY,endX1,endY1,paint);
                    canvas.drawLine(startX,startY,endX2,endY2,paint);
                }
            }
            if(key.equals(UUID.fromString("0000FF67-0000-1000-8000-00805f9b34fb"))){
                startX = (displayWidth/4);
                startY = h*9;
                endX1  = (int) (startX + h * Math.sin(value * Math.PI/180));
                endY1  = (int) (startY + h * Math.cos(value * Math.PI/180));
                endX2  = (int) (startX - h * Math.sin(value * Math.PI/180));
                endY2  = (int) (startY - h * Math.cos(value * Math.PI/180));

                double aux = value - 180;
                if(aux < 0 ){
                    aux *= -1;
                }

                if (aux <= valueGreen) {
                    paint.setColor(Color.GREEN);
                    canvas.drawLine(startX,startY,endX1,endY1,paint);
                    canvas.drawLine(startX,startY,endX2,endY2,paint);
                }
                else if(aux <= valueOrange) {
                    paint.setColor(Color.rgb(255, 152, 0));
                    canvas.drawLine(startX, startY, endX1, endY1, paint);
                    canvas.drawLine(startX,startY,endX2,endY2,paint);
                }else{
                    paint.setColor(Color.RED);
                    canvas.drawLine(startX,startY,endX1,endY1,paint);
                    canvas.drawLine(startX,startY,endX2,endY2,paint);
                }
            }
        }

        //Generar lineas de cuadricula.
        for(int i = 0; i < displayHeight; i+= displayHeight/12){
            canvas.drawLine(0,i,displayWidth,i,paintBlack);
        }
        canvas.drawLine(displayWidth/2,0,displayWidth/2,displayHeight,paintBlack);

        new Handler().postDelayed(new Runnable(){
            public void run(){

                //Borrar cuando se cogen los valores del dispositivo.
                GlobalVariables.values.put(UUID.fromString("0000FF61-0000-1000-8000-00805f9b34fb"),Math.random()*(200-160)+160);
                GlobalVariables.values.put(UUID.fromString("0000FF62-0000-1000-8000-00805f9b34fb"),Math.random()*(220-140)+140);
                GlobalVariables.values.put(UUID.fromString("0000FF63-0000-1000-8000-00805f9b34fb"),Math.random()*(240-120)+120);
                GlobalVariables.values.put(UUID.fromString("0000FF64-0000-1000-8000-00805f9b34fb"),Math.random()*(270-90)+90);
                GlobalVariables.values.put(UUID.fromString("0000FF65-0000-1000-8000-00805f9b34fb"),Math.random()*(270-90)+90);
                GlobalVariables.values.put(UUID.fromString("0000FF66-0000-1000-8000-00805f9b34fb"),Math.random()*(270-90)+90);
                GlobalVariables.values.put(UUID.fromString("0000FF67-0000-1000-8000-00805f9b34fb"),Math.random()*(270-90)+90);

                //redibujar Canvas.
                invalidate();
            }
        }, 500);

    }

}
