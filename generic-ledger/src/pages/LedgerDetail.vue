<template>
  <v-container>
    <v-icon size="x-large" class="float-left" @click="goToHome">mdi-arrow-left</v-icon>
    <div class="text-center">
      <h1>{{ ledger.name }}</h1>
      <div class="text-subtitle-1">{{ ledger.description }}</div>

      <transaction-chart v-if="loaded"
        class="mt-10"
        :transactions="transactions"
        :key="transactions">
      </transaction-chart>

      <div class="ma-10">
        <div class="text-h2 font-weight-medium">{{ currencySymbol }}{{ this.currentBalance }}</div>
        <div class="text-caption">
          <span class="ma-3">Inflow: <span class="debit">+{{ currencySymbol }}{{ inflow }}</span></span>
          <span class="ma-3">Outflow: <span class="credit">-{{ currencySymbol }}{{ outflow }}</span></span>
        </div>
      </div>

      <div class="ma-7">
        <v-btn
          class="ma-1"
          color="success"
          prepend-icon="mdi-plus"
          @click.prevent="openDialog(false)">
          Debit
        </v-btn>

        <v-btn
          class="ma-1"
          color="error"
          prepend-icon="mdi-minus"
          :disabled="this.currentBalance === 0"
          @click.prevent="openDialog(true)">
          Credit
        </v-btn>

        <v-btn
          class="ma-1"
          prepend-icon="mdi-file-export-outline"
          :disabled="this.transactions.length < 1"
          @click.prevent="exportToCsv">
          Export
        </v-btn>
      </div>
    </div>

    <div class="text-h6">Transactions</div>
    <transaction-table :currency="currencySymbol" :transactions="transactions"></transaction-table>

    <transaction-dialog
      :balance="currentBalance"
      :dialog="dialog"
      :ledgerId="ledgerId"
      @closeDialog="closeDialog">
    </transaction-dialog>
  </v-container>
</template>

<script>
import TransactionTable from '../components/TransactionTable.vue';
import TransactionDialog from '../components/TransactionDialog.vue';
import TransactionChart from '../components/TransactionChart.vue';
import { mapState, mapActions, mapGetters } from 'vuex';
import Parser from 'json2csv';

export default {
  name: 'LedgerDetail',

  components: {
    TransactionTable,
    TransactionDialog,
    TransactionChart
  },

  props: {
    ledgerId: Number
  },

  data() {
    return {
      dialog: false,
      symbol: {
        'USD': '$',
        'JPY': '¥',
        'KRW': '₩'
      },
      loaded: false
    };
  },

  created () {
    this.loadTransactions();
  },

  computed: {
    ...mapState([
      'ledgers',
      'transactions',
      'transactionType'
    ]),
    ...mapGetters([
      'getLedger'
    ]),
    ledger() {
      return this.getLedger(this.ledgerId);
    },
    inflow() {
      let result = 0;

      this.transactions.filter(transaction => !transaction.isCredit).forEach(transaction => {
        result += transaction.amount;
      });

      return result;
    },
    outflow() {
      let result = 0;

      this.transactions.filter(transaction => transaction.isCredit).forEach(transaction => {
        result += transaction.amount;
      });
      
      return result;
    },
    currentBalance() {
      return this.transactions.length > 0 ? this.transactions[0].balance : 0;
    },
    currencySymbol() {
      return this.symbol[this.ledger.currency];
    }
  },

  methods: {
    ...mapActions([
      'getTransactions',
      'setTransactionType'
    ]),
    async loadTransactions() {
      await this.getTransactions(this.ledgerId);
      this.loaded = true;
    },
    async openDialog(isCredit) {
      await this.setTransactionType(isCredit ? 'credit' : 'debit');
      this.dialog = true;
    },
    closeDialog() {
      this.dialog = false;
    },
    goToHome() {
      this.$router.push({
        name: 'overview'
      });
    },
    exportToCsv() {
      const csv = Parser.parse(this.transactions, {
        fields: [
          {
            label: 'Date',
            value: 'transactionDateTime'
          },
          {
            label: 'Description',
            value: 'description'
          },
          {
            label: 'Debit',
            value: (item) => {
              return !item.isCredit ? `${this.ledger.currency} ${item.amount}` : '';
            }
          },
          {
            label: 'Credit',
            value: (item) => {
              return item.isCredit ? `${this.ledger.currency} ${item.amount}` : '';
            }
          },
          {
            label: 'Balance',
            value: (item) => {
              return `${this.ledger.currency} ${item.balance}`;
            }
          }
        ],
        quote: ''
      });

      const csvFile = new Blob([ csv ], {type: 'text/csv'});
      const link = document.createElement('a');
      link.href = URL.createObjectURL(csvFile);
      link.download = `${this.ledger.name}.csv`;
      link.click();
      URL.revokeObjectURL(link.href);
    }
  }
};
</script>

<style scoped>
.debit {
  color: #0C8732;
}
.credit {
  color: #DF330A;
}
</style>
