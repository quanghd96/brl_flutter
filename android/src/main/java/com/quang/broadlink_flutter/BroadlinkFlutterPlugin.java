package com.quang.broadlink_flutter;

import androidx.annotation.NonNull;

import cn.com.broadlink.sdk.param.controller.BLDeviceConfigParam;
import cn.com.broadlink.sdk.result.controller.BLDeviceConfigResult;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/**
 * BroadlinkFlutterPlugin
 */
public class BroadlinkFlutterPlugin implements FlutterPlugin, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private MethodChannel channel;
    private DevConfigModel mDevConfigModel;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "broadlink_flutter");
        channel.setMethodCallHandler(this);
        mDevConfigModel = new DevConfigModel();
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        if (call.method.equals("startConfig")) {
            String ssid = call.argument("ssid");
            String password = call.argument("password");
            BLDeviceConfigParam configParam = new BLDeviceConfigParam();
            configParam.setSsid(ssid);
            configParam.setPassword(password);
            configParam.setVersion(3);
            BLDeviceConfigResult configResult = mDevConfigModel.startConfig(configParam);
            result.success(configResult);
        } else if (call.method.equals("cancelConfig")) {
            mDevConfigModel.cancelConfig();
        } else {
            result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }
}
