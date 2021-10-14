export default {
  _: "Français",
  drawer: {
    home: "Accueil",
    references: "Références",
    motd: "Mot du Jour",
    settings: "Paramètres",
    about: "À propos",
  },
  references: {
    _: "Références",
    actions: {
      add: {
        tooltip: "Ajouter",
      },
      refresh: {
        tooltip: "Actualiser",
      },
      open: {
        tooltip: "Ouvrir",
      },
      delete: {
        tooltip: "Supprimer",
      },
    },
    dialog: {
      import: {
        title: "Importer",
        fields: {
          files: {
            label: "Fichiers",
          },
        },
        actions: {
          upload: "Envoyer",
          cancel: "Annuler",
        },
        errors: {
          conflict: "Conflit: la référence existe déjà",
          "not-acceptable": "Non acceptable: mauvaise extension",
          internal: "Erreur interne",
        },
      },
    },
  },
  motd: {
    _: "Mot du Jour",
    actions: {
      open: {
        tooltip: "Ouvrir",
      },
      delete: {
        tooltip: "Supprimer",
        title: "Confirmation de suppression",
        text: "Êtes-vous sûr de vouloir supprimer cela ?",
      },
      import: {
        tooltip: "Importer",
      },
      "update-slide-duration": {
        title: "Mettre à jour la durée",
        text: "Valeur (en secondes) (utilisez 0 pour utiliser la valeur par défaut)",
      },
      "update-name": {
        title: "Mettre à jour le nom",
        text: "Nouveau nom",
      },
      "update-duration": {
        title: "Mettre à jour la durée par défaut",
        text: "Valeur (en secondes)",
      },
    },
    state: {
      active: "actif",
    },
    error: {
      fetch: "Impossible de récupérer: {error}",
      update: "Impossible de mettre à jour: {error}",
      delete: "Impossible de supprimer: {error}",
      import: "Impossible d'importer: {error}"
    },
    text: {
      "default-slide-duration": "Durée de la Diapositive par Défaut: {value}s",
      "no-active": "pas de présentation active"
    },
  },
  settings: {
    _: "Paramètres",
    general: {
      _: "Générale",
      language: {
        _: "Langue",
        description: "Changer la langue d'affichage du site",
      },
      dark: {
        _: "Foncé",
        description: "Change le thème",
      },
    },
  },
  about: {
    by: "par",
  },
};
