<template>
  <v-dialog v-model="showDialog" width="300px" persistent>
    <v-card>
      <v-card-title>
        {{ title }}
      </v-card-title>
      <v-card-text>
        <v-text-field
          v-model="transaction.amount"
          label="Amount"
          type="number"
          required
          :rules="[value => {
            if (isCredit && value > balance) {
              return 'Amount must be less than the current balance.';
            }
            return true;
          }]">
        </v-text-field>
        <v-text-field v-model="transaction.description" label="Description"></v-text-field>
      </v-card-text>
      <v-card-actions>
        <v-btn @click="submit" :disabled="disableSubmit" :loading="isProcessing">OK</v-btn>
        <v-btn @click="close">Cancel</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import moment from 'moment';
import { mapGetters, mapActions } from 'vuex';

export default {
  name: 'TransactionDialog',

  props: {
    balance: Number,
    dialog: Boolean,
    ledgerId: Number
  },

  data() {
    return {
      showDialog: false,
      isProcessing: false,
      transaction: {}
    };
  },

  created () {
    this.initialize();
  },

  computed: {
    ...mapGetters([
      'isCredit'
    ]),
    disableSubmit() {
      return this.transaction.amount === 0 || this.transaction.amount === '' || (this.isCredit && this.transaction.amount > this.balance);
    },
    title() {
      return this.isCredit? 'Credit' : 'Debit';
    }
  },

  methods: {
    ...mapActions([
      'addTransaction'
    ]),
    initialize() {
      this.isProcessing = false;
      this.transaction = {
        ledgerId: this.ledgerId,
        transactionDateTime: '',
        isCredit: false,
        amount: '',
        balance: this.balance,
        description: ''
      };
    },
    async submit() {
      this.transaction.transactionDateTime = moment().format('YYYY-MM-DD HH:mm:ss');
      this.transaction.isCredit = this.isCredit;
      this.transaction.balance = this.balance + (this.transaction.amount * (this.isCredit ? -1 : 1));
      this.isProcessing = true;

      await this.addTransaction(this.transaction);

      this.isProcessing = false;
      this.close();
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
