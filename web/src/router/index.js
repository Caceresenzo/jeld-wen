import Vue from 'vue'
import VueRouter from 'vue-router'

import Home from '../views/Home.vue'
import BeaconData from '../views/BeaconData.vue'

Vue.use(VueRouter)

const routes = [{
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/beacon-data',
    name: 'BeaconData',
    component: BeaconData
  },
  {
    path: '/settings',
    name: 'Settings',
    component: () => import('../views/Settings.vue')
  }
]

const router = new VueRouter({
  routes
})

export default router