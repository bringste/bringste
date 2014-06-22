'use strict';

var fs = require('fs');

var angular = require('angular');

var ngModule = angular.module('bringste.settings', []);

var SettingsController = [ '$scope', '$http', 'credentials', function($scope, $http, credentials) {

  $http.get('http://www.bringste.berlin:80/app/rest/users/' + credentials.id).then(function(response) {
    $scope.profile = response.data;
  });

  $scope.save = function() {
    $http.post('http://www.bringste.berlin:80/app/rest/users/' + credentials.id, $scope.profile).then(function(response) {

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