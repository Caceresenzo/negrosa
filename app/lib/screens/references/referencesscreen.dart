import 'package:flutter/material.dart';
import 'package:negrosa/drawer/maindrawer.dart';
import 'package:negrosa/generated/l10n.dart';
import 'package:negrosa/screens/references/dialogs/updatedialog.dart';
import 'package:negrosa/services/references/refsservice.dart';
import 'package:photo_view/photo_view.dart';

void openViewer(BuildContext context, String name) {
  showDialog(
    context: context,
    builder: (BuildContext context) {
      return Dialog(
        child: Container(
          child: PhotoView(
            tightMode: true,
            imageProvider: RefsService().imageOf(name),
          ),
        ),
      );
    },
  );
}

class ReferencesScreen extends StatefulWidget {
  ReferencesScreen({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _ReferencesScreenState createState() => _ReferencesScreenState();
}

class CustomSearchDelegate extends SearchDelegate {
  final Function callback;
  final List<String> _refs = List();

  CustomSearchDelegate(this.callback) {
    _refs.addAll(RefsService().listRefs());
  }

  @override
  List<Widget> buildActions(BuildContext context) {
    return [
      IconButton(
        icon: Icon(
          Icons.close,
        ),
        onPressed: () {
          query = "";
        },
      ),
    ];
  }

  @override
  Widget buildLeading(BuildContext context) {
    return IconButton(
      icon: Icon(
        Icons.arrow_back,
      ),
      onPressed: () {
        close(context, null);
      },
    );
  }

  @override
  Widget buildResults(BuildContext context) {
    List<String> top = List.from(
      _refs.where((element) => element.contains(query)).take(25),
    );

    return ListView(
      children: List.from(
        top.map(
          (e) {
            return ListTile(
              leading: Icon(Icons.format_shapes),
              title: Text(e),
              onTap: () => callback(e),
            );
          },
        ),
      ),
    );
  }

  @override
  Widget buildSuggestions(BuildContext context) {
    return buildResults(context);
  }
}

class _ReferencesScreenState extends State<ReferencesScreen> {
  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: 2,
      child: Scaffold(
        appBar: AppBar(
          title: Text(S.of(context).references),
          bottom: TabBar(
            tabs: [
              Tab(icon: Icon(Icons.input)),
              Tab(icon: Icon(Icons.list)),
            ],
          ),
          actions: [
            IconButton(
              icon: Icon(Icons.search),
              onPressed: () {
                showSearch(
                  context: context,
                  delegate: CustomSearchDelegate((reference) {
                    openViewer(context, reference);
                  }),
                );
              },
            ),
            IconButton(
              icon: Icon(Icons.refresh),
              onPressed: () {
                showDialog(
                  context: context,
                  builder: (_) => new SimpleDialog(
                    children: [
                      Padding(
                        padding: EdgeInsets.all(5),
                        child: UpdateDialog(() {
                          setState(() {});
                        }),
                      )
                    ],
                  ),
                );
              },
            ),
          ],
        ),
        drawer: AppDrawer(),
        body: TabBarView(
          children: [
            InputZoneWidget(),
            SearchZoneWidget(),
          ],
        ),
      ),
    );
  }
}

class SearchZoneWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return ListView(
      children: [
        Wrap(
          alignment: WrapAlignment.center,
          spacing: 8.0,
          runSpacing: 4.0,
          children: List<Widget>.from(
            RefsService().listRefs().map(
                  (e) => ActionChip(
                    label: Text(e),
                    onPressed: () {
                      openViewer(context, e);
                    },
                  ),
                ),
          ),
        )
      ],
    );
  }
}

class InputZoneWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Center(
      child: Padding(
        padding: EdgeInsets.all(32),
        child: TextField(
          textCapitalization: TextCapitalization.characters,
          decoration: new InputDecoration(
            hintText: 'Exact Reference',
          ),
          onSubmitted: (value) => openViewer(context, value.toUpperCase()),
        ),
      ),
    );
  }
}
