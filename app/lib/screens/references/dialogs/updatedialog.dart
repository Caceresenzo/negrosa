import 'package:flutter/material.dart';
import 'package:negrosa/generated/l10n.dart';
import 'package:negrosa/services/references/refsservice.dart';

typedef UpdateFinishedListener = void Function();

class UpdateDialog extends StatefulWidget {
  UpdateFinishedListener listener;

  UpdateDialog(this.listener);

  @override
  _UpdateDialogState createState() => _UpdateDialogState(listener);
}

class _UpdateDialogState extends State<UpdateDialog> {
  UpdateFinishedListener listener;
  UpdateAction _action;
  String _ref;
  int _position;
  int _totalCount;
  List<String> _errors = List();

  _UpdateDialogState(this.listener);

  @override
  void initState() {
    super.initState();

    RefsService().update((action, ref, position, count) {
      _action = action;
      _ref = ref;
      _position = position;
      _totalCount = count;

      if (this.mounted) {
        setState(() {});
      }
    }, () {
      _action = UpdateAction.done;
      _ref = null;
      _position = null;
      _totalCount = null;

      if (this.mounted) {
        setState(() {});
      }

      listener();
    }, (action, ref, exception) {
      _action = action;
      _ref = ref;
      _errors.insert(0, exception.toString());

      if (this.mounted) {
        setState(() {});
      }
    });
  }

  String actionName(BuildContext context) {
    S s = S.of(context);

    switch (_action) {
      case UpdateAction.fetching:
        return s.fetchingUpdateAction;

      case UpdateAction.delete:
        return s.deletingUpdateAction;

      case UpdateAction.download:
        return s.downloadingUpdateAction;

      case UpdateAction.done:
        return s.doneUpdateAction;
    }

    return ("???");
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        ListTile(
          title: Center(
            child: Text(actionName(context)),
          ),
          subtitle: _ref != null
              ? Center(
                  child: Text(_ref),
                )
              : null,
        ),
        _position != null
            ? LinearProgressIndicator(
                value: _position / _totalCount,
              )
            : Container(),
        _errors.isNotEmpty
            ? ExpansionTile(
                title: Center(
                  child: Text("Errors"),
                ),
                children: List.from(
                  _errors.map(
                    (e) => Center(
                      child: Text(e),
                    ),
                  ),
                ),
              )
            : Container()
      ],
    );
  }
}
