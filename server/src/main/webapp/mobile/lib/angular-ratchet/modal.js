'use strict';

var angular = require('angular');

var ngModule = angular.module('angular-ratchet.modal', []);


ngModule.provider('$modal', function() {

  var modals = {};

  var modalProvider = {

    register: function(id, config) {
      modals[id] = config;
    },

    $get: [ '$rootScope', '$http', '$timeout', '$controller', '$compile',
    function($rootScope,   $http,   $timeout,   $controller,   $compile) {

      var opened = { };

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

      function open(id) {

        var config = modals[id];

        withTemplate(config, function(tpl) {

          var element = angular.element(tpl).appendTo(document.body);
          var scope = $rootScope.$new();

          if (config.controller) {
            $controller(config.controller, { $scope: scope });
          }

          $compile(element)(scope);

          element.addClass('active');

          opened[id] = element;
        });
      }

      function close(id) {

        var modal = opened[id];
        if (modal) {

          modal.removeClass('active');

          $timeout(function() {
            modal.remove();
          }, 2000);

          delete opened[id];
        }
      }

      return {
        open: open,
        close: close,
        toggle: function(id) {
          if (opened[id]) {
            close(id);
          } else {
            open(id);
          }
        }
      };

    }]
  };

  return modalProvider;
});


ngModule.directive('rtToggleModal', function($modal) {

  return {

    link: function(scope, element, attrs) {

      element.on('click', function(e) {

        // prevent actual navigation
        e.preventDefault();
        e.stopPropagation();

        // toggle state
        var name = attrs['rtToggleModal'];
        $modal.toggle(name);

        scope.$digest();
      });
    }
  };

});


module.exports = ngModule;