// GENERATED CODE - DO NOT MODIFY BY HAND
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'intl/messages_all.dart';

// **************************************************************************
// Generator: Flutter Intl IDE plugin
// Made by Localizely
// **************************************************************************

// ignore_for_file: non_constant_identifier_names, lines_longer_than_80_chars
// ignore_for_file: join_return_with_assignment, prefer_final_in_for_each
// ignore_for_file: avoid_redundant_argument_values

class S {
  S();
  
  static S current;
  
  static const AppLocalizationDelegate delegate =
    AppLocalizationDelegate();

  static Future<S> load(Locale locale) {
    final name = (locale.countryCode?.isEmpty ?? false) ? locale.languageCode : locale.toString();
    final localeName = Intl.canonicalizedLocale(name); 
    return initializeMessages(localeName).then((_) {
      Intl.defaultLocale = localeName;
      S.current = S();
      
      return S.current;
    });
  } 

  static S of(BuildContext context) {
    return Localizations.of<S>(context, S);
  }

  /// `Hello World!`
  String get helloWorld {
    return Intl.message(
      'Hello World!',
      name: 'helloWorld',
      desc: 'The conventional newborn programmer greeting',
      args: [],
    );
  }

  /// `References`
  String get references {
    return Intl.message(
      'References',
      name: 'references',
      desc: '',
      args: [],
    );
  }

  /// `Negro SA`
  String get negrosa {
    return Intl.message(
      'Negro SA',
      name: 'negrosa',
      desc: '',
      args: [],
    );
  }

  /// `Settings`
  String get settings {
    return Intl.message(
      'Settings',
      name: 'settings',
      desc: '',
      args: [],
    );
  }

  /// `Server Address`
  String get serverAddressSettings {
    return Intl.message(
      'Server Address',
      name: 'serverAddressSettings',
      desc: '',
      args: [],
    );
  }

  /// `Max History Size`
  String get maxHistorySizeSettings {
    return Intl.message(
      'Max History Size',
      name: 'maxHistorySizeSettings',
      desc: '',
      args: [],
    );
  }

  /// `<unspecified>`
  String get unspecified {
    return Intl.message(
      '<unspecified>',
      name: 'unspecified',
      desc: '',
      args: [],
    );
  }

  /// `Fetching`
  String get fetchingUpdateAction {
    return Intl.message(
      'Fetching',
      name: 'fetchingUpdateAction',
      desc: '',
      args: [],
    );
  }

  /// `Deleting`
  String get deletingUpdateAction {
    return Intl.message(
      'Deleting',
      name: 'deletingUpdateAction',
      desc: '',
      args: [],
    );
  }

  /// `Downloading`
  String get downloadingUpdateAction {
    return Intl.message(
      'Downloading',
      name: 'downloadingUpdateAction',
      desc: '',
      args: [],
    );
  }

  /// `Done`
  String get doneUpdateAction {
    return Intl.message(
      'Done',
      name: 'doneUpdateAction',
      desc: '',
      args: [],
    );
  }
}

class AppLocalizationDelegate extends LocalizationsDelegate<S> {
  const AppLocalizationDelegate();

  List<Locale> get supportedLocales {
    return const <Locale>[
      Locale.fromSubtags(languageCode: 'en'),
    ];
  }

  @override
  bool isSupported(Locale locale) => _isSupported(locale);
  @override
  Future<S> load(Locale locale) => S.load(locale);
  @override
  bool shouldReload(AppLocalizationDelegate old) => false;

  bool _isSupported(Locale locale) {
    if (locale != null) {
      for (var supportedLocale in supportedLocales) {
        if (supportedLocale.languageCode == locale.languageCode) {
          return true;
        }
      }
    }
    return false;
  }
}