import 'dart:convert';
import 'dart:io';
import 'package:flutter/widgets.dart';
import 'package:negrosa/preferences/sharedpreferenceshelper.dart';
import 'package:path_provider/path_provider.dart';
import 'package:http/http.dart' as http;
import 'package:path/path.dart';

enum UpdateAction {
  fetching,
  delete,
  download,
  done,
}
typedef OnUpdateProgressListener = void Function(
  UpdateAction action,
  String ref,
  int position,
  int remainingItem,
);
typedef OnUpdatedListener = void Function();
typedef OnUpdateErrorListener = void Function(
  UpdateAction action,
  String ref,
  Exception exception,
);

class RefsService {
  Directory _applicationDirectory;
  Directory _storageDirectory;

  void initialize() async {
    _applicationDirectory = await getApplicationDocumentsDirectory();
    _storageDirectory =
        Directory("${_applicationDirectory.path}/refs-storage/");

    if (!_storageDirectory.existsSync()) {
      _storageDirectory.createSync();
    }

    print("Reference Service: Initialized");
  }

  Future<void> update(
    OnUpdateProgressListener progressListener,
    OnUpdatedListener updatedListener,
    OnUpdateErrorListener errorListener,
  ) async {
    progressListener(
      UpdateAction.fetching,
      null,
      null,
      null,
    );

    return baseUrl().then((baseUrl) => {
          http.get("$baseUrl/api/references").then((response) async {
            List<String> current = listRefs();

            List<String> newRefs = List();
            List<String> sameRefs = List();
            List<String> deletedRefs = List();

            for (String ref
                in List.castFrom(jsonDecode(response.body)["payload"])) {
              if (current.contains(ref)) {
                print("Same ref: $ref");
                sameRefs.add(ref);
              } else {
                print("New ref: $ref");
                newRefs.add(ref);
              }
            }

            for (String ref in current) {
              if (!sameRefs.contains(ref) && !newRefs.contains(ref)) {
                print("Deleted ref: $ref");
                deletedRefs.add(ref);
              }
            }

            for (int i = 0; i < deletedRefs.length; i++) {
              String ref = deletedRefs[i];

              progressListener(
                UpdateAction.delete,
                ref,
                i,
                deletedRefs.length,
              );

              print("Deleting: $ref");

              try {
                File("${_storageDirectory.path}/$ref").deleteSync();
              } on Exception catch (exception) {
                errorListener(UpdateAction.delete, ref, exception);
              }
            }

            for (int i = 0; i < newRefs.length; i++) {
              String ref = newRefs[i];

              progressListener(
                UpdateAction.download,
                ref,
                i,
                newRefs.length,
              );

              print("Downloading: $ref");

              try {
                var response = await http.get("$baseUrl/api/references/$ref");

                if (response.statusCode != 200) {
                  throw Exception("$ref returned ${response.statusCode}");
                }

                File("${_storageDirectory.path}/$ref")
                    .writeAsBytesSync(response.bodyBytes);
              } on Exception catch (exception) {
                errorListener(UpdateAction.download, ref, exception);
              }
            }

            updatedListener();
          }).catchError((error) {
            errorListener(UpdateAction.fetching, null, error);
          })
        });
  }

  FileImage imageOf(String ref) {
    return FileImage(File("${_storageDirectory.path}/$ref"));
  }

  List<String> listRefs() {
    return List.of(_storageDirectory.listSync().map((e) => basename(e.path)));
  }

  Future<String> baseUrl() async {
    return SharedPreferencesHelper.getServerAddress();
  }

  factory RefsService() => _singleton;

  static final RefsService _singleton = RefsService._internal();
  RefsService._internal(); // private constructor
}
