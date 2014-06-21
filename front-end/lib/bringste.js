'use strict';

var angular = require('angular');

var ngModule = angular.module('bringste', [
  require('./angular-ratchet').name,
  require('./skeleton').name,
  require('./lists').name,
  require('./discover').name,
  require('./shoppinglist').name,
  require('./settings').name,
  require('./util').name
]);

ngModule.config([ '$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
  //$locationProvider.html5Mode(true);

  $routeProvider.otherwise({ redirectTo: '/lists' });
}]);

module.exports = ngModule;
