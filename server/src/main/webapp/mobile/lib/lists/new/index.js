'use strict';

var fs = require('fs');

var angular = require('angular');


var ngModule = angular.module('bringste.lists.new', []);

var NewListController = [ '$scope', 'focus', '$http', function($scope, focus, $http) {

  $scope.list = {
    dueDate: null,
    targetLocation: {name: "", longitude: "", latitude: ""},
    deliverHome: false,
    tipAmount: 5,
    items: []
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
    $http.post("../../app/rest/shopping-list/new", $scope.list);
  };

}];


ngModule.config([ '$routeProvider', function($routeProvider) {

  $routeProvider.when('/lists/new', {
    controller: NewListController,
    template: fs.readFileSync(__dirname + '/view.html', 'utf-8')
  });

}]);


module.exports = ngModule;