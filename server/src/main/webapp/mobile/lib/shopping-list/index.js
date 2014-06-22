'use strict';

var fs = require('fs');

var angular = require('angular');

var ngModule = angular.module('bringste.shoppinglist', []);

var ShoppinglistController = [ '$scope', '$http', function($scope, $http) {
  $scope.subscribedLists = [];

  $http.get("../../app/rest/shopping-lists/assigned").then(function(result) {
    $scope.subscribedLists = result.data.lists;
  });

  $scope.toggleSelected = function(list) {
    list.isSelected = !list.isSelected;
  };

}];

ngModule.config([ '$routeProvider', function($routeProvider) {

  $routeProvider.when('/shopping-list', {
    controller: ShoppinglistController,
    template: fs.readFileSync(__dirname + '/view.html', 'utf-8')
  });

}]);


module.exports = ngModule;