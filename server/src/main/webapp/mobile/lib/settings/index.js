'use strict';

var fs = require('fs');

var angular = require('angular');

var ngModule = angular.module('bringste.settings', []);

var SettingsController = [ '$scope', 'api', 'credentials', function($scope, api, credentials) {

  api.get('/users/:id', { params: { id: credentials.id } }).then(function(response) {
    $scope.profile = response.data;
  });

  $scope.save = function() {
    api.post('/users/:id', $scope.profile, { params: { id: credentials.id } }).then(function(response) {

    });
  };

}];

ngModule.config([ '$routeProvider', function($routeProvider) {
  $routeProvider.when('/settings', {
    controller: SettingsController,
    template: fs.readFileSync(__dirname + '/view.html', 'utf-8')
  });
}]);


module.exports = ngModule;