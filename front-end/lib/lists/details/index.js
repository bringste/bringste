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

  $scope.changed = function(box) {

    var items = $scope.list.items;

    var idx = items.indexOf(box);
    if (idx == items.length - 1) {
      if (box.name) {
        items.push({ name: '' });
      }
    } else {
      if (!box.name) {
        items.splice(idx, 1);

        console.log(items[idx]);

        focus(items[idx]);
      }
    }
  };

  $scope.deliver = function(type) {
    $scope.list.deliver = type;
  };

  $scope.create = function() {
    console.log($scope.list);
  };

}];


ngModule.config([ '$routeProvider', function($routeProvider) {

  $routeProvider.when('/lists/details', {
    controller: DetailsController,
    template: fs.readFileSync(__dirname + '/view.html', 'utf-8')
  });

}]);


module.exports = ngModule;