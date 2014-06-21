'use strict';

var fs = require('fs');

var angular = require('angular');

var ngModule = angular.module('bringste.shoppinglist', []);


var ShoppinglistController = [ '$scope', function($scope) {
  $scope.subscribedLists = [{
    creator: {
      id: 0,
      name: 'Oma Inge'
    },
    createdAt: null,
    isDelivered: false,
    items: [{name: 'test'}]
  }];
}];

ngModule.config([ '$routeProvider', function($routeProvider) {

  $routeProvider.when('/shopping-list', {
    controller: ShoppinglistController,
    template: fs.readFileSync(__dirname + '/view.html', 'utf-8')
  });

}]);


module.exports = ngModule;