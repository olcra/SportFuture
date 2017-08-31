package com.upv.olcra.sportfuture;

import java.util.UUID;

public class Utils{

    private static long time;
    private static double ang_x_prev = 0, ang_y_prev = 0;

    protected static void convertirArray(byte[] data, UUID uuid){
        //double[] dataConv = new double[data.length];
        double RATIO_ACC = (4.0/32767);
        //double RATIO_GYRO = ((1000.0/32767)*(Math.PI/(180)));
        //double mov;
        double [] valores = new double[3];

        double x = (data[1] & 0xFF) << 8 | data[0] & 0xFF;
        if(x>32767) x=x-65535;
        x = x * RATIO_ACC;
        valores[0] = x;
        double y = (data[3] & 0xFF) << 8 | data[2] & 0xFF;
        if(y>32767) y=y-65535;
        y = y * RATIO_ACC;
        valores[1] = y;
        double z = (data[5] & 0xFF) << 8 | data[4] & 0xFF;
        if(z>32767) z=z-65535;
        z = z * RATIO_ACC;
        valores[2] = z;

        /*String aux ="[";
        for(int i = 0; i < data.length; i++){
            aux += String.format("0x%02X", data[i]) + ", ";
        }
        aux += "]";
        System.out.println(aux);*/

        /*for(int i = 0; i <= 5; i++) {
            dataConv[i] = (data[2 * i + 1] & 0xFF << 8 | data[2 * i] & 0xFF);
        }*/
            //dataConv[i] = data[i];
            
            //System.out.println("DatoConvertido "  + i + ": " + Double.toString(dataConv[i]));
        //}

        /*for (int i = 0; i <= 2; i++){
            mov = dataConv[i];
            if(mov > 32767)
                mov = (-(65534-mov));
            valores[i] = mov * RATIO_ACC;
            //System.out.println("Aceleracion " + i + ": " + mov);
        }*/

        /*for (int i = 3; i <= 5; i++){
            mov = dataConv[i];
            if(mov > 32767)
                mov = (-(65534-mov));
            valores[i] = mov * RATIO_GYRO;
            //System.out.println("Giroscopio " + i + ": " + mov);
        }*/
        calcularAngulo(valores,uuid);
    }

    public static void convBateria(){
        //double bat_raww = (dato[1] & 0xFF) << 8|dato[0] & 0xFF;
    }

    private static void calcularAngulo(double[] valores, UUID uuid){
        double accelAngleX, accelAngleY;
        double flexion, angleX, angleY, flexionFiltro, flexionPrev = 0;
        double dt = (System.nanoTime() - time);
        time = System.nanoTime();

        //Angulo entre Y-Z
        flexion = (Math.atan2(valores[1],valores[2])) * 180/Math.PI;
        //Aplicar filtro complementario
        //flexionPrev = GlobalVariables.values.get(uuid);
        //flexionFiltro = 0.98*(flexionPrev + (valores[4] * dt)) + 0.02*flexion;
        //Guardar valor anterior
        //flexionPrev = flexionFiltro;
        //Añadir al HashMap
        GlobalVariables.values.put(uuid,flexion);

        if(uuid.equals(UUID.fromString("0000FF65-0000-1000-8000-00805f9b34fb"))){
            System.out.println("UUID: " + uuid + " ZY: " + flexion);
        }


        /*accelAngleX = Math.atan2(valores[1],valores[2]) * 180/Math.PI;
        accelAngleY = Math.atan2(-valores[0], Math.sqrt(valores[1]*valores[1] + valores[2]*valores[2])) * 180/Math.PI;

        angleX = 0.98*(ang_x_prev + (valores[3] * dt)) + 0.02*accelAngleX;
        angleY = 0.98*(ang_y_prev + (valores[4] * dt)) + 0.02*accelAngleY;

        ang_x_prev = angleX;
        ang_y_prev = angleY;*/

        //myGV.setUuid(uuid);
        //myGV.setAngle(angleX);

        //System.out.println("angleX: " + angleX + ", angleY: " + angleY);
    }

}
