'use strict';

var angular = require('angular');

var ngModule = angular.module('bringste.util', []);

ngModule.service('focus', function() {

  var fn = function(element) {
    console.log('focus', element);
    fn.current = element;
  };

  return fn;
});


ngModule.directive('focusOn', [ 'focus', function(focus) {

  return {
    link: function(scope, element, attrs) {

      var obj = scope.$eval(attrs['focusOn']);

      scope.$watch(function() { return focus.current }, function(newFocus) {
        console.log(newFocus, obj);

        if (newFocus === obj) {
          element.focus();
        }
      });
    }
  };
}]);


module.exports = ngModule;