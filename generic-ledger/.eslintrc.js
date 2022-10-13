module.exports = {
  'root': true,
  'env': {
    'node': true
  },
  extends: [
    'plugin:vue/vue3-essential',
    'eslint:recommended'
  ],
  parserOptions: {
    'parser': '@babel/eslint-parser'
  },
  rules: {
    'comma-dangle': 'error',
    'quotes': [
      'error',
      'single'
    ],
    'linebreak-style': [
      'error',
      'unix'
    ],
    'array-bracket-spacing': [
      'error',
      'always'
    ],
    'semi': [
      'error',
      'always'
    ],
    'eol-last': [
      'error',
      'always'
    ],
    'indent': [
      'error',
      2
    ],
    'vue/multi-word-component-names': 0
  }
};
