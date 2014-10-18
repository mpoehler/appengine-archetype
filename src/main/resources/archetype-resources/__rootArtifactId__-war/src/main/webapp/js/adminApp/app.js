var adminApp = angular.module('adminApp', [
    'ngRoute',
    'adminAppControllers'
]);

adminApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/imprint', {
                templateUrl: '../imprint.html'
            }).
            when('/about', {
                templateUrl: '../about.html'
            }).
            otherwise({
                templateUrl: '../welcome.html'
            });
    }]);