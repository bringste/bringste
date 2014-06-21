'use strict';

var fs = require('fs');

var angular = require('angular');


var ngModule = angular.module('bringste.discover', [
  require('./map').name
]);


var DiscoverController = [ '$scope', function($scope) {
  $scope.bringRequests = [
    {
      isSelected: true,
      isExpanded: false,
      reward: 3.00,
      user: {
        imageUrl: 'http://lorempixel.com/42/42/people/',
        name: 'Oma Inge'
      },
      address: {
        street: 'Foo street 12',
        city: '10121 Berlin'
      },
      items: [
        { name: 'Milch' },
        { name: 'Brot' },
        { name: 'Zucker' }
      ]
    },
    {
      isSelected: false,
      isExpanded: false,
      reward: 5.00,
      user: {
        imageUrl: 'http://lorempixel.com/42/42/people/',
        name: 'Oma Inge'
      },
      address: {
        street: 'Foo street 13',
        city: '10121 Berlin'
      },
      items: [
        { name: 'Milch' },
        { name: 'Brot' },
        { name: 'Zucker' }
      ]
    },
    {
      isSelected: false,
      isExpanded: false,
      reward: 6.00,
      user: {
        imageUrl: 'http://lorempixel.com/42/42/people/',
        name: 'Oma Inge'
      },
      address: {
        street: 'Foo street 14',
        city: '10121 Berlin'
      },
      items: [
        { name: 'Milch' },
        { name: 'Brot' },
        { name: 'Zucker' }
      ]
    },
    {
      isSelected: false,
      isExpanded: false,
      reward: 8.00,
      user: {
        imageUrl: 'http://lorempixel.com/42/42/people/',
        name: 'Oma Inge'
      },
      address: {
        street: 'Foo street 15',
        city: '10121 Berlin'
      },
      items: [
        { name: 'Milch' },
        { name: 'Brot' },
        { name: 'Zucker' }
      ]
    }
  ];

  $scope.toggleSelected = function(list) {
    list.isSelected = !list.isSelected;
  };

  $scope.toggleExpansion = function(list) {
    list.isExpanded = !list.isExpanded;
  };

}];

ngModule.config([ '$routeProvider', function($routeProvider) {

  $routeProvider.when('/discover', {
    controller: DiscoverController,
    template: fs.readFileSync(__dirname + '/view.html', 'utf-8')
  });

}]);


module.exports = ngModule;