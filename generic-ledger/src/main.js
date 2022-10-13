import { createApp } from 'vue';
import { createRouter, createWebHistory } from 'vue-router';
import store from './store';

import { createVuetify } from 'vuetify';
import { loadFonts } from './plugins/webfontloader';
import * as components from 'vuetify/components';
import * as directives from 'vuetify/directives';
import 'vuetify/styles';
import '@mdi/font/css/materialdesignicons.css';

import App from './App.vue';

import Overview from './pages/Overview.vue';
import LedgerDetail from './pages/LedgerDetail.vue';

loadFonts();

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'overview',
      component: Overview
    },
    {
      path: '/ledger/:ledgerId',
      name: 'ledger',
      component: LedgerDetail,
      props: route => ({
        ledgerId: Number(route.params.ledgerId)
      })
    }
  ]
});

createApp(App)
  .use(router)
  .use(store)
  .use(createVuetify({
    components,
    directives,
    icons: {
      iconfont: 'mdiSvg'
    }
  })).mount('#app');
