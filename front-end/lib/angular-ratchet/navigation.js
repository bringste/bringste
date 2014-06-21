'use strict';

var angular = require('angular');

/** based on ratchet v2.0.2 push.js */


/* global _gaq: true */


var ngModule = angular.module('angular-ratchet.navigation', []);


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


ngModule.provider('$route', function() {

  var routes = {};

  var routeProvider = {

    $get: ['$rootScope', '$location', '$navigationCache', function($rootScope, $location, $navigationCache) {

      var currentRoute;

      $rootScope.$on('$locationChangeSuccess', updateRoute);

      function updateRoute() {

        var href = $location.path();

        var current = $navigationCache.get();

        if (!current || current.href !== href) {
          current = null;
        }

        var routeConfig = routes[href] || routes[null];

        if (!routeConfig) {
          throw new Error('no route for ' + href);
        }

        if (routeConfig.redirectTo) {
          return $location.path(routeConfig.redirectTo).replace();
        }

        /* jshint -W040 */

        currentRoute = angular.extend(current || {}, routeConfig);

        if (!current) {
          $navigationCache.push(currentRoute);
        }

        $rootScope.$broadcast('$routeChangeSuccess', { route: current });
      }

      function push(entry) {
        $navigationCache.push(entry);
        $location.path(entry.href);
      }

      function pop() {
        var last = $navigationCache.pop();

        $location.path(last.href);
      }

      return {
        push: push,
        pop: pop,
        current: function() {
          return currentRoute;
        }
      };
    }],

    when: function(url, config) {
      routes[url] = config;
    },

    otherwise: function(config) {
      this.when(null, config);
    }
  };

  return routeProvider;
});


ngModule.service('$navigationSupport', [ '$window', function($window) {

  var scrolling;

  $window.addEventListener('touchstart', function () { scrolling = false; });
  $window.addEventListener('touchmove', function () { scrolling = true; });

  return {
    isScrolling: function() {
      return scrolling;
    }
  };
}]);


ngModule.directive('rtView', [ '$document', '$http', '$compile', '$controller', '$rootScope', '$route', '$navigationCache',
  function($document, $http, $compile, $controller, $rootScope, $route, $navigationCache) {

  function withTemplate(config, complete) {

    if (config.template) {
      complete(config.template);
    } else {
      $http.get(config.templateUrl).then(function(response) {
        complete(response.data);
      }, function(err) {
        console.error(err);
      });
    }
  }

  return {

    link: function(scope, element, attrs) {

      scope.$on('$routeChangeSuccess', function(e) {

        var route = $navigationCache.get();

        withTemplate(route, function(tpl) {

          element.html(tpl);

          var scope = route.scope || $rootScope.$new();

          if (route.controller && !route.scope) {
            $controller(route.controller, { $scope: scope });
            route.scope = scope;
          }

          $compile(element)(scope);

          // force reflow to prevent scroll
          $document.get(0).body.offsetHeight;
        });

      });
    }
  };
}]);


ngModule.directive('rtNavigationPush', [ '$navigationSupport', '$route', function($navigationSupport, $route) {

  return {

    link: function(scope, element, attrs) {

      element.on('click', function(event) {
        event.preventDefault();
      });

      element.on('touchend', function(event) {
        event.preventDefault();

        if ($navigationSupport.isScrolling()) {
          return;
        }

        scope.$apply(function() {
          $route.push({
            href: element.attr('href'),
            transition: attrs['transition']
          });
        });
      });

    }
  };

}]);


module.exports = ngModule;