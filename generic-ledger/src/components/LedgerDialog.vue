<template>
  <v-dialog v-model="showDialog" width="300px" persistent>
    <v-card>
      <v-card-title>
        New Ledger
      </v-card-title>
      <v-card-text>
        <v-text-field v-model="ledger.name" label="Name"></v-text-field>
        <v-select v-model="ledger.currency" :items="currencies" label="Currency" required></v-select>
        <v-text-field v-model="ledger.description" label="Description"></v-text-field>
      </v-card-text>
      <v-card-actions>
        <v-btn @click="submit" :disabled="disableSubmit" :loading="isProcessing">OK</v-btn>
        <v-btn @click="close">Cancel</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import { mapState, mapActions, mapGetters } from 'vuex';

export default {
  name: 'LedgerDialog',

  props: {
    dialog: Boolean
  },

  data() {
    return {
      showDialog: false,
      isProcessing: false,
      currencies: [ 'USD', 'JPY', 'KRW' ],
      ledger: {
        name: '',
        currency: '',
        description: ''
      }
    };
  },

  computed: {
    ...mapState([
      'ledgers'
    ]),
    ...mapGetters([
      'latestLedger'
    ]),
    disableSubmit() {
      return this.ledger.name === '' || this.ledger.currency === '';
    }
  },

  methods: {
    ...mapActions([
      'addLedger'
    ]),
    initialize() {
      this.isProcessing = false;
      this.ledger = {
        name: '',
        currency: '',
        description: ''
      };
    },
    async submit() {
      this.isProcessing = true;

      await this.addLedger(this.ledger);

      this.isProcessing = false;
      this.close();

      const ledgerId = this.latestLedger;
      if (ledgerId > 0) {
        this.$router.push({
          name: 'ledger',
          params: {
            ledgerId
          }
        });
      }
    },
    close() {
      this.initialize();
      this.showDialog = false;
      this.$emit('closeDialog');
    }
  },

  watch: {
    dialog(newValue) {
      this.showDialog = newValue;
    }
  }
};
</script>
