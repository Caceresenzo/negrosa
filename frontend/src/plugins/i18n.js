import Vue from 'vue'
import VueI18n from 'vue-i18n'
import en from '../locales/en'
import fr from '../locales/fr'

Vue.use(VueI18n)

const messages = {
	en,
	fr,
};

let locale = localStorage.getItem("locale");
if (!(locale in messages)) {
	locale = "fr";
}

export default new VueI18n({
	locale,
	messages,
});