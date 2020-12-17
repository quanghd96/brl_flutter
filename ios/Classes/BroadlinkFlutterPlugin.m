#import "BroadlinkFlutterPlugin.h"

@implementation BroadlinkFlutterPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
    FlutterMethodChannel* channel = [FlutterMethodChannel
                                     methodChannelWithName:@"broadlink_flutter"
                                     binaryMessenger:[registrar messenger]];
    BroadlinkFlutterPlugin* instance = [[BroadlinkFlutterPlugin alloc] init];
    [registrar addMethodCallDelegate:instance channel:channel];
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
    if (self.let == nil) {
        self.let = [BLLet sharedLetWithLicense: @"javishome.vn"];
        [self.let setDebugLog:BL_LEVEL_DEBUG];
        [self.let.controller setSDKRawDebugLevel:BL_LEVEL_DEBUG];
    }
    
    if ([@"startConfig" isEqualToString:call.method]) {
        NSString *ssidName = call.arguments[@"ssid"];
        NSString *password = call.arguments[@"password"];
        dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
            NSDate *date = [NSDate date];
            NSLog(@"====Start Config===");
            BLDeviceConfigResult *res = [[BLLet sharedLet].controller deviceConfig:ssidName password:password version:3 timeout:60];
            NSLog(@"====Config over! Spends(%fs)", [date timeIntervalSinceNow]);
            dispatch_async(dispatch_get_main_queue(), ^{
                if ([res succeed]) {
                    NSLog(@"Configure Wi-Fi success");
                    result(@"Configure Wi-Fi success");
                } else {
                    NSLog(@"Configure Wi-Fi error");
                    result(@"Configure Wi-Fi error");
                }
            });
        });
    } else if ([@"cancelConfig" isEqualToString:call.method]) {
        [[BLLet sharedLet].controller deviceConfigCancel];
        result(@"ok");
    } else {
        result(FlutterMethodNotImplemented);
    }
}
@end
