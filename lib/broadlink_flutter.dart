import 'dart:async';

import 'package:flutter/services.dart';

class BroadlinkFlutter {
  static const MethodChannel _channel =
      const MethodChannel('broadlink_flutter');

  static Future startConfig(String ssid, String password) async {
    return await _channel.invokeMethod('startConfig', {
      "ssid": ssid,
      "password": password,
    });
  }

  static Future cancelConfig() async {
    return await _channel.invokeMethod('cancelConfig');
  }
}
