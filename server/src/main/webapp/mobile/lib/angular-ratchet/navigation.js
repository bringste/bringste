'use strict';

var angular = require('angular');

/** based on ratchet v2.0.2 push.js */


/* global _gaq: true */


var ngModule = angular.module('angular-ratchet.navigation', [
  require('angular-route').name
]);


ngModule.factory('$navigationCache', function() {

  var currentIdx = -1;
  var elements = [];

  return {
    push: function(entry) {

      // remove newer elements
      elements.length = ++currentIdx;

      elements.push(entry);
    },

    pop: function() {
      return elements[currentIdx--];
    },

    get: function() {
      return elements[currentIdx];
    }
  };

});


ngModule.service('$navigation', [ '$navigationCache', '$location', function($navigationCache, $location) {

  function NavigationService() {

    this.push = function(entry) {
      $navigationCache.push(entry);
      $location.url(entry.href);
    };

    this.pop = function(element) {
      $navigationCache.pop();
      $location.url(element.href);
    };
  }

  return new NavigationService();
}]);


ngModule.directive('rtNavPush', [ '$navigation', function($navigation) {

  return {

    link: function(scope, element, attrs) {

      element.on('click', function(event) {

        event.preventDefault();

        scope.$apply(function() {
          $navigation.push({
            href: element.attr('href'),
            transition: attrs['transition']
          });
        });
      });
    }
  };

}]);


ngModule.directive('rtNavPop', [ '$navigation', function($navigation) {

  return {

    link: function(scope, element, attrs) {

      element.on('click', function(event) {
        event.preventDefault();

        scope.$apply(function() {
          $navigation.pop({
            href: element.attr('href'),
            transition: attrs['transition']
          });
        });
      });

    }
  };

}]);


module.exports = ngModule;