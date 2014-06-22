'use strict';

var fs = require('fs');

var angular = require('angular');


var ngModule = angular.module('bringste.discover', [
  require('./map').name
]);


var DiscoverController = [ '$scope', 'api', function($scope, api) {

  api.get('/shopping-lists').then(function(response) {
    $scope.bringRequests = response.data.lists;

    for (var idx = 0; idx < $scope.bringRequests.length; ++idx) {
        var list = $scope.bringRequests[idx];
        list.expanded = false;
        list.selected = list.reserved;
    }

    console.log($scope.bringRequests);

    $scope.error = null;
  }, function(err) {
    $scope.error = err;
  });

  $scope.toggleSelected = function(list) {
    var selected = list.selected,
        action = selected ? 'unreserve' : 'reserve';

    api.post('/shopping-list/:id/:action', { params: { id: list.id, action: action } }).then(function() {
      list.selected = !list.selected;
    }, function(err) {
      console.log(err);
      // remove list from scope
      $scope.bringRequests.splice($scope.bringRequests.indexOf(list), 1);
    });
  };

  $scope.toggleExpanded = function(list) {
    console.log('acas');
    list.expanded = !list.expanded;
  };

}];

ngModule.config([ '$routeProvider', function($routeProvider) {

  $routeProvider.when('/discover', {
    controller: DiscoverController,
    template: fs.readFileSync(__dirname + '/view.html', 'utf-8')
  });

}]);


module.exports = ngModule;