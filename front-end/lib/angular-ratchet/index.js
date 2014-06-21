'use strict';

var angular = require('angular');

var ngModule = angular.module('angular-ratchet', [
  require('./modal').name,
  require('./push').name
]);

module.exports = ngModule;