'use strict';

var fs = require('fs');

var angular = require('angular');

var ngModule = angular.module('bringste.shoppinglist', []);

var ShoppinglistController = [ '$scope', function($scope) {
  $scope.foo = 'BAR';
}];

ngModule.config([ '$routeProvider', function($routeProvider) {

  $routeProvider.when('/shoppinglist', {
    controller: ShoppinglistController,
    template: fs.readFileSync(__dirname + '/view.html')
  });

}]);


module.exports = ngModule;