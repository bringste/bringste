'use strict';

var angular = require('angular');

var ngModule = angular.module('bringste', [
  require('angular-route').name,
]);

ngModule.config([ '$locationProvider', function($locationProvider) {
  $locationProvider.html5Mode(true);
}]);

require('./main');

module.exports = ngModule;
