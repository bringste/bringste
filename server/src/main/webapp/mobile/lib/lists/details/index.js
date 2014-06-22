'use strict';

var fs = require('fs');

var angular = require('angular');


var ngModule = angular.module('bringste.lists.details', []);

var DetailsController = [ '$scope', 'focus', '$route', function($scope, focus, $route) {

  $http.get("../../app/rest/shopping-list/" + $route.current.params.id).then(function(result){
    $scope.list = result.data;
  });

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

  $routeProvider.when('/lists/details/:id', {
    controller: DetailsController,
    template: fs.readFileSync(__dirname + '/view.html', 'utf-8')
  });

}]);


module.exports = ngModule;