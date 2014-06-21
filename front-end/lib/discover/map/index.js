'use strict';

var fs = require('fs');

var angular = require('angular');


var ngModule = angular.module('bringste.discover.map', []);

var DiscoverMapController = [ '$scope', function($scope) {

}];


ngModule.directive('bsteMap', function() {

  return {
    link: function(scope, element, attrs) {
      var map = window.L.mapbox.map(element.get(0), 'bringste.iik932a2');
      map.setView([40, -74.50], 9);
    }
  };

});

ngModule.config([ '$routeProvider', function($routeProvider) {

  $routeProvider.when('/discover/map', {
    controller: DiscoverMapController,
    template: fs.readFileSync(__dirname + '/view.html', 'utf-8')
  });

}]);


module.exports = ngModule;