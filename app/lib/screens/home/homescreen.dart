import 'package:flutter/material.dart';
import 'package:negrosa/drawer/maindrawer.dart';
import 'package:negrosa/generated/l10n.dart';

class HomeScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text(S.of(context).negrosa)),
      drawer: AppDrawer(),
      body: Text("Hello"),
    );
  }
}
