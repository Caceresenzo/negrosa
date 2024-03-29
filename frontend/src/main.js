import Vue from 'vue'
import App from './layouts/default.vue'
import router from './router'
import store from './store'
import vuetify from '@/plugins/vuetify'
import axios from '@/plugins/axios'
import i18n from '@/plugins/i18n'
import '@/plugins/countdown'
import '@/plugins/vuetify-dialog'
import '@/assets/scrollbars.scss'

Vue.config.productionTip = false
Vue.prototype.$http = axios

new Vue({
  router,
  store,
  vuetify,
  i18n,
  render: h => h(App)
}).$mount('#app')
