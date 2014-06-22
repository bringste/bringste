'use strict';

var fs = require('fs');

var angular = require('angular');

var ngModule = angular.module('bringste.shoppinglist', []);

var ShoppingListController = [ '$scope', '$location', 'api', function($scope, $location, api) {

  $scope.view = $location.search().view || 'aggregated';

  function update() {

    api.get('/shopping-lists/assigned').then(function(result) {
      var lists = result.data.lists;

      $scope.shoppinglists = lists;
      $scope.allItems = [];

      angular.forEach(lists, function(list) {

        angular.forEach(list.items, function(item) {
          $scope.allItems.push({ list: list, entry: item });
        });
      });
    });
  }


  update();


  $scope.finishShopping = function() { };

  $scope.setDelivered = function(e) {};

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