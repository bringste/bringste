'use strict';

var fs = require('fs');

var angular = require('angular');


var ngModule = angular.module('bringste.lists.new', []);

var NewListController = [ '$scope', '$navigation', 'focus', 'api', function($scope, $navigation, focus, api) {

  $scope.list = {
    dueDate: null,
    targetLocation: {name: "", longitude: "", latitude: ""},
    deliverHome: true,
    tipAmount: 5,
    items: [{name: "Beer"}, {name: ""}]
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
        focus(items[idx]);
      }
    }
  };

  $scope.deliver = function(deliverHome) {
    $scope.list.deliverHome = deliverHome;
  };

  $scope.create = function() {
    api.post("/shopping-list/new", $scope.list).then(function(success) {
      $navigation.pop({ href: '/lists' });
    }, function(err) {
      console.log(err);
    });
  };
}];


ngModule.config([ '$routeProvider', function($routeProvider) {

  $routeProvider.when('/lists/new', {
    controller: NewListController,
    template: fs.readFileSync(__dirname + '/view.html', 'utf-8')
  });

}]);


module.exports = ngModule;