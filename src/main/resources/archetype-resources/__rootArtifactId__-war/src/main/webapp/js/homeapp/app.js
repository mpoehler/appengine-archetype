var homeApp = angular.module('homeApp', [
    'ngRoute',
    'homeAppControllers'
]);

// HINT: don't forget to update the sitemap servlet with the urls below.

homeApp.config(['$routeProvider','$locationProvider',
    function($routeProvider, $locationProvider) {
        $locationProvider.hashPrefix('!');
        $routeProvider.
            when('/imprint', {
                templateUrl: 'imprint.html'
            }).
            when('/about', {
                templateUrl: 'about.html'
            }).
            otherwise({
                templateUrl: 'welcome.html'
            });
    }]);