package com.upv.olcra.sportfuture;

import android.app.Application;

import java.util.HashMap;
import java.util.UUID;

public class GlobalVariables extends Application {

    public final static HashMap<UUID,Double> values = new HashMap<UUID,Double>();

    public static int notificationValue = 0;

    public static int displayWidth = 0;
    public static int displayHeight = 0;
}
