'use strict';

var fs = require('fs');

var angular = require('angular');


var ngModule = angular.module('bringste.lists', [
  require('./new').name
]);

var ListsController = [ '$scope', function($scope) {
  $scope.lists = [{
    createdAt: null,
    isDelivered: false,
    items: [{name: 'test'}]
  }];
}];


ngModule.config([ '$routeProvider', function($routeProvider) {

  $routeProvider.when('/lists', {
    controller: ListsController,
    template: fs.readFileSync(__dirname + '/view.html', 'utf-8')
  });

}]);


module.exports = ngModule;