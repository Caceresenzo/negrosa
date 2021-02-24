import 'package:flutter/material.dart';
import 'package:negrosa/generated/l10n.dart';

class AppDrawer extends StatelessWidget {
  Widget _buildHeader() {
    return Container(
      margin: EdgeInsets.only(top: 4),
      height: 60,
      child: DrawerHeader(
        child: Image(
          image: AssetImage('assets/logo.png'),
        ),
      ),
    );
  }

  Widget _buildTile(
    BuildContext context, {
    String title,
    IconData icon,
    String toRoute,
    bool replace = false,
  }) {
    Function isSelected = () => ModalRoute.of(context).settings.name == toRoute;

    return ListTile(
      title: Text(title),
      leading: Icon(icon),
      selected: isSelected(),
      onTap: () {
        NavigatorState navigator = Navigator.of(context);

        navigator.pop();
        if (!isSelected()) {
          if (replace) {
            navigator.pushReplacementNamed(toRoute);
          } else {
            navigator.pushNamed(toRoute);
          }
        }
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: ListView(
        children: [
          _buildHeader(),
          _buildTile(
            context,
            title: S.of(context).references,
            icon: Icons.format_shapes,
            toRoute: "/references",
          ),
          _buildTile(
            context,
            title: S.of(context).settings,
            icon: Icons.settings,
            toRoute: "/settings",
          ),
        ],
      ),
    );
  }
}
