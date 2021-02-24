import 'package:shared_preferences/shared_preferences.dart';

class SharedPreferencesHelper {
  static const String _SERVER_ADDRESS = "server_address";

  static Future<SharedPreferences> _prefs() async {
    return SharedPreferences.getInstance();
  }

  static Future<String> getServerAddress() async {
    return (await _prefs()).getString(_SERVER_ADDRESS);
  }

  static Future<bool> setServerAddress(String value) async {
    return (await _prefs()).setString(_SERVER_ADDRESS, value);
  }
}
