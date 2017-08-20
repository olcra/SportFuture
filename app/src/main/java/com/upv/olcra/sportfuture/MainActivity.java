package com.upv.olcra.sportfuture;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragmentHelp.OnFragmentInteractionListener {

    private BluetoothAdapter mBluetoothAdapter;
    private int REQUEST_ENABLE_BT = 1;
    private Handler mHandler;
    private static final long SCAN_PERIOD = 10000;
    private BluetoothLeScanner mLEScanner;
    private ScanSettings settings;
    private List<ScanFilter> filters;
    private BluetoothGatt mGatt;
    //private String deviceMAC = "33:33:33:33:33:33";
    private String deviceMAC = "E0:C7:9D:5C:DC:B8";
    //private UUID SERVICE_UUID = UUID.fromString("0000FF60-0000-1000-8000-00805f9b34fb");
    private UUID SERVICE_UUID = UUID.fromString("0000FF60-0000-1000-8000-00805f9b34fb");

    private UUID CHARACTERISTIC_UUID_1 = UUID.fromString("0000FF61-0000-1000-8000-00805f9b34fb");
    private UUID CHARACTERISTIC_UUID_2 = UUID.fromString("0000FF62-0000-1000-8000-00805f9b34fb");
    private UUID CHARACTERISTIC_UUID_3 = UUID.fromString("0000FF63-0000-1000-8000-00805f9b34fb");
    private UUID CHARACTERISTIC_UUID_4 = UUID.fromString("0000FF64-0000-1000-8000-00805f9b34fb");
    private UUID CHARACTERISTIC_UUID_5 = UUID.fromString("0000FF65-0000-1000-8000-00805f9b34fb");
    private UUID CHARACTERISTIC_UUID_6 = UUID.fromString("0000FF66-0000-1000-8000-00805f9b34fb");
    private UUID CHARACTERISTIC_UUID_7 = UUID.fromString("0000FF67-0000-1000-8000-00805f9b34fb");

    private boolean mScanning = false;

    private BluetoothGattCharacteristic characteristic_1;
    private BluetoothGattCharacteristic characteristic_2;
    private BluetoothGattCharacteristic characteristic_3;
    private BluetoothGattCharacteristic characteristic_4;
    private BluetoothGattCharacteristic characteristic_5;
    private BluetoothGattCharacteristic characteristic_6;
    private BluetoothGattCharacteristic characteristic_7;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        

        if(savedInstanceState == null){
            Fragment fragment = null;
            Class fragmentClass;
            fragmentClass = FragmentHome.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        GlobalVariables.displayHeight = displayMetrics.heightPixels;
        System.out.println("Height: " + GlobalVariables.displayHeight);
        GlobalVariables.displayWidth = displayMetrics.widthPixels;
        System.out.println("Width: " + GlobalVariables.displayWidth);


        /**FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });**/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mHandler = new Handler();
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE Not Supported",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            if (Build.VERSION.SDK_INT >= 21) {
                mLEScanner = mBluetoothAdapter.getBluetoothLeScanner();
                settings = new ScanSettings.Builder()
                        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                        .build();
                filters = new ArrayList<ScanFilter>();
            }
            scanLeDevice(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
            scanLeDevice(false);
            try{
                mGatt.disconnect();
            }
            catch (NullPointerException e){
                Log.i("gatt empty",e.toString());
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (mGatt == null) {
            super.onDestroy();
            return;
        }
        mGatt.close();
        mGatt = null;
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        if (!mScanning) {
            menu.findItem(R.id.menu_stop).setVisible(false);
            menu.findItem(R.id.menu_scan).setVisible(true);
        } else {
            menu.findItem(R.id.menu_stop).setVisible(true);
            menu.findItem(R.id.menu_scan).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_scan:
                scanLeDevice(true);
                break;
            case R.id.menu_stop:
                scanLeDevice(false);
                break;
        }
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;
        FragmentManager fragmentManager;
        DrawerLayout drawer;

        switch(item.getItemId()){
            case R.id.nav_home:
                fragmentClass = FragmentHome.class;
                break;
            case R.id.nav_settings:
                fragmentClass = SettingsFragment.class;
                break;
            case R.id.nav_help:
                fragmentClass = FragmentHelp.class;
                break;
            default:
                fragmentClass = FragmentHome.class;
                break;
        }

        /*if (id == R.id.nav_home) {
            fragmentClass = FragmentHome.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            *//*item.setChecked(true);
            setTitle(item.getTitle());
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);*//*

        } else if (id == R.id.nav_settings) {

            fragmentClass = SettingsFragment.class;
            try{
                fragment = (Fragment) fragmentClass.newInstance();
            }catch (Exception e){
                e.printStackTrace();
            }

            //getFragmentManager().beginTransaction().replace(R.id.flContent,new SettingsFragment1()).commit();

            *//*getFragmentManager().beginTransaction()
                    .replace(R.id.flContent, new SettingsFragment1())
                    .commit();
            item.setChecked(true);
            setTitle(item.getTitle());
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            //fragmentClass = SettingsFragment1.class;*//*
        } else if (id == R.id.nav_help) {
            fragmentClass = FragmentHelp.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            *//*fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
            item.setChecked(true);
            setTitle(item.getTitle());
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);*//*

        }*/

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        item.setChecked(true);
        setTitle(item.getTitle());
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_CANCELED) {
                //Bluetooth not enabled.
                finish();
                return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (Build.VERSION.SDK_INT < 21) {
                        mBluetoothAdapter.stopLeScan(mLeScanCallback);
                        invalidateOptionsMenu();
                    } else {
                        mLEScanner.stopScan(mScanCallback);
                        mScanning = false;
                    }
                }
            }, SCAN_PERIOD);

            if (Build.VERSION.SDK_INT < 21) {
                mBluetoothAdapter.startLeScan(mLeScanCallback);
            } else {
                mLEScanner.startScan(filters, settings, mScanCallback);
                mScanning = true;
            }
        } else {
            mScanning = false;
            if (Build.VERSION.SDK_INT < 21) {
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
            } else {
                mLEScanner.stopScan(mScanCallback);
            }
        }
        invalidateOptionsMenu();
    }


    private ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            Log.i("callbackType", String.valueOf(callbackType));
            Log.i("result", result.toString());
            BluetoothDevice bluetoothDevice = result.getDevice();
            if(bluetoothDevice.getAddress().equals(deviceMAC)){
                connectToDevice(bluetoothDevice);
                mScanning = false;
            }
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            for (ScanResult sr : results) {
                Log.i("ScanResult - Results", sr.toString());
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            Log.e("Scan Failed", "Error Code: " + errorCode);
            mScanning = false;
        }
    };

    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, int rssi,
                                     byte[] scanRecord) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("onLeScan", device.toString());
                                connectToDevice(device);
                            }
                        });

                }
            };

    public void connectToDevice(BluetoothDevice device) {
        if (mGatt == null) {

            mGatt = device.connectGatt(this, true, gattCallback);
            Toast.makeText(this, "Connected to Device", Toast.LENGTH_SHORT).show();
            mScanning = false;
            scanLeDevice(false);

        }
    }

    private final BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            Log.i("onConnectionStateChange", "Status: " + status);
            switch (newState) {
                case BluetoothProfile.STATE_CONNECTED:
                    Log.i("gattCallback", "STATE_CONNECTED");
                    mScanning = false;
                    gatt.discoverServices();
                    break;
                case BluetoothProfile.STATE_DISCONNECTED:
                    Log.e("gattCallback", "STATE_DISCONNECTED");
                    gatt.close();
                    mGatt = null;
                    mScanning = false;
                    break;
                default:
                    Log.e("gattCallback", "STATE_OTHER");
            }

        }


        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            List<BluetoothGattService> services = gatt.getServices();
            Log.i("onServicesDiscovered", services.toString());
            //gatt.readCharacteristic(services.get(1).getCharacteristics().get(0));
            for(BluetoothGattService service : services){
                if(service.getUuid().equals(SERVICE_UUID)){
                    characteristic_1 = service.getCharacteristic(CHARACTERISTIC_UUID_1);
                    characteristic_2 = service.getCharacteristic(CHARACTERISTIC_UUID_2);
                    characteristic_3 = service.getCharacteristic(CHARACTERISTIC_UUID_3);
                    characteristic_4 = service.getCharacteristic(CHARACTERISTIC_UUID_4);
                    characteristic_5 = service.getCharacteristic(CHARACTERISTIC_UUID_5);
                    characteristic_6 = service.getCharacteristic(CHARACTERISTIC_UUID_6);
                    characteristic_7 = service.getCharacteristic(CHARACTERISTIC_UUID_7);
                }
            }

            for (BluetoothGattDescriptor descriptor : characteristic_1.getDescriptors()) {
                descriptor.setValue( BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                mGatt.writeDescriptor(descriptor);
            }
            gatt.readCharacteristic(characteristic_1);
            gatt.setCharacteristicNotification(characteristic_1,true);

            for (BluetoothGattDescriptor descriptor : characteristic_2.getDescriptors()) {
                descriptor.setValue( BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                mGatt.writeDescriptor(descriptor);
            }
            gatt.readCharacteristic(characteristic_2);
            gatt.setCharacteristicNotification(characteristic_2,true);

            for (BluetoothGattDescriptor descriptor : characteristic_3.getDescriptors()) {
                descriptor.setValue( BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                mGatt.writeDescriptor(descriptor);
            }
            gatt.readCharacteristic(characteristic_3);
            gatt.setCharacteristicNotification(characteristic_3,true);

            for (BluetoothGattDescriptor descriptor : characteristic_4.getDescriptors()) {
                descriptor.setValue( BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                mGatt.writeDescriptor(descriptor);
            }
            gatt.readCharacteristic(characteristic_4);
            gatt.setCharacteristicNotification(characteristic_4,true);

            for (BluetoothGattDescriptor descriptor : characteristic_5.getDescriptors()) {
                descriptor.setValue( BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                mGatt.writeDescriptor(descriptor);
            }
            gatt.readCharacteristic(characteristic_5);
            gatt.setCharacteristicNotification(characteristic_5,true);

            for (BluetoothGattDescriptor descriptor : characteristic_6.getDescriptors()) {
                descriptor.setValue( BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                mGatt.writeDescriptor(descriptor);
            }
            gatt.readCharacteristic(characteristic_6);
            gatt.setCharacteristicNotification(characteristic_6,true);

            for (BluetoothGattDescriptor descriptor : characteristic_7.getDescriptors()) {
                descriptor.setValue( BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                mGatt.writeDescriptor(descriptor);
            }
            gatt.readCharacteristic(characteristic_7);
            gatt.setCharacteristicNotification(characteristic_7,true);

            /*BluetoothGattCharacteristic characteristic = services.get(2).getCharacteristics().get(8);
            for (BluetoothGattDescriptor descriptor : characteristic.getDescriptors()) {
                descriptor.setValue( BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                mGatt.writeDescriptor(descriptor);            }

            gatt.readCharacteristic(characteristic);
            gatt.setCharacteristicNotification(characteristic, true);*/
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic
                                                 characteristic, int status) {
            Log.i("onCharacteristicRead", characteristic.toString());
            //gatt.disconnect();
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic) {
            //read the characteristic data
            //ByteBuffer.wrap(data).getInt();
            byte[] data = characteristic.getValue();
            UUID uuid = characteristic.getUuid();
            //convAceleracion(data);
            //convGiroscopio(data);
            //convertirArray(data, uuid);
            System.out.println("UUID: " + uuid + " Valor: " + Arrays.toString(data));

        }
    };
}
