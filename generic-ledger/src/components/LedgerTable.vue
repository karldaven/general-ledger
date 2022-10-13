<template>
  <v-table>
    <thead>
      <tr>
        <th>Name</th>
        <th>Currency</th>
        <th>Description</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="ledger in ledgers" :key="ledger.id" @click="goToDetail(ledger.id)">
        <td>{{ ledger.name }}</td>
        <td>{{ ledger.currency }}</td>
        <td>{{ ledger.description }}</td>
      </tr>
    </tbody>
  </v-table>
  <div v-if="ledgers.length < 1" class="ma-3">
    <div class="text-center">No ledgers yet.</div>
  </div>
</template>

<script>
export default {
  name: 'LedgerTable',

  props: {
    ledgers: Object
  },

  methods: {
    goToDetail(ledgerId) {
      this.$router.push({
        name: 'ledger',
        params: {
          ledgerId
        }
      });
    }
  }
};
</script>
