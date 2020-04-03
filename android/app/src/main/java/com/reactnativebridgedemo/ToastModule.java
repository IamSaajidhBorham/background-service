package com.reactnativebridgedemo;
import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.services.GPS_Service;

import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

public class ToastModule extends ReactContextBaseJavaModule {
    boolean service = false;


    //constructor
    public ToastModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }
    //Mandatory function getName that specifies the module name
    @Override
    public String getName() {
        return "ToastModule";
    }
    //Custom function that we are going to export to JS
    @ReactMethod
    public void showToast(String message) {
//        Toast.makeText(getReactApplicationContext(), "Hi", Toast.LENGTH_SHORT).show();
        if(service == false){
            startService();
        } else {
            stopService();
        }
    }

    @ReactMethod
    public void startService() {
        // Starting the heartbeat service
//        Toast.makeText(getReactApplicationContext(), "Starting Service...", Toast.LENGTH_SHORT).show();
        this.getReactApplicationContext().startService(new Intent(this.getReactApplicationContext(), GPS_Service.class));

        this.service = true;
    }

    @ReactMethod
    public void stopService() {
        // Starting the heartbeat service
//        Toast.makeText(getReactApplicationContext(), "Starting Service...", Toast.LENGTH_SHORT).show();
        this.getReactApplicationContext().stopService(new Intent(this.getReactApplicationContext(), GPS_Service.class));
        this.service = false;
    }
}