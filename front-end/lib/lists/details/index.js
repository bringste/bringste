'use strict';

var fs = require('fs');

var angular = require('angular');


var ngModule = angular.module('bringste.lists.details', []);

var DetailsController = [ '$scope', 'focus', function($scope, focus) {

  $scope.list = {
    dueDate: null,
    deliver: 'home',
    pledge: 0,
    items: [ { name: '' } ]
  };

}];


ngModule.config([ '$routeProvider', function($routeProvider) {

  $routeProvider.when('/lists/new', {
    controller: DetailsController,
    template: fs.readFileSync(__dirname + '/view.html', 'utf-8')
  });

}]);


module.exports = ngModule;