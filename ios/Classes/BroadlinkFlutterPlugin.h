#import <Flutter/Flutter.h>
#import <BLLetBase/BLLetBase.h>
#import <BLLetCore/BLLetCore.h>

@interface BroadlinkFlutterPlugin : NSObject<FlutterPlugin>
@property (strong, nonatomic) BLLet *let;
@end
