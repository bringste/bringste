'use strict';

var fs = require('fs');

var angular = require('angular');


var ngModule = angular.module('bringste.discover.map', []);

var DiscoverMapController = [ '$scope', function($scope) {

}];


ngModule.directive('bsteMap', function() {

  return {
    link: function(scope, element, attrs) {
      var map = window.L.mapbox.map(element.get(0), 'bringste.iip05e3b');
      map.setView([39.12367, -76.81229], 9);
      var myLayer = L.mapbox.featureLayer().addTo(map);

      //display markers
      var geojson = {
          type: 'FeatureCollection',
          features: [{
              type: 'Feature',
              properties: {
                  title: 'Washington, D.C.',
                  'marker-color': '#00a3c4',
                  'marker-size': 'large',
                  'marker-symbol': '9',
                  url: 'http://en.wikipedia.org/wiki/Washington,_D.C.'
              },
              geometry: {
                  type: 'Point',
                  coordinates: [-77.03201, 38.90065]
              }
          },
          {
              type: 'Feature',
              properties: {
                  title: 'Baltimore, MD',
                  'marker-color': '#00a3c4',
                  'marker-size': 'large',
                  'marker-symbol': '3',
                  url: 'http://en.wikipedia.org/wiki/Baltimore'
              },
              geometry: {
                  type: 'Point',
                  coordinates: [-76.60767, 39.28755]
              }
          }]
      };

      myLayer.setGeoJSON(geojson);
      myLayer.on('mouseover', function(e) {
          e.layer.openPopup();
      });
      myLayer.on('mouseout', function(e) {
          e.layer.closePopup();
      });

      //display current location
      map.locate();
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