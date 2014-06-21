'use strict';

var angular = require('angular');

var ngModule = angular.module('bringste', [
  require('./angular-ratchet').name,
  require('./skeleton').name,
  require('./lists').name,
  require('./discover').name,
  require('./shopping-list').name,
  require('./settings').name,
  require('./util').name
]).run(function() {
  window.FastClick.attach(document.body);
});

ngModule.value('credentials', { id: 'admin', password: 'admin' });

ngModule.config([ '$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
  //$locationProvider.html5Mode(true);

  $routeProvider.otherwise({ redirectTo: '/lists' });
}]);


ngModule.run(['$http', 'credentials', function($http, credentials) {

  $http.post('http://www.bringste.berlin:80/app/authentication', {
    j_username: credentials.id,
    j_password: credentials.password
  }).then(function() {
    console.log('success', arguments);
  }, function(err) {
    console.log('err', arguments);
  });

}]);

module.exports = ngModule;
