import Vue from 'vue'
import VueI18n from 'vue-i18n'

Vue.use(VueI18n)

const messages = {
	en: {
		_: "English",
		drawer: {
			home: "Home",
			references: "References",
			settings: "Settings",
			about: "About",
		},
		references: {
			_: "References",
			actions: {
				add: {
					tooltip: "Add"
				},
				refresh: {
					tooltip: "Refresh"
				},
				open: {
					tooltip: "Open"
				},
				delete: {
					tooltip: "Delete"
				},
			},
			dialog: {
				import: {
					title: "Import",
					fields: {
						files: {
							label: "Files"
						}
					},
					actions: {
						upload: "Upload",
						cancel: "Cancel",
					},
					errors: {
						conflict: "Conflict: reference already exists",
						"not-acceptable": "Not acceptable: bad extension",
						"internal": "Internal error"
					}
				}
			}
		},
		settings: {
			_: "Settings",
			general: {
				_: "General",
				language: {
					_: "Language",
					description: "Change site display language"
				},
				dark: {
					_: "Dark",
					description: "Change theme"
				}
			},
		},
		about: {
			by: "by"
		}
	},
	fr: {
		_: "Français",
		drawer: {
			home: "Accueil",
			references: "Références",
			settings: "Paramètres",
			about: "À propos",
		},
		references: {
			_: "Références",
			actions: {
				add: {
					tooltip: "Ajouter"
				},
				refresh: {
					tooltip: "Actualiser"
				},
				open: {
					tooltip: "Ouvrir"
				},
				delete: {
					tooltip: "Supprimer"
				},
			},
			dialog: {
				import: {
					title: "Importer",
					fields: {
						files: {
							label: "Fichiers"
						}
					},
					actions: {
						upload: "Envoyer",
						cancel: "Annuler",
					},
					errors: {
						conflict: "Conflit: la référence existe déjà",
						"not-acceptable": "Non acceptable: mauvaise extension",
						"internal": "Erreur interne"
					}
				}
			}
		},
		settings: {
			_: "Paramètres",
			general: {
				_: "Générale",
				language: {
					_: "Langue",
					description: "Changer la langue d'affichage du site"
				},
				dark: {
					_: "Foncé",
					description: "Change le thème"
				}
			},
		},
		about: {
			by: "par"
		}
	}
};

let locale = localStorage.getItem("locale");
if (!(locale in messages)) {
	locale = "fr";
}

export default new VueI18n({
	locale,
	messages,
});