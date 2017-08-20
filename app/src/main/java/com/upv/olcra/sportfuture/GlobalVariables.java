package com.upv.olcra.sportfuture;

import android.app.Application;

import java.util.HashMap;
import java.util.UUID;

public class GlobalVariables extends Application {

    public final static HashMap<UUID,Double> values = new HashMap<>();


     /* 0 = low
     * 1 = med
     * 2 = high*/
    public static int notificationValue = 0;

    public static int displayWidth = 0;
    public static int displayHeight = 0;
}
