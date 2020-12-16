import 'package:flutter/material.dart';
import 'package:broadlink_flutter/broadlink_flutter.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  TextEditingController _ssidController = TextEditingController();
  TextEditingController _passwordController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Broadlink config'),
        ),
        body: Column(
          children: [
            const SizedBox(height: 10),
            TextField(
              controller: _ssidController,
              decoration: InputDecoration(
                contentPadding: EdgeInsets.fromLTRB(20.0, 15.0, 20.0, 15.0),
                hintText: "SSID",
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(32.0),
                ),
              ),
            ),
            const SizedBox(height: 10),
            TextField(
              controller: _passwordController,
              decoration: InputDecoration(
                contentPadding: EdgeInsets.fromLTRB(20.0, 15.0, 20.0, 15.0),
                hintText: "Password",
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(32.0),
                ),
              ),
            ),
            const SizedBox(height: 10),
            RaisedButton(
              onPressed: () {
                BroadlinkFlutter.startConfig(
                    _ssidController.text, _passwordController.text);
              },
              child: Text("Config"),
            ),
            const SizedBox(height: 30),
            Text("Result:"),
          ],
        ),
      ),
    );
  }
}
