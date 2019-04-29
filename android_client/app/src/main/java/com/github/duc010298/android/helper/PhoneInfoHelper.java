package com.github.duc010298.android.helper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import com.github.duc010298.android.entity.PhoneInfoRegister;
import com.github.duc010298.android.entity.PhoneInfoUpdate;

import static android.content.Context.MODE_PRIVATE;

public class PhoneInfoHelper {

    public String getImei(Context context) {
        SharedPreferences pre = context.getSharedPreferences("android_client", MODE_PRIVATE);
        return pre.getString("imei_device", "");
    }

    public PhoneInfoRegister getInfoRegister(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        @SuppressLint("HardwareIds") String imei = telephonyManager.getDeviceId();

        SharedPreferences pre = context.getSharedPreferences("android_client", MODE_PRIVATE);
        SharedPreferences.Editor edit = pre.edit();
        edit.putString("imei_device", imei);
        edit.apply();

        PhoneInfoRegister phoneInfoRegister = new PhoneInfoRegister();
        phoneInfoRegister.setDeviceName(android.os.Build.MODEL);
        phoneInfoRegister.setImei(imei);
        return phoneInfoRegister;
    }

    public PhoneInfoUpdate getInfoUpdate(Context context) {
        SharedPreferences pre = context.getSharedPreferences("android_client", MODE_PRIVATE);
        String imei = pre.getString("imei_device", "");

        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        String networkName = networkInfo.getExtraInfo();
        String networkType = networkInfo.getTypeName();

        Intent batteryStatus = context.getApplicationContext().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int batteryLevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);

        int plugged = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean isCharging = (plugged == BatteryManager.BATTERY_PLUGGED_AC
                || plugged == BatteryManager.BATTERY_PLUGGED_USB
                || plugged == BatteryManager.BATTERY_PLUGGED_WIRELESS);

        PhoneInfoUpdate phoneInfoUpdate = new PhoneInfoUpdate();
        phoneInfoUpdate.setNetworkName(networkName);
        phoneInfoUpdate.setNetworkType(networkType);
        phoneInfoUpdate.setBatteryLevel(batteryLevel);
        phoneInfoUpdate.setCharging(isCharging);
        return phoneInfoUpdate;
    }
}
