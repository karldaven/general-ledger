<template>
  <v-container>
    <v-btn
      class="mt-2 float-right"
      color="info"
      prepend-icon="mdi-plus"
      @click.prevent="openDialog">
      Create New
    </v-btn>
    
    <h1 class="mb-3">Ledgers</h1>

    <ledger-table :ledgers="ledgers"></ledger-table>

    <ledger-dialog
      :dialog="dialog"
      @closeDialog="closeDialog">
    </ledger-dialog>
  </v-container>
</template>

<script>
import LedgerDialog from '../components/LedgerDialog.vue';
import LedgerTable from '../components/LedgerTable.vue';
import { mapState } from 'vuex';

export default {
  name: 'Overview',

  components: {
    LedgerTable,
    LedgerDialog
  },

  data() {
    return {
      dialog: false
    };
  },

  computed: {
    ...mapState([
      'ledgers'
    ])
  },

  methods: {
    openDialog() {
      this.dialog = true;
    },
    closeDialog() {
      this.dialog = false;
    }
  }
};
</script>
