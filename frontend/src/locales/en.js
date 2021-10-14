export default {
  _: "English",
  drawer: {
    home: "Home",
    references: "References",
    motd: "Motd",
    settings: "Settings",
    about: "About",
  },
  references: {
    _: "References",
    actions: {
      add: {
        tooltip: "Add",
      },
      refresh: {
        tooltip: "Refresh",
      },
      open: {
        tooltip: "Open",
      },
      delete: {
        tooltip: "Delete",
      },
    },
    dialog: {
      import: {
        title: "Import",
        fields: {
          files: {
            label: "Files",
          },
        },
        actions: {
          upload: "Upload",
          cancel: "Cancel",
        },
        errors: {
          conflict: "Conflict: reference already exists",
          "not-acceptable": "Not acceptable: bad extension",
          internal: "Internal error",
        },
      },
    },
  },
  motd: {
    _: "Motd of the Day",
    actions: {
      open: {
        tooltip: "Open",
      },
      delete: {
        tooltip: "Delete",
        title: "Deletion confirmation",
        text: "Are you sure you want to delete this?",
      },
      import: {
        tooltip: "Import",
      },
      "update-slide-duration": {
        title: "Update slide duration",
        text: "Value (in seconds) (use 0 to use default)",
      },
      "update-name": {
        title: "Update name",
        text: "New name",
      },
      "update-duration": {
        title: "Update default slide duration",
        text: "Value (in seconds)",
      },
    },
    state: {
      active: "active",
    },
    error: {
      fetch: "Could not fetch: {error}",
      update: "Could not update: {error}",
      delete: "Could not delete: {error}",
      import: "Could not import: {error}",
    },
    success: {
      update: "Updated",
      delete: "Deleted",
      activate: "Activated",
      deactivate: "Deactivated",
      import: "Imported"
    },
    text: {
      "default-slide-duration": "Default Slide Duration: {value}s",
      "no-active": "no active presentation"
    },
  },
  settings: {
    _: "Settings",
    general: {
      _: "General",
      language: {
        _: "Language",
        description: "Change site display language",
      },
      dark: {
        _: "Dark",
        description: "Change theme",
      },
    },
  },
  about: {
    by: "by",
  },
};
