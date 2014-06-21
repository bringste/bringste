'use strict';

var fs = require('fs');

var angular = require('angular');


var ngModule = angular.module('bringste.settings', []);


var SettingsController = [ '$scope', function($scope) {
  $scope.foo = 'saddsadsadsa';
}];


ngModule.config([ '$modalProvider', function($modalProvider) {

  $modalProvider.register('settings', {
    controller: SettingsController,
    template: fs.readFileSync(__dirname + '/view.html', 'utf-8')
  });

}]);


module.exports = ngModule;