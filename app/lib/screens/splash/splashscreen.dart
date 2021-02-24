import 'package:flutter/material.dart';
import 'package:negrosa/services/references/refsservice.dart';

class SplashScreen extends StatefulWidget {
  @override
  _SplashScreenState createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen> {
  @override
  void initState() {
    super.initState();

    Future.delayed(Duration(seconds: 1))
        .then((_) => RefsService().initialize())
        .then((_) => Navigator.pushReplacementNamed(context, "/references"));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          children: [
            Text("Negro SA"),
            Container(
              height: 10,
            ),
            CircularProgressIndicator(),
          ],
          mainAxisAlignment: MainAxisAlignment.center,
        ),
      ),
    );
  }
}
