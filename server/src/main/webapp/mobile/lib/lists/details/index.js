'use strict';

var fs = require('fs');

var angular = require('angular');


var ngModule = angular.module('bringste.lists.details', []);

var DetailsController = [ '$scope', 'api', '$routeParams', function($scope, api, $routeParams) {

  api.get("/shopping-list/:id", { params: { id: $routeParams.id } }).then(function(result) {
    $scope.list = result.data;

    console.log(result.data);
  });


  $scope.pay = function() {

    api.post('/shopping-list/:id/transfer-tip', { params: { id: $routeParams.id } }).then(function(response) {

      var data = response.data;
      window.location = data;
    });

  };
}];


ngModule.config([ '$routeProvider', function($routeProvider) {

  $routeProvider.when('/lists/:id', {
    controller: DetailsController,
    template: fs.readFileSync(__dirname + '/view.html', 'utf-8')
  });

}]);


module.exports = ngModule;