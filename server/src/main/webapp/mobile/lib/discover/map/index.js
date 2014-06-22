'use strict';

var fs = require('fs');

var angular = require('angular');


var ngModule = angular.module('bringste.discover.map', []);

var DiscoverMapController = [ '$scope', 'api', function($scope, api) {
  api.get("/shopping-lists").then(function(result) {
    $scope.shoppingLists = result.data.lists;
  });
}];


ngModule.directive('bsteMap', function() {

  return {
    link: function(scope, element, attrs) {

      var map = window.L.mapbox.map(element.get(0), 'bringste.iip05e3b');
      map.setView([39.12367, -76.81229], 9);
      var myLayer = L.mapbox.featureLayer().addTo(map);
      var shoppingListsLayer = L.mapbox.featureLayer().addTo(map);

      scope.$watch(attrs.entries, function(value){
        shoppingListsLayer.setGeoJSON([]);

        var geojson = {
          type: 'FeatureCollection',
          features: []
        };

        for (var key in value) {
          var shoppingList = value[key];
          var shoppingListTarget = shoppingList.targetLocation;
          var feature = {
            type: 'Feature',
            properties: {
              title: shoppingListTarget.name,
              'marker-color': '#00a3c4',
              'marker-size': 'large',
              'marker-symbol': shoppingList.items.length
            }, geometry: {
              type: 'Point',
              coordinates: [shoppingListTarget.longitude, shoppingListTarget.latitude]
            }
          };
          geojson.features.push(feature);
        }
        shoppingListsLayer.setGeoJSON(geojson);
      });

      myLayer.on('mouseover', function(e) {
        e.layer.openPopup();
      });
      myLayer.on('mouseout', function(e) {
        e.layer.closePopup();
      });

      //display current location
      map.on('locationfound', function(e) {
        map.fitBounds(e.bounds);

        myLayer.setGeoJSON({
            type: 'Feature',
            geometry: {
                type: 'Point',
                coordinates: [e.latlng.lng, e.latlng.lat]
            },
            properties: {
                'title': 'Here you are!',
                'marker-color': '#0076c4',
                'marker-size': 'small'
            }
        });
      });
      map.locate();
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