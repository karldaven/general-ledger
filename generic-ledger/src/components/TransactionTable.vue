<template>
  <v-table>
    <thead>
      <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Debit</th>
        <th>Credit</th>
        <th>Balance</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="transaction in transactions" :key="transaction.id">
        <td>{{ transaction.transactionDateTime }}</td>
        <td>{{ transaction.description }}</td>
        <td><span v-if="!transaction.isCredit" class="debit">{{ this.currency }}{{ transaction.amount }}</span></td>
        <td><span v-if="transaction.isCredit" class="credit">{{ this.currency }}{{ transaction.amount }}</span></td>
        <td>{{ this.currency }}{{ transaction.balance }}</td>
      </tr>
    </tbody>
  </v-table>
  <div v-if="transactions.length < 1" class="ma-3">
    <div class="text-center">No transactions yet.</div>
  </div>
</template>

<script>
export default {
  name: 'TransactionTable',

  props: {
    currency: String,
    transactions: Object
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
