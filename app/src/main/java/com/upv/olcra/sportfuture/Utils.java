package com.upv.olcra.sportfuture;

import java.util.UUID;

public class Utils{

    private static long time;

    protected static void convertirArray(byte[] data, UUID uuid){
        double RATIO_ACC = (4.0/32767);
        double[] dataConv = new double[3];
        //double RATIO_GYRO = ((1000.0/32767)*(Math.PI/(180)));
        double [] valores = new double[3];

        /*double x = (data[1] & 0xFF) << 8 | data[0] & 0xFF;
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
        valores[2] = z;*/

        /*for(int i = 0; i <= 2; i++) {
            dataConv[i] = (data[2 * i + 1] & 0xFF << 8 | data[2 * i] & 0xFF);
        }*/

        for (int i = 0; i <= 2; i++) {
            if (dataConv[i] > 32767)
                dataConv[i] = (-(65534 - dataConv[i]));
            dataConv[i] = dataConv[i] * RATIO_ACC;
        }

        /*for (int i = 3; i <= 5; i++){
            if(dataConv[i] > 32767)
                dataConv[i] = (-(65534-dataConv[i]));
            valores[i] = dataConv[i] * RATIO_GYRO;
        }*/
        calcularAngulo(valores,uuid);
    }

    private static void calcularAngulo(double[] valores, UUID uuid){
        double flexion, flexionPrev, flexionFiltro;
        double dt = (System.nanoTime() - time);
        time = System.nanoTime();

        flexion = (Math.atan2(valores[0], valores[2])) * 180 / Math.PI;

        /*Aplicar filtro complementario
        flexionPrev = GlobalVariables.values.get(uuid);
        flexionFiltro = 0.98 * (flexionPrev + (valores[4] * dt)) + 0.02 * flexion;
        Guardar valor anterior
        flexionPrev = flexionFiltro;
        Añadir al HashMap*/

        GlobalVariables.values.put(uuid, flexion);
    }
}
