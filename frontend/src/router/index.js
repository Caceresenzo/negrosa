import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../pages/index.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/references',
    name: 'References',
    component: () => import(/* webpackChunkName: "references" */ '../pages/references/index.vue')
  },
  {
    path: '/motd/active',
    name: 'Active Motd',
    component: () => import(/* webpackChunkName: "motd" */ '../pages/motd/active.vue'),
  },
  {
    path: '/motd',
    name: 'Motd',
    component: () => import(/* webpackChunkName: "motd" */ '../pages/motd/index.vue'),
    children: [
      {
        path: ':id',
        component: () => import(/* webpackChunkName: "motd" */ '../pages/motd/_id/index.vue'),
      }
    ]
  },
  {
    path: '/settings',
    name: 'Settings',
    component: () => import(/* webpackChunkName: "settings" */ '../pages/settings.vue')
  },
  {
    path: '/about',
    name: 'About',
    component: () => import(/* webpackChunkName: "about" */ '../pages/about.vue')
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
