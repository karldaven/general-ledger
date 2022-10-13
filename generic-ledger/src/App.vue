<template>
  <v-app>
    <v-main>
      <router-view></router-view>
    </v-main>
  </v-app>
</template>

<script>
import { mapActions } from 'vuex';

export default {
  name: 'App',

  created () {
    this.loadLedgers();
  },

  methods: {
    ...mapActions([
      'getLedgers'
    ]),
    async loadLedgers() {
      await this.getLedgers();
    }
  }
};
</script>
