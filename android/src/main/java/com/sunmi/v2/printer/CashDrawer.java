package com.sunmi.v2.printer;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;

import android.os.RemoteException;

import com.facebook.react.bridge.ReactMethod;

import woyou.aidlservice.jiuiv5.IWoyouService;
import woyou.aidlservice.jiuiv5.ICallback;

import com.facebook.react.bridge.Callback;

import android.content.ServiceConnection;
import android.content.ComponentName;
import android.os.IBinder;
import android.content.Intent;
import android.content.Context;


public class CashDrawer extends ReactContextBaseJavaModule {

    public static ReactApplicationContext reactApplicationContext = null;
    private IWoyouService woyouService;

    private ServiceConnection connService = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            woyouService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            woyouService = IWoyouService.Stub.asInterface(service);
        }
    };

    CashDrawer(ReactApplicationContext context) {
        super(context);

        reactApplicationContext = context;
        Intent intent = new Intent();
        intent.setPackage("woyou.aidlservice.jiuiv5");
        intent.setAction("woyou.aidlservice.jiuiv5.IWoyouService");
        context.startService(intent);
        context.bindService(intent, connService, Context.BIND_AUTO_CREATE);
    }

    @NonNull
    @Override
    public String getName() {
        return "CashDrawer";
    }

    @ReactMethod
    public void open(Callback callBack) {
        final IWoyouService service = woyouService;
        if (service != null) {
            byte[] mOpenDrawerCommand = new byte[5];
            mOpenDrawerCommand[0] = 0x10;
            mOpenDrawerCommand[1] = 0x14;
            mOpenDrawerCommand[2] = 0x00;
            mOpenDrawerCommand[3] = 0x00;
            mOpenDrawerCommand[4] = 0x00;

            try {
                service.sendRAWData(mOpenDrawerCommand, null);
            } catch (RemoteException e) {
                e.printStackTrace();
                callBack.invoke(false);
            }
        } else {
            callBack.invoke(service, woyouService);
        }
    }
    
    @ReactMethod
    public void cutPaper(Callback callBack) {
        final IWoyouService service = woyouService;
        if (service != null) {
            byte[] mCutPaperCommand = new byte[4];
            mCutPaperCommand[0] = 0x1d;
            mCutPaperCommand[1] = 0x56;
            mCutPaperCommand[2] = 0x42;
            mCutPaperCommand[3] = 0x00;

            try {
                service.sendRAWData(mCutPaperCommand, null);
            } catch (RemoteException e) {
                e.printStackTrace();
                callBack.invoke(false);
            }
        } else {
            callBack.invoke(service, woyouService);
        }
    }
}
