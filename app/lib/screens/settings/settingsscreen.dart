import 'package:flutter/material.dart';
import 'package:negrosa/generated/l10n.dart';
import 'package:negrosa/preferences/sharedpreferenceshelper.dart';
import 'package:prompt_dialog/prompt_dialog.dart';

class SettingsScreen extends StatefulWidget {
  @override
  _SettingsScreenState createState() => _SettingsScreenState();
}

class _SettingsScreenState extends State<SettingsScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(S.of(context).settings),
        leading: GestureDetector(
          child: Icon(Icons.arrow_back),
          onTap: () => Navigator.pop(context),
        ),
      ),
      body: ListView(
        children: [
          _buildServerAddressSettingTile(context),
        ],
      ),
    );
  }

  Widget _buildServerAddressSettingTile(BuildContext context) {
    return FutureBuilder(
      future: SharedPreferencesHelper.getServerAddress(),
      builder: (context, snapshot) {
        return ListTile(
          title: Text(S.of(context).serverAddressSettings),
          subtitle: Text(
              snapshot.hasData ? snapshot.data : S.of(context).unspecified),
          leading: Icon(Icons.cloud_queue),
          onTap: () async {
            String out = await prompt(
              context,
              initialValue: "${snapshot.data}",
            );

            if (out != null) {
              SharedPreferencesHelper.setServerAddress(out)
                  .then((value) => setState(() => {}));
            }
          },
        );
      },
    );
  }
}
