import Vue from 'vue'
import '@mdi/font/css/materialdesignicons.css'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'

Vue.use(Vuetify)

export default new Vuetify({
	icons: {
		iconfont: 'mdi', // default - only for display purposes
	},
	theme: {
		dark: localStorage.getItem("dark") === "true"
	},
})