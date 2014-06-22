'use strict';

var fs = require('fs');

var angular = require('angular');


var ngModule = angular.module('bringste.discover', [
  require('./map').name
]);


var DiscoverController = [ '$scope', '$location', 'api', function($scope, $location, api) {

  $scope.view = $location.search().view || 'map';

  api.get('/shopping-lists').then(function(response) {
    $scope.bringRequests = response.data.lists;
    $scope.error = null;
  }, function(err) {
    $scope.error = err;
  });

  $scope.toggleSelected = function(list) {
    var reserved = list.reserved,
        action = reserved ? 'unreserve' : 'reserve';

    api.post('/shopping-list/:id/:action', { params: { id: list.id, action: action } }).then(function() {
      list.reserved = !list.reserved;
    }, function(err) {
      console.log(err);
      // remove list from scope
      $scope.bringRequests.splice($scope.bringRequests.indexOf(list), 1);
    });
  };

  $scope.toggleExpanded = function(list) {
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