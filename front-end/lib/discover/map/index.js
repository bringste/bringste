'use strict';

var fs = require('fs');

var angular = require('angular');


var ngModule = angular.module('bringste.discover.map', []);

var DiscoverMapController = [ '$scope', function($scope) {

  var L = window.L;

  console.log(L);

  var map = L.mapbox.map('map', 'bringste.iik932a2').setView([40, -74.50], 9);

}];


ngModule.config([ '$routeProvider', function($routeProvider) {

  $routeProvider.when('/discover/map', {
    controller: DiscoverMapController,
    template: fs.readFileSync(__dirname + '/view.html', 'utf-8')
  });

}]);


module.exports = ngModule;