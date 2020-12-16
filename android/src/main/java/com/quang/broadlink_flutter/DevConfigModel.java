package com.quang.broadlink_flutter;

import android.util.Log;

import cn.com.broadlink.sdk.BLLet;
import cn.com.broadlink.sdk.param.controller.BLDeviceConfigParam;
import cn.com.broadlink.sdk.result.controller.BLDeviceConfigResult;


public class DevConfigModel {

    public BLDeviceConfigResult startConfig(BLDeviceConfigParam deviceConfigParam) {
        final BLDeviceConfigResult result = BLLet.Controller.deviceConfig(deviceConfigParam);
        if (result != null && result.succeed()) {
            Log.d("BROADLINK_LET_SDK_LOG", "DeviceIP: " + result.getDevaddr());
            Log.d("BROADLINK_LET_SDK_LOG", "Did: " + result.getDid());
            Log.d("BROADLINK_LET_SDK_LOG", "Mac: " + result.getMac());
        }
        return result;
    }

    public void cancelConfig() {
        BLLet.Controller.deviceConfigCancel();
    }
}
