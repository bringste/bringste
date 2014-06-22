'use strict';

var fs = require('fs');

var angular = require('angular');

var ngModule = angular.module('bringste.shoppinglist', []);


var ShoppingListController = [ '$scope', '$location', function($scope, $location) {

  $scope.view = $location.search().view || 'aggregated';

  $scope.shoppingLists = [{
    creator: {
      id: 0,
      name: 'Oma Inge'
    },
    createdAt: null,
    isDelivered: false,
    items: [{name: 'Fritz Cola', selected: true}, {name: 'Wasser', selected: false}]
  },
  {
    creator: {
      id: 0,
      name: 'Oma Erna'
    },
    createdAt: null,
    isDelivered: false,
    items: [{name: 'Milch', selected: true}, {name: 'Zucker', selected: false}]
  }];


  $scope.allItems = [];

  angular.forEach($scope.shoppingLists, function(list) {

    angular.forEach(list.items, function(item) {
      $scope.allItems.push({ list: list, entry: item });
    });
  });

  $scope.finishShopping = function() {

  };

  $scope.setDelivered = function(e) {

  };

  $scope.toggleBought = function(entry) {
    var list = entry.list,
        element = entry.element;

    list.selected = !list.selected;
  };
}];


ngModule.config([ '$routeProvider', function($routeProvider) {

  $routeProvider.when('/shopping-list', {
    controller: ShoppingListController,
    template: fs.readFileSync(__dirname + '/view.html', 'utf-8')
  });

}]);


module.exports = ngModule;