'use strict';

var angular = require('angular');


var ngModule = angular.module('bringste.api', []);


ngModule.service('api', [ '$http', function($http) {

  var root = 'http://localhost:8080/app/rest';

  var methods = ['get', 'post'];

  var api = {};

  angular.forEach(methods, function(name) {

    api[name] = function() {

      var args = Array.prototype.slice.call(arguments),
          last = null;

      if (args.length > 0) {
        last = args[args.length - 1];
      }

      var url = args[0];

      if (last && angular.isObject(last) && last.params) {

        angular.forEach(last.params, function(val, key) {
          url = url.replace(':' + key, val);
        });
      }

      if (url.indexOf('/') === 0) {
        url = root + url;
      }

      args[0] = url;

      return $http[name].apply($http, args);
    };
  });

  return api;
}]);


module.exports = ngModule;