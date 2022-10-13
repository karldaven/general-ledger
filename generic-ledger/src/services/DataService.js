import axios from 'axios';

function createLedger(data) {
  return axios.post('http://localhost:8081/api/ledger', data).then(response => {
    return response.data;
  }).catch(error => {
    return Promise.reject(error);
  });
}

function getLedgers() {
  return axios.get('http://localhost:8081/api/ledger').then(response => {
    return response.data;
  }).catch(error => {
    return Promise.reject(error);
  });
}

function createTransaction(ledgerId, data) {
  return axios.post(`http://localhost:8081/api/ledger/${ledgerId}/transactions`, data).then(response => {
    return response.data;
  }).catch(error => {
    return Promise.reject(error);
  });
}

function getTransactions(ledgerId) {
  return axios.get(`http://localhost:8081/api/ledger/${ledgerId}/transactions`).then(response => {
    return response.data;
  }).catch(error => {
    return Promise.reject(error);
  });
}

export default {
  createLedger,
  getLedgers,
  createTransaction,
  getTransactions
};
