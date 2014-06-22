'use strict';

var fs = require('fs');

var angular = require('angular');


var ngModule = angular.module('bringste.skeleton', []);

ngModule.directive('bsteNav', [ '$location', function($location) {

  return {
    template: fs.readFileSync(__dirname + '/nav.html', 'utf-8'),
    scope: true,
    link: function(scope) {

      scope.isActive = function(element) {
        return $location.path().indexOf(element) === 0;
      };
    }
  };

}]);


module.exports = ngModule;