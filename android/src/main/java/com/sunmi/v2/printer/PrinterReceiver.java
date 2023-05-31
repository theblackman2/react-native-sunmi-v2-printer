package com.sunmi.v2.printer;

import android.content.BroadcastReceiver;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import android.content.Context;
import android.content.Intent;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import android.util.Log;

public class PrinterReceiver extends BroadcastReceiver {
    public PrinterReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent data) {
        String action = data.getAction();
        String type = "PrinterStatus";
        Log.d("PrinterReceiver", action);

        if (SunmiV2PrinterModule.reactApplicationContext != null) {
            SunmiV2PrinterModule.reactApplicationContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(type, action);
        }
    }
}
