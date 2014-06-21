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

    $get: [ '$q', '$http', '$rootScope', '$location', '$navigationCache', function($q, $http, $rootScope, $location, $navigationCache) {

      var $route = { };

      $rootScope.$on('$locationChangeSuccess', updateRoute);


      function loadTemplate(config) {

        if (config.template) {
          return $q.when(config.template);
        } else {
          var deferred = $q.defer();

          $http.get(config.templateUrl).then(function(response) {
            deferred.resolve(response.data);
          }, function(err) {
            deferred.reject(err);
          });

          return deferred.promise;
        }
      }


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

        var newRoute = angular.extend(current || {}, routeConfig);

        // TODO: error handling
        loadTemplate(newRoute).then(function(tpl) {

          if (!current) {
            $navigationCache.push(newRoute);
          }

          newRoute.locals = angular.extend(newRoute.locals || {}, { $template: tpl });

          $route.current = newRoute;
          $rootScope.$broadcast('$routeChangeSuccess', { route: newRoute });
        }, function(err) {
          $rootScope.$broadcast('$routeChangeFailure', { error: err });
        });

      }

      function push(entry) {
        $navigationCache.push(entry);
        $location.path(entry.href);
      }

      function pop(element) {
        $navigationCache.pop();
        $location.path(element.href);
      }

      $route.push = push;
      $route.pop = pop;

      return $route;
    }],

    when: function(url, config) {
      routes[url] = config;
      return this;
    },

    otherwise: function(config) {
      return this.when(null, config);
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



rtViewFactory.$inject = ['$route', '$animate'];
function rtViewFactory(   $route,   $animate) {
  return {
    restrict: 'ECA',
    terminal: true,
    priority: 400,
    transclude: 'element',
    link: function(scope, $element, attr, ctrl, $transclude) {
      var currentScope,
          currentElement,
          previousElement,
          onloadExp = attr.onload || '';

      scope.$on('$routeChangeSuccess', update);
      update();

      function cleanupLastView() {
        if(previousElement) {
          previousElement.remove();
          previousElement = null;
        }
        if(currentScope) {
          currentScope.$destroy();
          currentScope = null;
        }
        if(currentElement) {
          $animate.leave(currentElement, function() {
            previousElement = null;
          });
          previousElement = currentElement;
          currentElement = null;
        }
      }

      function update() {
        var locals = $route.current && $route.current.locals,
            template = locals && locals.$template;

        if (angular.isDefined(template)) {
          var newScope = scope.$new();
          var current = $route.current;

          // Note: This will also link all children of ng-view that were contained in the original
          // html. If that content contains controllers, ... they could pollute/change the scope.
          // However, using ng-view on an element with additional content does not make sense...
          // Note: We can't remove them in the cloneAttchFn of $transclude as that
          // function is called before linking the content, which would apply child
          // directives to non existing elements.
          var clone = $transclude(newScope, function(clone) {
            $animate.enter(clone, null, currentElement || $element);
            cleanupLastView();
          });

          currentElement = clone;
          currentScope = current.scope = newScope;
          currentScope.$emit('$viewContentLoaded');
          currentScope.$eval(onloadExp);
        } else {
          cleanupLastView();
        }
      }
    }
  };
}

rtViewFillContentsFactory.$inject = ['$compile', '$controller', '$route'];
function rtViewFillContentsFactory(   $compile,   $controller,   $route) {
  return {
    restrict: 'ECA',
    priority: -400,
    link: function(scope, $element) {
      var current = $route.current,
          locals = current.locals;

      $element.html(locals.$template);

      var link = $compile($element.contents());

      if (current.controller) {
        locals.$scope = scope;
        var controller = $controller(current.controller, locals);
        if (current.controllerAs) {
          scope[current.controllerAs] = controller;
        }
        $element.data('$ngControllerController', controller);
        $element.children().data('$ngControllerController', controller);
      }

      link(scope);
    }
  };
}

ngModule.directive('rtView', rtViewFactory);
ngModule.directive('rtView', rtViewFillContentsFactory);


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


ngModule.directive('rtNavigationPop', [ '$navigationSupport', '$route', function($navigationSupport, $route) {

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
          $route.pop({
            href: element.attr('href'),
            transition: attrs['transition']
          });
        });
      });

    }
  };

}]);


module.exports = ngModule;