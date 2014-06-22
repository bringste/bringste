'use strict';

var fs = require('fs');

var angular = require('angular');


var ngModule = angular.module('bringste.lists', [
  require('./new').name,
  require('./details').name
]);

var ListsController = [ '$scope', 'api', function($scope, api) {
  $scope.lists = [];

  api.get("/shopping-lists/created").then(function(result){
    $scope.lists = result.data.lists;
  });

}];

ngModule.config([ '$routeProvider', function($routeProvider) {

  $routeProvider.when('/lists', {
    controller: ListsController,
    template: fs.readFileSync(__dirname + '/view.html', 'utf-8')
  });

}]);


module.exports = ngModule;