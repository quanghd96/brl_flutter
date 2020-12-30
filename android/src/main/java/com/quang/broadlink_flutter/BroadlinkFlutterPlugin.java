package com.quang.broadlink_flutter;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import cn.com.broadlink.base.BLConfigParam;
import cn.com.broadlink.sdk.BLLet;
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
    private final Handler uiThreadHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "broadlink_flutter");
        channel.setMethodCallHandler(this);
        if (mDevConfigModel == null) {
            BLConfigParam blConfigParam = new BLConfigParam();
            blConfigParam.put(BLConfigParam.CONTROLLER_LOG_LEVEL, "4");
            blConfigParam.put(BLConfigParam.CONTROLLER_JNI_LOG_LEVEL, "4");
            blConfigParam.put(BLConfigParam.CONTROLLER_LOCAL_TIMEOUT, "3000");
            blConfigParam.put(BLConfigParam.CONTROLLER_REMOTE_TIMEOUT, "5000");
            blConfigParam.put(BLConfigParam.CONTROLLER_SEND_COUNT, "1");
            blConfigParam.put(BLConfigParam.CONTROLLER_DEVICE_CONFIG_TIMEOUT, "60");
            BLLet.init(flutterPluginBinding.getApplicationContext(), "javishome.vn", "", blConfigParam);
            mDevConfigModel = new DevConfigModel();
        }
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull final Result result) {
        if (call.method.equals("startConfig")) {
            String ssid = call.argument("ssid");
            String password = call.argument("password");
            final BLDeviceConfigParam configParam = new BLDeviceConfigParam();
            configParam.setSsid(ssid);
            configParam.setPassword(password);
            configParam.setVersion(3);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final BLDeviceConfigResult res = mDevConfigModel.startConfig(configParam);
                    uiThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Map<String, Object> map = new HashMap<>();
                                map.put("ip", res.getDevaddr());
                                map.put("mac", res.getMac());
                                map.put("did", res.getDid());
                                map.put("status", res.getStatus());
                                map.put("msg", res.getMsg());
                                result.success(map);
                            } catch (Exception e) {
                                result.success(null);
                            }
                        }
                    });
                }
            }).start();
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
