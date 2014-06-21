'use strict';

var fs = require('fs');

var angular = require('angular');


var ngModule = angular.module('bringste.discover', [
  require('./map').name
]);


var DiscoverController = [ '$scope', function($scope) {
  $scope.bringRequests = [
    {
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
    }
  ];
}];

ngModule.config([ '$routeProvider', function($routeProvider) {

  $routeProvider.when('/discover', {
    controller: DiscoverController,
    template: fs.readFileSync(__dirname + '/view.html', 'utf-8')
  });

}]);


module.exports = ngModule;