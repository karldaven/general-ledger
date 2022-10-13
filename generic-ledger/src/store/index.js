import { createStore } from 'vuex';
import DataService from '../services/DataService';
import {
  GET_LEDGERS,
  ADD_LEDGER,
  GET_TRANSACTIONS,
  SET_TRANSACTION_TYPE
} from './mutation-type';

const state = {
  ledgers: [],
  transactions: [],
  transactionType: 'debit'
};

const getters = {
  isCredit(state) {
    return state.transactionType === 'credit';
  },
  getLedger: (state) => (ledgerId) => {
    const result = state.ledgers.filter(ledger => ledger.id === ledgerId);
    return result.length === 1 ? result[0] : { name: '', currency:'', description:'' };
  },
  latestLedger(state) {
    const ledgerCount = state.ledgers.length;
    return ledgerCount > 0 ? state.ledgers[ledgerCount - 1].id : -1;
  }
};

const mutations = {
  [GET_LEDGERS](state, ledgers) {
    state.ledgers = ledgers;
  },
  [ADD_LEDGER](state, ledger) {
    state.ledgers.push(ledger);
  },
  [GET_TRANSACTIONS](state, transactions) {
    state.transactions = transactions;
  },
  [SET_TRANSACTION_TYPE](state, transactionType) {
    state.transactionType = transactionType;
  }
};

const actions = {
  async getLedgers({ commit }) {
    const ledgers = await DataService.getLedgers();
    commit(GET_LEDGERS, ledgers);
  },
  async addLedger({ commit }, ledger) {
    const createdLedger = await DataService.createLedger(ledger);
    commit(ADD_LEDGER, createdLedger);
  },
  async getTransactions({ commit }, ledgerId) {
    const transactions = await DataService.getTransactions(ledgerId);
    commit(GET_TRANSACTIONS, transactions);
  },
  async addTransaction({ dispatch }, transaction) {
    await DataService.createTransaction(transaction.ledgerId, transaction);
    dispatch('getTransactions', transaction.ledgerId);
  },
  async setTransactionType({ commit }, transactionType) {
    commit(SET_TRANSACTION_TYPE, transactionType);
  }
};

export default createStore({
  state,
  getters,
  mutations,
  actions
});
