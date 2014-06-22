'use strict';

var fs = require('fs');

var angular = require('angular');

var ngModule = angular.module('bringste.shoppinglist', []);

var ShoppinglistController = [ '$scope', function($scope) {
  $scope.subscribedLists = [
  {
    creator: {
      id: 0,
      name: 'Oma Inge'
    },
    createdAt: null,
    isDelivered: false,
    items: [{name: 'Fritz Cola', isSelected: true}, {name: 'Wasser', isSelected: false}]
  },
  {
    creator: {
      id: 0,
      name: 'Oma Erna'
    },
    createdAt: null,
    isDelivered: false,
    items: [{name: 'Milch', isSelected: true}, {name: 'Zucker', isSelected: false}]
  }
  ];

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