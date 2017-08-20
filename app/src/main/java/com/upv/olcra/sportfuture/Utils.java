package com.upv.olcra.sportfuture;

import java.util.UUID;

public class Utils{

    private static long time;
    private static double ang_x_prev = 0, ang_y_prev = 0;;

    public static void convertirArray(byte[] data, UUID uuid){
        double[] dataConv = new double[data.length];
        double RATIO_ACC = (4.0/32767);
        double RATIO_GYRO = ((1000.0/32767)*(Math.PI/(180)));
        double mov;
        double [] valores = new double[6];


        /*String aux ="[";
        for(int i = 0; i < data.length; i++){
            aux += String.format("0x%02X", data[i]) + ", ";
        }
        aux += "]";
        System.out.println(aux);*/

        for(int i = 0; i <= 5; i++){
            dataConv[i] = (data[2*i+1] & 0xFF << 8 | data[2*i] & 0xFF);
            //System.out.println("DatoConvertido "  + i + ": " + Double.toString(dataConv[i]));
        }

        for (int i = 0; i <= 2; i++){
            mov = dataConv[i];
            if(mov > 32767)
                mov = (-(65534-mov));
            valores[i] = mov * RATIO_ACC;
            //System.out.println("Aceleracion " + i + ": " + mov);
        }

        for (int i = 3; i <= 5; i++){
            mov = dataConv[i];
            if(mov > 32767)
                mov = (-(65534-mov));
            valores[i] = mov * RATIO_GYRO;
            //System.out.println("Giroscopio " + i + ": " + mov);
        }
        calcularAngulo(valores,uuid);
    }

    public static void convBateria(){
        //int bat_raww = (dato[1] & 0xFF) << 8|dato[0] & 0xFF;
    }

    public static void calcularAngulo(double[] valores, UUID uuid){
        double accelAngleX, accelAngleY;
        double inclinacion, angleX, angleY;;
        double dt = (System.nanoTime() - time);
        time = System.nanoTime();

        inclinacion = (Math.atan2(valores[1],valores[0])) * 180/Math.PI;

        accelAngleX = Math.atan2(valores[1],valores[2]) * 180/Math.PI;
        accelAngleY = Math.atan2(-valores[0], Math.sqrt(valores[1]*valores[1] + valores[2]*valores[2])) * 180/Math.PI;

        angleX = 0.98*(ang_x_prev + (valores[3] * dt)) + 0.02*accelAngleX;
        angleY = 0.98*(ang_y_prev + (valores[4] * dt)) + 0.02*accelAngleY;

        ang_x_prev = angleX;
        ang_y_prev = angleY;

        //myGV.setUuid(uuid);
        //myGV.setAngle(angleX);
        GlobalVariables.values.put(uuid,angleX);

        System.out.println("angleX: " + angleX + ", angleY: " + angleY);
    }

}
