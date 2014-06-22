'use strict';

var fs = require('fs');

var angular = require('angular');


var ngModule = angular.module('bringste.lists', [
  require('./new').name
]);

var ListsController = [ '$scope', function($scope) {
  $scope.lists = [
    {
      createdAt: null,
      isDelivered: false,
      isSelected: false,
      isExpanded: false,
      isPayed: false,
      reward: 3.00,
      items: [{name: 'nutella'}, {name: 'blub mate'}]
    },
    {
      createdAt: null,
      isDelivered: true,
      isSelected: true,
      isPayed: true,
      isExpanded: false,
      reward: 5.00,
      items: [{name: 'sahne'}, {name: 'zucker'}]
    }
  ];

  $scope.toggleSelected = function(list) {
    list.selected = !list.selected;
  };

  $scope.toggleExpanded = function(list) {
    list.expanded = !list.expanded;
  };

}];

ngModule.config([ '$routeProvider', function($routeProvider) {

  $routeProvider.when('/lists', {
    controller: ListsController,
    template: fs.readFileSync(__dirname + '/view.html', 'utf-8')
  });

}]);


module.exports = ngModule;