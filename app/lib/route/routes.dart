import 'package:flutter/material.dart';
import 'package:negrosa/screens/home/homescreen.dart';
import 'package:negrosa/screens/references/referencesscreen.dart';
import 'package:negrosa/screens/settings/settingsscreen.dart';
import 'package:negrosa/screens/splash/splashscreen.dart';

Map<String, WidgetBuilder> appRoutes() {
  return {
    '/splash': (context) => SplashScreen(),
    '/': (context) => HomeScreen(),
    '/references': (context) => ReferencesScreen(),
    '/settings': (context) => SettingsScreen(),
  };
}
