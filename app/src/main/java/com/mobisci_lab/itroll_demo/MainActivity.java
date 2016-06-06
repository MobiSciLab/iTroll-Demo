package com.mobisci_lab.itroll_demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_test_backward_compat).setOnClickListener(this);
        findViewById(R.id.btn_test_fake_loc).setOnClickListener(this);
        findViewById(R.id.btn_test_fake_navi).setOnClickListener(this);
        findViewById(R.id.btn_test_fake_html5).setOnClickListener(this);
        findViewById(R.id.btn_test_clear_backward_compat).setOnClickListener(this);
        findViewById(R.id.btn_test_clear).setOnClickListener(this);
        findViewById(R.id.btn_test_mix).setOnClickListener(this);
    }

    private void testFakeLocBackwardCompat() {
        Log.i("MobiSciLab", "testFakeLocBackwardCompat");
        Intent intent = new Intent();
        intent.setAction("com.msl.worldtroll.MANUAL");
        intent.putExtra("ENABLE_FAKE_LOCATION", true);//true: enable, false: disable

        intent.putExtra("LOCATION_MODE", 0); //0: High accuracy, 1: Baterry saving; 2: Device only

        intent.putExtra("ENABLE_GPS_DATA", true); //true:enable, false: disable
        intent.putExtra("GPS_DATA_DELAY", 1);//GPS location data will be simulated after "GPS_DATA_DELAY" seconds
        intent.putExtra("GPS_LOCATION", "38.871063 -77.055612 5 10"); //GPS data format: lat lon altitude accuracy

        intent.putExtra("ENABLE_NETWORK_DATA", true); //true:enable, false: disable
        intent.putExtra("NETWORK_DATA_DELAY", 1);//Network location data will be simulated after "NETWORK _DATA_DELAY" seconds
        intent.putExtra("NETWORK_LOCATION", "38.871063 -77.055612 5 10"); //Network data format: lat lon altitude accuracy

        intent.putExtra("ENABLE_FUSED_DATA", true); //true:enable, false: disable
        intent.putExtra("FUSED_DATA_DELAY", 1);//FUSED location data will be simulated after "FUSED_DATA_DELAY" seconds
        intent.putExtra("FUSED_LOCATION", "-18.558935 46.689362 5 10"); //Fused data format: lat lon altitude accuracy

        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        sendBroadcast(intent);
    }

    private void testClearBackwardCompat() {
        Log.i("MobiSciLab", "testClearBackwardCompat");
        Intent intent = new Intent();
        intent.setAction("com.msl.worldtroll.MANUAL");
        intent.putExtra("ENABLE_FAKE_LOCATION", false);//true: enable, false: disable
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        sendBroadcast(intent);
    }

    private void testFakeLoc() throws JSONException {
        Log.i("MobiSciLab", "testFakeLoc");
        JSONObject jsonData = new JSONObject();
        jsonData.put("ENABLE_FAKE_LOCATION", true);
        jsonData.put("LOCATION_MODE", 0);

        jsonData.put("ENABLE_GPS_DATA", true);
        jsonData.put("GPS_DATA_DELAY", 0);
        jsonData.put("GPS_LOCATION", new JSONObject().put("lat", 60.172720).put("lng", 24.951525).put("acc", 10).put("alt", 5));

        jsonData.put("ENABLE_NETWORK_DATA", true);
        jsonData.put("NETWORK_DATA_DELAY", 0);
        jsonData.put("NETWORK_LOCATION", new JSONObject().put("lat", 60.172720).put("lng", 24.951525).put("acc", 10).put("alt", 5));

        jsonData.put("ENABLE_FUSED_DATA", true);
        jsonData.put("FUSED_DATA_DELAY", 0);
        jsonData.put("FUSED_LOCATION", new JSONObject().put("lat", 60.172720).put("lng", 24.951525).put("acc", 10).put("alt", 5));

        Intent intent = new Intent();
        intent.setAction("com.msl.worldtroll.MANUAL");
        intent.putExtra("DATA", jsonData.toString());
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        sendBroadcast(intent);
    }


    private void testFakeNavigation() throws JSONException {
        Log.i("MobiSciLab", "testFakeNavigation");
        JSONArray terminal = new JSONArray();
        terminal.put(new JSONObject().put("lat", 38.872441).put("lng", -77.057790)); //Point A
        terminal.put(new JSONObject().put("lat", 38.874580).put("lng", -77.053097)); //Point B
        terminal.put(new JSONObject().put("lat", 38.869084).put("lng", -77.051337)); //Point C
        terminal.put(new JSONObject().put("lat", 38.870103).put("lng", -77.060950)); //Point D

        JSONObject jsonData = new JSONObject();
        jsonData.put("TERMINALS", terminal);//Set up terminal points
        jsonData.put("DURATION", 10);//Travel to all points (A, B, C, D) in 10 seconds


        Intent intent = new Intent();
        intent.setAction("com.msl.worldtroll.MANUAL");
        intent.putExtra("DATA", jsonData.toString());
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        sendBroadcast(intent);
    }


    private void testClear() throws JSONException {
        Log.i("MobiSciLab", "testClear");
        JSONObject jsonData = new JSONObject();
        jsonData.put("ENABLE_FAKE_LOCATION", false);

        Intent intent = new Intent();
        intent.setAction("com.msl.worldtroll.MANUAL");
        intent.putExtra("DATA", jsonData.toString());
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        sendBroadcast(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test_backward_compat:
                testFakeLocBackwardCompat();
                break;
            case R.id.btn_test_clear_backward_compat:
                testClearBackwardCompat();
                break;
            case R.id.btn_test_fake_loc:
                try {
                    testFakeLoc();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_test_fake_navi:
                try {
                    testFakeNavigation();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_test_fake_html5:
                //TODO
                break;

            case R.id.btn_test_clear:
                try {
                    testClear();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_test_mix:
                testClearBackwardCompat();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        testFakeLocBackwardCompat();
                    }
                }, 4000);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            testClear();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, 8000);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            testFakeLoc();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, 20000);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            testClear();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, 26000);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            testFakeNavigation();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, 36000);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            testClear();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, 42000);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            testFakeLoc();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, 52000);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            testFakeNavigation();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, 58000);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            testFakeLoc();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, 66000);

                break;
        }
    }
}
